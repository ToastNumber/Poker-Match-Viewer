package poker.play;

import java.util.List;

public class Action {
	private final int p1ChipCount;
	private final int p2ChipCount;
	private final int p1Bet;
	private final int p2Bet;
	private final int pot;
	private final ActionType playerAction;
	private final int actorIndex;
	private final HoleCards p1HoleCards;
	private final HoleCards p2HoleCards;
	private final List<String> board;
	private final int buttonIndex;

	public Action(int p1ChipCount, int p2ChipCount, int p1Bet, int p2Bet, int pot, ActionType action, int actorIndex,
			HoleCards p1HoleCards, HoleCards p2HoleCards, List<String> board, int buttonIndex) {
		super();
		this.p1ChipCount = p1ChipCount;
		this.p2ChipCount = p2ChipCount;
		this.p1Bet = p1Bet;
		this.p2Bet = p2Bet;
		this.pot = pot;
		this.playerAction = action;
		this.actorIndex = actorIndex;
		this.p1HoleCards = p1HoleCards;
		this.p2HoleCards = p2HoleCards;
		this.board = board;
		this.buttonIndex = buttonIndex;
	}
	
	public int getP1ChipCount() {
		return p1ChipCount;
	}

	public int getP2ChipCount() {
		return p2ChipCount;
	}

	public int getPot() {
		return pot;
	}

	public ActionType getPlayerAction() {
		return playerAction;
	}

	public int getActorIndex() {
		return actorIndex;
	}
	
	public HoleCards getP1HoleCards() {
		return p1HoleCards;
	}
	
	public HoleCards getP2HoleCards() {
		return p2HoleCards;
	}
	
	public List<String> getBoard() {
		return board;
	}
	
	public int getButtonIndex() {
		return buttonIndex;
	}

	@Override
	public String toString() {
		return "Action [p1ChipCount=" + p1ChipCount + ",\np2ChipCount=" + p2ChipCount + ",\npot=" + pot
				+ ",\nplayerAction=" + playerAction + ",\nactorIndex=" + actorIndex + "]";
	}

	public int getP1Bet() {
		return p1Bet;
	}

	public int getP2Bet() {
		return p2Bet;
	}
	
}
