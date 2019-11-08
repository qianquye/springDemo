package cn.lvy.websocet.chat;

import java.io.IOException;

import org.json.JSONObject;

public class ChatDemo {

	public static void main(String[] args) {
		ChatWebSocket ws = new ChatWebSocket();
		JSONObject jo = new JSONObject();
		jo.put("message", "这个比密码不对还想登录!");
		jo.put("To", "admin");// 给用户名为admin的用户推送
		try {
		    ws.onMessage(jo.toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
