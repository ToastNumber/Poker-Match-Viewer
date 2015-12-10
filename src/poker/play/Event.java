package poker.play;

import java.util.List;

public class Event {
	private final int p1ChipCount;
	private final int p2ChipCount;
	private final int p1Behind;
	private final int p2Behind;
	private final int pot;
	private final EventType playerAction;
	private final int actorIndex;
	private final HoleCards p1HoleCards;
	private final HoleCards p2HoleCards;
	private final List<String> board;
	private final int buttonIndex;

	public Event(int p1ChipCount, int p2ChipCount, int p1Behind, int p2Behind, int pot, EventType action, int actorIndex,
			HoleCards p1HoleCards, HoleCards p2HoleCards, List<String> board, int buttonIndex) {
		super();
		this.p1ChipCount = p1ChipCount;
		this.p2ChipCount = p2ChipCount;
		this.p1Behind = p1Behind;
		this.p2Behind = p2Behind;
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

	public EventType getPlayerAction() {
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

	public int getP1Behind() {
		return p1Behind;
	}

	public int getP2Behind() {
		return p2Behind;
	}
	
}
