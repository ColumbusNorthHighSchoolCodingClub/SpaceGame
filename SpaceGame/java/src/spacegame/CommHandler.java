package src.spacegame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles the communication Between a Client and a Server (and vice/versa).
 *
 * @author David Baker
 */
public class CommHandler {
	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	private ArrayList<String> outBox;

	private boolean connected = false;
	
	/**
	 * Creates a Communication link between the server and the client.
	 *
	 * @param skt The Socket to Connect to the Server
	 */
	public CommHandler(Socket socket) {
	
		this.connect(socket);

		outBox = new ArrayList<String>();
		connected = true;
	}
	
	/**
	 * Creates a CommHandler ready to connect to a server.
	 */
	public CommHandler() {
	
		outBox = new ArrayList<String>();
	}

	/**
	 * Connects to the given Server Socket
	 * @param socket Socket of the Server
	 */
	public void connect(Socket socket) {
	
		this.socket = socket;

		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e) {
		}
		
	}

	/**
	 * Whether the object is connected to a server.
	 * @return Is Connected to a Server
	 */
	public boolean isConnected() {
	
		return connected;
	}

	/**
	 * Sends out all of the Messages in the outBox.
	 */
	public void sendMessages() {
	
		for(String msg : outBox) {
			writer.println(msg);
		}
		
		outBox.clear();
	}
	
	/**
	 * Adds a Message to the outBox <code>ArrayList</code> to be sent out.
	 * @param msg The Message to Be Sent
	 */
	public void addMessage(String msg) {
	
		outBox.add(msg);
	}
	
	public void sendMessage(String msg) {
	
		writer.println(msg);
	}
	
	/**
	 * Asks for the latest message recieved
	 * @return Message
	 */
	public String getMessage() {
	
		String msg = null;
		
		try {
			msg = reader.readLine();
		}
		catch(IOException e) { //This means the connection is severed! (in some cases at least)
			return "STOP";
		}
		
		return msg;
	}
	
	public ArrayList<String> getAllMessages() {
	
		ArrayList<String> msgList = new ArrayList<String>();
		
		try {
			while(reader.ready())
				msgList.add(getMessage());
		}
		catch(IOException e) { //This means the connection is severed! (in some cases at least)
			System.out.println("IOException in class ClientComm");
			this.close();
		}
		
		return msgList;
	}
	
	public void clearInbox() {
	
	}

	/**
	 * Safely closes all connections.
	 */
	public void close() {
	
		try {
			writer.close();
			reader.close();
			socket.close();
		}
		catch(IOException e) {
			System.out.println("I/O exception occurred in Creating ClientComm");
			System.exit(1);
		}
	}
	
}
