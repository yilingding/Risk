/**
 * This class represents the dice in the game.
 * It randomly "rolls" the die
 */

package model;
import java.io.Serializable;
import java.util.Random;

public class Die implements Serializable{
	
	private Random rng;
	private int value;
	
	/*
	 * The Dice object constructor
	 */
	public Die() {
		rng = new Random();
	}
	
	/*
	 * Rolls the Dice object to get a random int
	 * between 1 and 6.
	 */
	public void roll() {
		value = rng.nextInt(6) + 1;
	}
	
	/*
	 * Returns the value the Dice object currently has.
	 */
	public int getValue() {
		return value;
	}

	/*
	 * Compares two Die objects.
	 */
	public int compareTo(Die d) {
		return this.getValue() - d.getValue();
	}
}

 

