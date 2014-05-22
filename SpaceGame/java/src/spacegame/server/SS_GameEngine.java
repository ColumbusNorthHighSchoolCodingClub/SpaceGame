package src.spacegame.server;

import java.util.ArrayList;
import java.util.Random;

import src.spacegame.client.ClientInfo;
import src.spacegame.Market;
import src.spacegame.Roster;
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
	private ArrayList<ServerMessage> inbox = new ArrayList<ServerMessage>(); 	
	private ArrayList<Integer> scoreBoard = new ArrayList<Integer>();
	private Roster roster = new Roster();
	//The major game objects
	private Universe theUniverse;
	private Market theMarket;
	
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
			if(header == "REQU") System.out.println("Requests should've been handled in SS_Thread! ugh.");
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
			else System.out.println("<SS_Thread> Unexpected message format: " + message.getMessage());
			
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
		ClientInfo temp = roster.getRoster().get(in.getWho());
//		temp.getShipStats().unpack(in.getMessage());
//		roster.getPlayer(in.getWho()).getShipStats().unpack(in.getMessage());
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

		System.out.println("Processing new Login! - " + message);

		ClientInfo newPlayer = new ClientInfo(message);

		roster.addPlayer(newPlayer);
		startUpPlayerGeneration(who);

		System.out.println("Done with new Login.  New Roster Size = " + roster.getRoster().size());
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
					ClientInfo player = roster.getRoster().get(playerID);
					//double materialsZ = ;
					//double fuelZ = 0;
					//materialsZ += (player.getTechnology().getMineSpeed() * .05 + .2) * v.getMat();
					//fuelZ += (player.getTechnology().getFuelSpeed() * .05 + .2) * v.getFuel();
					player.setMaterials((int) (player.getMaterials() + ((player.getTechnology().getMineSpeed() * .05 + .2) * v.getMat())));
					player.setFuel((int) (player.getTechnology().getFuelSpeed() * .05 + .2) * v.getFuel());
				}
			}
		}
	}
	
	private ArrayList calculateScore() {

		for(int p = 0; p < roster.getRoster().size(); p++) 
		{
			ClientInfo player = roster.getRoster().get(p);
			int fuelComp = 0;
			int materialComp = 0;
			int planetComp = 0;
			int techCompPlanet = 0;
			int techCompShip = 0;
			int shipComp = 0;
			int scoredNow = 0;

			materialComp = player.getMaterials();
			fuelComp = player.getFuel();

			for(int a = 0; a < theUniverse.getSectorWidth(); a++)
			{
				for(int b = 0; b < theUniverse.getSectorHeight(); b++)
				{
					for(Planet v : theUniverse.getSectors()[a][b].getPlanets())
					{
						if(v.getOwnerID() == p)
							planetComp++;
					}
				}
			}
			techCompPlanet += Roster.theRoster.get(p).getTechnology().getFuelSpeed();
			techCompPlanet += Roster.theRoster.get(p).getTechnology().getMineSpeed();

			techCompShip += Roster.theRoster.get(p).getTechnology().getStealth();
			techCompShip += Roster.theRoster.get(p).getTechnology().getSensors();

			// shipComp += theRoster(a).getShips ??!!??
			scoredNow = materialComp / 100 + fuelComp / 100 + planetComp * 10 + techCompPlanet + techCompShip + shipComp / 20;

			if(scoreBoard.size() < p) {
				scoreBoard.add(p, scoredNow);
			}
			else {
				scoreBoard.set(p, scoredNow);
			}

		}

		return scoreBoard;
	}
}