package src.spacegame.old;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Timer;

public class ClientMain implements ActionListener {
	
	private Timer timer; //REMOVED
	private final int UPDATE_DELAY_IN_MSEC = 5500;
	
	private Universe theUniverse = new Universe(); //MOVED: ClientInfo
	private Market theMarket = new Market(); //MOVED: ClientInfo
	
	private GenericComm comm; //REPLACED: ClientComm
	private ClientDisplay theDisplay; //REPLACED: GUI System
	
	public static int myPlayerNum; //MOVED: ClientInfo
	
	public static void main(String[] args) {
	
		Debug.msg("Welcome to SpaceGame!");
		ClientMain joe = new ClientMain();
		joe.init();
	}
	
	public void init() {
	
		comm = new GenericComm();
		myPlayerNum = comm.getMyNumber();
		Debug.msg("myPlayerNum is: #" + myPlayerNum);
		comm.setDebugValues("C_COMM", 0);
		
		Login myLogin = new Login();
		comm.sendMessage(myLogin.pack());
		
		theDisplay = new ClientDisplay(theUniverse, theMarket);
		Debug.msg("Initializing Complete.");
		
		initTimer();
	}
	
	private void initTimer() {
	
		timer = new Timer(UPDATE_DELAY_IN_MSEC, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
	
		// --SEND/GET UPDATE(s) FROM SERVER-- Called whenever timer goes off (every 5 sec.)
		
		// ==== SEND ALL OF THE MESSAGES WE HAVE ====
		comm.sendOutboxMessages();
		// ==== SEND A REQUEST FOR A UNIVERSAL UPDATE ====
		comm.sendMessage("REQUEST");
		// ==== RECIEVE THE UNIVERSE/ROSTER/MARKET UPDATES ====
		ArrayList<String> responses = comm.getAllMessages();
		// ==== HERE IS WHERE WE UNPACK THE UPDATES FROM THE SERVER ====
		for(int q = 0; q < responses.size(); q++) {
			String header = responses.get(q).substring(0, 4);
			
			if(header.equals("UNIV")) {
				theUniverse.unpack(responses.get(q));
				Debug.msg("Unpacked a Universe");
				theDisplay.updateDisplay();
			}
			else if(header.equals("ROST")) {
				Roster.unpack(responses.get(q));
			}
			else if(header.equals("MARK")) {
				theMarket.unpack(responses.get(q));
			}
			else if(header.equals("SERV")) {
				Vector inParsed = ParseUtil.parseStringBySign(responses.get(q), '-');
				myPlayerNum = Integer.parseInt((String) inParsed.elementAt(1));
			}
			else
				Debug.msg("Unknown header recieved in ClientMain.actionPerformed() ");
		}
		
	}
	
}
