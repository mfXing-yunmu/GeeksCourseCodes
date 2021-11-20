package com.yunmu.geek.outbound.okhttp;

import com.yunmu.geek.filter.HeaderHttpResponseFilter;
import com.yunmu.geek.filter.HttpRequestFilter;
import com.yunmu.geek.filter.HttpResponseFilter;
import com.yunmu.geek.inbound.HttpInboundServer;
import com.yunmu.geek.router.HttpEndpointRouter;
import com.yunmu.geek.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler {
    private static Logger logger = LoggerFactory.getLogger(OkhttpOutboundHandler.class);

    /** 资源解析类型 */
    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /** 初始化 HTTP 客户端 */
    public OkHttpClient okHttpClient = new OkHttpClient();

    /** 服务端地址集合 */
    private List<String> backendUrls;

    /** 代理线程池 */
    private ExecutorService proxyService;

    /** 过滤器 */
    HttpResponseFilter filter = new HeaderHttpResponseFilter();

    /** 路由 */
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public OkhttpOutboundHandler(List<String> backends) {
        /** 服务端地址集合 */
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        /** 启动一个线程池 */
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        /** 从服务端地址集合中随机路由一个地址返回，用于访问 */
        String backendUrl = router.route(this.backendUrls);

        /** 拼装完整的请求 URL 路径 */
        final String url = backendUrl + fullRequest.uri();
        logger.info("完整请求 URL 路径：{}", url);

        filter.filter(fullRequest, ctx);
        proxyService.submit(()-> {
            try {
                okHttpPost(fullRequest, ctx, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /** POST 请求 */
    public void okHttpPost(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) throws IOException {
        FullHttpResponse fullHttpResponse = null;
        String responseBody = "Default";
        long contentLength = 0L;

        try {
            RequestBody body = RequestBody.create("", JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            /** try 块退出时，会自动调用 response.close() 方法，关闭资源 */
            try (Response response = okHttpClient.newCall(request).execute()) {
                responseBody = response.body().string();
                contentLength = response.body().contentLength();
            }

            byte[] bytesBody = responseBody.getBytes();
            logger.info("bytesBody = {}", new String(bytesBody));
            logger.info("bytesBody.length = {}", bytesBody.length);

            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytesBody));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(String.valueOf(contentLength)));

            filter.filter(fullHttpResponse);
        } catch (Throwable throwable) {
            logger.error("Request server exception!", throwable);
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();
        }
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
}
