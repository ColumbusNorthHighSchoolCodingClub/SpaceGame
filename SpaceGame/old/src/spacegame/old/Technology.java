package src.spacegame.old;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.*;
/**
 * Write a description of class Technology here.
 * 
 * You should probably get your class to compile.
 * 
 * @author (The Messiah) 
 * @version (.06)
 */
public class Technology implements Packable
{

    
    // instance variables
    private int fuelSpeed=1;
    private int mineSpeed=1;
    private int stealth=1;
    private int sensors=1;
    
    //prices - TBD could change depending on rate at which minerals are being aquired
    private int fuelCost=100;
    private int mineCost=100;
    private int stealthCost=100;
    private int sensorsCost=100;
    
    final double increase= 1.10;
    
    
    //packing stuffdddd
    
    //Accessors
    public int getFuelSpeed(){return fuelSpeed;}
    public int getMineSpeed(){return mineSpeed;}
    public int getStealth(){return stealth;}
    public int getSensors(){return sensors;}
    
    //Modifiers
    public void setFuelSpeed(int newFuelSpeed){fuelSpeed = newFuelSpeed;}
    public void setMineSpeed(int newMineSpeed){mineSpeed = newMineSpeed;}
    public void setStealthness(int newStealth){stealth = newStealth;}
    public void setSensors(int newSensors){sensors = newSensors;}
    
    //Buttons
    JButton fuelButton = new JButton("FuelSpeed- #" + fuelSpeed+ "Cost= " + fuelCost);
    JButton mineButton = new JButton("mineSpeed- #" + mineSpeed+ " Cost=" + mineCost);
    JButton stealthButton = new JButton("Stealthness- #" + stealth+ "Cost=" + stealthCost);
    JButton sensorsButton = new JButton("Sensors- #" + sensors+ "Cost=" + sensorsCost);

    /**
     * Constructor for objects of class Technology
     */
    
    public Technology()
    {
        // initialise instance variables
        fuelSpeed=1;
        mineSpeed=1;
        stealth=1;
        sensors=1;
        
        
        fuelCost=100;
        mineCost=100;
        stealthCost=100;
        sensorsCost=100;
    }
    
    
    public JPanel getPanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, 150));
        
        
        fuelButton.setBackground(Color.GREEN);
        mineButton.setBackground(Color.GREEN);
        stealthButton.setBackground(Color.GREEN);
        sensorsButton.setBackground(Color.GREEN);
        
        
        panel.add(fuelButton);
        panel.add(mineButton);
        panel.add(stealthButton);
        panel.add(sensorsButton);
        
        fuelButton.addActionListener(new fuelButtonAL());
        panel.add(fuelButton);
        mineButton.addActionListener(new mineButtonAL());
        panel.add(mineButton);
        stealthButton.addActionListener(new stealthButtonAL());
        panel.add(stealthButton);
        sensorsButton.addActionListener(new sensorsButtonAL());
        panel.add(sensorsButton);
        
        panel.setBackground(Color.GREEN);
        
        
        return panel;
//         JButton 
    }
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   
     * @return    
     */
    
    private final char parseChar = '+';
    
    //packing methods
    public String pack()
    {
        String packed = "TECH"+parseChar;
        packed += ""+fuelSpeed+parseChar+mineSpeed+parseChar+stealth+parseChar+sensors+parseChar;
//         Debug.msg("The packed Tech is: "+packed);
        return packed;    
    }
    
    
    public void unpack(String data)
    {
        Vector inParsed = ParseUtil.parseStringBySign(data,parseChar);
        String header = (String)inParsed.elementAt(0);
        if(header.equals("TECH"))
        {
            fuelSpeed = Integer.parseInt((String)inParsed.elementAt(1));
            mineSpeed = Integer.parseInt((String)inParsed.elementAt(2));
            stealth = Integer.parseInt((String)inParsed.elementAt(3));
            sensors = Integer.parseInt((String)inParsed.elementAt(4));
        }
        else 
            System.out.println("parse error Technology.unpack() ");
    }
    
    
    
    private class fuelButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()>=fuelCost)
              {
                  Roster.getPlayer(ClientMain.myPlayerNum);// the call for getting my player
                  Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                    (Roster.getPlayer(ClientMain.myPlayerNum).getMoney()-fuelCost);
                  fuelSpeed++;
                  fuelCost=(int)(100 * fuelSpeed* increase);
                  fuelButton.setLabel("FuelSpeed- #" + fuelSpeed+ "Cost= " + fuelCost);
                  Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                    (Roster.getPlayer(ClientMain.myPlayerNum).getMoney() - fuelCost);
                   //String message = "" +Roster.getPlayer(ClientMain.myPlayerNum).getMoney();
                    
                    //Debug.msg(message);
              //fuelButton.setLabel("FuelSpeed- #" + fuelSpeed + "Cost= " + FuelCost);
              }
          }
     }    //======================
     
     private class mineButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()>=mineCost)
              {
                  Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                        (Roster.getPlayer(ClientMain.myPlayerNum).getMoney()-mineCost);
                  mineSpeed++;
                  //mineButton.setLabel("mineSpeed- #" + mineSpeed+ " Cost=" + mineCost); 
                  mineCost=(int)(100 * mineSpeed* increase);
                  mineButton.setLabel("MineSpeed- #" + mineSpeed + " Cost= " + mineCost);
                  Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                    (Roster.getPlayer(ClientMain.myPlayerNum).getMoney() - mineCost);
             }
          }
     }    //======================
     
     private class stealthButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()>=stealthCost)
              {
                 Roster.getPlayer(ClientMain.myPlayerNum).setMoney(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()-stealthCost);
                  stealth++;
                  stealthCost=(int)(100 * stealth* increase);
                  stealthButton.setLabel("Stealthness- #" + stealth+ "Cost=" + stealthCost); 
                  Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                    (Roster.getPlayer(ClientMain.myPlayerNum).getMoney() - stealthCost);
              }
                   
          }
     }    //======================
     
     private class sensorsButtonAL implements ActionListener
     {
          public void actionPerformed (ActionEvent ev)
          {   
              if(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()>=sensorsCost)
              {
                Roster.getPlayer(ClientMain.myPlayerNum).setMoney(Roster.getPlayer(ClientMain.myPlayerNum).getMoney()-sensorsCost);
                sensors++;
                sensorsCost=(int)(100 * sensors* increase);
                sensorsButton.setLabel("Sensors- #" + sensors+ "Cost=" + sensorsCost);
                Roster.getPlayer(ClientMain.myPlayerNum).setMoney
                    (Roster.getPlayer(ClientMain.myPlayerNum).getMoney() - sensorsCost);
              }
                   
          }
     }    //======================
}
