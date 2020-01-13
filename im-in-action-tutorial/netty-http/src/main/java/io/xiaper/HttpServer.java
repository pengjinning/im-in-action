package io.xiaper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.StaticLog;
import io.xiaper.action.ExampleAction;
import io.xiaper.mini.config.HttpConfig;
import io.xiaper.websocket.handler.ActionHandler;
import io.xiaper.websocket.handler.HttpChunkContentCompressor;
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
 * 用于启动服务器的主对象<br>
 * 使用HttpServer.start()启动服务器<br>
 * 服务的Action类和端口等设置在ServerSetting中设置
 *
 * @author Looly
 */
public class HttpServer {

    private static final Log log = StaticLog.get();

    /**
     * 启动服务
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
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
                                    //压缩Http消息
                                    .addLast(new HttpChunkContentCompressor())
                                    //大文件支持
                                    .addLast(new ChunkedWriteHandler())
                                    // 自定义
                                    .addLast(new ActionHandler());
                        }
                    });

            final Channel ch = b.bind(HttpConfig.getPort()).sync().channel();

            log.info("***** Welcome To HttpServer on port [{}], startting spend {}ms *****", HttpConfig.getPort(), DateUtil.spendMs(start));

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
            HttpConfig.setAction("/example", ExampleAction.class);
            new HttpServer().start();
        } catch (InterruptedException e) {
            log.error("Http Server start error!", e);
        }
    }
}
