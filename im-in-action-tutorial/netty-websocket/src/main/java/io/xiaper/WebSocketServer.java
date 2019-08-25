package io.xiaper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.xiaper.initializer.WebSocketServerInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpServer starter<br>
 */
@Slf4j
public class WebSocketServer {

    static final boolean SSL = System.getProperty("ssl") != null;

    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "3091"));

    /**
     * 启动服务
     *
     * @throws Exception
     */
    public void start() throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 处理器
                    .childHandler(new WebSocketServerInitializer(sslCtx));

            final Channel ch = b.bind(PORT).sync().channel();

            log.info("Welcome To HttpServer on port [{}]", PORT);

            ch.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 启动服务器
     */
    public static void main(String[] args) throws Exception {
        try {
            new WebSocketServer().start();
        } catch (InterruptedException e) {
            log.error("Http Server start error!", e);
        }
    }
}
