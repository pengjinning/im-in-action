package io.xiaper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * 代码清单 12-4 引导服务器
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class ChatServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "3443" : "3080"));

    // 创建 DefaultChannelGroup，其将保存所有已经连接的 WebSocket Channel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        // 引导服务器
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));
        //
        ChannelFuture future = bootstrap.bind(address);

        System.out.println("Open your web browser and navigate to " +
                (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');

        future.syncUninterruptibly();
        channel = future.channel();
        //
        return future;
    }

    /**
     * 创建 ChatServerInitializer
     * @param group
     * @return
     */
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new ChatServerInitializer(group);
    }

    /**
     * 处理服务器关闭，并释放所有的资源
     */
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        //
//        if (args.length != 1) {
//            System.err.println("Please give port as argument");
//            System.exit(1);
//        }
//         Integer.parseInt(args[0]);

//        int port = 8019;
        final ChatServer endpoint = new ChatServer();
        //
        ChannelFuture future = endpoint.start(new InetSocketAddress(PORT));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();

    }
}