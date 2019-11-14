package io.xiaper.mars.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.xiaper.mars.handler.MarsTransportHandler;

/**
 * @author bytedesk.com on 2019-07-30
 */
public class MarsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new MarsTransportHandler());
    }
}
