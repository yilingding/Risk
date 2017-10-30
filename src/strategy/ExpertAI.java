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

public class ExpertAI implements AI, Serializable {

	@Override
	public Country placeInitialArmy(Map map, Player player) {
		// Tries to occupy North America first and then its border countries
		// before moving on
		Continent con = map.getNorthAmerica();
		ArrayList<Country> unoccupied = con.getUnoccupiedCountries();
		Country c = null;
		if (unoccupied.size() > 0) {
			
			Collections.shuffle(unoccupied);
			c = unoccupied.get(0);
			c.setOwner(player.getName());
			c.addArmies(1);
			player.addCountry(c);
		
		} else {
			c = map.getCountry("Venezuela");
			if (c.getOwner() == null ) {
				c.setOwner(player.getName());
				c.addArmies(1);
				player.addCountry(c);
	
			} else {
				c = map.getCountry("Iceland");
				if (c.getOwner() == null) {
					c.setOwner(player.getName());
					c.addArmies(1);
					player.addCountry(c);
			
				} else {
					c = map.getCountry("Kamchatka");
					if (c.getOwner() == null) {
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
									con = map.getAsia();
									unoccupied = con.getUnoccupiedCountries();
									if (unoccupied.size() > 0) {
										Collections.shuffle(unoccupied);
										c = unoccupied.get(0);
										c.setOwner(player.getName());
										c.addArmies(1);
										player.addCountry(c);
								
									} else {
										con = map.getAustralia();
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
				}
			}
		}
		return c;
	}

	@Override
	public void fortifyInitialArmy(Map map, Player player) {
		// Strengthens its armies in North America only
		ArrayList<Country> allCountries = map.getNorthAmerica().getAllCountries();
		ArrayList<Country> occupied = new ArrayList<>();
		for (Country c : allCountries) {
			if (c.getOwner().equals(player.getName()))
				occupied.add(c);
		}
        if (occupied.size()>0){
        	Country c = occupied.get(0);
		for (int i = 0; i < occupied.size(); i++) {
			if (c.getArmies() > occupied.get(i).getArmies())
				c = occupied.get(i);
		}
		c.addArmies(1);
        }
        else
        {
        	Country c = player.getCountries().get(0);
        	for (Country iter:player.getCountries()){
        		if (c.getArmies() > iter.getArmies())
    				c = iter;
        	}  
        c.addArmies(1);
        }

	}

	@Override
	public void addArmies(Map map, Player player, int armies) {
		ArrayList<Country> allCountries = map.getNorthAmerica().getAllCountries();
		ArrayList<Country> unoccupied = new ArrayList<>();
		ArrayList<Country> occupiedNA = new ArrayList<>();
   
		// Checks to see if it owns all of North America
		for (Country aCountry : allCountries) {
			if (aCountry.getOwner().equals(player.getName())) {
				occupiedNA.add(aCountry);
			}
			else
				unoccupied.add(aCountry);
		}
		
	
		if (unoccupied.size() > 0 && occupiedNA.size()>0) {
			Country c = occupiedNA.get(0);
			for (int i = 0; i < occupiedNA.size(); i++) {
				if (occupiedNA.get(i).enemyNeighbors() > c.enemyNeighbors())
					c = occupiedNA.get(i);
			}
			c.addArmies(armies);// Fortify the country with all of the given armies
		} else {
			ArrayList<Country> occupied = new ArrayList<Country>(player.getCountries());
			for (Country aCountry : occupiedNA) {
				occupied.remove(aCountry);
			}
            if (occupied.size()>0) {
			Country c = occupied.get(0);
			for (int i = 0; i < occupied.size(); i++) {
				if (occupied.get(i).enemyNeighbors() > c.enemyNeighbors())
					c = occupied.get(i);
			}
			c.addArmies(armies);// fortify selected country
            }
            else
            {
              Country cc=player.getCountries().get(0);
               for (Country iter:player.getCountries()){
            	   if (iter.getEnemyNeighbors().size()>cc.getEnemyNeighbors().size()){
            		   cc=iter;
            	   }
               }
             	cc.addArmies(armies);
            }
		}

	}

	@Override
	public Country getAttackingCountry(Map map, Player player) {
		ArrayList<Country> allCountries = map.getNorthAmerica().getAllCountries();
		ArrayList<Country> unoccupiedNA = new ArrayList<>();
		ArrayList<Country> occupiedNA = new ArrayList<>();

		for (Country aCountry : allCountries) {
			if (aCountry.getOwner().equals(player.getName()))
				occupiedNA.add(aCountry);
			else
				unoccupiedNA.add(aCountry);
		}

		if (unoccupiedNA.size() > 0 && occupiedNA.size()>0) {
			Country c = occupiedNA.get(0);
			for (int i = 0; i < occupiedNA.size(); i++) {
				if (occupiedNA.get(i).enemyNeighbors() > c.enemyNeighbors() && occupiedNA.get(i).getArmies()>3 )
					c = occupiedNA.get(i);
			}
			if (c.getArmies()>3)
			return c;
			else
		    return null;
		} else {
//			System.out.println("occupiedNA    "+occupiedNA);
//			System.out.println("occupiedALL    "+player.getCountries());
			ArrayList<Country> occupied = new ArrayList<Country>(player.getCountries());
			for (Country aCountry : occupiedNA) {
				occupied.remove(aCountry);
			}
            if (occupied.size()>0){
			Country c = occupied.get(0);
			     for (int i = 0; i < occupied.size(); i++) {
				if (occupied.get(i).enemyNeighbors() > c.enemyNeighbors()&& occupied.get(i).getArmies()>3 )
					c = occupied.get(i);
			  }
		      	if (c.getArmies()>3)
				return c;
				else
			    return null;
            }
            else {
            	Country c;
            	Random rand = new Random(); 
            	int value = rand.nextInt(3)+1; 
            	if (value==1){
            		c=map.getNorthAmerica().getCountry("Alaska");
            	}
            	else if (value==2){
            		c=map.getNorthAmerica().getCountry("Central America");
            	}
            	else
            		c=map.getNorthAmerica().getCountry("GreenLand");        
            	return c;
            }
            	
		}

	}

	public Country getCountryToAttack(Map map, Player player, Country attacking) {
        if (attacking==null)
        	return null;
//      System.out.println(attacking.getName());
		ArrayList<Country> neighbors = attacking.getNeighbour();
//		System.out.println(neighbors);
		ArrayList<Country> canAttack = attacking.getEnemyNeighbors();
//		System.out.println(canAttack);
        if (canAttack.size()>0){
		Country weak = canAttack.get(0);
		for (int i = 0; i < canAttack.size(); i++) {
			if (weak.getArmies() > canAttack.get(i).getArmies())
				weak = canAttack.get(i);
		} 
		return weak;
        }
        else
        	return null;
	}

	@Override
	public Country fortifyFrom(Map map, Player player) {
		ArrayList<Country> occupied = player.getCountries();
		Country fortifyFrom = null;

		for (Country aCountry : occupied) {
			if (aCountry.enemyNeighbors() == 0 && aCountry.getArmies() > 1) {
				fortifyFrom = aCountry;
				break;
			}
		}
		
		return fortifyFrom;
	}
	
	public Country fortifyTo(Map map, Player player, Country fortify) {

		if (fortify != null) {
			ArrayList<Country> friendly = fortify.getNeighbour();
			Country weak = friendly.get(0);
			for (Country aCountry : friendly) {
				if (aCountry.getArmies() < weak.getArmies())
					weak = aCountry;
			}
			return weak;
		} else {
			return null; // Do not fortify if not possible
		}
		
	}

	@Override
	public ArrayList<Card> turnIn(Player player) {
		ArrayList<Card> hand =  player.getHand();
		if (hand.size()>4){
		 return	player.autoTurnIn(player.getHand());
		}
		return null;
	}

	@Override
	public void attackOthers(RiskGame theGame, Map map, ComputerPlayer player) {
	    player.setAttackDiceAmount(3);
	    player.setDefendDiceAmount(2);
		int restArmies=2;
		int count=0;
		while  (player.AttackOnce(theGame, map, player)){
			count++;
			if (count>10) {
//				System.out.println("explode!Expert");
			break;	
			}
		}
	}

}