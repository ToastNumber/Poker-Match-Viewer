package poker.graphics;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import poker.play.ActionPoint;
import poker.play.ActionType;
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

				ActionPoint action1 = new ActionPoint(1000, 1000, 800, 600, 200, new ActionType(ActionType.RAISE,
						400), 2, new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
						"ace diamonds", "5 clubs", "7 spades"));
				
				ActionPoint action2 = new ActionPoint(1000, 1000, 600, 600, 200, new ActionType(ActionType.CALL,
						400), 1, new HoleCards("king hearts", "queen diamonds"), new HoleCards(), Arrays.asList(
						"ace diamonds", "5 clubs", "7 spades"));
				
				Match match = new Match("Kelsey", "Priya", Arrays.asList(action1, action2));
				TourneyModel model = new TourneyModel(Arrays.asList(match));

				PokerPanel pokerPanel = new PokerPanel(model);
				frame.add(pokerPanel);
				
				frame.setVisible(true);
			}
		});
	}
}
