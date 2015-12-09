package poker.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import poker.ResourceHandler;
import poker.graphics.util.FontUtils;
import poker.play.Action;
import poker.play.ActionType;
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
		int x = (width / 2) - (g2.getFontMetrics().stringWidth(startOfMatchMsg) / 2);

		g2.drawString(startOfMatchMsg, x, y);
		/*** End of info message ***/

		/*** Draw the player bubbles ***/
		int bubbleRadius = (7 * width) / 100;
		int bubbleY = (17 * height) / 40;
		PlayerBubble p1Bubble = new PlayerBubble("Player 1", "Check", 100, (10 * width) / 100, bubbleY,
				bubbleRadius);
		p1Bubble.draw(g2);

		PlayerBubble p2Bubble = new PlayerBubble("Player 2", "Call 100", 100, (90 * width) / 100, bubbleY,
				bubbleRadius);
		p2Bubble.draw(g2);
		/*** End bubbles ***/
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				int width = 800;
				int height = (width * 915) / 1280;
				frame.setSize(width, height);
				// frame.setResizable(false);

				Action action = new Action(1000, 1000, 100, ActionType.CHECK, 1);
				Match match = new Match("A", "B", Arrays.asList(action));
				TourneyModel model = new TourneyModel(Arrays.asList(match));

				model.nextMatch();

				TableView view = new TableView(model);
				model.addObserver(view);

				frame.add(view);

				frame.setVisible(true);
			}
		});
	}
}
