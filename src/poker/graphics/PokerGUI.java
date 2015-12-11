package poker.graphics;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.json.simple.parser.ParseException;

import poker.play.TourneyModel;

public class PokerGUI {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		TourneyModel model = new TourneyModel(Arrays.asList(), "", "");
			
		PokerPanel pokerPanel = new PokerPanel(model);

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
				
				frame.add(pokerPanel);

				frame.setVisible(true);
			}
		});
	}
}
