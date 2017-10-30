package model;

import java.awt.Point;
import java.util.HashMap;

public class ArmyPoints {
	private HashMap<String,Point> allPoints;
	public ArmyPoints(){
		allPoints=new HashMap();
		construct();
		System.out.println("builded hash map:"+allPoints);
	}
	
	public Point getPoint(String name){
		return allPoints.get(name);
	}
	
	/**
	 * North America
	 */
	public static Point alaska=new Point(49, 111);
	public static Point nwt=new Point(144, 106);
	public static Point alberta=new Point(136, 165);
	public static Point wus=new Point(142, 239);
	public static Point eus=new Point(206,273);
	public static Point ontario=new Point(189,143);
	public static Point quebec=new Point(274,178);
	public static Point greenland=new Point(334, 69);

	public static Point ca=new Point(158,304);
	
	/**
	 * South America
	 */
	
	public static Point venezuela=new Point(194, 390);
	public static Point peru=new Point(204, 459);
	public static Point brazil=new Point(284, 459);
	public static Point argentina=new Point(245, 559);
	
	/**
	 * Europe
	 */
	public static Point iceland=new Point(413,136);
	public static Point gbr=new Point(403,220);
	public static Point ne=new Point(487,198);
	public static Point weu=new Point(423,320);
	public static Point seu=new Point(511,296);
	public static Point neu=new Point(488,192);
	public static Point ukraine=new Point(586,175);
	public static Point scandinavia=new Point(522,108);
	
	
	/**
	 * Africa
	 */
	public static Point madagascar=new Point(647,605);
	public static Point saf=new Point(537,616);
	public static Point congo=new Point(528,522);
	public static Point eaf=new Point(593,490);
	public static Point egypt=new Point(526,404);
	public static Point naf=new Point(454,443);
	
	/**
	 * Asia
	 */
	public static Point mde=new Point(609,343);
	public static Point afgh=new Point(675,254);
	public static Point ural=new Point(693,168);
	public static Point siberia=new Point(746,111);
	public static Point yakutsk=new Point(822,83);
	public static Point kamchatka=new Point(904,81);
	public static Point japan=new Point(925,248);
	public static Point irkutsk=new Point(815,169);
	public static Point mongolia=new Point(818,237);
	public static Point china=new Point(791,307);
	public static Point slam=new Point(818,389);
	public static Point india=new Point(727,359);
	
	
	/**
	 * Australia
	 */
	public static Point indonesia=new Point(827,508);
	public static Point ng=new Point(921,485);
	public static Point waus=new Point(867,569);
	public static Point eaus=new Point(958,594);
	private void construct(){
		allPoints.put("Alaska", alaska);
		allPoints.put("North West Territory", nwt);
		allPoints.put("Alberta", alberta);
		allPoints.put("Ontario", ontario);
		allPoints.put("Quebec", quebec);
		allPoints.put("Western United States", wus);
		allPoints.put("Eastern United States", eus);
		allPoints.put("Central America", ca);
		allPoints.put("GreenLand", greenland);
		
		allPoints.put("Venezuela", venezuela);
		allPoints.put("Brazil", brazil);
		allPoints.put("Peru", peru);
		allPoints.put("Argentina", argentina);
		
		allPoints.put("Iceland", iceland);
		allPoints.put("Great Britain", gbr);
		allPoints.put("Western Europe", weu);
		allPoints.put("Scandinavia", scandinavia);
		allPoints.put("Northern Europe", neu);
		allPoints.put("Southern Europe", seu);
		allPoints.put("Ukraine", ukraine);
		
		allPoints.put("North Africa", naf);
		allPoints.put("Egypt", egypt);
		allPoints.put("East Africa", eaf);
		allPoints.put("South Africa", saf);
		allPoints.put("Congo", congo);
		allPoints.put("Madagascar", madagascar);
		
		allPoints.put("China", china);
		allPoints.put("Ural", ural);
		allPoints.put("Afghanistan", afgh);
		allPoints.put("Middle East", mde);
		allPoints.put("India", india);
		allPoints.put("Slam", slam);
		allPoints.put("Mongolia", mongolia);
		allPoints.put("Irkutsk", irkutsk);
		allPoints.put("Yakutsk", yakutsk);
		allPoints.put("Kamchatka", kamchatka);
		allPoints.put("Japan", japan);
		allPoints.put("Siberia", siberia);
		
		allPoints.put("Indonesia",indonesia);
		allPoints.put("New Guniea", ng);
		allPoints.put("Eastern Australia", eaus);
		allPoints.put("Western Australia", waus);
	}
	
}