package src.spacegame.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import src.spacegame.Market;
import src.spacegame.Packable;
import src.spacegame.ParseUtil;
import src.spacegame.PlayerStats;
import src.spacegame.Ship;
import src.spacegame.Universe;

public class ClientInfo implements Packable {
	
	private static final String HEADER_CLASS = "CNFO";
	
	private final char PARSE_CHAR = '$';
	private final String headerIP = "MYIP",
			headerName = "NAME",
			headerID = "MYID";
	
	private ClientMain clMain; //Do Not Pack
	
	//TODO: Alliances
	private Universe univ;
	private Market market;

	private PlayerStats myStats;
	private ArrayList<Ship> myShips;
	
	private String myIP = "null";
	private String clName = "Joe Smith";
	private int clID = -1;
	
	public ClientInfo(ClientMain clMain) {
	
		this();
		
		this.clMain = clMain;
	}
	
	public ClientInfo(String data) {

		this();
		
		this.unpack(data);
	}
	
	public ClientInfo() {

		this.myStats = new PlayerStats();
		this.myShips = new ArrayList<Ship>();
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		}
		catch(UnknownHostException e) {
		}

		myIP = addr.getHostAddress();
	}

	public void init() throws UnknownHostException {

		this.clMain.getClientComm().addMessage("LOGI" + PARSE_CHAR + clName);
	}
	
	public static String getHeader() {
	
		return HEADER_CLASS;
	}
	
	public String getName() {
	
		return clName;
	}

	public void setName(String str) {
	
		if(str == null)
			return;

		if(str.length() < 3)
			return;

		this.clName = str;
	}
	
	public int getID() {
	
		return clID;
	}
	
	public String getIP() {

		return myIP;
	}
	
	@Override
	public String pack() {
	
		String pack = HEADER_CLASS + PARSE_CHAR;
		
		pack += univ.pack() + PARSE_CHAR;
		
		for(Ship ship : myShips)
			pack += ship.pack() + PARSE_CHAR;
		
		pack += headerIP + myIP + PARSE_CHAR;
		pack += headerName + clName + PARSE_CHAR;
		pack += headerID + clID + PARSE_CHAR;

		return pack;
	}
	
	@Override
	public void unpack(String data) {
	
		ArrayList<String> parse = ParseUtil.parseString(data, PARSE_CHAR);

		if(!parse.get(0).equals(HEADER_CLASS))
			return;

		myShips.clear();
		
		for(String str : parse) {
			String subheader = str.substring(0, 4), info = str.substring(4);

			if(subheader.equals(Universe.getHeader()))
				univ.unpack(info);
			else if(subheader.equals(Ship.getHeader()))
				myShips.add(new Ship(info));
			else if(subheader.equals(headerIP))
				myIP = info;
			else if(subheader.equals(headerName))
				clName = info;
			else if(subheader.equals(headerID))
				clID = Integer.parseInt(info);
			else if(!subheader.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in ClientInfo");
			
		}
		
	}
}
