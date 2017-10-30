package strategy;

import java.util.ArrayList;

import model.Card;
import model.ComputerPlayer;
import model.Country;
import model.Map;
import model.Player;
import model.RiskGame;

public interface AI {
	
	public Country placeInitialArmy(Map map, Player player);
	
	public void fortifyInitialArmy(Map map, Player player);
	
	public void addArmies(Map map, Player player, int armies);
	
	public Country getAttackingCountry(Map map, Player player);
	
	public Country getCountryToAttack(Map map, Player player, Country attacking);
	
	public Country fortifyFrom(Map map, Player player);
	
	public Country fortifyTo(Map map, Player player, Country fortify);
	
	public ArrayList<Card> turnIn(Player player);  //return null if there are no matched three cards to return or AI want to 
	//hold the cards in his hand
	public void attackOthers(RiskGame theGame, Map map, ComputerPlayer player);

}
