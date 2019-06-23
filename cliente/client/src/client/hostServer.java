package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class hostServer {
	
	public static void startServer(ServerSocket s1, Socket s2, int port, DataInputStream in, DataOutputStream out) throws IOException	{
		s1 = new ServerSocket(port);
		s2 = s1.accept();
		System.out.println("Aguardando Cliente Conectar-se...");
		in = new DataInputStream(s2.getInputStream());
		out = new DataOutputStream(s2.getOutputStream());
		System.out.println("Cliente Conectado");
	}
	
	public static void closeServer(ServerSocket s1, Socket s2, DataInputStream in, DataOutputStream out) throws IOException {
		s1.close();
		s2.close();
		in.close();
		out.close();
	}
}
