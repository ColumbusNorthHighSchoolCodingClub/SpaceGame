package src.spacegame;

import java.util.ArrayList;

public class ParseUtil {

	public static ArrayList<String> parseString(String data, char parseChar) {

		ArrayList<String> temp = new ArrayList<String>();

		int lastChar = 0;
		for(int index = 0; index < data.length(); index++) {

			if(data.charAt(index) == parseChar) {
				temp.add(data.substring(lastChar, index));
				lastChar = index + 1;
			}
		}

		if(lastChar < data.length())
			temp.add(data.substring(lastChar, data.length()));

		return temp;
	}
}
