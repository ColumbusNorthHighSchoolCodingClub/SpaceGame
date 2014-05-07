package src.spacegame.old;
/**
 * This is a class that offers static methods that will allow us to 
 * turn on and off our debug messages easily.  
 * 
 * @author (Spock) 
 * @version (030307)
 */
public class Debug
{
    private final static int DEBUG_LEVEL = 10; //This should be 0-10 (10 is verbose)

    public static void msg(String in)
    {
        if(DEBUG_LEVEL > 0)
            System.out.println(in);
    }
    
    public static void msg(String in, int level)
    {
        if(DEBUG_LEVEL > level)
            System.out.println(in);
    }
}
