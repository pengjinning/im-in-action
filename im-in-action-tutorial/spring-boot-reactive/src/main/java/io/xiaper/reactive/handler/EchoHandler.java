package io.xiaper.reactive.handler;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EchoHandler implements WebSocketHandler 
{
	@Override
	public Mono<Void> handle(WebSocketSession session) 
	{
		Flux<WebSocketMessage> output = session.receive()
				.map(msg -> "RECEIVED ON SERVER :: " + msg.getPayloadAsText())
				.map(session::textMessage);
		
		return session.send(output);
	}
}
