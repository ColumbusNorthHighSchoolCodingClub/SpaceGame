package src.spacegame;

/*
 *  If you know a player's number, you can get them from this static Roster
 * it is only an ArrayList to hold all of the Player objects in the game.  
 * Easy to access by any of the classes!  
 * Even use it to statically access 'yourself' to change fuel/materials/$$
 */
import java.util.ArrayList;

import src.spacegame.client.ClientInfo;


public class Roster implements Packable 
{
	private static final char PARSE_CHAR = '*'; //The parse character for this class.
	private ArrayList<ClientInfo> playerRoster = new ArrayList<ClientInfo>();
	
	public Roster()
	{
		
	}
	
	public ArrayList<ClientInfo> getRoster()
	{
		return playerRoster;
	}
	
	public int addPlayer(ClientInfo in)
	{
		playerRoster.add(in);
		System.out.println("Roster: added Player - " + in.getName() + " size is now:" + playerRoster.size());
		return playerRoster.size() - 1;
	}
	
	/**
	 * The pack method converts the data from this class into a String 
	 * for transfer over the network.
	 * 
	 * @return		the String to be transfered
	 */
	public String pack() {
	
		String out = "ROST" + PARSE_CHAR;
		for (int q = 0; q < playerRoster.size(); q++)
			out += playerRoster.get(q).pack() + PARSE_CHAR;
		
		return out;
	}
	
	/**
	 * The unpack method converts the data recieved from the server 
	 * into an object of this class.
	 * 
	 * @data		the String to be converted.
	 */
	public void unpack(String data) {
	
		if (data.substring(0, 4).equals("ROST")) {
			//Unpacking a completely new Roster (for all we know), so erase the old.
			playerRoster.clear();
			ArrayList<String> parsed = ParseUtil.parseString(data, PARSE_CHAR);
			for (int q = 1; q < parsed.size(); q++) //Start at 1, the 0 element is the header
			{
				ClientInfo temp = new ClientInfo(parsed.get(q));
				//temp.unpack(parsed.get(q));
				playerRoster.add(temp);
			}
		}
		else
			System.out.println("Incorrect header (" + data.substring(0, 4) + ")" );
	} //--end of unpack() mehtod--
	
}
