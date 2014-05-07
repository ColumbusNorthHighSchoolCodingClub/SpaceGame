package src.spacegame.old;
import java.util.*;
import java.awt.*;
/**
 * Write a description of class Ships here.
 * 
 * @author Matthew Muckley 
 * @version 0.1
 */
public class Ships implements Packable
{
    // instance variables - replace the example below with your own
    int playerNum;
    int numShips;
    int destX;
    int destY;
    public boolean hasMoved=false;
    /**
     * Constructor for objects of class Ships
     */
    public Ships() //default constructor to initialize values
    {
        playerNum = -1; numShips = 0; destX = 2; destY = 2;
    }
    
    
    public Ships(int player, int num) //input by austin
    {
        setPlayerNum(player); //added by austin
        setNumShips(num); //added by austin.
        destX = 2; destY = 2;
        
        // initialise instance variables
    }
    public void setNumShips(int newNum) {numShips = newNum;}
    public void setPlayerNum(int newNum) {playerNum = newNum;}
    public void setDest(int targX, int targY) {destX = targX; destY = targY;}
    public void setXDest(int targX) {destX = targX;}
    public void setYDest(int targY) {destY = targY;}
    
    public int getPlayerNum() {return playerNum;}
    public int getNumShips() {return numShips;}
    public int getDestX() {return destX;}
    public int getDestY() {return destY;}
    
    public void drawShip(Graphics g, int X, int Y, int Scale, int fleetNum)// Stolen from Riehle
    {
        //Determine if yours, ally or enemy for coloring
        if(ClientMain.myPlayerNum == playerNum)
            g.setColor(Color.GREEN);
        else if(Roster.getPlayer(ClientMain.myPlayerNum).getAlliances().isAllied(playerNum))
            g.setColor(Color.YELLOW);
        else
            g.setColor(Color.RED);
            
            
        //Display numbers and ships
            g.drawString(""+numShips,Scale*2 + X+Scale*3*fleetNum,Scale*2 + Y);
//             g.fillRect(Scale*2 + X,Scale*2 + Y, Scale*3, Scale*3);
            g.drawRect(Scale*2 + X+Scale*3*fleetNum,Scale*2 + Y, Scale*3, Scale*3);
    }
    
//============================================================================================    
//============================================================================================    
//============================================================================================    
    public final char PARSE_CHAR = '?';
    public String pack()
    {
        String output = "SHIP" + PARSE_CHAR+
                        playerNum+PARSE_CHAR+
                        numShips+PARSE_CHAR+
                        destX+PARSE_CHAR+
                        destY;
        return output;
    }
    public void unpack(String data)
    {
        Vector inParsed = ParseUtil.parseStringBySign (data, PARSE_CHAR);
        String header = (String)inParsed.elementAt(0);
        if (header.equals("SHIP"))
        {
            playerNum = Integer.parseInt((String)inParsed.elementAt(1));
            numShips = Integer.parseInt((String)inParsed.elementAt(2));
            destX = Integer.parseInt((String)inParsed.elementAt(3));
            destY = Integer.parseInt((String)inParsed.elementAt(4));
        }
    }
    
}//--end of Ships class--
