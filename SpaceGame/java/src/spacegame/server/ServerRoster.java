package src.spacegame.server;

/*
 *  If you know a player's number, you can get them from this static Roster
 * it is only an ArrayList to hold all of the Player objects in the game.
 * Easy to access by any of the classes!
 * Even use it to statically access 'yourself' to change fuel/materials/$$
 */
import java.util.ArrayList;

public class ServerRoster
{
	private static final String HEADER_CLASS = "RSTR";

	private final String headerPlayerName = "NAME",
			headerPlayerNumber = "NMBR",
			headerBreak = "BREK";

	private static final char PARSE_CHAR = '$'; //The parse character for this class.
	
	private ArrayList<SS_Thread> playerThreads;

	public ServerRoster()
	{

		this.playerThreads = new ArrayList<SS_Thread>();
	}

	public static String getHeader() {
	
		return HEADER_CLASS;
	}

	public static char getParseChar() {
	
		return PARSE_CHAR;
	}

	public void addThread(SS_Thread thread) {

		thread.start();
		this.playerThreads.add(thread);
	}
	
	public ArrayList<SS_Thread> getThreads() {

		return playerThreads;
	}
	
	/**
	 * The pack method converts the data from this class into a String
	 * for transfer over the network.
	 *
	 * @return		the String to be transfered
	 */
	public String packClientRoster() {

		String out = HEADER_CLASS + PARSE_CHAR;

		for(SS_Thread th : playerThreads) {

			out += headerPlayerName + th.getClientInfo().getName() + PARSE_CHAR;
			out += headerPlayerNumber + th.getClientInfo().getID() + PARSE_CHAR;
			out += headerBreak + PARSE_CHAR;
		}
		
		return out;
	}

}
