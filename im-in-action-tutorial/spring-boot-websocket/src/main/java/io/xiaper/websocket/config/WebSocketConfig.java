package io.xiaper.websocket.config;

import io.xiaper.websocket.handler.BinaryMessageHandler;
import io.xiaper.websocket.handler.ProtobufMessageHandler;
import io.xiaper.websocket.handler.TextMessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final TextMessageHandler textMessageHandler;

	private final BinaryMessageHandler binaryMessageHandler;

	private final ProtobufMessageHandler protobufMessageHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 注意：第二个参数不能为 '/text',否则连接失败
		registry.addHandler(textMessageHandler, "/mytext");
		registry.addHandler(binaryMessageHandler, "/binary");
		registry.addHandler(protobufMessageHandler, "/protobuf");
	}

}