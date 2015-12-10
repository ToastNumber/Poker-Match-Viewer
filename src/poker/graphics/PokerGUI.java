package poker.graphics;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import poker.play.Event;
import poker.play.EventType;
import poker.play.HoleCards;
import poker.play.Match;
import poker.play.TourneyModel;

public class PokerGUI {
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

				Event action0 = new Event(1000, 1000, 1000, 1000, 200, EventType.START, -1,
						new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
								"ace diamonds", "5 clubs", "7 spades"), 2);

				Event action1 = new Event(1000, 1000, 800, 600, 200, new EventType(EventType.RAISE,
						400), 2, new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
						"ace diamonds", "5 clubs", "7 spades"), 2);

				Event action2 = new Event(1000, 1000, 600, 600, 200, new EventType(EventType.CALL,
						400), 1, new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
						"ace diamonds", "5 clubs", "7 spades"), 2);

				Event action3 = new Event(500, 500, 500, 500, 0, EventType.START, -1, new HoleCards(
						"king clubs", "king spades"), new HoleCards("queen hearts", "queen clubs"), Arrays
						.asList(), 1);

				Event action4 = new Event(500, 500, 490, 500, 0, new EventType(EventType.POST_SB,
						10), 1, new HoleCards("king clubs", "king spades"), new HoleCards("queen hearts",
						"queen clubs"), Arrays.asList(), 1);

				Event action5 = new Event(500, 500, 490, 480, 0, new EventType(EventType.POST_BB,
						20), 2, new HoleCards("king clubs", "king spades"), new HoleCards("queen hearts",
						"queen clubs"), Arrays.asList(), 1);

				Match match = new Match(Arrays.asList(action0, action1, action2, action3, action4,
						action5));

				TourneyModel model = new TourneyModel(Arrays.asList(match), "Kelsey", "Priya");

				PokerPanel pokerPanel = new PokerPanel(model);
				frame.add(pokerPanel);

				frame.setVisible(true);
			}
		});
	}
}
