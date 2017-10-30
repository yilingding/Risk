package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Country implements Serializable{
	private String countryName;
	private String ownerName;
	
    private int ownerIndex; 
	private int armies;

	private ArrayList<Country> neighbour;

	/*
	 * Country constructor
	 */
	public Country(String i) {
		countryName = i;
		ownerName = null;
		armies=0;
		neighbour = new ArrayList<Country>();
	}
	
	/**
	 * this index keeps track with the index in playerList
	 */
	public void setOwnerIndex(int index){
		ownerIndex=index;
	}
	public int getOwnerIndex(){
		return ownerIndex;	
	}

	/*
	 * get Country name
	 */
	public String getName() {
		return countryName;
	}

	/*
	 * get the neighbour of the country
	 */
	public ArrayList<Country> getNeighbour() {
		
		return neighbour;
	}

	/*
	 * add country n to the neighbour of the country
	 */
	public void addNeighbour(Country n) {
		neighbour.add(n);
	}

	/*
	 * checking if the country is the neighbour or not
	 */
	public boolean neighbourOrNot(Country n) {
		return neighbour.contains(n);
	}

	/*
	 * public void occupied(String name) { ownerName = name; }
	 * 
	 * public boolean occupiedOrNot() { return ownerName == null; }
	 */

	/*
	 * print the country
	 */
	public String toString() {
		return countryName;
	}


	

	public void setArmies(int num) {
		armies = num;
	}

	public void addArmies(int num) {
		armies += num;
	}


	public void loseArmies(int num) {
		armies -= num;
	}

	public int getArmies() {
		return armies;
	}


	public void setOwner(String string) {
		this.ownerName = string;

	}

	public String getOwner() {
		return ownerName;
	}

	
	public int enemyNeighbors() {
		ArrayList<Country> neighbors = this.getNeighbour();
		int enemy = 0;
		for(Country aCountry : neighbors) {
			if(!this.getOwner().equals(aCountry.getOwner()))
				enemy++;
		}
		return enemy;
	}
	
	public ArrayList<Country> getEnemyNeighbors(){
		ArrayList<Country> enemyNeibors = new ArrayList<Country>();
		ArrayList<Country> neighbors = this.getNeighbour();
		
		for(Country aCountry : neighbors) {
			if(!this.getOwner().equals(aCountry.getOwner()))
				enemyNeibors.add(aCountry);
		}
		return enemyNeibors;
		
	}

	/**
	 * 
	 * @param neib
	 * @return
	 */
	public Country getNeighbour(String neib) {
		if (neighbour.size() == 0) {
			System.out.println("Error, this country has no neighbour.");
		} else {
			for (int i = 0; i < neighbour.size(); i++)
				if (neighbour.get(i).getName().equals(neib))
					return neighbour.get(i);
		}
		return null;
	}






}

