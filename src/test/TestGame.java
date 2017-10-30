package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Card;
import model.CardType;
import model.ComputerPlayer;
import model.Continent;
import model.Country;
import model.DiceCollection;
import model.HumanPlayer;
import model.Map;
import model.Player;
import model.PlayerList;
import model.RiskGame;

public class TestGame {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		RiskGame game=new RiskGame();
		game.addHumanPlayer("1", "blue");
		game.addExpertAI("2", "green");
		game.addIntermediateAI("3", "purple");
		game.addSimpleAI("4", "orange");
		game.setCurrentPlayer(1);
		assertTrue(game.returnCurrentPlayer()==1);
		game.setInitialArmies();
		assertTrue(game.getInitialArmy()==30);
		game.setStage("hello");
		assertTrue(game.getStage().compareTo("hello")==0);
		assertTrue(game.getPlayerList().getPlayerSize()==4);
		assertTrue(game.getPlayerList().getNumberOfAI()==3);
		
		assertTrue(game.getPlayerList().getAI().size()==3);
		assertTrue(game.getPlayerList().getHumanPlayer().size()==1);
		assertTrue(game.getPlayerList().getNumberOfAI()==3);
		Player pla=game.getPlayerList().getPlayer(0);
		assertTrue(game.getPlayerList().getPlayerByIndex(0)==pla);
		game.getPlayerList().iterateToNextPlayer(pla);
		assertTrue(game.getPlayerList().getPlayerColor("3").compareTo("purple")==0);
		game.getPlayerList().remove(pla);
		Player hu=game.getPlayerList().getPlayerByName("blue");
		assertTrue(game.getPlayerList().getPlayerSize()==3);
		HumanPlayer hu1=new HumanPlayer(" "," ");
		ComputerPlayer co=new ComputerPlayer(" "," ");
		assertTrue(game.getPlayerList().isHuman(hu1));
		assertFalse(game.getPlayerList().isHuman(co));
		game.getPlayerList().countriesToString();
		game.getPlayerList().colorToString();
		game.getPlayerList().shuffle();
		assertTrue(game.getPlayerList().getColor(1).compareTo(game.getPlayerList().getColor(1))==0);
		//game.getPlayerList().reShuffleForStartPlayer(0);
		
		
	}
	@Test
	public void testSetInitialArmies() {
		RiskGame game=new RiskGame();
		game.setInitialArmies();
		assertEquals(0,game.getInitialArmy());
		game.addHumanPlayer("1", "blue");
		game.setInitialArmies();
		assertEquals(0,game.getInitialArmy());
		game.addHumanPlayer("2", "blue");
		game.setInitialArmies();
		assertEquals(40,game.getInitialArmy());
		game.addHumanPlayer("3", "blue");
		game.setInitialArmies();
		assertEquals(35,game.getInitialArmy());
		game.addHumanPlayer("4", "blue");
		game.setInitialArmies();
		assertEquals(30,game.getInitialArmy());
		game.addHumanPlayer("5", "blue");
		game.setInitialArmies();
		assertEquals(25,game.getInitialArmy());
		game.addHumanPlayer("6", "blue");
		game.setInitialArmies();
		assertEquals(20,game.getInitialArmy());
		game.addHumanPlayer("7", "blue");
		game.setInitialArmies();
		assertEquals(20,game.getInitialArmy());
	}
	
	@Test
	public void test1() {
		RiskGame game=new RiskGame();
		Map map=new Map();
		game.addHumanPlayer("1", "blue");
		game.addExpertAI("2", "green");
		game.addIntermediateAI("3", "purple");
		game.addSimpleAI("4", "orange");
		game.getPlayerList().getPlayer(0).setAttackArmies(3);
		game.getPlayerList().getPlayer(0).setAttackArmies(-2);
		assertTrue(game.getPlayerList().getPlayer(0).getAttackArmies()==3);
		game.getPlayerList().getPlayer(0).setAttackDiceAmount(3);
		assertTrue(game.getPlayerList().getPlayer(0).getAttackDiceAmount()==3);
		game.getPlayerList().getPlayer(0).setDefendDiceAmount(2);;
		assertTrue(game.getPlayerList().getPlayer(0).getDefendDiceAmount()==2);
		Country	country=map.getAfrica().getCountry("East Africa");
		
		Country	country2=map.getAfrica().getCountry("Egypt");
		
		game.getPlayerList().getPlayer(0).addCountry(country);
		game.getPlayerList().getPlayer(0).getNeighbourAndOwnedCountries(country);
		game.getPlayerList().getPlayer(0).getNeighbourAndNotOwnedCountries(country);
		assertTrue(game.getPlayerList().getPlayer(0).hasThisCountry(country.getName()));
		assertFalse(game.getPlayerList().getPlayer(0).hasThisCountry(country2.getName()));
		game.getPlayerList().getPlayer(0).addCountry(country2);
		Card card= new Card(CardType.Infantry, "Alaska");
		game.getPlayerList().getPlayer(0).addToHand(card);
		assertTrue(game.getPlayerList().getPlayer(0).handSize()==1);
		assertTrue(game.getPlayerList().getPlayer(0).getHand().size()==1);
		assertFalse(game.getPlayerList().getPlayer(0).isTurnInable());
		Card card1=new Card(CardType.Calvary, "Northwest Territory");
		Card card2=new Card(CardType.Artillery, "Greenland");
		Card card3 =new Card(CardType.Infantry, "Alberta");
		game.getPlayerList().getPlayer(0).addToHand(card1);
		game.getPlayerList().getPlayer(0).addToHand(card2);
		game.getPlayerList().getPlayer(0).addToHand(card3);
		assertTrue(game.getPlayerList().getPlayer(0).isTurnInable());
		game.getPlayerList().getPlayer(0).handToString();
		game.getPlayerList().getPlayer(0).removeCountry(country);
		assertTrue(game.getPlayerList().getPlayer(0).getNumberOfTerritories()==1);
		assertTrue(game.getPlayerList().getPlayer(0).getNumberOfContinents()==0);
		game.getPlayerList().getPlayer(0).setThisTurnNewArmies(10);
		game.getPlayerList().getPlayer(0).minusThisTurnNewArmies(1);
		assertTrue(game.getPlayerList().getPlayer(0).getThisTurnNewArmies()==9);
		game.getPlayerList().getPlayer(0).setTotalArmies(24);
		assertTrue(game.getPlayerList().getPlayer(0).getTotalArmies()==24);
		game.getPlayerList().getPlayer(0).addTotalArmies(2);
		game.getPlayerList().getPlayer(0).minusTotalArmies(2);
		assertTrue(game.getPlayerList().getPlayer(0).getTotalArmies()==24);
		game.getPlayerList().getPlayer(0).removeCountry(country2);
	}
	
	@Test
	public void testSetGetArmyThisTurnAndAddCountrytoPlayer() {
		RiskGame game=new RiskGame();
		Map map=new Map();
		game.setStage("Deploy Army");
		game.addHumanPlayer("1", "blue");
		game.addExpertAI("2", "green");
		game.addIntermediateAI("3", "purple");
		game.addSimpleAI("4", "orange");
		
		PlayerList players=game.getPlayerList();
		game.setArmyThisTurn(20);
		Player player1=players.getPlayer(0);
		
		assertEquals(20,player1.getThisTurnNewArmies());
		Country egp=map.getAfrica().getCountry("Egypt");
		game.addCountryToPlayer(player1,egp);
		assertTrue(player1.hasThisCountry("Egypt"));
		assertEquals(player1.getName(),egp.getOwner());
	}
	
	@Test
	public void testDeploy() {
		RiskGame game=new RiskGame();
		Map map=new Map();
		
		game.addHumanPlayer("1", "blue");
		game.addHumanPlayer("2", "green");
		game.addHumanPlayer("3", "purple");
		game.addHumanPlayer("4", "orange");
		game.addHumanPlayer("5", "orange");
		game.addHumanPlayer("6", "orange");
		game.setStage("Deploy Army");
		PlayerList players=game.getPlayerList();
		Player player2=players.getPlayer(1);
		ArrayList<Card> threeCards=new ArrayList<Card>();
		threeCards.add(game.assignNewCards());
		threeCards.add(game.assignNewCards());
		threeCards.add(game.assignNewCards());
		game.humanHarvestArmise(player2,threeCards);
		
		Continent afr=map.getAfrica();
		for (int i=0;i<afr.getNumberOfCountry();i++){
			players.getPlayer(0).addCountry(afr.getCountryInt(i));
		}
		
		Continent asia=map.getAsia();
		for (int i=0;i<asia.getNumberOfCountry();i++){
			players.getPlayer(1).addCountry(asia.getCountryInt(i));
		}
		
		Continent nam=map.getNorthAmerica();
		for (int i=0;i<nam.getNumberOfCountry();i++){
			players.getPlayer(2).addCountry(nam.getCountryInt(i));
		}
		
		Continent sam=map.getSouthAmerica();
		for (int i=0;i<sam.getNumberOfCountry();i++){
			players.getPlayer(3).addCountry(sam.getCountryInt(i));
		}
		
		Continent eur=map.getEurope();
		for (int i=0;i<eur.getNumberOfCountry();i++){
			players.getPlayer(4).addCountry(eur.getCountryInt(i));
		}
		
		Continent aus=map.getAustralia();
		for (int i=0;i< aus.getNumberOfCountry();i++){
			players.getPlayer(5).addCountry( aus.getCountryInt(i));
		}
		
		game.setArmyThisTurn(20);
		Player player1=players.getPlayer(0);
		
		Continent africa=map.getAfrica();
		
		for(int i=0;i<africa.getNumberOfCountry();i++){
			player2.addCountry(africa.getCountryInt(i));
		}
		
		player2.setThisTurnNewArmies(5);
		
	
	}
	
	
	
	
}
