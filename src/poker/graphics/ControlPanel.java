package poker.graphics;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import poker.parse.PokerFileParser;
import poker.play.Tourney;
import poker.play.TourneyModel;

public class ControlPanel extends JPanel implements Observer {
	private final JButton btnBackward;
	private final JButton btnForward;
	private final JButton btnPrevHand;
	private final JButton btnNextHand;
	private final JButton btnPrevMatch;
	private final JButton btnNextMatch;
	private final JButton btnOpenTourney;
	private final TourneyModel model;

	public ControlPanel(TourneyModel model) {
		super();

		this.model = model;

		btnBackward = new JButton("◄");
		btnBackward.setFocusable(false);
		btnBackward.setEnabled(false);
		btnBackward.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.previousActionPoint();
				}
			});

			t.start();
		});

		btnForward = new JButton("►");
		btnForward.setFocusable(false);
		btnForward.setEnabled(false);
		btnForward.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.nextActionPoint();
				}
			});

			t.start();
		});
		
		btnPrevHand = new JButton("Previous hand");
		btnPrevHand.setFocusable(false);
		btnPrevHand.setEnabled(false);
		btnPrevHand.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.previousHand();
				}
			});

			t.start();			
		});
		
		btnNextHand = new JButton("Next hand");
		btnNextHand.setFocusable(false);
		btnNextHand.setEnabled(false);
		btnNextHand.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.nextHand();
				}
			});

			t.start();			
		});

		btnPrevMatch = new JButton("Previous match");
		btnPrevMatch.setFocusable(false);
		btnPrevMatch.setEnabled(false);
		btnPrevMatch.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.previousMatch();;
				}
			});

			t.start();
		});
		
		btnNextMatch = new JButton("Next match");
		btnNextMatch.setFocusable(false);
		btnNextMatch.setEnabled(false);
		btnNextMatch.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.nextMatch();
				}
			});

			t.start();
		});

		btnOpenTourney = new JButton("Open Tournament");
		btnOpenTourney.setFocusable(false);
		btnOpenTourney.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
					// Only allow a single directory to be selected.
					fc.setMultiSelectionEnabled(false);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

					// Show the dialog
					int option = fc.showOpenDialog(null);
					
					// If the user selected a file and clicked 'Open'
					if (option == JFileChooser.APPROVE_OPTION) {
						// Then set the text of the field to the selected
						// directory.
						File file = fc.getSelectedFile();
						Tourney t;
						try {
							t = PokerFileParser.parseTourney(file.getAbsolutePath());
							model.ofTourney(t);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (IOException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (ParseException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
						
					}
				}
			});

			t.start();
		});
		
		setLayout(new FlowLayout());
		add(btnBackward);
		add(btnForward);
		add(btnPrevHand);
		add(btnNextHand);
		add(btnPrevMatch);
		add(btnNextMatch);
		add(btnOpenTourney);
	}

	@Override
	public void update(Observable o, Object arg) {
		btnBackward.setEnabled(!model.isFirstActionPoint());
		btnForward.setEnabled(!model.isLastActionPoint());
		btnPrevHand.setEnabled(!model.isFirstHand());
		btnNextHand.setEnabled(!model.isLastHand());
		btnPrevMatch.setEnabled(!model.isFirstMatch());
		btnNextMatch.setEnabled(!model.isLastMatch());
	}
}
