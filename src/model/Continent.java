package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable{

	private ArrayList<Country> elements;

	/*
	 * Constructor for Continent
	 */
	public Continent() {
		elements = new ArrayList<Country>();
	}

	/*
	 * add the country to the continent
	 */
	public void addCountry(Country c) {
		elements.add(c);
	}

	/*
	 * checking if the continent contains the country or not
	 */
	public boolean containCountry(Country c) {
		return elements.contains(c);
	}
	public int getNumberOfCountry(){
		return elements.size();
	}

	/*
	 * get the country in the continent according the the given String
	 */
	public Country getCountry(String c) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getName().compareTo(c) == 0) {
				return elements.get(i);
			}
		}
		return null;
	}

	/*
	 * public boolean occupyContinent( ArrayList<Country> countries){ if(
	 * countries.size()!=elements.size()){ return false; }else{ for(int
	 * i=0;i<countries.size();i++){ if(!elements.contains(countries.get(i))){
	 * return false; } } } return true;
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i).toString());
		}
		return null;
	}

	
	public Country getCountryInt(int i){
		return elements.get(i);
	}
	
	public boolean occupyContinent(ArrayList<Country> countries) {
		if (countries.size() != elements.size()) {
			return false;
		} else {
			for (int i = 0; i < countries.size(); i++) {
				if (!elements.contains(countries.get(i))) {
					return false;
				}
			}
		}
		return true;

	}


	private String owner;

	public void setOwner(String o) {
		owner = o;
	}

	public String getOwner() {
		return owner;
	}
	
	public ArrayList<Country> getAllCountries() {
		return elements;
	}

	public ArrayList<Country> getUnoccupiedCountries() {
		ArrayList<Country> unoccupied = new ArrayList<>();
		for(Country aCountry : elements) {
			if(aCountry.getOwner()==null)

				unoccupied.add(aCountry);
		}
		
		return unoccupied;
	}

}