package io.xiaper.websocket.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author xiaper.com
 */
@Slf4j
@Component
public class TextMessageHandler extends TextWebSocketHandler {

    List sessions = new CopyOnWriteArrayList<>();

    @Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		String payload = message.getPayload();
		log.info("text {}", payload);
		session.sendMessage(new TextMessage(payload));

//		JSONObject jsonObject = JSONObject.parseObject(payload);
//		session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
	}

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {

    }
}