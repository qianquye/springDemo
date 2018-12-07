package cn.lvy.websocet.service;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandle extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		
		System.out.println("message:"+message.toString());
		super.handleTextMessage(session, message);
	}


	
}
