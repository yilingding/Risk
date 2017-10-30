package model;

import java.awt.Color;
import java.awt.Graphics;

// Use Java's Point class to store two ints: an x and a y

// @author mercer
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable{


	protected String color;
	protected String name;
	protected int totalArmies;
	protected int attackDiceAmount;
	protected int defendDiceAmount;

	protected ArrayList<Continent> occupiedContinents;
	protected ArrayList<Country> occupiedCountries;
	protected ArrayList<Card> hand;
	protected int thisTurnNewArmies;
	protected boolean autoRemoveFlag;
	protected int attackArmies;
	private int diceNumer;
	public boolean diceAlready;
  	
	public Player(String name, String color){
		attackDiceAmount=3;
		diceAlready=false;
		defendDiceAmount=2;
		occupiedContinents=new ArrayList<Continent>();
		occupiedCountries = new ArrayList<Country>();
		hand=new ArrayList<Card>();
		attackArmies=0;

		this.color=color;
		this.name=name;
		totalArmies=0;
		thisTurnNewArmies=0;
		/**
		 *  Be careful if a country does not have enough amries for the dices
		 *  setDices(num-1) and setDices(num+1)
		 * 
		 */		
	}
	public int getTheDiceNumber(){
		return this.diceNumer;
	}
	public void setTheDiceNumber(int i, int k){
		if(k==0){
		this.diceNumer=i;
		diceAlready=true;
		}else if(!diceAlready){
			this.diceNumer=i;
		}
	}
	
	public String toStringInHand(){
		String str="";
		if(hand.size()==0){
			return "0 card";
		}
		for(int i=0;i<hand.size();i++){
			str=str+hand.get(i)+ "   ";
		}
		return str;
	}
	 public String handToStringInPlayer(){
		 return "The player"+this.name+" has card:"+this.toStringInHand();
	 }
	
	public void setAttackArmies(int num){
	  if (num>0)
		attackArmies=num;
	  else {
		  System.out.println("invalid attack army number");
	  }
	}
	
	public int getAttackArmies(){
		return attackArmies;
	}
	
	public void setAttackDiceAmount(int num){
		attackDiceAmount=num;
	}
	public void setDefendDiceAmount(int num){
		defendDiceAmount=num;
	}
	
	public int getAttackDiceAmount(){
		return attackDiceAmount;
	}
	public int getDefendDiceAmount(){
		return defendDiceAmount;
	}

	
	public boolean hasThisCountry(String name){
		for (Country i:occupiedCountries){
			if (i.getName().equals(name))
				return true;
		}
		return false;
	}
	public String getName(){
		return name;
	}

	public String getColor(){
		return color;
	}
	public int getTotalArmies(){
		return totalArmies;
	}
	
	public void addToHand(Card card){
		hand.add(card);		
	}
	

   
	
	public int handSize(){
		return hand.size();
	}
	
	public boolean isTurnInable(){
		ArrayList<Card> copyHand=new ArrayList<Card>();
		for(int i=0;i<hand.size();i++){
			copyHand.add(hand.get(i));
		}
		if (autoTurnIn(copyHand)==null)
			return false;
		else
			return true;		
	}
	
	//return null means nothing to be auto removed.

	public ArrayList<Card> autoTurnIn(ArrayList<Card> hand){

    	 
		ArrayList<Card> removedCountries=new ArrayList<Card>();  //this is the list of the name of removedCountries
    	 
    	if (hand.size()>=3) {

        ArrayList<Card> infantryCards=new ArrayList<Card>();
        ArrayList<Card> calvaryCards=new ArrayList<Card>();  
        ArrayList<Card> artilleryCards=new ArrayList<Card>();
        ArrayList<Card> wildCards=new ArrayList<Card>();
        
        
        for (int i=0;i<hand.size();i++) {
        if (hand.get(i).getType()==CardType.Infantry)
        	infantryCards.add(hand.get(i));
        else if (hand.get(i).getType()==CardType.Calvary)
        	calvaryCards.add(hand.get(i));
        else if (hand.get(i).getType()==CardType.Artillery)
        	artilleryCards.add(hand.get(i));
        else
        	wildCards.add(hand.get(i));
        }
        
        if (infantryCards.size()>=1 && calvaryCards.size()>=1 && artilleryCards.size()>=1 ) {
        	removedCountries.add(infantryCards.get(0));
        	removedCountries.add(calvaryCards.get(0));
        	removedCountries.add(artilleryCards.get(0));    	       	
        	hand.remove(infantryCards.get(0));
        	hand.remove(calvaryCards.get(0));
        	hand.remove(artilleryCards.get(0));
        	return removedCountries;
        }
        
        if (infantryCards.size()>=3) {
        	for (int i=0;i<3;i++){
        		removedCountries.add(infantryCards.get(i));
        		hand.remove(infantryCards.get(i));
        	}      	
        	return removedCountries;
        }
        
        if (calvaryCards.size()>=3) {
        	for (int i=0;i<3;i++){
        		removedCountries.add(calvaryCards.get(i));
        		hand.remove(calvaryCards.get(i));
        	}      	
        	return removedCountries;
        }
        
        if (artilleryCards.size()>=3) {
        	for (int i=0;i<3;i++){
        		removedCountries.add(artilleryCards.get(i));
        		hand.remove(artilleryCards.get(i));
        	}      	
        	return removedCountries;
        }
        
        if (wildCards.size()<1)
        	return null;
        else if (wildCards.size()==2 && hand.size()==3){
        	for (int i=0;i<3;i++)
        		removedCountries.add(hand.get(i));
         	for (int i=0;i<3;i++)
        		hand.remove(0);      	
        }
        else     // one or two wild cards case
        {
        	for (int i=0;i<hand.size();i++)
        		if (hand.get(i).getType()==CardType.Wild)
        		{
        			hand.remove(i);
        			break;
        		}
        	for (int i=0;i<hand.size();i++){
        		if (hand.get(i).getType()!=CardType.Wild && removedCountries.size()<2){
        			removedCountries.add(hand.get(i));
        			hand.remove(i);
        			i--;  //removed position has been filled        			
        		}
        	}
        }
		
  	 }	
    	 return null;
	}
	
	public ArrayList<Country> getNeighbourAndNotOwnedCountries(Country country){
		ArrayList<Country> result=new ArrayList<Country>();
		ArrayList<Country> countries=this.getCountries();
		ArrayList<Country> neigh=country.getNeighbour();
		for(int i=0;i< neigh.size();i++){
			if((!countries.contains(neigh.get(i)))&&(!result.contains(neigh.get(i)))){
				result.add(neigh.get(i));
			}
		}
		if(result.size()==0){
			return null;
		}else{
			return result;
		}
	}
	public ArrayList<Country> getNeighbourAndOwnedCountries(Country country){
		ArrayList<Country> result=new ArrayList<Country>();
		ArrayList<Country> countries=this.getCountries();
		ArrayList<Country> neigh=country.getNeighbour();
		for(int i=0;i< neigh.size();i++){
			if((countries.contains(neigh.get(i)))&&(!result.contains(neigh.get(i)))){
				result.add(neigh.get(i));
			}
		}
		if(result.size()==0){
			return null;
		}else{
			return result;
		}
	}
	
	/**
	 * threeCards should be null when the player is a AI, otherwise is handpicked three cards that you want to turn in 
	 *  if the return value is null, the removal failed, otherwise it is the removed cards' names 
	 * @param threeCards
	 */
	
	public int addTotalArmies(int amount){
		totalArmies+=amount;
		return totalArmies;
	}
	
	public int minusTotalArmies(int amount){
		totalArmies-=amount;
		return totalArmies;
	}
	
	public int setTotalArmies(int amount){
		totalArmies=amount;
		return totalArmies;
	}
	


	public void setThisTurnNewArmies(int num){
		thisTurnNewArmies=num;
	}
	public int getThisTurnNewArmies(){
		return thisTurnNewArmies;
	}
	public void minusThisTurnNewArmies(int i){
		thisTurnNewArmies=thisTurnNewArmies-i; 
	}
	
	public int getNumberOfTerritories(){
		return occupiedCountries.size();
	}
	public int getNumberOfContinents(){
		return occupiedContinents.size();
	}
	
	public void addCountry(Country c) {
		occupiedCountries.add(c);
	}

	/**
	 * this also potentially removes continent
	 * @param c
	 */
	public void removeCountry(Country c){
		occupiedCountries.remove(c);
		for (Continent con:occupiedContinents){
			if (con.containCountry(c)) {
				occupiedContinents.remove(con);
				break;
		 }
		}
		
	}
	

	
	public ArrayList<Country> getCountries() {
		return occupiedCountries;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public String handToString(){
		String ret="";
		for (int i=0;i<hand.size();i++){
			ret+=(hand.get(i).getCountry()+" "+hand.get(i).getType()+", ");
		}
		
		return ret;
	}
	
	public void setColor(String string) {
		this.color=string;		
	}
	

}