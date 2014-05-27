package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;

public class GuiInGame extends Gui {

	ClientMain clMain = (ClientMain) panel;
	
	public GuiInGame(AnimPanel panel) {

		super(panel);
		
		this.setBGColor(0, 0, 0, 0);
	}
	
	@Override
	public void drawGui(Graphics g) {
	
		g.setColor(new Color(48, 48, 48, 150));
		g.fillRect(0, 0, clMain.getWidth(), 40);

		this.drawString("Player: " + clMain.getClientInfo().getName(), new Font("Arial", Font.BOLD, 12), Color.white, 5, 15, g);
		
		this.drawComponents(g, (panel.getWidth() / 2) - 100, 175);
	}

	@Override
	public void updateGui() {

		this.updateComponents();
	}

	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {
	
	}

}
