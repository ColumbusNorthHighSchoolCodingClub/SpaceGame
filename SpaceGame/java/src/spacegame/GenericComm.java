package src.spacegame;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

//************************************************************
// The GenericComm class contains all of the simple communication 
// methods wrapped up nicely to be used by servers and clients.  
// Methods include: 
//   public void sendMessage(String message)
//   public String getMessage()
//   public void closeNicely()
//   (also two constructors are provided for establishing connections.) 
//************************************************************

public class GenericComm
{   //Declare variables
    //--Two things to keep track of who's talking--
    private int myNumber = 0;
    private String myTitle = "???";
    //--The communication objects--
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket socket = null;
    private static ArrayList<String> outBox = new ArrayList<String>();
    //--------------------------------------------------------
    //Constructors
    public GenericComm(Socket in_socket)
    {   //Establish connection via socket.
        socket = in_socket;
        createStreams(socket);
    }
    public GenericComm() 
    {   //Don't know the socket details yet... need to connect.
        socket = askUserForIPaddress();
        createStreams(socket);
    }
    //--------------------------------------------------------    
    // PUBLIC Methods
    //--------------------------------------------------------
    public int getMyNumber() { return myNumber; }
    
    //--------------------------------------------------------
    public void sendMessage(String message)
    {
        out.println(message); //Send a message 
        debugMsg("<S<",message);
    }
    //--------------------------------------------------------
    public String getMessage()
    {
        String message = null;
        try
        {
           message = in.readLine();
        }
        catch (IOException e) 
        {   //This means the connection is severed! (in some cases at least)
            debugMsg("catch!","I/O exception occurred in G_Comm:getMessage()");
            closeNicely();
        }        
        if(message != null) debugMsg(">R>",message);
        else debugMsg(">R>", " null message recieved.");
        
        return message;
    }
    //------------------------------------------------------------
    public ArrayList<String> getAllMessages()
    {
        ArrayList<String> msgList = new ArrayList<String>();
        
        String message = null;
        try
        {
            while(in.ready())
                msgList.add(getMessage());
        } 
        catch (IOException e) 
        {   //This means the connection is severed! (in some cases at least)
            debugMsg("catch!","I/O exception occurred in G_Comm:getMostRecentMessage()");
            closeNicely();
        }      
        return msgList;
    }
    //------------------------------------------------------------
    public void closeNicely()
    {
        try
        {   //Close things up nicely when you are done.
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e) 
        {
            debugMsg("catch!","I/O exception occurred in G_Comm:closeNicely()");
            System.exit(1);
        }                
    }
    //------------------------------------------------------------
    public void sendOutboxMessages()
    {
        //Eventually need to improve this to only send single copies of the same types of updates
        for(int q=0;q<outBox.size();q++)
            sendMessage(outBox.get(q));
        outBox.clear();
    }
    //------------------------------------------------------------
    public static void addToOutbox(String in)
    {
        outBox.add(in);
    }//add a message into the outbox to be sent

    //------------------------------------------------------------
    // PRIVATE Methods
    //------------------------------------------------------------
    private void createStreams(Socket socket)
    {   //Establish connection via socket.
        try 
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch (IOException e) 
        {
            debugMsg("catch!","I/O exception occurred in G_Comm:createStreams");
            System.exit(1);
        }        
    }
    //------------------------------------------------------------
    private Socket askUserForIPaddress()
    {
        Debug.msg("Asking for IP address (popup might be hidden)");
        Socket theSocket = null;
        String serverName = JOptionPane.showInputDialog
                         ("Please enter the Server IP address", "10.17.3.120");
        try 
        {
            theSocket = new Socket(serverName, 4444);
        } 
        catch (UnknownHostException e) 
        {
            debugMsg("catch!","Don't know about host: "+serverName+" in G_Comm:askUserForIPaddress()");
            System.exit(1);
        } 
        catch (IOException e) 
        {
            debugMsg("catch!","I/O exception occurred in G_Comm:askUserForIPaddress()");
            System.exit(1);
        }        
        Debug.msg("Connection complete.");
        return theSocket;
    }
           
    
    //===========================================================
    // These two methods are used for debugging the communication.
    //===========================================================
    public void setDebugValues(String title, int num)
    {
        myNumber = num;
        myTitle = title;
    }
    
    public void debugMsg(String type, String text)
    {   //Displays a debug message in the output window.  
        System.out.println(type+myTitle+"("+myNumber+"):  "+text);
    }
    //===========================================================

} //--end of GenericComm class--
