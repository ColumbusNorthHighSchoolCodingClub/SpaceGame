package src.spacegame;

import java.awt.Color;

public enum PlanetType {
	VERONIAN, ADONIS, ERIS, FIERY, TIERRAN, THESBAN,
	TOMEKO, RING, CRACK, TITAN, STEALTH, SPARTAN,
	IONIS, PEYO, RIEHL, SARENA, XIAN, BETHELLEN,
	MIRRIAN, ADANMA;
	
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
