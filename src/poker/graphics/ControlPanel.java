package poker.graphics;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import poker.play.TourneyModel;

public class ControlPanel extends JPanel implements Observer {
	private final JButton btnPrev;
	private final JButton btnNext;
	private final JButton btnPrevMatch;
	private final JButton btnNextMatch;
	private final TourneyModel model;

	public ControlPanel(TourneyModel model) {
		super();

		this.model = model;

		btnPrev = new JButton("Previous");
		btnPrev.setFocusable(false);
		btnPrev.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.previousActionPoint();
				}
			});

			t.start();
		});

		// Set the previous button to disabled at first
		btnPrev.setEnabled(false);

		btnNext = new JButton("Next");
		btnNext.setFocusable(false);
		btnNext.addActionListener(e -> {
			Thread t = new Thread(new Runnable() {
				public void run() {
					model.nextActionPoint();
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
		add(btnPrev);
		add(btnNext);
		add(btnPrevMatch);
		add(btnNextMatch);
	}

	@Override
	public void update(Observable o, Object arg) {
		btnPrev.setEnabled(!model.isFirstActionPoint());
		btnNext.setEnabled(!model.isLastActionPoint());
		btnPrevMatch.setEnabled(!model.isFirstMatch());
		btnNextMatch.setEnabled(!model.isLastMatch());
	}
}
