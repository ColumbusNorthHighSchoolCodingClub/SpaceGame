package src.spacegame.old;

import java.util.ArrayList;
import java.util.*;

/**
 * Write a description of class BE2 here.
 * 
 * @author (Austin) 
 * @version (a version number or a date)
 */

public class BE2 {
	// instance variables - replace the example below with your own
	private static Universe theUniverse;
	Random randy = new Random();
	
	/**
	 * Constructor for objects of class BE2
	 */
	public static void BE2() {
	
		theUniverse = new Universe();
	}
	
	/**
	 * An example of a method - replace this comment with your own
	 * 
	 * @param  y   a sample parameter for a method
	 * @return     the sum of x and y 
	 */
	public void BattleOut() {
	
		for (int a = 0; a < theUniverse.NUM_OF_SECTORSX; a++) {
			for (int b = 0; b < theUniverse.NUM_OF_SECTORSY; b++) {
				if (theUniverse.getSector(a, b).ships.size() > 1)
					
					for (int x = 0; x < theUniverse.getSector(a, b).ships.size(); x++) {
						if (x + 1 <= theUniverse.getSector(a, b).ships.size()) {
							if (Roster.theRoster.get(theUniverse.getSector(a, b).ships.get(x).getPlayerNum()).getShipStats().getDefense() * randy.nextInt(4) * theUniverse.getSector(a, b).ships.get(x).getNumShips() < Roster.theRoster.get(theUniverse.getSector(a, b).ships.get(x + 1).getPlayerNum()).getShipStats().getAttack() * randy.nextInt(3) * theUniverse.getSector(a, b).ships.get(x).getNumShips()) {
								theUniverse.getSector(a, b).ships.get(x + 1).setNumShips(theUniverse.getSector(a, b).ships.get(x).getNumShips() - 1);
								if (theUniverse.getSector(a, b).ships.get(x + 1).getNumShips() <= 0)
									theUniverse.getSector(a, b).ships.remove(x + 1);
							}
							
							if (Roster.theRoster.get(theUniverse.getSector(a, b).ships.get(x).getPlayerNum()).getShipStats().getDefense() * randy.nextInt(4) * theUniverse.getSector(a, b).ships.get(x).getNumShips() > Roster.theRoster.get(theUniverse.getSector(a, b).ships.get(x + 1).getPlayerNum()).getShipStats().getAttack() * randy.nextInt(3) * theUniverse.getSector(a, b).ships.get(x).getNumShips()) {
								theUniverse.getSector(a, b).ships.get(x).setNumShips(theUniverse.getSector(a, b).ships.get(x).getNumShips() - 1);
								if (theUniverse.getSector(a, b).ships.get(x).getNumShips() <= 0)
									theUniverse.getSector(a, b).ships.remove(x);
							}
							
						}
					}
			}
		}
		
	}
	
}
