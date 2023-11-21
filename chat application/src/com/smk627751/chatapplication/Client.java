package com.smk627751.chatapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
//	private static final String HOST = "192.168.98.203";
	private static final String HOST = "localhost";
	private static final int PORT = 5000;
	
	private static void chatBubble(String messenger, String str)
	{
		int length = str.length();
	    String horizontalLine = new String(new char[length]).replace('\0', '-');
	    
		if(messenger.equals("sent"))
		{
			System.out.println("\t\s\s"+horizontalLine);
			System.out.println("\t(\s"+str+"\s)");
			System.out.println("\t\s\s"+horizontalLine);
		}
		else
		{
			System.out.println("\s\s"+horizontalLine);
			System.out.println("(\s"+str+"\s)");
			System.out.println("\s\s"+horizontalLine);
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name:");
		String name = sc.nextLine();
		System.out.println("to:");
		String to = sc.nextLine();
		try {
			Socket socket = new Socket(HOST,PORT);
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeUTF(name);
			Thread thread = new Thread(){
				public void run()
				{
					try 
					 {
	                    while (true) 
	                    {
	                        String str = dis.readUTF();
	                        chatBubble("received",str);
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
				}
			};
			thread.start();
			while(true){
					String msg = sc.nextLine();
					chatBubble("sent",msg);
					dout.writeUTF(name+","+to+","+msg);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
