package com.arcadeengine;

import java.util.ArrayList;

public class KeyBindingHandler {
	private ArrayList<String> keysPressed = new ArrayList<String>();
	private ArrayList<KeyBinding> customBindings = new ArrayList<KeyBinding>();

	public void addBindings(KeyBinding binding) {
		customBindings.add(binding);
	}

	public void runBindings(String key) {
		for (KeyBinding b : customBindings) {
			b.repeatBinding(key);
		}
		if (this.keysPressed.contains(key) == false) {
			keysPressed.add(key);
			singleBindings(key);
		}
	}

	private void singleBindings(String key) {
		for (KeyBinding b : customBindings) {
			b.singleBinding(key);
		}
	}

	public void removeKey(String key) {
		keysPressed.remove(key);
	}

}
