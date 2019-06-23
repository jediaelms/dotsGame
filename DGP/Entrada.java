import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This program implements a dots and boxes game.
 *
 * Please see Readme.txt for more information.
 * 
 * Assignment: Entrada
 * Class: CS 340, Fall 2005
 * TA: Nitin Jindal
 * System: jdk-1.5.0.4 and Eclipse 3.1 on Windows XP
 * @author Michael Leonhard (CS account mleonhar)
 * @version 12 Oct 2005
 */

public class Entrada {
	/**
	 * This method is called when the class is loaded from the command line.
	 * It makes an instance of the main game class and starts a game on it.
	 *
	 * @param argv command line parameters (ignored)
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] argv) throws UnknownHostException, IOException {
		// make an instance of the main game class
		DotsAndBoxes instance = new DotsAndBoxes();
		// start a new game
		int port = 10000;
	    String ServerIP = "192.168.0.106";
	    Socket localPlayerSocket = new Socket(ServerIP, port);
	    DataInputStream veio_outro_player = new DataInputStream(localPlayerSocket.getInputStream());
	    DataOutputStream manda_outro_player = new DataOutputStream(localPlayerSocket.getOutputStream());
		
//		instance.newGame(5, 5, localPlayerSocket, veio_outro_player, manda_outro_player);
	}
}
