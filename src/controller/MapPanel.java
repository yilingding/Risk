package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;

import model.ArmyPoints;
import model.Card;
import model.ComputerPlayer;
import model.Country;
import model.CountryName;
import model.FlagPoints;
import model.Map;
import model.Player;
import model.PlayerList;
import model.RiskGame;
import strategy.ExpertAI;
import strategy.IntermediateAI;
import strategy.SimpleAI;

public class MapPanel extends JPanel implements Serializable, OurObserver {

	private RiskGame theGame;
	private Map map;
	private ArrayList<Point> army;
	private HashMap<Point, String> armyColorMap;
	private ArrayList<Point> armyNumber;
	private HashMap<Point, Integer> armyNumberColorMap;
	private PlayerList players;
	private FlagPoints armyNumberPointes;
	private int belong;
	private int numberOfPlayers;
	private JButton secondStage1;
	private Image mapImg;
	private Image playerImg;
	private ArmyPoints choose;
	private Player currPlayer;
	private Player nextPlayer;
	private int numberOfClicked;
	private int numberOfClicked2;
	private int stageDeployStarts;
	private int playerNum;
	private int width;
	private int height;
	private JButton start;
	private JButton changeStrategy;
	private JButton countriesOwned;
	public JButton cardInformation;
	private PointListener listentopoint;
	public int readData;
	private int allComputerPlayer;

	public MapPanel(int w, int h, RiskGame game, int readData) {

		theGame = game;
		map = theGame.getMap();
		players = theGame.getPlayerList();
		if(players.getNumberOfAI()==6){
			this.allComputerPlayer=1;
		
		}else{
			this.allComputerPlayer=0;
		}
		 numberOfClicked=0;
		 numberOfClicked2=0;
		numberOfPlayers = players.getPlayerSize();
		army = new ArrayList<Point>();
		armyColorMap = new HashMap<>();
		armyNumber = new ArrayList<Point>();
		armyNumberColorMap = new HashMap<>();
		this.setOpaque(true);
		this.readData = readData;
		belong = -1;
		width = w;
		height = h - 20;
		playerNum = 0;
		stageDeployStarts = 0;
		start = new JButton("Start Game");
		changeStrategy = new JButton();
		countriesOwned = new JButton();
		changeStrategy.setText("Change Strategy");
		countriesOwned.setText("Countries Owned");
		secondStage1 = new JButton();
		secondStage1.setText("Second Stage");
		secondStage1.setEnabled(false);
		cardInformation = new JButton();
		cardInformation.setText("Card Information");
		//cardInformation.setEnabled(false);
		choose = new ArmyPoints();
		
		armyNumberPointes = new FlagPoints();
		this.add(start);
		this.add(countriesOwned);
		this.add(secondStage1);
		this.add(changeStrategy);
		this.add(cardInformation);
		secondStage1.addActionListener(new secondStage1Listener(numberOfClicked2));
		start.addActionListener(new ButtonListener());
		countriesOwned.addActionListener(new CountryOwnedListener());
		ArmiesListener theListener = new ArmiesListener();
		cardInformation.addActionListener(theListener);
		changeStrategy.addActionListener(new InformationListener());
		try {
			mapImg = ImageIO.read(new File("image/Map.jpg"));

		} catch (Exception e) {
		}
		if (this.readData == 1) {
			addThePoint();
			repaint();
			if (theGame.getStage().compareTo("Claim Country") == 0) {
				numberOfClicked = (theGame.returnCurrentPlayer() + 1) % theGame.getPlayerList().getPlayerSize();
				startGame();
			} else if (theGame.getStage().compareTo("Place Army") == 0) {
				listentopoint = new PointListener();
				addMouseListener(listentopoint);
				numberOfClicked = (theGame.returnCurrentPlayer()) % theGame.getPlayerList().getPlayerSize();
				Player player342234 = players.getPlayer(theGame.returnCurrentPlayer());
				if (players.isHuman(player342234)) {

					nextPlayer = players.getPlayer(numberOfClicked);
					if (player342234.getThisTurnNewArmies() != 0) {
						playerNum = (theGame.returnCurrentPlayer()) % theGame.getPlayerList().getPlayerSize();
						repaint();
						JOptionPane.showMessageDialog(null,
								"For this player, you haven't finished placing the armies,\n so please continue"
										+ numberOfClicked);
					} else {
						playerNum = (theGame.returnCurrentPlayer() + 1) % theGame.getPlayerList().getPlayerSize();

					}
				} else {
					update();
				}
			} else if (theGame.getStage().compareTo("Deploying Armies") == 0) {
				// TODO 01: read data
				numberOfClicked2 = theGame.returnCurrentPlayer();

			} else if (theGame.getStage().compareTo("Attacking the opposition") == 0) {
				// TODO 02: read data
				numberOfClicked2 = theGame.returnCurrentPlayer();

				JOptionPane.showMessageDialog(null, "haven't finished yet");
			} else if (theGame.getStage().compareTo("Fortifying the territories held") == 0) {
				// TODO 03: read data
				numberOfClicked2 = theGame.returnCurrentPlayer();

				JOptionPane.showMessageDialog(null, "haven't finished yet");
			}
		}
		if(players.getNumberOfAI()==6){
			startGame();
			
				}
		
	}

	private void startGame() {
		listentopoint = new PointListener();
		addMouseListener(listentopoint);
		
		nextPlayer = players.getPlayer(numberOfClicked);
		System.out.println("-------1" + players.countriesToString());
		theGame.setStage("Claim Country");

		// this part was use to write method of display cards, delete later
	

		repaint();

		if (players.isHuman(players.getPlayer(this.numberOfClicked))) {
			nextPlayer = players.getPlayer(numberOfClicked);
			repaint();
		} else {
			update();
		}

	}

	public Dimension getPreferedSize() {
		return (new Dimension(width, height));
	}

	public void updateArmies() {
		String[] names = new String[42];
		CountryName countriesNames = new CountryName();

		names = countriesNames.returnCountryName();
		for (int i = 0; i < 42; i++) {
			Country theCountry = map.getCountry(names[i]);
			JLabel armyNumber = new JLabel();

			armyNumber.setLocation(armyNumberPointes.getPoint(theCountry.getName()));
			armyNumber.setText("" + theCountry.getArmies());
		}

	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(start)) {
				startGame();
				
			}
		}

	}

	private class CountryOwnedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, (String) players.countriesToString());
		}

	}

	private class InformationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFrame frame = new JFrame("Change the strategy for computer player.");
			int numberOfCoumputerPlayer = theGame.getPlayerList().getNumberOfAI();
			String[] num = new String[numberOfCoumputerPlayer];
			int h = 0;
			HashMap<Integer, Integer> coumputerPlayerInPlayer = new HashMap<Integer, Integer>();
			System.out.println("-------2" + theGame.getPlayerList().getPlayerSize() + "   ----------   "
					+ numberOfCoumputerPlayer);
			for (int i = 0; i < theGame.getPlayerList().getPlayerSize(); i++) {
				if (theGame.getPlayerList().getPlayers().get(i).getClass().equals(ComputerPlayer.class)) {
					coumputerPlayerInPlayer.put(i, h);
					num[h] = "" + i;
					h++;
				}
			}
			String com;

			String hu = (String) JOptionPane.showInputDialog(frame,
					"Which computer player's strategy do you want to change?",
					"Change the strategy for computer player.", JOptionPane.QUESTION_MESSAGE, null, num, num[0]);
			if (hu == null) {
				JOptionPane.showMessageDialog(null, "You haven't changed any computer player's strategy!");
				return;
			}
			int thePlayernumber = Integer.valueOf(hu);
			int theComputerNumber = coumputerPlayerInPlayer.get(thePlayernumber);
			frame = new JFrame("Set Strategy for the computer player");
			String[] strategy = { "Simple", "Intermediate", "Expert" };

			String stra = (String) JOptionPane
					.showInputDialog(frame,
							"What Strategy do you want for player " + hu + "(same as computer player"
									+ theComputerNumber + ") ?",
							"Strategy", JOptionPane.QUESTION_MESSAGE, null, strategy, strategy[0]);
			if (stra == null) {
				JOptionPane.showMessageDialog(null, "You haven't changed any computer player's strategy!");
				return;
			}
			if (stra.equals("Simple")) {
				theGame.getPlayerList().getAI().get(theComputerNumber).setAI(new SimpleAI());
				JOptionPane.showMessageDialog(null, "You have changed player " + hu + "(same as computer player"
						+ theComputerNumber + ") 's strategy  to  Simple");

			} else if (stra.equals("Intermediate")) {
				theGame.getPlayerList().getAI().get(theComputerNumber).setAI(new IntermediateAI());
				JOptionPane.showMessageDialog(null, "You have changed player " + hu + "(same as computer player"
						+ theComputerNumber + ") 's strategy  to  Intermediate");
			} else {
				theGame.getPlayerList().getAI().get(theComputerNumber).setAI(new ExpertAI());
				JOptionPane.showMessageDialog(null, "You have changed player " + hu + "'s (same as computer player"
						+ theComputerNumber + ") to strategy Expert");
			}
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mapImg, 0, 0, this);
		Graphics2D g2 = (Graphics2D) g;
		for (Point aPoint : army) {
			String color = armyColorMap.get(aPoint);
			try {
				playerImg = ImageIO.read(new File("image/" + color + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2.drawImage(playerImg, aPoint.x, aPoint.y, Color.white, null);
		}

		for (Point point : armyNumber) {
			// System.out.println(point + "===================");
			int thearmyNumber = armyNumberColorMap.get(point);
			String countryNamehaha = armyNumberPointes.getName(point);
			if (countryNamehaha != null) {
				Country countryhaha = map.getCountry(countryNamehaha);
				thearmyNumber = countryhaha.getArmies();
				// addThePoint();
				armyNumberColorMap.put(point, thearmyNumber);
				Font myFont = new Font("default", Font.BOLD, 16);
				g2.setFont(myFont);
				g2.setColor(Color.RED);
				g2.drawString("" + thearmyNumber, point.x, point.y);
			}
		}
		if (stageDeployStarts == 0 && theGame.getStage() != null && nextPlayer != null) {
			Font myFont = new Font("default", Font.BOLD, 12);
			g2.setFont(myFont);
			g2.setColor(Color.black);
			g2.drawString("Stage: " + theGame.getStage(), 30, 530);
			g2.drawString("Player: " + nextPlayer.getName(), 30, 570);
			g2.drawString("Player Color: " + nextPlayer.getColor(), 30, 610);
			g2.drawString("Player Cards: " + nextPlayer.handToString(), 30, 650);
		} else if (stageDeployStarts == 1) {
			ImageIcon imageIcon = new ImageIcon("image/giphy.gif");
			// imageIcon.paintIcon(this, g2, 30, 500);
			// JLabel imageLbl = new JLabel(myImgIcon);
			// this.add(imageLbl);

			Font myFont = new Font("default", Font.BOLD, 12);
			g2.setFont(myFont);
			g2.setColor(Color.black);
			g2.drawString("Stage: " + theGame.getStage(), 30, 530);
			g2.drawString("Player: " + currPlayer.getName(), 30, 570);
			g2.drawString("Player Color: " + currPlayer.getColor(), 30, 610);
			g2.drawString("Player Cards: " + currPlayer.handToString(), 30, 650);
		}
	}

	public void addThePoint() {
		for (int i = 0; i < players.getPlayerSize(); i++) {
			for (int k = 0; k < players.getPlayer(i).getCountries().size(); k++) {
				Point x = choose.getPoint(players.getPlayer(i).getCountries().get(k).getName());

				
				army.add(x);
				armyColorMap.put(x, players.getColor(i));
				Point y = armyNumberPointes.getPoint(players.getPlayer(i).getCountries().get(k).getName());
				//JOptionPane.showMessageDialog(null,x+"  "+y);
				armyNumber.add(y);


				/*if (players.getPlayer(i).getCountries().get(k).getArmies() != armyNumberColorMap.get(y)) {
					JOptionPane.showMessageDialog(null,
							i + "player 's  " + players.getPlayer(i).getCountries().get(k) + "changes from "
									+ armyNumberColorMap.get(y) + " to "
									+ players.getPlayer(i).getCountries().get(k).getArmies());
				}*/
				armyNumberColorMap.put(y, players.getPlayer(i).getCountries().get(k).getArmies());
			}
		}

	}

	class PointListener implements MouseListener {
		private int selectCountryStage; // 0 ok to select, 1 no
		private int secondStage1Number; //

		private Point armyPoint = null;
		private Point armyNumberPoint = null;

		public PointListener() {
			selectCountryStage = 0;
			secondStage1Number = 1;
		}

		/*
		 * public ArrayList<Polygon> returnPolygons() { return polygons; }
		 */

		@Override
		public void mouseClicked(MouseEvent e) {
			/**
			 * North America
			 */
			Country country = null;
			if (Polygons.alaska.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Alaska");
				armyPoint = ArmyPoints.alaska;
				armyNumberPoint = FlagPoints.alaska;
				System.out.println("Alaska was clicked!");
				System.out.println(country.getNeighbour().toString());

			}

			if (Polygons.nwt.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("North West Territory");
				armyPoint = ArmyPoints.nwt;
				armyNumberPoint = FlagPoints.nwt;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Northwest Territory was clicked!");
			}

			if (Polygons.greenland.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("GreenLand");
				armyPoint = ArmyPoints.greenland;
				armyNumberPoint = FlagPoints.greenland;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Greenland was clicked!");
			}

			if (Polygons.alberta.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Alberta");
				armyPoint = ArmyPoints.alberta;
				armyNumberPoint = FlagPoints.alberta;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Alberta was clicked!");
			}

			if (Polygons.ontario.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Ontario");
				armyPoint = ArmyPoints.ontario;
				armyNumberPoint = FlagPoints.ontario;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Ontario was clicked!");
			}

			if (Polygons.quebec.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Quebec");
				armyPoint = ArmyPoints.quebec;
				armyNumberPoint = FlagPoints.quebec;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Quebec was clicked!");
			}

			if (Polygons.wus.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Western United States");
				armyPoint = ArmyPoints.wus;
				armyNumberPoint = FlagPoints.wus;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Western United States was clicked!");
			}

			if (Polygons.eus.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Eastern United States");
				armyNumberPoint = FlagPoints.eus;
				armyPoint = ArmyPoints.eus;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Eastern United States was clicked!");
			}

			if (Polygons.ca.contains(e.getPoint())) {
				country = map.getNorthAmerica().getCountry("Central America");
				armyNumberPoint = FlagPoints.ca;
				armyPoint = ArmyPoints.ca;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Central America was clicked!");
			}

			/**
			 * South America
			 */

			if (Polygons.venezuela.contains(e.getPoint())) {
				country = map.getSouthAmerica().getCountry("Venezuela");
				armyPoint = ArmyPoints.venezuela;
				armyNumberPoint = FlagPoints.venezuela;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Venezuela was clicked!");
			}

			if (Polygons.peru.contains(e.getPoint())) {
				country = map.getSouthAmerica().getCountry("Peru");
				armyPoint = ArmyPoints.peru;
				armyNumberPoint = FlagPoints.peru;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Peru was clicked!");
			}

			if (Polygons.brazil.contains(e.getPoint())) {
				country = map.getSouthAmerica().getCountry("Brazil");
				armyPoint = ArmyPoints.brazil;
				armyNumberPoint = FlagPoints.brazil;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Brazil was clicked!");
			}

			if (Polygons.argentina.contains(e.getPoint())) {
				country = map.getSouthAmerica().getCountry("Argentina");
				armyPoint = ArmyPoints.argentina;
				armyNumberPoint = FlagPoints.argentina;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Argentina was clicked!");
			}

			/**
			 * Europe
			 */

			if (Polygons.iceland.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Iceland");
				armyPoint = ArmyPoints.iceland;
				armyNumberPoint = FlagPoints.iceland;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Iceland was clicked!");
			}

			if (Polygons.gbr.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Great Britain");
				armyPoint = ArmyPoints.gbr;
				armyNumberPoint = FlagPoints.gbr;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Great Britain was clicked!");
			}

			if (Polygons.weu.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Western Europe");
				armyPoint = ArmyPoints.weu;
				armyNumberPoint = FlagPoints.weu;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Western Europe was clicked!");
			}

			if (Polygons.seu.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Southern Europe");
				armyPoint = ArmyPoints.seu;
				armyNumberPoint = FlagPoints.seu;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Southern Europe was clicked!");
			}

			if (Polygons.neu.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Northern Europe");
				armyPoint = ArmyPoints.neu;
				armyNumberPoint = FlagPoints.neu;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Northern Europe was clicked!");
			}

			if (Polygons.ukraine.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Ukraine");
				armyPoint = ArmyPoints.ukraine;
				armyNumberPoint = FlagPoints.ukraine;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Ukraine was clicked!");
			}

			if (Polygons.scandinavia.contains(e.getPoint())) {
				country = map.getEurope().getCountry("Scandinavia");
				armyPoint = ArmyPoints.scandinavia;
				armyNumberPoint = FlagPoints.scandinavia;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Scandinavia was clicked!");
			}

			/**
			 * Africa
			 */

			if (Polygons.madagascar.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("Madagascar");
				armyPoint = ArmyPoints.madagascar;
				armyNumberPoint = FlagPoints.madagascar;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Madagascar was clicked!");
			}

			if (Polygons.saf.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("South Africa");
				armyPoint = ArmyPoints.saf;
				armyNumberPoint = FlagPoints.saf;
				System.out.println(country.getNeighbour().toString());
				System.out.println("South Africa was clicked!");
			}

			if (Polygons.congo.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("Congo");
				armyPoint = ArmyPoints.congo;
				armyNumberPoint = FlagPoints.congo;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Congo was clicked!");
			}

			if (Polygons.eaf.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("East Africa");
				armyPoint = ArmyPoints.eaf;
				armyNumberPoint = FlagPoints.eaf;
				System.out.println(country.getNeighbour().toString());
				System.out.println("East Africa was clicked!");
			}

			if (Polygons.naf.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("North Africa");
				armyNumberPoint = FlagPoints.naf;
				armyPoint = ArmyPoints.naf;
				System.out.println(country.getNeighbour().toString());
				System.out.println("North Africa was clicked!");
			}

			if (Polygons.egypt.contains(e.getPoint())) {
				country = map.getAfrica().getCountry("Egypt");
				armyNumberPoint = FlagPoints.egypt;
				armyPoint = ArmyPoints.egypt;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Egypt was clicked!");
			}

			/**
			 * Australia
			 */

			if (Polygons.indonesia.contains(e.getPoint())) {
				country = map.getAustralia().getCountry("Indonesia");
				armyPoint = ArmyPoints.indonesia;
				armyNumberPoint = FlagPoints.indonesia;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Indonesia was clicked!");
			}

			if (Polygons.ng.contains(e.getPoint())) {
				country = map.getAustralia().getCountry("New Guniea");
				armyPoint = ArmyPoints.ng;
				armyNumberPoint = FlagPoints.ng;
				System.out.println(country.getNeighbour().toString());
				System.out.println("New Guniea was clicked!");
			}

			if (Polygons.waus.contains(e.getPoint())) {
				country = map.getAustralia().getCountry("Western Australia");
				armyNumberPoint = FlagPoints.waus;
				armyPoint = ArmyPoints.waus;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Western Australia was clicked!");
			}

			if (Polygons.eaus.contains(e.getPoint())) {
				country = map.getAustralia().getCountry("Eastern Australia");
				armyPoint = ArmyPoints.eaus;
				armyNumberPoint = FlagPoints.eaus;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Eastern Australia was clicked!");
			}

			/**
			 * Asia
			 */

			if (Polygons.mde.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Middle East");
				armyPoint = ArmyPoints.mde;
				armyNumberPoint = FlagPoints.mde;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Middle East was clicked!");
			}

			if (Polygons.afgh.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Afghanistan");
				armyPoint = ArmyPoints.afgh;
				armyNumberPoint = FlagPoints.afgh;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Afghanistan was clicked!");
			}

			if (Polygons.ural.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Ural");
				armyPoint = ArmyPoints.ural;
				armyNumberPoint = FlagPoints.ural;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Ural was clicked!");
			}

			if (Polygons.siberia.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Siberia");
				armyPoint = ArmyPoints.siberia;
				armyNumberPoint = FlagPoints.siberia;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Siberia was clicked!");
			}

			if (Polygons.yakutsk.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Yakutsk");
				armyPoint = ArmyPoints.yakutsk;
				armyNumberPoint = FlagPoints.yakutsk;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Yakutsk was clicked!");
			}

			if (Polygons.kamchatka.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Kamchatka");
				armyPoint = ArmyPoints.kamchatka;
				armyNumberPoint = FlagPoints.kamchatka;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Kamchatka was clicked!");
			}

			if (Polygons.irkutsk.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Irkutsk");
				armyNumberPoint = FlagPoints.irkutsk;
				armyPoint = ArmyPoints.irkutsk;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Irkutsk was clicked!");
			}

			if (Polygons.mongolia.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Mongolia");
				armyNumberPoint = FlagPoints.mongolia;
				armyPoint = ArmyPoints.mongolia;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Mongolia was clicked!");
			}

			if (Polygons.japan.contains(e.getPoint())) {
				country = map.getAsia().getCountry("Japan");
				armyNumberPoint = FlagPoints.japan;
				armyPoint = ArmyPoints.japan;
				System.out.println(country.getNeighbour().toString());
				System.out.println("Japan was clicked!");
			}

			if (Polygons.china.contains(e.getPoint())) {
				country = map.getAsia().getCountry("China");
				armyNumberPoint = FlagPoints.china;
				armyPoint = ArmyPoints.china;
				System.out.println(country.getNeighbour().toString());
				System.out.println("China was clicked!");
			}

			if (Polygons.slam.contains(e.getPoint())) {
				armyPoint = ArmyPoints.slam;
				armyNumberPoint = FlagPoints.slam;
				country = map.getAsia().getCountry("Slam");
				System.out.println(country.getNeighbour().toString());
				System.out.println("Slam was clicked!");
			}

			if (Polygons.india.contains(e.getPoint())) {
				armyPoint = ArmyPoints.india;
				armyNumberPoint = FlagPoints.india;
				country = map.getAsia().getCountry("India");
				System.out.println(country.getNeighbour().toString());
				System.out.println("India was clicked!");
			}

			if (theGame.getStage().equals("Claim Country")) {
				if (army.size() <= 42) {
					if (armyPoint == null) {
						JOptionPane.showMessageDialog(null, "Please select a country.");
					} else if (army.contains(armyPoint)) {
						JOptionPane.showMessageDialog(null, "This country has been assigned.");
					} else {

						belong = numberOfClicked % (numberOfPlayers);
						System.out.println(belong + "---------===============" + numberOfClicked);
						theGame.setCurrentPlayer(belong);
						currPlayer = players.getPlayer(belong);
						theGame.addCountryToPlayer(currPlayer, country);
						// theGame.passedCountryFromGUI(country);

						army.add(armyPoint);
						armyColorMap.put(armyPoint, players.getColor(belong));
						armyNumber.add(armyNumberPoint);

						armyNumberColorMap.put(armyNumberPoint, country.getArmies());
						numberOfClicked++;

						nextPlayer = players.getPlayer(numberOfClicked % (numberOfPlayers));

						if (!players.isHuman(nextPlayer)) {
							update();
						}

					}

					if (numberOfClicked == 42) {
						theGame.setArmyThisTurn(theGame.getInitialArmy());
						selectCountryStage = 1;
						theGame.setStage("Place Army");
						JOptionPane.showMessageDialog(null, "Enter place army stage.");
						nextPlayer = players.getPlayer(0);
						if (!players.isHuman(nextPlayer))
							update();
					}
					armyPoint = null;
				}
			} else if (theGame.getStage().equals("Place Army")) {
				// players.countriesToString();
				if (country == null) {
					JOptionPane.showMessageDialog(null, "Please select the country you owned.");
					return;
				}
				currPlayer = players.getPlayer(playerNum);
				theGame.setCurrentPlayer(playerNum);
				if (currPlayer.hasThisCountry(country.getName())) {
					System.out.println("you have armies: " + currPlayer.getThisTurnNewArmies());
					if (currPlayer.getThisTurnNewArmies() != 0) {
						country.addArmies(1);
						currPlayer.minusThisTurnNewArmies(1);

					} else {
						playerNum++;

						JOptionPane.showMessageDialog(null, "You have finished your initial deploying, next player.");

						if (playerNum == players.getPlayerSize()) {
							secondStage1Number = 0;
							stageDeployStarts = 1;
							nextPlayer = players.getPlayer(0);
							JOptionPane.showMessageDialog(null,
									"You have finished the first stage, you can move to the next stage."
											);
							start.setEnabled(false);
							secondStage1.setEnabled(true);
							removeMouseListener(listentopoint);
							repaint();
							if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
								update1();
							} else {
								currPlayer = players.getPlayer(numberOfClicked2 % (numberOfPlayers));
								decisionMade(numberOfClicked2 % (numberOfPlayers));
								
							}
						} else {
							nextPlayer = players.getPlayer(playerNum);
							if (!players.isHuman(nextPlayer)) {
								update();
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select the country you have.");
				}

			}

			repaint();

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

	public void update1() {
		// TODO Auto-generated method stub
		/*
		 * belong = numberOfClicked2 % numberOfPlayers; numberOfClicked2++;
		 * currPlayer = players.getPlayer(belong);
		 * theGame.deployArmies(currPlayer); ((ComputerPlayer)
		 * currPlayer).attackingOpp(map, currPlayer);
		 */

		if (theGame.gameOver() != null) {
			JOptionPane.showMessageDialog(null, "Game over " + theGame.gameOver().getName() + "win");
		} else {
			belong = numberOfClicked2 % numberOfPlayers;
			//JOptionPane.showMessageDialog(null, "It's player's turn--------." + belong);
			theGame.setCurrentPlayer(belong);

			numberOfClicked2++;
			// nextPlayer = players.getPlayer(numberOfClicked2 %
			// (numberOfPlayers));
			currPlayer = players.getPlayer(belong);
			//JOptionPane.showMessageDialog(null, "Deploying starts" + belong);
			theGame.setStage("Deploy Armies");

			theGame.deployArmies(currPlayer);
			addThePoint();
			repaint();

			//JOptionPane.showMessageDialog(null, "For this computer player deploying armies end");
			//JOptionPane.showMessageDialog(null, "Attacking starts");
			theGame.setStage("Attacking the opposition");
			theGame.attackingOppositionAI((ComputerPlayer) currPlayer);
			addThePoint();
			repaint();
			//JOptionPane.showMessageDialog(null, "For this computer player attacking the opposition ends");
			theGame.setStage("Fortifying the territories held");
			//JOptionPane.showMessageDialog(null, "Fortifying starts");
			theGame.fortifyingTerritoriesAI((ComputerPlayer) currPlayer);
			addThePoint();
			repaint();
			//JOptionPane.showMessageDialog(null, "For this computer player fortifying the territories held");

			addThePoint();
			repaint();

			if (!players.isHuman(players.getPlayer(numberOfClicked2 % numberOfPlayers))) {
				update1();
			} else {
				currPlayer = players.getPlayer(numberOfClicked2 % (numberOfPlayers));
				//JOptionPane.showMessageDialog(null, "whhhhhhhhh------------");
				decisionMade(numberOfClicked2 % (numberOfPlayers));
				//JOptionPane.showMessageDialog(null,"Human Player's turn, please click the button");
			}
		}

	}

	@Override
	public void update() {
		if (theGame.getStage().equals("Claim Country") && numberOfClicked <= 42) {
			if (numberOfClicked == 42) {
				theGame.setArmyThisTurn(theGame.getInitialArmy());
				numberOfClicked++;
				theGame.setStage("Place Army");
				JOptionPane.showMessageDialog(null, "Enter place army stage.");
				nextPlayer = players.getPlayer(0);
				if (!players.isHuman(nextPlayer))
					update();
			} else {
				belong = numberOfClicked % numberOfPlayers;
				System.out.println("numberOfClicked is:" + numberOfClicked);
				System.out.println("belong in computer player is:" + belong);
				theGame.setCurrentPlayer(belong);
				currPlayer = players.getPlayer(belong);
				Country c = ((ComputerPlayer) currPlayer).placeInitialArmy(map);

				if (c == null)
					return;

				System.out.println("added country " + c);
				Point x = choose.getPoint(c.getName());
				army.add(x);
				armyColorMap.put(x, players.getColor(belong));
				Point y = armyNumberPointes.getPoint(c.getName());
				armyNumber.add(y);
				armyNumberColorMap.put(y, c.getArmies());
				numberOfClicked++;
				nextPlayer = players.getPlayer(numberOfClicked % (numberOfPlayers));
				repaint();

				if (!players.isHuman(nextPlayer)) {
					update();
				}
			}
		} else if (theGame.getStage().equals("Place Army")) {
			currPlayer = players.getPlayer(playerNum);
			theGame.setCurrentPlayer(playerNum);
			for (int i = 0; i < theGame.getInitialArmy(); i++) {
				System.out.println(theGame.getInitialArmy() + "-=-=-=-==" + playerNum);
				((ComputerPlayer) currPlayer).fortifyInitialArmy(map);
			}
			playerNum++;
			if (playerNum == players.getPlayerSize()) {
				nextPlayer = players.getPlayer(0);
				stageDeployStarts = 1;
				JOptionPane.showMessageDialog(null,
						"You have finished the first stage, you can move to the next stage." + numberOfClicked2);
				secondStage1.setEnabled(true);
				//cardInformation.setEnabled(true);
				start.setEnabled(false);
				this.removeMouseListener(listentopoint);
				nextPlayer = players.getPlayer(0);
				if (!players.isHuman(players.getPlayer(0))) {
					update1();
					// numberOfClicked2++;
				} else {
					currPlayer = players.getPlayer(numberOfClicked2 % (numberOfPlayers));
					decisionMade(numberOfClicked2 % (numberOfPlayers));
					//JOptionPane.showMessageDialog(null,"Human Player's turn, please click the button");
				}

			} else {
				nextPlayer = players.getPlayer(playerNum);
				repaint();
				if (!players.isHuman(nextPlayer)) {
					update();
				}
			}
		}
		repaint();
	}

	public void decisionMade(int belong) {
		theGame.setCurrentPlayer(belong);
		//JOptionPane.showMessageDialog(null, "------" + belong);
		theGame.setStage("Check card");
		currPlayer = players.getPlayer(belong);
		if(currPlayer.handSize()==0){
		JOptionPane.showMessageDialog(null, "As for now, you don't have any cards, which means you will just to the deploying stage.");
			theGame.humanHarvestArmise(currPlayer, null);
			deployingArmies(belong);
		}else if(currPlayer.handSize()>0){
			checkArmies(belong);
		}
		
	}

	private CardGUI cardpanel;

	public void checkArmies(int belong) {
		theGame.setCurrentPlayer(belong);
		currPlayer = players.getPlayer(belong);
		//JOptionPane.showMessageDialog(null, "111------" + belong);
		//JOptionPane.showMessageDialog(null, "1234567" + currPlayer.getName());
		cardpanel = new CardGUI(currPlayer);
		cardpanel.setVisible(true);

	}

	public void assignCards(int belong) {

		JFrame frame = new JFrame("Assign Cards");
		//JOptionPane.showMessageDialog(null, "2------" + belong);
		currPlayer = players.getPlayer(belong);
		if (!currPlayer.isTurnInable()) {
			JOptionPane.showMessageDialog(null, "You are not qualified to turn in cards now.");
			frame.dispose();
			cardpanel.dispose();
			theGame.humanHarvestArmise(currPlayer, null);
			deployingArmies(belong);
		} else {
			ArrayList<Card> hand = players.getPlayer(belong).getHand();
			JOptionPane.showMessageDialog(null, "currPlayer is" + currPlayer.getColor());
			String[] part123 = new String[hand.size()];// names.returnCountryName();
			for (int i = 0; i < hand.size(); i++) {
				part123[i] = hand.get(i).toString();
			}

			JPanel asscard = new JPanel();
			asscard.setPreferredSize(new Dimension(400, 300));
			JList list = new JList(part123);
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			asscard.add(new JLabel("Please select the three cards you want to turn in:"));
			asscard.add(new JScrollPane(list));
			int[] cards = new int[0];
			ArrayList<Card> ThreeCards = new ArrayList<Card>();
			while (cards.length != 3) {
				if (cards.length != 0) {
					JOptionPane.showMessageDialog(null, "Please choose 3 cards!");
				}
				int result = JOptionPane.showConfirmDialog(null, asscard, "Choose Turn In Card ",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					cards = list.getSelectedIndices();
				}
			}
			for (int i = 0; i < cards.length; i++)
				ThreeCards.add(hand.get((cards[i])));
			if (theGame.threeCardsIsTurnInable(ThreeCards)) {
				theGame.humanHarvestArmise(currPlayer, ThreeCards);
				repaint();
			}
			frame.dispose();
			cardpanel.dispose();
			deployingArmies(belong);
		}

	}

	public void deployingArmies(int belong) {
		JFrame frame = new JFrame("Deploying Armies");
		theGame.setStage("Deploy Army");
		theGame.setCurrentPlayer(belong);
		CountryName names = new CountryName();
		Player currPlayer = theGame.getPlayerList().getPlayer(belong);
		nextPlayer = currPlayer;
		int numberArmThisTurn = currPlayer.getThisTurnNewArmies();
		JOptionPane.showMessageDialog(null, "The armies you get this turn are: " + numberArmThisTurn);
		addMouseListener(listentopoint);
		repaint();
		String[] part1 = new String[currPlayer.getCountries().size()];// names.returnCountryName();
		for (int i = 0; i < currPlayer.getCountries().size(); i++) {
			part1[i] = currPlayer.getCountries().get(i).getName();
		}
		String countryName;
		countryName = (String) JOptionPane.showInputDialog(frame, "Which country do you want to deploy Armies?",
				"Second Stage Player" + belong + " Deploying Armies", JOptionPane.QUESTION_MESSAGE, null, part1,
				part1[0]);
		while (countryName == null) {
			JOptionPane.showMessageDialog(null, "You must select a country! You can't jump it.");
			frame = new JFrame("Deploying Armies");
			names = new CountryName();
			currPlayer = theGame.getPlayerList().getPlayer(belong);
			part1 = new String[currPlayer.getCountries().size()];// names.returnCountryName();
			for (int i = 0; i < currPlayer.getCountries().size(); i++) {
				part1[i] = currPlayer.getCountries().get(i).getName();
			}

			countryName = (String) JOptionPane.showInputDialog(frame, "Which country do you want to deploy Armies?",
					"Second Stage Player" + belong + " Deploying Armies", JOptionPane.QUESTION_MESSAGE, null, part1,
					part1[0]);
		}
		// theGame.deployArmies(currPlayer);

		Country country = map.getCountryFromMap(countryName);
		String[] number = new String[numberArmThisTurn + 1];
		for (int c = 0; c <= numberArmThisTurn; c++) {
			number[c] = "" + c + "";
		}
		String numberArmies;
		numberArmies = (String) JOptionPane.showInputDialog(frame,
				"How many armies do you want to deploy to this country" + country.getName(),
				"Second Stage Player" + belong + " Deploying Armies", JOptionPane.QUESTION_MESSAGE, null, number,
				number[0]);
		while (numberArmies == null) {
			JOptionPane.showMessageDialog(null, "Please select the number of army!");
			numberArmies = (String) JOptionPane.showInputDialog(frame,
					"How many armies do you want to deploy to this country" + country.getName(),
					"Second Stage Player" + belong + " Deploying Armies", JOptionPane.QUESTION_MESSAGE, null, number,
					number[0]);

		}
		int armyNumber = Integer.parseInt(numberArmies);
		// players.getPlayer(belong).addCountry(country);
		country.addArmies(armyNumber);
		// currPlayer.minusTotalArmies(armyNumber);
		addThePoint();
		repaint();

		attackOppo(belong);
	}

	public void attackOppo(int belong) {
		theGame.setCurrentPlayer(belong);
		theGame.setStage("Attacking the opposition");
		JFrame frame = new JFrame("Attacking the opposition");
		Player currPlayer = theGame.getPlayerList().getPlayer(belong);
		String[] part1 = new String[currPlayer.getCountries().size() + 1];
		for (int i = 0; i < currPlayer.getCountries().size(); i++) {
			part1[i] = currPlayer.getCountries().get(i).getName();
		}
		part1[currPlayer.getCountries().size()] = "Omit Attacking the opposition";
		String countryName = null;
		countryName = (String) JOptionPane.showInputDialog(frame,
				"Now you own these countries, which country do you want to choose? Please make sure that you have at least 2 armies on it, so that you can attack. ",
				"Second Stage Player" + belong + "Attacking the opposition", JOptionPane.QUESTION_MESSAGE, null, part1,
				part1[0]);
		if (countryName == null) {
			JOptionPane.showMessageDialog(null,
					"You choose cancel which means you have jumped this step---Attacking the opposition");
			addThePoint();
			repaint();
			fortify(belong);
		} else if (countryName.compareTo("Omit Attacking the opposition") == 0) {
			JOptionPane.showMessageDialog(null, "You have jumped this step---Attacking the opposition!");
			addThePoint();
			repaint();
			fortify(belong);
		}

		while (countryName != null && countryName.compareTo("Omit Attacking the opposition") != 0) {

			Country country = map.getCountryFromMap(countryName);
			while (country.getArmies() <= 2) {

				JOptionPane.showMessageDialog(null, "As shown in the map, country " + country
						+ " has <= 2 armies, so you can't do this step, which means you need to make another choice");

				countryName = (String) JOptionPane.showInputDialog(frame,
						"Now you own these countries, which country do you want to choose? Please make sure that you have at least 2 armies on it, so that you can attack. ",
						"Second Stage Player" + belong + "Attacking the opposition", JOptionPane.QUESTION_MESSAGE, null,
						part1, part1[0]);
				if (countryName == null || countryName.compareTo("Omit Attacking the opposition") == 0) {
					if (countryName == null) {
						JOptionPane.showMessageDialog(null,
								"You choose cancel which means you have jumped this step---Attacking the opposition");
					} else {
						JOptionPane.showMessageDialog(null, "You have jumped this step---Attacking the opposition!");
					}
					addThePoint();
					repaint();
					fortify(belong);
				}
				country = map.getCountryFromMap(countryName);
			}

			String[] nei = new String[currPlayer.getNeighbourAndNotOwnedCountries(country).size()];
			for (int i = 0; i < currPlayer.getNeighbourAndNotOwnedCountries(country).size(); i++) {
				nei[i] = currPlayer.getNeighbourAndNotOwnedCountries(country).get(i).getName();
			}

			String neighCoun;
			neighCoun = (String) JOptionPane.showInputDialog(frame,
					"Now from" + country.getName() + "has the following neighbour countries:",
					"Second Stage Player " + belong + "Attacking the opposition", JOptionPane.QUESTION_MESSAGE, null,
					nei, nei[0]);
			if (neighCoun == null) {
				JOptionPane.showMessageDialog(null,
						"You choose cancel which means you have jumped this step---Attacking the opposition");
				addThePoint();
				repaint();
				fortify(belong);
			}
			Country neighCountry = map.getCountryFromMap(neighCoun);
			String[] dicenum = { "1", "2", "3" };
			String diceNum = null;
			int diceN = 0;
			diceNum = (String) JOptionPane.showInputDialog(frame, "How many dice do you want:", "Set Dice Number",
					JOptionPane.QUESTION_MESSAGE, null, dicenum, dicenum[0]);
			if (diceNum == null) {
				JOptionPane.showMessageDialog(null,
						"You choose cancel which means you have jumped this step---Attacking the opposition");
				addThePoint();
				repaint();
				fortify(belong);
			}
			diceN = Integer.parseInt(diceNum);
			while (currPlayer.getCountries().size() < diceN + 1) {
				diceNum = (String) JOptionPane.showInputDialog(frame, "How many dice do you want:", "Set Dice Number",
						JOptionPane.QUESTION_MESSAGE, null, dicenum, dicenum[0]);
				if (diceNum == null) {
					JOptionPane.showMessageDialog(null,
							"You choose cancel which means you have jumped this step---Attacking the opposition");
					addThePoint();
					repaint();
					fortify(belong);
				}

				diceN = Integer.parseInt(diceNum);
			}
			currPlayer.setAttackDiceAmount(diceN);

			/*
			 * String[] moveNumber = { "1", "2", "3" }; String moveNum=null; int
			 * moveN=0; moveNum= (String) JOptionPane.showInputDialog(frame,
			 * "How many dice do you want:", "Set Dice Number",
			 * JOptionPane.QUESTION_MESSAGE, null, moveNumber, moveNumber[0]);
			 * if(moveNum==null){ JOptionPane.showMessageDialog(null,
			 * "You choose cancel which means you have jumped this step---Attacking the opposition"
			 * ); fortify(belong); } moveN=Integer.parseInt(moveNum);
			 */

			String[] number = new String[country.getArmies() + 1];
			for (int c = 0; c < country.getArmies() + 1; c++) {
				number[c] = "" + c + "";
			}
			String numberArmies;
			numberArmies = (String) JOptionPane.showInputDialog(frame,
					"Now from " + country.getName() + "to neighbour country " + neighCoun
							+ " how many armies do you want to move if you win?",
					"Second Stage " + currPlayer.getName() + " Attacking the opposition", JOptionPane.QUESTION_MESSAGE,
					null, number, number[0]);
			if (numberArmies == null) {
				JOptionPane.showMessageDialog(null,
						"You choose cancel which means you have jumped this step---Attacking the opposition");
				addThePoint();
				repaint();
				fortify(belong);
			}
			int armyNumber = Integer.parseInt(numberArmies);

			Player winner = theGame.attackingHelper(currPlayer, country, neighCountry, armyNumber);
			if (winner.equals(currPlayer)) {
				JOptionPane.showMessageDialog(null, "You win from attack!");
				addThePoint();
				repaint();
			} else {
				JOptionPane.showMessageDialog(null, "You lose from attack!");
				addThePoint();
				repaint();
			}
			countryName = (String) JOptionPane.showInputDialog(frame,
					"Now you own these countries, which country do you want to choose? Please make sure that you have at least 2 armies on it, so that you can attack. ",
					"Second Stage Player" + belong + "Attacking the opposition", JOptionPane.QUESTION_MESSAGE, null,
					part1, part1[0]);
			if (countryName == null) {
				JOptionPane.showMessageDialog(null,
						"You choose cancel which means you have jumped this step---Attacking the opposition");
				addThePoint();
				repaint();
				fortify(belong);
			} else {
				JOptionPane.showMessageDialog(null, "You have jumped this step---Attacking the opposition!");
				addThePoint();
				repaint();
				fortify(belong);
			}

		}
		addThePoint();
		repaint();
		fortify(belong);
		/*
		 * countryName = (String) JOptionPane.showInputDialog(frame,
		 * "Now you own these countries, which country do you want to choose? Please make sure that you have at least 2 armies on it, so that you can attack. "
		 * , "Second Stage Player" + belong + "Attacking the opposition",
		 * JOptionPane.QUESTION_MESSAGE, null, part1, part1[0]);
		 * 
		 * 
		 * 
		 * if (countryName == null ||
		 * countryName.compareTo("Omit Attacking the opposition") == 0) { if
		 * (countryName == null) { JOptionPane.showMessageDialog(null,
		 * "You choose cancel which means you have jumped this step---Attacking the opposition"
		 * ); } else { JOptionPane.showMessageDialog(null,
		 * "You have jumped this step---Attacking the opposition!"); } } else {
		 * Country country = map.getCountryFromMap(countryName); while
		 * (country.getArmies() <= 2) {
		 * 
		 * JOptionPane.showMessageDialog(null, "As shown in the map, country " +
		 * country +
		 * " has <= 2 armies, so you can't do this step, which means you need to make another choice"
		 * );
		 * 
		 * countryName = (String) JOptionPane.showInputDialog(frame,
		 * "Now you own these countries, which country do you want to choose? Please make sure that you have at least 2 armies on it, so that you can attack. "
		 * , "Second Stage Player" + belong + "Attacking the opposition",
		 * JOptionPane.QUESTION_MESSAGE, null, part1, part1[0]); country =
		 * map.getCountryFromMap(countryName); if (countryName == null ||
		 * countryName.compareTo("Omit Attacking the opposition") == 0) { if
		 * (countryName == null) { JOptionPane.showMessageDialog(null,
		 * "You choose cancel which means you have jumped this step---Attacking the opposition"
		 * ); } else { JOptionPane.showMessageDialog(null,
		 * "You have jumped this step---Attacking the opposition!"); }
		 * fortify(belong); } } String[] nei = new
		 * String[currPlayer.getNeighbourAndNotOwnedCountries(country).size()];
		 * for (int i = 0; i <
		 * currPlayer.getNeighbourAndNotOwnedCountries(country).size(); i++) {
		 * nei[i] =
		 * currPlayer.getNeighbourAndNotOwnedCountries(country).get(i).getName()
		 * ; }
		 * 
		 * String neighCoun; neighCoun = (String)
		 * JOptionPane.showInputDialog(frame, "Now from" + country.getName() +
		 * "has the following neighbour countries:", "Second Stage Player " +
		 * belong + "Attacking the opposition", JOptionPane.QUESTION_MESSAGE,
		 * null, nei, nei[0]); if (neighCoun == null) {
		 * JOptionPane.showMessageDialog(null,
		 * "You choose cancel which means you have jumped this step---Attacking the opposition"
		 * ); fortify(belong); } Country neighCountry =
		 * map.getCountryFromMap(neighCoun);
		 * 
		 * String[] number = new String[country.getArmies() + 1]; for (int c =
		 * 0; c < country.getArmies() + 1; c++) { number[c] = "" + c + ""; }
		 * String numberArmies; numberArmies = (String)
		 * JOptionPane.showInputDialog(frame, "Now from " + country.getName() +
		 * "to neighbour country " + neighCoun +
		 * " how many armies do you want to choose?", "Second Stage Player " +
		 * belong + " Attacking the opposition", JOptionPane.QUESTION_MESSAGE,
		 * null, number, number[0]); if (numberArmies == null) {
		 * JOptionPane.showMessageDialog(null,
		 * "You choose cancel which means you have jumped this step---Attacking the opposition"
		 * ); fortify(belong); } int armyNumber =
		 * Integer.parseInt(numberArmies); country.loseArmies(armyNumber);
		 * 
		 * if (armyNumber > neighCountry.getArmies()) {
		 * neighCountry.setArmies(armyNumber - neighCountry.getArmies());//
		 * .addArmies(armyNumber-neighCountry.getArmies());
		 * neighCountry.setOwner(currPlayer.getName());
		 * JOptionPane.showMessageDialog(null, "Now you own the country!" +
		 * neighCountry.getName()); } else { JOptionPane.showMessageDialog(null,
		 * "You fail to attack!");
		 * neighCountry.setArmies(neighCountry.getArmies() - armyNumber); }
		 * 
		 * } theGame.setStage("Attack Opposition"); repaint(); fortify(belong);
		 */
	}

	public void fortify(int belong) {
		theGame.setCurrentPlayer(belong);
		JFrame frame = new JFrame("Fortifying the territories held");
		theGame.setStage("Fortifying the territories held");

		String[] part1 = new String[theGame.getPlayerList().getPlayer(belong).getCountries().size() + 1];
		for (int i = 0; i < theGame.getPlayerList().getPlayer(belong).getCountries().size(); i++) {
			part1[i] = theGame.getPlayerList().getPlayer(belong).getCountries().get(i).getName();
		}

		part1[theGame.getPlayerList().getPlayer(belong).getCountries()
				.size()] = "Omitting the Fortifying the territories held";
		Player currPlayer = theGame.getPlayerList().getPlayer(belong);
		String countryName;
		countryName = (String) JOptionPane.showInputDialog(frame,
				"Now you own these countries, which country do you want to choose?",
				"Second Stage Player " + belong + " Fortifying the territories held", JOptionPane.QUESTION_MESSAGE,
				null, part1, part1[0]);
		if (countryName == null) {
			JOptionPane.showMessageDialog(null,
					"You choose cancel which means you have jumped this step---Fortifying the territories held");
			addThePoint();
			repaint();
			numberOfClicked2++;
			if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
				update1();
			} else {
				//decisionMade(numberOfClicked2 % (numberOfPlayers));
				JOptionPane.showMessageDialog(null,"Human Player's turn, please click the button");
			}
			return;
		}
		if (countryName.compareTo("Omitting the Fortifying the territories held") == 0) {
			JOptionPane.showMessageDialog(null, "You have jumped this step---Fortifying the territories held!");
			addThePoint();
			repaint();
			numberOfClicked2++;
			if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
				update1();
			} else {
				decisionMade(numberOfClicked2 % (numberOfPlayers));
			}
		} else {
			Country country = map.getCountryFromMap(countryName);

			if (currPlayer.getNeighbourAndOwnedCountries(country) == null) {
				JOptionPane.showMessageDialog(null,
						"As for now this country has no adjacent country to fortify!Please make another decision.");
				addThePoint();
				repaint();
				fortify(belong);
			} else {
				String[] neiAndOwned = new String[currPlayer.getNeighbourAndOwnedCountries(country).size()];
				for (int i = 0; i < currPlayer.getNeighbourAndOwnedCountries(country).size(); i++) {
					neiAndOwned[i] = currPlayer.getNeighbourAndOwnedCountries(country).get(i).getName();
				}

				String neighCoun;
				neighCoun = (String) JOptionPane.showInputDialog(frame,
						"Now from" + country.getName() + "has the following neighbour countries:",
						"Second Stage Player" + belong + " Fortifying the territories held",
						JOptionPane.QUESTION_MESSAGE, null, neiAndOwned, neiAndOwned[0]);
				if (neighCoun == null) {
					JOptionPane.showMessageDialog(null,
							"You choose cancel which means you have jumped this step---Fortifying the territories held");
					addThePoint();
					repaint();
					numberOfClicked2++;
					if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
						update1();
					} else {
						decisionMade(numberOfClicked2 % (numberOfPlayers));
					}
				}
				Country neighCountry = map.getCountryFromMap(neighCoun);
				String[] number = new String[country.getArmies() + 1];
				for (int c = 0; c < country.getArmies() + 1; c++) {
					number[c] = "" + c + "";
				}
				String numberArmies;
				numberArmies = (String) JOptionPane.showInputDialog(frame,
						"Now from " + country.getName() + "to neighbour country " + neighCoun
								+ " how many armies do you want to choose?",
						"Second Stage Player" + belong + " Fortifying the territories held",
						JOptionPane.QUESTION_MESSAGE, null, number, number[0]);
				if (numberArmies == null) {
					JOptionPane.showMessageDialog(null,
							"You choose cancel which means you have jumped this step---Fortifying the territories held");
					addThePoint();
					repaint();
					numberOfClicked2++;
					if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
						update1();
					} else {
						decisionMade(numberOfClicked2 % (numberOfPlayers));
					}
					return;
				}
				int armyNumber = Integer.parseInt(numberArmies);
				if (country.getArmies() - armyNumber <= 1) {
					JOptionPane.showMessageDialog(null,
							"You must leave at least one army in your old terrority, you can't make this change!");
					addThePoint();
					repaint();
					numberOfClicked2++;
					if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
						update1();
					} else {
						decisionMade(numberOfClicked2 % (numberOfPlayers));
					}
				} else {
					country.addArmies(armyNumber);
					neighCountry.loseArmies(armyNumber);
					addThePoint();
					repaint();
					numberOfClicked2++;
					if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
						update1();
					} else {
						decisionMade(numberOfClicked2 % (numberOfPlayers));
					}

				}
			}
		}
		repaint();
		numberOfClicked2++;
		if (!players.isHuman(players.getPlayer(numberOfClicked2 % (numberOfPlayers)))) {
			update1();
		} else {
			decisionMade(numberOfClicked2 % (numberOfPlayers));
		}
		// attackOppo(belong);
	}

	public class CardGUI extends JFrame implements Serializable {

		public CardGUI(Player player) {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(new Dimension(1000, 600));
			this.setLocation(400, 400);
			
			this.setTitle("Card Information");
			this.setLayout(null);
			JPanel cardPanel = new CardPanel(player);
			this.add(cardPanel);
			cardPanel.setSize(new Dimension(1000, 600));
			cardPanel.setLocation(0, 0);
			cardPanel.setBackground(new Color(255, 218, 185));

		}

		public class CardPanel extends JPanel implements Serializable {
			private Image cardImg;
			private Image cardBackground;
			private Map map;
			private ArrayList<Card> hand;
			private int paint_x, paint_y;
			private int armyCount;
			private JButton yes;
			private JButton no;
			private int choose;

			public CardPanel(Player player) {
				choose = 0;
				currPlayer = player;
				hand = currPlayer.getHand();
				this.setLayout(null);

				yes = new JButton("YES");
				add(yes);
				yes.setLocation(400, 500);
				yes.setSize(new Dimension(100, 50));
				armyCount = 0;
				no = new JButton("NO");
				add(no);
				no.setLocation(550, 500);
				no.setSize(new Dimension(100, 50));
				ChooseListener chooseListener = new ChooseListener();
				yes.addActionListener(chooseListener);
				no.addActionListener(chooseListener);
				repaint();

			}

			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.white);
				try {
					cardBackground = ImageIO.read(new File("image/map_back.jpeg"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				g2.drawImage(cardBackground, 0, 0, this);

				Font myFont = new Font("default", Font.BOLD, 14);
				g2.setFont(myFont);
				g2.drawString("The Cards you have now are: ", 50, 50);
				for (int i = 0; i < hand.size(); i++) {

					paint_x = i * 150 + 50;
					paint_y = 70;
					Card thisCard = hand.get(i);
					try {
						System.out.println("image/" + thisCard.getType() + ".png");
						cardImg = ImageIO.read(new File("image/" + thisCard.getType() + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					g2.drawImage(cardImg, paint_x, paint_y, this);
					g2.setColor(Color.black);
					g2.drawString(thisCard.getCountry(), (paint_x + 20), (paint_y + 40));
				}
				g2.setColor(Color.white);
				// g2.drawString("The armies deployed from the countries and
				// continent you owned is:", 50,400);
				// g2.drawString(""+armyCount, 550,400);
				g2.drawString("Do you want to turn in cards?", 50, 450);
			}

			private class ChooseListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Find out the text of the JMenuItem that was just clicked
					if (e.getSource().equals(yes)) {
						JOptionPane.showMessageDialog(null, "hehehehehehehehehehe!");
						assignCards(theGame.returnCurrentPlayer());
					}
					if (e.getSource().equals(no)) {
						choose = 1;
						if (hand.size() >= 5) {
							JOptionPane.showMessageDialog(null, "You have more than 5 cards, you have to turn in!");
							assignCards(theGame.returnCurrentPlayer());
						} else {
							cardpanel.dispose();
							deployingArmies(theGame.returnCurrentPlayer());
						}

					}
				}
			}

		}

	}

	public class secondStage1Listener implements ActionListener{
			private int thistimePlayer;
		public secondStage1Listener(int numberOfClicked2) {
			// TODO Auto-generated constructor stub
			this.thistimePlayer=numberOfClicked2%theGame.getPlayerList().getPlayerSize();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			decisionMade(this.thistimePlayer);
		}
		
	}
	public class ArmiesListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, (String) players.cardToStringInPlayerList());
		}

	}
}