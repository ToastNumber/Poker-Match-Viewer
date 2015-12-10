package poker.play;

import java.util.List;
import java.util.Observable;

public class TourneyModel extends Observable {
	private final String p1Name;
	private final String p2Name;
	private List<Match> matches;
	private int matchIndex = -1;

	public TourneyModel(List<Match> matches, String p1Name, String p2Name) {
		this.matches = matches;
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		
		start();
	}
	
	public void setMatches(List<Match> matches) {
		this.matches = matches;
		start();
	}

	private Match currentMatch() {
		return matches.get(matchIndex);
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

	/**
	 * Moves to the next match
	 */
	public void nextHand() {
		currentMatch().nextHand();
		setChanged();
		notifyObservers();
	}
	
	public void previousHand() {
		currentMatch().previousHand();
		setChanged();
		notifyObservers();
	}
	
	public int getHandIndex() { 
		return currentMatch().getHandIndex();
	}
	
	public int getNumHands() {
		return currentMatch().getNumHands();
	}
	
	public boolean isFirstHand() {
		return currentMatch().isFirstHand();
	}
	
	public boolean isLastHand() {
		return currentMatch().isLastHand();
	}
	
	/**
	 * Moves to the next match
	 */
	public void nextMatch() {
		if (matchIndex + 1 < matches.size()) {
			++matchIndex;
			currentMatch().start();

			setChanged();
			notifyObservers();
		} else {
			throw new IndexOutOfBoundsException("There are no more tourneys");
		}
	}

	/**
	 * Moves to the previous match
	 */
	public void previousMatch() {
		if (matchIndex - 1 >= 0) {
			--matchIndex;
			currentMatch().start();

			setChanged();
			notifyObservers();
		} else throw new IndexOutOfBoundsException("This is the first tourney");
	}

	/**
	 * Moves to the next action in the current match.
	 */
	public void nextActionPoint() {
		try {
			currentMatch().nextEvent();
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
			currentMatch().previousEvent();
			setChanged();
			notifyObservers();
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return the current action to be made
	 */
	public Event getActionPoint() {
		return currentMatch().getEvent();
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
	 * @return true if this is the first action of the current match; false otherwise
	 */
	public boolean isFirstActionPoint() {
		return currentMatch().isFirstEvent();
	}
	
	/**
	 * @return true if this is the last action of the current match; false otherwise.
	 */
	public boolean isLastActionPoint() {
		return currentMatch().isLastEvent();
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
	
	public void start() {
		matchIndex = 0;
		currentMatch().start();
	}
	
}
