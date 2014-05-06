package src.spacegame;        
/**
 * Write a description of class Spaceship here.OK I'm on it.
 * 
 * The Spaceship class is a class that controls the drawing of the ship, the varibles of the ship, and the upgrade Frame
 * 
 * 
 * @author (Tom Riehle) 
 * @version (0.0.0.01Apha)
 */
import java.util.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ShipStats implements Packable
{
    // instance variables - replace the example below with your own
    private static int speed=1;
    private static int attack=1;
    private static int defense=1;
    
    private int speedCost=100;
    private int attackCost=100;
    private int defenseCost=100;
    
    private int newMinerals;
    
    ///////////////
    public int getFleetSize(){return (int)10;}
    ///////////////////////
    
    final double increase= 1.15;
    
    private static int s=0;
    private static int a=0;
    private static int d=0;
    int numTotal=0;
    
    JButton speedButton = new JButton("Speed- #" + speed + "- Cost: " + speedCost);
    JButton attackButton = new JButton("Attack- #" + attack + "- Cost:  " + attackCost);
    JButton defenseButton = new JButton("Defense- #" + defense + "- Cost: " + defenseCost);
//     Player player = new Player(); //A Player "has a" ShipStats, not the other way around.  See below for work-around. (Spock0303)
    //We'll have the player class pass you the current amounts of materials/fuel/etc... when the panel is requested. 
    //ShipStats shipStats = new ShipStats();
    private int clicked=0;
    private String Data=("");
    
    /**
     * Constructor for objects of class Spaceship
     * it just set all of the stats and the ship at the start
     */
    public void ShipStats()
    {
       speed=1;
       attack=1;
       defense=1;
       
       numTotal= (speed+defense+attack);
       speedCost=100;
       attackCost=100;
       attackCost=100;

    }
    
    /**
     * The upgradeFrame
     * I am think this is going to be pretty simple.
     */
    public JPanel getPanel() 
    {
        
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 150));
       //panel.setBounds(600,100,155,190);
        
        speedButton.setBackground(Color.RED);
        attackButton.setBackground(Color.BLUE);
        defenseButton.setBackground(Color.GREEN);
        panel.setBackground(Color.RED);
        
        panel.add(speedButton);
        panel.add(attackButton);
        panel.add(defenseButton);
        
        speedButton.addActionListener(new speedButtonAL());
        panel.add(speedButton);
        speedButton.setBounds(20,10,40,40);
        attackButton.addActionListener(new attackButtonAL());
        panel.add(attackButton);
        defenseButton.setBounds(20,60,40,40);
        defenseButton.addActionListener(new defenseButtonAL());
        panel.add(defenseButton);
        defenseButton.setBounds(20,110,40,40);
        
        return panel;
    }

    /**
     * 
     *This is going to be filled with images of ships and stuff
     *There is going to be at least three ships
     *
     */
    public void drawShip(Graphics g, int X, int Y, int Scale)// graghics, X, Y, scale
    {
        if(numTotal%5==0)
        {
        g.setColor(Color.RED);
        g.drawRect(12,23,34,45);
        g.fillRect(23,34,45,56);
        }
        else if(numTotal%5==1) 
        {
        g.setColor(Color.PINK);
        g.drawRect(12,23,34,45);
        g.fillRect(23,34,45,56);
        }
        else if(numTotal%5==2) 
        {
        g.setColor(Color.BLACK);
        g.drawRect(12,23,34,45); 
        g.fillRect(23,34,45,56);
        }
        else if(numTotal%5==3) 
        {
        g.setColor(Color.BLUE);
        g.drawRect(12,23,34,45);
        g.fillRect(23,34,45,56);
        }
        else if(numTotal%5==4)
        {
        g.setColor(Color.WHITE);
        g.drawRect(12,23,34,45);
        g.fillRect(23,34,45,56);
        }
        
    }
    /**
     * This packs the data for it to be sent my code word is SHIP
     *
     */
    private final char PARSE_CHAR = '&';// alt 1256 ~
    public String pack()
    {
       Data = "SHIP" + PARSE_CHAR + speed +PARSE_CHAR +  attack+ PARSE_CHAR +defense + PARSE_CHAR +newMinerals;
       return Data;
    }
    /**
     * 
     *
     */
    public void unpack(String data)
    {
       Vector inParsed = ParseUtil.parseStringBySign(data,PARSE_CHAR);
        String header = (String)inParsed.elementAt(0);
        if(header.equals("SHIP"))
        {
            speed = Integer.parseInt((String)inParsed.elementAt(1));
            attack = Integer.parseInt((String)inParsed.elementAt(2));
            defense = Integer.parseInt((String)inParsed.elementAt(3));
            newMinerals = Integer.parseInt((String)inParsed.elementAt(4));
        }
        else 
            System.out.println("parse error (Shipstats.unpackData): ");
    }
    
    private class speedButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              Roster.getPlayer(ClientMain.myPlayerNum);
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()>speedCost)
              {
                   s++; 
                   speed++;
                   speedCost=(int)(100 * speed * increase);
                   Roster.getPlayer(ClientMain.myPlayerNum).setMaterials
                        (Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()-speedCost);
                   //newMinerals=Player.getStaticMaterials()-speedCost;
                   GenericComm.addToOutbox(pack());
                   
              }
                   speedButton.setLabel("Speed- #" + speed + "- Cost: " + speedCost);
                   
          }
     }    //======================
     private class attackButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()>=attackCost)
              {
                     a++; 
                     attack++;
                     attackCost=(int)(100 * attack * increase);
                     Roster.getPlayer(ClientMain.myPlayerNum).setMaterials(Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()-attackCost);
                     GenericComm.addToOutbox(pack());
              }      
                     attackButton.setLabel("Attack- #" + attack + "- Cost:  " + attackCost);
          }
     }    //======================
     private class defenseButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              Roster.getPlayer(ClientMain.myPlayerNum);
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()>=defenseCost)
              {
                      d++;
                      defense++;
                      defenseCost=(int)(100 * defense* increase);
                      Roster.getPlayer(ClientMain.myPlayerNum).setMaterials(Roster.getPlayer(ClientMain.myPlayerNum).getMaterials()-defenseCost);
                      GenericComm.addToOutbox(pack());
              }     
                      defenseButton.setLabel("Defense #" + defense + " Cost: " + defenseCost);
          }
     }    //======================
     
    public void calculate()
    {
        
    }
     
    public int getSpeed(){return (int)speed;}//others can recieve the speed stat
    public void setSpeed(int in){speed=in;}//others can set the speed stat if upgraded
    
    
    public int getAttack(){return (int)attack;}//others can attack the speed stat
    public void setAttack(int in){attack=in;}//others can set the attack stat if upgraded
    
    
    public int getDefense(){return (int)defense;}//others can recieve the defense stat
    public void setDefense(int in){defense=in;}//others can set the defense stat if upgraded
    
    
}
