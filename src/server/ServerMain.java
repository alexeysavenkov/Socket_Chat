package server;

import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.ref.WeakReference;

public class ServerMain {

	private static int port = 7777;

	public static void main(String[] ar) {
		
		try(ServerSocket ss = new ServerSocket(port)) {
			System.out.println("Waiting for a client...");

			while (true) {
				Socket socket = ss.accept();
				ServerMessageBroadcastService.addClient(socket);
				
				InputStream in = socket.getInputStream();
				ServerMessageReceiverService clientHandler = new ServerMessageReceiverService(in);
				Thread clientHandlerThread = new Thread(clientHandler);
				clientHandlerThread.start();
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
