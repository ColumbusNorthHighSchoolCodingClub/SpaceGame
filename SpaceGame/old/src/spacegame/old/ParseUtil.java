//---------------------------------------------------------
package src.spacegame.old;

// ParseUtil class
// contains utility methods for parsing data
// created 3-24-05 by Spock
//---------------------------------------------------------
import java.util.ArrayList;
import java.util.Vector; //for Vectors

@Deprecated
public class ParseUtil {
	
	public static Vector parseStringBySign(String in, char sign) { //This method returns a Vector of Strings created by
	
		//parsing the input string on occurances of 'sign'.
		Vector result = new Vector(); //Like an array of unknown length.
		int lastSignIndex = 0;
		for(int z = 0; z < in.length(); z++) {
			if(in.charAt(z) == sign) {
				result.addElement(in.substring(lastSignIndex, z));
				lastSignIndex = z + 1;
			}
		}
		if(lastSignIndex < in.length()) //Get last string if no sign at end.
			result.addElement(in.substring(lastSignIndex, in.length()));
		
		return result;
	} //--end of parseStringBySign() method--
	
	public static String getHeader(String in, char sign) { //This method returns the start of in until 'sign'.
	
		int z = 0;
		while(z < in.length() && in.charAt(z) != sign)
			z++;
		return in.substring(0, z);
	} //--end of getHeader() method--

	public static ArrayList<String> parseString(String in, char sign) { //This method returns an ArrayList of Strings created by
	
		//parsing the input string on occurances of 'sign'.
		ArrayList<String> result = new ArrayList<String>();
		int lastSignIndex = 0;
		for(int z = 0; z < in.length(); z++) {
			if(in.charAt(z) == sign) {
				result.add(in.substring(lastSignIndex, z));
				lastSignIndex = z + 1;
			}
		}
		if(lastSignIndex < in.length()) //Get last string if no sign at end.
			result.add(in.substring(lastSignIndex, in.length()));
		
		return result;
	} //--end of parseStringBySign() method--
	
	public static String getHeader(String in) { //This method returns the first four characters of 'in'
	
		return in.substring(0, 4);
	} //--end of getHeader() method--
	
} //--end of ParseUtil class

