package poker.play;

import java.util.List;

public class Match {
	private final List<Hand> hands;
	private int handIndex;

	public Match(List<Hand> hands) {
		this.hands = hands;
	}
	
	public Hand currentHand() {
		return hands.get(handIndex);
	}

	public void nextHand() {
		if (handIndex + 1 < hands.size()) {
			++handIndex;
			currentHand().start();
		}
	}

	public void previousHand() {
		if (handIndex - 1 >= 0){
			--handIndex;
			currentHand().start();
		}
	}

	public int getHandIndex() {
		return handIndex;
	}

	public int getNumHands() {
		return hands.size();
	}

	public boolean isFirstHand() {
		return handIndex == 0;
	}

	public boolean isLastHand() {
		return handIndex == hands.size() - 1;
	}

	/**
	 * Goes to the next action. This must be called before you try to get an
	 * action, since the initial action index is -1.
	 */
	public void nextEvent() throws IndexOutOfBoundsException {
		currentHand().nextEvent();
	}

	/**
	 * Goes to the previous action.
	 */
	public void previousEvent() throws IndexOutOfBoundsException {
		currentHand().previousEvent();
	}

	/**
	 * @return true if this is the first action of the match; false otherwise
	 */
	public boolean isFirstEvent() {
		return currentHand().isFirstEvent();
	}

	/**
	 * @return true if this is the last action of the match; false otherwise.
	 */
	public boolean isLastEvent() {
		return currentHand().isLastEvent();
	}

	/**
	 * @return the current action of the match.
	 */
	public Action getEvent() {
		return currentHand().getEvent();
	}

	public void start() {
		handIndex = 0;
		currentHand().start();
	}
}
