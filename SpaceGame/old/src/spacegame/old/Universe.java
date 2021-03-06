package src.spacegame.old;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@Deprecated
public class Universe implements Packable {
	//Data about the Universe!
	public static final int NUM_OF_SECTORSX = 10;
	public static final int NUM_OF_SECTORSY = 10;
	private static Sector sectors[][] = new Sector[NUM_OF_SECTORSX][NUM_OF_SECTORSY]; //I NEED AN ACESSOR FOR THIS!!!! Austin

	private int selectedSectorX = 0;
	private int selectedSectorY = 0;
	private boolean sectorSelected = false;

	//Stuff for the display to work nicely
	UniversePanel universePanel = new UniversePanel();
	Thread animator;
	boolean animate;

	//The constructor.
	public Universe() {

		for(int x = 0; x < NUM_OF_SECTORSX; x++)
			for(int y = 0; y < NUM_OF_SECTORSY; y++)
				sectors[x][y] = new Sector();

		//         universePanel = new UniversePanel();
		//         start(); //Start the Runnable Thread to keep updating the look of the universe based on mouse input.
	}

	public Sector getSector(int x, int y) {

		if(x < NUM_OF_SECTORSX && x >= 0 && y < NUM_OF_SECTORSY && y >= 0)
			return sectors[x][y];
		else
			return new Sector();
	}

	public static Sector[][] getSectors() {

		return sectors;
	}

	public void createRandom() {

		for(int x = 0; x < NUM_OF_SECTORSX; x++)
			for(int y = 0; y < NUM_OF_SECTORSY; y++)
				sectors[x][y].createRandom();
	}

	public JPanel getUniversePanel() {

		return universePanel;
	}

	public void updateFrame() {

		universePanel.getUpdatedFrame();
	}

	private final char PARSE_CHAR = '*';

	/**
	 * This method packs the data into a String. It loops through the planets and ships and seperates them with a parse char (�).
	 *
	 * @param  shipString Packs all the data for all the ships into a String.
	 * @return     the entire String, all packed up.
	 */
	@Override
	public String pack() {

		String theThingToPack = "UNIV" + PARSE_CHAR;

		for(int x = 0; x < NUM_OF_SECTORSX; x++) {
			for(int y = 0; y < NUM_OF_SECTORSY; y++) {
				theThingToPack += sectors[x][y].pack() + PARSE_CHAR;
			}
		}

		return theThingToPack;
	}

	/**
	 * This method unpacks all the data from a String. It does this by seperating the String into elements using a parse char (�).
	 *
	 * @param  inParsed A Vector with all the data parsed into elements.
	 * @param  header The header for the Vector, the first element.
	 */
	@Override
	public void unpack(String data) {

		Vector inParsed = ParseUtil.parseStringBySign(data, PARSE_CHAR);
		String header = (String) inParsed.elementAt(0);
		if(header.equals("UNIV")) {
			for(int x = 0; x < NUM_OF_SECTORSX; x++) {
				for(int y = 0; y < NUM_OF_SECTORSY; y++) {
					//                      if(x==0)
					//                         Debug.msg("about to unpack sector(0,"+y+"): "+(String)inParsed.elementAt(x*NUM_OF_SECTORSX+y));
					sectors[x][y].unpack((String) inParsed.elementAt(x * NUM_OF_SECTORSY + y + 1));
					//                      if(x==0)
					//                         Debug.msg("Done unpacking 0,"+y+" == "+sectors[x][y].toString());
				}
			}
		}
	}

	public JPanel getNorthPanel()//
	{

		//This creates the slider Panel for the top of the display.
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 6, universePanel.zoomSlider);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		//slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int value = slider.getValue();
				universePanel.zoomSlider = value;//provides value of zoom factor
			}
		});

		final JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 6, universePanel.scrollSlider);
		slider2.setPaintTicks(true);
		slider2.setMajorTickSpacing(1);
		//slider.setPaintLabels(true);
		slider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int value2 = slider2.getValue();
				universePanel.scrollSlider = value2;//provides value of zoom factor
			}
		});

		JPanel panel = new JPanel();
		panel.add(new Label("Zoom Factor: "));
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.add(slider);
		panel.add(new Label("Scroll Speed: "));
		panel.add(slider2);
		return panel;
	}

	class UniversePanel extends JPanel implements MouseListener, MouseMotionListener {
		public int zoomSlider = 3;
		public int scrollSlider = 3;
		private final int bottomX = 760;//will change according to my panel
		private final int bottomY = 410;//will change according to my panel
		int changingY = 0;//top left corner of the universe is what scrolls around
		int changingX = 0;// i may need to change the code to make zooming more reliable
		private int mouseX = 0, mouseY = 0;//where the mouse is at every moment in the game
		int destinationX;
		int destinationY;
		boolean moveable;

		public UniversePanel() {

			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.setPreferredSize(new Dimension(800, 600)); //Needed to add this to get display right...
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for(int x = 0; x < NUM_OF_SECTORSX; x++) {
				for(int y = 0; y < NUM_OF_SECTORSY; y++) {
					sectors[x][y].drawSector(g, 50 * zoomSlider, 50 * zoomSlider, changingX + ((50 * zoomSlider) * x), changingY + ((50 * zoomSlider) * y), zoomSlider, universePanel);
					//

					//                  drawSector(g,50*zoomSlider,50*zoomSlider,changingX+((50*zoomSlider)*x),changingY+((50*zoomSlider)*y));
					//this is where i will have to integrate with muckley and the stuff that he's doing
					g2.drawString("@ " + x + "," + y, (int) (changingX + ((50 * zoomSlider) * (x + 0.5))), (int) (changingY + ((50 * zoomSlider) * (y + 0.5))));
				}
			}
			g2.drawString("x: " + mouseX, 5, 120);
			g2.drawString("y: " + mouseY, 5, 130);
			g2.drawString("zoomSlider: " + zoomSlider, 5, 140);
			g2.drawString("zoomSlider: " + scrollSlider, 5, 150);
			g2.drawString("changing x: " + changingX, 5, 160);
			g2.drawString("changing y: " + changingY, 5, 170);
			g2.drawString("moveable: " + moveable, 5, 180);
		}

		//     public void drawSector(Graphics page,int width, int height,int XLoc,int YLoc)
		//     {
		//         page.setColor(Color.BLUE);
		//         page.drawRect(XLoc,YLoc,width,height);
		//     }

		public void getUpdatedFrame() {

			if(moveable)
				slideAcross();
			repaint();
		}

		private void slideAcross() {

			if(mouseX >= bottomX)
				changingX -= scrollSlider * 2;//offsets the animation left five pixels
			if(mouseY >= bottomY)
				changingY -= scrollSlider * 2;//offsets the animation up five pixels
			if(mouseX <= 30)
				changingX += scrollSlider * 2;//offsets the animation right five pixels
			if(mouseY <= 30)
				changingY += scrollSlider * 2;//offsets the animation down five pixels

			//zooming works well but there could be problems because the drwing commences at the
			//top right corner. this causes awkward problems when zomming in and out such as when
			//completely zoomed out when you zoom out you move more and more towards the bottom right

			if(changingX <= -NUM_OF_SECTORSX * 50 * zoomSlider)
				changingX = -NUM_OF_SECTORSX * 50 * zoomSlider;//for now these two keep the box from moving off the screen
			if(changingY <= -NUM_OF_SECTORSY * 50 * zoomSlider)
				changingY = -NUM_OF_SECTORSY * 50 * zoomSlider;//eventually will need an offset of the entire thing to see
			//how far over until only the bottom parts are showing
			if(changingX >= 5)
				changingX = 5;//for now these two keep the box from moving off the screen
			if(changingY >= 0)
				changingY = 0;//eventually will need an offset of the entire thing to see
			//how far over until only the bottom parts are showing

		}

		@Override
		public void mouseEntered(MouseEvent e) {

			moveable = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {

			moveable = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {

			destinationX = e.getX();
			destinationY = e.getY();
			if(e.getButton() == MouseEvent.BUTTON1) //Muckley was here
			{
				selectedSectorX = mouseX / (50 * zoomSlider);
				selectedSectorY = mouseY / (50 * zoomSlider);
				sectorSelected = true;

				Debug.msg("(x,y)=(" + selectedSectorX + "," + selectedSectorY + ") " + sectors[selectedSectorX][selectedSectorY].toString());
			}
			if(e.getButton() == MouseEvent.BUTTON3 && sectorSelected == true) {
				if(selectedSectorX < NUM_OF_SECTORSX && selectedSectorY < NUM_OF_SECTORSY)
					if(sectors[selectedSectorX][selectedSectorY].getMyShips(ClientMain.myPlayerNum) != null)
						sectors[selectedSectorX][selectedSectorY].getMyShips(ClientMain.myPlayerNum).setDest(mouseX / (50 * zoomSlider), mouseY / (50 * zoomSlider));
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {

			mouseX = e.getX();
			mouseY = e.getY();
		}

	}

}
