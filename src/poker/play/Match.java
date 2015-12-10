package poker.play;

import java.util.List;

public class Match {
	private final String p1Name;
	private final String p2Name;
	private final List<ActionPoint> actionList;
	private int actionIndex = -1;

	public Match(String p1Name, String p2Name, List<ActionPoint> actionList) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.actionList = actionList;
	}

	/**
	 * Goes to the next action. This must be called before you try to get an
	 * action, since the initial action index is -1.
	 */
	public void next() throws IndexOutOfBoundsException {
		if (actionIndex + 1 < actionList.size()) {
			++actionIndex;
		} else throw new IndexOutOfBoundsException("There are no more actions");
	}

	/**
	 * Goes to the previous action.
	 */
	public void previous() throws IndexOutOfBoundsException {
		if (actionIndex - 1 >= 0) {
			--actionIndex;
		} else throw new IndexOutOfBoundsException("This is the first action");
	}

	/**
	 * @return true if the match is about to start; false otherwise
	 */
	public boolean aboutToStart() {
		return actionIndex < 0;
	}
	
	/**
	 * @return true if this is the first action of the match; false otherwise
	 */
	public boolean isFirstActionPoint() {
		return actionIndex == 0;
	}

	/**
	 * @return true if this is the last action of the match; false otherwise.
	 */
	public boolean isLastActionPoint() {
		return actionIndex == actionList.size() - 1;
	}

	/**
	 * @return the current action of the match.
	 */
	public ActionPoint getActionPoint() {
		return actionList.get(actionIndex);
	}
	
	/**
	 * @return the name of the first player
	 */
	public String getPlayer1Name() {
		return p1Name;
	}
	
	/**
	 * @return the name of the second player
	 */
	public String getPlayer2Name() {
		return p2Name;
	}
	
	public void start() {
		actionIndex = 0;
	}
}
