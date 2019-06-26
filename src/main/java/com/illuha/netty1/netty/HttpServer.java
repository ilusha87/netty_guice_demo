package com.illuha.netty1.netty;

import com.google.inject.Inject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class HttpServer {

    private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
    private final ChannelInboundHandler channelInitialer;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final SocketAddress socketAddress;
    private final LoggingHandler loggingHandler;

    @Inject
    public HttpServer(
            ChannelInboundHandler channelInitializer,
            EventLoopGroup bossgroup,
            EventLoopGroup workergroup,
            SocketAddress sa,
            LoggingHandler loggingHandler) {
        this.channelInitialer = channelInitializer;
        this.bossGroup = bossgroup;
        this.workerGroup = workergroup;
        this.socketAddress = sa;
        this.loggingHandler = loggingHandler;
    }


    public void run() throws Exception {
        // Configure SSL.
        int port = ((InetSocketAddress) socketAddress).getPort();
        String host = ((InetSocketAddress) socketAddress).getHostString();
        logger.info("Running ServerBootstrap on " + host + ":" + port);

        // Configure the server.
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(loggingHandler)
                    .childHandler(channelInitialer);

            Channel ch = b.bind(socketAddress).sync().channel();

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
