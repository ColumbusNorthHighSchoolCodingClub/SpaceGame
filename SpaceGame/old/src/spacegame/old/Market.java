package src.spacegame.old;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The economy aspect of the game such as the price of fuel which the ships 
 * require and materials which are used for building stuff.
 * 
 * @author Daniel Robinson
 * @version 3/28/07
 */

public class Market extends JPanel implements Packable {
	
	//fuel
	private int fuelPrice;
	private int rateOfFuelBought;
	private int rateOfFuelSold;
	//materials
	private int materialsPrice;
	private int rateOfMaterialsBought;
	private int rateOfMaterialsSold;
	//constants
	private final char PARSE_CHAR = '*';
	private final int INCREASE_FUEL = 50;
	private final int DECREASE_FUEL = 50;
	Player currentPlayer = Roster.getPlayer(ClientMain.myPlayerNum);
	
	/**
	 * Contructor for Market
	 */
	public Market() {
	
		//fuel
		fuelPrice = 100;
		rateOfFuelBought = 0;
		rateOfFuelSold = 0;
		//materials
		materialsPrice = 100;
		rateOfMaterialsBought = 0;
		rateOfMaterialsSold = 0;
	}
	
	/**
	 * Pack puts all the data that needs to be sent into one
	 * line of text within a String.
	 * @return packedData
	 */
	public String pack() {
	
		String packedData;
		packedData = "MARK" + PARSE_CHAR + fuelPrice + PARSE_CHAR + materialsPrice + PARSE_CHAR;
		
		return packedData;
	}
	
	/**
	 * Unpack takes the packed data and parses it for 
	 * important data. 
	 * @param in - String that is to be unpacked
	 */
	public void unpack(String in) {
	
		ArrayList<String> parsedInput = ParseUtil.parseString(in, PARSE_CHAR);
		String header = ParseUtil.getHeader(in, PARSE_CHAR);
		if (header.equals("MARK")) {
		}
		else if (header.equals("BUYY")) {
			//     int type = Integer.parseInt((String)parsedInput.elementAt(1));
			
		}
		else
			Debug.msg("Can't unpack Market data");
	}
	
	public String packPurchase(String header, int type) {
	
		String packedData;
		packedData = header + PARSE_CHAR + type + PARSE_CHAR;
		return packedData;
	}
	
	JButton buyFuelButton = new JButton("Buy Fuel");
	JButton sellFuelButton = new JButton("Sell Fuel");
	JButton buyMaterialsButton = new JButton("Buy Materials");
	JButton sellMaterialsButton = new JButton("Sell Materials");
	JLabel fuelPriceLabel = new JLabel("Fuel Price: " + fuelPrice);
	JLabel materialsPriceLabel = new JLabel("Materials Price: " + materialsPrice);
	
	/**
	 * The frame that displays the current market. 
	 * @return panel
	 */
	public JPanel marketFrame() {
	
		JPanel panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(190, 155));
		//set background colors of buttons to blue
		buyFuelButton.setBackground(Color.BLUE);
		sellFuelButton.setBackground(Color.BLUE);
		buyMaterialsButton.setBackground(Color.BLUE);
		sellMaterialsButton.setBackground(Color.BLUE);
		//adds labels showing fuel and materials prices
		panel.add(fuelPriceLabel);
		panel.add(materialsPriceLabel);
		//adds buttons
		panel.add(buyFuelButton);
		buyFuelButton.addActionListener(new BuyFuelButtonAL());
		panel.add(sellFuelButton);
		sellFuelButton.addActionListener(new SellFuelButtonAL());
		panel.add(buyMaterialsButton);
		buyMaterialsButton.addActionListener(new BuyMaterialsButtonAL());
		panel.add(sellMaterialsButton);
		sellMaterialsButton.addActionListener(new SellMaterialsButtonAL());
		
		panel.setBackground(Color.CYAN);
		
		return panel;
	}
	
	/**
	 * Action Listener for buyFuelButton button
	 * Calls buyFuel()
	 */
	private class BuyFuelButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent av) {
		
			buyFuel();
			updatePrice(rateOfFuelBought, rateOfFuelSold, fuelPrice); //updates price of fuel after every transaction
			fuelPriceLabel.setText("Fuel Price: " + fuelPrice);
		}
	}
	
	/**
	 * Action Listener for sellFuelButton button
	 * Calls sellFuel()
	 */
	private class SellFuelButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent av) {
		
			sellFuel();
			updatePrice(rateOfFuelBought, rateOfFuelSold, fuelPrice);
			fuelPriceLabel.setText("Fuel Price: " + fuelPrice);
		}
	}
	
	/**
	 * Action Listener for buyMaterialsButton
	 * Calls buyMaterials()
	 */
	private class BuyMaterialsButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent av) {
		
			buyMaterials();
			updatePrice(rateOfMaterialsBought, rateOfMaterialsSold, materialsPrice); //updates price of materials after every transaction
			materialsPriceLabel.setText("Materials Price: " + materialsPrice);
		}
	}
	
	/**
	 * Action Listener for sellMaterialsButton
	 * Calls sellMaterials()
	 */
	private class SellMaterialsButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent av) {
		
			sellMaterials();
			updatePrice(rateOfMaterialsBought, rateOfMaterialsSold, materialsPrice);
			materialsPriceLabel.setText("Materials Price: " + materialsPrice);
		}
	}
	
	/**
	 * Updates the current price of something according to the buy and sell rates
	 * @param buyRate the rate at which fuel or materials is bought
	 * @param sellRate the rate at which fuel or material is sold
	 * @param currentPrice the current price of something on the market
	 * @return currentPrice the updated current price 
	 */
	private int updatePrice(int buyRate, int sellRate, int currentPrice) {
	
		if (buyRate > sellRate) //raises current price if buyrate is greater than sellrate
		{
			currentPrice = currentPrice + 5;
		}
		else if (buyRate < sellRate) //does the opposite if buyrate is less than sellRate
		{
			currentPrice = currentPrice - 5;
		}
		else {
		}
		return currentPrice;
	}
	
	/**
	 * Subtracts money from player and adds fuel to player
	 */
	public void buyFuel() {
	
		//         Player.setStaticFuel(Player.getStaticFuel()+INCREASE_FUEL);
		//         rateOfFuelBought = rateOfFuelBought+1;
		//         Player.setStaticMoney(Player.getStaticMoney() - fuelPrice);
		currentPlayer.setFuel(currentPlayer.getFuel() + INCREASE_FUEL);
		rateOfFuelBought = rateOfFuelBought + 1;
		currentPlayer.setMoney(currentPlayer.getMoney() - fuelPrice);
		GenericComm.addToOutbox(packPurchase("BUYY", 0));
		
	}
	
	/**
	 * Subtracts money from player and adds materials to player
	 */
	public void buyMaterials() {
	
		//         Player.setStaticMaterials(Player.getStaticMaterials()+50);
		//         rateOfMaterialsBought = rateOfMaterialsBought+1;
		//         Player.setStaticMoney(Player.getStaticMoney() - materialsPrice);
		currentPlayer.setMaterials(currentPlayer.getMaterials() + 50);
		rateOfMaterialsBought = rateOfMaterialsBought + 1;
		currentPlayer.setMoney(currentPlayer.getMoney() - materialsPrice);
		
	}
	
	/**
	 * Adds money to player and subtracts fuel from player
	 */
	public void sellFuel() {
	
		//         Player.setStaticFuel(Player.getStaticFuel()-DECREASE_FUEL);
		//         rateOfFuelSold = rateOfFuelSold+1;
		//         Player.setStaticMoney(Player.getStaticMoney() + fuelPrice);
		currentPlayer.setFuel(currentPlayer.getFuel() - DECREASE_FUEL);
		rateOfFuelSold = rateOfFuelSold + 1;
		currentPlayer.setMoney(currentPlayer.getMoney() + fuelPrice);
	}
	
	/**
	 * Adds money to player and subtracts materials from player
	 */
	public void sellMaterials() {
	
		//         Player.setStaticMaterials(Player.getStaticMaterials()-50);
		//         rateOfMaterialsSold = rateOfMaterialsSold+1;
		//         Player.setStaticMoney(Player.getStaticMoney() + materialsPrice);
		currentPlayer.setMaterials(currentPlayer.getMaterials() - 50);
		rateOfMaterialsSold = rateOfMaterialsSold + 1;
		currentPlayer.setMoney(currentPlayer.getMoney() + materialsPrice);
		
	}
	
	/**
	 *  Accessor for fuelPrice
	 *  @return     fuelPrice
	 */
	public int getFuelPrice() {
	
		return fuelPrice;
	}
	
	/**
	 * Accessor for materialsPrice
	 * @return  materialsPrice
	 */
	
	public int getMaterialsPrice() {
	
		return materialsPrice;
	}
	
	/**
	 * Modifier for fuelPrice
	 * @param   newPrice
	 */
	public void setFuelPrice(int newPrice) {
	
		fuelPrice = newPrice;
	}
	
	/**
	 * Modifier for materialsPrice
	 * @param   newPrice
	 */
	public void setMaterialsPrice(int newPrice) {
	
		materialsPrice = newPrice;
	}
}