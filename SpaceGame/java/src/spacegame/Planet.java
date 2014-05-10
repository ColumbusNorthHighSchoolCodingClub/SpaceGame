package src.spacegame;

import java.util.ArrayList;
import java.util.Random;
import src.spacegame.old.ParseUtil;

public class Planet implements Packable {
	
	private static final String HEADER_CLASS = "PLNT";
	
	private final char PARSE_CHAR = '$';
	private final String headerOwner = "OWNR",
			headerPlntType = "TYPE";
	
	private Random rand = new Random();
	
	private int ownerID = -1;
	private PlanetType myType;
	
	public Planet() {
	
		this(-1);
	}
	
	public Planet(int ownerID) {
	
		this.ownerID = ownerID;
		genPlanet();
	}
	
	public static String getHeader() {
	
		return HEADER_CLASS;
	}
	
	private void genPlanet() {
	
		//TODO: Make Type Selection Based on Rarity of Planet
		myType = PlanetType.values()[rand.nextInt(PlanetType.values().length)];
	}
	
	public int getOwnerID() {
	
		return ownerID;
	}
	
	public void setNewOwner(int ownerID) {
	
		this.ownerID = ownerID;
	}
	
	@Override
	public String pack() {
	
		String pack = HEADER_CLASS + PARSE_CHAR;
		
		pack += headerOwner + ownerID + PARSE_CHAR;
		pack += headerPlntType + myType.name() + PARSE_CHAR;
		
		//TODO: Add Individual Generated Stats
		
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
				this.ownerID = Integer.parseInt(info);
			else if(subheader.equals(headerPlntType))
				this.myType = PlanetType.valueOf(info);
			else if(!subheader.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Planet");
		}
	}
	
}
