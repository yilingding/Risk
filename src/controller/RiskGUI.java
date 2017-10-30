package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;



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
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class RiskGUI extends JFrame implements Serializable {

	public static void main(String[] args) {
		RiskGUI g = new RiskGUI();
		g.setVisible(true);
	}

	private AudioStream as;
	private AudioData audioData;
	private Timer t;
	private Timer t2;
	private FileInputStream blah;
	private RiskGame theGame;
	private PlayerList players;
	private Clip clip;
	private LoadPanel menu1;
	private MapPanel theMap;
	private int numberOfPlayers;
	private ContinuousAudioDataStream loop;
	public JPanel currentView;
	public static final int width = 1100;
	public static final int height = 720;
	
	// private Map map;

	private int numberOfHuman;
	private int numberOfAI;
	private JMenuItem menu;
	private JMenuItem musicMenu;
	private JMenuItem informationMenu;
	private JMenuItem diceInformaiton;

	// private int viewStatus = 0; // 1 == map, 2 ==load

	/*
	 * The constructor of RiskGUI.
	 */

	public RiskGUI() {

		loadDataWithOptionPane();

		this.setLocation(100, 40);
		this.setTitle("Riskier Business");
		this.setSize(width, height);
		setupMenus();

		menu1 = new LoadPanel(width, height);

		this.add(menu1);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("./songfiles/song2.wav"));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.addWindowListener(new CloseWindow());

	}

	private void initializeGame() {
		theGame = new RiskGame();
		players = theGame.getPlayerList();

		numberOfPlayers = 0;
		// theMap = new MapPanel(width, height, theGame);
	}

	private void setViewTo(JPanel newView) {

		if (currentView != null)
			remove(currentView);
		currentView = newView;
		add(currentView);
		currentView.repaint();
		validate();
	}

	public Dimension getPreferedSize() {
		return (new Dimension(width, height));
	}

	private void loadDataWithOptionPane() {
		// TODO Auto-generated method stub

	}

	private void quitPanel() {
		saveAndQuit(1);
	}

	public void startAnewGame() {

		String[] num = { "", "0", "1", "2", "3", "4", "5", "6" };
		JComboBox HuInput = new JComboBox(num);
		JComboBox ComInput = new JComboBox(num);

		JPanel choosePlayer = new JPanel();
		choosePlayer.setPreferredSize(new Dimension(200, 100));
		choosePlayer.add(new JLabel("Please Select Player Numbers:"));
		choosePlayer.add(new JLabel("Human Player:"));
		choosePlayer.add(HuInput);
		choosePlayer.add(new JLabel("Computer Player:"));
		choosePlayer.add(ComInput);

		int result = JOptionPane.showConfirmDialog(null, choosePlayer, "Choose Number of Players",
				JOptionPane.OK_CANCEL_OPTION);
		String hu = "", com = "";
		if (result == JOptionPane.OK_OPTION) {
			hu = HuInput.getSelectedItem().toString();
			com = ComInput.getSelectedItem().toString();
		} else {
			JOptionPane.showMessageDialog(null, "Please select the number of human and computer players!");
			return;
		}

		if (hu == "") {
			JOptionPane.showMessageDialog(null, "Please select the number of human players!");
			return;
		} else if (com == "") {
			JOptionPane.showMessageDialog(null, "Please select the number of computer players!");
			return;
		}

		else {

			int total = Integer.parseInt(com) + Integer.parseInt(hu);
			if (total < 2) {
				JOptionPane.showMessageDialog(null, "Please have at least 2 player!");
				return;
			} else if (total > 6) {
				JOptionPane.showMessageDialog(null, "Please have at most 6 player!");
				return;
			} else {

				initializeGame();

				numberOfHuman = Integer.parseInt(hu);
				// System.out.println("number of human player:"+numberOfHuman);

				numberOfAI = Integer.parseInt(com);
				// System.out.println("number of computer player:"+numberOfAI);

				for (int i = 0; i < numberOfHuman; i++) {
					String color = players.getColor(i);
					JFrame frame = new JFrame("Enter human player name");

					// prompt the user to enter their name
					String name = JOptionPane.showInputDialog(frame, "What's the name for human player " + i + " ?");

					// get the user's input. note that if they press Cancel,
					// 'name' will be null
					// System.out.printf("The user's name is '%s'.\n", name);
					theGame.addHumanPlayer(name, color);
				}
				for (int i = 0; i < numberOfAI; i++) {

					// String[] strategy = { "Simple", "Intermediate",
					// "Expert"};

					// for(int j=0;j<numberOfAI;j++){
					// JComboBox setStra = new JComboBox(strategy);
					//
					// JPanel strat = new JPanel();
					// strat.add(new JLabel("Computer Player "+j+":"));
					// strat.add(HuInput);
					// strat.add(Box.createHorizontalStrut(15)); // a spacer
					// }
					//
					// JOptionPane.showConfirmDialog(null, choosePlayer,
					// "Please Enter the Strategy You Want for Every Computer
					// Player: ", JOptionPane.OK_CANCEL_OPTION);
					//
					// if (result == JOptionPane.OK_OPTION) {
					// hu=HuInput.getSelectedItem().toString();
					// com=HuInput.getSelectedItem().toString();
					// }

					JFrame frame1 = new JFrame("Enter computer player name");

					// prompt the user to enter their name
					String name = JOptionPane.showInputDialog(frame1,
							"What's the name for computer player " + i + " ?");

					JFrame frame = new JFrame("Set Strategy for computer player " + i);
					String[] strategy = { "Simple", "Intermediate", "Expert" };

					String stra = (String) JOptionPane.showInputDialog(frame,
							"What Strategy do you want for computer player " + i + " ?", "Strategy",
							JOptionPane.QUESTION_MESSAGE, null, strategy, strategy[0]);

					if (stra == null) {
						JOptionPane.showMessageDialog(null,
								"Please select computer player strategy for computer player " + i + " !");
						return;
					} else {
						if (stra.equals("Simple"))
							theGame.addSimpleAI(name, players.getColor(i + numberOfHuman));
						else if (stra.equals("Intermediate"))
							theGame.addIntermediateAI(name, players.getColor(i + numberOfHuman));
						else
							theGame.addExpertAI(name, players.getColor(i + numberOfHuman));

					}
				}
				menu1.removeAll();
				theGame.setInitialArmies();
				System.out.println("initialize game playerlist:" + players.colorToString());
				players.shuffle();
				System.out.println("before game playerlist:" + players.colorToString());
				theMap = new MapPanel(width, height, theGame, 0);
				menu.setEnabled(true);
				t.stop();
				setViewTo(theMap);
				theMap.repaint();
				numberOfPlayers = numberOfHuman + numberOfAI;

				// System.out.println("size of human player:"+numberOfHuman);
				// System.out.println("size of computer player:"+numberOfAI);
			}
		}
	}

	public String saveAndQuit(int k) {
		final JFrame frame222 = new JFrame("Quit and Save Game");
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setCurrentDirectory(new File("./"));
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setBorder(BorderFactory.createEmptyBorder());
		textArea.setText(
				"Do you want to save the game? If yes, you only need to provide the filename you want without the extension.");
		Font myFont = new Font("default", Font.BOLD, 12);
		textArea.setFont(myFont);

		JButton btn2 = new JButton("NO!!!!");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (k == 1) {
					JOptionPane.showMessageDialog(null, "You didn't save the game because you choose no.");
					System.exit(0);
				} else if (k == 2) {
					JOptionPane.showMessageDialog(null, "You didn't save the game because you choose no.");
					frame222.dispose();
				} else if (k == 3) {
					JOptionPane.showMessageDialog(null, "You didn't save the game because you choose no.");
					frame222.dispose();
					startAnewGame();
				}
			}
		});
		JButton btn3 = new JButton("YES!!!!");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fc.showSaveDialog(frame222);
				if (i == JFileChooser.APPROVE_OPTION) {
					// System.out.println(fc.getSelectedFile().getName());
					FileOutputStream bytesToDisk = null;
					try {
						bytesToDisk = new FileOutputStream(fc.getSelectedFile().getName() + ".ser");
						ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
						outFile.writeObject(theGame);
						outFile.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (k == 1) {
						JOptionPane.showMessageDialog(null, "You have saved the game" + theGame.returnCurrentPlayer());
						System.exit(0);
					} else if (k == 2) {
						JOptionPane.showMessageDialog(frame222, "You have saved the game");
						frame222.dispose();
					} else if (k == 3) {
						JOptionPane.showMessageDialog(frame222, "You have saved the game");
						frame222.dispose();
						startAnewGame();
					}
				} else {

					if (k == 1) {
						JOptionPane.showMessageDialog(null, "You didn't save the game because you choose cancel.");
						System.exit(0);
					} else if (k == 2) {
						JOptionPane.showMessageDialog(null, "You didn't save the game because you choose cancel.");
						frame222.dispose();
					} else if (k == 3) {
						JOptionPane.showMessageDialog(null, "You didn't save the game because you choose cancel.");
						frame222.dispose();
						startAnewGame();
					}
				}

			}
		});

		Container pane = frame222.getContentPane();
		pane.setLayout(new GridLayout(3, 1, 10, 10));
		pane.add(textArea, BorderLayout.CENTER);
		pane.add(btn2);
		pane.add(btn3);

		frame222.setSize(300, 200);
		frame222.setVisible(true);
		return "haha";
	}

	private class CloseWindow implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			quitPanel();
		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private void setupMenus() {
		
		menu = new JMenu("Menu");
		musicMenu = new JMenu("Music");
		informationMenu = new JMenu("Information (Card and Countries)");
		JMenuItem cardIn = new JMenu("Card Information");
		informationMenu.add(cardIn);
		JMenuItem numberOfCard = new JMenuItem("Card Number");
		cardIn.add(numberOfCard);

		JMenuItem imagecard = new JMenuItem("The card you owned (Image)");
		cardIn.add(imagecard);

		diceInformaiton=new JMenu("Dice");
		JMenuItem dice = new JMenuItem("Current Dice Number");
		diceInformaiton.add(dice);
		JMenuItem diceN = new JMenuItem("Change the Dice Number");
		diceInformaiton.add(diceN);
		
		JMenuItem textcard = new JMenuItem("Cards each player owned (Text)");
		cardIn.add(textcard);
		JMenuItem CounteisII = new JMenuItem("Countries Owned Information");
		informationMenu.add(CounteisII);
		JMenuItem newGame = new JMenuItem("New Game");
		menu.add(newGame);
		JMenuItem save = new JMenuItem("Save Current Game");
		menu.add(save);
		JMenuItem strategy1111 = new JMenuItem("Change Strategy");
		menu.add(strategy1111);
		strategy1111.addActionListener(new InformationListener());

		JMenuItem stop = new JMenuItem("Music Stop");
		musicMenu.add(stop);
		JMenuItem play = new JMenuItem("Music Play");
		musicMenu.add(play);

		// Set the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(menu);
		menuBar.add(musicMenu);
		menuBar.add(informationMenu);
		menuBar.add(diceInformaiton);
		// Add the same listener to all menu items requiring action
		MenuItemListener menuListener = new MenuItemListener();
		newGame.addActionListener(menuListener);
		stop.addActionListener(menuListener);
		dice.addActionListener(menuListener);
		diceN.addActionListener(menuListener);
		play.addActionListener(menuListener);
		save.addActionListener(menuListener);
		imagecard.addActionListener(menuListener);
		textcard.addActionListener(menuListener);
		CounteisII.addActionListener(menuListener);
		numberOfCard.addActionListener(menuListener);

	}

	public class InformationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (theGame.getPlayerList().getNumberOfAI() == 0) {
				JOptionPane.showMessageDialog(null, "Now you have 0 computer players, you can't change strategy!");
				return;
			}
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

	private class MenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Find out the text of the JMenuItem that was just clicked
			String text = ((JMenuItem) e.getSource()).getText();
			if (text.equals("New Game")) {

				saveAndQuit(3);

			} else if (text.equals("Music Stop")) {
				clip.stop();
			} else if (text.equals("Music Play")) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			} else if (text.equals("Save Current Game")) {
				saveAndQuit(2);
			} else if (text.equals("Card Number")) {
				JOptionPane.showMessageDialog(null,
						"Now the game currently has " + theGame.cardLeftInTheGame() + " cards");

			} else if (text.equals("The card you owned (Image)")) {
				CardOwned owneeeed = new CardOwned(theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
				owneeeed.setVisible(true);
			} else if (text.equals("Cards each player owned (Text)")) {
				JOptionPane.showMessageDialog(null, (String) players.cardToStringInPlayerList());
			} else if (text.equals("Countries Owned Information")) {

				JOptionPane.showMessageDialog(null, (String) players.countriesToString());
			}else if(text.equals("Current Dice Number")){
				JOptionPane.showMessageDialog(null, "Your dice Number is"+theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()).getTheDiceNumber());
			}else if(text.equals("Change the Dice Number")){
				JFrame frame = new JFrame("Change the dice number.");
				int numberOfCoumputerPlayer = theGame.getPlayerList().getNumberOfAI();
				String[] num = new String[4];
				
				
				for (int i = 0; i < 4; i++) {
					
						num[i] = "" + i;
					
				}
				String com;

				String hu = (String) JOptionPane.showInputDialog(frame,
						"the dice number you want",
						"Change the dice.", JOptionPane.QUESTION_MESSAGE, null, num, num[0]);
				
				int thePlayernumber = Integer.valueOf(hu);
				theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()).setTheDiceNumber(thePlayernumber, 0);
				JOptionPane.showMessageDialog(null, "you change you dice number to"+ hu);
			}
		}
	}

	public class CardOwned extends JFrame implements Serializable {

		public CardOwned(Player player) {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(new Dimension(1000, 600));
			this.setLocation(400, 400);

			this.setTitle("The information of the card you own.");
			this.setLayout(null);
			JPanel cardPanel = new CardPanel11(player);
			this.add(cardPanel);
			cardPanel.setSize(new Dimension(1000, 600));
			cardPanel.setLocation(0, 0);
			cardPanel.setBackground(new Color(255, 218, 185));

		}

		public class CardPanel11 extends JPanel implements Serializable {
			private Image cardImg;
			private Image cardBackground;
			private Map map;
			private ArrayList<Card> hand;
			private int paint_x, paint_y;
			private int armyCount;
			private Player currPlayer1111;

			private int choose;

			public CardPanel11(Player player) {
				choose = 0;
				currPlayer1111 = player;
				hand = currPlayer1111.getHand();
				this.setLayout(null);

				armyCount = 0;
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
				g2.drawString("Card you own now. ", 50, 450);
			}

		}
	}

	public class LoadPanel extends JPanel implements Serializable {

		private Image background;
		private JButton info = new JButton(new ImageIcon("image/information.png"));
		private JButton start = new JButton(new ImageIcon("image/new_game.png"));
		private JButton readData = new JButton(new ImageIcon("image/continue_button.png"));
		private int width;
		private int height;
		private int amplitude = 10;
		private int currSpot;
		private int startx = 50;
		private int starty = 600;
		private boolean increasing = true;
		private String message = "RISKIERBUSINESS";
		private ArrayList<Image> imageList;
		private Image r;
		private Image i;
		private Image s;
		private Image k;
		private Image e;
		private Image b;
		private Image u;
		private Image n;

		public LoadPanel(int w, int h) {
			menu.setEnabled(false);
			this.setOpaque(true);
			imageList = new ArrayList<Image>();
			try {
				background = ImageIO.read(new File("image/risk_begin.jpeg"));

			} catch (Exception e) {

			}

			this.setBackground(Color.BLACK);
			currSpot = 0;
			readImage();
			imageList.add(r);
			imageList.add(i);
			imageList.add(s);
			imageList.add(k);
			imageList.add(i);
			imageList.add(e);
			imageList.add(r);
			imageList.add(b);
			imageList.add(u);
			imageList.add(s);
			imageList.add(i);
			imageList.add(n);
			imageList.add(e);
			imageList.add(s);
			imageList.add(s);
			t = new Timer(100, new Ticker());

			t.start();
			info.setPreferredSize(new Dimension(100, 100));
			this.add(info);
			start.setPreferredSize(new Dimension(400, 100));
			this.add(start);
			ButtonListener start1 = new ButtonListener();
			info.addActionListener(start1);
			start.addActionListener(start1);
			readData.setPreferredSize(new Dimension(400, 100));
			this.add(readData);
			readData.addActionListener(start1);

		}

		public void readImage() {

			try {
				String rname = "image/R.JPG";
				r = ImageIO.read(new File(rname));
				String iname = "image/I.JPG";
				i = ImageIO.read(new File(iname));
				String sname = "image/S.JPG";
				s = ImageIO.read(new File(sname));
				String kname = "image/K.JPG";
				k = ImageIO.read(new File(kname));
				String ename = "image/E.JPG";
				e = ImageIO.read(new File(ename));
				String bname = "image/B.JPG";
				b = ImageIO.read(new File(bname));
				String uname = "image/U.JPG";
				u = ImageIO.read(new File(uname));
				String nname = "image/N.JPG";
				n = ImageIO.read(new File(nname));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public Dimension getPreferedSize() {
			return (new Dimension(width, height));
		}

		private class Ticker implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				menu1.repaint();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			this.setBackground(Color.black);
			g2.setColor(Color.black);

			g.drawImage(background, 0, 0, this);
			// g2.fillRect(0, 0, 900, 150);
			for (int index = 0; index < message.length(); index++) {
				int differential;
				if (index % 2 == 0) {
					differential = currSpot;
				} else {
					differential = -currSpot;
				}
				g2.drawImage(imageList.get(index), startx + (65 * index), starty + differential, null);
			}
			if (increasing)
				currSpot++;
			else
				currSpot--;

			if (currSpot == amplitude) {
				increasing = false;

			} else if (currSpot == -amplitude)
				increasing = true;
		}

		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				menu1.add(start);
				menu1.add(readData);

				if (arg0.getSource().equals(start)) {
					startAnewGame();

				} else if (arg0.getSource().equals(readData)) {

					readData();

				} else if (arg0.getSource().equals(info)) {
					JFrame frame = new JFrame("About");
					JOptionPane.showMessageDialog(frame,
                            "About Game: Risk is a complex board game produced by\n"
                            + "                     Hasbro. The goal is to take over the world.\n"
                            + "                     Players attempt to take over the world by\n"
                            + "                     eliminating all other players. Players are eliminated \n"
                            + "                     when they lose all of their troops on the game board. \n\n"
                            + "Instruction: If you want to start a new game, please press \n"
                            + "                     \"New Game\" button. If you want to continue a game, \n"
                            + "                     please press \"Click Here to Continue\" button.\n"
                            + "                     The menu button in on the top left corner, and \n"
                            + "                     you can start a new game, select dice number, \n "
                            + "                     and manage music player there. When the game \n"
                            + "                     starts, first you will click the start button,\n"
                            + "                     and select the country you want by clicking it.\n"
                            + "                     After all players claimed country, you need to\n"
                            + "                     place the army you have now on the map. After every\n"
                            + "                     player put their armies, you need to click on    \n"
                            + "                     button when it's your turn and a window will pop\n"
                            + "                     up to continue the game. All the game information\n"
                            + "                     will be updated in mappanel and you can see form\n"
                            + "                     there.\n\n"
                            + "Team Name:  Riskier Business\n\n" + "Team Member: Colin Widner\n"
                            + "                        Qiming Wan\n" + "                        Yiling Ding\n"
                            + "                        Mingjun Zhou\n",
                            "About", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}

	public void readData() {
		final JFrame frame222 = new JFrame("Load a previously saved game ");
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setCurrentDirectory(new File("./"));
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setBorder(BorderFactory.createEmptyBorder());
		textArea.setText(
				"Do you want to load a previously saved game ? If yes, please choose one file with extention ser.");
		Font myFont = new Font("default", Font.BOLD, 12);
		textArea.setFont(myFont);

		JButton btn2 = new JButton("NO!!!!");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"You fail loading the previously saved game, because you choose NO.");
				frame222.dispose();

			}
		});
		JButton btn3 = new JButton("YES!!!!");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retVal = fc.showOpenDialog(frame222);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFiles().length > 1) {
						JOptionPane.showMessageDialog(null,
								"You have choosed mutiple files, but we will onlt take the first file to load. ");

					}
					File[] selectedfiles = fc.getSelectedFiles();

					String name = selectedfiles[0].getName();
					try {
						// System.out.println(name);
						ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(name));
						frame222.dispose();
						theGame = (RiskGame) inFile.readObject();
						players = theGame.getPlayerList();
						// theGame.setPlayerList(players);
						numberOfPlayers = theGame.getPlayerList().getPlayerSize();
						theMap = new MapPanel(width, height, theGame, 1);
						menu1.removeAll();
						menu.setEnabled(true);
						setViewTo(theMap);
						t.stop();
						// theMap.repaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"You fail loading the previously saved game, because you choose cancel.");
					frame222.dispose();
				}

			}
		});
		Container pane = frame222.getContentPane();
		pane.setLayout(new GridLayout(3, 1, 10, 10));
		pane.add(textArea, BorderLayout.CENTER);
		pane.add(btn2);
		pane.add(btn3);

		frame222.setSize(300, 200);
		frame222.setVisible(true);

	}

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
		private JButton secondStage;
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
		public StageGUI paneeeel;
		public Stage2GUI paneeeel1;
		public Stage3GUI paneeeel2;

		private PointListener listentopoint;
		public int readData;
		private int allComputerPlayer;

		public MapPanel(int w, int h, RiskGame game, int readData) {

			theGame = game;
			map = theGame.getMap();
			t2 = new Timer(100, new Ticker2());
			t2.start();
			players = theGame.getPlayerList();
			if (players.getNumberOfAI() == 6) {
				this.allComputerPlayer = 1;

			} else {
				this.allComputerPlayer = 0;
			}
			numberOfClicked = 0;
			numberOfClicked2 = 0;
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
			
			// secondStage1.addActionListener(new
			// secondStage1Listener(numberOfClicked2));
			stageDeployStarts = 0;
			start = new JButton("Start Game");
			// changeStrategy = new JButton();

			

			// cardInformation.setEnabled(false);
			choose = new ArmyPoints();

			armyNumberPointes = new FlagPoints();
			this.add(start);

			start.addActionListener(new ButtonListener());

			// changeStrategy.addActionListener(new InformationListener());
			try {
				mapImg = ImageIO.read(new File("image/Map.jpg"));

			} catch (Exception e) {
			}
			if (this.readData == 1) {
				addThePoint();
				repaint();
				start.setEnabled(false);
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
											);
						} else {
							playerNum = (theGame.returnCurrentPlayer() + 1) % theGame.getPlayerList().getPlayerSize();

						}
					} else {
						update();
					}
				} else if (theGame.getStage().compareTo("Deploying Armies") == 0) {
					// TODO 01: read data
					paneeeel = new StageGUI(theGame, theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
					
					paneeeel.setVisible(true);

				} else if (theGame.getStage().compareTo("Attacking the opposition") == 0) {
					// TODO 02: read data
paneeeel1 = new Stage2GUI(theGame, theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
					
					paneeeel1.setVisible(true);
				} else if (theGame.getStage().compareTo("Fortifying the territories held") == 0) {
					// TODO 03: read data
paneeeel2 = new Stage3GUI(theGame, theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
					
					paneeeel2.setVisible(true);
				}
			}

		}

		private class Ticker2 implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				addThePoint();
				repaint();
			}
		}

		private void startGame() {
			listentopoint = new PointListener();
			addMouseListener(listentopoint);
			theGame.setCurrentPlayer(0);
			nextPlayer = players.getPlayer(theGame.returnCurrentPlayer());
			theGame.setStage("Claim Country");
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
					if (!army.contains(x)) {
						army.add(x);
					}
					armyColorMap.put(x, players.getColor(i));
					Point y = armyNumberPointes.getPoint(players.getPlayer(i).getCountries().get(k).getName());
					if (!armyNumber.contains(y)) {
						armyNumber.add(y);
					}

					/*
					 * if
					 * (players.getPlayer(i).getCountries().get(k).getArmies()
					 * != armyNumberColorMap.get(y)) {
					 * JOptionPane.showMessageDialog(null, i + "player 's  " +
					 * players.getPlayer(i).getCountries().get(k) +
					 * "changes from " + armyNumberColorMap.get(y) + " to " +
					 * players.getPlayer(i).getCountries().get(k).getArmies());
					 * }
					 */
					armyNumberColorMap.put(y, players.getPlayer(i).getCountries().get(k).getArmies());
				}
			}

		}

		private class PointListener implements MouseListener {
			private int selectCountryStage; // 0 ok to select, 1 no
			private int secondStageNumber; //

			private Point armyPoint = null;
			private Point armyNumberPoint = null;

			public PointListener() {
				selectCountryStage = 0;
				secondStageNumber = 1;
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
							if(theGame.getPlayerList().getAI().size()==6){
							JOptionPane.showMessageDialog(null, "Enter place army stage.");
							}
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
							if(theGame.getPlayerList().getAI().size()!=6){
							JOptionPane.showMessageDialog(null,
									"You have finished your initial deploying, next player.");
							}

							if (playerNum == players.getPlayerSize()) {
								secondStageNumber = 0;
								stageDeployStarts = 1;
								repaint();
								nextPlayer = players.getPlayer(0);
								if(theGame.getPlayerList().getAI().size()==6){
								JOptionPane.showMessageDialog(null,
										"You have finished the first stage, you can move to the next stage.");
								}
								start.setEnabled(false);
								secondStage.setEnabled(true);

								removeMouseListener(listentopoint);
								if (!players.isHuman(players.getPlayer((theGame.returnCurrentPlayer() + 1)
										% theGame.getPlayerList().getPlayerSize()))) {
									update1();
								} else {
									currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
									decisionMade(theGame.returnCurrentPlayer());
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
				JOptionPane.showMessageDialog(null, "Game over " + theGame.gameOver().getName() + " win");
			} else {
				theGame.setCurrentPlayer((theGame.returnCurrentPlayer() + 1) % theGame.getPlayerList().getPlayerSize());
				// addThePoint();
				// repaint();

				currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
				theGame.setStage("Deploying Armies");

				theGame.deployArmies(currPlayer);

				addThePoint();
				repaint();
				theGame.setStage("Attacking the opposition");
				theGame.attackingOppositionAI((ComputerPlayer) currPlayer);
				addThePoint();
				repaint();
				theGame.setStage("Fortifying the territories held");
				theGame.fortifyingTerritoriesAI((ComputerPlayer) currPlayer);
				addThePoint();
				repaint();
				// JOptionPane.showMessageDialog(null,
				// "hehehhehehehehhhhhhhhhh.");
				if (!players.isHuman(players
						.getPlayer((theGame.returnCurrentPlayer() + 1) % theGame.getPlayerList().getPlayerSize()))) {
					update1();
				} else {
					decisionMade((theGame.returnCurrentPlayer() ) % theGame.getPlayerList().getPlayerSize());
					// JOptionPane.showMessageDialog(null,"Human
					// Player's turn, please click the button");
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
					repaint();
					if(theGame.getPlayerList().getAI().size()==6){
					JOptionPane.showMessageDialog(null, "Enter place army stage.");
					}
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
					if(theGame.getPlayerList().getAI().size()==6){
					JOptionPane.showMessageDialog(null,
							"You have finished the first stage, you can move to the next stage.");
					}
					// cardInformation.setEnabled(true);
					start.setEnabled(false);
					this.removeMouseListener(listentopoint);
					theGame.setStage("Deploying armies");
					nextPlayer = players.getPlayer(0);
					if (!players.isHuman(players.getPlayer(0))) {
						// JOptionPane.showMessageDialog(null,"heheh11111");
						update1();
						// numberOfClicked2++;
					} else {
						currPlayer = players.getPlayer(0);
						decisionMade(-1);

						// JOptionPane.showMessageDialog(null,"Human Player's
						// turn, please click the button");
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
			theGame.setCurrentPlayer((belong+1)%theGame.getPlayerList().getPlayerSize());
			// JOptionPane.showMessageDialog(null, "------" + belong);
			theGame.setStage("Check card");
			currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
			if (currPlayer.handSize() == 0) {
				JOptionPane.showMessageDialog(null,
						"As for now, you don't have any cards, which means you will just to the deploying stage.");
				theGame.humanHarvestArmise(currPlayer, null);
				
				 paneeeel = new StageGUI(theGame, currPlayer);
				paneeeel.setVisible(true);
				theGame.setStage("Deploying Armies");
			} else if (currPlayer.handSize() > 0) {
				checkArmies((belong+1)%theGame.getPlayerList().getPlayerSize());
			}
		}

		private CardGUI cardpanel;

		public void checkArmies(int belong) {
			currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
			cardpanel = new CardGUI(currPlayer);
			cardpanel.setVisible(true);
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
				private Player currPlayer123;

				public CardPanel(Player player) {
					choose = 0;
					currPlayer123 = player;
					hand = currPlayer123.getHand();
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
						// Find out the text of the JMenuItem that was just
						// clicked
						if (e.getSource().equals(yes)) {
							assignCards(theGame.returnCurrentPlayer());
						}
						if (e.getSource().equals(no)) {
							choose = 1;
							if (hand.size() >= 5) {
								JOptionPane.showMessageDialog(null, "You have more than 5 cards, you have to turn in!");
								assignCards(theGame.returnCurrentPlayer());
							} else {
								paneeeel.dispose();
								 paneeeel = new StageGUI(theGame, theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
								
								paneeeel.setVisible(true);
							}

						}
					}
				}

			}
		}

		public void assignCards(int belong) {

			JFrame frame = new JFrame("Assign Cards");
			// JOptionPane.showMessageDialog(null, "2------" + belong);
			currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
			if (!currPlayer.isTurnInable()) {
				JOptionPane.showMessageDialog(null, "You are not qualified to turn in cards now.");
				frame.dispose();
				cardpanel.dispose();
				theGame.humanHarvestArmise(currPlayer, null);
				StageGUI paneeeel = new StageGUI(theGame, theGame.getPlayerList().getPlayer(0));
				paneeeel.setVisible(true);
			} else {
				ArrayList<Card> hand = players.getPlayer(belong).getHand();
				//JOptionPane.showMessageDialog(null, "currPlayer is" + currPlayer.getColor());
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
				StageGUI paneeeel = new StageGUI(theGame, theGame.getPlayerList().getPlayer(theGame.returnCurrentPlayer()));
				paneeeel.setVisible(true);
			}

		}

		class StageGUI extends JFrame implements Serializable {
			private Player thecurrentPlayer222;
			
			JSplitPane splitPane;
			private Image cardImg;
			private Image cardBackground;
			private Image first;
			private Image second;
			private Timer timer;
			private ArrayList<Card> hand;
			private JButton bu1;
			private JButton bu2;
			private JButton bu3;
			private JButton bu4;
			private int amplitude = 10;
			private int currSpot;
			private ArrayList<Integer> startxl;
			private ArrayList<Integer> startyl;

			private boolean increasing = true;
			private Player thecurrentPlayer;
			private ArrayList<Image> imagell;
			private ArrayList<Image> imagel2;
			private ArrayList<Image> imagel3;
			private RiskGame theGame11;
			
			private Map map;
			public String namecccc;
			public StageGUI(RiskGame theGame, Player thecurrent) {
				this.thecurrentPlayer=thecurrent;
				JPanel cc=new JPanel();
				this.theGame11=theGame;
				cc.setBackground(new Color(255, 218, 185));
				bu1=new JButton("Countries to deploy");
				cc.add(bu1);
				bu1.setLocation(new Point(400,500));
				bu2=new JButton("Number of Armies to deploy.");
				cc.add(bu2);
				bu2.setEnabled(false);
				
				
				bu2.setLocation(new Point(300,100));
				bu3=new JButton("Next:Attacking the opposition.");
				cc.add(bu3);
				bu3.setLocation(new Point(400,100));
				bu4=new JButton("Fortifying the territories held");
				cc.add(bu4);
				bu4.setEnabled(false);
				//cc.add(exp);
				bu4.setLocation(new Point(500,100));
				bu3.setEnabled(false);
				
				JPanel stagePanel = new StagePanel(theGame, thecurrent);
				this.add(stagePanel);
				
				stagePanel.setSize(new Dimension(1000, 600));
				stagePanel.setLocation(0, 0);
				stagePanel.setBackground(new Color(255, 218, 185));
				splitPane = new JSplitPane();
				splitPane.setSize(1000, 600);
				splitPane.setDividerSize(0);
				splitPane.setDividerLocation(300);
				splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
				splitPane.setLeftComponent(cc);

				splitPane.setRightComponent(stagePanel);
	this.add(splitPane);
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				this.setSize(new Dimension(1000, 600));
				this.setLocation(400, 400);

				this.setTitle("Human Player Deploying Armies");
				//this.setLayout(null);
				bu1.addActionListener(new Wh());
				bu2.addActionListener(new Wh1());
				bu3.addActionListener(new Wh2());
				bu4.addActionListener(new Wh3());
			}
			
			
			private class Wh implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					CountryName names = new CountryName();
					String[] part1 = new String[thecurrentPlayer.getCountries().size()];// names.returnCountryName();
					for (int i = 0; i < thecurrentPlayer.getCountries().size(); i++) {
						part1[i] = thecurrentPlayer.getCountries().get(i).getName();
					}
					JPanel asscard = new JPanel();
					asscard.setPreferredSize(new Dimension(400, 300));
					JList list = new JList(part1);
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					asscard.add(new JLabel("Please select the country you want to deploy:"));
					asscard.add(new JScrollPane(list));
					int[] cards = new int[0];
					int result = JOptionPane.showConfirmDialog(null, asscard, "Choose The Country ",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						cards = list.getSelectedIndices();
					}
					String ccccc=part1[cards[0]];
					
					JOptionPane.showMessageDialog(null, "You select "+ccccc+" to deloy.");
						bu2.setEnabled(true);
						namecccc=ccccc;
				}
			}
				
				
				
				private class Wh1 implements ActionListener {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						CountryName names = new CountryName();
						String[] part1 = new String[thecurrentPlayer.getThisTurnNewArmies()+1];// names.returnCountryName();
						for (int i = 0; i <=thecurrentPlayer.getThisTurnNewArmies(); i++) {
							part1[i] = ""+i;
						}
						JPanel asscard = new JPanel();
						asscard.setPreferredSize(new Dimension(400, 300));
						JList list = new JList(part1);
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						asscard.add(new JLabel("Please select the number of you want to deploy:"));
						asscard.add(new JScrollPane(list));
						int[] cards = new int[0];
						while (cards.length!= 1) {
							
						int result = JOptionPane.showConfirmDialog(null, asscard, "Select the number ",
								JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							cards = list.getSelectedIndices();
						}
						}
						String ccccc=part1[cards[0]];
						
						JOptionPane.showMessageDialog(null, "You select "+ccccc+" armies to deloy.");
							bu4.setEnabled(true);
							int armyNumber = Integer.parseInt(ccccc);
							Country country11 = map.getCountryFromMap(namecccc);
							country11.addArmies(armyNumber);
							thecurrentPlayer.minusThisTurnNewArmies(armyNumber);
							bu3.setEnabled(true);
							bu4.setEnabled(true);
					}

			}
				private class Wh2 implements ActionListener {

					@Override
					public void actionPerformed(ActionEvent e) {
						paneeeel.dispose();
						
						 paneeeel1 = new Stage2GUI(theGame11, thecurrentPlayer);
						
						paneeeel1.setVisible(true);
					}
					
				}
				
				private class Wh3 implements ActionListener {

					@Override
					public void actionPerformed(ActionEvent e) {
						paneeeel.dispose();
						
						 paneeeel2 = new Stage3GUI(theGame11, thecurrentPlayer);
						
						paneeeel2.setVisible(true);
					}
					
				}
			public class StagePanel extends JPanel implements Serializable {
				private Player thecurrentPlayer;
				RiskGame theGame11;

				public StagePanel(RiskGame theGame, Player thecurrent) {
					this.theGame11=theGame;
					theGame11.setStage("Deploying armies");
					this.setOpaque(true);
					this.thecurrentPlayer = thecurrent;
					imagell = new ArrayList<Image>();
					imagel2 = new ArrayList<Image>();
					imagel3 = new ArrayList<Image>();
					startxl = new ArrayList<Integer>();
					startyl = new ArrayList<Integer>();
					map = theGame.getMap();
					currSpot = 0;
					try {
						first = ImageIO.read(new File("image/char1.png"));
						second = ImageIO.read(new File("image/country_ani.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					imagell.add(first);
					imagell.add(second);
					startxl.add(500);
					startxl.add(100);
					startyl.add(100);
					startyl.add(200);
					this.setLayout(null);
					timer = new Timer(100, new Ticker1());
					timer.start();
					
					// deployingArmies(thecurrent);
				}

				private class Ticker1 implements ActionListener {

					public void actionPerformed(ActionEvent arg0) {
						repaint();
					}
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
					int currspot = 0;
					for (int i = 0; i < imagell.size(); i++) {
						int differential;
						if (i % 2 == 0) {
							differential = currSpot;
						} else {
							differential = -currSpot;
						}
						g2.drawImage(imagell.get(i), startxl.get(i) + differential, startyl.get(i), null);
					}
					if (increasing)
						currSpot = currSpot + 10;
					else
						currSpot = currSpot - 10;

					if (currSpot == amplitude) {
						increasing = false;

					} else if (currSpot == -amplitude)
						increasing = true;
					Font myFont = new Font("default", Font.BOLD, 14);
					g2.setFont(myFont);
					g2.drawString("Human Player: " + thecurrentPlayer.getName(), 20, 20);
					g2.drawString("For Deploying Armies, You can't jump it, but you could jump Attacking or Fortifying." ,50, 100);
					
				}
			}
		}
	class Stage2GUI extends JFrame implements Serializable {
		    
		    JSplitPane splitPane;
		    private Image cardImg;
		    private Image cardBackground;
		    private Image first;
		    private Image second;
		    private Timer timer;
		    private ArrayList<Card> hand;
		    private JButton butt1;
		    private JButton butt2;
		    private JButton butt3;
		    private JButton butt4;
		    private JButton butt5;
		    private JButton butt6;
		    private int amplitude = 10;
		    private int currSpot;
		    private ArrayList<Integer> startxl;
		    private ArrayList<Integer> startyl;
		    private int armyNumber11;
		    private boolean increasing = true;
		    private Player thecurrentPlayer;
		    private ArrayList<Image> imagell;
		    private ArrayList<Image> imagel2;
		    private ArrayList<Image> imagel3;
		    private RiskGame theGame1;
		    private Map map;
	    private Country namefrom=null;
	    private Country nameTo=null;
		    public Stage2GUI(RiskGame theGame, Player thecurrent) {
		    	this.theGame1=theGame;
		   theGame1.setStage("Attacking the opposition");
	            
		        JPanel att=new JPanel();
	            this.thecurrentPlayer=thecurrent;
		        att.setBackground(new Color(255, 218, 185));
		        butt1=new JButton("Choose the country to attack from");
		        att.add(butt1);
		        butt1.setLocation(new Point(400,500));
		        butt2=new JButton("Choose the country in your neighbor to attack.");
		        att.add(butt2);
		       
		        butt2.setEnabled(false);
		       
		        butt5=new JButton("Attack(Dice is default)");
		        butt6=new JButton("number of Armies to attack if win");
		        att.add(butt6);
		        att.add(butt5);
		       
		        butt2.setLocation(new Point(300,100));
		        butt3=new JButton("Next:Attacking the opposition Again.");
		        att.add(butt3);
		        butt3.setLocation(new Point(400,100));
		        butt4=new JButton("Fortifying the territories held");
		        att.add(butt4);
		        //cc.add(exp);
		        butt3.setEnabled(false);
		        //butt4.setEnabled(false);
		        butt5.setEnabled(false);
		        butt6.setEnabled(false);
		        JPanel stagePanel = new StagePanel1(theGame1, thecurrent);
		        this.add(stagePanel);
		        
		        stagePanel.setSize(new Dimension(1000, 600));
		        stagePanel.setLocation(0, 0);
		        stagePanel.setBackground(new Color(255, 218, 185));
		        splitPane = new JSplitPane();
		        splitPane.setSize(1000, 600);
		        splitPane.setDividerSize(0);
		        splitPane.setDividerLocation(300);
		        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		        splitPane.setLeftComponent(att);
		        
		        splitPane.setRightComponent(stagePanel);
		        this.add(splitPane);
		        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        this.setSize(new Dimension(1000, 600));
		        this.setLocation(400, 400);
		        this.setTitle("Human Player Attacking");
		        //this.setLayout(null);
		        butt1.addActionListener(new ListenToAttack());
		        butt2.addActionListener(new ListenToAttack1());
		        butt6.addActionListener(new ListenToAttack2());
		        butt5.addActionListener(new ListenToAttack3());
		        butt3.addActionListener(new ListenToAttack4());
		        butt4.addActionListener(new ListenToAttack5());
		        
		    }

		    private class ListenToAttack implements ActionListener {
		        
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            // TODO Auto-generated method stub
		        	 CountryName names = new CountryName();
		             String[] part1 = new String[thecurrentPlayer.getCountries().size()];// names.returnCountryName();
		             for (int i = 0; i < thecurrentPlayer.getCountries().size(); i++) {
		                 part1[i] = thecurrentPlayer.getCountries().get(i).getName();
		             }
		             JPanel asscard = new JPanel();
		             asscard.setPreferredSize(new Dimension(400, 300));
		             JList list = new JList(part1);
		             list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		             asscard.add(new JLabel("Please select the country you want to attack from:"));
		             asscard.add(new JScrollPane(list));
		             int[] cards = new int[0];
		             int result = JOptionPane.showConfirmDialog(null, asscard, "Choose The Country ",
		                                                        JOptionPane.OK_CANCEL_OPTION);
		             if (result == JOptionPane.OK_OPTION) {
		                 cards = list.getSelectedIndices();
		             }
		             String ccccc=part1[cards[0]];
		             
		             butt2.setEnabled(true);
		           
		             Country country = map.getCountryFromMap(ccccc);
		             if(country.getArmies() <= 2||country==null){
		            		JOptionPane.showMessageDialog(null, "As shown in the map, country " + country
		    						+ " has <= 2 armies, so you can't do this step, which means you need to make another choice, or you could jump.");
		            		butt2.setEnabled(false);
		            		return;
		             }
		             JOptionPane.showMessageDialog(null, "You select "+ccccc+" to attack from.");
		             namefrom=country; 
		        }
		        
		    }
		    
		    
	 private class ListenToAttack1 implements ActionListener {
		        
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            // TODO Auto-generated method stub
		        	 CountryName names = new CountryName();
		        	
		             String[] part1 = new String[thecurrentPlayer.getNeighbourAndNotOwnedCountries(namefrom).size()];// names.returnCountryName();
		             for (int i = 0; i < thecurrentPlayer.getNeighbourAndNotOwnedCountries(namefrom).size(); i++) {
		                 part1[i] = thecurrentPlayer.getNeighbourAndNotOwnedCountries(namefrom).get(i).getName();
		             }
		             JPanel asscard = new JPanel();
		             asscard.setPreferredSize(new Dimension(400, 300));
		             JList list = new JList(part1);
		             list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		             asscard.add(new JLabel("Please select the country you want to attack :"));
		             asscard.add(new JScrollPane(list));
		             int[] cards = new int[0];
		             int result = JOptionPane.showConfirmDialog(null, asscard, "Choose The Country ",
		                                                        JOptionPane.OK_CANCEL_OPTION);
		             if (result == JOptionPane.OK_OPTION) {
		                 cards = list.getSelectedIndices();
		             }
		             String ccccc=part1[cards[0]];
		             
		             butt6.setEnabled(true);
		             
		             Country country = map.getCountryFromMap(ccccc);
		            
		             JOptionPane.showMessageDialog(null, "You select "+ccccc+" to attack.");
		             nameTo=country;
		        }
		    }
	 
	 private class ListenToAttack2 implements ActionListener {
	     
		   @Override
	       public void actionPerformed(ActionEvent e) {
	           // TODO Auto-generated method stub
	           CountryName names = new CountryName();
	           String[] part1 = new String[namefrom.getArmies()+1];// names.returnCountryName();
	           for (int i = 0; i <=namefrom.getArmies(); i++) {
	               part1[i] = ""+i;
	           }
	           JPanel asscard = new JPanel();
	           asscard.setPreferredSize(new Dimension(400, 300));
	           JList list = new JList(part1);
	           list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	           asscard.add(new JLabel("If you win how many armies do you want to use to attack"));
	           asscard.add(new JScrollPane(list));
	           int[] cards = new int[0];
	           while (cards.length!= 1) {
	               
	               int result = JOptionPane.showConfirmDialog(null, asscard, "Select the number ",
	                                                          JOptionPane.OK_CANCEL_OPTION);
	               if (result == JOptionPane.OK_OPTION) {
	                   cards = list.getSelectedIndices();
	               }
	           }
	           String ccccc=part1[cards[0]];
	           
	           JOptionPane.showMessageDialog(null, "You select "+ccccc+" armies to attack if you win.");
	           butt5.setEnabled(true);
	            armyNumber11 = Integer.parseInt(ccccc);
	          
	       }
	 }
	 
	 private class ListenToAttack3 implements ActionListener {
	     
		   @Override
	     public void actionPerformed(ActionEvent e) {
	         // TODO Auto-generated method stub
			   thecurrentPlayer.setAttackDiceAmount(2);
			   Player winner = theGame1.attackingHelper(thecurrentPlayer, namefrom, nameTo, armyNumber11);
				if(winner==null){
					JOptionPane.showMessageDialog(null, "You lose from attack!");
				}else  if (winner.equals(thecurrentPlayer)) {
					repaint();
					theGame1.assignNewCards();
					JOptionPane.showMessageDialog(null, "You win from attack!");
				} else {
					JOptionPane.showMessageDialog(null, "You lose from attack!");
				}
				
	         butt4.setEnabled(true);
	        
	         butt3.setEnabled(true);
	         butt3.setEnabled(true);
	     }
	}
	 private class ListenToAttack4 implements ActionListener {
	     
		   @Override
	   public void actionPerformed(ActionEvent e) {
	       // TODO Auto-generated method stub
			   butt2.setEnabled(false);
		      // butt4.setEnabled(false);
		       butt3.setEnabled(false);
		       butt5.setEnabled(false);
		       butt6.setEnabled(false);
					JOptionPane.showMessageDialog(null, "You can attack again!");
		 }
	}
	 private class ListenToAttack5 implements ActionListener {
	     
		   @Override
	 public void actionPerformed(ActionEvent e) {
	     // TODO Auto-generated method stub
				paneeeel1.dispose();
				
				 paneeeel2 = new Stage3GUI(theGame1, thecurrentPlayer);
				
				paneeeel2.setVisible(true);
		 }
	}
		    public class StagePanel1 extends JPanel implements Serializable {
		        private Player thecurrentPlayer;
		        RiskGame theGame22;
		        
		        public StagePanel1(RiskGame theGame, Player thecurrent) {
		            this.theGame22=theGame;
		            this.theGame22.setStage("Attacking the opposition");
		            this.setOpaque(true);
		            this.thecurrentPlayer = thecurrent;
		            imagell = new ArrayList<Image>();
		            imagel2 = new ArrayList<Image>();
		            imagel3 = new ArrayList<Image>();
		            startxl = new ArrayList<Integer>();
		            startyl = new ArrayList<Integer>();
		            map = theGame.getMap();
		            currSpot = 0;
		            try {
		                first = ImageIO.read(new File("image/char1.png"));
		                second = ImageIO.read(new File("image/country_ani.png"));
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		            
		            imagell.add(first);
		            imagell.add(second);
		            startxl.add(500);
		            startxl.add(100);
		            startyl.add(100);
		            startyl.add(200);
		            this.setLayout(null);
		            timer = new Timer(100, new Ticker1());
		            timer.start();
		            
		            // deployingArmies(thecurrent);
		        }
		        
		        private class Ticker1 implements ActionListener {
		            
		            public void actionPerformed(ActionEvent arg0) {
		                repaint();
		            }
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
		            int currspot = 0;
		            for (int i = 0; i < imagell.size(); i++) {
		                int differential;
		                if (i % 2 == 0) {
		                    differential = currSpot;
		                } else {
		                    differential = -currSpot;
		                }
		                g2.drawImage(imagell.get(i), startxl.get(i) + differential, startyl.get(i), null);
		            }
		            if (increasing)
		                currSpot = currSpot + 10;
		            else
		                currSpot = currSpot - 10;
		            
		            if (currSpot == amplitude) {
		                increasing = false;
		                
		            } else if (currSpot == -amplitude)
		                increasing = true;
		            Font myFont = new Font("default", Font.BOLD, 14);
		            g2.setFont(myFont);
		            g2.drawString("Human Player: " + thecurrentPlayer.getName(), 20, 20);
		            g2.drawString("For Attacking, You can jump it, you could choose Fortifying." ,50, 100);
		            
		        }
		    

		    }
	}

	class Stage3GUI extends JFrame implements Serializable {
	    private Player thecurrentPlayer222;
	    
	    JSplitPane splitPane;
	    private Image cardImg;
	    private Image cardBackground;
	    private Image first;
	    private Image second;
	    private Timer timer;
	    private ArrayList<Card> hand;
	    private JButton bu1;
	    private JButton bu2;
	    private JButton bu3;
	    private JButton bu4;
	    private int amplitude = 10;
	    private int currSpot;
	    private ArrayList<Integer> startxl;
	    private ArrayList<Integer> startyl;
	    
	    private boolean increasing = true;
	    private Player thecurrentPlayer;
	    private ArrayList<Image> imagell;
	    private ArrayList<Image> imagel2;
	    private ArrayList<Image> imagel3;
	    private RiskGame theGame8585;
	    private Country from;
	    private Country to;
	    
	    private Map map;
	    public String namecccc;
	    public Stage3GUI(RiskGame theGame, Player thecurrent) {
	    	this.theGame8585=theGame;
	        this.thecurrentPlayer=thecurrent;
	        JPanel cc=new JPanel();
	        cc.setBackground(new Color(255, 218, 185));
	        bu1=new JButton("Countries to fortify from");
	        cc.add(bu1);
	        bu1.setLocation(new Point(400,500));
	        bu2=new JButton("Countries to fortify to");
	        cc.add(bu2);
	        bu2.setEnabled(false);
	        
	        
	        bu2.setLocation(new Point(300,100));
	        bu3=new JButton("Number of Armies to fortify.");
	        cc.add(bu3);
	        bu3.setLocation(new Point(400,100));
	        bu4=new JButton("Next Player's turn");
	        cc.add(bu4);
	        //cc.add(exp);
	        bu4.setLocation(new Point(500,100));
	        bu3.setEnabled(false);
	       
	        JPanel stagePanel = new StagePanel(theGame, thecurrent);
	        this.add(stagePanel);
	        
	        stagePanel.setSize(new Dimension(1000, 600));
	        stagePanel.setLocation(0, 0);
	        stagePanel.setBackground(new Color(255, 218, 185));
	        splitPane = new JSplitPane();
	        splitPane.setSize(1000, 600);
	        splitPane.setDividerSize(0);
	        splitPane.setDividerLocation(300);
	        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
	        splitPane.setLeftComponent(cc);
	        
	        splitPane.setRightComponent(stagePanel);
	        this.add(splitPane);
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        this.setSize(new Dimension(1000, 600));
	        this.setLocation(400, 400);
	        
	        this.setTitle("Human Player Deploying Armies");
	        //this.setLayout(null);
	        bu1.addActionListener(new Wh());
	        bu2.addActionListener(new Wh1());
	        bu3.addActionListener(new Wh2());
	        bu4.addActionListener(new Wh3());
	    }
	    
	 private class Wh3 implements ActionListener {
	        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	        	if (!players.isHuman(players.getPlayer((theGame.returnCurrentPlayer() + 1)
						% theGame.getPlayerList().getPlayerSize()))) {
	        		paneeeel2.dispose();
					update1();
				} else {
					paneeeel2.dispose();
					currPlayer = players.getPlayer(theGame.returnCurrentPlayer());
					decisionMade(theGame.returnCurrentPlayer());
				}
			}
	        }
	    
	 
	    private class Wh implements ActionListener {
	        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            CountryName names = new CountryName();
	            String[] part1 = new String[thecurrentPlayer.getCountries().size()];// names.returnCountryName();
	            for (int i = 0; i < thecurrentPlayer.getCountries().size(); i++) {
	                part1[i] = thecurrentPlayer.getCountries().get(i).getName();
	            }
	            JPanel asscard = new JPanel();
	            asscard.setPreferredSize(new Dimension(400, 300));
	            JList list = new JList(part1);
	            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	            asscard.add(new JLabel("Please select the country you want to fortify from:"));
	            asscard.add(new JScrollPane(list));
	            int[] cards = new int[0];
	            int result = JOptionPane.showConfirmDialog(null, asscard, "Choose The Country ",
	                                                       JOptionPane.OK_CANCEL_OPTION);
	            if (result == JOptionPane.OK_OPTION) {
	                cards = list.getSelectedIndices();
	            }
	            String ccccc=part1[cards[0]];
	            
	            JOptionPane.showMessageDialog(null, "You select "+ccccc+" to fortify from.");
	            bu2.setEnabled(true);
	            namecccc=ccccc;
	        }
	    }
	    
	 private class Wh1 implements ActionListener {
	        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            Country country = map.getCountryFromMap(namecccc);
	            from=country;
	            if(thecurrentPlayer.getNeighbourAndOwnedCountries(country) == null) {
					JOptionPane.showMessageDialog(null,
							"As for now this country has no adjacent country to fortify!Please make another decision.");
	return;
	            }
	            String[] part1 = new String[thecurrentPlayer.getNeighbourAndOwnedCountries(country).size()];// names.returnCountryName();
	            for (int i = 0; i < thecurrentPlayer.getNeighbourAndOwnedCountries(country).size(); i++) {
	                part1[i] = thecurrentPlayer.getNeighbourAndOwnedCountries(country).get(i).getName();
	            }
	            JPanel asscard = new JPanel();
	            asscard.setPreferredSize(new Dimension(400, 300));
	            JList list = new JList(part1);
	            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	            asscard.add(new JLabel("Please select the country you want to fortify from:"));
	            asscard.add(new JScrollPane(list));
	            int[] cards = new int[0];
	            int result = JOptionPane.showConfirmDialog(null, asscard, "Choose The Country ",
	                                                       JOptionPane.OK_CANCEL_OPTION);
	            if (result == JOptionPane.OK_OPTION) {
	                cards = list.getSelectedIndices();
	            }
	            String ccccc=part1[cards[0]];
	            
	            JOptionPane.showMessageDialog(null, "You select "+ccccc+" to fortify from.");
	            to = map.getCountryFromMap(ccccc);
	            bu3.setEnabled(true);
	          
	        }
	    }
	    
	    private class Wh2 implements ActionListener {
	        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            CountryName names = new CountryName();
	            String[] part1 = new String[from.getArmies()+1];// names.returnCountryName();
	            for (int i = 0; i <=from.getArmies(); i++) {
	                part1[i] = ""+i;
	            }
	            JPanel asscard = new JPanel();
	            asscard.setPreferredSize(new Dimension(400, 300));
	            JList list = new JList(part1);
	            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	            asscard.add(new JLabel("Please select the number of you want to fortify:"));
	            asscard.add(new JScrollPane(list));
	            int[] cards = new int[0];
	            while (cards.length!= 1) {
	                
	                int result = JOptionPane.showConfirmDialog(null, asscard, "Select the number ",
	                                                           JOptionPane.OK_CANCEL_OPTION);
	                if (result == JOptionPane.OK_OPTION) {
	                    cards = list.getSelectedIndices();
	                }
	            }
	            String ccccc=part1[cards[0]];
	            int armyNumber = Integer.parseInt(ccccc);
	            if(from.getArmies()-armyNumber<=1){
	            	JOptionPane.showMessageDialog(null,
							"You must leave at least one army in your old terrority, you can't make this change! Choose again!");
	            	bu2.setEnabled(false);
	            	bu3.setEnabled(false);
	            	return;
	            }
	           
	           from.loseArmies(armyNumber);
	           to.loseArmies(armyNumber);
	            bu4.setEnabled(true);
	           
	        }
	        
	    }
	    public class StagePanel extends JPanel implements Serializable {
	        private Player thecurrentPlayer;
	        RiskGame theGame;
	        
	        public StagePanel(RiskGame theGame, Player thecurrent) {
	            this.theGame=theGame;
	            theGame.setStage("Fortifying the territories held");
	            this.setOpaque(true);
	            this.thecurrentPlayer = thecurrent;
	            imagell = new ArrayList<Image>();
	            imagel2 = new ArrayList<Image>();
	            imagel3 = new ArrayList<Image>();
	            startxl = new ArrayList<Integer>();
	            startyl = new ArrayList<Integer>();
	            map = theGame.getMap();
	            currSpot = 0;
	            try {
	                first = ImageIO.read(new File("image/char1.png"));
	                second = ImageIO.read(new File("image/country_ani.png"));
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            
	            imagell.add(first);
	            imagell.add(second);
	            startxl.add(500);
	            startxl.add(100);
	            startyl.add(100);
	            startyl.add(200);
	            this.setLayout(null);
	            timer = new Timer(100, new Ticker1());
	            timer.start();
	            
	            // deployingArmies(thecurrent);
	        }
	        
	        private class Ticker1 implements ActionListener {
	            
	            public void actionPerformed(ActionEvent arg0) {
	                repaint();
	            }
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
	            int currspot = 0;
	            for (int i = 0; i < imagell.size(); i++) {
	                int differential;
	                if (i % 2 == 0) {
	                    differential = currSpot;
	                } else {
	                    differential = -currSpot;
	                }
	                g2.drawImage(imagell.get(i), startxl.get(i) + differential, startyl.get(i), null);
	            }
	            if (increasing)
	                currSpot = currSpot + 10;
	            else
	                currSpot = currSpot - 10;
	            
	            if (currSpot == amplitude) {
	                increasing = false;
	                
	            } else if (currSpot == -amplitude)
	                increasing = true;
	            Font myFont = new Font("default", Font.BOLD, 14);
	            g2.setFont(myFont);
	            g2.drawString("Human Player: " + thecurrentPlayer.getName(), 20, 20);
	            g2.drawString("For Deploying Armies, You can't jump it, but you could jump Attacking or Fortifying." ,50, 100);
	            
	        }
	    }
	}

	}

	  
}