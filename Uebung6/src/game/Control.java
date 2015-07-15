package game;




import java.awt.Point;
import java.io.IOException;


import java.net.Socket;
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
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Control {
	HBox[] rowDown = new HBox[10];
	VBox[][] columnDown = new VBox[10][10];
	HBox[] rowUp = new HBox[10];
	VBox[][] columnUp = new VBox[10][10];
	PlayingField playingField;
	Figure[][] bfUp,bfDown;
	VBox down,up,divider;
	InfoText infoText;
	Label lblInfText = new Label();
	Thread KIThread;
	Thread colorRefresh;
	Thread readGame;
	Point mousePoint = new Point(-1,-1);
	Color bgStrokeColor;
	boolean hostPlayer;
	boolean loading;
	BackgroundImage[] imgBattleship= new BackgroundImage[5];
	BackgroundImage[] imgDreadnought= new BackgroundImage[4];
	BackgroundImage[] imgDestroyer= new BackgroundImage[3];
	BackgroundImage[] imgSubmarine= new BackgroundImage[2];
	BackgroundImage imgRedFlag;
	BackgroundImage imgWhiteFlag;
	BackgroundImage imgHit;
	Socket socket;
	
	
	public Control(VBox down,VBox up, VBox divider,
			boolean hostPlayer, boolean loading, Socket socket){
		this.down =down;
		this.up = up;
		this.divider=divider;
		this.divider.getChildren().addAll(lblInfText);
		this.hostPlayer=hostPlayer;
		this.loading =loading;
		this.socket=socket;
		IOSystem.socket=socket;
		initGame();	 
	}
	
	private void initGame() {
		
		if(loading){
			try {
				
				playingField = IOSystem.readFile();
			} catch (ClassNotFoundException | IOException e) {
				
				System.out.println("Spiel nicht vorhanden.");
			}
		}else{
			playingField = new PlayingField();
			if(hostPlayer){
				playingField.getPlayerOne().setTurn(true);
				IOSystem.writeFile(playingField);
			}
			
		}
		
		 infoText = new InfoText(playingField.getPlayerOne(),playingField.getPlayerTwo());
		 
		createGUIGrid();	
		loadImageBuffer();
		if(socket == null){
			KIThread = new Thread(new KI());
			KIThread.setDaemon(true);
			KIThread.start();
		}
		readGame = new Thread(new Runnable(){
			@Override
		public void run() {
			while (true){				
				readGameFile();
			}}});

		readGame.setDaemon(true);
		readGame.start();
		
		colorRefresh = new Thread(new Runnable(){
			@Override
			public void run() {
				while (true){
					refreshColor();
				}}});
		colorRefresh.setDaemon(true);
		colorRefresh.start();
	}
	
	private void readGameFile() {
		try {
			Thread.sleep(1000);
			if(!Mutex.mutex)playingField = IOSystem.readFile();
			infoText.setShip1(playingField.getPlayerOne().getShips());
			infoText.setShip2(playingField.getPlayerTwo().getShips());
		} catch (ClassNotFoundException |InterruptedException| IOException e) {
			System.out.println("Spieler lesefehler");
		}
	}

	private void shootRandom(){
	Random rnd = new Random();
	Point pos = new Point();
		while(true){
			if(isAktivPlayersTurn()){
				infoText.setMsg("");
				pos.x=rnd.nextInt(10);
				pos.y=rnd.nextInt(10);
				playingField.shoot(pos);
	
				
			}
		}
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
				Figure  f = playingField.getBattlefieldTwo()[i][j];
				Point pos = new Point(i,j);
				columnUp[i][j].setOnMouseClicked(e -> clickEvent(e,f,pos));
				columnUp[i][j].setOnMouseEntered(e -> HoverEvent(e,pos));
				rowUp[i].getChildren().add(columnUp[i][j]);
			}
		}
	}
	
	private void loadImageBuffer() {
		BackgroundSize bgSize = new BackgroundSize(0, 0, false, false, true, false);
		down.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		up.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));			
		imgRedFlag = new BackgroundImage(new Image("/pictures/redFlag.png"), null, null, null, bgSize);
		imgWhiteFlag = new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null, null, bgSize);
		imgHit = new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, bgSize);
		
		for (int i = 0; i< 5;i++){
			imgBattleship[i] = new BackgroundImage(new Image("/pictures/battleship" + i + ".png"), null, null, null, bgSize); 
			if(i<4)imgDreadnought[i] = new BackgroundImage(new Image("/pictures/dreadnought" + i + ".png"), null, null, null, bgSize); 
			if(i<3)imgDestroyer[i] = new BackgroundImage(new Image("/pictures/destroyer" + i + ".png"), null, null, null, bgSize); 
			if(i<2)imgSubmarine[i] = new BackgroundImage(new Image("/pictures/submarine" + i + ".png"), null, null, null, bgSize); 
		}
	}
	
	private void HoverEvent(MouseEvent e, Point cord ){
			mousePoint.x = cord.x;
			mousePoint.y = cord.y;
	}
	
	public void refreshColor(){
		try {
			Thread.sleep(50);
			
				Platform.runLater(new Runnable(){
				@Override
				public void run() {
					if(hostPlayer){
						bfUp 			= playingField.getBattlefieldTwo();
						bfDown			= playingField.getBattlefieldOne();
					}else{
						bfUp 			= playingField.getBattlefieldOne();
						bfDown			= playingField.getBattlefieldTwo();
					}
					
					bgStrokeColor 	= Color.BLACK;
					Figure  f;
					for(int i = 0; i<10 ; i++){
						for(int j = 0; j<10 ; j++){
							 f = bfUp[i][j];	
							columnUp[i][j].setBorder(new Border(new BorderStroke(bgStrokeColor, BorderStrokeStyle.SOLID, null, null)));
							if(isAktivPlayersTurn()&& i == mousePoint.x && j == mousePoint.y)columnUp[i][j].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null,new BorderWidths(5))));

							if (f== null){ 
								columnUp[i][j].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
								
							}else if (f instanceof Pin){
								columnUp[i][j].setBackground(new Background(imgWhiteFlag));
							}else{
								Ship s = ((Ship)f);
								int picNum = i - s.position.x + j - s.position.y;
								if(s.isDestroyed()) {
									if (f instanceof Battleship)
										columnUp[i][j].setBackground(new Background(imgBattleship[picNum]));
									if (f instanceof Dreadnought)
										columnUp[i][j].setBackground(new Background(imgDreadnought[picNum]));
									if (f instanceof Destroyer)
										columnUp[i][j].setBackground(new Background(imgDestroyer[picNum]));
									if (f instanceof Submarine)
										columnUp[i][j].setBackground(new Background(imgSubmarine[picNum]));
									if (!((Ship)f).isHorizontal()) columnUp[i][j].setRotate(-90);
										columnUp[i][j].setBackground(new Background(columnUp[i][j].getBackground().getImages().get(0),(imgHit)));
								}else if(s.isHit(new Point(i,j))){
									columnUp[i][j].setBackground(new Background(imgRedFlag));
								}
								
							}			
						}
						
					}
					
					for(int i = 0; i<10 ; i++){
						for(int j = 0; j<10 ; j++){
							f = bfDown[i][j];	
							columnDown[i][j].setBorder(new Border(new BorderStroke(bgStrokeColor, BorderStrokeStyle.SOLID, null, null)));
							if (f== null){
								columnDown[i][j].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
							}else if (f instanceof Pin){
								columnDown[i][j].setBackground(new Background(imgWhiteFlag));
							}else{
								Ship s = ((Ship)f);
								int picNum = i - s.position.x + j - s.position.y;
								if(s.isHit(new Point(i,j))){
									if (f instanceof Battleship)
										columnDown[i][j].setBackground(new Background(imgBattleship[picNum],imgHit));
									if (f instanceof Dreadnought)
										columnDown[i][j].setBackground(new Background(imgDreadnought[picNum],imgHit));
									if (f instanceof Destroyer)
										columnDown[i][j].setBackground(new Background(imgDestroyer[picNum],imgHit));
									if (f instanceof Submarine)
										columnDown[i][j].setBackground(new Background(imgSubmarine[picNum],imgHit));
									if (!((Ship)f).isHorizontal()) columnDown[i][j].setRotate(-90);
								}else{
										if (f instanceof Battleship)
											columnDown[i][j].setBackground(new Background(imgBattleship[picNum]));
										if (f instanceof Dreadnought)
											columnDown[i][j].setBackground(new Background(imgDreadnought[picNum]));
										if (f instanceof Destroyer)
											columnDown[i][j].setBackground(new Background(imgDestroyer[picNum]));
										if (f instanceof Submarine)
											columnDown[i][j].setBackground(new Background(imgSubmarine[picNum]));
										if (!((Ship)f).isHorizontal()) columnDown[i][j].setRotate(-90);
								}
							}
						
						}
					}
					infoText.setWaiting(!isAktivPlayersTurn());
					setText();
				}
			
				});
		
		
		} catch (InterruptedException e) {
		}
	}
	
	public void setText(){
		lblInfText.setText(infoText.info());
	}
	
	public void clickEvent(MouseEvent e, Figure f, Point pos){
		if (e.getClickCount()==2 ){
			if(isAktivPlayersTurn()){
				infoText.setMsg("");
				if(!playingField.shoot(pos)){
					infoText.setMsg("Darauf hast du bereits geschossen");
					setText();
					return;
				}	
				
			}
		}	
	}

	private boolean isAktivPlayersTurn() {
		return (hostPlayer && playingField.getPlayerOne().isTurn())||(!hostPlayer && playingField.getPlayerTwo().isTurn());
	}
	
	
	
	
	
}