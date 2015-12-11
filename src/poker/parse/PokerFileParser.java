package poker.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import poker.play.Action;
import poker.play.ActionType;
import poker.play.Hand;
import poker.play.HoleCards;
import poker.play.Match;
import poker.play.Tourney;

public class PokerFileParser {
	public static List<String> parseCards(JSONArray cards) {
		List<String> svar = new ArrayList<>();

		for (int i = 0; i < cards.size(); ++i) {
			JSONObject current = (JSONObject) cards.get(i);
			String rank = (String) current.get("rank");
			String suit = (String) current.get("suit");

			svar.add(rank + " " + suit);
		}

		return svar;
	}

	public static Hand parseHand(int p1ChipCount, int p2ChipCount, int buttonIndex, JSONObject hand) {
		int winnerIndex = intValue(hand, "winner");

		Object p1TempCards = hand.get("p1Cards");
		HoleCards p1Cards;
		if (p1TempCards == null) p1Cards = new HoleCards();
		else {
			List<String> arr = parseCards((JSONArray) p1TempCards);
			p1Cards = new HoleCards(arr.get(0), arr.get(1));
		}

		Object p2TempCards = hand.get("p2Cards");
		HoleCards p2Cards;
		if (p2TempCards == null) p2Cards = new HoleCards();
		else {
			List<String> arr = parseCards((JSONArray) p2TempCards);
			p2Cards = new HoleCards(arr.get(0), arr.get(1));
		}

		List<String> boardCards;
		if (hand.containsKey("board")) {
			boardCards = parseCards((JSONArray) hand.get("board"));
		} else {
			boardCards = new ArrayList<>();
		}

		List<Action> svar = new ArrayList<>();
		// Initially the potis 0
		int pot = 0;
		int p1Bet = 0;
		int p2Bet = 0;
		// Initially no community cards shown
		int boardLength = 0;
		// Whoever is the button first acts first
		int actorIndex = buttonIndex;

		// Now calculate the actions
		JSONArray actions = (JSONArray) hand.get("actions");
		for (Object obj : actions) {
			JSONObject currentAction = (JSONObject) obj;
			String name = (String) currentAction.get("name");

			if (name.equals("deal")) {
				svar.add(new Action(p1ChipCount, p2ChipCount, 0, 0, 0, ActionType.START, -1, p1Cards, p2Cards,
						Arrays.asList(), buttonIndex));

				// Don't change the actor
				continue;
				
			} else if (name.equals("flop") || name.equals("turn") || name.equals("river")) {
				switch (name) {
					case "flop":
						boardLength = 3;
						break;
					case "turn":
						boardLength = 4;
						break;
					case "river":
						boardLength = 5;
						break;
					default:
						break;
				}

				// Set the actor as the button, so that the next action will be
				// played by the other player.
				actorIndex = buttonIndex;

				svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, ActionType.DEAL, -1, p1Cards,
						p2Cards, boardCards.subList(0, boardLength), buttonIndex));
			} else {
				if (name.equals("call")) {
					if (actorIndex == 1) {
						// You can't call more than your chip count
						p1Bet = Math.min(p1ChipCount, p2Bet);
					} else {
						p2Bet = Math.min(p2ChipCount, p1Bet);
					}

					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, ActionType.CALL, actorIndex,
							p1Cards, p2Cards, boardCards.subList(0, boardLength), buttonIndex));

					// Put the bets in the pot
					pot += p1Bet + p2Bet;

					// Decrease the chip counts by the amount they have bet
					p1ChipCount -= p1Bet;
					p2ChipCount -= p2Bet;

					// Now the bets aren't in front of the players
					p1Bet = 0;
					p2Bet = 0;
				} else if (name.equals("check")) {
					svar.add(new Action(p1ChipCount, p2ChipCount, 0, 0, pot, ActionType.CHECK, actorIndex,
							p1Cards, p2Cards, boardCards.subList(0, boardLength), buttonIndex));
				} else if (name.equals("fold")) {
					if (actorIndex == 1) {
						// Put their chips in the pot
						pot += p1Bet;
						// And take it away from them
						p1ChipCount -= p1Bet;
						p1Bet = 0;
						// Set their cards as unknown
						p1Cards = new HoleCards();
						
						// Give the chips back to the other player
						p2Bet = 0;
					} else {
						pot += p2Bet;
						p2ChipCount -= p2Bet;
						p2Bet = 0;
						p2Cards = new HoleCards();
						
						p1Bet = 0;
					}

					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, ActionType.FOLD, actorIndex,
							p1Cards, p2Cards, boardCards.subList(0, boardLength), buttonIndex));
				} else if (name.equals("win")) {
					int winAmount = intValue(currentAction, "amount");

					if (winnerIndex == 1) {
						p1ChipCount += winAmount;
					} else {
						p2ChipCount += winAmount;
					}

					pot -= winAmount;

					svar.add(new Action(p1ChipCount, p2ChipCount, 0, 0, pot, new ActionType(ActionType.WIN,
							winAmount), winnerIndex, p1Cards, p2Cards, boardCards.subList(0, boardLength),
							buttonIndex));
				} else if (name.equals("show")) {
					JSONArray cards = (JSONArray) currentAction.get("cards");
					List<String> cardsShown = parseCards(cards);

					if (actorIndex == 1) {
						p1Cards = new HoleCards(cardsShown.get(0), cardsShown.get(1));
					} else {
						p2Cards = new HoleCards(cardsShown.get(0), cardsShown.get(1));
					}
					
					// Put any bets in the pot
					

					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, ActionType.SHOW, actorIndex,
							p1Cards, p2Cards, boardCards.subList(0, boardLength), buttonIndex));
				} else if (name.equals("take excess")) {
					int excessAmount = (int) currentAction.get("amount");

					if (actorIndex == 1) {
						p1ChipCount += excessAmount;
					} else {
						p2ChipCount += excessAmount;
					}

					pot -= excessAmount;

					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, new ActionType(
							ActionType.TAKE_EXCESS, excessAmount), actorIndex, p1Cards, p2Cards, boardCards
							.subList(0, boardLength), buttonIndex));
				} else if (name.equals("all in")) {
					if (actorIndex == 1) {
						p1Bet = p1ChipCount;
					} else {
						p2Bet = p2ChipCount;
					}
					
					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, ActionType.ALL_IN, actorIndex,
							p1Cards, p2Cards, boardCards.subList(0, boardLength), buttonIndex));
				} else {
					int actionAmount = intValue(currentAction, "amount");

					if (actorIndex == 1) {
						p1Bet = actionAmount;
					} else {
						p2Bet = actionAmount;
					}

					ActionType type;
					if (name.equals("bet")) {
						type = new ActionType(ActionType.BET, actionAmount);
					} else if (name.equals("raise")) {
						type = new ActionType(ActionType.RAISE, actionAmount);
					} else if (name.equals("post ante")) {
						type = new ActionType(ActionType.POST_ANTE, actionAmount);
					} else if (name.equals("post sb")) {
						type = new ActionType(ActionType.POST_SB, actionAmount);
					} else if (name.equals("post bb")) {
						type = new ActionType(ActionType.POST_BB, actionAmount);
					} else {
						throw new IllegalArgumentException(name + " is an unknown action type");
					}

					svar.add(new Action(p1ChipCount, p2ChipCount, p1Bet, p2Bet, pot, type, actorIndex, p1Cards,
							p2Cards, boardCards.subList(0, boardLength), buttonIndex));
				}
			}

			// Move to the next player
			actorIndex = 1 + (actorIndex % 2);
		}

		return new Hand(svar);
	}

	private static int intValue(JSONObject obj, String field) {
		return ((Long) obj.get(field)).intValue();
	}

	public static Match parseMatch(JSONObject match) {
		int p1ChipCount = intValue(match, "p1ChipCount");
		int p2ChipCount = intValue(match, "p2ChipCount");
		int buttonIndex = intValue(match, "buttonIndex");

		JSONArray hands = (JSONArray) match.get("hands");

		List<Hand> handList = new ArrayList<>();
		for (Object obj : hands) {
			JSONObject current = (JSONObject) obj;
			Hand currentHand = parseHand(p1ChipCount, p2ChipCount, buttonIndex, current);
			handList.add(currentHand);

			p1ChipCount = currentHand.getResultingP1ChipCount();
			p2ChipCount = currentHand.getResultingP2ChipCount();

			buttonIndex = 1 + (buttonIndex % 2);
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

	public static Tourney parseTourney(String filePath) throws FileNotFoundException, IOException,
			ParseException {
		JSONObject file = (JSONObject) (new JSONParser()).parse(new FileReader(filePath));

		String p1Name = (String) file.get("p1Name");
		String p2Name = (String) file.get("p2Name");

		JSONArray matches = (JSONArray) file.get("matches");
		List<Match> parsedMatches = parseMatches(matches);

		System.out.println("Finished parsing.");

		return new Tourney(parsedMatches, p1Name, p2Name);
	}

}
