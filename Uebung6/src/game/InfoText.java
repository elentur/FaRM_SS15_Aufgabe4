package game;

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




	public void setShip1(int ship1) {
		this.ship1 = ship1;
	}




	public int getShip2() {
		return ship2;
	}




	public void setShip2(int ship2) {
		this.ship2 = ship2;
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}




	public  String info(){
		
			
			//return 	"Runde: " +lap + "\n" + p1.getName() +"s Schiffe: " + ship1 + "\n" + p2.getName() +"s Schiffe: " + ship2 + "\n" + msg;
			return  msg + " " + waitingMsg;

		
	}
}
