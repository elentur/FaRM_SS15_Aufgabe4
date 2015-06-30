package game;

import java.awt.Point;
import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import exception.WrongPlaceException;

public class PlayingField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Figure[][]  battlefield;
		
	public PlayingField(){
		battlefield = new Figure[10][10];
		
	}
	
	
	public void  addShip(Ship ship) throws WrongPlaceException{
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
	
	public void print(){

		for (int i = 0; i <10; i++){
			for (int j = 0; j <10; j++){
				if(battlefield[i][j] == null) System.out.print("0 ");
				else System.out.print(((Ship)battlefield[i][j]).getSize() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public boolean  shoot(Point pos){
		if(battlefield[pos.x][pos.y]== null){
			battlefield[pos.x][pos.y] = new Pin();
			return true;
		}else if(battlefield[pos.x][pos.y] instanceof Ship ){
			Ship ship = (Ship)battlefield[pos.x][pos.y];
			if(!ship.hitShip(pos)  ){
				return false;
			}
		}else {
			return false;
		}
		
		return true;
	}
	public Figure[][] getBattlefield(){
		return battlefield;
	}
}
