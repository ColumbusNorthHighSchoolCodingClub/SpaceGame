package src.spacegame.old;

import javax.swing.JOptionPane;
import java.util.*;

public class Login implements Packable
{
    // instance variables 
    private String playerName = "empty";
    
    /**
     * Constructor for objects of class Login
     */
    public Login()
    {
        playerName = askUserLoginInfo();
    }
    public Login(boolean in)
    {
        //constructor for a blank login object (server-side)
    }

    //Accessor(s)
    public String getName() { return playerName; }
    
//============================================================================================
    
    private String askUserLoginInfo()
    {
        String playerName = JOptionPane.showInputDialog
                         ("Please Enter your Name (no profanity)", "John Doe");
        return playerName;
    }

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//  IMPLEMENTS PACKABLE 
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    private static final char PARSE_CHAR = '$';
    
    public String pack()
    {
        String out = "LOGI" + PARSE_CHAR + playerName;
        return out;
    }

    public void unpack(String data)
    {
       Vector inParsed = ParseUtil.parseStringBySign(data,PARSE_CHAR);
        String header = (String)inParsed.elementAt(0);
        if(header.equals("LOGI"))
        {
            playerName = (String)inParsed.elementAt(1);
        }
        else 
            System.out.println("parse error (LOGIN.unpackData): ");
    }
    
    public static char getParseChar(){return PARSE_CHAR;}//others can recieve the defense stat
    
}//--end of Login class--
