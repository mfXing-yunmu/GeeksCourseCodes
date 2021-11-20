package com.yunmu.geek.filter;

import com.yunmu.geek.inbound.HttpInboundServer;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    private static Logger logger = LoggerFactory.getLogger(HeaderHttpResponseFilter.class);

    @Override
    public void filter(FullHttpResponse response) {
        ByteBuf content = response.content();
        String contentStr = content.toString(CharsetUtil.UTF_8);
        logger.info("[HeaderHttpResponseFilter_filter], content={}", contentStr);

        if (contentStr.startsWith("Hello")) {
            logger.info("正确结果，通过！");
        } else {
            logger.error("错误结果，返回数据错误");
            throw new RuntimeException("错误结果，返回数据错误: content=" + contentStr);
        }

        HttpHeaders headers = response.headers();
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        headers.add("res-proxy-tag", this.getClass().getSimpleName());
    }
}
