package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import src.spacegame.Planet;
import src.spacegame.Sector;
import src.spacegame.Universe;
import src.spacegame.client.ClientInfo;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiButton;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.GuiSlider;

public class GuiInGame extends Gui {
	
	private final ClientMain clMain = (ClientMain) panel;
	
	private GuiSlider scaler = new GuiSlider(panel, "Universe Scale", 150, 40, 0, 14, false);
	private GuiButton mouseHitbox = new GuiButton(panel, 0, 45, panel.getWidth() - 150, panel.getHeight() - 45, "");

	private ArrayList<GuiComponent> menu;
	private GuiButton menuPlType = new GuiButton(panel, 150, 22, "Type: N/A");
	private GuiButton menuPlRarity = new GuiButton(panel, 150, 22, "Rarity: N/A");
	private GuiButton menuClose = new GuiButton(panel, 18, 18, "X");

	private GuiButtonPlanet currPlanet;

	private Point lastMouse, msRef, lastDraw = new Point(5, 45), drawLoc = new Point(5, 45);
	private double lastValue = scaler.getValue(), nextValue = scaler.getValue();

	private boolean scrolling = false;
	
	private ArrayList<GuiComponent> plntButtons;

	public GuiInGame(AnimPanel panel) {
	
		super(panel);
		
		this.setBGColor(0, 0, 0, 0);
		
		mouseHitbox.setColors(new Color(0, 0, 0, 0), new Color(0, 0, 0, 0));
		
		plntButtons = new ArrayList<GuiComponent>();
		menu = new ArrayList<GuiComponent>();

		Universe uni = clMain.getClientInfo().getUniverse();
		for(int r = 0; r < uni.getSectorWidth(); r++)
			for(int c = 0; c < uni.getSectorHeight(); c++) {
				
				Sector sect = uni.getSectors()[r][c];
				for(Planet pl : sect.getPlanets()) {
					pl.initButton(clMain);
					plntButtons.add(pl.getButton());
				}
			}
		
		this.components.add(scaler);
		
		this.menu.add(menuClose);
		this.menu.add(menuPlType);
		this.menu.add(menuPlRarity);

		this.componentArrays.add(menu);
		this.componentArrays.add(plntButtons);
	}

	@Override
	public void drawGui(Graphics g) {
	
		g.setColor(new Color(10, 10, 45));
		g.fillRect(0, 0, clMain.getWidth(), clMain.getHeight());

		this.drawUniverse(g);
		
		if(this.currPlanet != null)
			this.drawComponents(g, menu, currPlanet.getLocX() + currPlanet.getWidth() / 2, currPlanet.getLocY() + currPlanet.getHeight() / 2);
		
		this.drawInterface(g);
		this.drawComponents(g, (panel.getWidth()) - 300, 0);
	}
	
	public void drawUniverse(Graphics g) {
	
		Graphics2D page = (Graphics2D) g;
		
		page.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double sectL = (10D * (scaler.getValue() + 1.5D)) + 30D;
		Universe uni = clMain.getClientInfo().getUniverse();

		for(int r = 0; r < uni.getSectorWidth(); r++)
			for(int c = 0; c < uni.getSectorHeight(); c++) {

				Sector sect = uni.getSectors()[r][c];

				int x = (int) (drawLoc.getX() + (sectL * r)), y = (int) (drawLoc.getY() + (sectL * c));

				g.setColor(Color.DARK_GRAY);
				g.drawRect(x, y, (int) sectL, (int) sectL);

				int tx = 0;
				for(Planet pl : sect.getPlanets()) {
					
					GuiButtonPlanet btn = pl.getButton();
					if(btn == null) {
						
						pl.initButton(clMain);
						plntButtons.add(pl.getButton());
						btn = pl.getButton();
					}

					btn.setLocX(x + tx + 3);
					btn.setLocY(y + 3);

					btn.setWidth((int) ((sectL) / 3D) - 3);
					btn.setHeight((int) ((sectL) / 3D) - 3);

					tx += (int) ((sectL) / 3D);
				}
			}
		this.drawComponents(g, plntButtons, 0, 0);
	}
	
	public void drawInterface(Graphics g) {
	
		g.setColor(new Color(90, 90, 90, 150));
		g.fillRect(0, 0, clMain.getWidth(), 40);
		g.setColor(new Color(60, 60, 60, 150));
		g.fillRect(clMain.getWidth() - 150, 40, 150, clMain.getHeight() - 40);
		
		g.setFont(new Font("Verdana", Font.BOLD, 18));
		Rectangle2D tempR = g.getFontMetrics().getStringBounds("Player List", g);
		this.drawString("Player List", g.getFont(), Color.WHITE, (int) (clMain.getWidth() - tempR.getWidth() - 4), 58, g);
		
		g.setFont(new Font("Verdana", Font.BOLD, 10));
		
		int y = 58 + (int) tempR.getHeight();
		for(ClientInfo info : clMain.getClientRoster().getPlayers()) {
			String name = info.getName();
			if(info.getID() == clMain.getClientInfo().getID())
				name += " (You)";
			
			Rectangle2D rect = g.getFontMetrics().getStringBounds(name, g);
			this.drawString(name, g.getFont(), Color.WHITE, (int) (clMain.getWidth() - rect.getWidth() - 4), y, g);
			
			y += rect.getHeight() + 2;
		}
	}
	
	@Override
	public void updateGui() {
	
		this.updateComponents();
		
		this.updateScale();
		this.updateUniverseLoc();
		this.updatePlanetMenu();

		this.lastMouse = clMain.getMousePosition();
		lastValue = Double.valueOf(scaler.getValue());
	}

	private void updatePlanetMenu() {
	
		if(this.currPlanet == null) {
			for(GuiComponent comp : menu)
				comp.setVisible(false);
		}
		else {
			for(GuiComponent comp : menu) {
				comp.setVisible(true);
				comp.setColors(currPlanet.getPlanet().getType().getColor(), Color.BLACK);
			}
			
			this.menuPlType.setLabel("Type: " + currPlanet.getPlanet().getType().name());
			this.menuPlRarity.setLabel("Rarity: " + currPlanet.getPlanet().getType().getRarity());
			
			this.updateComponents(menu);
		}
	}

	private void updateUniverseLoc() {
	
		if((scaler.getValue() != nextValue) && scrolling == true) {
			
			double sect = (10D * (scaler.getValue() + 1.5D)) + 30D;
			double sectOld = (10D * (lastValue + 1.5D)) + 30D;

			double x = msRef.getX() - ((msRef.getX() - lastDraw.getX()) * sect / sectOld);
			double y = msRef.getY() - ((msRef.getY() - lastDraw.getY()) * sect / sectOld);
			
			drawLoc.setLocation(x, y);
		}

		if(lastMouse != null && mouseHitbox.isHovered()) {
			if(clMain.isLeftClickHeld()) {

				Point temp = clMain.getMousePosition();

				if(temp.distance(lastMouse) > 1.5D)
					drawLoc.setLocation(drawLoc.getX() + (temp.getX() - lastMouse.getX()), drawLoc.getY() + (temp.getY() - lastMouse.getY()));
			}
		}
	}

	private void updateScale() {
	
		if((scaler.getValue() != nextValue) && scrolling) {
			
			double inc = nextValue / (2D * scaler.getMaxValue());
			
			if(nextValue < 1D)
				inc = scaler.getValue() / (2D * scaler.getMaxValue());
			else if(Math.abs(scaler.getValue() - nextValue) < 1D)
				inc = nextValue / (3D * scaler.getMaxValue());
			
			if(nextValue < scaler.getValue())
				inc *= -1D;

			scaler.setValue(scaler.getValue() + inc);
		}
		
		if(Math.abs(scaler.getValue() - nextValue) < .1D) {
			scaler.setValue(nextValue);
			scrolling = false;
		}
	}
	
	@Override
	public void onMouseScroll(MouseWheelEvent e) {

		if(this.mouseHitbox.isHovered())
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

				double amount = (e.getWheelRotation()) * (scaler.getMaxValue() / 10D);
				nextValue = (scaler.getValue() - amount);

				if(nextValue < 0D)
					nextValue = 0D;
				
				if(nextValue > scaler.getMaxValue())
					nextValue = scaler.getMaxValue();

				scrolling = true;
				msRef = (Point) clMain.getMousePosition().clone();
				lastDraw = drawLoc;
			}
	}
	
	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

		if(comp instanceof GuiButtonPlanet)
			currPlanet = (GuiButtonPlanet) comp;
		else if(comp.equals(menuClose))
			this.currPlanet = null;
		
	}
	
}
