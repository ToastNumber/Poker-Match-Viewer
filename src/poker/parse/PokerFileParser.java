package poker.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import poker.play.Hand;
import poker.play.HoleCards;
import poker.play.Match;
import poker.play.TourneyModel;

public class PokerFileParser {
	public static List<String> parseCards(JSONArray cards) {
		for (int i = 0; i < cards.size(); ++i) {
			JSONObject current = (JSONObject) cards.get(i);

			System.out.println(current.get("rank"));
			System.out.println(current.get("suit"));
		}
		
		return null;
	}
	
	public static Hand parseHand(int p1ChipCount, int p2ChipCount, int buttonIndex, JSONObject hand) {
		Object p1TempCards = hand.get("p1Cards");
		Object p1Cards;
		if (p1TempCards == null) p1Cards = new HoleCards();
		else {
			List<String> arr = parseCards((JSONArray) p1TempCards);
			p1Cards = new HoleCards(arr.get(0), arr.get(1));
		}
		
		Object p2TempCards = hand.get("p1Cards");
		Object p2Cards;
		if (p2TempCards == null) p2Cards = new HoleCards();
		else {
			List<String> arr = parseCards((JSONArray) p2TempCards);
			p2Cards = new HoleCards(arr.get(0), arr.get(1));
		}
		
		
		
		return null;
	}
	
	public static Match parseMatch(JSONObject match) {
		int p1ChipCount = Integer.valueOf((String) match.get("p1ChipCount"));
		int p2ChipCount = Integer.valueOf((String) match.get("p2ChipCount"));
		int buttonIndex = (int) match.get("buttonIndex");
		
		JSONArray hands = (JSONArray) match.get("hands");
		
		List<Hand> handList = new ArrayList<>();
		for (Object obj : hands) {
			JSONObject current = (JSONObject) obj;
			handList.add(parseHand(p1ChipCount, p2ChipCount, buttonIndex, current));
		}
		
		return new Match(handList);
	}
	
	public static List<Match> parseMatches(JSONArray matches) {
		List<Match> svar = new ArrayList<>();
		
		for (Object obj : matches) {
			JSONObject current = (JSONObject) obj;
			svar.add(parseMatch(current));
		}
		
		return svar;
	}
	
	public static TourneyModel parseTourney(String filePath) throws FileNotFoundException, IOException, ParseException {
		JSONObject file = (JSONObject) (new JSONParser()).parse(new FileReader(filePath));
		
		String p1Name = (String) file.get("p1Name");
		String p2Name = (String) file.get("p2Name");
		
		JSONArray matches = (JSONArray) file.get("matches");
		List<Match> parsedMatches = parseMatches(matches);
		
		return new TourneyModel(parsedMatches, p1Name, p2Name);
	}
	
	public static void main(String[] args) {
		try {
			JSONObject a = (JSONObject) (new JSONParser()).parse(new FileReader("asd.json"));
			JSONArray arr = (JSONArray) a.get("p1Cards");
			parseCards(arr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
