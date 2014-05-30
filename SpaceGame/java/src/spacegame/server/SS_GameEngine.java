package src.spacegame.server;

import java.util.ArrayList;
import java.util.Random;
import src.spacegame.Market;
import src.spacegame.Planet;
import src.spacegame.PlayerStats;
import src.spacegame.Roster;
import src.spacegame.Ship;
import src.spacegame.Universe;
import src.spacegame.client.ClientInfo;

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

		theUniverse = new Universe(10, 10);
		theMarket = new Market();
	}

	public void addToInbox(String msg, int from) {

		inbox.add(new ServerMessage(msg, from));
	}

	public Roster getRoster()
	{

		return roster;
	}

	public String getPackedUniverse()
	{

		return theUniverse.pack();
	}

	public String getPackedRoster() {

		return roster.pack();
	}

	public String getPackedMarket()
	{

		return theMarket.pack();
	}

	public void processInbox()
	{
	
		for(int i = 0; i < inbox.size(); i++) //Loop through whole inbox
		{
			ServerMessage message = inbox.get(i);
			String header = message.getMessage().substring(0, 4);
			
			if(header.equals("REQU")) //Send the universe, etc...
				System.out.println("Requests should've been handled in SS_Thread! ugh.");
			else if(header.equals("MOVE")) //Process a move request
				processMoveRequest(message);
			else if(header.equals("BUYY"))//Process a market buy
				processPurchaseRequest(message);
			else if(header.equals("SELL"))//Process a market sell
				processSellRequest(message);
			else if(header.equals("SHIP"))//Process a ship upgrade
				processShipUpgrade(message);
			else if(header.equals(PlayerStats.getHeader()))//Process a technology upgrade
				processTechnologyUpgrade(message);
			else if(header.equals("ALLY"))//Process a new alliance
				processNewAlliance(message);
			else if(header.equals("LOGI"))//Process a new login
				processNewLogin(message);
			else
				System.out.println("<SS_Thread> Unexpected message format: " + message.getMessage());

			inbox.remove(i);
			i--;
		}
	}

	//The next few methods all process the different actions that
	//can occur in the universe

	private void processMoveRequest(ServerMessage in)
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

		ClientInfo temp = roster.getPlayers().get(in.getOwner());
		//		temp.getShipStats().unpack(in.getMessage());
		//		roster.getPlayer(in.getWho()).getShipStats().unpack(in.getMessage());
		//Take care of cost.
	}

	private void processTechnologyUpgrade(ServerMessage in)
	{

	}

	private void processNewAlliance(ServerMessage in)
	{

	}

	public void processNewLogin(ServerMessage in)
	{

		int who = in.getOwner();
		String message = in.getMessage();

		System.out.println("Processing new Login! - " + message);

		ClientInfo newPlayer = new ClientInfo();
		newPlayer.setName(message.substring(5));
		newPlayer.setID(roster.getPlayers().size());
		newPlayer.setMarket(theMarket);
		newPlayer.setUniverse(theUniverse);
		PlayerStats newstats = new PlayerStats();
		newPlayer.setPlayerStats(newstats);

		roster.addPlayer(newPlayer);
		startUpPlayerGeneration(who);

		System.out.println("Done with new Login.  New Roster Size = " + roster.getPlayers().size());
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
					PlayerStats player = roster.getPlayers().get(playerID).getStats();
					//double materialsZ = ;
					//double fuelZ = 0;
					//materialsZ += (player.getTechnology().getMineSpeed() * .05 + .2) * v.getMat();
					//fuelZ += (player.getTechnology().getFuelSpeed() * .05 + .2) * v.getFuel();
					player.setMat((int) (player.getMat() + ((player.getMineSpeed() * .05 + .2) * v.getMat())));
					player.setFuel((int) (player.getFuelSpeed() * .05 + .2) * v.getFuel());
				}
			}
		}
	}

	private ArrayList calculateScore() {

		for(int p = 0; p < roster.getPlayers().size(); p++)
		{
			PlayerStats player = roster.getPlayers().get(p).getStats();
			int fuelComp = 0;
			int materialComp = 0;
			int planetComp = 0;
			int techCompPlanet = 0;
			int techCompShip = 0;
			int shipComp = 0;
			int scoredNow = 0;

			materialComp = player.getMat();
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
			techCompPlanet += player.getFuelSpeed();
			techCompPlanet += player.getMineSpeed();

			techCompShip += player.getStealthStat();
			techCompShip += player.getSensorsStat();

			// shipComp += theRoster(a).getShips ??!!??
			scoredNow = materialComp / 100 + fuelComp / 100 + planetComp * 10 + techCompPlanet + techCompShip + shipComp / 20;

			if(scoreBoard.size() < p)
				scoreBoard.add(p, scoredNow);
			else
				scoreBoard.set(p, scoredNow);
		}
		return scoreBoard;
	}

	public void startUpPlayerGeneration(int playerNum)
	{

		Random randy = new Random();
		//player is created
		int xOne = -500;
		int yOne = -500;
		int max = theUniverse.getSectorWidth() * theUniverse.getSectorHeight() * 2;
		int counter = 0;
		int numShips = 15; //change this to be whatever we want.

		do {
			xOne = randy.nextInt(theUniverse.getSectorWidth());
			yOne = randy.nextInt(theUniverse.getSectorHeight());
			counter++;
		}
		//Planet v : theUniverse.getSectors()[a][b].getPlanets()
		//change so players only get one planet at a time
		//Example: if a sector has two or more planets, the player will only be given one of those
		//sharing sectors should allow for some more interesting game play
		while(counter < max && ((theUniverse.getSectors()[xOne][yOne].getPlanets().size() != 0) && theUniverse.getSectors()[xOne][yOne].getPlanets().get(0).getOwnerID() != -1) || (theUniverse.getSectors()[xOne][yOne].getPlanets().size() == 0));

		theUniverse.getSectors()[xOne][yOne].getPlanets().get(0).setNewOwner(playerNum);

		generateStartShips(xOne, yOne, playerNum, numShips);
	}

	public void generateStartShips(int sectorX, int sectorY, int playerID, int numShips) //Austin
	{

		Ship ship = new Ship(playerID);
		theUniverse.getSectors()[sectorX][sectorY].getShips().add(ship);

		//		for(int a = 0; a < theUniverse.getSectorWidth(); a++) {
		//			for(int b = 0; b < theUniverse.getSectorHeight(); b++) {
		//				//temp for testing!!!!
		//				Ships tempship = new Ships(playerNum, a + 1);
		//				theUniverse.getSector(a, b).ships.add(tempship);
		//
		//				// System.out.println("Ship info!" + theUniverse.getSector(a , b).ships.getNumShips());  FIX THIS
		//			}
		//		}
	}

	public void MoveShips() {
		for(int row = 0; row < theUniverse.getSectorHeight(); row++) {
			for(int col = 0; col < theUniverse.getSectorWidth(); col++) {
				//for(int w = 0; w < theUniverse.getSectors()[col][row].getShips().size(); w++) {
				for(int i = 0; i <= theUniverse.getSectors()[col][row].getShips().size(); i++) {
					Ship w = theUniverse.getSectors()[col][row].getShips().get(i);
					theUniverse.getSectors()[w.getDestination().x][w.getDestination().y].getShips().add(theUniverse.getSectors()[col][row].getShips().remove(i));
					//should be unnessecary now, leaving in case current code dosent work
		/*			if(col == w.getDestination().x)
					{
						//do nothing
					}
					else if(col < w.getDestination().x)
					{
						theUniverse.getSectors()[w.getDestination().x][row].getShips().add(theUniverse.getSectors()[col][row].getShips().remove(i));
					}
					else
					{
						theUniverse.getSectors()[col-1][row].getShips().add(theUniverse.getSectors()[col][row].getShips().remove(i));
					}

					if(row == w.getDestination().y)
					{
						//do nothing
					}
					else if(row < w.getDestination().y)
					{
						theUniverse.getSectors()[col][w.getDestination().y].getShips().add(theUniverse.getSectors()[col][row].getShips().remove(i));
					}
					else
					{
						theUniverse.getSectors()[col][row-1].getShips().add(theUniverse.getSectors()[col][row].getShips().remove(i));
					}*/
				}
			}
		}
	}
}