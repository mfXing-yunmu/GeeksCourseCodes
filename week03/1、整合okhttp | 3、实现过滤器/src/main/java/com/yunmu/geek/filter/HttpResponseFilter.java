package com.yunmu.geek.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {
    void filter(FullHttpResponse response);
}
