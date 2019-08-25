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
package io.xiaper.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles handshakes and messages
 */
@Slf4j
public class WebSocketServerFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            log.info("CloseWebSocketFrame");
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            log.info("PingWebSocketFrame");
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            log.info("TextWebSocketFrame");
            ctx.write(frame.retain());
            return;
        }

        if (frame instanceof BinaryWebSocketFrame) {
            log.info("BinaryWebSocketFrame");
            // Echo the frame
            ctx.write(frame.retain());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}