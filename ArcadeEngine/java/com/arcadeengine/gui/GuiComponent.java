package com.arcadeengine.gui;

import java.awt.Color;
import java.awt.Graphics;
import com.arcadeengine.AnimPanel;

public abstract class GuiComponent {
	protected AnimPanel panel;
	
	protected Color currColor = new Color(102, 185, 204);
	
	protected Color primColor = new Color(102, 185, 204);
	protected Color secColor = new Color(17, 23, 24);
	protected Color disabledColor = new Color(60, 101, 228);
	
	protected String label;
	
	protected int x;
	protected int y;
	
	protected int height;
	protected int width;
	
	protected boolean enabled = true;
	protected boolean visible = true;
	protected boolean autoplaced;
	protected boolean hovered = false;
	
	public GuiComponent(AnimPanel panel, String label, int x, int y, int width, int height) {
	
		this(panel, label, width, height);
		
		this.label = label;
		
		this.x = x;
		this.y = y;
		
		this.autoplaced = false;
	}
	
	public GuiComponent(AnimPanel panel, String label, int width, int height) {
	
		this.panel = panel;
		
		this.label = label;
		
		this.width = width;
		this.height = height;
		
		this.autoplaced = true;
	}
	
	/**
	 * Draw the component onto the Screen.
	 *
	 * @param g
	 *            The graphics object.
	 * @param panel
	 *            The game. (put "this" here)
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * Draw the component onto the Screen.
	 *
	 * @param g
	 *            The graphics object.
	 * @param panel
	 *            The game. (put "this" here)
	 */
	public abstract void draw(int x, int y, Graphics g);
	
	/**
	 * Update for when the component is hovered
	 */
	public abstract void onHover();
	
	public abstract void onHoverLeave();

	public void onClick(int msButton) {

	}
	
	/**
	 * Updates the component every tick
	 */
	public abstract void update();
	
	/**
	 * Returns true if the mouse is on the component.
	 */
	public abstract boolean isHovered();
	
	public int getHeight() {
	
		return this.height;
	}
	
	public int getWidth() {
	
		return this.width;
	}
	
	public void setWidth(int w) {
	
		this.width = w;
	}

	public void setHeight(int h) {
	
		this.height = h;
	}
	
	public int getLocX() {
	
		return x;
	}

	public int getLocY() {
	
		return y;
	}

	public boolean isVisible() {
	
		return this.visible;
	}

	public void setLocX(int x) {

		this.x = x;
	}
	
	public void setLocY(int y) {

		this.y = y;
	}
	
	public void setLabel(String label) {
	
		this.label = label;
	}
	
	/**
	 * Sets the Primary and Secondary Color of the button.
	 *
	 * @param prim
	 *            Primary Fill Color of the Button.
	 * @param sec
	 *            Secondary Color for the Highlight and Text of the Button.
	 */
	public void setColors(Color prim, Color sec) {
	
		this.primColor = prim;
		this.secColor = sec;
	}
	
	/**
	 * Sets the Primary of the Button.
	 *
	 * @param prim
	 *            Primary Fill Color of the Button.
	 */
	public void setPrimColor(Color prim) {
	
		this.primColor = prim;
	}
	
	/**
	 * Sets the Secondary of the Button.
	 *
	 * @param sec
	 *            Secondary Color for the Highlight and Text of the Button.
	 */
	public void setSecColor(Color sec) {
	
		this.secColor = sec;
	}
	
	/**
	 * Set the button to be hovered or not.
	 */
	public void setHovered(boolean state) {
	
		this.hovered = state;
	}
	
	/**
	 * Set the button to be enabled or not.
	 */
	public void setEnabled(boolean enabled) {
	
		this.enabled = enabled;
	}
	
	public void setVisible(boolean isVisible) {
	
		this.visible = isVisible;
	}

	/**
	 * Returns true if the button is disabled.
	 */
	public boolean isEnabled() {
	
		return this.enabled;
	}
	
	public void onUpdateDefault() {
	
		if(this.isHovered()) {
			if(this.hovered != true) {
				this.onHover();
				this.setHovered(true);
			}
		}
		else {
			if(this.hovered != false) {
				this.onHoverLeave();
				this.setHovered(false);
			}
		}
	}
	
	/**
	 * Disable the button.
	 */
	public void disable() {
	
		this.enabled = false;
	}
	
	/**
	 * Enabel the button.
	 */
	public void enable() {
	
		this.enabled = true;
	}
	
}
