package com.yunmu.geek.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundServer {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    /** 代理端口 */
    private int port;

    /** 代理服务 */
    private List<String> proxyServers;

    public HttpInboundServer(int port, List<String> proxyServers) {
        this.port=port;
        this.proxyServers = proxyServers;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getProxyServers() {
        return proxyServers;
    }

    public void setProxyServers(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    public void run() throws Exception {
        /** 负责接受新的连接 */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        /** 负责处理读写 */
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            /** 初始化 ServerBootstrap 实例 */
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            /** 配置 ServerSocketChannel 的选项，以及子通道也就是 SocketChannel 的选项 */
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            /**
             * 1、通过 ServerBootstrap 的 group 方法，设置初始化的 bossGroup 和 workerGroup;
             * 2、指定通道 channel 的类型，由于是服务端，故而是 NioServerSocketChannel;
             * 3、设置 ServerSocketChannel 的处理器;
             * 4、设置子通道也就是 SocketChannel 的处理器，其内部是具体的业务逻辑实现。
             * */
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new HttpInboundInitializer(this.proxyServers));

            /** 绑定并监听设定的代理端口 */
            Channel ch = serverBootstrap.bind(port).sync().channel();
            logger.info("开启 netty http 服务器，监听地址和端口为 http://127.0.0.1:{}/", port);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
