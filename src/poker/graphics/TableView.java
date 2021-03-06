package poker.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.imgscalr.Scalr;

import poker.ResourceHandler;
import poker.graphics.util.FontUtils;
import poker.play.Action;
import poker.play.TourneyModel;

public class TableView extends JPanel implements Observer {
	private TourneyModel model;

	public TableView(TourneyModel model) {
		this.model = model;
	}

	public void paintOpeningWindow(Graphics2D g2) {
		BufferedImage opening = ResourceHandler.handler.GREETING_IMAGE;
		if (opening != null) {
			g2.drawImage(opening, 0, 0, getWidth(), getHeight(), null);
		}
	}

	public void paintPlayWindow(Graphics2D g2) {
		ResourceHandler handler = ResourceHandler.handler;

		Action actionPoint = model.getActionPoint();

		int width = getWidth();
		int height = getHeight();

		g2.clearRect(0, 0, width, height);

		/*** Draw the poker table ***/
		BufferedImage pokerTable = handler.TABLE_IMG;
		if (pokerTable != null) {
			pokerTable = Scalr.resize(pokerTable, width, height);
			g2.drawImage(pokerTable, 0, 0, width, height, null);
		}
		/*** End of poker table ***/

		/*** Print "Starting/Playing match x of y" ***/
		String startOfMatchMsg = String.format("%s match %d of %d",
				model.isFirstHand() && model.isFirstActionPoint() ? "Starting" : "Playing",
				model.getMatchIndex() + 1, model.getNumMatches());

		g2.setFont(FontUtils.getLargestPossibleFont(g2, width / 4, height / 20, startOfMatchMsg));
		g2.setColor(Color.WHITE);

		int y = (87 * height) / 100;
		int x = width / 20;

		g2.drawString(startOfMatchMsg, x, y);

		String handNumMessage = String.format("Hand %d of %d", model.getHandIndex() + 1, model.getNumHands());
		g2.setFont(FontUtils.getLargestPossibleFont(g2, width / 4, height / 20, handNumMessage));
		y += (5 * height) / 100;
		g2.drawString(handNumMessage, x, y);
		/*** End of info message ***/

		/*** Draw the player bubbles ***/
		int bubbleRadius = (7 * width) / 100;
		int bubbleY = (17 * height) / 40;

		String p1Action = "";
		String p2Action = "";
		String action = "" + actionPoint.getPlayerAction();
		int actorIndex = actionPoint.getActorIndex();
		if (actorIndex == 1) {
			p1Action = action;
		} else if (actorIndex == 2) {
			p2Action = action;
		}

		p1Action = actionPoint.getActorIndex() == 1 ? "" + actionPoint.getPlayerAction() : "";
		PlayerBubble p1Bubble = new PlayerBubble(model.getPlayer1Name(), p1Action, actionPoint.getP1ChipCount()
				- actionPoint.getP1Bet(), (10 * width) / 100, bubbleY, bubbleRadius);
		p1Bubble.draw(g2);

		p2Action = actionPoint.getActorIndex() == 2 ? "" + actionPoint.getPlayerAction() : "";
		PlayerBubble p2Bubble = new PlayerBubble(model.getPlayer2Name(), p2Action, actionPoint.getP2ChipCount()
				- actionPoint.getP2Bet(), (90 * width) / 100, bubbleY, bubbleRadius);
		p2Bubble.draw(g2);
		/*** End bubbles ***/

		/*** Draw hole cards ***/
		BufferedImage card1, card2;
		int cardWidth = (int) (0.05 * width);
		int cardHeight = (int) (726 * cardWidth) / 500;

		// Draw player 1 hole cards
		if (actionPoint.getP1HoleCards().known()) {
			String[] cards = actionPoint.getP1HoleCards().getCards();
			String[] c1 = cards[0].split(" ");
			String[] c2 = cards[1].split(" ");
			card1 = handler.getPlayingCard(c1[0], c1[1]);
			card2 = handler.getPlayingCard(c2[0], c2[1]);
		} else {
			card1 = handler.CARD_BACK;
			card2 = handler.CARD_BACK;
		}

		if (card1 != null) {
			card1 = Scalr.resize(card1, cardWidth, cardHeight);
			g2.drawImage(card1, (17 * width) / 100, (33 * height) / 100 - cardHeight / 2, null);
		}
		if (card2 != null) {
			card2 = Scalr.resize(card2, cardWidth, cardHeight);
			g2.drawImage(card2, (17 * width) / 100 + cardWidth + width / 150, (33 * height) / 100 - cardHeight
					/ 2, null);
		}

		// Draw player 2 hole cards
		if (actionPoint.getP2HoleCards().known()) {
			String[] cards = actionPoint.getP2HoleCards().getCards();
			String[] c1 = cards[0].split(" ");
			String[] c2 = cards[1].split(" ");
			card1 = handler.getPlayingCard(c1[0], c1[1]);
			card2 = handler.getPlayingCard(c2[0], c2[1]);
		} else {
			card1 = handler.CARD_BACK;
			card2 = handler.CARD_BACK;
		}

		if (card1 != null) {
			card1 = Scalr.resize(card1, cardWidth, cardHeight);
			g2.drawImage(card1, (78 * width) / 100 - (cardWidth + width / 150), (33 * height) / 100 - cardHeight
					/ 2, null);
		}
		if (card2 != null) {
			card2 = Scalr.resize(card2, cardWidth, cardHeight);
			g2.drawImage(card2, (78 * width) / 100, (33 * height) / 100 - cardHeight / 2, null);
		}
		/*** End hole cards ***/

		/*** Draw community cards ***/
		List<String> board = actionPoint.getBoard();
		int boardCardWidth = (int) (0.06 * width);
		int boardCardHeight = (int) (726 * boardCardWidth) / 500;
		for (int i = 0; i < board.size(); ++i) {
			String[] rankSuit = board.get(i).split(" ");

			BufferedImage card = handler.getPlayingCard(rankSuit[0], rankSuit[1]);

			if (card != null) {
				card = Scalr.resize(card, boardCardWidth, boardCardHeight);
				int xOffset = ((19 * width) / 20 - 5 * boardCardWidth) / 2 + i * (boardCardWidth + width / 100);
				g2.drawImage(card, xOffset, bubbleY - boardCardHeight / 2, null);
			}
		}
		/*** End community cards ***/

		/*** Pot ***/
		if (actionPoint.getPot() > 0) {
			String potString = "" + actionPoint.getPot();
			g2.setFont(FontUtils.getLargestPossibleFont(g2, (15 * width) / 100, (7 * height) / 100, potString));
			g2.drawString(potString, (58 * width) / 100 - g2.getFontMetrics().stringWidth(potString) / 2,
					(26 * height) / 100);

			BufferedImage potImage = handler.POT;

			if (potImage != null) {
				int potImageWidth = (8 * width) / 100;
				int potImageHeight = (246 * potImageWidth) / 306;
				potImage = Scalr.resize(potImage, potImageWidth, potImageHeight);
				
				g2.drawImage(potImage, width / 2 - potImageWidth / 2, (19 * height) / 100, null);
			}
		}
		/*** End pot ***/

		/*** Draw betting chips ***/
		if (actionPoint.getP1Bet() != 0) {
			BufferedImage chips = handler.CHIPS;

			if (chips != null) {
				int chipWidth = (5 * width) / 100;
				int chipHeight = (516 * chipWidth) / 630;

				chips = Scalr.resize(chips, chipWidth, chipHeight);
				
				g2.drawImage(chips, (22 * width) / 100, (55 * height) / 100, null);

				g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
						+ actionPoint.getP1Bet()));
				g2.drawString("" + actionPoint.getP1Bet(), (29 * width) / 100, (60 * height) / 100);
			}
		}

		if (actionPoint.getP2Bet() != 0) {
			BufferedImage chips = handler.CHIPS;
			int chipWidth = (5 * width) / 100;
			int chipHeight = (516 * chipWidth) / 630;
			
			chips = Scalr.resize(chips, chipWidth, chipHeight);
			
			g2.drawImage(chips, (71 * width) / 100, (55 * height) / 100, null);

			g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
					+ actionPoint.getP2Bet()));
			g2.drawString("" + actionPoint.getP2Bet(), (78 * width) / 100, (60 * height) / 100);
		}
		/*** End betting chips ***/

		/*** Draw dealer button ***/
		BufferedImage dealerButton = handler.DEALER_BUTTON;

		if (dealerButton != null) {
			int dealerX = actionPoint.getButtonIndex() == 1 ? (18 * width) / 100 : (77 * width) / 100;
			int dealerWidth = (5 * width) / 100;
			dealerButton = Scalr.resize(dealerButton, dealerWidth, dealerWidth);
			g2.drawImage(dealerButton, dealerX, (43 * height) / 100, null);
		}
		/*** End dealer button ***/
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (model.getNumMatches() == 0) paintOpeningWindow(g2);
		else paintPlayWindow(g2);

	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
