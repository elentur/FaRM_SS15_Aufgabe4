package game;




import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
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
	Thread KIThread;
	Thread colorRefresh;
	Point mousePoint = new Point(0,0);

	public Control(VBox down,VBox up, VBox divider){
		this.down =down;
		this.up = up;
		this.divider=divider;
		this.divider.getChildren().addAll(lblInfText);
		initGame();	 
	}
	
	private void initGame() {

		me = new Player("test");
		me.setTurn(true);
		enemie= new Player("enemie");
		enemie.setTurn(false);
		IOSystem.writeFile(me);
		IOSystem.writeFile(enemie);
		infoText = new InfoText(me,enemie) ;
		bf = me.getBattlefield().getBattlefield();
		bf2 = enemie.getBattlefield().getBattlefield();
		createGUIGrid();	
		loadImageBuffer();
		//refreshColor();
		//setText();
		KIThread = new Thread(new KI(me,infoText,this));
		KIThread.start();
		colorRefresh = new Thread(new GUIRefresh(this));
		colorRefresh.start();
	}
	
	private void createGUIGrid() {
		for(int i = 0; i<10 ; i++){
			rowDown[i] = new HBox();
			rowDown[i].minWidthProperty().bind(down.prefWidthProperty());
			rowDown[i].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
			rowUp[i] = new HBox();
			rowUp[i].minWidthProperty().bind(down.prefWidthProperty());
			rowUp[i].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
			down.getChildren().add(rowDown[i]);
			up.getChildren().add(rowUp[i]);
			for(int j = 0; j<10 ; j++){
				columnDown[i][j] = new VBox();
				columnDown[i][j].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
				columnDown[i][j].minWidthProperty().bind(down.prefWidthProperty().divide(10.0));				
				rowDown[i].getChildren().add(columnDown[i][j]);
				columnUp[i][j] = new VBox();
				columnUp[i][j].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
				columnUp[i][j].minWidthProperty().bind(down.prefWidthProperty().divide(10.0));
				Figure  f = bf[i][j];
				Point pos = new Point(i,j);
				columnUp[i][j].setOnMouseClicked(e -> clickEvent(e,f,pos));
				columnUp[i][j].setOnMouseEntered(e -> HoverEvent(e,pos, true));
				columnUp[i][j].setOnMouseExited(e -> HoverEvent(e,pos,false));
				rowUp[i].getChildren().add(columnUp[i][j]);
			}
		}	
	}
	
	private void loadImageBuffer() {
		down.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		up.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));			
		
	}
	
	private void HoverEvent(MouseEvent e, Point cord, boolean enter ){
		//refreshColor();
		//setText();
		me = IOSystem.readFile(me);
		enemie = IOSystem.readFile(enemie);
		
		if (enter){
			mousePoint.x = cord.x;
			mousePoint.y = cord.y;
			columnUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null,null)));
		}else{
			columnUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, null, null)));
		}
	}
	
	public void refreshColor(){
	BackgroundSize bgSize = new BackgroundSize(0, 0, false, false, true, false);
		bf = me.getBattlefield().getBattlefield();
		bf2 = enemie.getBattlefield().getBattlefield();
		
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				Figure  f = bf2[i][j];	
				if( i != mousePoint.x && j != mousePoint.y) columnUp[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
				if (f== null){ 
					columnUp[i][j].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
					
				}else if (f instanceof Pin){
					columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null, null, bgSize)));
				}else{
					Ship s = ((Ship)f);
					int picNum = i - s.position.x + j - s.position.y;
					if(s.isDestroyed()) {
						if (f instanceof Battleship)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/battleship" + picNum + ".png"), null,null, null, bgSize)));
						if (f instanceof Dreadnought)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/dreadnought" + picNum + ".png"), null, null, null, bgSize)));
						if (f instanceof Destroyer){
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/destroyer" + picNum + ".png"), null, null, null, bgSize)));
						}
						if (f instanceof Submarine)
							columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/submarine" + picNum + ".png"), null, null, null, bgSize)));
						if (!((Ship)f).isHorizontal()) columnUp[i][j].setRotate(-90);
						columnUp[i][j].setBackground(new Background(columnUp[i][j].getBackground().getImages().get(0),(new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, bgSize))));
					}else if(s.isHit(new Point(i,j))){
						columnUp[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/redFlag.png"), null, null, null, bgSize)));
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
					columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null, null, bgSize)));
				}else{
					Ship s = ((Ship)f);
					int picNum = i - s.position.x + j - s.position.y;
					if(s.isHit(new Point(i,j))){
						columnDown[i][j].setBackground(new Background(columnDown[i][j].getBackground().getImages().get(0),(new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, bgSize))));
					}else{
							if (f instanceof Battleship)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/battleship" + picNum + ".png"), null, null, null, bgSize)));
							if (f instanceof Dreadnought)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/dreadnought" + picNum + ".png"), null, null, null, bgSize)));
							if (f instanceof Destroyer){
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/destroyer" + picNum + ".png"), null, null, null, bgSize)));
							}
							if (f instanceof Submarine)
								columnDown[i][j].setBackground(new Background(new BackgroundImage(new Image("/pictures/submarine" + picNum + ".png"), null, null, null, bgSize)));
							if (!((Ship)f).isHorizontal()) columnDown[i][j].setRotate(-90);
					}
				}
			
			}
		}
		
	}
	
	public void setText(){
		lblInfText.setText(infoText.info());
	}
	
	public void clickEvent(MouseEvent e, Figure f, Point pos){
		if (e.getClickCount()==2 && me.isTurn()){
			infoText.setMsg("");
			if(!enemie.getBattlefield().shoot(pos)){
				infoText.setMsg("Darauf hast du bereits geschossen");
				setText();
				return;
			}	
			checkVictory(enemie);
			me.setTurn(false);
			IOSystem.writeFile(me);
			IOSystem.writeFile(enemie);
			enemie = IOSystem.readFile(enemie);
		//	refreshColor();
			//setText();
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
	
	
	
}