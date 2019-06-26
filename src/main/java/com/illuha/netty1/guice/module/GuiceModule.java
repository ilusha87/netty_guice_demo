package com.illuha.netty1.guice.module;

import com.google.inject.*;
import com.illuha.netty1.dao.ApplicationDao;
import com.illuha.netty1.netty.HttpServerHandler;
import com.illuha.netty1.netty.HttpServerInitializer;
import com.illuha.netty1.repository.CustomerRepository;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.cert.CertificateException;

public class GuiceModule extends AbstractModule {

    private static final boolean SSL = System.getProperty("ssl") != null;

    private final String hostname;
    private final int port;
    private final String dbConnectionUrl;

    public GuiceModule(String hostname, int port, String dbConnectionUrl) {
        this.hostname = hostname;
        this.port = port;
        this.dbConnectionUrl = dbConnectionUrl;
    }

    @Override
    protected void configure() {
        bind(CustomerRepository.class).asEagerSingleton();
        bind(HttpServerHandler.class).asEagerSingleton();
        bind(ChannelInboundHandler.class).to(HttpServerInitializer.class).asEagerSingleton();
    }

    @Provides
    public SocketAddress provideSocketAddress() {
        return new InetSocketAddress(hostname, port);
    }

    @Provides
    public EventLoopGroup providesEventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Provides
    public LoggingHandler provideLoggingHandler() {
        return new LoggingHandler(LogLevel.INFO);
    }


    @Provides
    @Singleton
    public ApplicationDao provideApplicationDao() {
        return new ApplicationDao(dbConnectionUrl);
    }

    @Provides
    @Singleton
    public SslContext provideSslContext() {
        return getSslCtx();
    }

    private SslContext getSslCtx() {
        SslContext sslCtx = null;
        if (SSL) {
            SelfSignedCertificate ssc;
            try {
                ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } catch (CertificateException | SSLException e) {
                e.printStackTrace();
            }
        }
        return sslCtx;
    }

}
