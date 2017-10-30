package model;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import strategy.ExpertAI;
import strategy.IntermediateAI;

import strategy.SimpleAI;
public class RiskGame extends Observable implements Serializable{

	private  PlayerList playerList;
	private Map map;
	private String stage;
	private Deck deck;
	private int initialArmies;
	private int currentPlayer;
	public static final int totalCountriesInMap = 42;

	public RiskGame() {
		initializeGame();
		deck=new Deck();
		//initialArmies = 0;
	}

public void setCurrentPlayer(int i){
	this.currentPlayer=i;
}

public int returnCurrentPlayer(){
	return this.currentPlayer;
}
	private void initializeGame() {
		map = new Map();
		//playerList = PlayerList.PlayerListConstructor();
		playerList=new PlayerList();
	}
	

	public String getStage(){
		return stage;
	}
	
	public void setStage(String currStage){
		stage=currStage;
	}

	/**
	 * this function needs to be called in GUI
	 */
	public void addSimpleAI(String name, String color) {
		ComputerPlayer AI = new ComputerPlayer(name, color);
		AI.setAI(new SimpleAI());
		playerList.addPlayers(AI);
	}

	public void addIntermediateAI(String name, String color) {
		ComputerPlayer AI = new ComputerPlayer(name, color);
		AI.setAI(new IntermediateAI());
		playerList.addPlayers(AI);

	}

	public void addExpertAI(String name, String color) {
		ComputerPlayer AI = new ComputerPlayer(name, color);
		AI.setAI(new ExpertAI());
		playerList.addPlayers(AI);
	}

	/**
	 * this function needs to be called in GUI
	 */
	public void addHumanPlayer(String name, String color) {
		HumanPlayer human = new HumanPlayer(name, color);
		playerList.addPlayers(human);

	}

	/**
	 * this function needs to be called in GUI
	 */
	public void setInitialArmies() {
		Collections.shuffle(playerList.getPlayers());  //now the collection is shuffle for the playerList
		if (playerList.getPlayerSize() == 0) {
			System.out.println("haven't added players yet");
		} else if (playerList.getPlayerSize() == 1) {
			System.out.println("why do you want to play by yourself?");
		} else if (playerList.getPlayerSize() < 7 && playerList.getPlayerSize() > 1) {
			initialArmies = 50 - playerList.getPlayerSize() * 5;
			for (int i = 0; i < playerList.getPlayerSize(); i++)
				playerList.getPlayers().get(i).setTotalArmies(initialArmies);
		} else {
			System.out.println("Too many players(or negative players)"); // players'
																			// amount
																			// should
																			// be
																			// controled
																			// in
																			// GUI
		}
//		System.out.println("initialArmies is: "+initialArmies);
		return;
	}
	
	public int getInitialArmy(){
		return initialArmies;
	}

	public PlayerList getPlayerList() {
		return playerList;
	}

    
	public void setArmyThisTurn(int initialArmy) {
		for (int i=0;i<playerList.getPlayerSize();i++){
			if(getStage().equals("Claim Country")||getStage().equals("Place Army")){
			playerList.getPlayer(i).setThisTurnNewArmies(initialArmy-playerList.getPlayer(i).getCountries().size()-1);
			}
			else 
				playerList.getPlayer(i).setThisTurnNewArmies(initialArmy);
		}
	}


	
	public void addCountryToPlayer(Player player,Country country){
		country.setOwner(player.getName());
		country.setArmies(1);
		player.addCountry(country);
	}



	/**
	 * deploy player's newly-got armies at the begining of each turn;
	 * 
	 * should be only called by computer, you should handle the human logic part by calling humanHarvestArmise(Player player, ArrayList<Card> threeCards)
	 * addArmies() in GUI 
	 */

	public void deployArmies(Player player) {
	    	computerHarvestArmies(player);
	    	deployArmiesHelper(player);
	}
	private void deployArmiesHelper(Player player){
			ComputerPlayer computer = (ComputerPlayer) player;
			computer.deployingArmies(map, player.getThisTurnNewArmies());	
	}

	/**
	 * I think GUI should have a while loop to constantly construct the
	 * passedThreeCards when the three cards are clicked
	 */
	private ArrayList<Card> passedThreeCards;
	/**
	 * You need to set a ButtonListener or something for humanWantToTurnIn if
	 * the button is clicked, humanWantToTurnIn is set to false
	 */

	private boolean hasDuplicatesInThreeCards(ArrayList<Card> threeCards) {
		if (threeCards.get(0).equals(threeCards.get(1)) || threeCards.get(1).equals(threeCards.get(2))
				|| threeCards.get(0).equals(threeCards.get(2))) {
			return true;
		} else
			return false;
	}
	
	/*public boolean isValidToTurnIn(ArrayList<Card> threeCards){
		if (threeCards.get(0).getType().equals(threeCards.get(1).getType())&&
				threeCards.get(1).getType().equals(threeCards.get(2).getType())){
			return true;
		}else if((!threeCards.get(0).getType().equals(threeCards.get(1).getType()))&&
				(!threeCards.get(1).getType().equals(threeCards.get(2).getType()))&&
				(!threeCards.get(0).getType().equals(threeCards.get(1).getType()))){
			return true;
		}else if(threeCards.get(0).getType()==CardType.Wild){
			if(!threeCards.get(1).getType().equals(threeCards.get(2).getType())){
				return true;
			}
		}else if(threeCards.get(1).getType()==CardType.Wild){
			if(!threeCards.get(0).getType().equals(threeCards.get(2).getType())){
				return true;
			}
		}
		else if(threeCards.get(2).getType()==CardType.Wild){
			if(!threeCards.get(0).getType().equals(threeCards.get(1).getType())){
				return true;
			}
		}
		else{
			return false;
		}
		return false;
		
	}*/

	private int theXthSetCardsTurnedIn = 0;

	private int returnArmiesAccordingXthSet() {
		theXthSetCardsTurnedIn++;
		if (theXthSetCardsTurnedIn > 0 && theXthSetCardsTurnedIn < 6)
			return theXthSetCardsTurnedIn * 2 + 2;
		else
			return 15 + 5 * (theXthSetCardsTurnedIn - 6);
	}

	
	/**      You should implement these in GUI for calling humanHarvestArmise(Player player, ArrayList<Card> threeCards)     
	 *                   if (passedThreeCards == null || passedThreeCards.size() != 3
	 *							|| hasDuplicatesInThreeCards(passedThreeCards)
	 *							|| player.turnIn(passedThreeCards) == null) or not 
	 * 
	 * "player.turnIn(passedThreeCards) == null" means no matched three cards, nothing can be turned in 
	 * @param player
	 * @param threeCards
	 */
	public Card assignNewCards(){
		deck.shuffle();
		Card theOne=deck.draw();
		return theOne;
	}
	
	/**
	 * call this method before you call humanHarvestArmies()
	 * @param threeCards
	 * @return
	 */
	public boolean threeCardsIsTurnInable(ArrayList<Card> threeCards){            
	    	if (threeCards.size()!=3) {
	    		System.out.println("It is not three cards");	    		
	    		return false;
	    	}
	    	else if (hasDuplicatesInThreeCards(threeCards)){
	    		System.out.println("At least two cards is the same card");
	    		return false;
	    	}
	    	else  {
	        ArrayList<Card> infantryCards=new ArrayList<Card>();
	        ArrayList<Card> calvaryCards=new ArrayList<Card>();  
	        ArrayList<Card> artilleryCards=new ArrayList<Card>();
	        ArrayList<Card> wildCards=new ArrayList<Card>();
	        	        
	        for (int i=0;i<threeCards.size();i++) {
	        if (threeCards.get(i).getType()==CardType.Infantry)
	        	infantryCards.add(threeCards.get(i));
	        else if (threeCards.get(i).getType()==CardType.Calvary)
	        	calvaryCards.add(threeCards.get(i));
	        else if (threeCards.get(i).getType()==CardType.Artillery)
	        	artilleryCards.add(threeCards.get(i));
	        else
	        	wildCards.add(threeCards.get(i));
	        }
	        
	        if (infantryCards.size()==1 && calvaryCards.size()==1 && artilleryCards.size()==1 ) {
                  return true;
	        }
	        
	        if (infantryCards.size()==3 || calvaryCards.size()==3 || artilleryCards.size()==3 ) {
	             return true;
	        }        
	        if (wildCards.size()<1)
	        	return false;
	        else     	
	            return true;
	    	}
		
	}
	
	
	/**
	 *  call this before called the humanHarvestArmise(), pass the return value of "humanMustTurnInWhenHaveFiveCards()" to humanHarvestArmise(ArrayList<Card> threeCards)
	 *  if the return value is not null
	 * @param player
	 * @return
	 */
	public ArrayList<Card> humanMustTurnInWhenHaveFiveCards(Player player){
	 if (player.getHand().size()>4) {
		ArrayList<Card> threeCards=player.autoTurnIn(player.getHand());
		return threeCards;
	 }
	 else 
		 return null;
	}
	
	
	
	public void humanHarvestArmise(Player player, ArrayList<Card> threeCards){
		int newArmies = 0;
		int armiesForOccupiedTerritories = 0;
		armiesForOccupiedTerritories = player.getNumberOfTerritories() / 3;
		if (armiesForOccupiedTerritories < 3)
			armiesForOccupiedTerritories = 3;
		newArmies += armiesForOccupiedTerritories;

		newArmies += player.getNumberOfContinents() * 7;
		System.out.println("armies get from conatries"+newArmies);

			if (threeCards != null) {
				newArmies += returnArmiesAccordingXthSet(); // return armies
				int cardMatchedCountries = 0;
				for (int i = 0; i < 3; i++) {
					String name = null;
					name = threeCards.get(i).getCountry();
					if (name != null && player.hasThisCountry(name)) {
						map.getCountry(name).addArmies(2);
						cardMatchedCountries++;
						break;
					}
				}
				player.addTotalArmies(cardMatchedCountries * 2);  
//				give each card's matched country two armies, remember it is notadded two newArmies
//				because its placing is not controlled by the player
				deck.shuffleInThreeCards(threeCards);
			}
			
		player.addTotalArmies(newArmies);
		player.setThisTurnNewArmies(newArmies);		
	}
	
	
	
	
	private void computerHarvestArmies(Player player) {		
		int newArmies = 0;
		int armiesForOccupiedTerritories = 0;
		armiesForOccupiedTerritories = player.getNumberOfTerritories() / 3;
		if (armiesForOccupiedTerritories < 3)
			armiesForOccupiedTerritories = 3;
		newArmies += armiesForOccupiedTerritories;

		passedThreeCards=null;
		newArmies += player.getNumberOfContinents() * 7;
			if (player.isTurnInable() == true)  {
				ComputerPlayer computerPlayer = (model.ComputerPlayer) player;
				passedThreeCards = computerPlayer.turnInRiskCards();
				if(passedThreeCards!=null){
				deck.shuffleInThreeCards(passedThreeCards);
				}
			}

			if (passedThreeCards != null) {
				newArmies += returnArmiesAccordingXthSet(); // return armies
															// according to X^th
															// Set it is
				int cardMatchedCountries = 0;
				for (int i = 0; i < 3; i++) {
					String name = null;
					name = passedThreeCards.get(i).getCountry();
					if (name != null && player.hasThisCountry(name)) {
						map.getCountry(name).addArmies(2);
						cardMatchedCountries++;
					}
				}
				player.addTotalArmies(cardMatchedCountries * 2);
			}

		

		player.addTotalArmies(newArmies);
		player.setThisTurnNewArmies(newArmies);
	}
	
	private DiceCollection attackerDice;
	private DiceCollection defenderDice;
	
	
	/** Colin: the player.attackingOpp(map, player) should handle the attack: including get the country want to attack from and to 
	 * and then call the attackingHelper(Player attacker, Country attackerCountry, Country defenderCountry,int extraArmiseYouWantToMoveWhenYouWon);
	 * and  player.attackingOpp(map, player); seems superfluous?
	 * 
	 * 
	 * 
	 * Yiling: this one is used for computer player in GUI
	 * @param player
	 * @return
	 */
	
	
	public void attackingOppositionAI(ComputerPlayer player){		
		if (player==null){
			System.out.println("player value is null");	
		}
		else {			
		     player.AiAttack(this, map);	
		}
	}
	
	
	
	
	/** use this method for human
	 * 
	 * @param attacker
	 * @param attackerCountry
	 * @param defenderCountry
	 * @param armiseYouWantToMoveWhenYouWon
	 * @return
	 */
	
	public Player attackingHelper(Player attacker, Country attackerCountry, Country defenderCountry,int extraArmiseYouWantToMoveWhenYouWon){
	    String name = defenderCountry.getOwner();    
		
		
		
		Player defender = playerList.getPlayerByName(name);	
		
		Player winner=null;
		int attackerWins=0;
		int defenderWins=0;
		int attackerDiceNum=attacker.getAttackDiceAmount();
		int defenderDiceNum=defender.getDefendDiceAmount();
		while(attackerCountry.getArmies() > 1 && defenderCountry.getArmies() > 0) {
		if (!attacker.hasThisCountry(attackerCountry.getName()) || !defender.hasThisCountry(defenderCountry.getName()) || attackerCountry.getArmies()<=1){
			   		   
		   if (!attacker.hasThisCountry(attackerCountry.getName())){
			   System.out.println("attacker does not have this country   "+attackerCountry);
		   }
		   
		   if (!defender.hasThisCountry(defenderCountry.getName())){
			   System.out.println("defender does not have this country   "+defenderCountry);
		   }	   
//		   if (attackerCountry.getArmies()<=1)
//			   System.out.println(" attacker's country only has one army");  
		   		   
		   return null;
		   
		}
		else if (attackerDiceNum>3 || defenderDiceNum>2 || attackerDiceNum<1 || defenderDiceNum<1)
		{
			System.out.println("you set the wrong dice number");	
			return null;
		}
		
		
		else {
			if (attackerDiceNum>(attackerCountry.getArmies()-1)){
				attackerDiceNum=attackerCountry.getArmies()-1;
			}
			if (defenderDiceNum>defenderCountry.getArmies()){
				defenderDiceNum=defenderCountry.getArmies();
			}
			attackerDice=new DiceCollection(attackerDiceNum);
			defenderDice=new DiceCollection(defenderDiceNum);
			
			int diceCompared=2;
			if (defenderDiceNum==1 || attackerDiceNum==1)
				diceCompared=1;
			
			
			attackerDice.roll();
			defenderDice.roll();
			if (attackerDice.getGreatest()>defenderDice.getGreatest()) {
				defenderCountry.addArmies(-1);			
				defender.addTotalArmies(-1);
				attackerWins++;
			}
			else {
				attackerCountry.addArmies(-1);
				attacker.addTotalArmies(-1);
				defenderWins++;
			}
							
			if (diceCompared!=1){
				if (attackerDice.getSecondGreatest()>defenderDice.getSecondGreatest()){
					defenderCountry.addArmies(-1);			
					defender.addTotalArmies(-1);
					attackerWins++;
				}
				else {
					attackerCountry.addArmies(-1);
					attacker.addTotalArmies(-1);
					defenderWins++;
				}
			}
			
			if (attackerCountry.getArmies()<1){
				System.out.println("attacker lose its country, the algorithm is faulty!");
				return null;				
			}
			
			if (defenderCountry.getArmies()<1){					
	  		    defenderCountry.setArmies(0);
	  		    defenderCountry.setOwner(attacker.getName());
			    defender.removeCountry(defenderCountry);			   
			    attacker.addCountry(defenderCountry);
			    if (attacker.getClass().equals(HumanPlayer.class))
			    attacker.addToHand(deck.draw()); 
			    
			    int removedArmies=diceCompared+extraArmiseYouWantToMoveWhenYouWon;
			    if (removedArmies>(attackerCountry.getArmies()-1)){
			 		removedArmies=attackerCountry.getArmies()-1;	    	
			    }
			     moveArmies(attackerCountry, defenderCountry, removedArmies) ;					 
			 if (defender.getNumberOfTerritories()<1){
				 for (Card card:defender.getHand()){
					 attacker.addToHand(card);
				 }
				playerList.remove(defender) ;
			 }
		    	 if (attacker.handSize()>4){
		         deck.shuffleInThreeCards(attacker.autoTurnIn(attacker.getHand()));
	             attacker.setThisTurnNewArmies(returnArmiesAccordingXthSet());	 
	             if (attacker.getClass().equals(ComputerPlayer.class))
	             deployArmiesHelper(attacker); //listens to clicking in GUI
			 }
			 
			}
			
	
		}
		
		if (defenderWins>attackerWins){
			winner=defender;
		}
		else if (defenderWins<attackerWins){
			winner=attacker;
		}
		else{
			winner=null;
		}
		}
		
		return winner;
	}
	public int cardLeftInTheGame(){
		return deck.size();
	}
	/** Colin: the strategy should get the fromCountry and toCountry
	 *  then call the function: fortifyingTerritories(Player player, Country fromCountry, Country toCountry);
	 * 
	 * @param player
	 */
	public void fortifyingTerritoriesAI(ComputerPlayer player){
		Country fortifyFrom = player.fortifyFrom(map, player);
		Country fortifyTo = player.fortifyTo(map, player, fortifyFrom);
		if (fortifyFrom!=null && fortifyTo!=null && fortifyFrom.neighbourOrNot(fortifyTo))
		   fortifyingTerritories(player, fortifyFrom, fortifyTo);
		else if (fortifyFrom==null) {
		//   System.out.println("fortifyFrom country is null");
		}
		else if (fortifyTo==null) {
		//   System.out.println("fortifyTo country is null");
		}
		else {
		//   System.out.println("fortified countries are not neighbors");
		}
	}
    
	
	/** only call once for each player when it is their turn, ok?
	 * 
	 * @param player
	 * @param fromCountry
	 * @param toCountry
	 */
	public void fortifyingTerritories(Player player, Country fromCountry, Country toCountry){
		if (player.hasThisCountry(fromCountry.getName())&&player.hasThisCountry(toCountry.getName())){
			moveArmies(fromCountry,toCountry,fromCountry.getArmies()-1);
		}
		else
		{
			System.out.println("can't not move from or to the country you do not own!");
		}
						
	}
	/**
	 * use this method as private helper method, it can only move from one
	 * player's territory to another territory that he occupied
	 * 
	 * @return
	 */
	public boolean moveArmies(Country fromCountry, Country toCountry, int num) {
		if (num >= fromCountry.getArmies() || !toCountry.getOwner().equals(fromCountry.getOwner()))
			return false;
		else {
			fromCountry.loseArmies(num);
			toCountry.addArmies(num);
			return true;
		}
	}

	public Player gameOver() {
		if (playerList.getPlayerSize() == 1) {
			return playerList.getPlayers().get(0);
		} else
			return null;
	}

	public Map getMap() {
		return map;
	}

	
}