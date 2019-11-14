/*
* Tencent is pleased to support the open source community by making Mars available.
* Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
*
* Licensed under the MIT License (the "License"); you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://opensource.org/licenses/MIT
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is
* distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
* either express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*/

package io.xiaper.mars.handler;

import com.tencent.mars.sample.chat.proto.Chat;
import com.tencent.mars.sample.proto.Main;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import io.xiaper.mars.service.ResponseService;
import io.xiaper.mars.service.ResponseServiceImpl;
import io.xiaper.mars.topic.TopicChats;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by zhaoyuan on 16/2/2.
 */
@Slf4j
@ChannelHandler.Sharable
public class MarsTransportHandler extends ChannelInboundHandlerAdapter {

    private ResponseService responseService;

    public MarsTransportHandler() {
        super();
        responseService = new ResponseServiceImpl();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("client connected! " + ctx.toString());
        TopicChats.getInstance().joinTopic(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {

            // decode request
            final MarsMsgHeader msgXp = new MarsMsgHeader();

            final InputStream socketInput = new ByteBufInputStream((ByteBuf) msg);

            boolean ret = msgXp.decode(socketInput);

            IOUtils.closeQuietly(socketInput);

            if(!ret) return;

            log.info("client req, cmdId={}, seq={}", msgXp.cmdId, msgXp.seq);

            switch (msgXp.cmdId) {
                case Main.CmdID.CMD_ID_HELLO_VALUE:
                    log.info("CMD_ID_HELLO_VALUE");
                case Main.CmdID.CMD_ID_HELLO2_VALUE:
                    log.info("CMD_ID_HELLO2_VALUE");

                    InputStream requestDataStream = new ByteArrayInputStream(msgXp.body);
                    Main.HelloResponse helloResponse = responseService.hello(requestDataStream);

                    if (helloResponse != null) {
                        msgXp.body = helloResponse.toByteArray();
                        IOUtils.closeQuietly(requestDataStream);
                        byte[] respBuf = msgXp.encode();
                        log.info("client resp, cmdId={}, seq={}, resp.len={}", msgXp.cmdId, msgXp.seq, msgXp.body == null ? 0 : msgXp.body.length);
                        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));
                    }

                    break;
                case Main.CmdID.CMD_ID_SEND_MESSAGE_VALUE:
                    log.info("CMD_ID_SEND_MESSAGE_VALUE");

                    requestDataStream = new ByteArrayInputStream(msgXp.body);
                    Chat.SendMessageResponse sendMessageResponse = responseService.sendmessage(requestDataStream);

                    if (sendMessageResponse != null) {
                        msgXp.body = sendMessageResponse.toByteArray();
                        Chat.SendMessageResponse response = Chat.SendMessageResponse.parseFrom(msgXp.body);
                        if (response != null && response.getErrCode() == Chat.SendMessageResponse.Error.ERR_OK_VALUE) {
                            TopicChats.getInstance().pushMessage(response.getTopic(), response.getText(), response.getFrom(), ctx);
                        }
                        IOUtils.closeQuietly(requestDataStream);
                        byte[] respBuf = msgXp.encode();
                        log.info("client resp, cmdId={}, seq={}, resp.len={}", msgXp.cmdId, msgXp.seq, msgXp.body == null ? 0 : msgXp.body.length);
                        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));
                    }

                    break;
                case Main.CmdID.CMD_ID_CONVERSATION_LIST_VALUE:
                    log.info("CMD_ID_CONVERSATION_LIST_VALUE");

                    requestDataStream = new ByteArrayInputStream(msgXp.body);
                    Main.ConversationListResponse conversationListResponse = responseService.getconvlist(requestDataStream);

                    if (conversationListResponse != null) {
                        msgXp.body = conversationListResponse.toByteArray();
                        IOUtils.closeQuietly(requestDataStream);
                        byte[] respBuf = msgXp.encode();
                        log.info("conversation client resp, cmdId={}, seq={}, resp.len={}", msgXp.cmdId, msgXp.seq, msgXp.body == null ? 0 : msgXp.body.length);
                        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));
                    }

                    break;
                case MarsMsgHeader.CMDID_NOOPING:
                    log.info("CMDID_NOOPING");

                    byte[] respBuf = msgXp.encode();
                    log.info("client resp, cmdId={}, seq={}, resp.len={}", msgXp.cmdId, msgXp.seq, msgXp.body == null ? 0 : msgXp.body.length);
                    ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(respBuf));
                    break;
                default:
                    log.info("default {}", msgXp.cmdId);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * user event
     *
     * @param context context
     * @param event event
     * @throws Exception exp
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) throws Exception {
        log.info("userEventTriggered");
        //
        if (event instanceof IdleStateEvent) {
            log.info("IdleStateEvent");
            //
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            //
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                //
                log.info("IdleState.ALL_IDLE");

                TopicChats.getInstance().leftTopic(context);
            }
        } else {
            super.userEventTriggered(context, event);
        }
    }

}

