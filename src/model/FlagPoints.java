package model;

import java.awt.Point;
import java.util.HashMap;

public class FlagPoints {
	private HashMap<String,Point> allPoints;
	public FlagPoints(){
		allPoints=new HashMap();
		construct();
		//System.out.println("builded hash map:"+allPoints);
	}

	
	public String getName(Point point){
		 for (String o : allPoints.keySet()) {
		      if (allPoints.get(o).equals(point)) {
		        return o;
		      }
		    }
		 return null;
	}
	
	
	
	
	public Point getPoint(String name){
		//System.out.println("-----------------------this is flag point "+ name );
		//System.out.println(allPoints.get(name));
		return allPoints.get(name);
	}
	/**
	 * North America
	 */
	public static Point alaska=new Point(53, 79);
	public static Point nwt=new Point(137, 82);
	public static Point alberta=new Point(140, 137);
	public static Point wus=new Point(146, 204);
	public static Point eus=new Point(220,237);
	public static Point ontario=new Point(223,183);
	public static Point quebec=new Point(271,142);
	public static Point greenland=new Point(339, 38);
	public static Point ca=new Point(171,363);
	
	
	
	/**
	 * South America
	 */
	
	public static Point venezuela=new Point(220, 368);
	public static Point peru=new Point(263, 493);
	public static Point brazil=new Point(284, 415);
	public static Point argentina=new Point(253, 524);
	
	
	/**
	 * Europe
	 */
	public static Point iceland=new Point(428,121);
	public static Point gbr=new Point(403,181);
	public static Point ne=new Point(471,250);
	public static Point weu=new Point(423,272);
	public static Point seu=new Point(532,261);
	public static Point neu=new Point(477,251);
	public static Point ukraine=new Point(586,131);
	public static Point scandinavia=new Point(498,159);
	
	
	/**
	 * Africa
	 */
	public static Point naf=new Point(454,393);
	public static Point madagascar=new Point(630,595);
	public static Point saf=new Point(540,676);
	public static Point congo=new Point(539,489);
	public static Point eaf=new Point(572,440);
	public static Point egypt=new Point(543,380);
	
	
	
	/**
	 * Asia
	 */
	public static Point mde=new Point(644,414);
	public static Point afgh=new Point(673,224);
	public static Point ural=new Point(690,129);
	public static Point siberia=new Point(746,65);
	public static Point yakutsk=new Point(838,57);
	public static Point kamchatka=new Point(858,116);
	public static Point japan=new Point(933,212);
	public static Point irkutsk=new Point(811,138);
	public static Point mongolia=new Point(845,203);
	public static Point china=new Point(759,268);
	public static Point slam=new Point(800,362);
	public static Point india=new Point(708,325);
	
	
	/**
	 * Australia
	 */
	public static Point indonesia=new Point(839,476);
	public static Point ng=new Point(918,455);
	public static Point waus=new Point(860,630);
	public static Point eaus=new Point(914,544);
	
	
	private void construct() {
		// TODO Auto-generated method stub
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
	
	
	
	