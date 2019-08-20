package cn.lvy.websocet.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {

	
}

class ClientSocket{
	private Socket socket;
	public void startSocket(){
		//socket = new Socket(host, port);
	}
	
}

class ServletSocket{
	
	private ServerSocket serverSocket;
	
	public void startServerSocket(){
		try {
			serverSocket = new ServerSocket(8888);
			
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			byte[] b = new byte[1024];
			
			while(input.read(b) != -1){
				System.out.println(new String(b));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}