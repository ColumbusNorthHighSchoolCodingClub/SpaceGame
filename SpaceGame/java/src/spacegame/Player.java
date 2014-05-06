package src.spacegame;

import java.net.InetAddress;
import java.util.*;
import javax.swing.*;
import java.awt.*;
/**
 * This class maintains each player's information and abilities.
 * 
 * @author Scott Skiles 
 * @version RC 1
 */
public class Player implements Packable 
{
//     //need a static instance of myFuel, myMaterials, myMoney (Spock0303)
//     private static int myStaticFuel, myStaticMaterials, myStaticMoney = 1000;
    
    public  String name ="???";
    private ShipStats myShip;
    private Technology myTech;
    private Alliances myAllies;
    private final char parseChar = '@';
    private int myFuel, myMaterials, myMoney = 0;
//     public static int numPlayers=0;
    public final String ip = getIpString();
//     private static int playerNumber=0;  //Changed by Muckley
    private static byte[] test = new byte[4];
    
    //--I added these to get access to the big3 game objects that a Player object stores.  (Spock0303)
    public ShipStats getShipStats() { return myShip; }
    public Technology getTechnology() { return myTech; }
    public Alliances getAlliances() { return myAllies; }
    
    
    
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        //Needed to at least non-null the game objects (spock0303)
        myShip = new ShipStats();
        myTech = new Technology();
        myAllies = new Alliances();
        
        //
        setVariables();   
        myFuel=500;myMaterials=1000;myMoney=750;
//         myStaticFuel=500; myStaticMaterials=1000; myStaticMoney=750;
//         numPlayers++;
//         playerNumber=numPlayers;
    }
    
    /**
     * This method is worthless and does nothing in particular.
     */
    private void setVariables()
    {
        myFuel=500;myMaterials=1000;myMoney=750;
//         myStaticFuel=500; myStaticMaterials=1000; myStaticMoney=750;
    }
    
    //public static void main (String[] args)
    //{String test = getHostname();//System.out.println(test);test=getIpByte();for(int x=0;x<4;x++)System.out.println(test[x]);}
    
    /**
     * Returns that Player Panel which displays all pertinent info.
     * 
     * @return          the panel containing variable labels. 
     */
    public JPanel getPanel()
    {
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
    public String pack()
    {   String packed = new String("PLAY");
        packed+= parseChar+myShip.pack()+parseChar+myTech.pack()+parseChar+myAllies.pack()+parseChar+name+
        parseChar+myFuel+parseChar+myMaterials+parseChar+myMoney+parseChar;
        return packed; }
    
    /**
     * This method separates an input string into variable data using '@' as the parseChar.
     * 
     * @param data   the input String. 
     */
    public void unpack(String data){
                
        Vector inParsed = ParseUtil.parseStringBySign(data,parseChar);
        String header = (String)inParsed.elementAt(0);
        if(header.equals("PLAY"))
        {
            myShip.unpack((String)inParsed.elementAt(1)); 
            myTech.unpack((String)inParsed.elementAt(2)); 
            myAllies.unpack((String)inParsed.elementAt(3)); 
            name = (String)inParsed.elementAt(4);
            myFuel = Integer.parseInt((String)inParsed.elementAt(5));
            myMaterials = Integer.parseInt((String)inParsed.elementAt(6));
            myMoney = Integer.parseInt((String)inParsed.elementAt(7));
//             playerNumber = Integer.parseInt((String)inParsed.elementAt(5));
        }
        else 
            System.out.println("parse error Player.unpack() ");
    }
    
    /**
     * This method gets the IP Address as a Byte[].
     * 
     * @return          the String representation of the IP Address. 
     */
    private byte[] getIpByte()
    {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            byte[] ipAddr = addr.getAddress();
            for(int x=0;x<4;x++)if(ipAddr[x]<0){System.out.println(ipAddr[x]);}
            return ipAddr;
        } 
        catch (java.net.UnknownHostException e) {}
        return null;
    }
    

    /**
     * This method gets the IP Address as a String.
     * 
     * @return          the String representation of the IP Address. 
     */
    private String getIpString()
    {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ipAddr = addr.getHostAddress();
            return ipAddr;
        } 
        catch (java.net.UnknownHostException e) {}
        return null;
    }
    
//////////////////////////////////////////////////////////////////////////////
//////////////////   Accessors and Modifiers!   //////////////////////////////

    /**
     * This method gets the IP Address as a String.
     * 
     * @return          the String representation of the IP Address. 
     */
    public String getHostname()
    {
        try {
        InetAddress addr = InetAddress.getLocalHost();
        String ipAddr = addr.getHostName();
        return ipAddr;
        } 
        catch (java.net.UnknownHostException e) {}
        return null;
    }
    /**
     * This method gets the number of players.
     * 
     * @return          the value of numPlayers. 
     */
//     public static int getPlayerNum(){return playerNumber;}  //Changed by Muckley
//     /**
//      * This method gets the number of players.
//      * 
//      * @return          the value of numPlayers. 
//      */
//     public int getNumPlayers(){return numPlayers;}  
//     /**
//      * This method gets the name of the player.
//      * 
//      * @return          the characters in name. 
//      */
    public String getName(){return name;}    
    /**
     * This method sets the name of a player.
     * 
     * @param newName   the desired name. 
     */
    public void setName(String newName){name=newName;}
    /**
     * This method gets the amount of fuel a player has.
     * 
     * @return          the value of myFuel. 
     */
    public int getFuel(){return myFuel;}
    /**
     * This method sets the amount of fuel a player has.
     * 
     * @param newFuel   the desired amount of fuel. 
     */
    public void setFuel(int newFuel){myFuel=newFuel;}
    /**
     * This method gets the amount of materials a player has.
     * 
     * @return          the value of myMaterials. 
     */
    public int getMaterials(){return myMaterials;}
    /**
     * This method sets the amount of materials a player has.
     * 
     * @param newMaterials   the desired amount of materials. 
     */
    public void setMaterials(int newMaterials){myMaterials=newMaterials;}
    /**
     * This method gets the amount of money a player has.
     * 
     * @return          the value of myMoney. 
     */
    public int getMoney(){ Debug.msg(""+myMoney); return myMoney;}
    /**
     * This method sets the amount of money a player has.
     * 
     * @param newMoney   the desired amount of money. 
     */
    public void setMoney(int newMoney){myMoney=newMoney;  Debug.msg("new money = " + newMoney);}
    
  
    /**
     * This method gets the amount of money a player has.
     * 
     * @return          the value of myMoney. 
     */
// //Added these Spock0303
//     public static int getStaticFuel() {return myStaticFuel;}
//     public static void setStaticFuel(int in) { myStaticFuel = in; }
//     
//     public static int getStaticMaterials() {return myStaticMaterials;}
//     public static void setStaticMaterials(int in) { myStaticMaterials = in; }
// 
//     public static int getStaticMoney() {return myStaticMoney;}
//     public static void setStaticMoney(int in) { myStaticMoney = in; }

    
    
}// End of Player class!


