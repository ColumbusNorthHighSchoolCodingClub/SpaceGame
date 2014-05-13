package src.spacegame.client;

import java.awt.Graphics;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import src.spacegame.client.gui.GuiDebug;
import src.spacegame.client.gui.GuiInGame;
import src.spacegame.client.gui.GuiMainMenu;
import src.spacegame.old.Debug;
import src.spacegame.old.SS_Main;
import com.arcadeengine.AnimPanel;

public class ClientMain extends AnimPanel {

	private static final long serialVersionUID = 5863479337524090281L;

	private static final long UPDATE_DELAY = 600;

	private SS_Main locServer;
	
	private ClientInfo clInfo;
	private ClientComm clComm;

	private boolean localServer = false;

	public ClientMain() {

		this.createInstance("Space Game", 800, 500);
		this.setResizable(false);

		this.setTimerDelay(60);
		
		//this.getKeyBoardHandler().addBindings(this.systemBindings);
		
		this.createGuiHandler(new GuiMainMenu(this));
		this.getGuiHandler().addDebug(new GuiDebug(this));
		this.getGuiHandler().setDebugState(true);
	}
	
	@Override
	public void initRes() {

	}
	
	public ClientInfo getClientInfo() {

		return clInfo;
	}

	public ClientComm getClientComm() {

		return clComm;
	}

	public boolean connectToServer() {
	
		String ipAddress = JOptionPane.showInputDialog("Please enter the Server IP address", "");

		try {
			Socket socket = new Socket(ipAddress, 4444);
			clComm = new ClientComm(this, socket);
			clInfo = new ClientInfo(this);

			this.getGuiHandler().switchGui(new GuiInGame(this));
			return true;
		}
		catch(UnknownHostException e) {
			System.out.println("Unable to Connect to host. Invalid IP or Network Access Doesn't Exist.");
		}
		catch(IOException e) {
			System.out.println("IOException in method connectToServer in class ClientMain");
		}

		return false;
	}

	public void startServer() {
	
		if(localServer == false) {
			
			Thread th = new Thread() {
				@Override
				public void run() {
				
					Debug.msg("Starting up server...");
					locServer = new SS_Main();
					try {
						localServer = true;
						locServer.runTheGameServer();
					}
					catch(IOException e) {
						
						localServer = false;
						e.printStackTrace();
					}
				}
			};
			th.start();
		}
	}

	public boolean isServerRunning() {
	
		return localServer;
	}

	@Override
	public Graphics renderFrame(Graphics g) {
	
		this.drawGui(g);
		return g;
	}

	@Override
	public void process() {

		if(System.currentTimeMillis() % UPDATE_DELAY == 0 && clComm != null) {
			if(clComm.isConnected()) {
				clComm.addMessage("REQUEST");
				clComm.sendMessages();
				
				ArrayList<String> msgs = clComm.getAllMessages();
				
				for(String msg : msgs) {
					String header = msg.substring(0, 4);
					
					System.out.println(header);
				}
			}
		}
		
		this.updateGui();
	}

	public void close() {
	
		if(this.clComm != null)
			clComm.close();

		System.out.println("Closing...");
		System.exit(0);
	}
}
