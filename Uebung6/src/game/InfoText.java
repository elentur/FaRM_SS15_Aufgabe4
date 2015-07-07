package game;

import java.util.List;

public class InfoText implements Figure {

	/**
	 * 
	 */	
	private  int lap = 1;
	private  int ship1=10;
	private  int ship2 =10;
	private  String msg ="";
	private String waitingMsg="";
	private  Player p1,p2;
	private boolean wait;
	
	private static final long serialVersionUID = 1L;
	
	public    InfoText(){
		
	}
	public    InfoText(Player p1,Player p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	

	public int getLap() {
		return lap;
	}


	public void setWaiting(boolean wait){
		this.wait = wait;
		if(wait){
			waitingMsg = "Warte auf anderen Spieler...";
		}else{
			waitingMsg="";
		}
	}

	public void setLap(int lap) {
		this.lap = lap;
	}




	public int getShip1() {
		return ship1;
	}




	public void setShip1(List<Ship> ship1) {
		this.ship1 = 10;
		for (int i=0 ; i<10;i++){
			if(ship1.get(i).isDestroyed())this.ship1--;
		}
	}




	public int getShip2() {
		return this.ship2;
	}




	public void setShip2(List<Ship> ship2) {
		this.ship2 = 10;
		for (int i=0 ; i<10;i++){
			if(ship2.get(i).isDestroyed())this.ship2--;
		}
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}




	public  String info(){
		
			
			return 	"Runde: " +lap + "\n" + p1.getName() +"s Schiffe: " + ship1 + "\n" + p2.getName() +"s Schiffe: " + ship2 + "\n" + msg + " " + waitingMsg;
			//return  msg + " " + waitingMsg;

		
	}
}
