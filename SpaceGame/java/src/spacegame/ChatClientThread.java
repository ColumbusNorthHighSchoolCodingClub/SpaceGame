/*
 * ChatClientThread.java
 *
 * Created on March 7, 2007, 8:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package src.spacegame;
 

import java.io.*;
import java.net.*;
/**
 *
 * @author Workstation
 */
public class ChatClientThread extends Thread
{   
    private BufferedReader din = null;
    
    /** Creates a new instance of ChatClientThread */
    public ChatClientThread(Socket socket)
    {
        try
        {
            din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch( IOException ie ) 
        { 
            System.out.println( ie ); 
        }
    }
    
    public void run()
    {
        try
        {
            while (true) 
            {
                String message = din.readLine();
                ChatParse.fromServer(message);
                String temp = ChatParse.From + ": " + ChatParse.Message;
                ChatClient.messages.add(temp);
                ChatClient.gotmessage = true;
                //System.out.println(message);
            }
        } 
        catch( IOException ie ) 
        { 
            System.out.println( ie ); 
        }
    }
}
