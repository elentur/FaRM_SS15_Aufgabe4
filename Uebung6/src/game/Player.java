package game;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import exception.WrongPlaceException;

public class Player implements Serializable{
	
	private static final int MAX_SHIP_TYP_NUMBER = 5;
	private String name;
	private boolean turn;
	private PlayingField battlefield;
	private List<Ship> ships = new ArrayList<Ship>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Player(String name){
		this.name = name;
		//my field where i save my ships, but not playing with it
		battlefield = new PlayingField();
		createShipsAndPlaceThem();
	}
	
	public String getName(){
		return name;
	}
	
	public List<Ship> getShips(){
		return ships;
	}
	
	private void createShipsAndPlaceThem(){
		Random rnd = new Random();
		int ship;
		int size;
		Point pos;
		boolean horizontal;
		for (int i = 1 ; i < MAX_SHIP_TYP_NUMBER;i++){
			ship = 0;
			size = 6-i;
			while(ship != i){
				//set horizontal or vertical position by random
				horizontal = rnd.nextInt(2) == 1? true:false;
				//pos=startpoint
				pos = new Point(rnd.nextInt(10),rnd.nextInt(10));
				if((horizontal && pos.x+size <=9) || (!horizontal && pos.y+size <=9 )){
					
						try {
							if(i==1)battlefield.addShip(new Battleship(pos,horizontal));
							if(i==2)battlefield.addShip(new Dreadnought(pos,horizontal));
							if(i==3)battlefield.addShip(new Destroyer(pos,horizontal));
							if(i==4)battlefield.addShip(new Submarine(pos,horizontal));
							ship++;
							ships.add(((Ship)battlefield.getBattlefield()[pos.x][pos.y]));
						} catch (WrongPlaceException e) {
							
						}
				}
			}
		}
	}
	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public PlayingField getBattlefield(){
		return battlefield;
	}
	
}
