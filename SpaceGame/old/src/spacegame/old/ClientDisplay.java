
/**
 * Write a description of class ClientMain here.
 * 
 * @author (Matt Showalter) 
 * @version (started 2/19/2007)
 * 
 * display the whole view
 * Choose what to display and then display it
 * need to draw JPanels
 * 
 */
 package src.spacegame.old;
import java.util.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;



public class ClientDisplay extends JFrame implements Runnable
{
    private Universe theUniverse;   //includes planets, ships, and whatever else
//     private Player thePlayer;   //gets all the players data
//     private Roster theRoster;   //has the list of all the players
    private Market theMarket;   //information about the market
 /**
  * length = tall
  * width = wide
  * 

 * 
 * SUBMIT COMMENTS, DATE, NAME HERE!!!
 * 
 * 
 * 
 * (friday after sb2k7, blake pedersen) hey matt theres somethin wrong with the display for the alliance panel with something to do with
 * the setbounds and setbackgrounds for the alliance display  * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *(2/26/2007, Matt Showalter)I think that the chat window would be good above the bottome left panel. I will set it so its is 600 by 200. 
 * 
 * The layout of the Game will be as follows:
 * The size of the game will be 750 tall, 1000 wide. 
 * The Universe will be the 4/5 length and 4/5 of the width (600 length, 800 width) of the screen area where the main game will be displayed
 * The chat panel is going top left and will be (600, 200) above the bottom left panel
 * The Market, technology info, player info, ship stats will be displayed in the bottom 1/5 of the window (150 length, 800 width width) 
 * in the info panel. On both sides of this panel will be two equal size panels (150 length, 100 width). The one on the left will
 * switch the info panel around and the right panel I'm not sure what it will be yet. (redraw everytime you press a button by using init)
 * 
 * (2/22/2007)hey matt im(blake) doing the alliance class and im prolly going to put roster and diplomacy together
 *also wat do u think about having the diplomace pop up as a fifth of the box only vertically like on the left or right side, above the bottom panel
 *
 * 
 * PANEL FUNCTIONS THAT YOU ARE SENDING TO OTHERS ARE ALWAYS GOING TO BE CALLED getPanel()
 * 
 */

    
    // have a menu panel, a message panel, a main panel
    private JPanel mainPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel chatPanel = new JPanel();
    
    public boolean shipInfo = false;
    public boolean techInfo = false;
    
      
    Thread animator;
    boolean animate;
    boolean close = false;
    
    

    
//     ShipStats shipStats = new ShipStats();  (This is part of a Player object)
    
    public ClientDisplay(Universe inUniverse, Market inMarket)
    {
        //This constructor recieves and stores references to the three big game objects (Spock0303)
//         thePlayer = inPlayer;
       // Graphics();
        theUniverse = inUniverse;
        theMarket = inMarket;
        //and then it initializes the display.
        init();
        
        start(); //Start the Runnable Thread to keep updating the look of the universe based on mouse input.
    }
        
    
  public void init()  //used when applet starts
    {    /**
         * start up applet
         * Grab the data from the roster, player, universe, market class
         * Maybe make the universe panel the main panel, the roster\player class should have message panel...
         */
       // make it visible to the user ang get stuff I need
  //      Universe.getPanel() = SpaceMain;
  //      Market.getPanel() = C_SPaceMarketPanel;
            initDisplay();
    }//end of init() function
    

   public void initDisplay()  //display stuff
    {    /**
         * Add in all of the windows together to make the display
         * set the bounds to be large
         */
//         Debug.msg("inClientDisplay initDisplay()");
    if(close == false)
    {
        this.setTitle("SpaceGame 05/22");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000, 700));
        this.setLayout(new BorderLayout()); 

        this.add("South", bottomDisplays());
        this.add("West", leftSideRosterPanel());
        this.add(universePanel());

     //Display the window.
        this.pack();
        this.setVisible (true);         // make it visible to the user
    }
        
    }//end of initDisplay() function


    private JPanel bottomDisplays()
    {
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout()); 
        bottom.add("West",leftBottomCorner());
        bottom.add("Center",bottomCenterDisplays());
        bottom.add("East",rightBottomCorner());
        
        return bottom;
    }
    
    private JPanel bottomCenterDisplays()
    {
        JPanel bottomCenter = new JPanel();
        bottomCenter.add("West", marketCenter());
        bottomCenter.add("Center", bottomCenterCenterDisplays());
        bottomCenter.add("East", playerCenter());
        
        return bottomCenter;
    }
    
    private JPanel bottomCenterCenterDisplays()
    {
        JPanel bottomCenterCenter = new JPanel();
        bottomCenterCenter.add("West", techCenter());
        bottomCenterCenter.add("East", shipCenter());
        
        return bottomCenterCenter;        
    }
    
  //bottom panels
    private JPanel leftBottomCorner()
    {
         
      JPanel lbc= new JPanel();
      lbc.setPreferredSize(new Dimension(100, 150));
      lbc.setBackground(Color.BLACK);
      //paintLogo(g, 100, 150); 

       return lbc;
    }

    private JPanel marketCenter()
    {
        Market market = new Market();
        
        JPanel marketPanel = new JPanel();
        marketPanel.setPreferredSize(new Dimension(190,155));
        marketPanel.setBackground(Color.CYAN);
        marketPanel.add(market.marketFrame());

        return marketPanel;
    }
    
     private JPanel techCenter()
    {
        JPanel techPanel = new JPanel();
        techPanel.setPreferredSize(new Dimension(190,155));
        techPanel.setBackground(Color.GREEN);
        techPanel.add(Roster.getPlayer(ClientMain.myPlayerNum).getTechnology().getPanel());

        return techPanel;
    }
     private JPanel shipCenter()
    {
        JPanel shipPanel = new JPanel();
        shipPanel.setPreferredSize(new Dimension(190,155));
        shipPanel.add(Roster.getPlayer(ClientMain.myPlayerNum).getShipStats().getPanel());
        shipPanel.setBackground(Color.RED);

        return shipPanel;
    }
     private JPanel playerCenter()
    {
        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(190,155));
        playerPanel.setBackground(Color.ORANGE);
        playerPanel.add(Roster.getPlayer(ClientMain.myPlayerNum).getPanel());

//         infoPanel = thePlayer.getShipStats().getPanel();
        return playerPanel;
    }
         /**
         * The main universe panel
         * x,y,witdh, height
         */
    private JPanel universePanel()
    {
        JPanel universePanel = new JPanel();
       universePanel.setPreferredSize(new Dimension(800,600));//sets the size of the grid display etc
       
       universePanel.add(theUniverse.getNorthPanel());
       universePanel.add(theUniverse.getUniversePanel());
       //.setPreferredSize(new Dimension(800,20));
       
       
       return universePanel;
    }
    
         /**
         * The bottom right panel that has a lot of buttons
         * These buttons when pressed will show different stuff in 
         * the main bottom bar
         * x,y,witdh, height
         * info Panel
         */
    private JPanel rightBottomCorner()
    {
        JPanel rbc = new JPanel();
        JButton exitButton = new JButton();
        rbc.setPreferredSize(new Dimension(100,150));
        exitButton.addActionListener(new ExitButtonAL());
        exitButton.setBounds(20,10,40,40);
 
        rbc.setBackground(Color.BLUE);
        rbc.add(exitButton);

        return rbc;
    }
   private class ExitButtonAL implements ActionListener
   {
          public void actionPerformed (ActionEvent ev)
          {   
              Debug.msg("EXITING");
              stop();
          }
     }    //======================
    
        /**
         * the chat panel that is the
         * big chat panel on the left
         * of the screen.
         */
    private JPanel leftSideRosterPanel()
    {
        //Alliance Panel
        JPanel leftSide = new JPanel();
        leftSide.setPreferredSize(new Dimension(200,600));
        leftSide.add(Roster.getPlayer(ClientMain.myPlayerNum).getAlliances().getPanel());
        leftSide.setBackground(Color.BLUE);
        return leftSide;
    }
    



    
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\\
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END OF PANELS!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\\
//............................................................................................................\\

    public void run() //Runs the Thread (implements Runnable)
    {
        while(animate)
        {
            try
            {
                Thread.sleep(200);
                animator.sleep(50);
            }
            catch(InterruptedException ie)
            {
                System.err.println("interrupt: " + ie.getMessage());
                animate = false;
            }
            theUniverse.updateFrame();
            Player temp = Roster.getPlayer(ClientMain.myPlayerNum);
            temp.getAlliances().updateFrame();
//************** BIG PROBLEM HERE, DISPLAY ERRORS WHEN BOTH ARE UPDATED ********************
        }
    }
    
    public void updateDisplay()
    {
        theUniverse.updateFrame();
        Player temp = Roster.getPlayer(ClientMain.myPlayerNum);
        temp.getAlliances().updateFrame();
    }        
    
    private void start() //Starts the Thread (implements Runnable)
    {
        if(!animate)
        {
            animate = true;
            animator = new Thread(this);
            animator.setPriority(Thread.NORM_PRIORITY);
            animator.start();
        }
    }
    private void stop() //Stops the Thread (implements Runnable)
    {
        animate = false;
        animator = null;
        close = true;       //stop making the panels
      
    }
    
    public Graphics paintLogo(Graphics g, int x, int y)
    {
        g.setColor(Color.darkGray); 
             g.drawOval(x, y, 25, 25);
             g.fillOval(x, y, 25, 25); 
        g.setColor(Color.lightGray); 
             g.drawOval(x, y, 10, 10);
        g.drawString("Eternal Wars 2007!", x, y);     
             
        return g;
    } 

    
}
