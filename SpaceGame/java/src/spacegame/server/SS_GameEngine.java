package src.spacegame.server;

import java.util.ArrayList;
import java.util.Random;
import src.spacegame.client.ClientInfo;
import src.spacegame.old.Debug;
import src.spacegame.old.Login;
import src.spacegame.old.Market;
import src.spacegame.old.Player;
import src.spacegame.old.Roster;
import src.spacegame.Planet;
import src.spacegame.Universe;
import src.spacegame.server.ServerMessage;

/**
 * This is where the game is processed.
 * Original author: Mike Spock
 * Cleaned and edited by: Brian Pierson
 * @author (Spock, Pierson)
 * @version (051514)
 */
public class SS_GameEngine 
{
	//a static array that each request to change the universe goes into...
	private static ArrayList<ServerMessage> inbox = new ArrayList<ServerMessage>(); 	
	private static ArrayList<Integer> scoreBoard = new ArrayList<Integer>();
	
	//The major game objects
	private static Universe theUniverse;
	private static Market theMarket;
	
	public void initializeGame() 
	{
		theUniverse = new Universe(10,10);
		theMarket = new Market();		
	}
	
	public void addToInbox(String msg, int from)
	{
		inbox.add(new ServerMessage(msg, from));
	}
	
	public String getPackedUniverse()
	{
		return theUniverse.pack();
	}
	
	public String getPackedMarket()
	{
		return theMarket.pack();
	}
	
	public void processInbox()
	{
		for(int i=0; i < inbox.size(); i++) //Loop through whole inbox
		{
			ServerMessage message = inbox.get(i);
			String header =  message.getMessage().substring(0,4);
			
				//Send the universe, etc...
			if(header == "REQU") Debug.msg("Requests should've been handled in SS_Thread! ugh.");
				//Process a move request
			else if(header.equals("MOVE")) processMoveRequest(message.getMessage()); 
				//Process a market buy
			else if(header.equals("BUYY")) processPurchaseRequest(message); 
				//Process a market sell
			else if(header.equals("SELL")) processSellRequest(message); 
				//Process a ship upgrade
			else if(header.equals("SHIP")) processShipUpgrade(message); 
				//Process a technology upgrade
			else if(header.equals("TECH")) processTechnologyUpgrade(message.getMessage()); 
				//Process a new alliance
			else if(header.equals("ALLY")) processNewAlliance(message.getMessage()); 
				//Process a new login
			else if(header.equals("LOGI")) processNewLogin(message);
				//In case of a mistake
			else Debug.msg("<SS_Thread> Unexpected message format: " + message.getMessage());
			
			inbox.remove(i);
			i--;			
		}
	}
	
	//The next few methods all process the different actions that
	//can occur in the universe
	
	private void processMoveRequest(String in) 
	{
		//This method (will) receive a packed Ships object and updates the universe to match the request.
	}
	
	private void processPurchaseRequest(ServerMessage in)
	{
		//int cost = theMarket.unpack(in);
				//Deal with changing the player in the Roster.
				//if(theMarket.unpack(in).type == 0)
				//{
				////0 is fuel
				//}
	}
	
	private void processSellRequest(ServerMessage in) 
	{

	}
	
	private void processShipUpgrade(ServerMessage in) 
	{
		Player temp = Roster.getPlayer(in.getWho());
		temp.getShipStats().unpack(in.getMessage());
		Roster.getPlayer(in.getWho()).getShipStats().unpack(in.getMessage());
		//Take care of cost.
	}
	
	private void processTechnologyUpgrade(String in) 
	{

	}
	
	private void processNewAlliance(String in)
	{

	}
	
	private void processNewLogin(ServerMessage in) 
	{
		int who = in.getWho();
		String message = in.getMessage();

		Debug.msg("Processing new Login! - " + message);
		Login theLogin = new Login(false);
		theLogin.unpack(message);

		ClientInfo newPlayer = new ClientInfo();
		newPlayer.setName(theLogin.getName());

		Roster.addPlayer(newPlayer);
		startUpPlayerGeneration(who);

		Debug.msg("Done with new Login.  New Roster Size = " + Roster.getRosterSize());
	}
	
	private void updateSupplies() 
	{
		for(int a = 0; a < theUniverse.getSectorWidth(); a++)
		{
			for(int b = 0; b < theUniverse.getSectorHeight(); b++)
			{				
				for(Planet v : theUniverse.getSectors()[a][b].getPlanets())
				{
					int playerID = v.getOwnerID();
					ClientInfo player = Roster.getPlayer(playerID);
					double materialsZ = Roster.getPlayer(playerID).getMaterials();
					double fuelZ = 0;
					materialsZ += (Roster.getPlayer(playerID).getTechnology().getMineSpeed() * .05 + .2) * v.getMaterialCapacity();

					fuelZ += (Roster.theRoster.get(playerID).getTechnology().getFuelSpeed() * .05 + .2) * theUniverse.getSector(a, b).planet.getFuelCapacity();

					Roster.theRoster.get(playerID).setMaterials((int) materialsZ);
					Roster.theRoster.get(playerID).setFuel((int) fuelZ);
				}
			}
		}
	}
}