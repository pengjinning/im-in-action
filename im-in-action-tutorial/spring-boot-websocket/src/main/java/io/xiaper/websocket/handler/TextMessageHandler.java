package io.xiaper.websocket.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {

		String payload = message.getPayload();
		log.info("text {}", payload);
		session.sendMessage(new TextMessage(payload));

		/*for(WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
		}*/
//		JSONObject jsonObject = JSONObject.parseObject(payload);
//		session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
	}

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        
        InetSocketAddress clientAddress = session.getRemoteAddress();
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();

        //the messages will be broadcasted to all users.
        log.info("Accepted connection from: {}:{}", clientAddress.getHostString(), clientAddress.getPort());
        log.info("Client hostname: {}", clientAddress.getHostName());
        log.info("Client ip: {}", clientAddress.getAddress().getHostAddress());
        log.info("Client port: {}", clientAddress.getPort());

        log.info("Session accepted protocols: {}", session.getAcceptedProtocol());
        log.info("Session binary message size limit: {}", session.getBinaryMessageSizeLimit());
        log.info("Session id: {}", session.getId());
        log.info("Session text message size limit: {}", session.getTextMessageSizeLimit());
        log.info("Session uri: {}", session.getUri().toString());

        log.info("Handshake header: Accept {}", handshakeHeaders.toString());
        log.info("Handshake header: User-Agent {}", handshakeHeaders.get("User-Agent").toString());
        log.info("Handshake header: Sec-WebSocket-Extensions {}", handshakeHeaders.get("Sec-WebSocket-Extensions").toString());
        log.info("Handshake header: Sec-WebSocket-Key {}", handshakeHeaders.get("Sec-WebSocket-Key").toString());
        log.info("Handshake header: Sec-WebSocket-Version {}", handshakeHeaders.get("Sec-WebSocket-Version").toString());

        //the messages will be broadcasted to all users.
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        log.info("Connection closed by {}:{}", session.getRemoteAddress().getHostString(), session.getRemoteAddress().getPort());
        super.afterConnectionClosed(session, status);

    }
}