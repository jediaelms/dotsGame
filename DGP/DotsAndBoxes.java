/**
 * This program implements a dots and boxes game.
 * 
 * Assignment: Entrada
 * Class: CS 340, Fall 2005
 * TA: Nitin Jindal
 * System: jdk-1.5.0.4 and Eclipse 3.1 on Windows XP
 * @author Michael Leonhard (CS account mleonhar)
 * @version 12 Oct 2005
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DotsAndBoxes extends javax.swing.JFrame implements ActionListener {
	// version number of this class, used for serialization
	private static final long serialVersionUID = 1L;
	// dialog box for starting a Novo (normally hidden)
	private NewGameDialog newGameDialog;
	// the field of dots and lines
	private Field field;
	// counter widget that displays user's score
	private CounterLabel userCounterLabel;
	// counter widget that displays computer's score
	private CounterLabel computerCounterLabel;

	// gameplay instructions
	final String HOWTOPLAYTEXT = "Como jogar\r\n" + "\r\n"
			+ "The game consists of a field of dots.  Take turns with the\r\n"
			+ "computer adding lines between the dots.  Complete a box to\r\n"
			+ "get another turn.  Your boxes will show O.  The computer's\r\n"
			+ "boxes get X.  Complete the most boxes to win!\r\n";

	// Sobre box text
	final String SobreTEXT = "Dots and Boxes is a Java program written by "
			+ "Michael Leonhard.\r\n"
			+ "\r\n"
			+ "Michael is an undergraduate studying Computer Science at the\r\n"
			+ "University of Illinois at Chicago.  This program is a project\r\n"
			+ "for CS340 Software Design.\r\n"
			+ "\r\n"
			+ "Michael has a website at http://tamale.net/\r\n"
			+ "------------------------------------------\r\n"
			+ "Michael Leonhard (mleonhar)\r\n"
			+ "Assignment 3 (Entrada)\r\n"
			+ "CS 340, Fall 2005\r\n"
			+ "Instructor: Pat Troy\r\n"
			+ "TA: Nitin Jindal\r\n"
			+ "Created 9 Oct 2005 with\r\n"
			+ "jdk-1.5.0.4 and Eclipse 3.1 on Windows XP";
	Socket localPlayerSocket;
	DataInputStream veio_outro_player;
	DataOutputStream manda_outro_player;
	/**
	 * Set up a Novo and show the frame
	 * 
	 * @param linhas number of columns of dots
	 * @param colunas number of linhas of dots
	 */
	public void newGame(int linhas, int colunas, Socket localPlayerSocket, DataInputStream veio_outro_player, DataOutputStream manda_outro_player) {
		// reset the counters
		this.userCounterLabel.reset();
		this.computerCounterLabel.reset();
		this.localPlayerSocket = localPlayerSocket; 
		this.veio_outro_player = veio_outro_player;
		this.manda_outro_player = manda_outro_player;
		// if an old field exists, remove it
		if (this.field != null) this.getContentPane().remove(this.field);

		// create the new field
		this.field = new Field(linhas, colunas, this.userCounterLabel,
				this.computerCounterLabel, localPlayerSocket, veio_outro_player, manda_outro_player);
		this.getContentPane().add(this.field);
		this.getContentPane().validate();

		// show the window (if this is the first game)
		this.setVisible(true);
	}

	/**
	 * This method is called when the class is loaded from the command line. It
	 * makes an instance of the main game class and starts a game on it.
	 * 
	 * @param argv command line parameters (ignored)
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] argv) throws UnknownHostException, IOException {
		// make an instance of the main game class
		DotsAndBoxes instance = new DotsAndBoxes();
		// start a Novo
//		int port = 10000;
//	    String ServerIP = "192.168.0.106";
//	    Socket localPlayerSocket = new Socket(ServerIP, port);
//	    DataInputStream veio_outro_player = new DataInputStream(localPlayerSocket.getInputStream());
//	    DataOutputStream manda_outro_player = new DataOutputStream(localPlayerSocket.getOutputStream());
	    
//		instance.newGame(5, 5, localPlayerSocket, veio_outro_player, manda_outro_player);
	    instance.newGame(5, 5, null, null, null);
	}

	/**
	 * Make an invisible game window, with menu and status bar
	 */
	public DotsAndBoxes() {
		// allow the super class to initialize (JFrame)
		super();
		// set up the window, menu, and status bar
		initGUI();

		// Anonymous object, gets called when user dispatches Novo dialog
		NewGameParametersCallback cback = new NewGameParametersCallback() {
			public void newGameParameters(int linhas, int colunas, Socket localPlayerSocket, DataInputStream veio_outro_player, DataOutputStream manda_outro_player) {
				newGame(linhas, colunas, localPlayerSocket, veio_outro_player, manda_outro_player);
			}
		};
		// create the hidden dialog box
		newGameDialog = new NewGameDialog(this, cback);
	}

	/**
	 * Set characteristics of main window, make menu, make status bar
	 */
	private void initGUI() {
		// closing the main window should dispose of it, allowing VM to exit
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// set window title
		this.setTitle("Jogo dos Pontinhos");
		// initial size
		this.setSize(400, 300);
		// let the window manager choose location (requires Java 1.5)
		//this.setLocationByPlatform(true);

		// main window layout
		BorderLayout thisLayout = new BorderLayout();
		this.getContentPane().setLayout(thisLayout);

		{// menu
			JMenuBar menuBar = new JMenuBar();
			this.setJMenuBar(menuBar);

			// Game menu
			JMenu gameMenu = new JMenu("Jogo");
			gameMenu.setMnemonic(java.awt.event.KeyEvent.VK_G);
			menuBar.add(gameMenu);

			// Novo menu item
			JMenuItem newGameMenuItem = new JMenuItem("Novo");
			newGameMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_N);
			newGameMenuItem.addActionListener(this);
			gameMenu.add(newGameMenuItem);

			// Exit menu item
			JMenuItem exitMenuItem = new JMenuItem("Desistir");
			exitMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_X);
			exitMenuItem.addActionListener(this);
			gameMenu.add(exitMenuItem);

			// Help menu
			JMenu helpMenu = new JMenu("Help");
			helpMenu.setMnemonic(java.awt.event.KeyEvent.VK_H);
			menuBar.add(helpMenu);

			// Como jogar menu item
			JMenuItem howToPlayMenuItem = new JMenuItem("Como jogar");
			howToPlayMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_P);
			howToPlayMenuItem.addActionListener(this);
			helpMenu.add(howToPlayMenuItem);

			// Sobre menu item
			JMenuItem SobreMenuItem = new JMenuItem("Sobre");
			SobreMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_A);
			SobreMenuItem.addActionListener(this);
			helpMenu.add(SobreMenuItem);
			
			JMenuItem mntmNewMenuItem = new JMenuItem("Sair");
			mntmNewMenuItem.addActionListener(this);
			menuBar.add(mntmNewMenuItem);
		}

		{// status bar
			JPanel statusBarPanel = new JPanel();
			GridBagLayout statsuBarLayout = new GridBagLayout();
			statsuBarLayout.columnWeights = new double[] { 0.05, 0.45, 0.45,
					0.05 };
			statsuBarLayout.columnWidths = new int[] { 7, 7, 7, 7 };
			statusBarPanel.setLayout(statsuBarLayout);
			this.getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
			statusBarPanel.setVisible(true);
			statusBarPanel.setFocusable(false);

			// user count
			this.userCounterLabel = new CounterLabel("Your Score: ");
			GridBagConstraints userLabelConstraints = new GridBagConstraints();
			userLabelConstraints.gridx = 1;
			statusBarPanel.add(userCounterLabel, userLabelConstraints);

			// computer count
			this.computerCounterLabel = new CounterLabel("Computer's Score: ");
			GridBagConstraints computerLabelConstraints = new GridBagConstraints();
			computerLabelConstraints.gridx = 2;
			statusBarPanel.add(computerCounterLabel, computerLabelConstraints);
		}
	}

	/**
	 * Handles menu events. Implements ActionListener.actionPerformed().
	 * 
	 * @param e object with information Sobre the event
	 */
	public void actionPerformed(ActionEvent e) {
		// System.out.println("actionPerformed: " + e.getActionCommand());

		// Novo menu item was selected
		if (e.getActionCommand().equals("Novo")) {
			// show modal Novo dialog box
			newGameDialog.showDialog();
		}

		// Como jogar menu item was selected
		else if (e.getActionCommand().equals("Como jogar")) {
			// show the instructional modal dialog box
			JOptionPane.showMessageDialog(this, HOWTOPLAYTEXT, "Como jogar",
					JOptionPane.PLAIN_MESSAGE);
		}

		// Sobre menu item was selected
		else if (e.getActionCommand().equals("Sobre")) {
			// show the modal Sobre box
			JOptionPane.showMessageDialog(this, SobreTEXT,
					"Sobre Dots and Boxes", JOptionPane.PLAIN_MESSAGE);
		}

		// Exit menu item was selected
		else if (e.getActionCommand().equals("Desistir")) {
			// dispose of the main window. Java VM will exit if there are no
			// other threads or windows.
			this.dispose();
		}
		// Exit menu item was selected
				else if (e.getActionCommand().equals("Sair")) {
					// dispose of the main window. Java VM will exit if there are no
					// other threads or windows.
					this.dispose();
				}
	}
}