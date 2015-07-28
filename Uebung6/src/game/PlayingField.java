package game;

import java.awt.Point;
import java.io.Serializable;
import java.net.Socket;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayingField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Figure[][]  battlefieldOne;
	private Figure[][]  battlefieldTwo;
	private Player playerOne;
	private Player playerTwo;
	private String chat="";
	
	
	public PlayingField(){
		battlefieldOne = new Figure[10][10];
		battlefieldTwo = new Figure[10][10];
		playerOne = new Player(battlefieldOne,battlefieldTwo);
		playerTwo = new Player(battlefieldTwo,battlefieldOne);

		
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
		String name="";
		if (playerOne.isTurn()){
			battlefield = battlefieldTwo;
			name = playerOne.getName();
		}else{
			battlefield = battlefieldOne;
			name = playerTwo.getName();
		}
		
		if(battlefield[pos.x][pos.y]== null){
			battlefield[pos.x][pos.y] = new Pin();
			addChat(name + " hat daneben geschossen.");
			PlayAudio.playWater();
		}else if(battlefield[pos.x][pos.y] instanceof Ship ){
			Ship ship = (Ship)battlefield[pos.x][pos.y];
			if(!ship.hitShip(pos)  ){
				return false;
			}
			if (ship.isDestroyed()){
				addChat(name + " hat ein(en) " + ship.getName() + " versenkt.");
			}else{
				addChat(name + " hat getroffen.");
			}
			PlayAudio.playShip();
			
		}else {
			return false;
		}
		
		playerOne.setTurn(!playerOne.isTurn());
		playerTwo.setTurn(!playerTwo.isTurn());
		checkVictory();
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
	private void checkVictory(){
		int ships = 10;
		for (int i=0 ; i<10;i++){
			if(playerOne.getShips().get(i).isDestroyed())ships--;
		}
		if (ships ==0){
			//System.out.println(playerTwo.getName() + " hat das Spiel gewonnen.");
			addChat(playerTwo.getName() + " hat das Spiel gewonnen.");
			//System.exit(0);
		}
		ships =10;
		for (int i=0 ; i<10;i++){
			if(playerTwo.getShips().get(i).isDestroyed())ships--;
		}
		if (ships ==0){
			//System.out.println(playerOne.getName() + " hat das Spiel gewonnen.");
			addChat(playerOne.getName() + " hat das Spiel gewonnen.");
			//System.exit(0);
		}
	}
	
	public String getChat(){
		return chat;
	}
	
	public void addChat(String text){
		chat = chat + "\n" +text;
	}
}
