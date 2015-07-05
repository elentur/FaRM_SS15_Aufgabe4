package game;

import java.awt.Point;
import java.io.Serializable;

public class PlayingField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Figure[][]  battlefieldOne;
	private Figure[][]  battlefieldTwo;
	private Player playerOne;
	private Player playerTwo;
	public PlayingField(){
		battlefieldOne = new Figure[10][10];
		battlefieldTwo = new Figure[10][10];
		playerOne = new Player("Spieler",battlefieldOne,battlefieldTwo);
		playerTwo = new Player("KI",battlefieldTwo,battlefieldOne);
	}

	
	public void print(){
		Figure[][]  battlefield;
		for (int n = 0; n<2;n++){
			
			if (n==0){
				battlefield = battlefieldOne;
			}else{
				battlefield = battlefieldTwo;
			}
		for (int i = 0; i <10; i++){
			for (int j = 0; j <10; j++){
				if(battlefield[i][j] == null) System.out.print("0 ");
				else System.out.print(((Ship)battlefield[i][j]).getSize() + " ");
			}
			System.out.println();
		}
		System.out.println();
		}
	}
	public boolean  shoot(Point pos){
		Figure[][]	battlefield;
		if (playerOne.isTurn()){
			battlefield = battlefieldTwo;
		}else{
			battlefield = battlefieldOne;
		}
		
		if(battlefield[pos.x][pos.y]== null){
			battlefield[pos.x][pos.y] = new Pin();
		}else if(battlefield[pos.x][pos.y] instanceof Ship ){
			Ship ship = (Ship)battlefield[pos.x][pos.y];
			if(!ship.hitShip(pos)  ){
				return false;
			}
		}else {
			return false;
		}
		
		playerOne.setTurn(!playerOne.isTurn());
		playerTwo.setTurn(!playerTwo.isTurn());
		
		Mutex.setMutex(true);
			IOSystem.writeFile(this);
		Mutex.setMutex(false);
		
		return true;
	}
	public Figure[][] getBattlefieldOne(){
		return battlefieldOne;
	}
	public Figure[][] getBattlefieldTwo(){
		return battlefieldTwo;
	}
	public Player getPlayerOne(){
		return playerOne;
	}
	public Player getPlayerTwo(){
		return playerTwo;
	}
}
