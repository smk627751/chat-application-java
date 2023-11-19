package com.smk627751.chatapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	private static ServerSocket s;
	private static ArrayList<Socket> sockets = new ArrayList<>();
	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(5000);
			while(true)
			{
				try {
					Socket socket = s.accept();
					sockets.add(socket);
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
				broadCastMessage(socket,str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void broadCastMessage(Socket sender,String str) throws IOException
	{
		for(Socket socket : sockets)
		{
			if(socket != sender)
			{
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(str);
			}
		}
	}
}
