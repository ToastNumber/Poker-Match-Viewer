package poker.play;

import java.util.List;

public class Hand {
	private List<Event> events;
	private int eventIndex;

	public Hand(List<Event> actions) {
		this.events = actions;
	}

	public List<Event> getActions() {
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

	public Event getEvent() {
		return events.get(eventIndex);
	}

	public void start() {
		eventIndex = 0;
	}
}
