package src.spacegame.old;

//     Austin F.   SERVER  calculateScore 
//     Add to each player’s supplies based on 
//     the planets that they control. 

//(planetNum*10 + shipNo)*techLevel+materials/10+fuel/10
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class calculateScore {
	
	public ArrayList calcScore() {
	
		ArrayList<Integer> scoreBoard = new ArrayList<Integer>();
		
		for (int p = 0; p < Roster.theRoster.size(); p++) {
			int fuelComp = 0;
			int materialComp = 0;
			int planetComp = 0;
			int techCompPlanet = 0;
			int techCompShip = 0;
			int shipComp = 0;
			int scoredNow = 0;
			
			materialComp = Roster.getPlayer(p).getMaterials();
			fuelComp = Roster.getPlayer(p).getFuel();
			
			for (int a = 0; a < Universe.NUM_OF_SECTORSX; a++) {
				for (int b = 0; b < Universe.NUM_OF_SECTORSY; b++) {
					Sector[][] sect = Universe.getSectors();
					//					if(sect[a][b].hasPlanet == true)
					//					{
					//						if( theRoster(sect[a][b].planet.getControlledBy()) == a)
					//							planetComp ++;
					//					}
				}
			}
			//			techCompPlanet += Roster.theRoster.get(p).getTechnology(fuelSpeed);
			//			techCompPlanet +=   Roster.theRoster.get(p).getTechnology(mineSpeed);
			//			
			//			techCompShip +=   Roster.theRoster.get(p).getTechnology(stealth);
			//			techCompShip +=  Roster.theRoster.get(p).getTechnology(sensors);
			
			// shipComp += theRoster(a).getShips ??!!??
			scoredNow = materialComp / 100 + fuelComp / 100 + planetComp * 10 + techCompPlanet + techCompShip + shipComp / 20;
			
			if (scoreBoard.size() < p)
				
				scoreBoard.add(p, scoredNow);
			
			else
				scoreBoard.set(p, scoredNow);
		}
		
		return scoreBoard;
	}
}
