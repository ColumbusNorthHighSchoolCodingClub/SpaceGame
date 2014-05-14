package com.arcadeengine;

import java.awt.Graphics;

import com.arcadeengine.gui.Gui;

/**
 * To make a quick window extend this class
 * @author Byron Zaharako
 */
public abstract class ProgramWrapper {

	private AnimPanel panel;

	private ProgramWrapper() {
		panel = new AnimPanel() {
			@Override
			public Graphics renderFrame(Graphics g) {
				super.calculateRenderFPS();
				drawGui(g);
				return g;
			}
			
			@Override
			public void process() {
				if (!isPaused()) {
					onPause();
				}
				updateGui();
			}
			
			@Override
			public void initRes() {}
		};
		panel.createInstance(getProgramName(), getDefaultWidth(), getDefaultHeight());
		panel.setResizable(true);
		panel.setTimerDelay(60);
		panel.getKeyBoardHandler().addBindings(getSystemBindings());
		panel.createGuiHandler(getFirstGui());
		
		panel.setVisible(true);
	}
	
	/**
	 * This will be the first gui to pop up at runtime
	 * @return
	 */
	protected abstract Gui getFirstGui();

	/**
	 * Create system wide keybindings
	 */
	protected abstract KeyBinding getSystemBindings();

	/**
	 * @return The begining width
	 */
	protected abstract int getDefaultWidth();

	/**
	 * @return The begining height
	 */
	protected abstract int getDefaultHeight();

	/**
	 * @return the program name
	 */
	protected abstract String getProgramName();

	protected abstract void onPause();

	public AnimPanel getPanel() {
		return panel;
	}

	public void setPanel(AnimPanel panel) {
		this.panel = panel;
	}	
	
}
