package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import src.spacegame.Planet;
import src.spacegame.Sector;
import src.spacegame.Universe;
import src.spacegame.client.ClientInfo;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.GuiSlider;

public class GuiInGame extends Gui {
	
	ClientMain clMain = (ClientMain) panel;
	
	private GuiSlider scaler = new GuiSlider(panel, "Universe Scale", 150, 40, 0, 10, false);

	public GuiInGame(AnimPanel panel) {
	
		super(panel);

		this.setBGColor(0, 0, 0, 0);

		this.components.add(scaler);
	}

	@Override
	public void drawGui(Graphics g) {

		g.setColor(new Color(10, 10, 45));
		g.fillRect(0, 0, clMain.getWidth(), clMain.getHeight());
		
		this.drawUniverse(g);

		this.drawInterface(g);

		this.drawComponents(g, (panel.getWidth()) - 300, 0);
	}

	public void drawUniverse(Graphics g) {

		Graphics2D page = (Graphics2D) g;

		page.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(clMain.getClientInfo().getUniverse() != null) {
			
			double scale = (10D * (scaler.getValue() + 1.5D)) + 30D;
			Universe uni = clMain.getClientInfo().getUniverse();

			for(int r = 0; r < uni.getSectorWidth(); r++)
				for(int c = 0; c < uni.getSectorHeight(); c++) {

					int x = (int) (5D + (scale * r)), y = (int) (45D + (scale * c));
					Sector sect = uni.getSectors()[r][c];

					g.setColor(Color.DARK_GRAY);
					g.drawRect(x, y, (int) scale, (int) scale);
					
					int tx = 0;
					for(Planet pl : sect.getPlanets()) {

						page.setColor(pl.getType().getColor());
						page.fillOval(x + tx + 3, y + 3, (int) ((scale) / 3D) - 3, (int) ((scale) / 3D) - 3);

						tx += (int) ((scale) / 3D);
					}
				}
		}
	}
	
	public void drawInterface(Graphics g) {
	
		g.setColor(new Color(90, 90, 90, 150));
		g.fillRect(0, 0, clMain.getWidth(), 40);
		g.fillRect(clMain.getWidth() - 100, 40, 100, clMain.getHeight() - 40);

		g.setFont(new Font("Verdana", Font.BOLD, 10));

		int y = 55;
		for(ClientInfo info : clMain.getClientRoster().getPlayers()) {

			String name = info.getName();
			if(info.getID() == clMain.getClientInfo().getID())
				name += " (You)";

			Rectangle2D rect = g.getFontMetrics().getStringBounds(name, g);

			this.drawString(name, g.getFont(), Color.WHITE, (int) (clMain.getWidth() - rect.getWidth() - 2), y, g);

			y += rect.getHeight() + 2;
		}
	}

	@Override
	public void updateGui() {
	
		this.updateComponents();
	}
	
	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

	}
	
}
