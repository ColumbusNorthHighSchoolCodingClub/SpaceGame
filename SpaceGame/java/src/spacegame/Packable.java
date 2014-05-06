package src.spacegame;
/**
 * Write a description of interface Packable here.
 * 
 * @author (Spock) 
 * @version (2_20_07)
 */

public interface Packable
{
	/**
	 * The pack method converts the data from this class into a String 
	 * for transfer over the network.
	 * 
	 * @return		the String to be transfered
	 */
	public String pack();
	
	/**
	 * The unpack method converts the data recieved from the server 
	 * into an object of this class.
	 * 
	 * @data		the String to be converted.
	 */
	public void unpack(String data);
}
