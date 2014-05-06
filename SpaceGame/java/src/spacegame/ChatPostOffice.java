/*
 * ChatPostOffice.java
 *
 * Created on March 12, 2007, 8:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package src.spacegame;
 
import java.util.*;

/**
 *
 * Coded by Tom Andrews
 */
public class ChatPostOffice 
{
    public static boolean READY = false;
    public static int MAX_PLAYERS = 25;
    public static ArrayList<ArrayList<String>> MESSAGES = new ArrayList<ArrayList<String>>();
    
    public static void initialize()
    {   //Initialize the class 
        for (int z=0; z<MAX_PLAYERS; z++)
        {MESSAGES.add(new ArrayList<String>());}
        for (int z=0; z<MAX_PLAYERS; z++)
        {MESSAGES.get(z).add(ChatParse.toServer("WELCOME", "<SERVER>", z));}
        READY = true;
    }
    
    public static void processMail(String in)
    {
        ChatParse.fromClient(in);
        MESSAGES.get(0).add(in);
        MESSAGES.get(1).add(in);
        System.out.println("NEW MAIL: " + ChatParse.Message + " " + ChatParse.From);
    }
    
    public static ArrayList<String> getMail(int num)
    {
        if(MESSAGES.size()<1){System.out.println("small");return null;}
        ArrayList<String> list = MESSAGES.get(num);
        //MESSAGES.get(num).clear();
        return list;
    }
    
    public static String getLastMail(int num)
    {
        if(MESSAGES.size()<1){System.out.println("small");return null;}
        String list = MESSAGES.get(num).get(MESSAGES.get(num).size()-1);
        //MESSAGES.get(num).clear();
        return list;
    }
    
    public static void clearMail(int num)
    {
        MESSAGES.get(num).clear();
    }
    
}
