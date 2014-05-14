package com.arcadeengine.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.arcadeengine.AnimPanel;

public class Tile extends Entity {

	private BufferedImage initImage, /* bounds, */background, foreground;

	public Tile(AnimPanel panel, Image img, int x, int y) {
		super(panel, x, y, 100, 75);

		this.loadImage(img);

	}

	public BufferedImage getRawImage() {

		return initImage;
	}

	public BufferedImage getForeGround() {

		return this.foreground;
	}

	public BufferedImage getBackGround() {

		return this.background;
	}

	@Override
	public void draw(Graphics g) {
		return;
	}

	private void loadImage(Image img) {

		initImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		initImage.getGraphics().drawImage(img, 0, 0, null);

		for (int y = 0, startHeight = 0, image = 0; y < initImage.getHeight(); y++) {

			if (getPixelColor(0, y).equals(Color.RED))
				if (getPixelColor(1, y).equals(Color.GREEN))
					if (getPixelColor(2, y).equals(Color.BLUE)) {

						System.out.println(y - startHeight);

						if (image == 0) {

							// bounds = this.filterImage(initImage.getWidth(),
							// y, 0);
							startHeight = y + 1;
							image++;
						} else if (image == 1) {

							background = this.filterImage(initImage.getWidth(), y - startHeight, startHeight);
							startHeight = y + 1;
							foreground = this.filterImage(initImage.getWidth(), initImage.getHeight() - startHeight, startHeight);
							return;
						}
					}
		}
	}

	private BufferedImage filterImage(int w, int h, int startHeight) {

		if (w == 0 || h == 0)
			return null;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		image.getGraphics().drawImage(initImage, 0, -startHeight, null);

		return image;
	}

	private Color getPixelColor(int x, int y) {

		return new Color(initImage.getRGB(x, y));
	}

	@Override
	public void think() {
		// TODO Auto-generated method stub

	}

}
