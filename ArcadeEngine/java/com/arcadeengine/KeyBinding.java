package com.arcadeengine;

public class KeyBinding {
	
	private AnimPanel panel;

	public KeyBinding(AnimPanel panel) {

		this.setPanel(panel);
	}

	/**
	 * Key binding to be fired only once when key is pressed.
	 */
	public void singleBinding(String key) {
	}

	{

	}

	/**
	 * Key binding to be fired while key is pressed.
	 */
	public void repeatBinding(String key) {

	}

	public AnimPanel getPanel() {
		return panel;
	}

	public void setPanel(AnimPanel panel) {
		this.panel = panel;
	}
}
