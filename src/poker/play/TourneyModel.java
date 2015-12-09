package poker.play;

import java.util.List;
import java.util.Observable;

public class TourneyModel extends Observable {
	private final List<Match> matches;
	private int matchIndex = -1;

	public TourneyModel(List<Match> matches) {
		this.matches = matches;
	}

	private Match currentMatch() {
		return matches.get(matchIndex);
	}
	
	/**
	 * @return the name of player 1 in the current match
	 */
	public String getP1Name() {
		return currentMatch().getPlayer1Name();
	}
	
	/**
	 * @return the name of player 2 in the current match
	 */
	public String getP2Name() {
		return currentMatch().getPlayer2Name();
	}

	/**
	 * Moves to the next match
	 */
	public void nextMatch() {
		if (matchIndex + 1 < matches.size()) {
			++matchIndex;

			setChanged();
			notifyObservers();
		} else throw new IndexOutOfBoundsException("There are no more tourneys");
	}

	/**
	 * Moves to the previous match
	 */
	public void previousMatch() {
		if (matchIndex - 1 >= 0) {
			--matchIndex;

			setChanged();
			notifyObservers();
		} else throw new IndexOutOfBoundsException("This is the first tourney");
	}

	/**
	 * Moves to the next action in the current match.
	 */
	public void nextActionPoint() {
		try {
			currentMatch().next();
			setChanged();
			notifyObservers();
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Moves to the previous action in the current match.
	 */
	public void previousActionPoint() {
		try {
			currentMatch().previous();
			setChanged();
			notifyObservers();
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return the current action to be made
	 */
	public ActionPoint getActionPoint() {
		return currentMatch().getActionPoint();
	}

	/**
	 * @return true if it is the first match; false otherwise
	 */
	public boolean isFirstMatch() {
		return matchIndex == 0;
	}

	/**
	 * @return true if this is the last match; false otherwise.
	 */
	public boolean isLastMatch() {
		return matchIndex == matches.size() - 1;
	}

	/**
	 * @return true if the match is about to start; false otherwise
	 */
	public boolean matchAboutToStart() {
		return currentMatch().aboutToStart();
	}

	/**
	 * @return true if this is the first action of the current match; false otherwise
	 */
	public boolean isFirstActionPoint() {
		return currentMatch().isFirstActionPoint();
	}
	
	/**
	 * @return true if this is the last action of the current match; false otherwise.
	 */
	public boolean isLastActionPoint() {
		return currentMatch().isLastActionPoint();
	}

	/**
	 * @return the index of the current match
	 */
	public int getMatchIndex() {
		return matchIndex;
	}
	
	/**
	 * @return the number of matches in this tournament
	 */
	public int getNumMatches() {
		return matches.size();
	}
	
	public String toString() {
		return currentMatch().toString();
	}
}
