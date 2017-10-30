package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable{
	/*
	 * the 6 continents in the map
	 */
	private   ArrayList<Continent> continents;
	private   Continent northAmerica;
	private   Continent southAmerica;
	private   Continent europe;
	private   Continent africa;
	private   Continent asia;
	private   Continent australia;
	/*
	 * countries for Austrialia
	 */
	private   Country indonesia = new Country("Indonesia");
	private   Country newGuniea = new Country("New Guniea");
	private   Country easternAustralia = new Country("Eastern Australia");
	private   Country westernAustralia = new Country("Western Australia");
	/*
	 * countries for Africa
	 */
	private   Country northAfrica = new Country("North Africa");
	private   Country egypt = new Country("Egypt");
	private   Country eastAfrica = new Country("East Africa");
	private   Country southAfrica = new Country("South Africa");
	private   Country congo = new Country("Congo");
	private   Country madagascar = new Country("Madagascar");
	/*
	 * countries for Asia
	 */

	private   Country china = new Country("China");
	private   Country ural = new Country("Ural");
	private   Country afghanistan = new Country("Afghanistan");
	private   Country middleEast = new Country("Middle East");
	private   Country india = new Country("India");
	private   Country slam = new Country("Slam");
	private   Country mongolia = new Country("Mongolia");
	private   Country irkutsk = new Country("Irkutsk");
	private   Country yakutsk = new Country("Yakutsk");
	private   Country kamchatka = new Country("Kamchatka");
	private   Country japan = new Country("Japan");
	private   Country siberia = new Country("Siberia");
	/*
	 * countries for North America
	 */
	private   Country alaska = new Country("Alaska");
	private   Country northWestTerritory = new Country("North West Territory");
	private   Country alberta = new Country("Alberta");
	private   Country ontario = new Country("Ontario");
	private   Country quebec = new Country("Quebec");
	private   Country westernUnitedStates = new Country("Western United States");
	private   Country easternUnitedStates = new Country("Eastern United States");
	private   Country centralAmerica = new Country("Central America");
	private   Country greenLand = new Country("GreenLand");

	/*
	 * countries for Europe
	 */
	private   Country iceland = new Country("Iceland");
	private   Country greatBritain = new Country("Great Britain");
	private   Country westernEurope = new Country("Western Europe");
	private   Country scandinavia = new Country("Scandinavia");
	private   Country northernEurope = new Country("Northern Europe");
	private   Country southernEurope = new Country("Southern Europe");
	private   Country ukraine = new Country("Ukraine");

	/*
	 * countries for South America
	 */
	private   Country venezuela = new Country("Venezuela");
	private   Country brazil = new Country("Brazil");
	private   Country peru = new Country("Peru");
	private   Country argentina = new Country("Argentina");

	/*
	 * constructor for the Map()
	 */
	public Map() {
		northAmerica = new Continent();
		southAmerica = new Continent();
		europe = new Continent();
		africa = new Continent();
		asia = new Continent();
		australia = new Continent();
		addTheCountries();

		continents = new ArrayList<>();

		continents.add(northAmerica);
		continents.add(southAmerica);
		continents.add(europe);
		continents.add(africa);
		continents.add(asia);
		continents.add(australia);

	}

	/*
	 * add the countries to corresponding continent
	 */
	private void addTheCountries() {
		// TODO Auto-generated method stub
		northAmerica.addCountry(alaska);
		northAmerica.addCountry(northWestTerritory);
		northAmerica.addCountry(alberta);
		northAmerica.addCountry(ontario);
		northAmerica.addCountry(quebec);
		northAmerica.addCountry(westernUnitedStates);
		northAmerica.addCountry(easternUnitedStates);
		northAmerica.addCountry(centralAmerica);
		northAmerica.addCountry(greenLand);

		southAmerica.addCountry(venezuela);
		southAmerica.addCountry(brazil);
		southAmerica.addCountry(peru);
		southAmerica.addCountry(argentina);

		europe.addCountry(iceland);
		europe.addCountry(greatBritain);
		europe.addCountry(westernEurope);
		europe.addCountry(scandinavia);
		europe.addCountry(northernEurope);
		europe.addCountry(southernEurope);
		europe.addCountry(ukraine);

		africa.addCountry(northAfrica);
		africa.addCountry(egypt);
		africa.addCountry(eastAfrica);
		africa.addCountry(southAfrica);
		africa.addCountry(congo);
		africa.addCountry(madagascar);

		asia.addCountry(china);
		asia.addCountry(ural);
		asia.addCountry(afghanistan);
		asia.addCountry(middleEast);
		asia.addCountry(india);
		asia.addCountry(slam);
		asia.addCountry(mongolia);
		asia.addCountry(irkutsk);
		asia.addCountry(yakutsk);
		asia.addCountry(kamchatka);
		asia.addCountry(japan);
		asia.addCountry(siberia);

		australia.addCountry(indonesia);
		australia.addCountry(newGuniea);
		australia.addCountry(easternAustralia);
		australia.addCountry(westernAustralia);
		/*
		 * Adding neighbor for North America
		 */
		alaska.addNeighbour(northWestTerritory);
		alaska.addNeighbour(alberta);
		northWestTerritory.addNeighbour(alaska);
		northWestTerritory.addNeighbour(alberta);
		northWestTerritory.addNeighbour(ontario);
		northWestTerritory.addNeighbour(greenLand);
		alberta.addNeighbour(alaska);
		alberta.addNeighbour(northWestTerritory);
		alberta.addNeighbour(ontario);
		alberta.addNeighbour(westernUnitedStates);
		ontario.addNeighbour(northWestTerritory);
		ontario.addNeighbour(alberta);
		ontario.addNeighbour(quebec);
		ontario.addNeighbour(greenLand);
		ontario.addNeighbour(easternUnitedStates);
		ontario.addNeighbour(westernUnitedStates);
		greenLand.addNeighbour(northWestTerritory);
		greenLand.addNeighbour(ontario);
		greenLand.addNeighbour(quebec);
		greenLand.addNeighbour(iceland);
		quebec.addNeighbour(ontario);
		quebec.addNeighbour(easternUnitedStates);
		quebec.addNeighbour(greenLand);
		westernUnitedStates.addNeighbour(alberta);
		westernUnitedStates.addNeighbour(ontario);
		westernUnitedStates.addNeighbour(easternUnitedStates);
		westernUnitedStates.addNeighbour(centralAmerica);
		easternUnitedStates.addNeighbour(westernUnitedStates);
		easternUnitedStates.addNeighbour(ontario);
		easternUnitedStates.addNeighbour(quebec);
		easternUnitedStates.addNeighbour(centralAmerica);
		centralAmerica.addNeighbour(westernUnitedStates);
		centralAmerica.addNeighbour(easternUnitedStates);
		centralAmerica.addNeighbour(venezuela);
		/*
		 * Adding neighbor for South America
		 */
		venezuela.addNeighbour(centralAmerica);
		venezuela.addNeighbour(peru);
		venezuela.addNeighbour(brazil);
		peru.addNeighbour(venezuela);
		peru.addNeighbour(brazil);
		peru.addNeighbour(argentina);
		brazil.addNeighbour(venezuela);
		brazil.addNeighbour(peru);
		brazil.addNeighbour(northAfrica);
		brazil.addNeighbour(argentina);
		argentina.addNeighbour(peru);
		argentina.addNeighbour(brazil);
		/*
		 * Adding neighbor for Europe
		 */
		iceland.addNeighbour(greenLand);
		iceland.addNeighbour(greatBritain);
		iceland.addNeighbour(scandinavia);
		greatBritain.addNeighbour(iceland);
		greatBritain.addNeighbour(westernEurope);
		greatBritain.addNeighbour(scandinavia);
		greatBritain.addNeighbour(northernEurope);
		westernEurope.addNeighbour(greatBritain);
		westernEurope.addNeighbour(northernEurope);
		westernEurope.addNeighbour(southernEurope);
		westernEurope.addNeighbour(northAfrica);
		scandinavia.addNeighbour(iceland);
		scandinavia.addNeighbour(greatBritain);
		scandinavia.addNeighbour(northernEurope);
		scandinavia.addNeighbour(ukraine);
		northernEurope.addNeighbour(scandinavia);
		northernEurope.addNeighbour(greatBritain);
		northernEurope.addNeighbour(westernEurope);
		northernEurope.addNeighbour(southernEurope);
		northernEurope.addNeighbour(ukraine);
		southernEurope.addNeighbour(westernEurope);
		southernEurope.addNeighbour(northernEurope);
		southernEurope.addNeighbour(ukraine);
		southernEurope.addNeighbour(egypt);
		southernEurope.addNeighbour(northAfrica);
		southernEurope.addNeighbour(middleEast);
		ukraine.addNeighbour(scandinavia);
		ukraine.addNeighbour(northernEurope);
		ukraine.addNeighbour(southernEurope);
		ukraine.addNeighbour(middleEast);
		ukraine.addNeighbour(ural);
		ukraine.addNeighbour(afghanistan);
		/*
		 * adding neighbor for Africa
		 */
		northAfrica.addNeighbour(brazil);
		northAfrica.addNeighbour(westernEurope);
		northAfrica.addNeighbour(southernEurope);
		northAfrica.addNeighbour(egypt);
		northAfrica.addNeighbour(eastAfrica);
		northAfrica.addNeighbour(congo);
		egypt.addNeighbour(northAfrica);
		egypt.addNeighbour(southernEurope);
		egypt.addNeighbour(eastAfrica);
		egypt.addNeighbour(middleEast);
		congo.addNeighbour(northAfrica);
		congo.addNeighbour(eastAfrica);
		congo.addNeighbour(southAfrica);
		eastAfrica.addNeighbour(egypt);
		eastAfrica.addNeighbour(northAfrica);
		eastAfrica.addNeighbour(congo);
		eastAfrica.addNeighbour(southAfrica);
		eastAfrica.addNeighbour(middleEast);
		eastAfrica.addNeighbour(madagascar);
		southAfrica.addNeighbour(congo);
		southAfrica.addNeighbour(eastAfrica);
		southAfrica.addNeighbour(madagascar);
		madagascar.addNeighbour(southAfrica);
		madagascar.addNeighbour(eastAfrica);
		/*
		 * adding neighbor for Asia
		 */

		china.addNeighbour(mongolia);
		china.addNeighbour(india);
		china.addNeighbour(siberia);
		china.addNeighbour(ural);
		china.addNeighbour(afghanistan);
		china.addNeighbour(slam);
		ural.addNeighbour(ukraine);
		ural.addNeighbour(afghanistan);
		ural.addNeighbour(siberia);
		ural.addNeighbour(china);
		afghanistan.addNeighbour(ural);
		afghanistan.addNeighbour(ukraine);
		afghanistan.addNeighbour(middleEast);
		afghanistan.addNeighbour(india);
		afghanistan.addNeighbour(china);
		middleEast.addNeighbour(southernEurope);
		middleEast.addNeighbour(ukraine);
		middleEast.addNeighbour(egypt);
		middleEast.addNeighbour(eastAfrica);
		middleEast.addNeighbour(india);
		middleEast.addNeighbour(afghanistan);
		india.addNeighbour(middleEast);
		india.addNeighbour(china);
		india.addNeighbour(slam);
		india.addNeighbour(afghanistan);
		slam.addNeighbour(india);
		slam.addNeighbour(china);
		slam.addNeighbour(indonesia);
		mongolia.addNeighbour(china);
		mongolia.addNeighbour(siberia);
		mongolia.addNeighbour(irkutsk);
		mongolia.addNeighbour(kamchatka);
		mongolia.addNeighbour(japan);
		japan.addNeighbour(mongolia);
		japan.addNeighbour(kamchatka);
		irkutsk.addNeighbour(mongolia);
		irkutsk.addNeighbour(kamchatka);
		irkutsk.addNeighbour(yakutsk);
		irkutsk.addNeighbour(siberia);
		kamchatka.addNeighbour(japan);
		kamchatka.addNeighbour(mongolia);
		kamchatka.addNeighbour(yakutsk);
		kamchatka.addNeighbour(irkutsk);
		yakutsk.addNeighbour(kamchatka);
		yakutsk.addNeighbour(irkutsk);
		yakutsk.addNeighbour(siberia);
		siberia.addNeighbour(yakutsk);
		siberia.addNeighbour(irkutsk);
		siberia.addNeighbour(mongolia);
		siberia.addNeighbour(china);
		siberia.addNeighbour(ural);
		/*
		 * adding neighbour for Australia
		 */
		indonesia.addNeighbour(slam);
		indonesia.addNeighbour(newGuniea);
		indonesia.addNeighbour(westernAustralia);
		newGuniea.addNeighbour(indonesia);
		newGuniea.addNeighbour(westernAustralia);
		newGuniea.addNeighbour(easternAustralia);
		westernAustralia.addNeighbour(indonesia);
		westernAustralia.addNeighbour(newGuniea);
		westernAustralia.addNeighbour(easternAustralia);
		easternAustralia.addNeighbour(westernAustralia);
		easternAustralia.addNeighbour(newGuniea);
	}

	/*
	 * get the Asia continent
	 */
	public Continent getAsia() {
		return asia;
	}

	/*
	 * get the North America continent
	 */
	public Continent getNorthAmerica() {
		return northAmerica;
	}

	/*
	 * get the South America continent
	 */
	public Continent getSouthAmerica() {
		return southAmerica;
	}

	/*
	 * get the South Europe continent
	 */
	public Continent getEurope() {
		return europe;
	}

	/*
	 * get the South Africa continent
	 */
	public Continent getAfrica() {
		return africa;
	}

	/*
	 * get the South Austria continent
	 */
	public Continent getAustralia() {
		return australia;
	}

	public Country getCountry(String name) {
		Country country = null;
		for (Continent aContinent : continents) {
			country = aContinent.getCountry(name);

			if (country != null)
				break;
		}

		return country;

	}
	
	public ArrayList<Country> getUnoccupiedCountries() {
		ArrayList<Country> unoccupied = new ArrayList<>();;
		for(Continent aContinent : continents) {
			ArrayList<Country> foo = new ArrayList<>();
			foo = aContinent.getUnoccupiedCountries();
			unoccupied.addAll(foo);
		}

//		System.out.println("unoccupied country:"+unoccupied);
		return unoccupied;
	}
	public Country getCountryFromMap(String name){
		if(this.getAsia().getCountry(name)!=null){
			return this.getAsia().getCountry(name);
		}else if(this.getAfrica().getCountry(name)!=null){
			return this.getAfrica().getCountry(name);
		}else if(this.getAustralia().getCountry(name)!=null){
			return this.getAustralia().getCountry(name);
		}else if (this.getEurope().getCountry(name)!=null){
			return this.getEurope().getCountry(name);
		}else if(this.getNorthAmerica().getCountry(name)!=null){
			return this.getNorthAmerica().getCountry(name);
		}else if(this.getSouthAmerica().getCountry(name)!=null){
			return this.getSouthAmerica().getCountry(name);
		}
		return null;

}
}