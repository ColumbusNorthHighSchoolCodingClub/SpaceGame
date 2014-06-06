package com.arcadeengine;

public class KeyBinding {
	
	private AnimPanel panel;

	public KeyBinding(AnimPanel panel) {

		this.setPanel(panel);
	}

	/**
	 * Key binding to be fired upon key being pressed
	 */
	public void onPress(String key) {

	}
	
	/**
	 * Key binding to be fired upon key being released
	 */
	public void onRelease(String key) {

	}

	/**
	 * Key binding to be fired while key is pressed.
	 */
	public void whilePressed(String key) {

	}

	public AnimPanel getPanel() {

		return panel;
	}

	public void setPanel(AnimPanel panel) {

		this.panel = panel;
	}
}
