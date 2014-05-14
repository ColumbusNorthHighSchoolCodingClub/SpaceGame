package com.arcadeengine.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.arcadeengine.AnimPanel;

public class GuiHandler {
	private AnimPanel panel;

	private Gui currentGui = null;
	private Gui debugGui = null;

	private Color bg = new Color(68, 68, 68, 160);

	private boolean debugstate = false, debugenabled = false;

	/**
	 * Constructor
	 * 
	 * @param startGui
	 *            The gui to be displayed when first started.
	 */
	public GuiHandler(AnimPanel panel, Gui startGui) {
		this.panel = panel;

		this.currentGui = startGui;
	}

	/**
	 * The current Gui.
	 */
	public Gui getGui() {
		try {
			return currentGui;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public void addDebug(Gui debug) {
		this.debugGui = debug;

		this.debugenabled = true;
	}

	/**
	 * The current state of the Debug screen.
	 */
	public boolean getDebugState() {
		return debugstate;
	}

	/**
	 * Set the state of the debug screen.
	 */
	public void setDebugState(boolean state) {
		debugstate = state;
	}

	/**
	 * Inverts the current state of the debug screen. (ON/OFF)
	 */
	public void invertDebugState() {
		debugstate = !debugstate;
	}

	public Color getBGColor() {
		try {
			return bg;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * The current GUI being displayed.
	 */
	public Gui getCurrentGui() {
		return this.currentGui;
	}

	/**
	 * The last GUI visited.
	 */
	public Gui getPrevGui() {
		try {
			return this.currentGui.getParent();
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Updates everything about the GUI.
	 * 
	 * @param g
	 *            The graphics object.
	 * @param panel
	 *            put 'this' in here if in the class 'Game'.
	 */
	public void drawGui(Graphics g) {
		g.setColor(bg);
		g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

		this.currentGui.drawGui(g);

		if (this.debugstate && debugenabled)
			this.debugGui.drawGui(g);
	}

	public void updateGui() {
		updateBG();
		this.currentGui.updateGui();

		if (this.debugstate && debugenabled)
			this.debugGui.updateGui();
	}

	/**
	 * Updates the color of the GUI to match the currently stored color.
	 */
	public void updateBG() {
		int r = bg.getRed(), g = bg.getGreen(), b = bg.getBlue(), a = bg.getAlpha();

		for (int loop = 0; loop < 5; loop++) {
			if (r < this.currentGui.getBGColor().getRed())
				r++;
			else if (r > this.currentGui.getBGColor().getRed())
				r--;

			if (b < this.currentGui.getBGColor().getBlue())
				b++;
			else if (b > this.currentGui.getBGColor().getBlue())
				b--;

			if (g < this.currentGui.getBGColor().getGreen())
				g++;
			else if (g > this.currentGui.getBGColor().getGreen())
				g--;

			if (a < this.currentGui.getBGColor().getAlpha())
				a++;
			else if (a > this.currentGui.getBGColor().getAlpha())
				a--;
		}

		bg = new Color(r, g, b, a);
	}

	/**
	 * ONLY FOR GUITRANSITION!
	 */
	public void setGui(Gui next) {
		currentGui = next;
	}

	/**
	 * Switches smoothly the Gui to another while preserving the last to be used
	 * for later .
	 * 
	 * @param parent
	 *            The current Gui being displayed. Put "this" here.
	 * @param next
	 *            The Gui you want to switch too. Put "new Gui___()" here.
	 * @return The next Gui with the given parent for use with previousGui()"
	 */
	public void switchGui(Gui next) {
		System.out.println("*** Switching Guis From " + currentGui.getClass().getSimpleName() + " To " + next.getClass().getSimpleName() + " ***");

		next.setParent(currentGui);

		this.currentGui = new GuiTransition(panel, TransitionType.slideLeft, currentGui, next);
	}

	/**
	 * Returns to the last Gui that was visited.
	 * 
	 * @return The last Gui that was visited.
	 */
	public void previousGui() {
		System.out.println("*** Returning to Gui: " + currentGui.getParent().getClass().getSimpleName() + " ***");

		this.currentGui = new GuiTransition(panel, TransitionType.slideRight, currentGui, currentGui.getParent());
	}
}
