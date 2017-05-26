package client;

import java.net.*;

import java.io.*;

public class ClientMain {
	
	private static int serverPort = 7777;
	private static String localhost = "127.0.0.1";
	
    public static void main(String[] ar) {
        
        

        InetAddress ipAddress = null;
        try {
        	ipAddress = InetAddress.getByName(localhost);
        } catch(UnknownHostException e) {
        	e.printStackTrace();
        }

        if(ipAddress == null) {
        	return;
        }
        
        try(Socket socket = new Socket(ipAddress, serverPort)) {
            System.out.println("Chat client started");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            ClientMessageReceiverService messageReceiverService = new ClientMessageReceiverService(in);
            ClientMessageWriterService messageWriterService = new ClientMessageWriterService(out);
            
            Thread messageReceiverThread = new Thread(messageReceiverService);
            Thread messageWriterThread = new Thread(messageWriterService);
            
            messageReceiverThread.start();
            messageWriterThread.start();
            
            messageReceiverThread.join();
            messageWriterThread.join();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}