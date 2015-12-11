package poker.play;

import java.util.List;

public class Tourney {
	private final List<Match> matches;
	private final String p1Name;
	private final String p2Name;
	
	public Tourney(List<Match> matches, String p1Name, String p2Name) {
		super();
		this.matches = matches;
		this.p1Name = p1Name;
		this.p2Name = p2Name;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public String getP1Name() {
		return p1Name;
	}

	public String getP2Name() {
		return p2Name;
	}
	
}
