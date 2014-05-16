package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;

public class GuiDebug extends Gui {

	private ClientMain clMain = (ClientMain) panel;

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

		final Font font = new Font("Consolas", Font.BOLD, 13);

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

		String ip = "null";

		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			
		}
		catch(UnknownHostException e) {
		}

		// All of the info to be displayed on the screen if debug is on.
		debug = new String[] {
				"FPS: " + clMain.getFPS(),
				"Frame Number: " + clMain.getFrameNumber(),
				"Player Name: " + clMain.getClientInfo().getName(),
				"IP Address: " + ip,
				"Running Local Server: " + clMain.isServerRunning(),
				addBreak, "Mouse X: " + clMain.getMousePosition().x,
				"Mouse Y: " + clMain.getMousePosition().y,
				"Left Click Held: " + clMain.isLeftClickHeld(),
				"Middle Click Held: " + clMain.isMiddleClickHeld(),
				"Right Click Held: " + clMain.isRightClickHeld(),
				"Clicked on GUI Component: " + clMain.isComponentClicked(),
				addBreak,
				"BG Red: " + clMain.getGuiHandler().getBGColor().getRed(),
				"BG Green: " + clMain.getGuiHandler().getBGColor().getGreen(),
				"BG Blue: " + clMain.getGuiHandler().getBGColor().getBlue(),
				"BG Alpha: " + clMain.getGuiHandler().getBGColor().getAlpha(),
		};
	}
	
	@Override
	public void actionPerformed(GuiComponent btn, int msBtn) {

	}
}
