package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.TransitionType;

public class GuiLoadingGame extends Gui {
	
	private ClientMain clMain = (ClientMain) panel;

	public GuiLoadingGame(AnimPanel panel) {
	
		super(panel);
		
		this.setBGColor(29, 41, 81, 200);
	}

	@Override
	public void drawGui(Graphics g) {
	
		String str = "Loading";

		double t = (5D * Math.abs(Math.sin(Math.toRadians(System.currentTimeMillis() / 20))));

		for(int l = 0; l < t; l++)
			str += ".";

		this.drawString(str, new Font("Verdana", Font.BOLD, 34), Color.ORANGE, 30, 200, g);
	}
	
	private boolean switched = false;
	
	@Override
	public void updateGui() {
	
		if(clMain.getClientInfo().getUniverse() != null && !switched) {
			clMain.getGuiHandler().switchGui(new GuiInGame(panel), TransitionType.fade);
			switched = true;
		}
	}
	
	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {
	
		// TODO Auto-generated method stub
		
	}
	
}
