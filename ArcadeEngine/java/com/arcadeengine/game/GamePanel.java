package com.arcadeengine.game;

import java.awt.Graphics;
import java.util.ArrayList;

import com.arcadeengine.AnimPanel;

/**
 * Main component for Game aspects of the Arcade Engine.
 * 
 * @author David Baker
 * @version 2.0.1
 */
@SuppressWarnings("serial")
public abstract class GamePanel extends AnimPanel {

	protected ArrayList<Entity> entities;

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
	@Override
	public void createInstance(String name, int width, int height) {
		super.createInstance(name, width, height);

		this.entities = new ArrayList<Entity>();
	}

	@Override
	public Graphics renderFrame(Graphics g) {

		if (entities != null)
			for (Entity ent : entities)
				ent.draw(g);

		return g;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void process() {

		if (entities != null)
			for (Entity ent : entities) {

				ArrayList<Entity> temps = (ArrayList<Entity>) entities.clone();
				temps.remove(ent);

				ent.moveWithVelocity(temps);
			}
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public void addEntity(Entity entity) {
		if (entity != null && !(entities.contains(entity)))
			this.entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		if (this.entities.contains(entity))
			entities.remove(entity);
	}
}
