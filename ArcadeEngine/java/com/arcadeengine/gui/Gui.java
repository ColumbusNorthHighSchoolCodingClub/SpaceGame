package com.arcadeengine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import com.arcadeengine.AnimPanel;

public abstract class Gui {
	protected AnimPanel panel;

	private Color bgColor = new Color(68, 68, 68, 160);

	protected ArrayList<ArrayList<GuiComponent>> componentArrays = new ArrayList<ArrayList<GuiComponent>>();
	protected ArrayList<GuiComponent> components = new ArrayList<GuiComponent>();

	/** The title for each GUI screen **/
	private String title = "Arcade Engine";

	/** The font the title will be displayed with */
	private Font titleFont = new Font("Arial", 3, 65);

	/** Color of the title */
	private Color titleColor = Color.BLUE;

	/** Y Coordinate of the title */
	private int titleY = 150;

	/** The image in the background of the game. **/
	private Image background;

	/** The last Gui Visited **/
	private Gui parent = null;

	public Gui(AnimPanel panel) {

		this.panel = panel;
		componentArrays.add(components); // Add the default array
	}

	/**
	 * Returns the current background image.
	 */
	public Image getBGImage() {

		if(background != null)
			return background;

		return null;
	}

	public void setBGImage(Image background) {

		if(background != null)
			this.background = background;
	}

	/**
	 * Returns the background color.
	 */
	public Color getBGColor() {

		try {
			return bgColor;
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	/**
	 * Sets the background color to the given R-G-B code with alpha
	 * transparency.
	 *
	 * @param r
	 *            Red integer of the given color code.
	 * @param g
	 *            Green integer of the given color code.
	 * @param b
	 *            Blue integer of the given color code.
	 * @param a
	 *            Alpha integer of the given color code.
	 */
	public void setBGColor(int r, int g, int b, int a) {

		this.bgColor = new Color(r, g, b, a);
	}

	/**
	 * Sets the background to the given color.
	 *
	 * @param color
	 *            The color for the background.
	 */
	public void setBGColor(Color color) {

		this.bgColor = color;
	}

	/**
	 * Returns the last visited Gui if applicable.
	 */
	public Gui getParent() {

		return this.parent;
	}

	/**
	 * Usually only used by the GuiHandler class, but feel free to abuse it as
	 * needed.
	 */
	public void setParent(Gui par) {

		this.parent = par;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {

		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {

		this.title = title;
	}

	/**
	 * @return the titleFont
	 */
	public Font getTitleFont() {

		return titleFont;
	}

	/**
	 * @param titleFont
	 *            the titleFont to set
	 */
	public void setTitleFont(Font titleFont) {

		this.titleFont = titleFont;
	}

	/**
	 * @return the titleColor
	 */
	public Color getTitleColor() {

		return titleColor;
	}

	/**
	 * @param titleColor
	 *            the titleColor to set
	 */
	public void setTitleColor(Color titleColor) {

		this.titleColor = titleColor;
	}

	/**
	 * Draws the title text of the GUi.
	 *
	 * @param g
	 *            The graphics object.
	 * @param width
	 *            The Width of the window.
	 */
	protected void drawTitle(Graphics g, int width) {

		Graphics2D page = (Graphics2D) g;

		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setFont(getTitleFont());

		Rectangle2D rect = page.getFontMetrics().getStringBounds(getTitle(), page);

		this.drawString(this.getTitle(), getTitleFont(), this.getTitleColor(), ((width / 2) - ((int) rect.getWidth() / 2)) + 2, titleY + 2, page);
	}

	/**
	 * Draws a given String with a shadow for contrast.
	 *
	 * @param str
	 *            The String to be drawn.
	 * @param p
	 *            The Point to be drawn at.
	 * @param size
	 *            The size of the font to be used.
	 * @param g
	 *            The Graphics object.
	 */
	protected void drawString(String str, int x, int y, Graphics g) {

		drawString(str, new Font("Arial", Font.BOLD, 12), x, y, g);
	}

	protected void drawString(String str, Font font, int x, int y, Graphics g) {

		drawString(str, font, Color.DARK_GRAY, x, y, g);
	}

	protected void drawString(String str, Font font, Color color, int x, int y, Graphics g) {

		Graphics2D page = (Graphics2D) g;
		
		Font old = g.getFont();
		g.setFont(font);

		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.black);
		//		g.drawString(str, x, y);
		//
		//		g.drawString(str, x + 2, y);

		g.drawString(str, x + 1, y + 1);

		//		g.drawString(str, x, y + 2);

		g.setColor(color);
		g.drawString(str, x, y);

		g.setFont(old);
	}

	/**
	 * Draws the buttons that aren't given coordinates at given point.
	 *
	 * @param g
	 *            The current graphics object.
	 * @param buttons
	 *            The array of buttons that the GUI has.
	 * @param x
	 *            X pos where to start auto placing(vertical downwards) the
	 *            buttons.
	 * @param y
	 *            Y pos where to start auto placing(vertical downwards) the
	 *            buttons.
	 */
	protected void drawComponents(Graphics g, ArrayList<GuiComponent> buttons, int x, int y) {

		int height = 0;
		int spacing = 4;

		for(GuiComponent b : buttons)
			if(b.autoplaced == false)
				b.draw(g);
			else {
				b.draw(x, y + height, g);

				height += b.getHeight() + spacing;
			}
	}

	/**
	 * Draws the buttons that aren't given coordinates at given point.
	 *
	 * @param g
	 *            The current graphics object.
	 * @param buttons
	 *            The array of buttons that the GUI has.
	 * @param x
	 *            X pos where to start auto placing(vertical downwards) the
	 *            buttons.
	 * @param y
	 *            Y pos where to start auto placing(vertical downwards) the
	 *            buttons.
	 */
	protected void drawComponents(Graphics g, int x, int y) {

		this.drawComponents(g, components, x, y);
	}

	/**
	 * Used for highlighting the buttons.
	 *
	 * @param mouse
	 *            The Mouse Coordinate
	 */
	protected void updateComponents(ArrayList<GuiComponent> comps) {

		for(GuiComponent b : comps) {
			b.onUpdateDefault(panel.getMousePosition());
			b.update();
		}
	}

	/**
	 * Used for highlighting the buttons.
	 *
	 * @param mouse
	 *            The Mouse Coordinate
	 */
	protected void updateComponents() {

		this.updateComponents(components);
	}
	
	/**
	 * Draw the Gui onto the Screen.
	 *
	 * @param g
	 *            The graphics object.
	 * @param panel
	 *            The game. (put "this" here)
	 */
	public abstract void drawGui(Graphics g);

	/**
	 * Updates for the GUI.
	 *
	 * @param panel
	 *            The game. (put "this" here)
	 */
	public abstract void updateGui();

	/**
	 *
	 * @param btn
	 */
	public final boolean updateOnClick(int btn) {
	
		for(ArrayList<GuiComponent> array : componentArrays) {
			for(GuiComponent component : array) {
				if(component.isHovered()) {
					actionPerformed(component, btn);
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract void actionPerformed(GuiComponent comp, int msBtn);
}
