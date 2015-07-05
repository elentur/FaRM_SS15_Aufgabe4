package game;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;


public class KI implements Runnable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  int[][] kiPlan= new int[10][10];
	private  Point kiShootPos = new Point(0,0);
	private  Ship kiHitShip;
	private  Random rnd = new Random();
	private PlayingField playingField;

	@Override
	
public void run() {
		while(true){
			
			try {
				if(	!Mutex.mutex)playingField= IOSystem.readFile();
				if(playingField.getPlayerTwo().isTurn()){
					shootKI();
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("KI lesefehler"
						+ Mutex.mutex);
			}
			
		}	
}

	
public void shootKI(){
		
		boolean hit = false;
		Point pos = new Point(kiShootPos.x,kiShootPos.y);

		while(!hit){
			//Sollten die positionswerte aus irgend einem grund au�erhalb des Rasters liegen
			//werden zuf�llig neue koordinaten erzeugt die im raster liegen
				if (pos.x > 9 || pos.x < 0) pos.x = rnd.nextInt(10);
				if (pos.y > 9 || pos.y < 0) pos.y = rnd.nextInt(10);
				
					//wenn auf das Feld noch nicht geschossen wurde
					if(kiPlan[pos.x][pos.y] == 0){
						hit =playingField.shoot(pos);
						setKIPlanFlag(pos);
						
					}else if(kiPlan[pos.x][pos.y] == -1){//wenn das aktuelle feld eine Nite ist
						setKIPlanNewPos(pos);					
					}else{
						//wenn zuvor auf ein schiff geschossen habe
						//wandere das schiff in schifsrichtung vorne eins weiter und schiese erneut
						Ship s = ((Ship)playingField.getBattlefieldOne()[pos.x][pos.y]);
						if (!s.isDestroyed()){
							setKIPlanNextPos(pos,s);
							
						}else{
							//wenn das schiff zerst�rt wurde suche wieder ein neues
							kiHitShip=null;
							pos.x += -2 + rnd.nextInt(4);
							pos.y += -2 + rnd.nextInt(4);
						}
						
					}
		}
		kiShootPos.x = pos.x;
		kiShootPos.y = pos.y;
			
	}
	private  void setKIPlanNextPos(Point pos,Ship s) {
		try {Thread.sleep(400);} 
		catch (InterruptedException e) {}
		if (s.isHorizontal() ){
			if (pos.x +1 <10){
				pos.x++;
				
			}else{
				for(int i = 0; i < s.getSize(); i++){
					if(s.hit[i]){
						pos.x = s.getPosition().x +i-1;
						break;
					}
				}
			}
		}else{
			if (pos.y +1 <10){
				pos.y++;
			}else{
				for(int i = 0; i < s.getSize(); i++){
					if(s.hit[i]){
						pos.y = s.getPosition().y +i-1;
						break;
					}
				}
			}
		}
		
	}
	private  void setKIPlanNewPos(Point pos) {
		//wenn zuvor ein schiff getroffen aber nicht versenkt wurde
		//gehe zum Feld eins vor dem ersten des schiffes das getroffen wurde

		if(kiHitShip!= null){
			if(kiHitShip.isHorizontal()){
				for(int i = 0; i < kiHitShip.getSize(); i++){
					if(kiHitShip.hit[i]){
						pos.x = kiHitShip.getPosition().x +i-1;
						try {Thread.sleep(700);} 
						catch (InterruptedException e) {}
						break;
					}	
				}
			}else{
				for(int i = 0; i < kiHitShip.getSize(); i++){
					if(kiHitShip.hit[i]){
						pos.y = kiHitShip.getPosition().y+i-1;
						try {Thread.sleep(700);} 
						catch (InterruptedException e) {}
						break;
					}
				}
			}
		}else{
			// sonst suche in der umgebung ein schiff
			try {Thread.sleep(300);} 
			catch (InterruptedException e) {}
			pos.x += -2 + rnd.nextInt(4);
			pos.y += -2 + rnd.nextInt(4);
		}
		
	}
	private  void setKIPlanFlag(Point pos) {
		if (playingField.getBattlefieldOne()[pos.x][pos.y] instanceof Ship){
			kiPlan[pos.x][pos.y] =1;
			kiHitShip = ((Ship)playingField.getBattlefieldOne()[pos.x][pos.y]);
			try {Thread.sleep(300);} 
			catch (InterruptedException e) {}
		}else{
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {}
			kiPlan[pos.x][pos.y] =-1;
		}
	}

}
