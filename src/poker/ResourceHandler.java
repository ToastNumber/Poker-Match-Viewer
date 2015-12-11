package poker;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceHandler {
	public BufferedImage TABLE_IMG;
	public BufferedImage CARD_BACK;
	public BufferedImage CHIPS;
	public BufferedImage POT;
	public BufferedImage DEALER_BUTTON;
	public BufferedImage GREETING_IMAGE;
	public BufferedImage[][] CARDS;
	private static final String[] ranks = { "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king" };
	private static final String[] suits = { "clubs", "diamonds", "hearts", "spades" };

	// Public instance
	public static ResourceHandler handler = new ResourceHandler();

	// Private constructor
	private ResourceHandler() {
		load();
	}

	private void load() {
		TABLE_IMG = getImage("/lightblue.png");
		CARD_BACK = getImage("/playing-cards/back.jpg");
		CHIPS = getImage("/chips.png");
		POT = getImage("/pot.png");
		DEALER_BUTTON = getImage("/dealer-button.png");
		GREETING_IMAGE = getImage("/chip-black-bg.jpg");
		
		CARDS = new BufferedImage[ranks.length][suits.length];
		for (int i = 0; i < ranks.length; ++i) {
			for (int j = 0; j < suits.length; ++j) {
				CARDS[i][j] = getCard(ranks[i], suits[j]);
			}
		}
	}

	private BufferedImage getCard(String rank, String suit) {
		return getImage(String.format("/playing-cards/%s_of_%s.png", rank, suit.toLowerCase()));
	}

	public BufferedImage getPlayingCard(String rank, String suit) {
		int rankIndex = 0;
		while (rankIndex < ranks.length && !ranks[rankIndex].equals(rank))
			++rankIndex;

		int suitIndex = 0;
		while (suitIndex < suits.length && !suits[suitIndex].equals(suit))
			++suitIndex;

		if (!ranks[rankIndex].equals(rank) || !suits[suitIndex].equals(suit)) return null;

		return CARDS[rankIndex][suitIndex];
	}

	private BufferedImage getImage(String path) {
		BufferedImage svar = null;
		try {
			svar = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return svar;
	}

}
