package com.arcadeengine.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.arcadeengine.AnimPanel;

public class GuiTransition extends Gui {
	private Gui current, next;
	
	private double currX = 0, currY = 0, transspeed = 28;
	private float fade = 0;
	
	private BufferedImage imgCurr, imgNext;

	private final TransitionType transition;
	
	public GuiTransition(AnimPanel panel, TransitionType type, Gui current, Gui next) {
	
		super(panel);
		
		this.setBGColor(next.getBGColor());
		this.setBGImage(next.getBGImage());
		
		this.transition = type;

		transspeed = panel.getWidth() / 50;
		
		this.current = current;
		this.next = next;

		if(type.equals(TransitionType.fade)) {

			imgCurr = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
			imgNext = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);

			current.drawGui(imgCurr.getGraphics());
			next.drawGui(imgNext.getGraphics());
		}
		
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
		else if(transition.equals(TransitionType.fade)) {

			if(fade < 1)
				fade += .01F;
			else
				fade = 1F;
		}
		
		this.current.updateGui();
		this.next.updateGui();
	}
	
	@Override
	public void drawGui(Graphics g) {
	
		if(fade < 1F)
			fade += .01F;
		else
			fade = 1F;
		
		if(current.getParent() == (null))
			drawNext(g);
		else if(!current.getParent().equals(next))
			drawNext(g);
		else
			drawPrev(g);
	}
	
	private void drawPrev(Graphics g) {
	
		Graphics2D p = (Graphics2D) g;
		if(this.transition.equals(TransitionType.fade)) {

			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F - fade));
			p.drawImage(imgCurr, 0, 0, null);
			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
			p.drawImage(imgNext, 0, 0, null);
			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			if(fade == 1) {
				panel.getGuiHandler().setGui(next);
			}
		}
		else {
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
	}
	
	private void drawNext(Graphics g) {
	
		if(this.transition.equals(TransitionType.fade)) {

			Graphics2D p = (Graphics2D) g;

			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.001F - fade));
			p.drawImage(imgCurr, 0, 0, null);
			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
			p.drawImage(imgNext, 0, 0, null);
			p.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			if(fade == 1F) {
				panel.getGuiHandler().setGui(next);
			}
		}
		else {
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
	}
	
	@Override
	public void actionPerformed(GuiComponent btn, int msBtn) {
	
	}
	
}
