package src.spacegame.old;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is where the game is processed.
 *
 * @author (Spock)
 * @version (030707)
 */
public class SS_GameEngine {
	//a static array that each request to change the universe goes into...
	private static ArrayList<SS_Message> inbox = new ArrayList<SS_Message>();
	private static ArrayList<Integer> scoreBoard = new ArrayList<Integer>();

	//The major game objects
	private static Universe theUniverse;
	private static Market theMarket;

	public static void initializeGame() {

		theUniverse = new Universe();
		theUniverse.createRandom();
		theMarket = new Market();
	}

	//=-=-=-=-=- STATIC METHODS -=-=-=-=-=

	public static void addToInbox(String msg, int from) {

		inbox.add(new SS_Message(msg, from));

		//Perhaps process immediately?...

	}

	public static String getPackedUniverse() {

		return theUniverse.pack();
	}

	public static String getPackedRoster() {

		return Roster.pack();
	}

	public static String getPackedMarket() {

		return theMarket.pack();
	}

	//================================================================

	public static void processInbox() {

		for(int q = 0; q < inbox.size(); q++) { //Loop through the entire inbox...
			String header = inbox.get(q).getMessage().substring(0, 4);

			if(header.equals("REQU"))
				Debug.msg("Requests should've been handled in SS_Thread! ugh."); //Send the universe, etc...
			else if(header.equals("MOVE"))
				processMoveRequest(inbox.get(q).getMessage()); //Process a move request
			else if(header.equals("BUYY"))
				processPurchaseRequest(inbox.get(q)); //Process a market buy
			else if(header.equals("SELL"))
				processSellRequest(inbox.get(q)); //Process a market sell
			else if(header.equals("SHIP"))
				processShipUpgrade(inbox.get(q)); //Process a ship upgrade
			else if(header.equals("TECH"))
				processTechnologyUpgrade(inbox.get(q).getMessage()); //Process a technology upgrade
			else if(header.equals("ALLY"))
				processNewAlliance(inbox.get(q).getMessage()); //Process a new alliance
			else if(header.equals("LOGI"))
				processNewLogin(inbox.get(q)); //Process a new login

			//             else if(header.equals("BYE"))
			//                 Debug.msg("Disconnecting player #"+myNumber);
			else
				Debug.msg("<SS_Thread> Unexpected message format: " + inbox.get(q).getMessage());

			//The message has been processed, now remove it.
			inbox.remove(q);
			q--;
		}
	} //--end of processInput() method--

	//===================================================================================================
	private static void processMoveRequest(String in) {

		//This method recieves a packed Ships object and updates the universe to match the request.
	}

	//===================================================================================================
	private static void processPurchaseRequest(SS_Message in) {

		//int cost = theMarket.unpack(in);
		//Deal with changing the player in the Roster.
		//if(theMarket.unpack(in).type == 0)
		//{
		////0 is fuel
		//}

	}

	//===================================================================================================
	private static void processSellRequest(SS_Message in) {

	}

	//===================================================================================================
	private static void processShipUpgrade(SS_Message in) {

		Player temp = Roster.getPlayer(in.getWho());
		temp.getShipStats().unpack(in.getMessage());
		//
		Roster.getPlayer(in.getWho()).getShipStats().unpack(in.getMessage());
		//Take care of cost.

	}

	//===================================================================================================
	private static void processTechnologyUpgrade(String in) {

	}

	//===================================================================================================
	private static void processNewAlliance(String in) {

	}

	//===================================================================================================
	private static void processNewLogin(SS_Message in) {

		int who = in.getWho();
		String message = in.getMessage();

		Debug.msg("Processing new Login! - " + message);
		Login theLogin = new Login(false);
		theLogin.unpack(message);

		Player newPlayer = new Player();
		newPlayer.setName(theLogin.getName());

		Roster.addPlayer(newPlayer);
		startUpPlayerGeneration(who);

		Debug.msg("Done with new Login.  New Roster Size = " + Roster.getRosterSize());
	}

	//===================================================================================================

	private void updateSupplies() {

		for(int a = 0; a < theUniverse.NUM_OF_SECTORSX; a++) {
			for(int b = 0; b < theUniverse.NUM_OF_SECTORSY; b++) {
				if(theUniverse.getSector(a, b).hasPlanet() == true) //needs an accessor....
				{
					int player = theUniverse.getSector(a, b).planet.getControlledBy();
					double materialsZ = Roster.theRoster.get(player).getMaterials();
					double fuelZ = 0;
					materialsZ += (Roster.theRoster.get(player).getTechnology().getMineSpeed() * .05 + .2) * theUniverse.getSector(a, b).planet.getMaterialCapacity();

					fuelZ += (Roster.theRoster.get(player).getTechnology().getFuelSpeed() * .05 + .2) * theUniverse.getSector(a, b).planet.getFuelCapacity();

					Roster.theRoster.get(player).setMaterials((int) materialsZ);
					Roster.theRoster.get(player).setFuel((int) fuelZ);

				}
			}
		}
	}

	//===================================================================================================

	//
	private static ArrayList calculateScore() {

		for(int p = 0; p < Roster.theRoster.size(); p++) {
			int fuelComp = 0;
			int materialComp = 0;
			int planetComp = 0;
			int techCompPlanet = 0;
			int techCompShip = 0;
			int shipComp = 0;
			int scoredNow = 0;

			materialComp = Roster.theRoster.get(p).getMaterials();
			fuelComp = Roster.theRoster.get(p).getFuel();

			for(int a = 0; a < theUniverse.NUM_OF_SECTORSX; a++) {
				for(int b = 0; b < theUniverse.NUM_OF_SECTORSY; b++) {

					if(theUniverse.getSector(a, b).hasPlanet() == true) {
						if(theUniverse.getSector(a, b).planet.getControlledBy() == p)
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

	//===================================================================================================
	//
	public static void startUpPlayerGeneration(int playerNum) {

		Random randy = new Random();
		//player is created
		int xOne = -500;
		int yOne = -500;
		int max = theUniverse.NUM_OF_SECTORSX * theUniverse.NUM_OF_SECTORSY * 2;
		int counter = 0;
		int numShips = 15; //change this to be whatever we want.

		do {
			xOne = randy.nextInt(theUniverse.NUM_OF_SECTORSX);
			yOne = randy.nextInt(theUniverse.NUM_OF_SECTORSY);
			counter++;
		}
		while(counter < max && (theUniverse.getSector(xOne, yOne).hasPlanet() && theUniverse.getSector(xOne, yOne).planet.getControlledBy() != -1) || theUniverse.getSector(xOne, yOne).hasPlanet() == false);

		theUniverse.getSector(xOne, yOne).planet.setControlledBy(playerNum);

		generateStartShips(xOne, yOne, playerNum, numShips);
	}

	public static void generateStartShips(int sectorX, int sectorY, int playerNum, int numShips) //Austin
	{

		Ships ship = new Ships(playerNum, numShips);

		theUniverse.getSector(sectorX, sectorY).ships.add(ship);

		for(int a = 0; a < theUniverse.NUM_OF_SECTORSX; a++) {
			for(int b = 0; b < theUniverse.NUM_OF_SECTORSY; b++) {
				//temp for testing!!!!
				Ships tempship = new Ships(playerNum, a + 1);
				theUniverse.getSector(a, b).ships.add(tempship);

				// System.out.println("Ship info!" + theUniverse.getSector(a , b).ships.getNumShips());  FIX THIS
			}
		}
	}

	public void MoveShips() {

		for(int row = 0; row < Universe.NUM_OF_SECTORSY; row++) {
			for(int col = 0; col < Universe.NUM_OF_SECTORSX; col++) {
				for(int w = 0; w < theUniverse.getSector(col, row).ships.size(); w++) {
					if(theUniverse.getSector(col, row).ships.get(w).hasMoved == false) {
						if(col == theUniverse.getSector(col, row).ships.get(w).getDestX() && row == theUniverse.getSector(col, row).ships.get(w).getDestY()) {
							System.out.println("Not moving");
						}
						if(col < theUniverse.getSector(col, row).ships.get(w).getDestX()) {
							theUniverse.getSector(col, row).ships.get(w).hasMoved = true;
							theUniverse.getSector(col + 1, row).ships.add(theUniverse.getSector(col, row).ships.get(w));
							theUniverse.getSector(col, row).ships.remove(w);
						}
						if(col > theUniverse.getSector(col, row).ships.get(w).getDestX()) {
							theUniverse.getSector(col, row).ships.get(w).hasMoved = true;
							theUniverse.getSector(col - 1, row).ships.add(theUniverse.getSector(col, row).ships.get(w));
							theUniverse.getSector(col, row).ships.remove(w);
						}
						if(row < theUniverse.getSector(col, row).ships.get(w).getDestY()) {
							theUniverse.getSector(col, row).ships.get(w).hasMoved = true;
							theUniverse.getSector(col, row + 1).ships.add(theUniverse.getSector(col, row).ships.get(w));
							theUniverse.getSector(col, row).ships.remove(w);
						}
						if(row > theUniverse.getSector(col, row).ships.get(w).getDestY()) {
							theUniverse.getSector(col, row).ships.get(w).hasMoved = true;
							theUniverse.getSector(col, row - 1).ships.add(theUniverse.getSector(col, row).ships.get(w));
							theUniverse.getSector(col, row).ships.remove(w);
						}
					}

				}
			}
			// put your code here
		}
		for(int row = 0; row < Universe.NUM_OF_SECTORSY; row++) {
			for(int col = 0; col < Universe.NUM_OF_SECTORSX; col++) {
				for(int w = 0; w < theUniverse.getSector(col, row).ships.size(); w++) {
					theUniverse.getSector(col, row).ships.get(w).hasMoved = false;
				}
			}
		}
	}//end of move ships///////////////////////

}//--end of SS_GameEngine class--
