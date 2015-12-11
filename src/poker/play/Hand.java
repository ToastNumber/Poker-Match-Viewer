package poker.play;

import java.util.List;

public class Hand {
	private List<Action> events;
	private int eventIndex;

	public Hand(List<Action> actions) {
		this.events = actions;
	}

	public List<Action> getActions() {
		return events;
	}

	public void nextEvent() {
		if (eventIndex + 1 < events.size()) {
			++eventIndex;
		} else throw new IndexOutOfBoundsException("There are no more events.");
	}

	public void previousEvent() {
		if (eventIndex - 1 >= 0) {
			--eventIndex;
		} else throw new IndexOutOfBoundsException("There are no more previous events.");
	}

	public boolean isFirstEvent() {
		return eventIndex == 0;
	}

	public boolean isLastEvent() {
		return eventIndex == events.size() - 1;
	}

	public Action getEvent() {
		return events.get(eventIndex);
	}

	public void start() {
		eventIndex = 0;
	}
	
	public int getResultingP1ChipCount() {
		return events.get(events.size() - 1).getP1ChipCount();
	}
	
	public int getResultingP2ChipCount() {
		return events.get(events.size() - 1).getP2ChipCount();
	}
	
}
