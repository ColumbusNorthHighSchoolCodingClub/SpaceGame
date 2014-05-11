package src.spacegame;

import java.util.ArrayList;

public class Ship implements Packable {
	
	//XXX: Packing and unPacking Validated

	private static final String HEADER_CLASS = "SHIP";
	
	private final char PARSE_CHAR = '?';
	private final String headerOwner = "OWNR",
			headerHP = "MYHP",
			headerLocX = "LOCX",
			headerLocY = "LOCY";
	
	private int ownerID = -1, locX, locY;
	private int myHP;
	
	public Ship(int ownerID) {
	
		this.ownerID = ownerID;
	}
	
	public Ship(String data) {

		unpack(data);
	}

	public static String getHeader() {
	
		return HEADER_CLASS;
	}
	
	public void setNewOwner(int ownerID) {
	
		this.ownerID = ownerID;
	}
	
	public int getOwnerID() {
	
		return ownerID;
	}
	
	public int[] getCoord() {
	
		return new int[] { locX, locY };
	}
	
	public void setCoord(int x, int y) {
	
		this.locX = x;
		this.locY = y;
	}

	public int getHP() {
	
		return this.myHP;
	}

	public void setHP(int hp) {
	
		this.myHP = hp;
	}

	@Override
	public String pack() {
	
		String pack = HEADER_CLASS + PARSE_CHAR;
		
		pack += headerOwner + ownerID + PARSE_CHAR;
		pack += headerHP + myHP + PARSE_CHAR;
		pack += headerLocX + locX + PARSE_CHAR;
		pack += headerLocY + locY + PARSE_CHAR;
		
		return pack;
	}
	
	@Override
	public void unpack(String data) {
	
		ArrayList<String> parse = ParseUtil.parseString(data, PARSE_CHAR);

		if(!parse.get(0).equals(HEADER_CLASS))
			return;

		for(String str : parse) {
			String subheader = str.substring(0, 4), info = str.substring(4);

			if(subheader.equals(headerOwner))
				ownerID = Integer.parseInt(info);
			else if(subheader.equals(headerHP))
				myHP = Integer.parseInt(info);
			else if(subheader.equals(headerLocX))
				locX = Integer.parseInt(info);
			else if(subheader.equals(headerLocY))
				locY = Integer.parseInt(info);
			else if(!subheader.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Ship");
		}
	}
}
