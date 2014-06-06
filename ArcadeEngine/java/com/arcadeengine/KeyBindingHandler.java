package com.arcadeengine;

import java.util.ArrayList;

public class KeyBindingHandler {
	private ArrayList<String> keysPressed = new ArrayList<String>();
	private ArrayList<KeyBinding> customBindings = new ArrayList<KeyBinding>();
	
	public void addBindings(KeyBinding binding) {
	
		customBindings.add(binding);
	}
	
	public void runBindings(String key) {
	
		if(!this.keysPressed.contains(key)) {
			keysPressed.add(key);
			for(KeyBinding b : customBindings)
				b.onPress(key);
		}

		for(KeyBinding b : customBindings)
			b.whilePressed(key);
	}
	
	public void removeKey(String key) {
	
		for(KeyBinding b : customBindings)
			b.onRelease(key);
		
		keysPressed.remove(key);
	}
	
}
