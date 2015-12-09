package poker.play;

public class Action {
	private final int p1ChipCount;
	private final int p2ChipCount;
	private final int pot;
	private final ActionType playerAction;
	private final int actorIndex;

	public Action(int p1ChipCount, int p2ChipCount, int pot, ActionType playerAction, int actorIndex) {
		super();
		this.p1ChipCount = p1ChipCount;
		this.p2ChipCount = p2ChipCount;
		this.pot = pot;
		this.playerAction = playerAction;
		this.actorIndex = actorIndex;
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

	@Override
	public String toString() {
		return "Action [p1ChipCount=" + p1ChipCount + ",\np2ChipCount=" + p2ChipCount + ",\npot=" + pot
				+ ",\nplayerAction=" + playerAction + ",\nactorIndex=" + actorIndex + "]";
	}
	
	
}
