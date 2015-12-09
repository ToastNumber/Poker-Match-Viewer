package poker;

public class ResourceHandler {
	public static final String TABLE_IMG = "/lightblue.png";
	public static final String CARD_BACK = "/playing-cards/back.jpg";
	public static final String CHIPS = "/chips.png";
	public static final String POT = "/pot.png";
	
	public static String getCard(String rank, String suit) {
		return String.format("/playing-cards/%s_of_%s.png", rank, suit.toLowerCase());
	}
}
