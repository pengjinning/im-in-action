package io.xiaper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.StaticLog;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HttpServer starter<br>
 */
public class WebSocketServer {

    private static final Log log = StaticLog.get();

    static final boolean SSL = System.getProperty("ssl") != null;

//    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "4080"));

    /**
     * 启动服务
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        //
        int port = 3091;
        //
        long start = System.currentTimeMillis();

        // Configure the server.
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new HttpServerCodec())
                                    //把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
                                    .addLast(new HttpObjectAggregator(65536))
                                    //大文件支持
                                    .addLast(new ChunkedWriteHandler())
                                    //
                                    .addLast(new HttpRequestHandler())
                                    // websocket
                                    .addLast(new WebSocketServerHandler());
                        }
                    });

            final Channel ch = b.bind(port).sync().channel();

            log.info("***** Welcome To HttpServer on port [{}], startting spend {}ms *****", port, DateUtil.spendMs(start));

            ch.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 启动服务器
     */
    public static void main(String[] args) {
        try {
            new WebSocketServer().start();
        } catch (InterruptedException e) {
            log.error("Http Server start error!", e);
        }
    }
}
