package com.arcadeengine.gui;

import java.awt.Graphics;
import com.arcadeengine.AnimPanel;

public class GuiTransition extends Gui {
	private Gui current, next;

	private double currX = 0, currY = 0, transspeed = 28;

	private final TransitionType transition;

	public GuiTransition(AnimPanel panel, TransitionType type, Gui current, Gui next) {

		super(panel);

		this.setBGColor(next.getBGColor());
		this.setBGImage(next.getBGImage());

		this.transition = type;
		
		transspeed = panel.getWidth() / 50;

		this.current = current;
		this.next = next;

		this.setTitleFont(null);
	}

	@Override
	public void updateGui() {

		if(transition.equals(TransitionType.slideLeft)) {
			currX -= transspeed * ((panel.getWidth()) / 500D);
		}
		else if(transition.equals(TransitionType.slideRight)) {
			currX += transspeed * ((panel.getWidth()) / 500D);
		}
		else if(transition.equals(TransitionType.slideUp)) {

		}
		else if(transition.equals(TransitionType.slideDown)) {

		}

		this.current.updateGui();
		this.next.updateGui();
	}

	@Override
	public void drawGui(Graphics g) {

		if(current.getParent() == (null))
			drawNext(g);
		else if(!current.getParent().equals(next))
			drawNext(g);
		else
			drawPrev(g);
	}

	private void drawPrev(Graphics g) {

		if(currX <= panel.getWidth()) {
			g.translate((int) currX, 0);
			current.drawGui(g);

			g.translate(-panel.getWidth(), 0);
			next.drawGui(g);

			g.translate(panel.getWidth() - (int) currX, 0);
		}
		else {
			g.translate(0, 0);

			panel.getGuiHandler().setGui(next);
			next.drawGui(g);
		}
	}

	private void drawNext(Graphics g) {

		if(currX >= -panel.getWidth()) {
			g.translate((int) currX, 0);
			current.drawGui(g);

			g.translate(panel.getWidth(), 0);
			next.drawGui(g);

			g.translate(-panel.getWidth() - (int) currX, 0);
		}
		else {
			g.translate(0, 0);
			panel.getGuiHandler().setGui(next);
			next.drawGui(g);
		}
	}

	@Override
	public void actionPerformed(GuiComponent btn, int msBtn) {

	}

}
