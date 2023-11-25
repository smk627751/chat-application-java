package com.smk627751.chatapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private static ServerSocket s;
	private static HashMap<String,Socket> socketsMap = new HashMap<>();
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(5000);
			while(true)
			{
				try {
					Socket socket = s.accept();
					DataInputStream getName = new DataInputStream(socket.getInputStream());
					String name = getName.readUTF();

					socketsMap.put(name, socket);

					Thread thread = new Thread() {
						public void run()
						{
							handleClient(socket);
						}
					};
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void handleClient(Socket socket)
	{
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String str;
			while(true)
			{
				str = dis.readUTF();
				System.out.println(str);
				sendMessage(socket,str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void sendMessage(Socket sender,String str) throws IOException
	{
		String[] messageData = str.split(",");
		for(Map.Entry<String, Socket> socket : socketsMap.entrySet())
		{
			if(socket.getValue() != sender && socket.getKey().equals(messageData[1]) || messageData[1].equals("all"))
			{
				DataOutputStream dout = new DataOutputStream(socket.getValue().getOutputStream());
				dout.writeUTF(messageData[0]+":"+messageData[2]);
			}
		}
	}
}
