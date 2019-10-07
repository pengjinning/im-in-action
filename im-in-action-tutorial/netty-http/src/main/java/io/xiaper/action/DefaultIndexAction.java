package io.xiaper.action;

import io.xiaper.websocket.handler.Request;
import io.xiaper.websocket.handler.Response;

/**
 * 默认的主页Action，当访问主页且没有定义主页Action时，调用此Action
 * @author Looly
 *
 */
public class DefaultIndexAction implements Action{

	@Override
	public void doAction(Request request, Response response) {
		response.setContent("Welcome to HttpServer.");
	}

}