package src.spacegame.server;

/**
 * Stores messages (who and what).
 * Original author: Mike Spock
 * Cleaned and edited by: Brian Pierson
 * @author (Spock, Pierson)
 * @version (051514)
 */
public class ServerMessage {
	
	public int msgOwner;
	public String message;

	//Constructor
	public ServerMessage(String msg, int from) {

		msgOwner = from;
		message = msg;
	}

	//Accessors
	//By owner we meant who sent, not who recieved
	public int getOwner() {

		return msgOwner;
	}

	public String getMessage() {

		return message;
	}

}
