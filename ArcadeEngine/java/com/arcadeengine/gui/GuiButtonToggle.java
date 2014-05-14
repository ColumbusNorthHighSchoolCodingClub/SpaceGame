package com.arcadeengine.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import com.arcadeengine.AnimPanel;

public class GuiButtonToggle extends GuiButton {
	/** The state of the button. */
	private boolean state = false;

	public String titleOn, titleOff;

	/**
	 * Creates a togglable Button.
	 * 
	 * @param x
	 *            The X Coordinate of the Button
	 * @param y
	 *            The Y Coordinate of the Button
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param l
	 *            The Label of the button to be used for actions. if there is
	 *            not titles for it.
	 * @param off
	 *            The string to be displayed if button is off.
	 * @param on
	 *            The string to be displayed if button is on.
	 * @param state
	 *            The starting state of the button.
	 */
	public GuiButtonToggle(AnimPanel panel, int x, int y, int w, int h, String off, String on, boolean state) {
		super(panel, x, y, w, h, "Loading...");

		if (state)
			this.label = on;
		else
			this.label = off;

		this.titleOff = off;
		this.titleOn = on;

		this.state = state;
	}

	/**
	 * Creates a togglable Button.
	 * 
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param l
	 *            The Label of the button to be used for actions. if there is
	 *            not titles for it.
	 * @param off
	 *            The string to be displayed if button is off.
	 * @param on
	 *            The string to be displayed if button is on.
	 * @param state
	 *            The starting state of the button.
	 */
	public GuiButtonToggle(AnimPanel panel, int w, int h, String off, String on, boolean state) {
		super(panel, w, h, "Loading...");

		if (state)
			this.label = on;
		else
			this.label = off;

		this.titleOff = off;
		this.titleOn = on;

		this.state = state;
	}

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

		// The Background color of the button.
		page.setColor(secColor);
		page.fill(this.buttonShadow);

		// The main color of the button.
		if (hovered) {

			if (isEnabled())
				page.setColor(primColor.darker());
			if (!isEnabled())
				page.setColor(disabledColor);
		} else {
			if (isEnabled())
				page.setColor(primColor);
			if (!isEnabled())
				page.setColor(disabledColor);
		}
		page.fill(this.button);

		label = titleOff;
		if (state == true)
			label = titleOn;
		if (state == false)
			label = titleOff;

		Font font = new Font("Arial", Font.BOLD, 14);
		Font old = g.getFont();
		g.setFont(font);

		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Rectangle2D rect = page.getFontMetrics().getStringBounds(label, page);

		int drawX = (this.button.width / 2 + this.button.x) - (int) rect.getWidth() / 2;
		int drawY = (this.button.height / 2 + this.button.y) - (int) (rect.getHeight() / 2 + rect.getY());

		int shad = 1;

		g.setColor(secColor);
		g.drawString(label, drawX, drawY);

		g.drawString(label, drawX + 2, drawY);

		g.drawString(label, drawX + 2, drawY + 2);

		g.drawString(label, drawX, drawY + 2);

		g.setColor(primColor.brighter());
		g.drawString(label, drawX + shad, drawY + shad);

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
		this.button.setLocation(x + 2, y + 2);
		this.buttonShadow.setLocation(x, y);

		this.draw(g);
	}

	public void invertState() {
		this.state = !this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean getState() {
		return this.state;
	}

}
