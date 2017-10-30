package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



public class PlayerList implements Serializable{
	
	//public static PlayerList thisPlayerList;
	private  ArrayList<String> colors;
	public static PlayerList thisPlayerList;
	private ArrayList<Player> players;
	private ArrayList<ComputerPlayer> computerPlayers;
	private ArrayList<HumanPlayer> humanPlayers;
	public PlayerList() {
		players = new ArrayList<Player>();
		colors=new ArrayList<String>();
		humanPlayers=new ArrayList<HumanPlayer>();
		computerPlayers=new ArrayList<ComputerPlayer>();
		addColor();
	}


	public String cardToStringInPlayerList(){
		String res="";
		for(int i=0;i< players.size();i++){
			res=res+players.get(i).handToStringInPlayer()+"\n";
		}
		return res;
	}


	public void remove(Player player){
		players.remove(player);
	}
/**
 * the color is matched with each player 
 */
	private void addColor( ) {
		// TODO Auto-generated method stub
		colors.add("blue");
		colors.add("green");
		colors.add("purple");
		colors.add("orange");
		colors.add("red");
		colors.add("yellow");
		
	}
	public String getPlayerColor(String name) {
		for(int i=0;i<players.size();i++){
			if(players.get(i).getName().compareTo(name)==0){
				return players.get(i).getColor();
			}
		}
		return "ERROR";
	}
	public String getColor(int i){
		return colors.get(i);
	}

	public void addPlayers(Player player) {
		players.add(player);
	}
	
	public Player iterateToNextPlayer(Player player){
		int index = -1;
		for (Player p:players){
			if (p.equals(player)){
				index = players.indexOf(player);
				break;
			}
		}
		if (index==-1)
			return null;
		else
		{
			if (index==players.size()-1){
				index=0;
			}
			else if (index<players.size()-1){
				index++;
			}
			else
			{
				System.out.println("wrong index input, find the bug!");
				return null;
			}
			
			return players.get(index);
		}
		
	}
	
	public Player getPlayerByIndex(int i){
		return players.get(i);
	}


	public int getPlayerSize() {
		return players.size();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public boolean isHuman(Player player){
		if(player.getClass().equals(HumanPlayer.class)){
			return true;
		}
		else
			return false;
	}
	
	public void shuffle(){
		Collections.shuffle(players);
		resetColor();
		System.out.println(colorToString());
	}

	private void resetColor() {
		for (int i=0;i<players.size();i++){
			players.get(i).setColor(colors.get(i));
			System.out.println("reseted player "+players.get(i).getName());
		}
	}


	public String getPlayerColor(int i) {
		return players.get(i).getColor();
	}
	
	public Player getPlayerByName(String name){
		for (Player p:players){
			if (p.getName().equals(name)){
				return p;
			}
		}
		return null;
	}
	
	public String countriesToString(){
		String ret="";
		for(int i=0;i<players.size();i++){
			ret+=(players.get(i).getName() +"has "+players.get(i).getCountries().size()+" countries:");
			for(int k=0;k<players.get(i).getCountries().size(); k++){
				ret+=("   "+players.get(i).getCountries().get(k));
			}
			ret+="\n";
		}
		
		return ret;
	}
	
	public String colorToString(){
		String ret="";
		for(int i=0;i<players.size();i++){
			ret+=(players.get(i).getName() +"is "+players.get(i).getColor());
			
			ret+="\n";
		}
		
		return ret;
	}


	public int getNumberOfAI() {
		int sum = 0;
		if (players != null && players.size() != 0)
			for (int i = 0; i < players.size(); i++)
				if (players.get(i).getClass().equals(ComputerPlayer.class))
					sum++;
		return sum;
	}

	public ArrayList<ComputerPlayer> getAI() {
		// TODO Auto-generated method stub
		
		if (players != null && players.size() != 0)
			for (int i = 0; i < players.size(); i++)
				if (players.get(i).getClass().equals(ComputerPlayer.class)){
					 computerPlayers.add((ComputerPlayer) players.get(i));
				}
		
		return computerPlayers;
	
	}
	public ArrayList<HumanPlayer> getHumanPlayer() {
		// TODO Auto-generated method stub
		
		if (players != null && players.size() != 0)
			for (int i = 0; i < players.size(); i++)
				if (players.get(i).getClass().equals(HumanPlayer.class)){
					humanPlayers.add((HumanPlayer) players.get(i));
				}
		System.out.println("size is-------"+humanPlayers.size());
		return humanPlayers;
	}

}
