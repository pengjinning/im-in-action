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
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.xiaper.handler.HttpRequestHandler;
import io.xiaper.handler.WebSocketServerFrameHandler;
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
        // 编解码
        pipeline.addLast(new HttpServerCodec());
        // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(65536));
        // 大文件支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 加载.html文件
        pipeline.addLast(new HttpRequestHandler());
        // websocket
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(Constants.WEBSOCKET_PATH, null, true));
        // 自行处理websocketframe
        pipeline.addLast(new WebSocketServerFrameHandler());
    }
}
