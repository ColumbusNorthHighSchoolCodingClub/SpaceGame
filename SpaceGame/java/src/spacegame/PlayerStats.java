package src.spacegame;

public class PlayerStats implements Packable {
	
	private static final String HEADER_CLASS = "STAT";
	
	private final char PARSE_CHAR = '∑';
	
	private final String headerFuelPurch = "FPUR",
			headerMatPurch = "MPUR",
			headerFuelSold = "FSLD",
			headerMatSold = "MSLD",
			headerFuelSpeed = "FSPD",
			headerMiningSpeed = "MISP",
			headerStealthStat = "STLH",
			headerSensorStat = "SENS",
			headerFuel = "FUEL",
			headerMat = "MATS",
			headerMoney = "CASH";

	//TODO: Home Planet

	private int fuelPurchased = 0, matPurchased = 0, fuelSold = 0, matSold = 0;

	private int fuelSpeed = 1, miningSpeed = 1, stealthStat = 1, sensorStat = 1;

	private int myFuel = -1, myMat = -1, myMoney = -1;

	public PlayerStats() {
	
	}
	
	public int getFuel() {
	
		return myFuel;
	}

	public int getMat() {
	
		return myMat;
	}

	public int myMoney() {
	
		return myMoney;
	}

	public void updateFuel(int amount) {
	
		this.myFuel += amount;
	}

	public void updateMat(int amount) {
	
		this.myMat += amount;
	}

	public void updateMoney(int amount) {
	
		this.myMoney += amount;
	}
	
	public void setFuel(int amount) {

		this.myFuel = amount;
	}
	
	public void setMat(int amount) {

		this.myMat = amount;
	}
	
	public void setMoney(int amount) {

		this.myMoney = amount;
	}
	
	@Override
	public String pack() {

		String pack = HEADER_CLASS + PARSE_CHAR;

		pack += headerFuel + myFuel + PARSE_CHAR;
		pack += headerMat + myMat + PARSE_CHAR;
		pack += headerMoney + myMoney + PARSE_CHAR;

		pack += headerFuelSpeed + fuelSpeed + PARSE_CHAR;
		pack += headerMiningSpeed + miningSpeed + PARSE_CHAR;
		pack += headerStealthStat + stealthStat + PARSE_CHAR;
		pack += headerSensorStat + sensorStat + PARSE_CHAR;

		pack += headerFuelPurch + fuelPurchased + PARSE_CHAR;
		pack += headerMatPurch + matPurchased + PARSE_CHAR;
		pack += headerFuelSold + fuelSold + PARSE_CHAR;
		pack += headerMatSold + matSold + PARSE_CHAR;

		return pack;
	}
	
	@Override
	public void unpack(String data) {
	
	}

}
