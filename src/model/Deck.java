/**
 * The deck of cards to be drawn from players in the game.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable{
	
	private ArrayList<Card> deck;
	
	/*
	 * The Deck class constructor.
	 */
	public Deck() {
		deck = new ArrayList<Card>();
		populateDeck();
	}
	
	/*
	 * Returns the size of the deck
	 */
	public int size() {
		return deck.size();
	}
	
	/*
	 * Shuffles all the Card objects in the Deck.
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/*
	 * Draws and returns the top card on the Deck.
	 */
	public Card draw() {
		Card card = deck.remove(0);
		if (deck.size()<=0){
			deck = new ArrayList<Card>();
			populateDeck();
			Collections.shuffle(deck);
		}
		return card;
	}
	
	/*
	 * Adds the Card user turned in back to the Deck.
	 */
	public void addCard(Card card){
		deck.add(card);
	}
	
	private void populateDeck() {
		deck.add(new Card(CardType.Infantry, "Alaska"));
		deck.add(new Card(CardType.Calvary, "Northwest Territory"));
		deck.add(new Card(CardType.Artillery, "Greenland"));
		deck.add(new Card(CardType.Infantry, "Alberta"));
		deck.add(new Card(CardType.Calvary, "Ontario"));
		deck.add(new Card(CardType.Artillery, "Quebec"));
		deck.add(new Card(CardType.Infantry, "Western United States"));
		deck.add(new Card(CardType.Calvary, "Eastern United States"));
		deck.add(new Card(CardType.Artillery, "Central America"));
		deck.add(new Card(CardType.Infantry, "Venzuela"));
		deck.add(new Card(CardType.Calvary, "Peru"));
		deck.add(new Card(CardType.Artillery, "Brazil"));
		deck.add(new Card(CardType.Infantry, "Argentina"));
		deck.add(new Card(CardType.Calvary, "Great Britain"));
		deck.add(new Card(CardType.Artillery, "Iceland"));
		deck.add(new Card(CardType.Infantry, "Western Europe"));
		deck.add(new Card(CardType.Calvary, "Northern Europe"));
		deck.add(new Card(CardType.Artillery, "Southern Europe"));
		deck.add(new Card(CardType.Infantry, "Ukraine"));
		deck.add(new Card(CardType.Calvary, "Scandinavia"));
		deck.add(new Card(CardType.Artillery, "North Africa"));
		deck.add(new Card(CardType.Infantry, "Egypt"));
		deck.add(new Card(CardType.Calvary, "East Africa"));
		deck.add(new Card(CardType.Artillery, "Congo"));
		deck.add(new Card(CardType.Infantry, "South Africa"));
		deck.add(new Card(CardType.Calvary, "Madagascar"));
		deck.add(new Card(CardType.Artillery, "Middle East"));
		deck.add(new Card(CardType.Infantry, "India"));
		deck.add(new Card(CardType.Calvary, "Siam"));
		deck.add(new Card(CardType.Artillery, "Afghanistan"));
		deck.add(new Card(CardType.Infantry, "China"));
		deck.add(new Card(CardType.Calvary, "Mongolia"));
		deck.add(new Card(CardType.Artillery, "Japan"));
		deck.add(new Card(CardType.Infantry, "Ural"));
		deck.add(new Card(CardType.Calvary, "Siberia"));
		deck.add(new Card(CardType.Artillery, "Irkutsk"));
		deck.add(new Card(CardType.Infantry, "Yakutsk"));
		deck.add(new Card(CardType.Calvary, "Kamchatka"));
		deck.add(new Card(CardType.Artillery, "Indonesia"));
		deck.add(new Card(CardType.Infantry, "New Guinea"));
		deck.add(new Card(CardType.Calvary, "Western Australia"));
		deck.add(new Card(CardType.Artillery, "Eastern Australia"));
		deck.add(new Card(CardType.Wild, "Wild"));
		deck.add(new Card(CardType.Wild, "Wild"));
		
	}

	public void shuffleInThreeCards(ArrayList<Card> passedThreeCards) {
//		System.out.println("deck is" +deck);
//		System.out.println("passed3Card"+passedThreeCards.size());
		deck.addAll(passedThreeCards);
		Collections.shuffle(deck);
	}

}