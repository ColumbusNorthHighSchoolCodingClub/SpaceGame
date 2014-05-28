package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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
	
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, clMain.getWidth(), clMain.getHeight());

		g.setColor(new Color(90, 90, 90, 150));
		g.fillRect(0, 0, clMain.getWidth(), 40);
		
		Font font = new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22);

		g.setFont(font);

		String plyName = "Player: " + clMain.getClientInfo().getName();
		
		Rectangle2D rect = g.getFontMetrics().getStringBounds(plyName, g);

		this.drawString(plyName, font, Color.white, 5, (int) rect.getHeight() - 2, g);
		
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
