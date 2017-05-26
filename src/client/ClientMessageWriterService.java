package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import common.Message;

public class ClientMessageWriterService implements Runnable {

	private ObjectOutputStream objOut;
	private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	
	public ClientMessageWriterService(OutputStream out) {
		try {
			this.objOut = new ObjectOutputStream(out);
		} catch(IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("What's your name?");
	        String authorName = keyboard.readLine();
	        
	        String line = null;
	        System.out.println("You can type your messages");
	        System.out.println();

	        while (true) {
	            line = keyboard.readLine();
	            objOut.writeObject(new Message(authorName, line));
	            objOut.flush();
	        }
		} catch(IOException e) {
			System.out.println("IO Error in MessageWriter");
			e.printStackTrace();
		}
	}
}
