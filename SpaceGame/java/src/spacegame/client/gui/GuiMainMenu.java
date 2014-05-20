/**
 *
 */
package src.spacegame.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import src.spacegame.client.ClientMain;
import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiButton;
import com.arcadeengine.gui.GuiComponent;

/**
 * @author 15bakerd
 *
 */
public class GuiMainMenu extends Gui {

	private GuiButton connect = new GuiButton(panel, 220, 32, "Connect to an IP"),
			conLocal = new GuiButton(panel, 220, 32, "Connet to Your Server"),
			startsv = new GuiButton(panel, 220, 32, "Start a Server..."),
			stopsv = new GuiButton(panel, 0, 0, 100, 32, "Stop Server"),
			setName = new GuiButton(panel, 220, 32, "Player Name: "),
			exit = new GuiButton(panel, 220, 32, "Exit");
	
	private ClientMain clMain = (ClientMain) panel;

	public GuiMainMenu(AnimPanel panel) {

		super(panel);

		this.setBGColor(29, 41, 81, 200);

		this.setTitle("Space Game v0.2");
		this.setTitleColor(new Color(58, 82, 162, 255));
		this.setTitleFont(new Font("Verdana", Font.BOLD, 28));

		connect.setColors(new Color(87, 123, 243, 255), Color.BLACK);
		conLocal.setColors(new Color(100, 87, 243, 255), Color.BLACK);
		startsv.setColors(new Color(123, 87, 243, 255), Color.BLACK);
		stopsv.setColors(new Color(243, 87, 123, 255), Color.BLACK);
		setName.setColors(new Color(87, 200, 100, 255), Color.BLACK);
		exit.setColors(new Color(250, 140, 0, 255), Color.BLACK);

		this.components.add(connect);
		this.components.add(startsv);
		this.components.add(setName);
		this.components.add(exit);
	}

	@Override
	public void drawGui(Graphics g) {

		this.drawTitle(g, panel.getWidth());

		this.drawComponents(g, (panel.getWidth() / 2) - 110, 175);
	}
	
	@Override
	public void updateGui() {

		if(clMain.isServerRunning()) {
			startsv.setLabel("Server Running");
			startsv.setWidth(120);
			startsv.disable();
			
			stopsv.setLocX(startsv.getLocX() + startsv.getWidth());
			stopsv.setLocY(startsv.getLocY());
			
			if(!this.components.contains(conLocal))
				components.add(1, conLocal);

			if(!this.components.contains(stopsv))
				components.add(stopsv);
		}
		else {
			startsv.setLabel("Start a Server...");
			startsv.setWidth(220);
			startsv.enable();

			if(this.components.contains(conLocal))
				components.remove(conLocal);
			
			if(this.components.contains(stopsv))
				components.remove(stopsv);
		}
		
		setName.setLabel("Player Name: " + clMain.getClientInfo().getName());
		
		this.updateComponents();
	}
	
	@Override
	public void actionPerformed(GuiComponent btn, int msBtn) {
	
		if(btn.equals(connect))
			clMain.connectToServer();
		else if(btn.equals(conLocal))
			clMain.connectToServer(clMain.getClientInfo().getIP());
		else if(btn.equals(startsv))
			clMain.startServer();
		else if(btn.equals(stopsv))
			clMain.stopServer();
		else if(btn.equals(setName)) {
			String str = JOptionPane.showInputDialog("Type Your Name... [Longer Than 2 Letters]", "(Ñø Þrófáñítý)");
			if(str != null)
				if("(Ñø Þrófáñítý)".contains(str))
					str = "";
			this.clMain.getClientInfo().setName(str);
		}
		else if(btn.equals(exit))
			clMain.close();
	}
}
