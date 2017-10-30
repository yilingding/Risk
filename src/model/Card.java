/**
 * This class represents the cards to be used in the game.
 */

package model;

import java.io.Serializable;

public class Card  implements Serializable{
	
	private CardType type;
	private String country;
	
	/*
	 * The card class constructor
	 */
	public Card(CardType t, String c) {
		this.type = t;
		this.country = c;
	}
	
	public CardType getType() {
		return this.type;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getTypeString(){
		if(this.getType()==CardType.Artillery){
			return "Artillery";
		}else if(this.getType()==CardType.Calvary){
			return "Calvary";
		}else if(this.getType()==CardType.Infantry){
			return "Infantry";
		}else if(this.getType()==CardType.Wild){
			return "Wild";
		}
		return "";
	}
	public String toString(){
		return this.getTypeString()+"  "+this.getCountry();
		
	}

}