package src.spacegame;

import java.util.ArrayList;
import src.spacegame.old.ParseUtil;

public class Universe implements Packable {

	private static final String HEADER_CLASS = "UNIV";

	private final char PARSE_CHAR = '*';
	private final int SECTOR_WIDTH, SECTOR_HEIGHT;

	private final Sector[][] sectors;

	public Universe() {

		this(10, 10);
	}

	public Universe(int sctW, int sctH) {

		SECTOR_WIDTH = sctW;
		SECTOR_HEIGHT = sctH;

		sectors = new Sector[SECTOR_WIDTH][SECTOR_HEIGHT];

		for(int w = 0; w < SECTOR_WIDTH; w++)
			for(int h = 0; h < SECTOR_WIDTH; h++)
				sectors[w][h] = new Sector(w, h);
	}

	public static String getHeader() {

		return HEADER_CLASS;
	}

	public Sector[][] getSectors() {

		return sectors;
	}

	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;

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
			String header = str.substring(0, 4);

			if(header.equals(Sector.getHeader())) {
				Sector temp = new Sector(-1, -1);
				temp.unpack(str);
				
				sectors[temp.getLocX()][temp.getLocY()] = temp;
			}
			else if(!header.equals(HEADER_CLASS))
				System.out.println("Unknown Packed Info in Universe");
		}
	}

}
