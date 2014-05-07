package src.spacegame.old;
/**
 * Stores messages (who and what).
 * 
 * @author (Spock) 
 * @version (030707)
 */
public class SS_Message
{
	// instance variables 
	public int whoSent;
	public String message;

	//Constructor
	public SS_Message(String msg, int from)
	{ whoSent = from;  message = msg;  }
	
	//Accessors
	public int getWho() { return whoSent; }
	public String getMessage() { return message; }

}

