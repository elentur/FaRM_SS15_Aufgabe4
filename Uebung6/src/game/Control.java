package game;




import java.awt.Point;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Control {
	HBox[] rowDown = new HBox[10];
	VBox[][] columnDown = new VBox[10][10];
	HBox[] rowUp = new HBox[10];
	VBox[][] columnUp = new VBox[10][10];
	Player me,enemie;
	Figure[][] bf,bf2;
	VBox down,up,divider;
	InfoText infoText;
	Label lblInfText = new Label();
	int[][] kiPlan= new int[10][10];
	Point kiShootPos = new Point(0,0);

	public Control(VBox down,VBox up, VBox divider){
		this.down =down;
		this.up = up;
		this.divider=divider;
		this.divider.getChildren().addAll(lblInfText);
		this.down.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		this.up.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		me = new Player("test");
		enemie= new Player("enemie");
		infoText = new InfoText(me,enemie) ;
		bf = me.getBattlefield().getBattlefield();
		bf2 = enemie.getBattlefield().getBattlefield();
		for(int i = 0; i<10 ; i++){
			rowDown[i] = new HBox();
			rowDown[i].setMinSize(400,40);
			rowUp[i] = new HBox();
			rowUp[i].setMinSize(400,40);
			this.down.getChildren().add(rowDown[i]);
			this.up.getChildren().add(rowUp[i]);
			for(int j = 0; j<10 ; j++){
				columnDown[i][j] = new VBox();
				columnDown[i][j].setMinSize(40,40);
				
				rowDown[i].getChildren().add(columnDown[i][j]);
				columnUp[i][j] = new VBox();
				columnUp[i][j].setMinSize(40,40);
				Figure  f = bf[i][j];
				Point pos = new Point(i,j);
				columnUp[i][j].setOnMouseClicked(e -> clickEvent(e,f,pos));
				columnUp[i][j].setOnMouseEntered(e -> HoverEvent(e,pos, true));
				columnUp[i][j].setOnMouseExited(e -> HoverEvent(e,pos,false));
				columnUp[i][j].setPrefHeight(40);
				columnUp[i][j].setPrefWidth(40);
				rowUp[i].getChildren().add(columnUp[i][j]);
			}
		}
		setColor();
		setText();
	     
		 
	}
	private void HoverEvent(MouseEvent e, Point cord, boolean enter ){
		if (enter){
			columnUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null,null)));
		}else{
			columnUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		}
	}
	
	private void setColor(){
		
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				Figure  f = bf2[i][j];	
				columnUp[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
				if (f== null){
					columnUp[i][j].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
					
				}else if (f instanceof Pin){
					columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null, null, null)));
				}else{
					Ship s = ((Ship)f);
					int picNum = i - s.position.x + j - s.position.y;
					if(s.isDestroyed()) {
						if (f instanceof Battleship)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/battleship" + picNum + ".png"), null, null, null, null)));
						if (f instanceof Dreadnought)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/dreadnought" + picNum + ".png"), null, null, null, null)));
						if (f instanceof Destroyer){
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/destroyer" + picNum + ".png"), null, null, null, null)));
						}
						if (f instanceof Submarine)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/submarine" + picNum + ".png"), null, null, null, null)));
						if (!((Ship)f).isHorizontal()) columnUp[i][j].setRotate(-90);
						columnUp[i][j].setBackground(new Background(columnUp[i][j].getBackground().getImages().get(0),(new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, null))));
					}else if(s.isHit(new Point(i,j))){
						columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/redFlag.png"), null, null, null, null)));
					}
					
				}
			
			}
		}
		
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				Figure  f = bf[i][j];	
				columnDown[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
				if (f== null){
					columnDown[i][j].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
				}else if (f instanceof Pin){
					columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null, null, null)));
				}else{
					Ship s = ((Ship)f);
					int picNum = i - s.position.x + j - s.position.y;
					if(s.isHit(new Point(i,j))){
						columnDown[i][j].setBackground(new Background(columnDown[i][j].getBackground().getImages().get(0),(new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, null))));
					}else{
							if (f instanceof Battleship)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/battleship" + picNum + ".png"), null, null, null, null)));
							if (f instanceof Dreadnought)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/dreadnought" + picNum + ".png"), null, null, null, null)));
							if (f instanceof Destroyer){
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/destroyer" + picNum + ".png"), null, null, null, null)));
							}
							if (f instanceof Submarine)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/submarine" + picNum + ".png"), null, null, null, null)));
							if (!((Ship)f).isHorizontal()) columnDown[i][j].setRotate(-90);
					}
				}
			
			}
		}
		
		
		
	}
	private void setText(){
		lblInfText.setText(infoText.info());
	}
	
	public void clickEvent(MouseEvent e, Figure f, Point pos){
		if (e.getClickCount()==2){
			infoText.setMsg("");
			if(!enemie.getBattlefield().shoot(pos)){
				infoText.setMsg("Darauf hast du bereits geschossen");
				return;
			}
			checkVictory(enemie);
			shootKI();
			checkVictory(me);
			infoText.setLap(infoText.getLap()+1);
			setColor();
			setText();
		}
		
	}
	
	private void checkVictory(Player player){
		if (player == me){
			infoText.setShip1(10);
			for(int i = 0; i<player.getShips().size() ; i++){
				if (player.getShips().get(i).isDestroyed() ) infoText.setShip1(infoText.getShip1()-1);
			}
			if(infoText.getShip1() == 0)	infoText.setMsg(enemie.getName() + " hat gewonnen.");
		}else{
			infoText.setShip2(10);
			for(int i = 0; i<player.getShips().size() ; i++){
				if (player.getShips().get(i).isDestroyed() ) infoText.setShip2(infoText.getShip2()-1);
			}
			if(infoText.getShip2() == 0)	infoText.setMsg(me.getName() + " hat gewonnen.");
		}
		
		setText();
	}
	
	private void shootKI(){
		boolean hit = false;
		Point pos = new Point(kiShootPos.x,kiShootPos.y);
		Random rnd = new Random();
		while(!hit){
					if (pos.x > 9 || pos.x < 0) pos.x = rnd.nextInt(10);
					if (pos.y > 9 || pos.y < 0) pos.x = rnd.nextInt(10);
					if(kiPlan[pos.x][pos.y] == 0){
						hit =me.getBattlefield().shoot(pos);
						if (me.getBattlefield().getBattlefield()[pos.x][pos.y] instanceof Ship){
							kiPlan[pos.x][pos.y] =1;
						}else{
							kiPlan[pos.x][pos.y] =-1;
						}
						
					}else 	if(kiPlan[pos.x][pos.y] == -1){
						pos.x += rnd.nextInt(2);
						pos.y += rnd.nextInt(2);
					}else{
						//wenn ausrichtung bekannt
						Ship s = ((Ship)me.getBattlefield().getBattlefield()[pos.x][pos.y]);
						if (s.isHorizontal()){
							
						}
					}

		}
		kiShootPos.x = pos.x;
		kiShootPos.y = pos.y;
	}
}