package com.yunmu.geek.filter;

import com.yunmu.geek.inbound.HttpInboundServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderHttpRequestFilter implements HttpRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.uri();
        logger.info(" filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: {}", uri);

        if (uri.startsWith("/geek")) {
            logger.info("合法请求，通过！");
        } else {
            logger.error("非法请求，不支持的 URI: {}", uri);
            throw new RuntimeException("非法请求，不支持的 URI: " + uri);
        }

        HttpHeaders headers = fullRequest.headers();
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        headers.add("req-proxy-tag", this.getClass().getSimpleName());
    }
}
