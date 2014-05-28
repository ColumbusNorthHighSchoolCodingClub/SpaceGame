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
import src.spacegame.server.ServerMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.KeyBinding;
import com.arcadeengine.gui.TransitionType;

public class ClientMain extends AnimPanel {
	
	private static final long serialVersionUID = 5863479337524090281L;
	
	private static final long UPDATE_DELAY = 600;
	
	private KeyBinding binding;
	
	private ServerMain locServer;

	private ClientInfo clInfo;
	private ClientComm clComm;
	
	private boolean serverRunning = false;
	
	public ClientMain() {
	
		this.createInstance("Space Game", 800, 500);
		this.setResizable(false);
		
		this.setTimerDelay(60);
		
		this.initBindings();

		this.getKeyBoardHandler().addBindings(this.binding);

		this.createGuiHandler(new GuiMainMenu(this));
		this.getGuiHandler().addDebug(new GuiDebug(this));
		this.getGuiHandler().setDebugState(true);
		
		clInfo = new ClientInfo(ClientMain.this);
	}

	@Override
	public void initRes() {
	
	}

	public void initBindings() {

		binding = new KeyBinding(this) {
			
			@Override
			public void singleBinding(String key) {

				if(key.equals("F1"))
					ClientMain.this.getGuiHandler().invertDebugState();
			}
		};
		
	}
	
	public ClientInfo getClientInfo() {
	
		return clInfo;
	}
	
	public ClientComm getClientComm() {
	
		return clComm;
	}
	
	public void connectToServer(final String ipAddress) {

		Thread sv = new Thread() {
			
			@Override
			public void run() {

				if(ipAddress == null)
					return;
				
				if(ipAddress.length() - ipAddress.replace(".", "").length() != 3)
					return;

				try {
					Socket socket = new Socket(ipAddress, 4444);
					ClientMain.this.clComm = new ClientComm(ClientMain.this, socket);
					ClientMain.this.clInfo.init();

					ClientMain.this.getGuiHandler().switchGui(new GuiInGame(ClientMain.this), TransitionType.fade);
				}
				catch(UnknownHostException e) {
					System.err.println("Unable to Connect to host. Invalid IP or Network Access Doesn't Exist.");
				}
				catch(IOException e) {
					System.err.println("IOException in method connectToServer in class ClientMain");
				}
				
				this.interrupt();
			}
		};

		sv.start();
	}
	
	public void connectToServer() {

		connectToServer(JOptionPane.showInputDialog("Please enter the Server IP address", ""));
	}
	
	public void startServer() {

		if(serverRunning == false) {

			Thread th = new Thread() {
				@Override
				public void run() {

					System.out.println("Starting up server...");
					locServer = new ServerMain();
					try {
						serverRunning = true;
						locServer.runServer();
					}
					catch(IOException e) {

						serverRunning = false;
						e.printStackTrace();
					}
				}
			};
			th.start();
		}
	}
	
	public void stopServer() {

		if(this.serverRunning)
			locServer.stop();
		
		this.serverRunning = false;
	}
	
	public boolean isServerRunning() {

		return serverRunning;
	}
	
	@Override
	public Graphics renderFrame(Graphics g) {

		this.calculateRenderFPS();

		this.drawGui(g);
		return g;
	}
	
	@Override
	public void process() {
	
		if(System.currentTimeMillis() % UPDATE_DELAY == 0 && clComm != null) {
			if(clComm.isConnected()) {
				clComm.addMessage("REQU");
				clComm.sendMessages();

				ArrayList<String> msgs = clComm.getAllMessages();

				for(String msg : msgs) {
					String header = msg.substring(0, 4);
					
					//	if(header.equals(Universe.getHeader()))
					
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
