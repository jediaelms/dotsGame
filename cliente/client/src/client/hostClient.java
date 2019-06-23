package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class hostClient {

	public static void startClient(Socket s, String IP, int port, DataInputStream in, DataOutputStream out) throws UnknownHostException, IOException {
		s = new Socket(IP, port);
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
	}
	
	public static void closeClient(Socket s, DataInputStream in, DataOutputStream out) throws IOException {
		s.close();
		in.close();
		out.close();
	}
}
