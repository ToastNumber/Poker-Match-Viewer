package poker.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import poker.ResourceHandler;
import poker.graphics.util.FontUtils;
import poker.play.ActionPoint;
import poker.play.TourneyModel;

public class TableView extends JPanel implements Observer {
	private TourneyModel model;

	public TableView(TourneyModel model) {
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g) {
		ResourceHandler handler = ResourceHandler.handler;
		
		Graphics2D g2 = (Graphics2D) g;
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		ActionPoint actionPoint = model.getActionPoint();

		int width = getWidth();
		int height = getHeight();

		g2.clearRect(0, 0, width, height);

		/*** Draw the poker table ***/
		Image pokerTable = handler.TABLE_IMG;
		if (pokerTable != null) {
			g2.drawImage(pokerTable, 0, 0, width, height, null);
		}
		/*** End of poker table ***/

		/*** Print "Starting/Playing match x of y" ***/
		String startOfMatchMsg = String.format("%s match %d of %d", model.isFirstActionPoint() ? "Starting"
				: "Playing", model.getMatchIndex() + 1, model.getNumMatches());

		g2.setFont(FontUtils.getLargestPossibleFont(g2, width / 4, height / 20, startOfMatchMsg));
		g2.setColor(Color.WHITE);

		int y = (87 * height) / 100;
		int x = width / 20;

		g2.drawString(startOfMatchMsg, x, y);
		
		String handNumMessage = String.format("Hand %d of %d", model.getHandIndex() + 1, model.getNumHands());
		g2.setFont(FontUtils.getLargestPossibleFont(g2, width/4, height/20, handNumMessage));
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
		PlayerBubble p1Bubble = new PlayerBubble(model.getP1Name(), p1Action, actionPoint.getP1Behind(),
				(10 * width) / 100, bubbleY, bubbleRadius);
		p1Bubble.draw(g2);

		p2Action = actionPoint.getActorIndex() == 2 ? "" + actionPoint.getPlayerAction() : "";
		PlayerBubble p2Bubble = new PlayerBubble(model.getP2Name(), p2Action, actionPoint.getP2Behind(),
				(90 * width) / 100, bubbleY, bubbleRadius);
		p2Bubble.draw(g2);
		/*** End bubbles ***/

		/*** Draw hole cards ***/
		Image card1, card2;
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
			g2.drawImage(card1, (17 * width) / 100, (33 * height) / 100 - cardHeight / 2, cardWidth, cardHeight,
					null);
		}
		if (card2 != null) {
			g2.drawImage(card2, (17 * width) / 100 + cardWidth + width / 150, (33 * height) / 100 - cardHeight
					/ 2, cardWidth, cardHeight, null);
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
			g2.drawImage(card1, (78 * width) / 100 - (cardWidth + width / 150), (33 * height) / 100 - cardHeight
					/ 2, cardWidth, cardHeight, null);
		}
		if (card2 != null) {
			g2.drawImage(card2, (78 * width) / 100, (33 * height) / 100 - cardHeight / 2, cardWidth, cardHeight,
					null);
		}
		/*** End hole cards ***/

		/*** Draw community cards ***/
		List<String> board = actionPoint.getBoard();
		int boardCardWidth = (int) (0.06 * width);
		int boardCardHeight = (int) (726 * boardCardWidth) / 500;
		for (int i = 0; i < board.size(); ++i) {
			String[] rankSuit = board.get(i).split(" ");

			Image card = handler.getPlayingCard(rankSuit[0], rankSuit[1]);

			if (card != null) {
				int xOffset = ((19 * width) / 20 - 5 * boardCardWidth) / 2 + i * (boardCardWidth + width / 100);
				g2.drawImage(card, xOffset, bubbleY - boardCardHeight / 2, boardCardWidth, boardCardHeight, null);
			}
		}
		/*** End community cards ***/

		/*** Pot ***/
		if (actionPoint.getPot() > 0) {
			String potString = "" + actionPoint.getPot();
			g2.setFont(FontUtils.getLargestPossibleFont(g2, (15 * width) / 100, (7 * height) / 100, potString));
			g2.drawString(potString, (58 * width) / 100 - g2.getFontMetrics().stringWidth(potString) / 2,
					(26 * height) / 100);

			Image potImage = handler.POT;

			if (potImage != null) {
				int potImageWidth = (8 * width) / 100;
				int potImageHeight = (246 * potImageWidth) / 306;
				g2.drawImage(potImage, width / 2 - potImageWidth / 2, (19 * height) / 100, potImageWidth,
						potImageHeight, null);
			}
		}
		/*** End pot ***/

		/*** Draw betting chips ***/
		if (actionPoint.getP1ChipCount() != actionPoint.getP1Behind()) {
			Image chips = handler.CHIPS;

			if (chips != null) {
				int chipWidth = (5 * width) / 100;
				int chipHeight = (516 * chipWidth) / 630;

				g2.drawImage(chips, (22 * width) / 100, (55 * height) / 100, chipWidth, chipHeight, null);

				int betAmount = actionPoint.getP1ChipCount() - actionPoint.getP1Behind();
				g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
						+ betAmount));
				g2.drawString("" + betAmount, (29 * width) / 100, (60 * height) / 100);
			}
		}

		if (actionPoint.getP2ChipCount() != actionPoint.getP2Behind()) {
			Image chips = handler.CHIPS;
			int chipWidth = (5 * width) / 100;
			int chipHeight = (516 * chipWidth) / 630;

			g2.drawImage(chips, (78 * width) / 100, (55 * height) / 100, chipWidth, chipHeight, null);

			int betAmount = actionPoint.getP2ChipCount() - actionPoint.getP2Behind();
			g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
					+ betAmount));
			g2.drawString("" + betAmount, (71 * width) / 100, (60 * height) / 100);
		}
		/*** End betting chips ***/

		/*** Draw dealer button ***/
		Image dealerButton = handler.DEALER_BUTTON;
		
		if (dealerButton != null) {
			int dealerX = actionPoint.getButtonIndex() == 1 ? (18 * width) / 100 : (77 * width) / 100;
			int dealerWidth = (5 * width) / 100;
			g2.drawImage(dealerButton, dealerX, (43 * height) / 100, dealerWidth, dealerWidth, null);
		}
		/*** End dealer button ***/
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
