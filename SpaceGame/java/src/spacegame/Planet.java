package src.spacegame;

import java.util.ArrayList;
import java.util.Random;

public class Planet implements Packable {
	
	//XXX: Packing and unPacking Validated

	private static final String HEADER_CLASS = "PLNT";
	
	private final char PARSE_CHAR = '@';
	private final String headerOwner = "OWNR",
			headerPlntType = "PLTP";
	
	private Random rand = new Random();
	
	private int offenseStat;
	private int defenseStat;
	private int fuelStat;
	private int matStat;

	private int ownerID = -1;
	private PlanetType myType;
	
	public Planet(int ownerID) {

		this.ownerID = ownerID;

		//TODO: Make Type Selection Based on Rarity of Planet
		for(int loop = 0; myType == null; loop++) {
			
			PlanetType type = PlanetType.values()[loop];
			
			double r = Math.random();
			if(r < type.getRarity()) {
				myType = type;
				System.out.println("Drop Num:" + r);
				System.out.println("  --Rarity:" + type.getRarity());
			}
			
			if(loop + 1 == PlanetType.values().length)
				loop = -1;
		}
		
		myType = PlanetType.values()[rand.nextInt(PlanetType.values().length)];

		offenseStat = myType.getBaseOffense() + rand.nextInt(11);
		defenseStat = myType.getBaseDefense() + rand.nextInt(11);
		fuelStat = myType.getBaseFuel() + rand.nextInt(100);
		matStat = myType.getBaseMat() + rand.nextInt(100);
	}
	
	public Planet(String data) {
	
		this.unpack(data);
	}
	
	public Planet() {

	}
	
	public static String getHeader() {
	
		return HEADER_CLASS;
	}
	
	public int getOwnerID() {
	
		return ownerID;
	}
	
	public PlanetType getType() {
	
		return myType;
	}

	public int getOffense() {
	
		return offenseStat;
	}

	public int getDefense() {
	
		return defenseStat;
	}

	public int getFuel() {
	
		return fuelStat;
	}

	public int getMat() {
	
		return matStat;
	}

	public void setNewOwner(int ownerID) {
	
		this.ownerID = ownerID;
	}
	
	public void setType(PlanetType type) {

		this.myType = type;
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
