package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;

import common.Message;

public class ClientMessageReceiverService implements Runnable {

	private ObjectInputStream objIn;
	
	public ClientMessageReceiverService(InputStream in) {
		try {
			objIn = new ObjectInputStream(in);
		} catch(IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				try {
					Message message = (Message) objIn.readObject();
					System.out.println(message);
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
