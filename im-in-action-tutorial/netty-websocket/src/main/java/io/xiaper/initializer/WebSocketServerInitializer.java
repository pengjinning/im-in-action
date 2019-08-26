/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.xiaper.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.xiaper.handler.HttpRequestHandler;
import io.xiaper.handler.BinaryWebSocketFrameHandler;
import io.xiaper.handler.TextWebSocketFrameHandler;
import io.xiaper.util.Constants;

/**
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public WebSocketServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        // HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
        pipeline.addLast(new HttpServerCodec());
        // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(65536));
        // 分块向客户端写数据，防止发送大文件时导致内存溢出
        pipeline.addLast(new ChunkedWriteHandler());
        // 加载.html文件
        pipeline.addLast(new HttpRequestHandler());
        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // 聚合 websocket 的数据帧，因为客户端可能分段向服务器端发送数据
        // https://github.com/netty/netty/issues/1112 https://github.com/netty/netty/pull/1207
        pipeline.addLast(new WebSocketFrameAggregator(10 * 1024 * 1024));
        // 处理close/ping/pong协议
        // 服务器端向外暴露的 web socket 端点，当客户端传递比较大的对象时，maxFrameSize参数的值需要调大
        pipeline.addLast(new WebSocketServerProtocolHandler(Constants.WEBSOCKET_PATH, null, true, 10485760));
        // 自定义处理binary/text协议
        pipeline.addLast(new TextWebSocketFrameHandler());
        pipeline.addLast(new BinaryWebSocketFrameHandler());
    }
}
