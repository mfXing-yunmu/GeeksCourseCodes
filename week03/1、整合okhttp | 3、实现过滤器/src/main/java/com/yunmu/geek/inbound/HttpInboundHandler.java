package com.yunmu.geek.inbound;

import com.yunmu.geek.filter.HeaderHttpRequestFilter;
import com.yunmu.geek.filter.HttpRequestFilter;
import com.yunmu.geek.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    private final List<String> proxyServer;
    private OkhttpOutboundHandler handler;
    private HttpRequestFilter filter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new OkhttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("channelReadComplete");
        ctx.flush();
    }

    /**
     * 每当客户端有新的请求数据过来时，就会调用 channelRead 方法
     *
     * @param ctx ChannelHandler 上下文，可以看做是一个管理它所关联的 ChannelHandler
     * @param msg 请求数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            logger.info("接收到的请求 URI 为：{}", uri);

            handler.handle(fullRequest, ctx, filter);
        } catch(Throwable throwable) {
            logger.error("处理请求异常", throwable);
            exceptionCaught(ctx, throwable);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("exceptionCaught");
        ctx.close();
    }
}
