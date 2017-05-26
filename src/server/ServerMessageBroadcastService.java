package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import common.Message;

public class ServerMessageBroadcastService {
	private ServerMessageBroadcastService() {}
	
	private static WeakHashMap<Socket, ObjectOutputStream> clientOutputStreams = new WeakHashMap<>();
	
	public static void broadcastMessage(Message message) {
		clientOutputStreams.values().forEach(objOut -> {
			try {
				objOut.writeObject(message);
				objOut.flush();
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void addClient(Socket clientSocket) {
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			clientOutputStreams.put(clientSocket, objOut);
		} catch(IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}
}
