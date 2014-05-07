package src.spacegame.old;
/**
 * Write a description of class Planet here.
 * 
 * @author Austin Funcheon
 * @version (v.5)
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.ArrayList;

public class editPlanet implements Packable
{
        // These are the information stored by a planet
        private int controlledBy;
        private int baseOffense;
        private int baseDefense;
        private int fuelCapacity;
        private int materialCapacity;
        private int planetType; //what "class of planet" is it?
        
        static int max_Planets = 1000;
       // static ArrayList<Integer> planetChoice = new ArrayList<Integer>();
      //  static Image smallPlanets[] = new Image [20];
       // static Image largePlanets[] = new Image [20];
     
        public static final Random randy = new Random();
        private String Data=("");
        //planet types possible. Each has different amount of material or fuel, different percents, defense, offense, etc.
        
        public editPlanet()
        {    
         int z = 0;
         
         z = randy.nextInt(1000);
         System.out.println("Planet" + z);
         supplyPlanet(z);
        }
    
         public static void initializePlanets()
    {   
       // loadGraphics();  
      //  generateWorld();  
        
    }
    
    
      /**
     * 
     *This get called to supply a planet. Need a planet made? Call this!
     */
        public void supplyPlanet(int z)
        { 
            if(z== 0)
            { 
                createVeronian();
              //  System.out.println("Veronian");
            }
            
             if(z>0 && z<=5)
             {    
                 createAdonis();
              //   System.out.println("Adonis");
            }
            
              if(z>5 && z<=15)
             {           
                 createEris();   
               //  System.out.println("Eris");
            }
            
              if(z>5 && z<=25)
             {        
                 createFiery();
                // System.out.println("Fiery");
            }
            
               if(z>25 && z<=100)
             {            
                 createTierran();
                // System.out.println("Tierran");
            }
            
               if(z>100 && z<=200)
             {            
                 createThesban();
                // System.out.println("Thesban");
            }
            
               if(z>200 && z<=300)
             {           
                 createTomeko();
                // System.out.println("Tomeko");
            }
            
               if(z>300 && z<=500)
             {            
                 createRing();
                 //System.out.println("ring");
            }
            
               if(z>500 && z<=515)
             {          
                 createCrack();
                // System.out.println("crack");
            }
            
               if(z>515 && z<=530)
             {            
                 createTitan(); 
                // System.out.println("titan");
            }
               if(z>530 && z<=560)
             {         
                 createStealth();
                 //System.out.println("stealth");
            }
            
               if(z>560 && z<=610)
             {          
                 createSpartan();  
                // System.out.println("spartan");
            }
            
               if(z>610 && z<=655)
             {          
                 createIonis();  
                // System.out.println("ionis");
            }
            
               if(z>655 && z<=700)
             {           
                 createPeyo(); 
                // System.out.println("peyo");
            }
            
               if(z>700 && z<=750)
             {            
                 createRiehl();  
                // System.out.println("riehl");
            }
            
               if(z>750)
             {          
                 createSarena();  
                // System.out.println("sarena");
            }   
            
          
        }
        
        
            /**
     * This method finds who controls the Planet.
     */
    public int getControlledBy()
    {
    return controlledBy;
    }
    
        /**
     * This method finds how much offense the base has.
     */
    public int getbaseOffense()
    {
    return baseOffense;
    }
    
      
        /**
     * This method finds how much defense the base has.
     */
    public int getbaseDefense()
    {
    return baseDefense;
    }
    
    
        /**
     * This method finds how much total 
     * fuel per cycle is present on the planet.
     */
    public int getFuelCapacity()
    {
    return fuelCapacity;
    }
         
    
         /**
     * This method finds how much total 
     * material per cycle is present on the planet.
     */
    public int getMaterialCapacity()
    {
    return materialCapacity;
    }
    
       /**
     * This sets who owns the planet.
     */
    public void setControlledBy(int playerNumber)
    {
    controlledBy = playerNumber;
    }
    
        
     /**
     * This generates the sector image for the specific planet. Will generate from 10,10 on the sector list .
     */
    public Graphics wantSmallPlanetFrame(Graphics g, int x, int y, int zoomFactor)
        {
          //p.getGraphics().drawImage(smallPlanets[planetType], x, y, zoomFactor*25, zoomFactor*25, p);
      // darkgray gray lightgrey orange pink blue red black white
        
          g.setColor(Color.blue);
          
      if (planetType == VERONIAN)
            {g.setColor(Color.orange);
              g.drawOval(x+8*zoomFactor, y+8*zoomFactor, 20*zoomFactor, 20*zoomFactor);
              g.fillOval(x+8, y+8, 20*zoomFactor, 20*zoomFactor);  
           //   System.out.println("type" + planetType);
            }
            
      if (planetType ==  ERIS)
           {g.setColor(Color.blue); 
             g.drawOval(x, y, 14*zoomFactor, 14*zoomFactor);
             g.fillOval(x, y, 14*zoomFactor, 14*zoomFactor);  
            //  System.out.println("type" + planetType);
            }
            
      if (planetType == TIERRAN)
           {g.setColor(Color.red); 
             g.drawOval(x, y, 23*zoomFactor, 23*zoomFactor);
             g.fillOval(x, y, 23*zoomFactor, 23*zoomFactor); 
             // System.out.println("type" + planetType);
            }
            
      if (planetType == ADONIS)
           {g.setColor(Color.green); 
             g.drawOval(x, y, 20*zoomFactor, 20*zoomFactor);
             g.fillOval(x, y, 20*zoomFactor, 20*zoomFactor); 
             // System.out.println("type" + planetType);
            }
            
      if (planetType == THESBAN)
           {g.setColor(Color.magenta); 
             g.drawOval(x, y, 9*zoomFactor, 9*zoomFactor);
             g.fillOval(x, y, 9*zoomFactor, 9*zoomFactor);  
             // System.out.println("type" + planetType);
            }
            
      if (planetType == TOMEKO)
           {g.setColor(Color.gray); 
             g.drawOval(x, y, 22*zoomFactor, 22*zoomFactor);
             g.fillOval(x, y, 22*zoomFactor, 22*zoomFactor);  
            g.setColor(Color.red); 
             g.drawOval(x, y, 9*zoomFactor, 9*zoomFactor);
             g.fillOval(x, y, 9*zoomFactor, 9*zoomFactor);  
            //  System.out.println("type" + planetType);
            }
            
      if (planetType == RING)
           {
             int moo = randy.nextInt(4)+1;
             if(moo == 1)
             {g.setColor(Color.blue);  
             }
             if(moo == 0)
             {g.setColor(Color.green);
             }
                
              if(moo == 2)
             {g.setColor(Color.orange);
             }
              if(moo == 3)
             {g.setColor(Color.red);
             }
                 
             g.drawOval(x, y, 10*zoomFactor, 10*zoomFactor);
             g.fillOval(x, y, 10*zoomFactor, 10*zoomFactor); 
            g.setColor(Color.red); 
             g.drawOval(x, y, 14*zoomFactor, 14*zoomFactor);
            //  System.out.println("type" + planetType);
            }
            
      if (planetType == CRACK)
           {g.setColor(Color.lightGray);
             g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
             g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor);  
            g.setColor(Color.darkGray);
             g.drawOval(x, y, 20*zoomFactor, 20*zoomFactor);
             g.drawOval(x, y, 15*zoomFactor, 15*zoomFactor);
             g.drawOval(x, y, 10*zoomFactor, 10*zoomFactor);
             g.drawOval(x, y, 5*zoomFactor, 5*zoomFactor);
            //  System.out.println("type" + planetType);
            }
            
      if (planetType == TITAN)
           {g.setColor(Color.darkGray); 
             g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
             g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor); 
           g.setColor(Color.darkGray); 
             g.drawOval(x, y, 10*zoomFactor, 10*zoomFactor);
             // System.out.println("type" + planetType);
            }
            
      if (planetType == STEALTH)
           {g.setColor(Color.black); 
              g.drawOval(x, y, 4*zoomFactor, 4*zoomFactor);
              g.fillOval(x, y, 4*zoomFactor, 4*zoomFactor);  
            g.setColor(Color.white);
              g.drawOval(x, y, 1*zoomFactor, 1*zoomFactor);
              g.fillOval(x, y, 1*zoomFactor, 1*zoomFactor); 
             //  System.out.println("type" + planetType);
            }
            
      if (planetType == SPARTAN)
           {g.setColor(Color.red); 
             g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
             g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor);
            g.setColor(Color.orange); 
             g.drawOval(x, y, 3*zoomFactor, 3*zoomFactor);
             g.fillOval(x, y, 3*zoomFactor, 3*zoomFactor);
             // System.out.println("type" + planetType);
            }
            
      if (planetType == IONIS)
           {g.setColor(Color.lightGray); 
             g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
             g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor);  
             // System.out.println("type" + planetType);
            }
            
      if (planetType == PEYO)
           {g.setColor(Color.blue); 
             g.drawOval(x, y, 15*zoomFactor, 15*zoomFactor);
             g.fillOval(x, y, 15*zoomFactor, 15*zoomFactor);  
             // System.out.println("type" + planetType);
            }
            
      if (planetType == RIEHL)
           {g.setColor(Color.pink); 
              g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
              g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor);
            g.setColor(Color.orange);   
              g.drawOval(x, y, 17*zoomFactor, 17*zoomFactor);
              g.fillOval(x, y, 17*zoomFactor, 17*zoomFactor);
            g.setColor(Color.yellow); 
              g.drawOval(x, y, 10*zoomFactor, 10*zoomFactor);
              g.fillOval(x, y, 10*zoomFactor, 10*zoomFactor);
             //  System.out.println("type" + planetType);
            }
            
      if (planetType == SARENA)
           {g.setColor(Color.orange); 
             g.drawOval(x, y, 9*zoomFactor, 9*zoomFactor);
             g.fillOval(x, y, 9*zoomFactor, 9*zoomFactor);  
             // System.out.println("type" + planetType);
            }
            
      if (planetType == XIAN)
           {g.setColor(Color.red); 
             g.drawOval(x, y, 10*zoomFactor, 10*zoomFactor);
             g.fillOval(x, y, 10*zoomFactor, 10*zoomFactor); 
             // System.out.println("type" + planetType);
            }
            
      if (planetType == BETHELLEN)
           {g.setColor(Color.white); 
             g.drawOval(x, y, 15*zoomFactor, 15*zoomFactor);
             g.fillOval(x, y, 15*zoomFactor, 15*zoomFactor);  
            g.setColor(Color.gray); 
             g.drawOval(x, y, 6*zoomFactor, 6*zoomFactor);
             g.fillOval(x, y, 6*zoomFactor, 6*zoomFactor); 
            //  System.out.println("type" + planetType);
            }
            
      if (planetType == MIRRIAN)
           {g.setColor(Color.cyan); 
             g.drawOval(x, y, 20*zoomFactor, 20*zoomFactor);
             g.fillOval(x, y, 20*zoomFactor, 20*zoomFactor);
             // System.out.println("type" + planetType);
            }
            
      if (planetType == ADANMA)
           {g.setColor(Color.red);
             g.drawOval(x, y, 25*zoomFactor, 25*zoomFactor);
             g.fillOval(x, y, 25*zoomFactor, 25*zoomFactor);  
             
            g.setColor(Color.green); 
             g.drawOval(x, y, 9*zoomFactor, 9*zoomFactor);
             g.fillOval(x, y, 9*zoomFactor, 9*zoomFactor); 
            //  System.out.println("type" + planetType);
            }

         return g;
           
        }
    
     /**
     * The bigger picture. Literally. a full sixe image of what the planet looks like.
     */
//    public JPanel wantFullPlanetFrame(JPanel p, int zoomFactor, int planetType, int x, int y)
//         {
//          p.getGraphics().drawImage(smallPlanets[planetType], x, y, zoomFactor*25, zoomFactor*25, p);  
//         
//          return p;
//         }
//     
//     
    
//     public static void generateWorld()
//     {
//         
//         if (max_Planets < 1000)
//           {
//            max_Planets = 1000;
//           }
//      
//      
//             
//             if(z== 0)
//             { type = VERONIAN;
//                 planetChoice.add(type);
//             }
//             
//              if(z>0 && z<=5)
//              { type = ADONIS;    
//                  planetChoice.add(type);   
//             }
//             
//               if(z>5 && z<=15)
//              { type = ERIS;           
//                  planetChoice.add(type);           
//             }
//             
//               if(z>5 && z<=25)
//              { type = FIERY;           
//                  planetChoice.add(type);           
//             }
//             
//                if(z>25 && z<=100)
//              { type = TIERRAN;           
//                  planetChoice.add(type);           
//             }
//             
//                if(z>100 && z<=200)
//              { type = THESBAN;            
//                  planetChoice.add(type);          
//             }
//             
//                if(z>200 && z<=300)
//              { type = TOMEKO;           
//                  planetChoice.add(type);          
//             }
//             
//                if(z>300 && z<=500)
//              { type = RING;            
//                  planetChoice.add(type);          
//             }
//             
//                if(z>500 && z<=515)
//              { type = CRACK;           
//                  planetChoice.add(type);            
//             }
//             
//                if(z>515 && z<=530)
//              { type = TITAN;           
//                  planetChoice.add(type);           
//             }
//                if(z>530 && z<=560)
//              { type = STEALTH;           
//                  planetChoice.add(type);         
//             }
//             
//                if(z>560 && z<=610)
//              { type = SPARTAN;           
//                  planetChoice.add(type);           
//             }
//             
//                if(z>610 && z<=655)
//              { type = IONIS;           
//                  planetChoice.add(type);           
//             }
//             
//                if(z>655 && z<=700)
//              { type = PEYO;           
//                  planetChoice.add(type);          
//             }
//             
//                if(z>700 && z<=750)
//              { type = RIEHL;             
//                  planetChoice.add(type);        
//             }
//             
//                if(z>750)
//              { type = SARENA;            
//                  planetChoice.add(type);           
//             }
//         
//         }
//       
   
        
        private void createVeronian()
        {
         fuelCapacity = 5000;
         materialCapacity = 5000;
         controlledBy = 0;
         planetType = VERONIAN;
         baseOffense = 0;
         baseDefense = -40;
        }
       
         private void createAdonis()
        {
         fuelCapacity = 1000;
         materialCapacity = 1000;
         controlledBy = 0;
         planetType = ADONIS;
         baseOffense = 5;
         baseDefense = 5;
        }  
           
        
       private void createFiery()
        {
         fuelCapacity = 1000;
         materialCapacity = 0;
         controlledBy = 0;
         planetType = FIERY;
         baseOffense = 0;
         baseDefense = -5;
       }
           
       private void createEris()
        {
         fuelCapacity = 0;
         materialCapacity = 1000;
         controlledBy = 0;
         planetType = ERIS;
         baseOffense = 0;
         baseDefense = -5;
        }
           
      private void createTierran()
        {
         fuelCapacity = 500;
         materialCapacity = 500;
         controlledBy = 0;
         planetType = TIERRAN;
         baseOffense = 0;
         baseDefense = 5;
        }
           
       private void createThesban()
        {
         fuelCapacity = randy.nextInt(400)+301;
         materialCapacity = randy.nextInt(300)+1;
         controlledBy = 0;
         planetType = THESBAN;
         baseOffense = 3;
         baseDefense = 3;
        }
           
       private void createTomeko()
        {
         materialCapacity = randy.nextInt(400)+301;
         fuelCapacity = randy.nextInt(300)+1;
         controlledBy = 0;
         planetType = TOMEKO;
         baseOffense = 3;
         baseDefense = 3;
        }
           
       private void createRing()
        {
         materialCapacity = 200;
         fuelCapacity = 200;
         controlledBy = 0;
         planetType = RING;
         baseOffense = 3;
         baseDefense = 4;
        }
        
        private void createCrack()
        {
         materialCapacity = 0;
         fuelCapacity = 2000;
         controlledBy = 0;
         planetType = CRACK;
         baseOffense = 5;
         baseDefense = -20;
        }
           
           
       private void createTitan()
        {
         materialCapacity = 1000;
         fuelCapacity = 0;
         controlledBy = 0;
         planetType = TITAN;
         baseOffense = 5;
         baseDefense = -20;
        }
           
           
       private void createStealth()
        {
         materialCapacity = 200;
         fuelCapacity = 200;
         controlledBy = 0;
         planetType = STEALTH;
         baseOffense = 3;
         baseDefense = 12;
        }
        
              
      private void createSpartan()
        {
         fuelCapacity = 500;
         materialCapacity = 200;
         controlledBy = 0;
         planetType = SPARTAN;
         baseOffense = 12;
         baseDefense = 3;
        }
           
      private void createIonis()
        {
         materialCapacity = randy.nextInt(1200)+201;
         fuelCapacity = 200;
         controlledBy = 0;
         planetType = IONIS;
         baseOffense = 5;
         baseDefense = 8;
       }
       
       private void createPeyo()
        {
         fuelCapacity = randy.nextInt(1200)+201;
         materialCapacity = 200;
         controlledBy = 0;
         planetType = PEYO;
         baseOffense = 8;
         baseDefense = 5;
        }
      private void createRiehl()
        {
         fuelCapacity = 1;
         materialCapacity = 1;
         controlledBy = 0;
         planetType = RIEHL;
         baseOffense = 0;
         baseDefense = 25;
        }

         private void createSarena()
        {
         fuelCapacity = randy.nextInt(500)+201;
         materialCapacity = randy.nextInt(500)+201;
         controlledBy = 0;
         planetType = SARENA;
         baseOffense = 5;
         baseDefense = 5;
        }
        
  /**
     * Pack will add the letter P betweeen all the information.
     *
     */
            public String pack()
    {
       Data = ("Plan" + controlledBy + "$" + baseOffense + "$" + baseDefense + "$" + fuelCapacity + "$" + materialCapacity + "$" + planetType);
       return Data;
    }
    /**
     * 
     *The letter P will between all information, and will be pulled out
     */
    public void unpack(String data)
    {
        ArrayList<String> result = new ArrayList<String>(); //Like an array of unknown length.
        int lastSignIndex = 0;
        for(int z=0;z<data.length();z++)
        {
            if(data.charAt(0) == 'P' && data.charAt(1) == 'L' && data.charAt(2) == 'A' && data.charAt(3) == 'N') 
            { lastSignIndex = 4;
            
                if(data.charAt(z)== '$')
                {
                    result.add(data.substring(lastSignIndex,z));
                    lastSignIndex = z+1;
                }
            }
            
            else
            System.out.println("THIS IS THE WRONG VALUE!!!");
            
        }
        
        controlledBy = Integer.parseInt(result.get(1));
        baseOffense = Integer.parseInt(result.get(2));
        baseDefense = Integer.parseInt(result.get(3));
        fuelCapacity = Integer.parseInt(result.get(4));
        materialCapacity = Integer.parseInt(result.get(5));
        planetType = Integer.parseInt(result.get(6));
    }
 
    
//     private static void loadGraphics()
//     {
//         planetEris = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetVeronian = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetEris = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetFiery = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetMoon = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetTierran = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetAdonis = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetThesban = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp"); 
//         planetTomeko = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetRing = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetCrack = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetTitan = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetStealth = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetSpartan  = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetIonis = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetPeyo = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetRiehl = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetSarena = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetXian = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetBethellen = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetMirrian = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");
//         planetAdanma = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\Shared\\APCompProg2\\SpaceGame_0219\\PlanetDisplays\\Eris.bmp");          
//     
//         
//         smallPlanets[0]= planetVeronian;  
//         smallPlanets[1]= planetAdonis;
//         smallPlanets[2]= planetEris;  
//         smallPlanets[3]= planetFiery;   
//         smallPlanets[4]= planetTierran;       
//         smallPlanets[5]= planetThesban;    
//         smallPlanets[6]= planetTomeko;   
//         smallPlanets[7]= planetRing;   
//         smallPlanets[8]= planetCrack;   
//         smallPlanets[9]= planetTitan;   
//         smallPlanets[10]= planetStealth;   
//         smallPlanets[11]= planetSpartan;  
//         smallPlanets[12]= planetIonis;   
//         smallPlanets[13]= planetPeyo;  
//         smallPlanets[14]= planetRiehl;   
//         smallPlanets[15]= planetSarena;   
//         smallPlanets[16]= planetXian;   
//         smallPlanets[17]= planetBethellen;   
//         smallPlanets[18]= planetMirrian;   
//         smallPlanets[19]= planetAdanma;  
//     }
    
    
    
        final static int VERONIAN = 0;
        final static int ADONIS = 1;
        final static int ERIS = 2;
        final static int FIERY = 3;
        final static int TIERRAN = 4;
        final static int THESBAN = 5;
        final static int TOMEKO = 6;
        final static int RING = 7;
        final static int CRACK = 8;
        final static int TITAN = 9;
        final static int STEALTH = 10;
        final static int SPARTAN = 11;
        final static int IONIS = 12;
        final static int PEYO = 13;
        final static int RIEHL = 14;
        final static int SARENA = 15;
        final static int XIAN = 16;
        final static int BETHELLEN = 17;
        final static int MIRRIAN = 18;
        final static int ADANMA = 19 ;
        
        
//        static Image planetVeronian;   
//        static Image planetEris;   
//        static Image planetFiery;   
//        static Image planetMoon;   
//        static Image planetTierran;   
//        static Image planetAdonis;   
//        static Image planetThesban;    
//        static Image planetTomeko;   
//        static Image planetRing;   
//        static Image planetCrack;   
//        static Image planetTitan;   
//        static Image planetStealth;   
//        static Image planetSpartan;  
//        static Image planetIonis;   
//        static Image planetPeyo;  
//        static Image planetRiehl;   
//        static Image planetSarena;   
//        static Image planetXian;   
//        static Image planetBethellen;   
//        static Image planetMirrian;   
//        static Image planetAdanma;  
//         
        
    
}
