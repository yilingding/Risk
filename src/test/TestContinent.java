package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.ArmyPoints;
import model.Continent;
import model.Country;
import model.CountryName;
import model.FlagPoints;
import model.Map;

public class TestContinent {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	Map map=new Map();
	ArmyPoints armyPoints=new ArmyPoints();
	FlagPoints flagPoints=new FlagPoints();
	CountryName countryNames=new CountryName();
	
	
	@Test
	public void test1() {
		System.out.println(map.getAfrica().toString());
		Country	country=map.getAfrica().getCountry("East Africa");
		
		Country	country2=map.getAfrica().getCountry("Egypt");
		assertTrue(country.getName().compareTo("East Africa")==0);
		assertTrue(country.getNeighbour().contains(country2));
		assertTrue(country.neighbourOrNot(country2));
		assertTrue(map.getAfrica().containCountry(country));
		Country	country1=map.getAfrica().getCountry("GreenLand");
		assertTrue(country1==null);
		assertTrue(countryNames.returnCountryName().length==42);
		 Point china=new Point(791,307);
		 //assertTrue(china==flagPoints.getPoint("China"));
		// System.out.println("hehehehe"+flagPoints.getName(china));
		
		 armyPoints.getPoint("India");
		 flagPoints.getPoint("China");
		 flagPoints.getName(china);
		 Point india=new Point(727,359);
		// assertTrue(india==armyPoints.getPoint("India"));
	}
	@Test
	public void test2() {
		Continent aisa=map.getAsia();
		Continent na=map.getNorthAmerica();
		Continent sa=map.getSouthAmerica();
		Continent eu=map.getEurope();
		Continent au=map.getAustralia();
		au.setOwner("1");
		assertTrue(au.getOwner().compareTo("1")==0);
		assertTrue(au.getNumberOfCountry()==au.getAllCountries().size());
		Country china=aisa.getCountry("China");
		assertTrue(map.getCountryFromMap("China")==china);
		Country	country2=map.getCountryFromMap("Egypt");
		assertFalse(au.getCountryInt(0)==country2);
		ArrayList<Country> countries =new ArrayList<Country> ();
		countries.add(china);
		assertFalse(au.occupyContinent(countries));
		ArrayList<Country> countries2=au.getAllCountries();
		assertTrue(au.occupyContinent(countries2));
		assertTrue(map.getUnoccupiedCountries().size()==42);
		
		Country	country1=map.getCountryFromMap("GreenLand");
		Country	country3=map.getCountryFromMap("Eastern Australia");
		Country	country4=map.getCountryFromMap("North Africa");
		Country	country5=map.getCountryFromMap("Brazil");
		Country	country6=map.getCountryFromMap("Iceland");
		Country	country7=map.getCountry("Iceland");
		//country2.enemyNeighbors();
		assertTrue(country7==country6);
		assertTrue(country5.getName().compareTo("Brazil")==0);
		country1.setOwnerIndex(1);
		assertTrue(country1.getOwnerIndex()==1);
		
		Country india=map.getCountryFromMap("India");
		
		assertTrue(india==china.getNeighbour("India"));
		country4.setArmies(1);
		country4.addArmies(1);
		assertTrue(country4.getArmies()==2);
		country4.loseArmies(2);
		assertTrue(country4.getArmies()==0);
		String name="1";
		country3.setOwner(name);
		assertTrue(country3.enemyNeighbors()!=0);
		assertTrue(country3.getOwner().compareTo(name)==0);
		
	}
	@Test
	public void testCountryNeighbor() {
		Country c=new Country("countryYouLike");
		assertEquals(null,c.getNeighbour("a"));
		assertEquals(0, c.getEnemyNeighbors().size());
	}
	
	

}
