package com.arcadeengine.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

import com.arcadeengine.AnimPanel;

public abstract class Entity {

	protected AnimPanel panel;

	protected Point coord, vel;

	protected int width, height;

	protected boolean stuck = false;

	protected Polygon bounds = new Polygon();

	Area test;

	public Entity(AnimPanel panel, int x, int y, int width, int height) {

		this.panel = panel;

		coord = new Point(x, y);

		vel = new Point(0, 0);

		this.width = width;
		this.height = height;

		bounds.addPoint(x, y);
		bounds.addPoint(x + width, y);
		bounds.addPoint(x + width, y + height);
		bounds.addPoint(x, y + height);

		test = new Area(bounds);
	}

	public Polygon getPolygon() {

		return this.bounds;
	}

	public int[] getDimensions() {

		return new int[] { width, height };
	}

	public Point getCoord() {

		return coord;
	}

	public boolean dectectPolyIntersect(Polygon dragPoly, Polygon entPoly) {

		for (int coord = 0, coord2; coord < dragPoly.npoints; coord++) {

			if (coord == dragPoly.npoints - 1)
				coord2 = 0;
			else
				coord2 = coord + 1;

			for (int loop = 0, loop2; loop < entPoly.npoints; loop++) {

				if (loop == entPoly.npoints - 1)
					loop2 = 0;
				else
					loop2 = loop + 1;

				if (Line2D.linesIntersect(dragPoly.xpoints[coord], dragPoly.ypoints[coord], dragPoly.xpoints[coord2], dragPoly.ypoints[coord2],

				entPoly.xpoints[loop], entPoly.ypoints[loop], entPoly.xpoints[loop2], entPoly.ypoints[loop2])) {

					return true;
				}
			}
		}

		return false;
	}

	public Polygon getDragPolygon(int xVel, int yVel) {

		int tempX = coord.x + xVel, tempY = coord.y + yVel;

		Polygon tempPoly = new Polygon();

		if (xVel > 0) {

			tempPoly.addPoint(coord.x, coord.y + height);
			tempPoly.addPoint(coord.x, coord.y);

			if (yVel > 0)
				tempPoly.addPoint(coord.x + width, coord.y);
			else if (yVel < 0)
				tempPoly.addPoint(tempX, tempY);

			tempPoly.addPoint(tempX + width, tempY);
			tempPoly.addPoint(tempX + width, tempY + height);

			if (yVel > 0)
				tempPoly.addPoint(tempX, tempY + height);
			else if (yVel < 0)
				tempPoly.addPoint(coord.x + width, coord.y + height);
			;
		} else if (xVel < 0) {

			tempPoly.addPoint(coord.x + width, coord.y);
			tempPoly.addPoint(coord.x + width, coord.y + height);

			if (yVel < 0)
				tempPoly.addPoint(coord.x, coord.y + height);
			else if (yVel > 0)
				tempPoly.addPoint(tempX + width, tempY + height);

			tempPoly.addPoint(tempX, tempY + height);
			tempPoly.addPoint(tempX, tempY);

			if (yVel < 0)
				tempPoly.addPoint(tempX + width, tempY);
			else if (yVel > 0)
				tempPoly.addPoint(coord.x, coord.y);
		} else {

			if (yVel > 0)
				tempPoly.addPoint(coord.x + width, coord.y);
			else if (yVel < 0)
				tempPoly.addPoint(tempX, tempY);

			if (yVel > 0)
				tempPoly.addPoint(tempX + width, tempY + height);
			else if (yVel < 0)
				tempPoly.addPoint(coord.x, coord.y + height);

			if (yVel > 0)
				tempPoly.addPoint(tempX, tempY + height);
			else if (yVel < 0)
				tempPoly.addPoint(coord.x + width, coord.y + height);
			;

			if (yVel < 0)
				tempPoly.addPoint(tempX + width, tempY);
			else if (yVel > 0)
				tempPoly.addPoint(coord.x, coord.y);
		}

		return tempPoly;
	}

	protected ArrayList<Entity> getCollidedEnts(ArrayList<Entity> entities, int xVel, int yVel) {

		Polygon dragPoly = this.getDragPolygon(xVel, yVel);

		ArrayList<Entity> tempEnts = new ArrayList<Entity>();

		for (Entity ent : entities) {
			final Polygon entPoly = ent.getPolygon();

			if (this.dectectPolyIntersect(dragPoly, entPoly))
				tempEnts.add(ent);
		}

		return tempEnts;
	}

	protected ArrayList<Entity> getCollidedEnts(ArrayList<Entity> entities, int xVel, int yVel, double prop) {

		double xDouble = (xVel) * prop, yDouble = (yVel) * prop;

		return this.getCollidedEnts(entities, (int) xDouble, (int) yDouble);
	}

	public void setVelocity(int xVel, int yVel) {

		vel.x = xVel;
		vel.y = yVel;
	}

	public void moveWithVelocity(ArrayList<Entity> entities) {

		if (this.vel.x == 0 && this.vel.y == 0)
			return;

		if (this.getCollidedEnts(entities, vel.x, vel.y).size() == 0) {

			bounds.translate(vel.x, vel.y);

			coord.x += vel.x;
			coord.y += vel.y;
		} else {

			boolean invLoc = true;

			double prop = .5, loop = 0;

			while (invLoc) {

				if (this.getCollidedEnts(entities, vel.x, vel.y, prop).size() == 0) {

					if (loop >= 15)
						invLoc = false;
					else
						prop += prop / (2);
				} else {
					prop -= prop / (2);
				}

				loop++;
			}

			int newX = (int) ((vel.x) * prop), newY = (int) ((vel.y) * prop);
			;

			bounds.translate(newX, newY);

			coord.x += newX;
			coord.y += newY;

			Random rand = new Random();

			int randX = rand.nextInt(3) - 1;
			int randY = rand.nextInt(3) - 1;

			while (randX == 0 || randY == 0) {
				randX = rand.nextInt(3) - 1;
				randY = rand.nextInt(3) - 1;
			}

			this.setVelocity(vel.x * randX, vel.y * randY);
		}
	}

	public void drawDrag(Graphics g) {

		Polygon poly = this.getDragPolygon(vel.x, vel.y);

		g.setColor(new Color(0, 255, 0, 100));
		g.fillPolygon(poly);

		g.setColor(Color.cyan);
		for (int coord = 0, coord2; coord < poly.npoints; coord++) {

			if (coord == poly.npoints - 1)
				coord2 = 0;
			else
				coord2 = coord + 1;

			g.drawLine(poly.xpoints[coord], poly.ypoints[coord], poly.xpoints[coord2], poly.ypoints[coord2]);
		}
	}

	public abstract void draw(Graphics g);

	public abstract void think();
}
