package io.xiaper.mars.service;

import com.tencent.mars.sample.chat.proto.Chat;
import com.tencent.mars.sample.proto.Main;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author bytedesk.com on 2019-07-31
 */
@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {

    private String[][] conDetails = new String[][]{
            new String[] {"Mars", "0", "STN Discuss"},
            new String[] {"Mars", "1", "Xlog Discuss"},
            new String[] {"Mars", "2", "SDT Discuss"}
    };

    @Override
    public Main.HelloResponse hello(InputStream is) {

        try {

            final Main.HelloRequest request = Main.HelloRequest.parseFrom(is);

            log.info("hello request from user={}, text={}", request.getUser(), request.getText());

            int retCode = 0;

            String errMsg = "congratulations from long_link " + request.getUser();

            final Main.HelloResponse response = Main.HelloResponse.newBuilder().setRetcode(retCode).setErrmsg(errMsg).build();

            return response;

        } catch (Exception e) {
            log.info("{}", e);
        }

        return null;
    }

    @Override
    public Main.ConversationListResponse getconvlist(InputStream is) {

        try {

            final Main.ConversationListRequest request = Main.ConversationListRequest.parseFrom(is);

            log.info("getconvlist request from access_token={}, type={}", request.getAccessToken(), request.getType());

            List<Main.Conversation> conversationList = null;

            if (request.getType() == Main.ConversationListRequest.FilterType.DEFAULT_VALUE) {

                conversationList = new LinkedList<Main.Conversation>();

                for (String[] conv : conDetails) {

                    conversationList.add(Main.Conversation.newBuilder()
                            .setName(conv[0])
                            .setTopic(conv[1])
                            .setNotice(conv[2])
                            .build());
                }
            }

            final Main.ConversationListResponse response = Main.ConversationListResponse.newBuilder()
                    .addAllList(conversationList).build();

            return response;

        } catch (Exception e) {
            log.info("request invalid", e);
        }

        return null;
    }

    @Override
    public Chat.SendMessageResponse sendmessage(InputStream is) {

        try {

            final Chat.SendMessageRequest request = Chat.SendMessageRequest.parseFrom(is);

            log.info("sendmessage request from user={}, text={}, topic={}", request.getFrom(), request.getText(), request.getTopic());

            int retCode = Chat.SendMessageResponse.Error.ERR_OK_VALUE;

            String errMsg = "congratulations, " + request.getFrom();

            final Chat.SendMessageResponse response = Chat.SendMessageResponse.newBuilder()
                    .setErrCode(retCode)
                    .setErrMsg(errMsg)
                    .setFrom(request.getFrom())
                    .setText(request.getText())
                    .setTopic(request.getTopic())
                    .build();

            return response;

        } catch (Exception e) {
            log.info(e.toString());
        }

        return null;
    }
}
