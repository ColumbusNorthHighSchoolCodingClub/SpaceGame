/*
 * ChatParse.java
 *
 * Created on March 16, 2007, 8:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package src.spacegame.old;

/**
 *
 * Coded by Tom Andrews
 */
public class ChatParse {
	public static String Message = "";
	public static String From = "";
	public static String To = "";
	
	public static String toServer(String s, String from, int to) {
	
		String temp = "$";
		temp += to;
		temp += "#";
		temp += from;
		temp += "@";
		temp += s;
		return temp;
	}
	
	public static String toClient(String s, String from) {
	
		String temp = "$";
		temp += from;
		temp += ": ";
		temp += s;
		return temp;
	}
	
	public static void fromServer(String s) {
	
		if (s.substring(0, 1).equals("$")) {
			From = s.substring(1, s.indexOf(":"));
			Message = s.substring(s.indexOf(":") + 1);
		}
		else
			System.out.println("COULDN'T PARSE MESSAGE from Server" + s);
	}
	
	public static void fromClient(String s) {
	
		if (s.substring(0, 1).equals("$")) {
			To = s.substring(1, s.indexOf("#"));
			From = s.substring(s.indexOf("#") + 1, s.indexOf("@"));
			Message = s.substring(s.indexOf("@") + 1);
		}
		else
			System.out.println("COULDN'T PARSE MESSAGE from Client " + s);
	}
}
