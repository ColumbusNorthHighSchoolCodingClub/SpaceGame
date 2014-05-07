package src.spacegame.old;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClientMain implements ActionListener
{
    private Timer timer;
    private final int UPDATE_DELAY_IN_MSEC = 5500; //delay in mSec

//     private Player thePlayer = new Player(); //Don't need this (use Roster copy)
    private Universe theUniverse = new Universe();
    private Market theMarket = new Market();
    
    private GenericComm comm;
    
    private ClientDisplay theDisplay;
    
    public static int myPlayerNum;
        
    //*********************************************************
    public static void main(String[] args)
    {
        Debug.msg("Welcome to SpaceGame!");
        ClientMain joe = new ClientMain();
        joe.init();
    } //This method makes this a 'runnable' class.  
    //*********************************************************
   
    //*********************************************************
    public void init()  //Called (automatically) when started up as an Applet.  (Otherwise called from main())
    {
        //--communication stuff--
        comm = new GenericComm(); //This initiates communication with the server.  
        myPlayerNum = comm.getMyNumber();
        Debug.msg("myPlayerNum is: #"+myPlayerNum);
        comm.setDebugValues("C_COMM",0);  //A static debug method... eventually will be removed.
        
//         
//                 Planet.initializePlanets(); //Austin
//         Debug.msg("Planet types made...");
//         
        Login myLogin = new Login();
        comm.sendMessage(myLogin.pack()); //Send a login object to the server... 
        
//         Roster.fillWithFakeData();  //*** TEMPORARY ***
        
        
        //--display stuff--
        theDisplay = new ClientDisplay(theUniverse, theMarket);
        Debug.msg("Initializing Complete.");

        
        initTimer();
    }//--end of init() function--

    //*********************************************************
    private void initTimer()
    {   //Set up a timer that calls this object's action handler.
        timer = new Timer(UPDATE_DELAY_IN_MSEC, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        timer.start();
    }

//========================================================================    
    public void actionPerformed(ActionEvent e) 
    {   //--SEND/GET UPDATE(s) FROM SERVER--
        //Called whenever timer goes off (every 5 sec.) 
        
        //==== SEND ALL OF THE MESSAGES WE HAVE ====
        comm.sendOutboxMessages();
        //==== SEND A REQUEST FOR A UNIVERSAL UPDATE ====
        comm.sendMessage("REQUEST");
        //==== RECIEVE THE UNIVERSE/ROSTER/MARKET UPDATES ====
        ArrayList<String> responses = comm.getAllMessages();
        //==== HERE IS WHERE WE UNPACK THE UPDATES FROM THE SERVER ====
        for(int q=0;q<responses.size();q++)
        {
            String header = responses.get(q).substring(0,4);
            
            if( header.equals("UNIV"))
            {
                theUniverse.unpack(responses.get(q));
                Debug.msg("Unpacked a Universe");
                theDisplay.updateDisplay();
            }
            else if( header.equals("ROST"))
            {
                Roster.unpack(responses.get(q));    
                //Update that display!!!
            }
            else if( header.equals("MARK"))
            {
                theMarket.unpack(responses.get(q));
                //Update that display!!!
            }            
            else if( header.equals("SERV"))
            {
                Vector inParsed = ParseUtil.parseStringBySign (responses.get(q), '-');
                myPlayerNum = Integer.parseInt((String)inParsed.elementAt(1));
                //Update that display!!!
            }

            else 
                Debug.msg("Unknown header recieved in ClientMain.actionPerformed() ");
        }
        
    }//--- end of actionPerformed() method ---    
                
}//--end of ClientMain class--
