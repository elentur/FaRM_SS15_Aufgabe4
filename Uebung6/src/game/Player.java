package game;

import java.awt.Point;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import exception.WrongPlaceException;

public class Player implements Serializable{
	
	private static final int MAX_SHIP_TYP_NUMBER = 5;
	private String name;
	private boolean turn;
	private Figure[][] myBattlefield, enemiesBattlefield;
	private List<Ship> ships = new ArrayList<Ship>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Player(String name,
			Figure[][] myBattlefield,Figure[][] enemiesBattlefield){
		this.name = name;
		this.myBattlefield=myBattlefield;
		this.enemiesBattlefield = enemiesBattlefield;
		//my field where i save my ships, but not playing with it
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
							if(i==1)addShip(new Battleship(pos,horizontal));
							if(i==2)addShip(new Dreadnought(pos,horizontal));
							if(i==3)addShip(new Destroyer(pos,horizontal));
							if(i==4)addShip(new Submarine(pos,horizontal));
							ship++;
							ships.add(((Ship)myBattlefield[pos.x][pos.y]));
						} catch (WrongPlaceException e) {
							
						}
				}
			}
		}
	}
	
	public void  addShip(Ship ship) throws WrongPlaceException{
		Figure[][]  battlefield = myBattlefield;
			for(int i = 0; i <ship.getSize();i++ ){
				if(ship.isHorizontal()){
					if(battlefield[ship.getPosition().x+i][ship.getPosition().y]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().y-1 >= 0 && battlefield[ship.getPosition().x+i][ship.getPosition().y-1]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().y+1 < 10 && battlefield[ship.getPosition().x+i][ship.getPosition().y+1]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().x+1 < 10 && battlefield[ship.getPosition().x+i+1][ship.getPosition().y]!=null){
						throw new WrongPlaceException();
					}else if(i == 0 && ship.getPosition().x-1 >= 0 && battlefield[ship.getPosition().x+i-1][ship.getPosition().y]!=null){
						throw new WrongPlaceException();
					}
					
				}else if(!ship.isHorizontal()){
					if(battlefield[ship.getPosition().x][ship.getPosition().y+i]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().x-1 >= 0 && battlefield[ship.getPosition().x-1][ship.getPosition().y+i]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().x+1 < 10 && battlefield[ship.getPosition().x+1][ship.getPosition().y+i]!=null){
						throw new WrongPlaceException();
					}else if(ship.getPosition().y+1 < 10 && battlefield[ship.getPosition().x][ship.getPosition().y+i+1]!=null){
						throw new WrongPlaceException();
					}else if(i == 0 && ship.getPosition().y-1 >= 0 && battlefield[ship.getPosition().x][ship.getPosition().y+i-1]!=null){
						throw new WrongPlaceException();
					}
					
				}
					
			}

			for(int i = 0; i <ship.getSize();i++ ){
				if(ship.isHorizontal()){
					battlefield[ship.getPosition().x+i][ship.getPosition().y]=ship;
				}else if(!ship.isHorizontal()){
					battlefield[ship.getPosition().x][ship.getPosition().y+i]= ship;
				}else{
					throw new WrongPlaceException();
				}
					
			}
		
	}
	
	
	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	
}
