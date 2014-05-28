package src.spacegame.server;

import java.net.Socket;
import java.util.ArrayList;
import src.spacegame.old.GenericComm;

public class SS_Thread extends Thread {
	//--static class variables--
	private int numConnections = 0; //Counts the total number of connections
	private ArrayList<String> outBox = new ArrayList<String>();
	//--private instance variables--
	private int myNumber = 0; //Which connection number am I?
	private GenericComm comm = null; //Generic communication object
	SS_GameEngine engine;

	public SS_Thread(Socket socket, SS_GameEngine engine) //Constructor
	{

		super("SS_Thread");
		//***There is a new connection, and I'm it!
		numConnections++;
		myNumber = numConnections;
		this.engine = engine;
		//***Initialize the GenericComm object.
		comm = new GenericComm(socket);
		comm.setDebugValues("SS_Thread", myNumber);
	}

	//This method is automatically called once the Thread is running.
	//---------------------------------------------------------------
	@Override
	public void run() {

		String inputLine, outputLine;
		//         outBox.add("SS: Hello, you are connection #"+myNumber); //Send a welcome.
		//This loop constantly waits for input from Client and responds...
		//----------------------------------------------------------------
		while((inputLine = comm.getMessage()) != null) {
			//When the user requests an update of the universe, send it to them.
			if(inputLine.substring(0, 4).equals("REQU"))
				sendClientUpdate();
			else if(inputLine.substring(0, 4).equals("LOGI")) {
				engine.processNewLogin(new ServerMessage(inputLine, myNumber));
				sendClientUpdate();
			}
			else
				engine.addToInbox(inputLine, myNumber);

			//If the user is leaving the game, end the thread?...
			if(inputLine.equals("Bye"))
				break;
		}

		//Clean things up by closing streams and sockets.
		//-----------------------------------------------
		comm.closeNicely();
	} //--end of run() method--

	public void sendClientUpdate() {

		if(myNumber - 1 < engine.getRoster().getPlayers().size())
			comm.sendMessage(engine.getRoster().getPlayers().get(myNumber - 1).pack());

	}
}
