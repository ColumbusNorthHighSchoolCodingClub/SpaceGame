package src.spacegame.client.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import src.spacegame.Planet;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.GuiComponent;

public class GuiButtonPlanet extends GuiComponent {
	
	private final ClientMain clMain = (ClientMain) panel;
	
	private final Planet myPlanet;
	
	public GuiButtonPlanet(AnimPanel panel, Planet plnt) {

		super(panel, plnt.getType().name(), 0, 0, 0, 0);

		this.myPlanet = plnt;

	}

	public Planet getPlanet() {
	
		return myPlanet;
	}

	@Override
	public void draw(Graphics g) {

		Graphics2D page = (Graphics2D) g;

		page.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		page.setColor(myPlanet.getType().getColor());
		page.fillOval(x, y, width, height);
	}
	
	@Override
	public void draw(int x, int y, Graphics g) {
	
		this.setLocX(x);
		this.setLocY(y);
		this.draw(g);
	}
	
	@Override
	public void onHover() {
	
	}
	
	@Override
	public void onHoverLeave() {
	
	}
	
	@Override
	public void update() {
	
	}
	
	@Override
	public boolean isHovered() {

		Point point = this.panel.getMousePosition();
		Rectangle mouse = new Rectangle(point.x - 2, point.y - 2, 4, 4);
		
		if(mouse.intersects(new Rectangle(this.x, this.y, this.width, this.height)) && isEnabled())
			return true;

		return false;
	}
	
}
