package src.spacegame;

import java.util.ArrayList;
import java.util.Random;

public class Sector implements Packable {

	//XXX: Packing and unPacking Validated

	private static final String HEADER_CLASS = "SECT";

	private final char PARSE_CHAR = '§';
	private final String headerLocX = "LOCX",
			headerLocY = "LOCY";

	private ArrayList<Planet> planets;
	private ArrayList<Ship> ships;

	private int locX, locY;

	public Sector(int locX, int locY) {

		this.locX = locX;
		this.locY = locY;

		Random rand = new Random();
		
		ships = new ArrayList<Ship>();

		planets = new ArrayList<Planet>();

		for(int loop = 0; loop < 3; loop++)
			if(rand.nextInt(2) == 0)
				planets.add(new Planet());

	}

	public Sector(String data) {

		this();
		this.unpack(data);
	}

	public Sector() {
	
		planets = new ArrayList<Planet>();
		ships = new ArrayList<Ship>();
	}

	public static String getHeader() {

		return HEADER_CLASS;
	}

	public int getLocX() {

		return locX;
	}

	public int getLocY() {

		return locY;
	}

	public boolean hasShips() {

		return(ships.size() != 0);
	}

	public ArrayList<Ship> getShips() {

		return ships;
	}

	public boolean hasPlanets() {

		return(planets.size() != 0);
	}

	public ArrayList<Planet> getPlanets() {

		return planets;
	}

	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;

		pack += headerLocX + locX + PARSE_CHAR;
		pack += headerLocY + locY + PARSE_CHAR;

		for(Planet plt : planets)
			pack += plt.pack() + PARSE_CHAR;

		if(hasShips())
			for(Ship ship : ships)
				pack += ship.pack() + PARSE_CHAR;

		return pack;
	}

	@Override
	public void unpack(String data) {

		ArrayList<String> parse = ParseUtil.parseString(data, PARSE_CHAR);

		if(!parse.get(0).equals(HEADER_CLASS))
			return;

		int planCount = 0, shipCount = 0;

		for(String str : parse) {
			String header = str.substring(0, 4), info = str.substring(4);

			if(header.equals(headerLocX))
				locX = Integer.parseInt(info);
			else if(header.equals(headerLocY))
				locY = Integer.parseInt(info);
			else if(header.equals(Planet.getHeader())) {
				if(!(planCount < planets.size() - 1))
					planets.add(new Planet(str));
				else
					planets.get(planCount).unpack(str);
				planCount++;
			}
			else if(header.equals(Ship.getHeader())) {
				
				if(!(shipCount < ships.size() - 1))
					ships.add(new Ship(str));
				else
					ships.get(planCount).unpack(str);
				shipCount++;
			}
			else if(!str.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Sector");
		}
	}

}
