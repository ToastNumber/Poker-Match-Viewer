package poker.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import poker.ResourceHandler;
import poker.graphics.util.FontUtils;
import poker.play.ActionPoint;
import poker.play.ActionType;
import poker.play.HoleCards;
import poker.play.Match;
import poker.play.TourneyModel;

public class TableView extends JPanel implements Observer {
	private TourneyModel model;

	public TableView(TourneyModel model) {
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		ActionPoint actionPoint = model.getActionPoint();

		int width = getWidth();
		int height = getHeight();

		g2.clearRect(0, 0, width, height);

		/*** Draw the poker table ***/
		Image pokerTable;
		try {
			pokerTable = ImageIO.read(getClass().getResource(ResourceHandler.TABLE_IMG));
			g2.drawImage(pokerTable, 0, 0, width, height, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*** End of poker table ***/

		/*** Print "Starting/Playing match x of y" ***/
		String startOfMatchMsg = String.format("%s match %d of %d", model.matchAboutToStart() ? "Starting"
				: "Playing", model.getMatchIndex() + 1, model.getNumMatches());

		g2.setFont(FontUtils.getLargestPossibleFont(g2, width / 4, height / 20, startOfMatchMsg));
		g2.setColor(Color.WHITE);

		int y = (95 * height) / 100;
		int x = width / 20;

		g2.drawString(startOfMatchMsg, x, y);
		/*** End of info message ***/

		/*** Draw the player bubbles ***/
		int bubbleRadius = (7 * width) / 100;
		int bubbleY = (17 * height) / 40;
		String p1Action = actionPoint.getActorIndex() == 1 ? "" + actionPoint.getPlayerAction() : "";
		PlayerBubble p1Bubble = new PlayerBubble(model.getP1Name(), p1Action, actionPoint.getP1Behind(),
				(10 * width) / 100, bubbleY, bubbleRadius);
		p1Bubble.draw(g2);

		String p2Action = actionPoint.getActorIndex() == 2 ? "" + actionPoint.getPlayerAction() : "";
		PlayerBubble p2Bubble = new PlayerBubble(model.getP2Name(), p2Action, actionPoint.getP2Behind(),
				(90 * width) / 100, bubbleY, bubbleRadius);
		p2Bubble.draw(g2);
		/*** End bubbles ***/

		/*** Draw hole cards ***/
		Image card1, card2;
		int cardWidth = (int) (0.05 * width);
		int cardHeight = (int) (726 * cardWidth) / 500;
		try {
			// Draw player 1 hole cards
			if (actionPoint.getP1HoleCards().known()) {
				String[] cards = actionPoint.getP1HoleCards().getCards();
				String[] c1 = cards[0].split(" ");
				String[] c2 = cards[1].split(" ");
				card1 = ImageIO.read(getClass().getResource(ResourceHandler.getCard(c1[0], c1[1])));
				card2 = ImageIO.read(getClass().getResource(ResourceHandler.getCard(c2[0], c2[1])));
			} else {
				card1 = ImageIO.read(getClass().getResource(ResourceHandler.CARD_BACK));
				card2 = ImageIO.read(getClass().getResource(ResourceHandler.CARD_BACK));
			}

			g2.drawImage(card1, (16 * width) / 100, (36 * height) / 100 - cardHeight / 2, cardWidth, cardHeight,
					null);
			g2.drawImage(card2, (16 * width) / 100 + cardWidth + width / 100, (36 * height) / 100  - cardHeight / 2,
					cardWidth, cardHeight, null);

			// Draw player 2 hole cards
			if (actionPoint.getP2HoleCards().known()) {
				String[] cards = actionPoint.getP2HoleCards().getCards();
				String[] c1 = cards[0].split(" ");
				String[] c2 = cards[1].split(" ");
				card1 = ImageIO.read(getClass().getResource(ResourceHandler.getCard(c1[0], c1[1])));
				card1 = ImageIO.read(getClass().getResource(ResourceHandler.getCard(c2[0], c2[1])));
			} else {
				card1 = ImageIO.read(getClass().getResource(ResourceHandler.CARD_BACK));
				card2 = ImageIO.read(getClass().getResource(ResourceHandler.CARD_BACK));
			}

			g2.drawImage(card1, (79 * width) / 100 - (cardWidth + width / 100), (36 * height) / 100 - cardHeight / 2,
					cardWidth, cardHeight, null);
			g2.drawImage(card2, (79 * width) / 100, (36 * height) / 100 - cardHeight / 2, cardWidth, cardHeight, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*** End hole cards ***/

		/*** Draw community cards ***/
		List<String> board = actionPoint.getBoard();
		int boardCardWidth = (int) (0.06 * width);
		int boardCardHeight = (int) (726 * boardCardWidth) / 500;
		try {
			for (int i = 0; i < board.size(); ++i) {
				String[] rankSuit = board.get(i).split(" ");
				Image card = ImageIO.read(getClass().getResource(
						ResourceHandler.getCard(rankSuit[0], rankSuit[1])));

				int xOffset = ((19 * width) / 20 - 5 * boardCardWidth) / 2 + i * (boardCardWidth + width / 100);
				g2.drawImage(card, xOffset, bubbleY - boardCardHeight / 2, boardCardWidth, boardCardHeight, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*** End community cards ***/

		/*** Pot ***/
		if (actionPoint.getPot() > 0) {
			String potString = "" + actionPoint.getPot();
			g2.setFont(FontUtils.getLargestPossibleFont(g2, (15 * width) / 100, (7 * height) / 100, potString));
			g2.drawString(potString, (58 * width) / 100 - g2.getFontMetrics().stringWidth(potString) / 2,
					(26 * height) / 100);

			Image potImage;
			try {
				potImage = ImageIO.read(getClass().getResource(ResourceHandler.POT));
				int potImageWidth = (8 * width) / 100;
				int potImageHeight = (246 * potImageWidth) / 306;
				g2.drawImage(potImage, width / 2 - potImageWidth / 2, (19 * height) / 100, potImageWidth,
						potImageHeight, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*** End pot ***/

		/*** Draw betting chips ***/
		if (actionPoint.getP1ChipCount() != actionPoint.getP1Behind()) {
			try {
				Image chips = ImageIO.read(getClass().getResource(ResourceHandler.CHIPS));
				int chipWidth = (5 * width) / 100;
				int chipHeight = (516 * chipWidth) / 630;

				g2.drawImage(chips, (22 * width) / 100, (55 * height) / 100, chipWidth, chipHeight, null);

				int betAmount = actionPoint.getP1ChipCount() - actionPoint.getP1Behind();
				g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
						+ betAmount));
				g2.drawString("" + betAmount, (29 * width) / 100, (60 * height) / 100);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (actionPoint.getP2ChipCount() != actionPoint.getP2Behind()) {
			try {
				Image chips = ImageIO.read(getClass().getResource(ResourceHandler.CHIPS));
				int chipWidth = (5 * width) / 100;
				int chipHeight = (516 * chipWidth) / 630;

				g2.drawImage(chips, (78 * width) / 100, (55 * height) / 100, chipWidth, chipHeight, null);

				int betAmount = actionPoint.getP2ChipCount() - actionPoint.getP2Behind();
				g2.setFont(FontUtils.getLargestPossibleFont(g2, (12 * width) / 100, (4 * height) / 100, ""
						+ betAmount));
				g2.drawString("" + betAmount, (71 * width) / 100, (60 * height) / 100);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*** End betting chips ***/
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Texas Hold'em");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.addWindowFocusListener(new WindowFocusListener() {
					@Override
					public void windowLostFocus(WindowEvent e) {
					}

					@Override
					public void windowGainedFocus(WindowEvent e) {
						e.getWindow().repaint();
					}
				});

				int width = 800;
				int height = (width * 915) / 1280;
				frame.setSize(width, height);

				ActionPoint action = new ActionPoint(1000, 1000, 800, 600, 200, new ActionType(ActionType.RAISE,
						400), 2, new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
						"ace diamonds", "5 clubs", "7 spades"));
				Match match = new Match("Kelsey", "Priya", Arrays.asList(action));
				TourneyModel model = new TourneyModel(Arrays.asList(match));

				model.nextMatch();
				model.nextActionPoint();

				TableView view = new TableView(model);
				model.addObserver(view);

				frame.add(view);

				frame.setVisible(true);
			}
		});
	}
}
