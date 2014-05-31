package src.spacegame.server;

import java.net.Socket;
import java.util.ArrayList;
import src.spacegame.CommHandler;
import src.spacegame.PlayerStats;
import src.spacegame.client.ClientInfo;

public class SS_Thread extends Thread {
	
	private ArrayList<String> outBox = new ArrayList<String>();
	//--private instance variables--
	private CommHandler comm; //Generic communication object
	private SS_GameEngine engine;
	
	private ClientInfo clInfo;

	public SS_Thread(Socket socket, SS_GameEngine engine) //Constructor
	{

		super("SS_Thread");

		this.engine = engine;

		comm = new CommHandler(socket);

		//comm.setDebugValues("SS_Thread", myNumber);
	}

	public ClientInfo getClientInfo() {

		return clInfo;
	}

	//This method is automatically called once the Thread is running.
	//---------------------------------------------------------------
	@Override
	public void run() {
	
		String message;
		
		//This loop constantly waits for input from Client and responds...
		//----------------------------------------------------------------
		while((message = comm.getMessage()) != null) {

			String header = message.substring(0, 4);
			if(header.equals("LOGI")) {
				
				System.out.println("Processing new Login! - " + message);

				ClientInfo newPlayer = new ClientInfo();
				newPlayer.setName(message.substring(5));
				newPlayer.setID(engine.getRoster().getThreads().indexOf(this));
				newPlayer.setMarket(engine.getMarket());
				newPlayer.setUniverse(engine.getUniverse());
				PlayerStats newstats = new PlayerStats();
				newPlayer.setPlayerStats(newstats);

				this.clInfo = newPlayer;
				sendClientUpdate();
			}
			else if(header.equals("REQU"))
				sendClientUpdate();
			else if(header.equals("STOP"))
				break;
			else
				engine.addToInbox(message, this.clInfo.getID());
		}

		//Clean things up by closing streams and sockets.
		//-----------------------------------------------
		System.out.println("Client #" + clInfo.getID() + " Disconnected: " + clInfo.getName());

		engine.getRoster().getThreads().remove(this);
		comm.close();
	} //--end of run() method--
	
	public void sendClientUpdate() {
	
		comm.sendMessage(this.clInfo.pack());
		comm.sendMessage(this.engine.getRoster().packClientRoster());
	}
}
