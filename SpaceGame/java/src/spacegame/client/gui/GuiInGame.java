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

		this.setTitle("In Game Gui Here");
		this.setTitleColor(new Color(162, 82, 58, 255));
		this.setTitleFont(new Font("Century Gothic", Font.BOLD, 20));
		
		this.setBGColor(81, 41, 21, 200);
	}

	@Override
	public void drawGui(Graphics g) {
	
		this.drawTitle(g, clMain.getWidth());
		
		this.drawComponents(g, (panel.getWidth() / 2) - 110, 175);
	}
	
	@Override
	public void updateGui() {
	
		this.updateComponents();
	}
	
	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {
	
	}
	
}
