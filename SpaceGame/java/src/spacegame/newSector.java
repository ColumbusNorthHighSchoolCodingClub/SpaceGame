package src.spacegame;    
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;
/**
 * Stores a sector, including its planet and any ships in the sector. Will also
 * draw the sector. Go ahead and leave comments if you want something.
 * 
 * @author Matthew Muckley 
 * @version 0.1
 */
public class newSector implements Packable
{

    //////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Variables //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    
    Random randy = new Random();
    TextField inputX = new TextField("Enter target X");
    TextField inputY = new TextField("Enter target Y");
    Button confirmButton = new Button("GO!");
    Planet planet;
    ArrayList<Ships> ships;
    JPanel movePanel = moveShipsFrame();
    
    private boolean planetExists = false;
    private boolean contested = false;
    
    public boolean hasPlanet() {if (planetExists == true) return true;
                                    else return false;}
    
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////// Constructor /////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Initializes the planet and the ships in the sector.
     */
    public newSector()
    {
        ships = new ArrayList<Ships>();
    }
    
    /**
     * Creates a Sector, randomly deciding whether it has a planet.
     */
    
    public void createRandom()
    {
        if (randy.nextInt(2) == 0)
        {
            planet = new Planet();
            planetExists = true;
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Panels, Drawing ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a panel in which a player can input a destination for his ships.
     * 
     * @return the panel
     */
    public JPanel moveShipsFrame()
    {
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
    
    public JPanel getPanel()
    {
        return movePanel;
    }
    public ArrayList<Ships> getShips()
    {
        return ships;
    }
    public Ships getMyShips(int player)
    {
        for (int x=0; x<ships.size(); x++)
            if (ships.get(x).getPlayerNum() == player)
                return ships.get(x);
        return null;
    }
    /**
     * This method draws the sector as box with width WIDTH and height HEIGHT.
     */
    public void drawSector(Graphics g, int WIDTH, int HEIGHT, int x, int y, int zoomFactor, JPanel p)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(playerColor());
        g.drawRect(x, y, WIDTH, HEIGHT);
        if (planetExists)
            planet.wantSmallPlanetFrame(g, x, y, zoomFactor, p);
        if (!ships.isEmpty())
            for (int h=0; h<ships.size(); h++)
                ships.get(h).drawShip(g, x, y, zoomFactor,0);
    }
    
    private Color playerColor()
    {
        if (planetExists)
        {
            if (planet.getControlledBy() == -1)
                return Color.GRAY;
            if (planet.getControlledBy() == 0)
                return Color.PINK;
            if (planet.getControlledBy()== 1)
                return Color.CYAN;
            if (planet.getControlledBy() == 2)
                return Color.BLUE;
            if (planet.getControlledBy() == 3)
                return Color.YELLOW;
            if (planet.getControlledBy() == 4)
                return Color.MAGENTA;
            if (planet.getControlledBy() == 5)
                return Color.GREEN;
            if (planet.getControlledBy() == 6)
                return Color.ORANGE;
            if (planet.getControlledBy() == 7)
                return Color.WHITE;
        }
        if (contested)
            return Color.RED;
        return Color.GRAY;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Packing Methods ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    
    private final char PARSE_CHAR = '§';
    
    /**
     * This method packs the data into a String. It loops through the planets and ships and seperates them with a parse char (§).
     * 
     * @param  shipString Packs all the data for all the ships into a String.
     * @return     the entire String, all packed up.
     */
    public String pack()
    {
        String shipString = "" + ships.size() + PARSE_CHAR;
        for (int h=0;h<ships.size();h++)
            shipString += ships.get(h).pack() + PARSE_CHAR;

        String output = "SECT"+PARSE_CHAR;
        
        if(planetExists)
            output += planet.pack()+PARSE_CHAR;
            
        output += shipString;
        
        return output;
    }
    
    /**
     * This method unpacks all the data from a String. It does this by seperating the String into elements using a parse char (§).
     * 
     * @param  inParsed A Vector with all the data parsed into elements.
     * @param  header The header for the Vector, the first element.
     */
    public void unpack(String data)
    {
        Vector inParsed = ParseUtil.parseStringBySign (data, PARSE_CHAR);
        String header = (String)inParsed.elementAt(0);
        if (header.equals("SECT"))
        {
            String header2 = (String)inParsed.elementAt(1);
            if (header2.substring(0, 3).equals("PLAN"))
                planet.unpack((String)inParsed.elementAt(1));
            else if ((Integer)inParsed.elementAt(1) > 0)
                {
                    ships.clear();
                    for (int h=0; h<(Integer)inParsed.elementAt(1); h++)
                    {
                        ships.add(new Ships(0, 0));
                        ships.get(h).unpack((String)inParsed.elementAt(2 + h));
                    }
                }
            if ((Integer)inParsed.elementAt(2) > 0)
            {
                ships.clear();
                for (int h=0; h<(Integer)inParsed.elementAt(2); h++)
                {
                    ships.add(new Ships(0, 0));
                    ships.get(h).unpack((String)inParsed.elementAt(3 + h));
                }
            }
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Action Listeners //////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    
    class GO_AL implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                for (int h=0; h<ships.size(); h++)
                    if (ships.get(h).getPlayerNum() == ClientMain.myPlayerNum)
                    {
                        ships.get(h).setXDest(Integer.parseInt(inputX.getText()));
                        ships.get(h).setYDest(Integer.parseInt(inputY.getText()));
                        GenericComm.addToOutbox(ships.get(h).pack());
                    }
            }
            catch (Exception NumberFormatException) {  }
        }
    }
}