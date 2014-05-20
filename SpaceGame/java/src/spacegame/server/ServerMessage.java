package src.spacegame.server;

/**
 * Stores messages (who and what).
 * Original author: Mike Spock
 * Cleaned and edited by: Brian Pierson
 * @author (Spock, Pierson)
 * @version (051514)
 */
public class ServerMessage {
	// instance variables 
	public int whoSent;
	public String message;
	
	//Constructor
	public ServerMessage(String msg, int from) {
	
		whoSent = from;
		message = msg;
	}
	
	//Accessors
	public int getWho() {
	
		return whoSent;
	}
	
	public String getMessage() {
	
		return message;
	}
	
}
