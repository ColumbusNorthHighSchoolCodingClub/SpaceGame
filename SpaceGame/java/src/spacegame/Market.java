package src.spacegame;

import src.spacegame.client.ClientInfo;
import src.spacegame.client.ClientMain;

public class Market implements Packable {

	private static final String HEADER_CLASS = "MARK";

	private final char PARSE_CHAR = '*';

	private final int fuelMultiplier = 50, matMultiplier = 50;

	private ClientMain clMain;
	private ClientInfo clInfo;	
	
	public Market(ClientMain clMain) {
	
		this.clMain = clMain;
		clInfo = clMain.getClientInfo();

	}
	
	public Market()
	{
	
	}

	public static String getHeader() {
	
		return HEADER_CLASS;
	}

	public boolean requestShipment() {
	
		return false;
	}

	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;
		
		return pack;
	}

	@Override
	public void unpack(String data) {

	}

}
