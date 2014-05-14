package src.spacegame.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientComm {

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	private ClientMain clMain;
	private ArrayList<String> outBox;
	
	private boolean connected = false;

	/**
	 * Creates a Communication link between the server and the client.
	 *
	 * @param clInfo The ClientInfo Instance for the current client.
	 * @param skt The Socket to Connect to the Server
	 * @throws IOException
	 */
	public ClientComm(ClientMain clMain, Socket socket) throws IOException {

		this.socket = socket;
		
		writer = new PrintWriter(socket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		this.clMain = clMain;
		outBox = new ArrayList<String>();
		connected = true;
	}

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
			return "Connection Lost. Probably.";
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
