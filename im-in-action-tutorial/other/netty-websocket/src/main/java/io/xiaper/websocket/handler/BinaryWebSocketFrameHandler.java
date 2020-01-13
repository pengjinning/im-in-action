/*
 * Copyright 2014 The Netty Project
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
package io.xiaper.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Handles handshakes and messages
 */
@Slf4j
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    @Override
    public void channelRead0(ChannelHandlerContext context, BinaryWebSocketFrame frame) throws IOException {
        //
        log.info("接收到二进制消息,消息长度:[{}]", frame.content().capacity());
//        ByteBuf byteBuf = Unpooled.directBuffer(frame.content().capacity());
//        byteBuf.writeBytes(frame.content());
//        String contentString = new String(byteBuf.array(), CharsetUtil.UTF_8);
//
        //转成byte
//        byte [] bytes = new byte[frame.content().capacity()];
//        byteBuf.readBytes(bytes);

        //
//        log.info("decode binary content {}", String.valueOf(bytes));

        // Echo the frame
        context.write(frame.retain());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 添加
        log.info(" 客户端加入 [ {} ]", ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 移除
        log.info(" 离线 [ {} ] ", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("binary userEventTriggered");
        //
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("binary web socket 握手成功。");
            //
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String requestUri = handshakeComplete.requestUri();
            log.info("binary requestUri:[{}]", requestUri);
            //
            String subproTocol = handshakeComplete.selectedSubprotocol();
            log.info("binary subproTocol:[{}]", subproTocol);
            //
            handshakeComplete.requestHeaders().forEach(entry -> log.info("binary header key:[{}] value:[{}]", entry.getKey(), entry.getValue()));
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
