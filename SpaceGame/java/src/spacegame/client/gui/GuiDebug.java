package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;

public class GuiDebug extends Gui {
	private ClientMain demo = (ClientMain) panel;
	
	private String addBreak = "----------------------";
	private String debug[];
	
	public GuiDebug(AnimPanel panel) {
	
		super(panel);
		
		debug = new String[] { "Loading Debug..." };
	}
	
	@Override
	public void drawGui(Graphics g) {
	
		Graphics2D page = (Graphics2D) g;
		
		int height = -4;
		int spacing = -2;
		
		final Font font = new Font("Arial", Font.BOLD, 13);
		
		for(String str : debug) {
			g.setFont(font);
			page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			Rectangle2D rect = g.getFontMetrics().getStringBounds(str, g);
			
			if(font.getSize() <= 20)
				height += rect.getHeight() + 1;
			else
				height += rect.getHeight() + 2;
			
			g.setColor(new Color(85, 85, 85, 190));
			
			this.drawString(str, font, Color.WHITE, this.panel.getWidth() - (int) rect.getWidth() - 3, height, g);
			
			height += spacing;
		}
	}
	
	@Override
	public void updateGui() {
	
		// All of the info to be displayed on the screen if debug is on.
		debug = new String[] {
				"FPS: " + demo.getFPS(),
				"Frame Number: " + demo.getFrameNumber(),
				addBreak, "Mouse X: " + demo.getMousePosition().x,
				"Mouse Y: " + demo.getMousePosition().y,
				"Left Click Held: " + demo.isLeftClickHeld(),
				"Middle Click Held: " + demo.isMiddleClickHeld(),
				"Right Click Held: " + demo.isRightClickHeld(),
				"Clicked on GUI Component: " + demo.isComponentClicked(),
				addBreak,
				"BG Red: " + demo.getGuiHandler().getBGColor().getRed(),
				"BG Green: " + demo.getGuiHandler().getBGColor().getGreen(),
				"BG Blue: " + demo.getGuiHandler().getBGColor().getBlue(),
				"BG Alpha: " + demo.getGuiHandler().getBGColor().getAlpha(),
		};
	}

	@Override
	public void actionPerformed(GuiComponent btn, int msBtn) {
	
	}
}
