package poker.play;

public class HoleCards {
	private String[] cards;
	private boolean known;
	
	public HoleCards(String card1, String card2) {
		cards = new String[]{card1, card2};
		
		this.known = true;
	}
	
	public HoleCards() {
		this.known = false;
	}
	
	public String[] getCards() {
		return cards;
	}
	
	public boolean known() {
		return known;
	}
	
	public String toString() {
		return String.format("[%s, %s]", cards[0], cards[1]);
	}
}
