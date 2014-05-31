package src.spacegame.client;

import java.util.ArrayList;
import src.spacegame.ParseUtil;
import src.spacegame.server.ServerRoster;

/**
 * A ClientSide Roster-Object to store basic information about other players.
 *
 * @author David Baker
 */
public class ClientRoster {
	
	private final String headerPlayerName = "NAME",
			headerPlayerNumber = "NMBR",
			headerBreak = "BREK";
	
	private ArrayList<ClientInfo> players;
	
	public ClientRoster(String data) {

		this();
		
		this.unpack(data);
	}
	
	public ClientRoster() {

		players = new ArrayList<ClientInfo>();
	}
	
	public ArrayList<ClientInfo> getPlayers() {

		return players;
	}
	
	public void unpack(String data) {

		ArrayList<String> parse = ParseUtil.parseString(data, ServerRoster.getParseChar());

		if(!parse.get(0).equals(ServerRoster.getHeader()))
			return;
		
		players.clear();
		
		ClientInfo temp = new ClientInfo();

		for(String str : parse) {
			String header = str.substring(0, 4), info = str.substring(4);
			
			if(header.equals(headerPlayerName))
				temp.setName(info);
			else if(header.equals(headerPlayerNumber))
				temp.setID(Integer.parseInt(info));
			else if(header.equals(headerBreak)) {
				players.add(temp);
				temp = new ClientInfo();
			}
			else if(!str.equals(ServerRoster.getHeader()))
				System.out.println("Unknown Packed Info in ClientRoster");
			
		}
	}
}
