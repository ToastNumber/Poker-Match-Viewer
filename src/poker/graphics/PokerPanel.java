package poker.graphics;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import poker.play.TourneyModel;

public class PokerPanel extends JPanel {
	
	public PokerPanel(TourneyModel model) {
		super();
		
		TableView view = new TableView(model);
		model.addObserver(view);
		
		ControlPanel pnlControl = new ControlPanel(model);
		model.addObserver(pnlControl);
		
		setLayout(new BorderLayout());
		add(view, BorderLayout.CENTER);
		add(pnlControl, BorderLayout.SOUTH);
	}
}
