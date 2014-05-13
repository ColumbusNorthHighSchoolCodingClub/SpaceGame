package src.spacegame.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import com.arcadeengine.AnimPanel;

public class ClientRunner {
	
	AnimPanel world = new ClientMain();
	
	// ==============================================================================
	// --- Typically you will never need to edit any of the code below this ---
	// ==============================================================================
	
	private JFrame myFrame;
	
	public ClientRunner() {
	
		this.myFrame = new JFrame();
		this.myFrame.addWindowListener(new Closer());
		
		addFrameComponents();
		
		this.myFrame.pack();
		
		this.myFrame.setVisible(true);
		this.myFrame.setResizable(world.isResizable());
		
		this.myFrame.setLocationRelativeTo(null);
		
		startAnimation();
	}
	
	public void addFrameComponents() {
	
		this.myFrame.setTitle(this.world.getMyName());
		
		this.myFrame.add(this.world);
	}
	
	public void startAnimation() {
	
		Thread anim = new Thread() {
			@Override
			public void run() {
			
				Timer timer = new Timer(1000 / 60, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
					
						if(!ClientRunner.this.myFrame.isResizable())
							ClientRunner.this.myFrame.pack();
						ClientRunner.this.myFrame.getComponent(0).repaint();
						ClientRunner.this.world.process();
					}
				});
				timer.start();
			}
		};

		Thread process = new Thread() {

			@Override
			public void run() {

				Timer timer = new Timer(1000 / ClientRunner.this.world.getTimerDelay(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
					
						ClientRunner.this.world.process();
					}
				});
				timer.start();
			}
		};

		anim.start();
		process.start();
	}
	
	public static void main(String[] args) {
	
		new ClientRunner();
	}
	
	private static class Closer extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
		
			System.out.println("Closing...");
			System.exit(0);
		} // ======================
	}
	
}
