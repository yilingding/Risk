package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import strategy.AI;
import strategy.SimpleAI;

public class ComputerPlayer extends Player implements Serializable{
	
	AI myAI;

	public ComputerPlayer(String name, String color) {
		super(name, color);
		
		myAI = new SimpleAI();
	}
	
	public void setAI(AI newAI) {
		myAI = newAI;
	}

	public Country placeInitialArmy(Map map) {
		return myAI.placeInitialArmy(map, this);
	}
	
	public void fortifyInitialArmy(Map map) {
		myAI.fortifyInitialArmy(map, this);
	}
	
	public void deployingArmies(Map map, int armies) {
		myAI.addArmies(map, this, armies);
	}
		
	public Country getAttackingCountry(Map map, Player player) {
		return myAI.getAttackingCountry(map,player);
	}
	
	public Country getCountryToAttack(Map map, Player player, Country attacking) {
		return myAI.getCountryToAttack(map, player, attacking);
	}
	
	public Country fortifyFrom(Map map, Player player) {
		return myAI.fortifyFrom(map, player);
	}
	
	public Country fortifyTo(Map map, Player player, Country fortify) {
		return myAI.fortifyTo(map, player, fortify);
	}
	
	public ArrayList<Card> turnInRiskCards() {
		// TODO Auto-generated method stub
		return myAI.turnIn(this);
	}
	
	public ArrayList<String> turnInRiskCardsByString(){
		ArrayList<Card> list=myAI.turnIn(this);
		ArrayList<String> strList=new ArrayList<String>();
		
		for (Card i:list)
			strList.add(i.getCountry());
		
		return strList;
		
	}
	
	public AI getStrategy(){
		return myAI;
	}
	public void AiAttack(RiskGame theGame,Map map){
		myAI.attackOthers(theGame,map,this);
	}
	
	public boolean AttackOnce(RiskGame theGame,Map map, ComputerPlayer player){
    Country attacking = player.getAttackingCountry(map, player);
    if (attacking==null)   //means  no available country to be used to attack other country
    {  
    //	System.out.println("attacking country is null   "+player.getName()+"  ");
    	return false;
    }
    Country defending = player.getCountryToAttack(map, player, attacking);
    if (defending==null){
  //  	System.out.println("defending country is null");
    	return false;
    }
   
    String ownerName = defending.getOwner();
    if (attacking!=null && defending!=null)  {
    	
    	if (!player.hasThisCountry(attacking.getName())){
     		System.out.println("error: player cannot attack with countries he does not own"); 
    		return false;
    	}
    	if (player.hasThisCountry(defending.getName())){
     		System.out.println("error: player cannot attack his own country"); 
    		return false;
    	}
    	theGame.attackingHelper(player, attacking, defending, player.getAttackDiceAmount());
    
    	if (defending.getOwner().equals(ownerName)) //whenever successfully invaded a country 
    	{  
//    		System.out.println("\nunsuccesfully invading");
//    		System.out.println(attacking.getOwner()+"   "+attacking.getOwner()+"   "+attacking.getName()+"   "+attacking.getArmies());
//    		System.out.println(defending.getOwner()+"   "+defending.getOwner()+"   "+defending.getName()+"   "+defending.getArmies());

    		return true;
    	}
       }
       return false;
	}
	
}
	
