package src.spacegame;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
 
public class NewUniverse implements Packable
{
    //Data about the Universe!
    public static final int NUM_OF_SECTORSX = 10;
    public static final int NUM_OF_SECTORSY = 10;
    private Sector sectors[][] = new Sector[NUM_OF_SECTORSX][NUM_OF_SECTORSY]; //I NEED AN ACESSOR FOR THIS!!!! Austin

    private Sector selectedSector = new Sector();
    private boolean sectorSelected = false;

    //Stuff for the display to work nicely
    UniversePanel universePanel = new UniversePanel();
    Thread animator;
    boolean animate;
    
    //The constructor.
    public NewUniverse()
    {
        createRandom();
//         universePanel = new UniversePanel();
//         start(); //Start the Runnable Thread to keep updating the look of the universe based on mouse input.
    }
 
    public Sector getSector(int x, int y)
    {
        
        if (x<NUM_OF_SECTORSX && x>=0 && y<NUM_OF_SECTORSY && y>=0)
            return sectors[x][y];
        else 
            return new Sector();
    }
    
    private void createRandom()
    {  
        for(int x=0;x<NUM_OF_SECTORSX;x++)
        {
            for(int y=0;y<NUM_OF_SECTORSY;y++)
            {
                sectors[x][y] = new Sector();
                sectors[x][y].createRandom();
            }
        }
    }

    public JPanel getUniversePanel()
    {
        return universePanel;
    }
        
    public void updateFrame()
    {
        universePanel.getUpdatedFrame();
    }

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//  IMPLEMENTS PACKABLE 
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    /**
     * This method packs the data into a String. It loops through the planets and ships and seperates them with a parse char (§).
     * 
     * @param  shipString Packs all the data for all the ships into a String.
     * @return     the entire String, all packed up.
     */
 
    private final char PARSE_CHAR = '*';
   
    public String pack()
    {
        
        String theThingToPack = "UNIV";
        
        for (int x=0;x<NUM_OF_SECTORSX;x++)
        {
            for (int y=0;y<NUM_OF_SECTORSY;y++)
            {
                theThingToPack += sectors[x][y].pack()+PARSE_CHAR;
            }
        }
        return theThingToPack;
    }
    
    /**
     * This method unpacks all the data from a String. It does this by seperating the String into elements using a parse char (§).
     * 
     * @param  inParsed A Vector with all the data parsed into elements.
     * @param  header The header for the Vector, the first element.
     */
    public void unpack(String data)
    {
                 Vector inParsed = ParseUtil.parseStringBySign (data, PARSE_CHAR);
        String header = (String)inParsed.elementAt(0);
        if (header.equals("UNIV"))
        {
             for (int x=0;x<NUM_OF_SECTORSX;x++)
             {
                 for (int y=0;y<NUM_OF_SECTORSY;y++)
                 {
                     sectors[x][y].unpack((String)inParsed.elementAt(x*NUM_OF_SECTORSX+y));
                 }
             }
        }
    }
     
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//..............................................................................................

    
    public JPanel getNorthPanel()//
    {
        //This creates the slider Panel for the top of the display.     
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 6, universePanel.zoomSlider);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        //slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                int value = slider.getValue();
                universePanel.zoomSlider =value;//provides value of zoom factor
            }
        });
        
        final JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 6, universePanel.scrollSlider);
        slider2.setPaintTicks(true);
        slider2.setMajorTickSpacing(1);
        //slider.setPaintLabels(true);
        slider2.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                int value2 = slider2.getValue();
                universePanel.scrollSlider =value2;//provides value of zoom factor
            }
        });
        
        JPanel panel = new JPanel();
        panel.add(new Label("Zoom Factor: "));
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.add(slider);
        panel.add(new Label("Scroll Speed: "));
        panel.add(slider2);
        return panel;
    }
    
     
//****************************************************************************************     
//****************************************************************************************     
//****************************************************************************************     
 class UniversePanel extends JPanel implements MouseListener,MouseMotionListener
 {
    public int zoomSlider=3;
    public int scrollSlider=3;
    private final int bottomX=790;//will change according to my panel
    private final int bottomY=436;//will change according to my panel
    int changingY=0;//top left corner of the universe is what scrolls around
    int changingX=0;// i may need to change the code to make zooming more reliable
    private int mouseX=0, mouseY=0;//where the mouse is at every moment in the game
    int destinationX;
    int destinationY;
    boolean moveable;
     
    
    public UniversePanel()
    {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(800,600)); //Needed to add this to get display right...
    }
     
    
 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         
                                                        
        for(int x=0;x<NUM_OF_SECTORSX;x++)
        {
            for(int y=0;y<NUM_OF_SECTORSY;y++)
            {
                 //sectors[x][y].drawSector(g,50*zoomSlider,50*zoomSlider,changingX+((50*zoomSlider)*x),changingY+((50*zoomSlider)*y),zoomSlider,zoomSlider,universePanel);
//                 
                 
//                  drawSector(g,50*zoomSlider,50*zoomSlider,changingX+((50*zoomSlider)*x),changingY+((50*zoomSlider)*y));
                 //this is where i will have to integrate with muckley and the stuff that he's doing
                 g2.drawString("@ "+x+","+y, (int)(changingX+((50*zoomSlider)*(x+0.5))), (int)(changingY+((50*zoomSlider)*(y+0.5))) );
            }
        }
        g2.drawString("x: "+mouseX,5,120);
        g2.drawString("y: "+mouseY,5,130);
        g2.drawString("zoomSlider: "+zoomSlider,5,140);
        g2.drawString("zoomSlider: "+scrollSlider,5,150);
        g2.drawString("changing x: "+changingX,5,160);
        g2.drawString("changing y: "+changingY,5,170);
        g2.drawString("moveable: "+moveable,5,180);
    }
    

//     public void drawSector(Graphics page,int width, int height,int XLoc,int YLoc)
//     {
//         page.setColor(Color.BLUE);
//         page.drawRect(XLoc,YLoc,width,height);
//     }
    
 
    public void getUpdatedFrame()
    {
        if(moveable)slideAcross();
        repaint();
    }
 
    private void slideAcross()
    {
       if(mouseX>=bottomX)changingX-=scrollSlider*2;//offsets the animation left five pixels
       if(mouseY>=bottomY)changingY-=scrollSlider*2;//offsets the animation up five pixels 
       if(mouseX<=10)changingX+=scrollSlider*2;//offsets the animation right five pixels 
       if(mouseY<=10)changingY+=scrollSlider*2;//offsets the animation down five pixels
       
       //zooming works well but there could be problems because the drwing commences at the 
       //top right corner. this causes awkward problems when zomming in and out such as when
       //completely zoomed out when you zoom out you move more and more towards the bottom right
       
       
       if (changingX<=-NUM_OF_SECTORSX*50*zoomSlider) changingX=-NUM_OF_SECTORSX*50*zoomSlider;//for now these two keep the box from moving off the screen
       if (changingY<=-NUM_OF_SECTORSY*50*zoomSlider) changingY=-NUM_OF_SECTORSY*50*zoomSlider;//eventually will need an offset of the entire thing to see 
                                     //how far over until only the bottom parts are showing
      if (changingX>=5) changingX=5;//for now these two keep the box from moving off the screen
       if (changingY>=0) changingY=0;//eventually will need an offset of the entire thing to see 
                                     //how far over until only the bottom parts are showing
       
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void mouseEntered(MouseEvent e)  
    {
       moveable=true;
    } //Enable scrolling when entered.   
    public void mouseExited(MouseEvent e)   
    { 
       moveable=false;
    } //Don't scroll when exited?   
    public void mousePressed(MouseEvent e) {   }
    public void mouseReleased(MouseEvent e) {   }
    public void mouseClicked(MouseEvent e) 
    {
         destinationX=e.getX();
        destinationY=e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) //Muckley was here
        {
            selectedSector = sectors[mouseX/(50*zoomSlider)][mouseY/(50*zoomSlider)];
            sectorSelected = true;
        }
        if (e.getButton() == MouseEvent.BUTTON2 && sectorSelected == true)
            if (selectedSector.getMyShips(ClientMain.myPlayerNum) != null)
                selectedSector.getMyShips(ClientMain.myPlayerNum).setDest(mouseX/(50*zoomSlider), mouseY/(50*zoomSlider));
    }
    public void mouseDragged(MouseEvent e) {   }
    public void mouseMoved(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
        
        
        

 }//--- end of UniversePanel 'sub'-class ---
     
//****************************************************************************************     
//****************************************************************************************     
//****************************************************************************************     
     
}//--- end of Universe class ---
 
 
