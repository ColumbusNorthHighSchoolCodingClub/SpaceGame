package src.spacegame.old;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.Timer;

public class SS_Main implements ActionListener
//The Server-side main (MULTI_SERVER!)
{
	//Timer that triggers the GameEngine to process a set of moves
	private Timer timer;
	private final int UPDATE_DELAY_IN_MSEC = 8000; //delay in mSec (60000)
	
	public static void main(String[] args) throws IOException {
	
		Debug.msg("Starting up server...");
		SS_Main joe = new SS_Main();
		joe.runTheGameServer();
	}
	
	public void runTheGameServer() throws IOException {
	
		SS_GameEngine.initializeGame();
		Debug.msg("Universe and game initialized...");
		//
		//                 Planet.initializePlanets(); //Austin
		//         Debug.msg("Planet types made...");
		
		initTimer();
		Debug.msg("Game Timer initialized...");
		
		ServerSocket sendSocket = null;
		boolean listening = true;
		
		//Declare and establish the Server Socket...
		//-----------------------------------------
		try {
			//             System.out.println("At the start.");
			sendSocket = new ServerSocket(4444);
			//             System.out.println("At the start2.");
			
		}
		catch(IOException e) {
			System.err.println("Could not listen on port: 4444. or 4445.?");
			System.exit(-1);
		}
		
		//Create a new thread whenever someone tries to connect...
		//-----------------------------------------
		while(listening) {
			new SS_Thread(sendSocket.accept()).start();
		}
		
		//Clean things up by closing socket.
		//-----------------------------------------------
		sendSocket.close();
	}
	
	//*********************************************************
	private void initTimer() { //Set up a timer that calls this object's action handler.
	
		timer = new Timer(UPDATE_DELAY_IN_MSEC, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		timer.start();
	}
	
	//========================================================================
	@Override
	public void actionPerformed(ActionEvent e) { //--SEND/GET UPDATE(s) FROM SERVER--
	
		//Called whenever timer goes off (every 60 sec.)
		Debug.msg("Processing the Server InBox (Timer event) ");
		SS_GameEngine.processInbox();
		
	}//--- end of actionPerformed() method ---
	
}//-- end of SS_Main class --

