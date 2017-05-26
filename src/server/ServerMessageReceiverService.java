package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidParameterException;

import common.Message;

public class ServerMessageReceiverService implements Runnable {
	private ObjectInputStream objIn;
	
	public ServerMessageReceiverService(InputStream in) {
		try {
			this.objIn = new ObjectInputStream(in);
		} catch(IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Got new client");
			System.out.println();		

			while (true) {
				Message message = (Message) objIn.readObject();
				System.out.println(message);
				ServerMessageBroadcastService.broadcastMessage(message);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("IOException in ServerMessageReceiverService. Probably client closed the connection");
		}
	}
}
