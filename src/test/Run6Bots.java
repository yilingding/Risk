package test;
import model.ComputerPlayer;
import model.Map;
import model.Player;
import model.PlayerList;
import model.RiskGame;
import strategy.ExpertAI;
import strategy.SimpleAI;

public class Run6Bots {

	private final static int totalCountriesInMap = 42;
	private static RiskGame theGame;
    private static  Map map;
  	private static final int runTimes=1000;     // change the run time if you wish
    /**
     * when you set the runTimes to 1000, the run time of this program is about 10 seconds.
     * 
     * you can comment out one of the AI and leave two AI playing against each other you will discover that the ExpertAI beats IntermediateAI and SimpleAI,
     * and IntermediateAI beat SimpleAI.
     */
  	public static void main(String[] args)  {
  	    

  		int simpleWins=0;
  		int intermediateWins=0;
  		int expertWins=0;
  		
  	   for (int times=0; times<1000; times++)	{
  		    theGame = new RiskGame();
 //			theGame.addExpertAI("expertAI1", "blue");
//	  		theGame.addExpertAI("expertAI2", "green");
  	  		
  	  	theGame.addExpertAI("expertAI3", "purple");
//	  		theGame.addExpertAI("expertAI4", "orange");
	  		theGame.addExpertAI("expertAI5", "red");
//  	    theGame.addExpertAI("expertAI6", "yellow");
 // 		    theGame.addIntermediateAI("IntermediateAI3", "blue");
     	    theGame.addIntermediateAI("IntermediateAI1", "purple");
  	  		theGame.addIntermediateAI("IntermediateAI2", "orange");
//  	  		theGame.addSimpleAI("simpleAI1", "red");
         	theGame.addSimpleAI("simpleAI2", "yellow");	  		
    	  	theGame.addSimpleAI("simpleAI3", "orange");
         	
  	  		theGame.setInitialArmies();
  	  		map=theGame.getMap();
  	  		setupArmies(theGame.getPlayerList());
  	  		
  	  		

  	  		
  		ComputerPlayer p = (ComputerPlayer) theGame.getPlayerList().getPlayers().get(0);
  		while (theGame.getPlayerList().getPlayerSize()>1){
  			theGame.deployArmies(p);	
  			theGame.attackingOppositionAI(p);				
  			theGame.fortifyingTerritoriesAI(p);
// 			System.out.println(theGame.getPlayerList().getPlayers().size());
//  	       for (Player print:theGame.getPlayerList().getPlayers()){
//  			System.out.println("total Armies:"+print.getName()+"   "+ print.getTotalArmies() + "    " +print.getNumberOfTerritories());
//  	       }
    	
  	       p = (ComputerPlayer) theGame.getPlayerList().iterateToNextPlayer(p);
  	       
  		}
  		ComputerPlayer winner = (ComputerPlayer) theGame.getPlayerList().getPlayer(0);
  		
  		if (winner.getStrategy().getClass().equals(SimpleAI.class)){
  			simpleWins++;
  		}
  		else if (winner.getStrategy().getClass().equals(ExpertAI.class))
  		{
  			expertWins++;
  		}
  		else
  			intermediateWins++;
  		
  //		System.out.println(winner);
  	   }
  		System.out.println("simpleAI wins: "+ simpleWins);
  		System.out.println("intermediateAI wins: "+ intermediateWins);
  		System.out.println("expertAI wins: "+ expertWins);
  	}
  	
  	
    private static void setupArmies(PlayerList playerList){
	int claimed;
	int armyIndex;
	int playerIndex = 0;
	int initialArmies = theGame.getInitialArmy();
	
	for (armyIndex = 0, claimed = 0; claimed < totalCountriesInMap && armyIndex < initialArmies; armyIndex++) {
		
		
		for (playerIndex = 0; claimed < totalCountriesInMap
				&& playerIndex < playerList.getPlayerSize(); playerIndex++, claimed++) {
			
			Player currentPlayer = playerList.getPlayers().get(playerIndex);
				ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
				computerPlayer.placeInitialArmy(map);
		}
//		System.out.println(playerList.countriesToString());
	}
	
	for (int i = playerIndex; i < playerList.getPlayerSize(); i++) {

		Player currentPlayer = playerList.getPlayers().get(i);	
			ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
			computerPlayer.fortifyInitialArmy(map);
//			System.out.println(playerList.countriesToString());
		
	}

	for (; armyIndex < initialArmies; armyIndex++) {
		for (playerIndex = 0; playerIndex < playerList.getPlayerSize(); playerIndex++) {
			Player currentPlayer = playerList.getPlayers().get(playerIndex);
			 
				ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
				computerPlayer.fortifyInitialArmy(map);
		}
//		System.out.println(playerList.countriesToString());
	}	
}  	
}