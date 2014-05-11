package src.spacegame.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
	//TODO: Market

	private String myIP = "null";
	private String clName = "Default";
	private int clID = -1;

	private int myMoney = -1;

	public ClientInfo(ClientMain clMain) {

		this.clMain = clMain;

		try {
			InetAddress addr = InetAddress.getLocalHost();
			myIP = addr.getHostAddress();
		}
		catch(UnknownHostException e) {
		}
		
		clName = JOptionPane.showInputDialog("Please Enter your Name (no profanity)", "John Doe");
		this.clMain.getClientComm().addMessage("LOGI" + PARSE_CHAR + clName);
		
		myShips = new ArrayList<Ship>();
	}

	public ClientInfo() {
	
	}

	public static String getHeader() {

		return HEADER_CLASS;
	}

	public String getName() {

		return clName;
	}

	public int getID() {

		return clID;
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
