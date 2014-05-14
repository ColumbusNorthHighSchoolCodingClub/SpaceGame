package com.arcadeengine;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("serial")
@Deprecated
public abstract class AnimationPanel extends AnimPanel {
	protected int mouseX, mouseY;

	public AnimationPanel(String string, int width, int height) {

		this.createInstance(string, width, height);
	}

	@Override
	public void process() {

		mouseX = this.getMousePosition().x;
		mouseY = this.getMousePosition().y;
		return;
	}

	public abstract void initGraphics();

	public abstract void initMusic();

	@Override
	public void initRes() {

		initGraphics();
		initMusic();
	}

	public AudioClip loadClip(String name) {

		try {
			return Applet.newAudioClip(this.getClass().getResource(name));
		} catch (NullPointerException e) {
			System.out.println(name + " Doesn't Exist!");
			return null;
		}
	}
}
