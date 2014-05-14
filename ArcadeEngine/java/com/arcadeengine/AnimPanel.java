package com.arcadeengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiHandler;

/**
 * Main component for Arcade Engine.
 * 
 * @author David Baker
 * @version 2.1.1
 */
@SuppressWarnings("serial")
public abstract class AnimPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	// Variables
	private String myName;

	private int timerDelay = 60;

	private int FPS = 0;
	private long nextFPSTime = 0;
	private int fpsLoop = 0;

	private int frameNumber;

	private Point lastMouseCoord = new Point(-1, -1);

	// Handlers
	protected GuiHandler guiHandler;
	protected KeyBindingHandler kbHandler = new KeyBindingHandler();

	// Booleans
	private boolean paused = true;

	private boolean componentClicked = false;

	private boolean resizable = false;

	public abstract Graphics renderFrame(Graphics g);

	protected void drawGui(Graphics g) {
		if (this.guiHandler != null)
			this.guiHandler.drawGui(g);
	}

	protected void updateGui() {
		if (this.guiHandler != null)
			this.guiHandler.updateGui();
	}

	public abstract void process();

	public abstract void initRes();

	public String getMyName() {

		return myName;
	}

	/**
	 * Constructor for objects of class Game
	 * 
	 * @param name
	 *            The name of the AnimPanel.
	 * @param width
	 *            The width (in pixels) of the AnimPanel.
	 * @param height
	 *            The height (in pixels) of the AnimPanel.
	 */
	protected void createInstance(String name, int width, int height) {
		frameNumber = 0;
		this.myName = name;

		setPreferredSize(new Dimension(width, height));
		// this.setSize(new Dimension(width,height));
		// this.setLocation(80, 80); // move to the right
		setVisible(true); // make it visible to the user
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		// ---LOAD ALL RESOURCES---
		initRes();
	}

	public void createGuiHandler(Gui gui) {

		this.guiHandler = new GuiHandler(this, gui);
	}

	// Getters
	public GuiHandler getGuiHandler() {
		return guiHandler;
	}

	public KeyBindingHandler getKeyBoardHandler() {
		return kbHandler;
	}

	public void setResizable(boolean value) {

		this.resizable = value;
	}

	public boolean isResizable() {

		return this.resizable;
	}

	public void calculateRenderFPS() {

		if (System.currentTimeMillis() >= nextFPSTime) {

			FPS = Integer.valueOf(fpsLoop);

			fpsLoop = 0;

			nextFPSTime = System.currentTimeMillis() + 1000L;
		} else
			fpsLoop++;
	}

	public void setTimerDelay(int delay) {
		this.timerDelay = delay;
	}

	public int getTimerDelay() {
		return timerDelay;
	}

	public int getFPS() {
		return FPS;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPauseState(boolean state) {
		paused = state;
	}

	@Override
	public void paintComponent(Graphics g) {
		frameNumber++;

		if (frameNumber >= 20000)
			frameNumber = 0;

		this.requestFocusInWindow();

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g = renderFrame(g);
	}

	@Override
	public Point getMousePosition() {

		try {
			Point temp = new Point(super.getMousePosition().x + 1 - 1, super.getMousePosition().y + 1 - 1);

			lastMouseCoord = temp;
			return super.getMousePosition();
		} catch (Exception e) {
			try {
				if (!this.lastMouseCoord.equals(null))
					return lastMouseCoord;
			} catch (NullPointerException es) {
			}
		}

		return new Point(-1, -1);
	}

	public boolean isComponentClicked() {

		return this.componentClicked;
	}

	public void setComponentClicked(boolean isClicked) {

		this.componentClicked = isClicked;
	}

	public boolean isLeftClickHeld() {
		return this.leftClickHeld;
	}

	public boolean isRightClickHeld() {
		return this.rightClickHeld;
	}

	public boolean isMiddleClickHeld() {
		return this.middleClickHeld;
	}

	private boolean leftClickHeld = false, rightClickHeld = false, middleClickHeld = false;

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			if (leftClickHeld != true)
				leftClickHeld = true;

		if (e.getButton() == MouseEvent.BUTTON2)
			if (middleClickHeld != true)
				middleClickHeld = true;

		if (e.getButton() == MouseEvent.BUTTON3)
			if (rightClickHeld != true)
				rightClickHeld = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			if (leftClickHeld != false)
				leftClickHeld = false;

		if (e.getButton() == MouseEvent.BUTTON2)
			if (middleClickHeld != false)
				middleClickHeld = false;

		if (e.getButton() == MouseEvent.BUTTON3)
			if (rightClickHeld != false)
				rightClickHeld = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (guiHandler != null)
			componentClicked = guiHandler.getGui().updateOnClick(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());

		this.kbHandler.runBindings(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());
		this.kbHandler.removeKey(key);
	}
}
