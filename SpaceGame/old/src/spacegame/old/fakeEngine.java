package src.spacegame.old;

import java.util.ArrayList;
import java.lang.Integer;

/**
 * This is where the game is processed.  
 * 
 * @author (Spock) 
 * @version (030707)
 */
public class fakeEngine {
	//a static array that each request to change the universe goes into... 
	private static ArrayList<SS_Message> inbox = new ArrayList<SS_Message>();
	
	//The major game objects
	private static Universe theUniverse;
	private static Market theMarket;
	private static ArrayList<Integer> allScores = new ArrayList<Integer>();
	
	public static void initializeGame() {
	
		theUniverse = new Universe();
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
	
		for (int q = 0; q < inbox.size(); q++) { //Loop through the entire inbox... 
			String header = inbox.get(q).getMessage().substring(0, 4);
			
			if (header.equals("REQU"))
				Debug.msg("Requests should've been handled in SS_Thread! ugh."); //Send the universe, etc...
			else if (header.equals("MOVE"))
				processMoveRequest(inbox.get(q).getMessage()); //Process a move request
			else if (header.equals("BUYY"))
				processPurchaseRequest(inbox.get(q)); //Process a market buy
			else if (header.equals("SELL"))
				processSellRequest(inbox.get(q)); //Process a market sell
			else if (header.equals("SHIP"))
				processShipUpgrade(inbox.get(q)); //Process a ship upgrade
			else if (header.equals("TECH"))
				processTechnologyUpgrade(inbox.get(q).getMessage()); //Process a technology upgrade
			else if (header.equals("ALLY"))
				processNewAlliance(inbox.get(q).getMessage()); //Process a new alliance
			else if (header.equals("LOGI"))
				processNewLogin(inbox.get(q).getMessage()); //Process a new login
				
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
	private static void processNewLogin(String in) {
	
		Debug.msg("Processing new Login! - " + in);
		Login theLogin = new Login(false);
		theLogin.unpack(in);
		
		Player newPlayer = new Player();
		newPlayer.setName(theLogin.getName());
		
		Roster.addPlayer(newPlayer);
		//theUniverse.addNewPlayerByGivingPlanetAndShipsToStartWith();
		
		Debug.msg("Done with new Login.  New Roster Size = " + Roster.getRosterSize());
	}
	//===================================================================================================
	
}//--end of SS_GameEngine class--
