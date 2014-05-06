/*
 * Chat.java
 *
 * Created on February 19, 2007
 *
 */
package src.spacegame;
 
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * Coded by Tom Andrews
 */
public class ChatSockets extends Thread
{
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket socket = null;
    
    private static boolean[] connected = new boolean[ChatPostOffice.MAX_PLAYERS];
    
    /** Creates a new instance of ChatThread */
    public ChatSockets(Socket sock)
    {
        System.out.println("new connection " + sock.getPort());
        socket = sock;
        try 
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch (IOException e) 
        {
            System.err.println("error creating server streams");
        }
        System.out.println("new connection created");
    }
    
    public void run()
    {
        String inputLine, outputLine;
        System.out.println("connection running");
        while((inputLine = getMessage()) != null)
        {
            //process(inputLine);
            if(inputLine.equals("MAIL PLEASE"))sendAllMessages();
            else 
            {
                ChatPostOffice.processMail(inputLine);
            }
        }
    }
    
    public static int getMyNumber()
    {
        for(int i=0; i<ChatPostOffice.MAX_PLAYERS; i++)
        {
            connected[i] = true;
            if(!connected[i])return i;
        }
        return 99;
    }
    
    //========================================================================//
    //                          SERVER METHODS                                //
    //========================================================================//
    public void sendMessage(String message)
    {
        out.println(message); //Send a message 
    }
    
    public void sendAllMessages()
    {
        ArrayList<String> mail = ChatPostOffice.getMail(0);
        for(int i=0; i<mail.size(); i++)
        {
            ChatParse.fromClient(mail.get(i));
            sendMessage(ChatParse.toClient(ChatParse.Message, ChatParse.From));
        }
        ChatPostOffice.clearMail(0);
    }
    //--------------------------------------------------------------------------
    public String getMessage()
    {
        String message = null;
        try
        {
           message = in.readLine();
        }
        catch (IOException e) 
        {   //This means the connection is severed! (in some cases at least)
            System.err.println("could not get message");
        }        
        return message;
    }
    //--------------------------------------------------------------------------
    public String getMostRecentMessage()
    {
        String message = null;
        try
        {
            while(in.ready())
                message = getMessage();
        } 
        catch (IOException e) 
        {   //This means the connection is severed! (in some cases at least)
        }      
        return message;
    }
    //--------------------------------------------------------------------------
    public boolean messageWaiting()
    {
        boolean result = false;
        try
        {
            result = in.ready();
        } 
        catch (IOException e) 
        {   //This means the connection is severed! (in some cases at least)
        }   
        return result;
    }
    
}
