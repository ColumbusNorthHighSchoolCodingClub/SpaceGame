package src.spacegame.client;

import java.awt.Graphics;
import java.util.ArrayList;
import com.arcadeengine.AnimPanel;

public class ClientMain extends AnimPanel {
	
	private static final long serialVersionUID = 5863479337524090281L;
	
	private static final int UPDATE_DELAY = 5500;
	
	private ClientInfo clInfo;
	private ClientComm clComm;
	
	public ClientMain() {
	
		this.createInstance("Space Game", 800, 500);
		this.setResizable(false);
		
		this.setTimerDelay(60);
		
		/*
			this.getKeyBoardHandler().addBindings(this.systemBindings);

			// Un-Comment These Lines to Activate The Gui System

			this.createGuiHandler(new GuiMainMenu(this));
			this.getGuiHandler().addDebug(new GuiDebug(this));
			this.getGuiHandler().setDebugState(true);
		*/
		
		clComm = new ClientComm(this);
		clInfo = new ClientInfo(this);
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
	
	@Override
	public Graphics renderFrame(Graphics g) {
	
		return g;
	}
	
	@Override
	public void process() {
	
		if(System.currentTimeMillis() % UPDATE_DELAY == 0) {
			
			clComm.addMessage("REQUEST");
			clComm.sendMessages();
			
			ArrayList<String> msgs = clComm.getAllMessages();
			
			for(String msg : msgs) {
				//TODO:Update ClientInfo & etc
			}
		}
	}
	
}
