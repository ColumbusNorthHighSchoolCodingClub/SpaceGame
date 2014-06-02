package com.arcadeengine.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import com.arcadeengine.AnimPanel;

public class GuiButton extends GuiComponent {
	/** The Outline Rectangle of the Button. */
	protected Rectangle buttonShadow;
	/** The Inner Rectangle of the Button. */
	protected Rectangle button;
	
	/**
	 * Creates a Button.
	 *
	 * @param x
	 *            X Coord of the Button
	 * @param y
	 *            Y Coord of the Button
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param l
	 *            The Label of the button to be used for actions. if there is
	 *            not titles for it.
	 */
	public GuiButton(AnimPanel panel, int x, int y, int w, int h, String l) {
	
		super(panel, l, x, y, w, h);
		
		this.buttonShadow = new Rectangle(x, y, w, h);
		this.button = new Rectangle(x + 2, y + 2, w - 4, h - 4);
		
	}
	
	/**
	 * Creates a Button.
	 *
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param l
	 *            The Label of the button to be used for actions. if there is
	 *            not titles for it.
	 */
	public GuiButton(AnimPanel panel, int w, int h, String l) {
	
		super(panel, l, w, h);
		
		this.buttonShadow = new Rectangle(0, 0, w, h);
		this.button = new Rectangle(0 + 2, 0 + 2, w - 4, h - 4);
	}
	
	// End Constructors ------------------------------------------------
	
	/**
	 * Draws the button on to the screen.
	 *
	 * @param g
	 *            The graphics object
	 */
	@Override
	public void draw(Graphics g) {
	
		// Draws the Button to be clicked upon.
		Graphics2D page = (Graphics2D) g;
		
		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// The Background color of the button.
		page.setColor(secColor);
		page.fill(this.buttonShadow);
		
		// Highlight the button if hovered.
		if(hovered) {
			if(isEnabled() && primColor.getAlpha() != 0)
				page.setColor(primColor.darker());
		}
		else {
			if(isEnabled())
				page.setColor(primColor);
		}
		if(!isEnabled())
			page.setColor(disabledColor);
		
		page.fill(this.button);
		
		Font font = new Font("Verdana", Font.BOLD, 13);
		Font old = g.getFont();
		
		g.setFont(font);
		
		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		Rectangle2D rect = page.getFontMetrics().getStringBounds(label, page);
		
		int drawX = (this.button.width / 2 + this.button.x) - (int) rect.getWidth() / 2;
		int drawY = (this.button.height / 2 + this.button.y) - (int) (rect.getHeight() / 2 + rect.getY());
		
		panel.getGuiHandler().getCurrentGui().drawString(label, font, primColor.brighter().brighter(), drawX, drawY, page);
		
		g.setFont(old);
	}
	
	/**
	 * Draws the button on to the screen.
	 *
	 * @param g
	 *            The graphics object
	 * @param x
	 *            The current x coord in the for loop for auto placement of
	 *            buttons.
	 * @param y
	 *            The current y coord in the for loop for auto placement of
	 *            buttons.
	 */
	@Override
	public void draw(int x, int y, Graphics g) {
	
		this.x = x;
		this.y = y;

		this.button.setLocation(x + 2, y + 2);
		this.buttonShadow.setLocation(x, y);
		
		this.draw(g);
	}
	
	@Override
	public void update() {

		this.buttonShadow.setSize(this.width, this.height);
		this.button.setSize(this.width - 4, this.height - 4);
		
		if(!this.autoplaced) {
			this.buttonShadow.setLocation(x, y);
			this.button.setLocation(x + 2, y + 2);
		}
	}
	
	@Override
	public void onHover() {
	
	}
	
	@Override
	public void onHoverLeave() {
	
	}
	
	/**
	 * True if the pointer is inside of the button area.
	 */
	@Override
	public boolean isHovered() {
	
		Point point = this.panel.getMousePosition();
		
		Rectangle mouse;
		try {
			mouse = new Rectangle(point.x - 2, point.y - 2, 4, 4);
			
			if(mouse.intersects(buttonShadow) && isEnabled())
				return true;
		}
		catch(NullPointerException e) {
		}
		
		return false;
	}
}
