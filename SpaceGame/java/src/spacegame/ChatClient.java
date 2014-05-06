package src.spacegame;
/*
 * Chat.java
 *
 * Created on February 19, 2007
 *
 */

 
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.io.IOException;

/**
 *
 * https://www6.software.ibm.com/developerworks/education/j-chat/j-chat-4-3.html
 * Coded by Tom Andrews
 */
public class ChatClient extends JFrame implements ActionListener, KeyListener
{
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="variables">
    
    private int _socketNum;
    private int count = 0;
    private String _ip = null;
    private Socket _socket = null;
    private ServerSocket[] ss = null;
    private boolean _connected = false;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private javax.swing.Timer timer = null;
    private String[] chats = null;
    private Image _background = null;
    private int[] PORTS;
    private int numPlayers;
    private String[] data = new String[10];
    private String typed = "";
    private PrintWriter dout = null;
    private int number;
    private String name = "default";
    public static int receiver;
    public static int sender;
    public static boolean gotmessage = false;
    public static ArrayList<String> messages;
    
    // </editor-fold>
    
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="constructor">
    
    /** Creates a new instance of Chat */
    public ChatClient(String ip)
    {
        int socketNum = 5200;
        System.out.println("Start: " + ip + " - " + socketNum);
        this.setSize(350, 350);
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        _ip = ip;
        _socketNum = socketNum;
        messages = new ArrayList<String>();
        try
        {
            InetAddress in = InetAddress.getByName(_ip);
            _socket = new Socket(in, _socketNum);
        }
        catch (IOException e) 
        {
            System.out.println("DOH " + e);
        }
        numPlayers = Roster.theRoster.size();
        number = ClientMain.myPlayerNum;
        name = Roster.getPlayer(number).getName();
        System.out.println("name " + name);
        addKeyListener(this);
        //initPic();
        initWrite();
        initTimer();
    }
    
    public static void main(String[] args)
    {
        ChatClient chat = new ChatClient("172.16.1.10");
    }
    
    // </editor-fold>
    
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="init stuff">
    
    public void initPic()
    {
        //_background = Toolkit.getDefaultToolkit().getImage("P:\\STUDENTS\\2007\\andrewst778\\java\\Netgame\\src\\Game\\Paper.png");
    }
    
    public void initWrite()
    {
        System.out.println("port " + _socket.getPort());
        try
        {
            dout = new PrintWriter(_socket.getOutputStream(), true);
        }
        catch( IOException ie ) 
        { 
            System.out.println( ie ); 
        }
        new ChatClientThread(_socket).start();
        data[0] = "";
    }
    
    // </editor-fold>
    
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="message stuff">
    
    public void getMail()
    {
        dout.println("MAIL PLEASE");
    }
    
    public void readMail()
    {
        gotmessage = false;
        while(messages.size()>0)
        {
            addString(messages.get(0));
            messages.remove(0);
        }
    }
    
    public void sendString(String s, int to)
    {
        ////////////////////////////////////////////////////////////////////////
        dout.println(ChatParse.toServer(s, name, to));
        ////////////////////////////////////////////////////////////////////////
        for(int i=data.length-1; i>0; i--)
        {
            data[i] = data[i-1];
        }
        data[0] = "";
    }
    
    public void addString(String s)
    {
        for(int i=data.length-1; i>1; i--)
        {
            data[i] = data[i-1];
        }
        data[1] = s;
    }
    
    // </editor-fold>
    
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="paint stuff">
    
    public void paint(Graphics g) //paint the panel
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 400);
        //g.drawImage(_background, 0, 25, this);
        g.setColor(Color.BLACK);
        for(int i=0; i<data.length; i++)
        {
            if(data[i]!=null)
            g.drawString(data[i], 50, 25*i + 50);
        }
    }
    
    //==========================================================================
    
    private void initTimer()
    {
        timer = new javax.swing.Timer(100, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        timer.start();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        count++;
        if(count%5==0)getMail();
        if(gotmessage)readMail();
        this.repaint();
    }
    
    // </editor-fold>    
    
    //==========================================================================
    
    // <editor-fold defaultstate="collapsed" desc="keyboard stuff">
    
    public boolean isFocusable(){return true;}

    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        char c = e.getKeyChar();
        if(keyCode==e.VK_ENTER);
        else if(keyCode==e.VK_BACK_SPACE && data[0].length()>0)
            data[0] = data[0].substring(0, data[0].length()-1);
        else if(keyCode==e.VK_CAPS_LOCK);
        else if(keyCode==e.VK_SHIFT);
        else data[0] += c;
    }

    public void keyReleased(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if(keyCode==e.VK_ENTER)
        {
            sendString(data[0], 1);
        }
    }

    public void keyTyped(KeyEvent e) 
    {
    }
    
    // </editor-fold> 
}