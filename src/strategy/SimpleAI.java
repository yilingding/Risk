package strategy;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.Card;
import model.ComputerPlayer;
import model.Continent;
import model.Country;
import model.Map;
import model.Player;
import model.RiskGame;

public class SimpleAI implements AI , Serializable{

	@Override
	public Country placeInitialArmy(Map map, Player player) {
		// Randomly chooses an unoccupied country on the map
		ArrayList<Country> unoccupied = map.getUnoccupiedCountries();
		Collections.shuffle(unoccupied,new Random());

		if(unoccupied.size()==0)
			return null;
        Country c = unoccupied.get(0);
		c.setOwner(player.getName());
		c.addArmies(1);

		player.addCountry(c);
		return c;
	}

	@Override
	public void fortifyInitialArmy(Map map, Player player) {
		// Randomly fortifies an friendly country
		ArrayList<Country> occupied = player.getCountries();
		Collections.shuffle(occupied,new Random());
		Country c = occupied.get(0);
		c.addArmies(1);


	}

	@Override
	public void addArmies(Map map, Player player, int armies) {
		// Adds one army to a random owned country
		ArrayList<Country> occupied = player.getCountries();

		// Repeats placing a unit on a random country util all allocated armies
		// have run out
		for (int i = 0; i < armies; i++) {
			Collections.shuffle(occupied,new Random());
			Country c = occupied.get(0);
			c.addArmies(1);
		}

	}

	@Override
	public Country getAttackingCountry(Map map, Player player) {
		// Randomly chooses a country to attack from
		ArrayList<Country> occupied = player.getCountries();
		Collections.shuffle(occupied,new Random());
		Country c = null;

		// This makes sure the selected country has enough armies to attack
		
			// This makes sure the selected country has enough armies to fortify
		    for (Country iter:occupied){
		    	if (iter.getArmies()>=2 && iter.getEnemyNeighbors().size()>0){
		    	  c=iter;
		    	 break;
		    	}    	
		    }	
		return c;
	}
	
	public Country getCountryToAttack(Map map, Player player, Country attacking) {
       if (attacking==null)
    	   return null;
		ArrayList<Country> neighbors = attacking.getNeighbour();
		ArrayList<Country> canAttack = attacking.getEnemyNeighbors();


		// Attacks a random enemy neighbor
		if (canAttack.size() > 0) {
			Collections.shuffle(canAttack,new Random());
			return canAttack.get(0);
			// Attack the chosen Country!
		} else {
			return null;
		}

	}
	

	@Override
	public Country fortifyFrom(Map map, Player player) {
		ArrayList<Country> occupied = player.getCountries();
		Collections.shuffle(occupied,new Random());
		Country c = occupied.get(0);
        boolean wantFortify=false;
		// This makes sure the selected country has enough armies to fortify
	    for (Country iter:occupied){
	    	if (iter.getArmies()>=2){
	    	   wantFortify=true;
	    		break;
	    	}
	    }
	    if (wantFortify==true){
		while (c.getArmies() < 2) {
			Collections.shuffle(occupied,new Random());
			c = occupied.get(0);
		  }
	    }
	    else
		    c=null;
		return c;
	}
	
	public Country fortifyTo(Map map, Player player, Country fortify) {
		if (fortify==null) 
			return null;
		ArrayList<Country> neighbors = fortify.getNeighbour();
		ArrayList<Country> canFortify = new ArrayList<>();
		
		for(Country aCountry : neighbors) {
			if(aCountry.getOwner().equals(player.getName()))
				canFortify.add(aCountry);
		}
		
		if(canFortify.size() > 0) {
			Collections.shuffle(canFortify,new Random());
			Country fort = canFortify.get(0);
			return fort;
		} else {
			return null;
		}

	}

	@Override
	public ArrayList<Card> turnIn(Player player) {
		ArrayList<Card> hand =  player.getHand();
		if (hand.size()>2){
		 return	player.autoTurnIn(player.getHand());
		}
		return null;
	}


	@Override
	public void attackOthers(RiskGame theGame, Map map, ComputerPlayer player) {
	    player.setAttackDiceAmount(1);
	    player.setDefendDiceAmount(1);
		int restArmies=2;
		int count=0;
		while  (player.AttackOnce(theGame, map, player)){
			count++;
			if (count>10) {
	//			System.out.println("explode!");
				break;
			}
		}
	}
}