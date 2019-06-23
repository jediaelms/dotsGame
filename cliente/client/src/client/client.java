package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author mtpat
 */
public class client {
    
    public client(){
        
    }
    
    public static void main(String[] args) throws IOException {
       int port = 10000;
       String ServerIP = "192.168.0.106";  
       Socket s = null; //Socket para o servidor
       ServerSocket ss = null;
       Socket s2 = null;
       DataInputStream in = null;
       DataOutputStream out = null;
       DataInputStream in2 = null;
       DataOutputStream out2 = null;
       String op = "1";
       while(op != "0") {
    	   op = JOptionPane.showInputDialog("0 - Sair \n 1 - StartServer \n 2 - CloseServer \n 3 - StarClient \n 4 - CloseClient \n 5 - Enviar Mensagem \n");
    	   switch(op) {
    	   case "1":
    		   //Neste caso, o cliente foi desafiado
    		   
    		   int exit = 0;
    		   //hostServer.startServer(ss, s2, port, in2, out2);
    		   
    		   ss = new ServerSocket(port);
    		   System.out.println("Aguardando Cliente Conectar-se...");
    			s2 = ss.accept();
    			in2 = new DataInputStream(s2.getInputStream());
    			out2 = new DataOutputStream(s2.getOutputStream());
    			System.out.println("Cliente Conectado");
    		   while(exit == 0) {
    			   out2.writeUTF(JOptionPane.showInputDialog("Insira Sua mensagem"));
    			   System.out.println(in2.readUTF());
    			   exit = Integer.parseInt(JOptionPane.showInputDialog("Deseja sair?"));
    		   }
    		   break;
    	   case "2":
    		   //hostServer.closeServer(ss, s2, in2, out2);
    		   ss.close();
    			s2.close();
    			in2.close();
    			out2.close();
    		   break;
    	   case "3":
    		   //Neste caso, o cliente � o desafiante
    		   
    		   //hostClient.startClient(socket2, ServerIP, port, in2, out2);
    		   
    		   s2 = new Socket(ServerIP, port);
    		   in2 = new DataInputStream(s2.getInputStream());
    		   out2 = new DataOutputStream(s2.getOutputStream());
    		   int exit2 = 0;
    		   while(exit2 == 0) {
    			   System.out.println(in2.readUTF());
    			   out2.writeUTF(JOptionPane.showInputDialog("Insira Sua mensagem"));
    			   exit2 = Integer.parseInt(JOptionPane.showInputDialog("Deseja sair?"));
    		   }
    		   break;
    	   case "4":
    		   //hostClient.closeClient(s2, in2, out2);
    		   s2.close();
    			in2.close();
    			out2.close();
    		   break;
    	   case "5":		   
    		   break;
    		default:
    			if (op != "0")JOptionPane.showMessageDialog(null, "Op��o Incorreta");
    			break;
    	   }
       }
    }
    
}
