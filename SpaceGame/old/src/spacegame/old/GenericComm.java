package src.spacegame.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

@Deprecated
public class GenericComm {

	private int myNumber = 0; //MOVED: ClientInfo
	private String myTitle = "???"; //MOVED: ClientInfo

	private PrintWriter out = null;
	private BufferedReader in = null;
	private Socket socket = null;
	private static ArrayList<String> outBox = new ArrayList<String>();

	//--------------------------------------------------------
	//Constructors
	public GenericComm(Socket in_socket) { //Establish connection via socket.

		socket = in_socket;
		createStreams(socket);
	}

	public GenericComm() { //Don't know the socket details yet... need to connect.

		socket = askUserForIPaddress();
		createStreams(socket);
	}

	/**
	 * MOVED: ClientInfo
	 * @return myNumber
	 */
	public int getMyNumber() {

		return myNumber;
	}

	/**
	 * REMOVED: Using addMessage Instead
	 * @param message
	 */
	public void sendMessage(String message) {

		out.println(message); //Send a message
		debugMsg("<S<", message);
	}

	public String getMessage() {

		String message = null;
		try {
			message = in.readLine();
		}
		catch(IOException e) { //This means the connection is severed! (in some cases at least)
			debugMsg("catch!", "I/O exception occurred in G_Comm:getMessage()");
			closeNicely();
		}
		if(message != null)
			debugMsg(">R>", message);
		else
			debugMsg(">R>", " null message recieved.");

		return message;
	}

	public ArrayList<String> getAllMessages() {

		ArrayList<String> msgList = new ArrayList<String>();

		//String message = null;
		try {
			while(in.ready())
				msgList.add(getMessage());
		}
		catch(IOException e) { //This means the connection is severed! (in some cases at least)
			debugMsg("catch!", "I/O exception occurred in G_Comm:getMostRecentMessage()");
			closeNicely();
		}
		return msgList;
	}

	public void closeNicely() {

		try { //Close things up nicely when you are done.
			out.close();
			in.close();
			socket.close();
		}
		catch(IOException e) {
			debugMsg("catch!", "I/O exception occurred in G_Comm:closeNicely()");
			System.exit(1);
		}
	}

	public void sendOutboxMessages() {

		//Eventually need to improve this to only send single copies of the same types of updates
		for(int q = 0; q < outBox.size(); q++)
			sendMessage(outBox.get(q));
		outBox.clear();
	}

	public static void addToOutbox(String in) {

		outBox.add(in);
	}//add a message into the outbox to be sent

	private void createStreams(Socket socket) { //Establish connection via socket.

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e) {
			debugMsg("catch!", "I/O exception occurred in G_Comm:createStreams");
			System.exit(1);
		}
	}

	private Socket askUserForIPaddress() {

		Debug.msg("Asking for IP address (popup might be hidden)");
		Socket theSocket = null;
		String serverName = JOptionPane.showInputDialog("Please enter the Server IP address", "0.0.0.0");
		try {
			theSocket = new Socket(serverName, 4444);
		}
		catch(UnknownHostException e) {
			debugMsg("catch!", "Don't know about host: " + serverName + " in G_Comm:askUserForIPaddress()");
			System.exit(1);
		}
		catch(IOException e) {
			debugMsg("catch!", "I/O exception occurred in G_Comm:askUserForIPaddress()");
			System.exit(1);
		}
		Debug.msg("Connection complete.");
		return theSocket;
	}

	public void setDebugValues(String title, int num) {

		myNumber = num;
		myTitle = title;
	}

	public void debugMsg(String type, String text) { //Displays a debug message in the output window.

		System.out.println(type + myTitle + "(" + myNumber + "):  " + text);
	}

}
