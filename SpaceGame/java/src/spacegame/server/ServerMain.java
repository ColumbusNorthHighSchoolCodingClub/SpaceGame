package src.spacegame.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.Timer;

import src.spacegame.server.SS_GameEngine;
import src.spacegame.server.SS_Thread;
/**
 * Does server things. 
 * Original author: Mike Spock
 * Cleaned and edited by: Brian Pierson
 * @author (Spock, Pierson)
 * @version (051514)
 */
public class ServerMain implements ActionListener //The Server-side main (MULTI_SERVER!)
{	
	private Timer timer;	//Timer that triggers the GameEngine to process a set of moves
	private final int UPDATE_DELAY_IN_MSEC = 8000; //delay in mSec (60000)
	SS_GameEngine engine = new SS_GameEngine();
	ServerSocket sendSocket;
	
	public void main(String[] args) throws IOException 
	{
		System.out.println("Starting up server...");
		ServerMain joe = new ServerMain();
		joe.runServer();
	}
	
	public void runServer() throws IOException 
	{
		engine.initializeGame();
		System.out.println("Universe and game initialized...");
		initTimer();
		System.out.println("Game Timer initialized...");		
		ServerSocket sendSocket = null;
		boolean listening = true;
		
		try //Declare and establish the Server Socket...
		{
			sendSocket = new ServerSocket(4444);			
		}		
		catch(IOException e) 
		{
			System.err.println("Could not listen on port: 4444. or 4445.?");
			System.exit(-1);
		}		
		
		while(listening) //Create a new thread whenever someone tries to connect...
		{
			new SS_Thread(sendSocket.accept()).start();
		}		
		
		sendSocket.close(); //Clean things up by closing socket.
	}
	
	private void initTimer() //Set up a timer that calls this object's action handler.	
	{ 	
		timer = new Timer(UPDATE_DELAY_IN_MSEC, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		timer.start();
	}
	
	public void stop() {
		
		this.timer.stop();
		try {
			sendSocket.close();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) //--SEND/GET UPDATE(s) FROM SERVER--
	{ 									 	//Called whenever timer goes off (every 60 sec.)
		System.out.println("Processing the Server InBox (Timer event) ");
		engine.processInbox();		
	}
}