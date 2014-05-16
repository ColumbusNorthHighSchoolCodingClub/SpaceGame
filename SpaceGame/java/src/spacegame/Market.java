package src.spacegame;

public class Market implements Packable {
	
	private static final String HEADER_CLASS = "MARK";
	
	private final char PARSE_CHAR = '*';
	
	private final int fuelMultiplier = 50, matMultiplier = 50;
	
	public Market() {

	}
	
	public static String getHeader() {

		return HEADER_CLASS;
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
