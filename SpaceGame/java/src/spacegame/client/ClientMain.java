package src.spacegame.client;

import java.awt.Graphics;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import src.spacegame.CommHandler;
import src.spacegame.client.gui.GuiDebug;
import src.spacegame.client.gui.GuiLoadingGame;
import src.spacegame.client.gui.GuiMainMenu;
import src.spacegame.server.ServerMain;
import src.spacegame.server.ServerRoster;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.KeyBinding;

public class ClientMain extends AnimPanel {
	
	private static final long serialVersionUID = 5863479337524090281L;
	
	private KeyBinding binding;
	
	private ServerMain locServer;

	private ClientRoster clRoster;
	private ClientInfo clInfo;
	private CommHandler clComm;
	
	private boolean serverRunning = false;
	
	public ClientMain() {
	
		this.createInstance("Space Game", 900, 500);
		this.setResizable(false);
		this.initBindings();

		this.getKeyBoardHandler().addBindings(this.binding);

		this.createGuiHandler(new GuiMainMenu(this));
		this.getGuiHandler().addDebug(new GuiDebug(this));
		this.getGuiHandler().setDebugState(true);
		
		clInfo = new ClientInfo(ClientMain.this);
		clComm = new CommHandler();
		clRoster = new ClientRoster();
	}

	@Override
	public void initRes() {
	
	}
	
	public void initBindings() {

		binding = new KeyBinding(this) {
			
			@Override
			public void onPress(String key) {

				if(key.equals("F1"))
					ClientMain.this.getGuiHandler().invertDebugState();
				else if(key.equals("Ctrl")) {
					
				}
			}
			
			@Override
			public void onRelease(String key) {

				if(key.equals("Ctrl")) {
					
				}
			}
			
			@Override
			public void whilePressed(String key) {
			
			}
		};
		
	}
	
	public ClientInfo getClientInfo() {
	
		return clInfo;
	}
	
	public CommHandler getClientComm() {
	
		return clComm;
	}
	
	public ClientRoster getClientRoster() {

		return clRoster;
	}
	
	/**
	 * Makes an attempt to connect to the given IP Address.
	 * @param ipAddress IP Address of the Server
	 */
	public void connectToServer(final String ipAddress) {
	
		//Create a Thread so that Connecting to the Server Doesn't Stop the Main Process
		Thread sv = new Thread() {
			
			@Override
			public void run() {

				//Removes all periods.
				String temp = ipAddress.replace(".", "");
				
				//The String temp should contain a real number, if it doesn't, tell the client.
				try {
					Integer i = (Integer.parseInt(temp));
				}
				catch(NumberFormatException e) {

					JOptionPane.showMessageDialog(null, "Invalid Address Given!");
					this.interrupt();
					return;
				}

				//The String temp should contain 3 periods, so the the original length minus
				//temp's length has to equal 3. If not, stop and tell the client.
				if((ipAddress.length() - temp.length() != 3)) {
					JOptionPane.showMessageDialog(null, "Invalid Address Given!");
					this.interrupt();
					return;
				}
				
				//The given String is proven to be an address with correct syntax,
				//try to make a connection and tell the user if it can't.
				try {
					Socket socket = new Socket(ipAddress, 4444);
					ClientMain.this.clComm = new CommHandler(socket);
					ClientMain.this.clComm.sendMessage("LOGI" + "." + ClientMain.this.clInfo.getName());

					//If this is able to run, then a connection to the server was made.
					ClientMain.this.getGuiHandler().switchGui(new GuiLoadingGame(ClientMain.this));
				}
				catch(UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Unable to Connect to host. Invalid IP or Network Access Doesn't Exist.");
					System.err.println("Unable to Connect to host. Invalid IP or Network Access Doesn't Exist.");
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to Connect to host. Invalid IP or Network Access Doesn't Exist.");
					System.err.println("IOException in method connectToServer in class ClientMain");
				}
				
				//We're done here. Stop the thread.
				this.interrupt();
			}
		};

		sv.start();
	}

	/**
	 * Prompts the client to enter a valid IP Address.
	 */
	public void connectToServer() {

		connectToServer(JOptionPane.showInputDialog("Please enter the Server IP address", ""));
	}
	
	/**
	 * Safely starts a local server.
	 */
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
	
	/**
	 * Stops the local server if exists.
	 */
	public void stopServer() {

		if(this.serverRunning)
			locServer.stop();
		
		this.serverRunning = false;
	}
	
	/**
	 * Sees if the local server is running or not.
	 * @return true if a local server is running
	 */
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
	
		if(clComm.isConnected()) {
			clComm.sendMessages();
			ArrayList<String> msgs = clComm.getAllMessages();
			
			for(String msg : msgs) {
				String header = msg.substring(0, 4);
				
				if(header.equals(ClientInfo.getHeader())) {
					clInfo.unpack(msg);
					clComm.addMessage("REQU");
				}
				else if(header.equals(ServerRoster.getHeader()))
					clRoster.unpack(msg);
			}
		}
		this.updateGui();
	}
	
	public void close() {

		if(this.clComm.isConnected())
			clComm.close();
		
		System.out.println("Closing...");
		System.exit(0);
	}
}
