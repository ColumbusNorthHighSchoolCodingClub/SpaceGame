package src.spacegame.old;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Alliances implements Packable {
	// instance variables - replace the example below with your own
	public ArrayList<Boolean> alliedTo = new ArrayList<Boolean>();
	
	//Stuff for the display to work nicely
	AlliancePanel alliancePanel = new AlliancePanel();
	
	//     Thread animator;
	//     boolean animate;
	
	//The constructor.
	public Alliances() {
	
	}
	
	public boolean isAllied(int index) {
	
		if (index < alliedTo.size() && index >= 0)
			return alliedTo.get(index);
		else
			return false; //New player or a mistake (thus not allied)
	}
	
	public JPanel getPanel() {
	
		alliancePanel.setBackground(Color.BLUE);
		return alliancePanel;
	}
	
	public void updateFrame() {
	
		alliancePanel.getUpdatedFrame();
	}
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//  IMPLEMENTS PACKABLE
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	private final char PARSE_CHAR = '%'; //The parse character for this class.
	
	/**
	 * This method packs the data into a String. It loops through the planets and ships and seperates them with a parse char (§).
	 * 
	 * @param  shipString Packs all the data for all the ships into a String.
	 * @return     the entire String, all packed up.
	 */
	
	public String pack() {
	
		String Data = "ALLY" + PARSE_CHAR;
		for (int x = 0; x < alliedTo.size(); x++) {
			Data += alliedTo.get(x).toString();//Takes the alliedTo arraylist of booleans and takes each 
			Data += PARSE_CHAR;//               element and turns it into a string for the server to take from Data
			
		}
		return Data;
	}
	
	/**
	 * This method unpacks all the data from a String. It does this by seperating the String into elements using a parse char (§).
	 * 
	 * @param  inParsed A Vector with all the data parsed into elements.
	 * @param  header The header for the Vector, the first element.
	 */
	public void unpack(String data) {
	
		if (data.substring(0, 4).equals("ALLY")) {
			alliedTo.clear();
			ArrayList<String> parsed = ParseUtil.parseString(data, PARSE_CHAR);
			for (int b = 1; b < parsed.size(); b++)// start at 1 cuz the hearder is 0
			{
				if (parsed.get(b) == "true")
					alliedTo.set(b, true);
				else
					alliedTo.set(b, false);
			}//end of parsed size for loop
		}//end of header if statment   
		else
			System.out.println("parse error Alliances.unpack() ");
	}
	
	//****************************************************************************************     
	//****************************************************************************************     
	//****************************************************************************************     
	class AlliancePanel extends JPanel implements MouseListener, MouseMotionListener {
		private int row = 0; //where the mouse is at on the list
		private final int TOP_SIZE = 35;
		private final int ROW_SIZE = 30;
		
		int count = 1;
		
		public AlliancePanel() {
		
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.setPreferredSize(new Dimension(200, 600)); //Needed to add this to get display right...
		}
		
		protected void paintComponent(Graphics g) {
		
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for (int q = 0; q < Roster.getRosterSize(); q++) //Loop through the entire Roster
			{
				if (q >= alliedTo.size()) //NEW PLAYER IN THE GAME (add to my alliedTo list)(add them as enemy)
					alliedTo.add(false);
				
				//Check if allied (for color-coding)
				if (alliedTo.get(q))
					g2.setColor(Color.GREEN);
				else
					g2.setColor(Color.RED);
				
				g2.drawString("Player #" + q + " = " + Roster.getPlayer(q).getName(), 15, ROW_SIZE * q + TOP_SIZE);
			}
			g2.setColor(Color.YELLOW);
			g2.drawRect(10, TOP_SIZE + row * ROW_SIZE - ROW_SIZE, 180, ROW_SIZE);
		}
		
		public void getUpdatedFrame() {
		
			//         Debug.msg("Updating Alliances"+count++);
			repaint();
		}
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		public void mouseEntered(MouseEvent e) {
		
		} //Enable scrolling when entered.   
		
		public void mouseExited(MouseEvent e) {
		
		} //Don't scroll when exited?   
		
		public void mousePressed(MouseEvent e) {
		
		}
		
		public void mouseReleased(MouseEvent e) {
		
		}
		
		public void mouseClicked(MouseEvent e) {
		
			alliedTo.set(row, !alliedTo.get(row));
		}
		
		public void mouseDragged(MouseEvent e) {
		
		}
		
		public void mouseMoved(MouseEvent e) {
		
			int mouseX = e.getX();
			if (mouseX > 0 && mouseX < 200) {
				row = (e.getY() - TOP_SIZE) / ROW_SIZE;
				if (row >= Roster.getRosterSize())
					row = Roster.getRosterSize() - 1;
			}
			else
				row = ClientMain.myPlayerNum;
		}
		
	}//--- end of UniversePanel 'sub'-class ---
	
	//****************************************************************************************     
	//****************************************************************************************     
	//****************************************************************************************     
	
}//--- end of Alliances class ---

