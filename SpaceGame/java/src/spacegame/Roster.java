package src.spacegame;
/*
 *  If you know a player's number, you can get them from this static Roster
 * it is only an ArrayList to hold all of the Player objects in the game.  
 * Easy to access by any of the classes!  
 * Even use it to statically access 'yourself' to change fuel/materials/$$
 */


import java.util.ArrayList;

public class Roster
{
    // instance variables - replace the example below with your own
    public static ArrayList<Player> theRoster = new ArrayList<Player>();

   
    public static void fillWithFakeData()
    {
        // fill with junk
        for(int q=0; q<7; q++)
            theRoster.add(new Player());
    }    
    
    public static ArrayList<Player> getRoster()
    {
        return theRoster;
    }
    
    public static int getRosterSize()
    {
        return theRoster.size();
    }
    
    public static Player getPlayer(int index)
    {
        if(index < theRoster.size())
            return theRoster.get(index);
        else return new Player();
    }
    
    public static int addPlayer(Player in)
    {
        //Returns the index that it was added at
        theRoster.add(in);
        Debug.msg("Roster: added Player - "+in.getName()+" size is now:"+theRoster.size());
        return theRoster.size()-1;
    }
//     private static ArrayList<Player> setTheRoster(String in,int index)
//     {
//         theRoster(index)=in;
//     }


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//  IMPLEMENTS PACKABLE (well sorta, statically in this case)
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    private static final char PARSE_CHAR = '*'; //The parse character for this class.
	/**
	 * The pack method converts the data from this class into a String 
	 * for transfer over the network.
	 * 
	 * @return		the String to be transfered
	 */
	 public static String pack()
	 {
	    String out = "ROST"+PARSE_CHAR;
	    for(int q=0;q<theRoster.size();q++)
	       out += theRoster.get(q).pack() + PARSE_CHAR;
	       
	   return out;
     }
	
	/**
	 * The unpack method converts the data recieved from the server 
	 * into an object of this class.
	 * 
	 * @data		the String to be converted.
	 */
	public static void unpack(String data)
	{
	    if(data.substring(0,4).equals("ROST"))
	    {
	        //Unpacking a completely new Roster (for all we know), so erase the old.
	        theRoster.clear();
	        ArrayList<String> parsed = ParseUtil.parseString(data,PARSE_CHAR);
	        for(int q=1;q<parsed.size();q++) //Start at 1, the 0 element is the header
	        {
	            Player temp = new Player();
	            temp.unpack(parsed.get(q));
	            theRoster.add(temp);
	        }
	    }
	    else
	       Debug.msg("Unable to unpack a Roster?...");
	} //--end of unpack() mehtod--

}//--end of Roster class--
