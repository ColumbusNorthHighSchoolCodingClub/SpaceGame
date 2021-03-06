package src.spacegame.old;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;

//XXX: Done Remaking

/**
 * Stores a sector, including its planet and any ships in the sector. Will also
 * draw the sector. Go ahead and leave comments if you want something.
 *
 * @author Matthew Muckley
 * @version 0.1
 */
@Deprecated
public class Sector implements Packable {

	Random randy = new Random();
	TextField inputX = new TextField("Enter target X");
	TextField inputY = new TextField("Enter target Y");
	Button confirmButton = new Button("GO!");
	Planet planet;
	ArrayList<Ships> ships;
	JPanel movePanel = moveShipsFrame();

	private boolean planetExists = false;
	private boolean contested = false;

	public boolean hasPlanet() {

		if(planetExists == true)
			return true;
		else
			return false;
	}

	/**
	 * Initializes the planet and the ships in the sector.
	 */
	public Sector() {

		ships = new ArrayList<Ships>();
	}

	/**
	 * Creates a Sector, randomly deciding whether it has a planet.
	 */

	public void createRandom() {

		if(randy.nextInt(2) == 0) {
			planet = new Planet();
			planetExists = true;
		}
	}

	/**
	 * Creates a panel in which a player can input a destination for his ships.
	 *
	 * @return the panel
	 */
	public JPanel moveShipsFrame() {

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(190, 155));
		panel.add(inputX);
		inputX.addActionListener(new GO_AL());
		panel.add(inputY);
		inputY.addActionListener(new GO_AL());
		panel.add(confirmButton);
		confirmButton.addActionListener(new GO_AL());
		return panel;
	}

	/**
	 * Returns the panel created by this Sector.
	 */

	public JPanel getPanel() {

		return movePanel;
	}

	public ArrayList<Ships> getShips() {

		return ships;
	}

	public Ships getMyShips(int player) {

		for(int x = 0; x < ships.size(); x++)
			if(ships.get(x).getPlayerNum() == player)
				return ships.get(x);
		return null;
	}

	/**
	 * This method draws the sector as box with width WIDTH and height HEIGHT.
	 */
	public void drawSector(Graphics g, int WIDTH, int HEIGHT, int x, int y, int zoomFactor, JPanel p) {

		g.setColor(Color.BLACK);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(playerColor());
		g.drawRect(x, y, WIDTH, HEIGHT);
		if(planetExists)
			planet.wantSmallPlanetFrame(g, x, y, zoomFactor, p);
		if(!ships.isEmpty()) {
			//             Debug.msg("Drawing Ships");
			for(int h = 0; h < ships.size(); h++)
				ships.get(h).drawShip(g, x, y, zoomFactor, h);
		}
	}

	/**
	 * MOVED: PlanetType
	 */
	private Color playerColor() {

		if(planetExists) {
			if(planet.getControlledBy() == -1)
				return Color.GRAY;
			if(planet.getControlledBy() == 0)
				return Color.PINK;
			if(planet.getControlledBy() == 1)
				return Color.CYAN;
			if(planet.getControlledBy() == 2)
				return Color.BLUE;
			if(planet.getControlledBy() == 3)
				return Color.YELLOW;
			if(planet.getControlledBy() == 4)
				return Color.MAGENTA;
			if(planet.getControlledBy() == 5)
				return Color.GREEN;
			if(planet.getControlledBy() == 6)
				return Color.ORANGE;
			if(planet.getControlledBy() == 7)
				return Color.WHITE;
		}
		if(contested)
			return Color.RED;
		return Color.GRAY;
	}

	private final char PARSE_CHAR = '#';

	/**
	 * This method packs the data into a String. It loops through the planets and ships and seperates them with a parse char (�).
	 *
	 * @param  shipString Packs all the data for all the ships into a String.
	 * @return     the entire String, all packed up.
	 */
	@Override
	public String pack() {

		String shipString = "";// + ships.size() + PARSE_CHAR;
		for(int h = 0; h < ships.size(); h++)
			shipString += ships.get(h).pack() + PARSE_CHAR;

		String output = "SECT" + PARSE_CHAR;

		if(planetExists)
			output += planet.pack() + PARSE_CHAR;

		output += shipString;

		return output;
	}

	/**
	 * This method unpacks all the data from a String. It does this by seperating the String into elements using a parse char (�).
	 *
	 * @param  inParsed A Vector with all the data parsed into elements.
	 * @param  header The header for the Vector, the first element.
	 */
	@Override
	public void unpack(String data) {

		//         Debug.msg("Unpacking a SECTOR!");
		int shipsSize = 0;
		Vector inParsed = ParseUtil.parseStringBySign(data, PARSE_CHAR);
		String header = (String) inParsed.elementAt(0);
		if(header.equals("SECT")) {
			planetExists = false; //Assume no planet until you know otherwise.
			ships.clear(); //Assume no ships until otherwise.

			for(int z = 1; z < inParsed.size(); z++) {
				String parseData = (String) inParsed.elementAt(z);
				String subHeader = parseData.substring(0, 4);

				if(subHeader.equals("PLAN")) {
					planet = new Planet();
					planetExists = true;
					planet.unpack((String) inParsed.elementAt(z));
				}
				else if(subHeader.equals("SHIP")) {
					//                     Debug.msg("Unpacking a Ships!");
					ships.add(new Ships());
					ships.get(ships.size() - 1).unpack(parseData);
				}
				else
					Debug.msg("Got JUNK? in unpack Sector : " + parseData);
			}
		}
	}//--end of unpack() method--

	@Override
	public String toString() {

		String output = "Sector: ";

		if(planetExists)
			output += "has Planet !";

		for(int z = 0; z < ships.size(); z++)
			output += "ships (" + ships.get(z).numShips + "," + ships.get(z).playerNum + ") ";

		return output;
	}

	//             String header2 = (String)inParsed.elementAt(1);
	//             if (header2.substring(0, 3).equals("PLAN"))
	//             {
	//                 planet.unpack((String)inParsed.elementAt(1));
	//                 shipsSize = (Integer)inParsed.elementAt(2);
	//             }
	//             else
	//                 shipsSize = (Integer)inParsed.elementAt(1);
	//
	//             if (shipsSize > 0)
	//                 {
	//                     ships.clear();
	//                     for (int h=0; h<(Integer)inParsed.elementAt(1); h++)
	//                     {
	//                         ships.add(new Ships(0, 0));
	//                         ships.get(h).unpack((String)inParsed.elementAt(2 + h));
	//                     }
	//                 }
	//             if ((Integer)inParsed.elementAt(2) > 0)
	//             {
	//                 ships.clear();
	//                 for (int h=0; h<(Integer)inParsed.elementAt(2); h++)
	//                 {
	//                     ships.add(new Ships(0, 0));
	//                     ships.get(h).unpack((String)inParsed.elementAt(3 + h));
	//                 }
	//             }
	//         }
	//     }

	class GO_AL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				for(int h = 0; h < ships.size(); h++)
					if(ships.get(h).getPlayerNum() == ClientMain.myPlayerNum) {
						ships.get(h).setXDest(Integer.parseInt(inputX.getText()));
						ships.get(h).setYDest(Integer.parseInt(inputY.getText()));
						GenericComm.addToOutbox(ships.get(h).pack());
					}
			}
			catch(Exception NumberFormatException) {
			}
		}
	}
}