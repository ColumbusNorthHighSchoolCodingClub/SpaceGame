package src.spacegame;

import java.util.ArrayList;

/**
 * Universe stores a 2-Dimensional Array of Sectors
 *
 * @author David Baker
 */
public class Universe implements Packable {

	private static final String HEADER_CLASS = "UNIV";

	private final char PARSE_CHAR = '^';
	private final String headerWidth = "WDTH",
			headerHeight = "HGHT";
	
	private int SECTOR_WIDTH = -1, SECTOR_HEIGHT = -1;

	private Sector[][] sectors;

	/**
	 * Creates a New Random Universe with Given Width and Height
	 *
	 * @param sctW How Wide the Universe will Be
	 * @param sctH How Tall the Universe will Be
	 */
	public Universe(int sctW, int sctH) {

		SECTOR_WIDTH = sctW;
		SECTOR_HEIGHT = sctH;

		sectors = new Sector[SECTOR_WIDTH][SECTOR_HEIGHT];

		for(int w = 0; w < SECTOR_WIDTH; w++)
			for(int h = 0; h < SECTOR_WIDTH; h++)
				sectors[w][h] = new Sector(w, h);
	}

	/**
	 * Creates a Universe from the Given String of Data
	 *
	 * @param data Data to be unpacked
	 */
	public Universe(String data) {

		this.unpack(data);
	}
	
	/**
	 * A Blank Universe to for future unpackaging.
	 */
	public Universe() {

	}
	
	/**
	 * Gets the Header used in Packing and Unpacking data
	 * @return HEADER_CLASS
	 */
	public static String getHeader() {

		return HEADER_CLASS;
	}

	public Sector[][] getSectors() {
	
		return sectors;
	}

	public int getSectorWidth() {
	
		return SECTOR_WIDTH;
	}

	public int getSectorHeight() {
	
		return SECTOR_HEIGHT;
	}

	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;

		pack += headerWidth + SECTOR_WIDTH + PARSE_CHAR;
		pack += headerHeight + SECTOR_HEIGHT + PARSE_CHAR;
		
		for(int w = 0; w < SECTOR_WIDTH; w++)
			for(int h = 0; h < SECTOR_HEIGHT; h++)
				pack += sectors[w][h].pack() + PARSE_CHAR;

		return pack;
	}

	@Override
	public void unpack(String data) {

		ArrayList<String> parse = ParseUtil.parseString(data, PARSE_CHAR);
		
		if(!parse.get(0).equals(HEADER_CLASS))
			return;
		
		for(String str : parse) {
			String header = str.substring(0, 4), info = str.substring(4);
			
			if(header.equals(headerWidth))
				SECTOR_WIDTH = Integer.parseInt(info);
			else if(header.equals(headerHeight))
				SECTOR_HEIGHT = Integer.parseInt(info);
			else if(header.equals(Sector.getHeader())) {
				Sector temp = new Sector(str);
				
				if(sectors != null) {
					try {
						sectors[temp.getLocX()][temp.getLocY()].unpack(str);
					}
					catch(NullPointerException e) {
						sectors[temp.getLocX()][temp.getLocY()] = temp;
					}
				}
				else {
					sectors = new Sector[SECTOR_WIDTH][SECTOR_HEIGHT];
					sectors[temp.getLocX()][temp.getLocY()] = temp;
				}
			}
			else if(!header.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Universe");
		}
	}

}
