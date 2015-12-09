package poker.play;

import java.util.Optional;

public class ActionType {
	public static final ActionType FOLD = new ActionType("FOLD");
	public static final ActionType CHECK = new ActionType("CHECK");
	public static final ActionType CALL = new ActionType("CALL");
	public static final String BET = "BET";
	public static final String RAISE = "RAISE";
	public static final String ALL_IN = "ALL IN";
	public static final String SHOW = "SHOW";
	public static final String POST = "POST";
	
	private final String name;
	private final Optional<Integer> amount;
	
	private ActionType(String name) {
		this.name = name;
		amount = Optional.empty();
	}
	
	public ActionType(String name, int amount) {
		this.name = name;
		this.amount = Optional.of(amount);
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<Integer> getAmount() {
		return amount;
	}
}
