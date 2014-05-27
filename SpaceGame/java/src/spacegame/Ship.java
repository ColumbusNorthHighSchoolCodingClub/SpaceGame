package src.spacegame;

import java.awt.Point;
import java.util.ArrayList;

public class Ship implements Packable {
	
	//XXX: Packing and unPacking Validated

	private static final String HEADER_CLASS = "SHIP";
	
	private final char PARSE_CHAR = '?';
	private final String headerOwner = "OWNR",
			headerHP = "MYHP",
			headerLocX = "LOCX",
			headerLocY = "LOCY";
	
	Point dest = new Point(-1, -1),
			coord = new Point(-1, -1);
	
	private int ownerID = -1;
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
	
	public Point getCoord() {
	
		return coord;
	}
	
	public void setCoord(int x, int y) {
	
		coord.setLocation(x, y);
	}

	public Point getDestination() {

		return dest;
	}
	
	public void setDestination(int x, int y) {
	
		dest.setLocation(x, y);
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
		pack += headerLocX + coord.getX() + PARSE_CHAR;
		pack += headerLocY + coord.getY() + PARSE_CHAR;
		
		return pack;
	}
	
	@Override
	public void unpack(String data) {
	
		ArrayList<String> parse = ParseUtil.parseString(data, PARSE_CHAR);

		if(!parse.get(0).equals(HEADER_CLASS))
			return;
		
		int x = -1, y = -1;

		for(String str : parse) {
			String subheader = str.substring(0, 4), info = str.substring(4);

			if(subheader.equals(headerOwner))
				ownerID = Integer.parseInt(info);
			else if(subheader.equals(headerHP))
				myHP = Integer.parseInt(info);
			else if(subheader.equals(headerLocX))
				x = Integer.parseInt(info);
			else if(subheader.equals(headerLocY))
				y = Integer.parseInt(info);
			else if(!subheader.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Ship");
		}
		
		coord = new Point(x, y);
	}
}
