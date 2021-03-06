package com.arcadeengine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.ResourceUtil;

public class GuiSlider extends GuiComponent {
	private Image handleImage = ResourceUtil.loadInternalImage("com.arcadeengine.res", "sliderHandle.png");
	private Rectangle handle = new Rectangle(handleImage.getWidth(null), handleImage.getHeight(null)), boxOutline, boxFill, bar;

	private double scale, valueLimit = 10, value;

	private boolean snap = true;

	/**
	 * Creates a slider with a specific coordinate.
	 *
	 * @param x
	 *            x Coord of the slider.
	 * @param y
	 *            y Coord of the slider.
	 * @param w
	 *            Width of the slider.
	 * @param h
	 *            Height of the slider, 38 is recommended.
	 * @param startValue
	 *            Starting value of the slider.
	 * @param valueLimit
	 *            Max value of the slider.
	 * @param snap
	 *            Snap to ticks or not, snapping recommended.
	 * @param l
	 *            Label of the slider.
	 */
	public GuiSlider(AnimPanel panel, String label, double x, int y, double w, int h, double startValue, double valueLimit, boolean snap) {

		super(panel, label, (int) x, y, (int) w, h);

		this.valueLimit = valueLimit;

		this.scale = (w - 24) / this.valueLimit;

		if(startValue < 0)
			this.value = 0;
		else if(startValue > this.valueLimit)
			this.value = this.valueLimit;
		else
			this.value = startValue;

		this.bar = new Rectangle((int) (x + 4), y + 8, (int) (w - 24), 6);

		this.boxOutline = new Rectangle((int) x, y, width, height);
		this.boxFill = new Rectangle((int) (x + 2), y + 2, width - 4, height - 4);

		this.autoplaced = false;
		this.snap = snap;
	}

	/**
	 * Creates a slider that will be auto-placed.
	 *
	 * @param w
	 *            Width of the slider.
	 * @param h
	 *            Height of the slider, 38 is recommended.
	 * @param startValue
	 *            Starting value of the slider.
	 * @param valueLimit
	 *            Max value of the slider.
	 * @param snap
	 *            Snap to ticks or not, snapping is recommended.
	 * @param l
	 *            Label of the slider.
	 */
	public GuiSlider(AnimPanel panel, String l, double w, int h, double startValue, double valueLimit, boolean snap) {

		super(panel, l, (int) w, h);

		this.valueLimit = valueLimit;

		this.scale = (w - 24) / this.valueLimit;

		if(startValue < 0)
			this.value = 0;
		else if(startValue > this.valueLimit)
			this.value = this.valueLimit;
		else
			this.value = startValue;

		this.bar = new Rectangle(0 + 4, 0 + 8, (int) (w - 24), 6);

		this.boxOutline = new Rectangle(0, 0, width, height);
		this.boxFill = new Rectangle(0 + 2, 0 + 2, width - 4, height - 4);

		this.autoplaced = true;
		this.snap = snap;
	}

	/**
	 * Returns the current value of the slider.
	 */
	public double getValue() {

		return value;
	}

	/**
	 * Sets the current value of the slider to the give double.
	 */
	public void setValue(double value) {

		if(value < 0)
			this.value = 0;
		else if(value > this.valueLimit)
			this.value = valueLimit;
		else
			this.value = value;
	}

	public double getMaxValue() {
	
		return valueLimit;
	}

	@Override
	public void draw(Graphics g) {

		this.draw(boxOutline.x, boxOutline.y, g);
	}

	@Override
	public void draw(int x, int y, Graphics g) {

		Graphics2D page = (Graphics2D) g;

		this.bar.setLocation(x + 4, y + 8);

		this.boxFill.setLocation(x + 2, y + 2);

		this.boxOutline.setLocation(x, y);

		this.handle.setLocation((int) (bar.x + (this.value * scale)), bar.y);

		page.setColor(secColor);

		page.fill(this.boxOutline);

		if(isEnabled())
			page.setColor(primColor);
		if(!isEnabled())
			page.setColor(disabledColor);

		page.fill(this.boxFill);

		page.setColor(new Color(99, 130, 191));
		
		Rectangle tempBar = new Rectangle(x + 12, 8, (int) bar.getWidth(), (int) bar.getHeight());
		
		page.fill(tempBar);
		
		page.drawImage(handleImage, handle.x, handle.y, null);

		page.setColor(this.secColor);

		Font font = new Font("Noto Sans", Font.BOLD, 14);
		Font old = g.getFont();
		g.setFont(font);

		String val = "" + value;
		
		ArrayList<Character> chars = new ArrayList<Character>();
		
		for(int loop = 0; loop < val.length(); loop++) {
			
			Character ch = val.toCharArray()[loop];
			if(ch.equals('.')) {
				
				chars.add('.');
				chars.add(val.toCharArray()[loop + 1]);
				break;
			}
			else
				chars.add(ch);
		}
		
		String str = label + ": ";
		
		for(Character ch : chars)
			str += ch;

		Rectangle2D rect = page.getFontMetrics().getStringBounds(str, page);

		int drawX = (this.boxOutline.width / 2 + this.boxOutline.x) - (int) rect.getWidth() / 2;
		int drawY = this.boxOutline.y + this.boxOutline.height - 5;

		int shad = 1;

		this.panel.getGuiHandler().getCurrentGui().drawString(str, font, primColor.brighter().brighter(), drawX, drawY, page);
		
		page.setFont(old);
	}

	@Override
	public void onHover() {

	}

	@Override
	public void onHoverLeave() {

	}
	
	@Override
	public void update() {

		if(isHovered() && panel.isLeftClickHeld()) {

			Point point = this.panel.getMousePosition();

			int x = point.x - this.handleImage.getWidth(null) / 2;

			if(x < bar.x || ((x - bar.x) / scale) < 0)
				this.value = 0;

			else if(x > bar.x + bar.width || ((x - bar.x) / scale) > valueLimit)
				this.value = this.valueLimit;

			else if(snap)
				this.value = (int) ((x - bar.x) / scale);

			else
				this.value = (x - bar.x) / scale;
		}

	}

	@Override
	public boolean isHovered() {

		Point point = this.panel.getMousePosition();
		Rectangle mouse;
		try {
			mouse = new Rectangle(point.x - 2, point.y - 2, 4, 4);

			if(mouse.intersects(boxOutline) && this.isEnabled())
				return true;
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}

		return false;
	}

}
