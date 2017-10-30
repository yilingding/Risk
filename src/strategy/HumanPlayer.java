package strategy;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.Continent;
import model.Country;
import model.Card;
import model.CardType;
import model.DiceCollection;
import model.RiskGame;

public class HumanPlayer extends Player implements Serializable{
	

	public HumanPlayer(String name, String color) {

		super(name,color);
		autoRemoveFlag=true;
	 //  Auto-generated constructor stub		
	}
	/**
	 *  set this flag in GUI
	 * @param flag
	 */
	public void setAutoRemoveFlag(boolean flag){
		autoRemoveFlag=flag;
	}
	
	public boolean isAutoRemove(){
		return autoRemoveFlag;
	}

	public ArrayList<String> handPickedTurnIn(ArrayList<Card> threeCards){

	  return handPickedRemove(threeCards);		
	}
	private ArrayList<String> handPickedRemove(ArrayList<Card> list){
		if (list.size()!=3) 
			return null;
		else
		{
		   ArrayList<String> removedCountries=new ArrayList<String>(); 
		   int wildAmount=0;
		   for (int i=0;i<hand.size();i++) // hand.size()==3 initially
			   if (list.get(i).getType()==CardType.Wild) {
				   wildAmount++;
				   list.remove(i);
				   i--;
			   }
				  
		   if (wildAmount>2)
		   {
			   System.out.println("Error: more than three wild Cards");
			   return null;
		   }		   
		   else if (wildAmount==2){
			   removedCountries.add(list.get(0).getCountry());
			   list.remove(0);
			   return removedCountries;
		   }
		   else if (wildAmount==1)
		   {
			   removedCountries.add(list.get(0).getCountry());
			   removedCountries.add(list.get(1).getCountry());
			   list.remove(0);
			   list.remove(0);
			   return removedCountries;
		   }
		   else {
			   if (hand.get(0).getType()==hand.get(1).getType() && hand.get(1).getType()==hand.get(2).getType()) {
					for (int i=0;i<3;i++)
		        		removedCountries.add(hand.get(i).getCountry());
		         	for (int i=0;i<3;i++)
		        		hand.remove(0); 
		        return removedCountries; 	
			   }
			   if (hand.get(0).getType()!=hand.get(1).getType() && hand.get(1).getType()!=hand.get(2).getType() &&
					   hand.get(0).getType()!=hand.get(2).getType()){
					for (int i=0;i<3;i++)
		        		removedCountries.add(hand.get(i).getCountry());
		         	for (int i=0;i<3;i++)
		        		hand.remove(0); 
		        return removedCountries; 
			   }
			   return null;
		   }		
		}
		
	}


	
	
	

	  
}

