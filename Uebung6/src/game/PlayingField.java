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
	private boolean testMode;
	
	public PlayingField( boolean testMode){
		battlefieldOne = new Figure[10][10];
		battlefieldTwo = new Figure[10][10];
		playerOne = new Player("Spieler",battlefieldOne,battlefieldTwo);
		playerTwo = new Player("KI",battlefieldTwo,battlefieldOne);
		this.testMode=testMode;
	}

	
	public String print(){
		Figure[][]  battlefield;
		String s="";
		for (int n = 0; n<2;n++){
			if (n==0){
				battlefield = battlefieldOne;
			}else{
				battlefield = battlefieldTwo;
			}
			for (int i = 0; i <10; i++){
				for (int j = 0; j <10; j++){
					if(battlefield[j][i] == null){
						s= s+("0 ");
					}else if(battlefield[j][i] instanceof Pin){
						s= s+("W ");
					}else if(((Ship)battlefield[j][i]).isHit(new Point(j,i))){
						s= s+("R ");
					}else{
						s= s+(((Ship)battlefield[j][i]).getSize() + " ");
					}
				}
				s=s+"\r\n";
			}
			s=s+"\r\n";
		}
		return s;
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
		checkVictory();
		Mutex.setMutex(true);
			IOSystem.writeFile(this);
			if(testMode){
				IOSystem.writeTurnMap(this);
				System.out.println(print());
				System.exit(0);
			}
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
	private void checkVictory(){
		int ships =10;
		for (int i=0 ; i<10;i++){
			if(playerOne.getShips().get(i).isDestroyed())ships--;
		}
		if (ships ==0){
			System.out.println(playerTwo.getName() + " hat das Spiel gewonnen.");
			System.exit(0);
		}
		ships =10;
		for (int i=0 ; i<10;i++){
			if(playerTwo.getShips().get(i).isDestroyed())ships--;
		}
		if (ships ==0){
			System.out.println(playerOne.getName() + " hat das Spiel gewonnen.");
			System.exit(0);
		}
	}
}
