package model;

import java.util.ArrayList;

public class CardHand {
	
	private ArrayList<Card> hand;
	
	public CardHand() {
		hand = new ArrayList<Card>();
	}
	
	public int size() {
		return hand.size();
	}
	
	public void addCard(Card c) {
		hand.add(c);
	}
	
	

}