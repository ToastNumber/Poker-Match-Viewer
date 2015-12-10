package poker.play;

import java.util.Optional;

import poker.graphics.util.StringUtil;

public class EventType {
	public static final EventType FOLD = new EventType("FOLD");
	public static final EventType CHECK = new EventType("CHECK");
	public static final EventType START = new EventType("START");
	public static final String CALL = "CALL";
	public static final String BET = "BET";
	public static final String RAISE = "RAISE";
	public static final String ALL_IN = "ALL IN";
	public static final String SHOW = "SHOW";
	public static final String POST_SB = "POST SB";
	public static final String POST_BB = "POST BB";
	
	private final String name;
	private final Optional<Integer> amount;
	
	private EventType(String name) {
		this.name = name;
		amount = Optional.empty();
	}
	
	public EventType(String name, int amount) {
		this.name = name;
		this.amount = Optional.of(amount);
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<Integer> getAmount() {
		return amount;
	}
	
	public String toString() {
		if (name.equals(POST_SB)) return "Post SB" + (amount.isPresent() ? " " + amount.get() : "");
		else if (name.equals(POST_BB)) return "Post BB" + (amount.isPresent() ? " " + amount.get() : "");
		else return String.format("%s%s", StringUtil.firstUpper(name), amount.isPresent() ? " " + amount.get() : "");
	}
}
