package poker.play;

import java.util.List;

public class Match {
	private final String p1Name;
	private final String p2Name;
	private final List<ActionPoint> actionList;
	private int actionIndex = 0;

	public Match(String p1Name, String p2Name, List<ActionPoint> actionList) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.actionList = actionList;
	}

	public void nextHand() {
		boolean found = false;

		for (int i = actionIndex + 1; i < actionList.size(); ++i) {
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				found = true;
				actionIndex = i;
			}
		}

		if (!found) throw new IndexOutOfBoundsException("There are no more hands");
	}

	public void previousHand() {
		boolean found = false;

		for (int i = actionIndex - 1; i >= 0; --i) {
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				found = true;
				actionIndex = i;
			}
		}

		if (!found) throw new IndexOutOfBoundsException("This is the first hand");
	}
	
	public int getHandIndex() {
		int svar = 0;
		
		for (int i = actionIndex; i >= 0; --i) {
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				++svar;
			}
		}
		
		// Make it 0-based
		return svar - 1;
	}
	
	public int getNumHands() {
		int svar = 0;
		
		for (int i = 0; i < actionList.size(); ++i) {
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				++svar;
			}
		}
		
		return svar;
	}

	public boolean isFirstHand() {
		// Go backwards to try and find a hand start
		for (int i = actionIndex - 1; i >= 0; --i) {
			// / If we find another hand, then return false
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				return false;
			}
		}

		// We haven't found another hand before this one
		// so return true
		return true;
	}

	public boolean isLastHand() {
		// Go forwards to try and find a hand start
		for (int i = actionIndex + 1; i < actionList.size(); ++i) {
			// If we find another hand, then return false
			if (actionList.get(i).getPlayerAction().equals(ActionType.START)) {
				return false;
			}
		}

		// We haven't found another hand before this one
		// so return true
		return true;
	}

	/**
	 * Goes to the next action. This must be called before you try to get an
	 * action, since the initial action index is -1.
	 */
	public void nextAction() throws IndexOutOfBoundsException {
		if (actionIndex + 1 < actionList.size()) {
			++actionIndex;
		} else throw new IndexOutOfBoundsException("There are no more actions");
	}

	/**
	 * Goes to the previous action.
	 */
	public void previousAction() throws IndexOutOfBoundsException {
		if (actionIndex - 1 >= 0) {
			--actionIndex;
		} else throw new IndexOutOfBoundsException("This is the first action");
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
