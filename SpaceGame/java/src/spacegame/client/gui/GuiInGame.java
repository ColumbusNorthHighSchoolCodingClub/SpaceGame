package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiButton;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.TransitionType;

public class GuiInGame extends Gui {
	
	ClientMain clMain = (ClientMain) panel;

	private GuiButton back = new GuiButton(panel, 200, 20, "Back");
	
	public GuiInGame(AnimPanel panel) {
	
		super(panel);

		this.setTitle("In Game Gui Here");
		this.setTitleColor(new Color(162, 82, 58, 255));
		this.setTitleFont(new Font("Century Gothic", Font.BOLD, 20));
		
		this.setBGColor(81, 41, 21, 200);
		
		this.components.add(back);
	}

	@Override
	public void drawGui(Graphics g) {
	
		this.drawTitle(g, clMain.getWidth());
		
		g.setColor(new Color(48, 48, 48, 150));
		g.fillRect(0, 0, clMain.getWidth(), 40);
		
		this.drawComponents(g, (panel.getWidth() / 2) - 100, 175);
	}
	
	@Override
	public void updateGui() {
	
		this.updateComponents();
	}
	
	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

		if(back.equals(comp))
			this.clMain.getGuiHandler().previousGui(TransitionType.fade);
	}
	
}
