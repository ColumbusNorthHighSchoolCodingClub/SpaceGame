/*
 * ChatThread.java
 *
 * Created on March 2, 2007, 7:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package src.spacegame.old;

import java.net.*;
import java.io.*;

/**
 *
 * Coded by Tom Andrews
 */
public class ChatThreads extends Thread {
	private int port;
	
	/** Creates a new instance of ChatThread */
	public ChatThreads(int portNum) {
	
		port = portNum;
	}
	
	public void run() {
	
		try {
			Search();
		}
		catch (java.io.IOException i) {
			System.err.println("Error in Search()");
		}
	}
	
	private void Search() throws IOException {
	
		ServerSocket sendSocket = null;
		boolean listening = true;
		
		System.out.println("Searching");
		
		try {
			sendSocket = new ServerSocket(port);
		}
		catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}
		
		while (listening) {
			new ChatSockets(sendSocket.accept()).start();
		}
		
		sendSocket.close();
	}
}
