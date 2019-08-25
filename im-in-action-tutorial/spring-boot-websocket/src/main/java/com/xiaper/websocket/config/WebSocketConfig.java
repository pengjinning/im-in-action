package com.xiaper.websocket.config;

import com.xiaper.websocket.handler.TextMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-server
 *
 * @author xiaper.com
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	TextMessageHandler textMessageHandler;

	@Autowired
	BinaryWebSocketHandler binaryWebSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(textMessageHandler, "/user");
	}

}