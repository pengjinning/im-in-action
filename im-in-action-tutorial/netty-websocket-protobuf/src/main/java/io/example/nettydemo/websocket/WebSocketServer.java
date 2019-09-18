package io.example.nettydemo.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class WebSocketServer {

    private Channel serverChannel;

    private EventLoopGroup parentEventLoopGroup;

    private EventLoopGroup childEventLoopGroup;

    @PostConstruct
    public void init() throws Exception {

        log.info("Starting websocket transport server");

        parentEventLoopGroup = new NioEventLoopGroup();

        childEventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(parentEventLoopGroup, childEventLoopGroup)
                //
                .channel(NioServerSocketChannel.class)
                // 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
                // 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                .option(ChannelOption.SO_BACKLOG, 1024)
//                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                // 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，
                // 组装成大的数据包进行发送，虽然该方式有效提高网络的有效
                // 参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 打印log
                .handler(new LoggingHandler(LogLevel.INFO))
                //
                .childHandler(new WebSocketServerChannelInitializer());

        serverChannel = serverBootstrap.bind("0.0.0.0", 8899).sync().channel();

        log.info("socket transport started!");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("Stopping websocket transport!");
        try {
            serverChannel.close().sync();
        } finally {
            childEventLoopGroup.shutdownGracefully();
            parentEventLoopGroup.shutdownGracefully();
        }
        log.info("websocket transport stopped!");
    }
}