package com.yunmu.geek.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    /** 代理服务 */
    private List<String> proxyServer;

    public HttpInboundInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        /** HttpServerCodec 只能获取 uri 中的参数，所以需要加上 HttpObjectAggregator */
        channelPipeline.addLast(new HttpServerCodec());
        /** HttpObjectAggregator 把 HttpMessage 和 HttpContent 聚合成为一个 FullHttpRquest 或者 FullHttpRsponse */
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        channelPipeline.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
