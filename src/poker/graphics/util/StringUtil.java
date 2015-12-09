package poker.graphics.util;

public class StringUtil {
	
	public static String firstUpper(String s) {
		if (s.isEmpty()) return "";
		else {
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
	}
}
