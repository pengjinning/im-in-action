package io.xiaper.mars.service;

import com.tencent.mars.sample.chat.proto.Chat;
import com.tencent.mars.sample.proto.Main;

import java.io.InputStream;

/**
 * @author bytedesk.com on 2019-07-31
 */
public interface ResponseService {

    Main.HelloResponse hello(InputStream is);

    Main.ConversationListResponse getconvlist(InputStream is);

    Chat.SendMessageResponse sendmessage(InputStream is);
}
