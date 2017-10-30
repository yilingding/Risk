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

public class IntermediateAI implements AI , Serializable{

	@Override
	public Country placeInitialArmy(Map map, Player player) {
		// Tries to claim entire continents starting with Australia
		Continent con = map.getAustralia();
		ArrayList<Country> unoccupied = con.getUnoccupiedCountries();
		Country c = null;
		if (unoccupied.size() > 0) {
			Collections.shuffle(unoccupied);
			c = unoccupied.get(0);
			c.setOwner(player.getName());
			c.addArmies(1);

			player.addCountry(c);
		} else {
			con = map.getAsia();
			unoccupied = con.getUnoccupiedCountries();
			if (unoccupied.size() > 0) {
				Collections.shuffle(unoccupied);
				c = unoccupied.get(0);
				c.setOwner(player.getName());
				c.addArmies(1);
	
				player.addCountry(c);
			} else {
				con = map.getEurope();
				unoccupied = con.getUnoccupiedCountries();
				if (unoccupied.size() > 0) {
					Collections.shuffle(unoccupied);
					c = unoccupied.get(0);
					c.setOwner(player.getName());
					c.addArmies(1);
			
					player.addCountry(c);
				} else {
					con = map.getAfrica();
					unoccupied = con.getUnoccupiedCountries();
					if (unoccupied.size() > 0) {
						Collections.shuffle(unoccupied);
						c = unoccupied.get(0);
						c.setOwner(player.getName());
						c.addArmies(1);
				
						player.addCountry(c);
					} else {
						con = map.getSouthAmerica();
						unoccupied = con.getUnoccupiedCountries();
						if (unoccupied.size() > 0) {
							Collections.shuffle(unoccupied);
							c = unoccupied.get(0);
							c.setOwner(player.getName());
							c.addArmies(1);
					
							player.addCountry(c);
						} else {
							con = map.getNorthAmerica();
							unoccupied = con.getUnoccupiedCountries();
							if (unoccupied.size() > 0) {
								Collections.shuffle(unoccupied);
								c = unoccupied.get(0);
								c.setOwner(player.getName());
								c.addArmies(1);
						
								player.addCountry(c);
							}
						}
					}
				}
			}
		}
		return c;
	}

	@Override
	public void fortifyInitialArmy(Map map, Player player) {
		// Forifies the weakest owned country
		ArrayList<Country> occupied = player.getCountries();
		Country c = occupied.get(0);
		for (int i = 0; i < occupied.size(); i++) {
			if (c.getArmies() > occupied.get(i).getArmies())
				c = occupied.get(i);
		}
		c.addArmies(1);
	

	}

	@Override
	public void addArmies(Map map, Player player, int armies) {

		// Adds a unit to the weakest owned country
		ArrayList<Country> occupied = player.getCountries();
		for (int i = 0; i < armies; i++) {
			Country c = occupied.get(0);
			for (int j = 0; j < occupied.size(); j++) {
				if (c.getArmies() > occupied.get(j).getArmies())
					c = occupied.get(j);
			}
			c.addArmies(1);
		}

	}

	@Override
	public Country getAttackingCountry(Map map, Player player) {
		// Finds the strongest Country and then attacks its weakest neighbor
		ArrayList<Country> occupied = player.getCountries();
		Collections.shuffle(occupied);
		Country strong = occupied.get(0);
        Random random = new Random();
        int rng=random.nextInt(3);
        if (rng!=0)
		for (int i = 0; i < occupied.size(); i++) {
			if (strong.getArmies() < occupied.get(i).getArmies() && strong.getEnemyNeighbors().size()>0)
				strong = occupied.get(i);
		}
		if (strong.getEnemyNeighbors().size()>0 && strong.getArmies()>1)
		return strong;
		else
			return null;
	}
	
	public Country getCountryToAttack(Map map, Player player, Country attacking) {


       if (attacking==null)
    	   return null;
       else {
   		ArrayList<Country> canAttack = attacking.getEnemyNeighbors();
		if (canAttack.size() > 0) {
			Country weak = canAttack.get(0);
			for (int i = 0; i < canAttack.size(); i++) {
				if (weak.getArmies() > canAttack.get(i).getArmies())
					weak = canAttack.get(i);
			}
			return weak;
		} else {
			return null; // Skip attack if not possible
		}
       }
	}

	@Override
	public Country fortifyFrom(Map map, Player player) {
		 Collections.shuffle(player.getCountries());
		ArrayList<Country> occupied = player.getCountries();
		Country fortifyFrom = null;

		for (Country aCountry : occupied) {
			if (aCountry.enemyNeighbors() <2 && aCountry.getArmies() > 1) {
				fortifyFrom = aCountry;
				break;
			}
		}
		
		return fortifyFrom;
		
	}
	
	public Country fortifyTo(Map map, Player player, Country fortify) {
	    if (fortify==null)
	    	return null;
	    Collections.shuffle(fortify.getNeighbour());
		ArrayList<Country> neighbors = fortify.getNeighbour();
		ArrayList<Country> friendly = new ArrayList<>();
	
		for(Country aCountry : neighbors) {
			if(aCountry.getOwner().equals(player.getName()))
				friendly.add(aCountry);
		}
		
		if(friendly.size() > 0) {
			Country weak = friendly.get(0);
			for (int i = 0; i < friendly.size(); i++) {
				if (weak.getArmies() > friendly.get(i).getArmies() && weak.getEnemyNeighbors().size()>1)
					weak = friendly.get(i);
			}
			if (weak.getEnemyNeighbors().size()>0)
			return weak;
			else {
				Random rng=new Random();
				int a=rng.nextInt(4);
				if (a==0)
					return weak;
				else
				return null;
			}
		
		} else {
			return null;
		}

	}
	@Override
	public ArrayList<Card> turnIn(Player player) {
		ArrayList<Card> hand =  player.getHand();
		if (hand.size()>3){
		 return	player.autoTurnIn(player.getHand());
		}
		return null;
	}
	@Override
	public void attackOthers(RiskGame theGame, Map map, ComputerPlayer player) {
	    player.setAttackDiceAmount(2);
	    player.setDefendDiceAmount(1);
		int restArmies=2;
		int count=0;
		while  (player.AttackOnce(theGame, map, player)){
			count++;
			if (count>10) {
	//			System.out.println("explode!Intermediate");
				break;
			}
		}
	}
	}
