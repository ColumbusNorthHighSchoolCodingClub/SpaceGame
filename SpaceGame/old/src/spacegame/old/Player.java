package src.spacegame.old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.net.InetAddress;
import java.util.Vector;
import javax.swing.JPanel;

/**
 * This class maintains each player's information and abilities.
 *
 * @author Scott Skiles
 * @version RC 1
 */
public class Player implements Packable {
	
	public String name = "???"; //MOVED: ClientInfo
	private ShipStats myShip; //MOVED: ClientInfo
	private Technology myTech;
	private Alliances myAllies; //MOVED: ClientInfo
	private final char parseChar = '@';
	private int myFuel, myMaterials, myMoney = 0; //MOVED: ClientInfo
	public final String ip = getIpString(); //MOVED: ClientInfo
	
	//--I added these to get access to the big3 game objects that a Player object stores.  (Spock0303)
	public ShipStats getShipStats() {
	
		return myShip;
	}
	
	public Technology getTechnology() {
	
		return myTech;
	}
	
	public Alliances getAlliances() {
	
		return myAllies;
	}
	
	/**
	 * Constructor for objects of class Player
	 */
	public Player() {
	
		//Needed to at least non-null the game objects (spock0303)
		myShip = new ShipStats();
		myTech = new Technology();
		myAllies = new Alliances();
		
		//
		setVariables();
		myFuel = 500;
		myMaterials = 1000;
		myMoney = 750;
		//         myStaticFuel=500; myStaticMaterials=1000; myStaticMoney=750;
		//         numPlayers++;
		//         playerNumber=numPlayers;
	}
	
	/**
	 * This method is worthless and does nothing in particular.
	 */
	private void setVariables() {
	
		myFuel = 500;
		myMaterials = 1000;
		myMoney = 750;
		//         myStaticFuel=500; myStaticMaterials=1000; myStaticMoney=750;
	}

	/**
	 * Returns that Player Panel which displays all pertinent info.
	 *
	 * @return          the panel containing variable labels.
	 */
	public JPanel getPanel() {
	
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(190, 155));
		panel.add(new Label("Name: " + getName()));
		panel.add(new Label("IP Address " + ip));
		panel.add(new Label("Fuel: " + myFuel));
		panel.add(new Label("Materials: " + myMaterials));
		panel.add(new Label("Money: " + myMoney));
		panel.add(new Label("Player Number: " + ClientMain.myPlayerNum));
		panel.setBackground(Color.ORANGE);
		
		return panel;
	}
	
	/**
	 * This method packs all variable data into a string to be sent out.
	 *
	 * @return          the String containing all the data separated by the '@' char.
	 */
	@Override
	public String pack() {
	
		String packed = new String("PLAY");
		packed += parseChar + myShip.pack() + parseChar + myTech.pack() + parseChar + myAllies.pack() + parseChar + name + parseChar + myFuel + parseChar + myMaterials + parseChar + myMoney + parseChar;
		return packed;
	}
	
	/**
	 * This method separates an input string into variable data using '@' as the parseChar.
	 *
	 * @param data   the input String.
	 */
	@Override
	public void unpack(String data) {
	
		Vector inParsed = ParseUtil.parseStringBySign(data, parseChar);
		String header = (String) inParsed.elementAt(0);
		if(header.equals("PLAY")) {
			myShip.unpack((String) inParsed.elementAt(1));
			myTech.unpack((String) inParsed.elementAt(2));
			myAllies.unpack((String) inParsed.elementAt(3));
			name = (String) inParsed.elementAt(4);
			myFuel = Integer.parseInt((String) inParsed.elementAt(5));
			myMaterials = Integer.parseInt((String) inParsed.elementAt(6));
			myMoney = Integer.parseInt((String) inParsed.elementAt(7));
			//             playerNumber = Integer.parseInt((String)inParsed.elementAt(5));
		}
		else
			System.out.println("parse error Player.unpack() ");
	}

	/**
	 * This method gets the IP Address as a String.
	 *
	 * @return          the String representation of the IP Address.
	 */
	private String getIpString() {
	
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ipAddr = addr.getHostAddress();
			return ipAddr;
		}
		catch(java.net.UnknownHostException e) {
		}
		return null;
	}

	/**
	 * This method gets the IP Address as a String.
	 *
	 * @return          the String representation of the IP Address.
	 */
	public String getHostname() {
	
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ipAddr = addr.getHostName();
			return ipAddr;
		}
		catch(java.net.UnknownHostException e) {
		}
		return null;
	}

	public String getName() {
	
		return name;
	}
	
	/**
	 * This method sets the name of a player.
	 *
	 * @param newName   the desired name.
	 */
	public void setName(String newName) {
	
		name = newName;
	}
	
	/**
	 * This method gets the amount of fuel a player has.
	 *
	 * @return          the value of myFuel.
	 */
	public int getFuel() {
	
		return myFuel;
	}
	
	/**
	 * This method sets the amount of fuel a player has.
	 *
	 * @param newFuel   the desired amount of fuel.
	 */
	public void setFuel(int newFuel) {
	
		myFuel = newFuel;
	}
	
	/**
	 * This method gets the amount of materials a player has.
	 *
	 * @return          the value of myMaterials.
	 */
	public int getMaterials() {
	
		return myMaterials;
	}
	
	/**
	 * This method sets the amount of materials a player has.
	 *
	 * @param newMaterials   the desired amount of materials.
	 */
	public void setMaterials(int newMaterials) {
	
		myMaterials = newMaterials;
	}
	
	/**
	 * This method gets the amount of money a player has.
	 *
	 * @return          the value of myMoney.
	 */
	public int getMoney() {
	
		Debug.msg("" + myMoney);
		return myMoney;
	}
	
	/**
	 * This method sets the amount of money a player has.
	 *
	 * @param newMoney   the desired amount of money.
	 */
	public void setMoney(int newMoney) {
	
		myMoney = newMoney;
		Debug.msg("new money = " + newMoney);
	}

}
