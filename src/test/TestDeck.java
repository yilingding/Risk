package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Card;
import model.CardHand;
import model.CardType;
import model.Deck;

public class TestDeck {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	@Test
	public void test() {
		Deck deck=new Deck();
		System.out.println(deck.size());
		 assertTrue(deck.size()==44);
		Card card= new Card(CardType.Infantry, "Alaska");
		Card card1= new Card(CardType.Calvary, "Alaska");
		Card card2= new Card(CardType.Artillery, "Alaska");
		Card card3= new Card(CardType.Infantry, "Alaska");
		Card card4= new Card(CardType.Wild, "Wild");
		card.toString();
		 assertTrue(card.getType()==CardType.Infantry);
		 assertTrue(card1.getTypeString().equals("Calvary"));
		 assertTrue(card2.getTypeString().equals("Artillery"));
		 assertTrue(card3.getTypeString().equals("Infantry"));
		 assertTrue(card4.getTypeString().equals("Wild"));
		 assertTrue(card.getCountry().compareTo("Alaska")==0);
		 CardHand hand=new CardHand();
		 hand.addCard(card);
		 //deck.addCard(card);
		 assertTrue(hand.size()==1);
	}
	
	@Test
	public void testDrawCard() {
		 Deck deck=new Deck();
		 deck.shuffle();
		 deck.draw();
		 assertTrue(deck.size()==43);
	}
	
	@Test
	public void testShuffleTurnInThreeCards() {
		 Deck deck=new Deck();
		 ArrayList<Card> threeCard=new ArrayList<Card>();
		 deck.shuffle();
		 Card a=deck.draw();
		 a.getTypeString();
		 threeCard.add(a);
		 Card b=deck.draw();
		 threeCard.add(b);
		 b.getTypeString();
		 Card c=deck.draw();
		 threeCard.add(c);
		 c.getTypeString();
		 deck.shuffleInThreeCards(threeCard);
		 assertTrue(deck.size()==44);
	}

}
