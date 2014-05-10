package src.spacegame.client;

import javax.swing.JOptionPane;
import src.spacegame.Packable;

public class ClientInfo implements Packable {
	
	private static final String HEADER_CLASS = "CNFO";
	
	private final char PARSE_CHAR = '$';
	
	private ClientMain clMain;
	
	//TODO: Home Planet
	//TODO: Universe
	//TODO: Market
	//TODO: ArrayList<Ship> myShips
	
	private String clName;
	private int clID;
	
	public ClientInfo(ClientMain clMain) {
	
		this.clMain = clMain;
		
		clName = JOptionPane.showInputDialog("Please Enter your Name (no profanity)", "John Doe");
		this.clMain.getClientComm().addMessage("LOGI" + PARSE_CHAR + clName);
	}
	
	public static String getHeader() {
	
		return HEADER_CLASS;
	}
	
	public String getName() {
	
		return clName;
	}
	
	public int getID() {
	
		return clID;
	}
	
	public String pack() {
	
		//TODO:DEFINE METHOD
		return "";
	}
	
	@Override
	public void unpack(String data) {
	
		//TODO:DEFINE METHOD
	}
}
