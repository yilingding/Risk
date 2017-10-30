package controller;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Country;
import model.Map;

/**
 * This class listen to user's mouse click to select country.
 */
public class PointListener implements MouseListener {

	private int selectCountryStage; // 0 ok to select, 1 no
    private int numberOfClicked;
    private Map map;
    private Polygon selectedPolygons = null;
	public PointListener(Map map) {
		this.map=map;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/**
		 * North America
		 */
		if (Polygons.alaska.contains(e.getPoint())){
			Country alaska= map.getNorthAmerica().getCountry("Alaska");
			System.out.println("Alaska was clicked!");
			System.out.println(alaska.getNeighbour().toString());
			
		}
			
		if (Polygons.nwt.contains(e.getPoint())){
			Country northwestTerritory= map.getNorthAmerica().getCountry("North West Territory");
			System.out.println(northwestTerritory.getNeighbour().toString());
			System.out.println("Northwest Territory was clicked!");
		}
			
		if (Polygons.greenland.contains(e.getPoint())){
			Country greenland= map.getNorthAmerica().getCountry("GreenLand");
			System.out.println(greenland.getNeighbour().toString());
			System.out.println("Greenland was clicked!");
		}
			
		if (Polygons.alberta.contains(e.getPoint())){
			Country alberta= map.getNorthAmerica().getCountry("Alberta");
			System.out.println(alberta.getNeighbour().toString());
			System.out.println("Alberta was clicked!");
		}
			
		if (Polygons.ontario.contains(e.getPoint())){
			Country ontario= map.getNorthAmerica().getCountry("Ontario");
			System.out.println(ontario.getNeighbour().toString());
			System.out.println("Ontario was clicked!");
		}
			
		if (Polygons.quebec.contains(e.getPoint())){
			Country quebec= map.getNorthAmerica().getCountry("Quebec");
			System.out.println(quebec.getNeighbour().toString());
			System.out.println("Quebec was clicked!");
		}
			
		if (Polygons.wus.contains(e.getPoint())){
			Country westernUnitedStates= map.getNorthAmerica().getCountry("Western United States");
			System.out.println(westernUnitedStates.getNeighbour().toString());
			System.out.println("Western United States was clicked!");
		}
			
		if (Polygons.eus.contains(e.getPoint())){
			Country easternUnitedStates= map.getNorthAmerica().getCountry("Eastern United States");
			System.out.println(easternUnitedStates.getNeighbour().toString());
			System.out.println("Eastern United States was clicked!");
		}
			
		if (Polygons.ca.contains(e.getPoint())){
			Country centralAmerica= map.getNorthAmerica().getCountry("Central America");
			System.out.println(centralAmerica.getNeighbour().toString());
			System.out.println("Central America was clicked!");
		}
			


		/**
		 * South America
		 */
		if (Polygons.venezuela.contains(e.getPoint())){
			Country venezuela= map.getSouthAmerica().getCountry("Venezuela");
			System.out.println(venezuela.getNeighbour().toString());
			System.out.println("Venezuela was clicked!");
		}
			
		if (Polygons.peru.contains(e.getPoint())){
			Country peru= map.getSouthAmerica().getCountry("Peru");
			System.out.println(peru.getNeighbour().toString());
			System.out.println("Peru was clicked!");
		}
			
		if (Polygons.brazil.contains(e.getPoint())){
			Country brazil= map.getSouthAmerica().getCountry("Brazil");
			System.out.println(brazil.getNeighbour().toString());
			System.out.println("Brazil was clicked!");
		}
			
		if (Polygons.argentina.contains(e.getPoint())){
			Country argentina= map.getSouthAmerica().getCountry("Argentina");
			System.out.println(argentina.getNeighbour().toString());
			System.out.println("Argentina was clicked!");
		}
			


		/**
		 * Europe
		 */
		if (Polygons.iceland.contains(e.getPoint())){
			Country iceland= map.getEurope().getCountry("Iceland");
			System.out.println(iceland.getNeighbour().toString());
			System.out.println("Iceland was clicked!");
		}
			
		if (Polygons.gbr.contains(e.getPoint())){
			Country greatBritain= map.getEurope().getCountry("Great Britain");
			System.out.println(greatBritain.getNeighbour().toString());
			System.out.println("Great Britain was clicked!");
		}
			
		if (Polygons.weu.contains(e.getPoint())){
			Country westernEurope= map.getEurope().getCountry("Western Europe");
			System.out.println(westernEurope.getNeighbour().toString());
			System.out.println("Western Europe was clicked!");
		}
			
		if (Polygons.seu.contains(e.getPoint())){
			Country seu= map.getEurope().getCountry("Southern Europe");
			System.out.println(seu.getNeighbour().toString());
			System.out.println("Southern Europe was clicked!");
		}
			
		if (Polygons.neu.contains(e.getPoint())){
			Country neu= map.getEurope().getCountry("Northern Europe");
			System.out.println(neu.getNeighbour().toString());
			System.out.println("Northern Europe was clicked!");
		}
			
		if (Polygons.ukraine.contains(e.getPoint())){
			Country ukraine= map.getEurope().getCountry("Ukraine");
			System.out.println(ukraine.getNeighbour().toString());
			System.out.println("Ukraine was clicked!");
		}
			
		if (Polygons.scandinavia.contains(e.getPoint())){
			Country country= map.getEurope().getCountry("Scandinavia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Scandinavia was clicked!");
		}
			


		/**
		 * Africa
		 */
		if (Polygons.madagascar.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("Madagascar");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Madagascar was clicked!");
		}
			
		if (Polygons.saf.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("South Africa");
			System.out.println(country.getNeighbour().toString());
			System.out.println("South Africa was clicked!");
		}
			
		if (Polygons.congo.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("Congo");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Congo was clicked!");
		}
			
		if (Polygons.eaf.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("East Africa");
			System.out.println(country.getNeighbour().toString());
			System.out.println("East Africa was clicked!");
		}
			
		if (Polygons.naf.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("North Africa");
			System.out.println(country.getNeighbour().toString());
			System.out.println("North Africa was clicked!");
		}
			
		if (Polygons.egypt.contains(e.getPoint())){
			Country country= map.getAfrica().getCountry("Egypt");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Egypt was clicked!");
		}
			


		/**
		 * Australia
		 */
		if (Polygons.indonesia.contains(e.getPoint())){
			Country country= map.getAustralia().getCountry("Indonesia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Indonesia was clicked!");
		}
			
		if (Polygons.ng.contains(e.getPoint())){
			Country country= map.getAustralia().getCountry("New Guniea");
			System.out.println(country.getNeighbour().toString());
			System.out.println("New Guniea was clicked!");
		}
			
		if (Polygons.waus.contains(e.getPoint())){
			Country country= map.getAustralia().getCountry("Western Australia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Western Australia was clicked!");
		}
			
		if (Polygons.eaus.contains(e.getPoint())){
			Country country= map.getAustralia().getCountry("Eastern Australia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Eastern Australia was clicked!");
		}
			


		/**
		 * Asia
		 */
		if (Polygons.mde.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Middle East");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Middle East was clicked!");
		}
			
		if (Polygons.afgh.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Afghanistan");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Afghanistan was clicked!");
		}
			
		if (Polygons.ural.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Ural");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Ural was clicked!");
		}
			
		if (Polygons.siberia.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Siberia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Siberia was clicked!");
		}
			
		if (Polygons.yakutsk.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Yakutsk");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Yakutsk was clicked!");
		}
			
		if (Polygons.kamchatka.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Kamchatka");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Kamchatka was clicked!");
		}
			
		if (Polygons.irkutsk.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Irkutsk");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Irkutsk was clicked!");
		}
			
		if (Polygons.mongolia.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Mongolia");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Mongolia was clicked!");
		}
			
		if (Polygons.japan.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Japan");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Japan was clicked!");
		}
			
		if (Polygons.china.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("China");
			System.out.println(country.getNeighbour().toString());
			System.out.println("China was clicked!");
		}
			
		if (Polygons.slam.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("Slam");
			System.out.println(country.getNeighbour().toString());
			System.out.println("Slam was clicked!");
		}
			
		if (Polygons.india.contains(e.getPoint())){
			Country country= map.getAsia().getCountry("India");
			System.out.println(country.getNeighbour().toString());
			System.out.println("India was clicked!");
		}
			


		System.out.println(e.getPoint() + "\n"); // Use this to find the
													// significant points to put
													// into the arrays.

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}