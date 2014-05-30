package src.spacegame;

import java.awt.Color;

public enum PlanetType {

	VERONIAN(0.001D, Color.ORANGE, 0, -40, 5000, 5000),
	ADONIS(0.006D, Color.GREEN.brighter(), 0, -40, 5000, 5000),
	ERIS(0.015D, Color.BLUE.darker(), 0, -40, 5000, 5000),
	FIERY(0.020D, Color.RED.darker(), 0, -40, 5000, 5000),
	TIERRAN(0.075D, Color.GREEN.darker(), 0, -40, 5000, 5000),
	THESBAN(0.100D, Color.MAGENTA, 0, -40, 5000, 5000),
	TOMEKO(0.100D, Color.GRAY, 0, -40, 5000, 5000),
	RING(0.200D, Color.BLUE.brighter(), 0, -40, 5000, 5000),
	CRACK(0.515D, new Color(184, 115, 51), 0, -40, 5000, 5000),
	TITAN(0.015D, Color.DARK_GRAY, 0, -40, 5000, 5000),
	STEALTH(0.030D, new Color(20, 20, 20), 0, -40, 5000, 5000),
	SPARTAN(0.050D, Color.RED, 0, -40, 5000, 5000),
	IONIS(0.040D, Color.LIGHT_GRAY, 0, -40, 5000, 5000),
	PEYO(0.045D, Color.BLUE, 0, -40, 5000, 5000),
	RIEHL(0.050D, Color.PINK, 0, -40, 5000, 5000),
	SARENA(0.250D, Color.ORANGE.darker(), 0, -40, 5000, 5000);

	private final Color color;
	private final double rarity;
	private final int baseOffense;
	private final int baseDefense;
	private final int baseFuel;
	private final int baseMat;

	//TODO: Remove
	PlanetType() {
	
		this(.1, Color.CYAN, 0, 0, 0, 0);
	}

	PlanetType(double rarity, Color color, int baseOff, int baseDef, int baseFuel, int baseMat) {
	
		this.rarity = rarity;
		this.color = color;

		baseOffense = baseOff;
		baseDefense = baseDef;
		this.baseFuel = baseFuel;
		this.baseMat = baseMat;
	}

	public double getRarity() {
	
		return rarity;
	}

	public Color getColor() {
	
		return color;
	}

	/**
	 * The Planet's Offensive Stat
	 * @return offense
	 */
	public int getBaseOffense() {
	
		return baseOffense;
	}

	/**
	 * The Planet's Defensive Stat
	 * @return offense
	 */
	public int getBaseDefense() {
	
		return baseDefense;
	}

	/**
	 * The Planet's Fuel Capacity Stat
	 * @return offense
	 */
	public int getBaseFuel() {
	
		return baseFuel;
	}

	/**
	 * The Planet's Material Capacity Stat
	 * @return offense
	 */
	public int getBaseMat() {
	
		return baseMat;
	}
}
