package src.spacegame.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import src.spacegame.Market;
import src.spacegame.Packable;
import src.spacegame.ParseUtil;
import src.spacegame.Ship;
import src.spacegame.Universe;

public class ClientInfo implements Packable {

	private static final String HEADER_CLASS = "CNFO";

	private final char PARSE_CHAR = '$';
	private final String headerIP = "MYIP",
			headerName = "NAME",
			headerID = "MYID",
			headerMoney = "CASH";

	private ClientMain clMain; //Do Not Pack

	//TODO: Alliances

	//TODO: Home Planet
	private Universe univ;
	private ArrayList<Ship> myShips;

	private Market market;

	private int fuelPurchased = 0, matPurchased = 0, fuelSold = 0, matSold = 0;

	private int myMoney = -1;
	
	private String myIP = "null";
	private String clName = "Joe Smith";
	private int clID = -1;

	public ClientInfo(ClientMain clMain) {

		this.clMain = clMain;
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		}
		catch(UnknownHostException e) {
		}
		
		myIP = addr.getHostAddress();

		myShips = new ArrayList<Ship>();
	}

	public ClientInfo(String data) {
	
		this.myShips = new ArrayList<Ship>();

		this.unpack(data);
	}

	public ClientInfo() {
	
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

	//TODO: public int getTotalFuel()
	//TODO: public int getTotalMat()
	//TODO: public int getMoney()

	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;

		pack += univ.pack() + PARSE_CHAR;

		for(Ship ship : myShips)
			pack += ship.pack() + PARSE_CHAR;

		pack += headerIP + myIP + PARSE_CHAR;
		pack += headerName + clName + PARSE_CHAR;
		pack += headerID + clID + PARSE_CHAR;
		pack += headerMoney + myMoney + PARSE_CHAR;

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
			else if(subheader.equals(headerMoney))
				myMoney = Integer.parseInt(info);
			else if(!subheader.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in ClientInfo");

		}

	}
}
