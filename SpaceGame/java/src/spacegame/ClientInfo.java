package src.spacegame;

import javax.swing.JOptionPane;

public class ClientInfo implements Packable {
	
	private static final char PARSE_CHAR = '$';
	
	private ClientMain clMain;
	
	//TODO: Universe
	//TODO: Market
	
	private String clName;
	private int clID;
	
	public ClientInfo(ClientMain clMain) {
	
		this.clMain = clMain;
		
		clName = JOptionPane.showInputDialog("Please Enter your Name (no profanity)", "John Doe");
		this.clMain.getClientComm().addMessage("LOGI" + PARSE_CHAR + clName);
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
