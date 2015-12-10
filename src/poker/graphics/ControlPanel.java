package poker.graphics;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import poker.play.TourneyModel;

public class ControlPanel extends JPanel implements Observer {
	private final JButton btnBackward;
	private final JButton btnForward;
	private final JButton btnPrevHand;
	private final JButton btnNextHand;
	private final JButton btnPrevMatch;
	private final JButton btnNextMatch;
	private final TourneyModel model;

	public ControlPanel(TourneyModel model) {
		super();

		this.model = model;

		btnBackward = new JButton("Previous");
		btnBackward.setFocusable(false);
		btnBackward.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.previousActionPoint();
				}
			});

			t.start();
		});

		btnForward = new JButton("Next");
		btnForward.setFocusable(false);
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
		btnNextMatch.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.nextMatch();
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
		
		// Set the enabled states of the buttons
		update(null, null);
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
