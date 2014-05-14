package com.arcadeengine.game;

import java.awt.Color;
import java.awt.Graphics;

import com.arcadeengine.AnimPanel;

public class Wall extends Entity {

	public Wall(AnimPanel panel, int x, int y, int width, int height) {
		super(panel, x, y, width, height);

	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Color.red);
		g.fillRect(coord.x, coord.y, width, height);
	}

	@Override
	public void think() {

	}

}
