/**
 * This class stores the Dice objects that will be used
 * in the game. The collection adds and remove Dice objects
 * based on user preference.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

public class DiceCollection implements Serializable{

	private ArrayList<Die> dice;

	/*
	 * The DiceCollection constructor.
	 */
	public DiceCollection(int n) {
		dice = new ArrayList<Die>();
		while (dice.size() < n) {
			dice.add(new Die());
		}
	}
	
	/*
	 * Returns the size of the DiceCollection
	 */
	public int size() {
		return dice.size();
	}

	/*
	 * Sets the amount of Dice objects will be in the DiceCollection
	 */
	public void setAmount(int n) {
		while (dice.size() < n) {
			dice.add(new Die());
		}

		while (dice.size() > n) {
			
			System.out.println(dice.size());
			System.out.println(n);
			dice.remove(0);
		}

	}

	/*
	 * Rolls every Dice object in the DiceCollection
	 */

	public void roll() {
		for (int i = 0; i < dice.size(); i++) {
			dice.get(i).roll();
		}
	}

	/*
	 * Finds and returns the greatest dice value in the DiceCollection
	 */

	public int getGreatest() {
		int greatest = 0;
		
		for(int i = 0; i < dice.size(); i++) {
			if(dice.get(i).getValue() > greatest)
				greatest = dice.get(i).getValue();
		}
		
		return greatest;

	}

	/*
	 * Finds and returns the second greatest dice value in the DiceCollection
	 */
	public int getSecondGreatest() {
		ArrayList<Die> second = (ArrayList<Die>) dice.clone();
		int greatest = getGreatest();
		
		for(int i = 0; i < dice.size(); i++) {
			if(second.get(i).getValue() == greatest) {
				second.remove(i);
				greatest = 0;
				break;
			}
		}
		greatest=-1;
		for(int i = 0; i < second.size(); i++) {
			if(second.get(i).getValue() > greatest)
				greatest = second.get(i).getValue();
		}
		
		return greatest;
	}

	public Die getDieInCollection(int i) {
		return dice.get(i);
	}

}