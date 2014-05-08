package src.spacegame.old;

import java.util.Vector;
import javax.swing.JOptionPane;

public class Login implements Packable {
	
	private String playerName = "empty"; //MOVED: ClientInfo
	
	/**
	 * Constructor for objects of class Login
	 */
	public Login() {
	
		playerName = askUserLoginInfo();
	}
	
	public Login(boolean in) {
	
		//constructor for a blank login object (server-side)
	}
	
	public String getName() {
	
		return playerName;
	}
	
	private String askUserLoginInfo() {
	
		String playerName = JOptionPane.showInputDialog("Please Enter your Name (no profanity)", "John Doe");
		return playerName;
	}
	
	private static final char PARSE_CHAR = '$';
	
	public String pack() {
	
		String out = "LOGI" + PARSE_CHAR + playerName;
		return out;
	}
	
	public void unpack(String data) {
	
		Vector inParsed = ParseUtil.parseStringBySign(data, PARSE_CHAR);
		String header = (String) inParsed.elementAt(0);
		if(header.equals("LOGI")) {
			playerName = (String) inParsed.elementAt(1);
		}
		else
			System.out.println("parse error (LOGIN.unpackData): ");
	}
	
	public static char getParseChar() {
	
		return PARSE_CHAR;
	}
	
}
