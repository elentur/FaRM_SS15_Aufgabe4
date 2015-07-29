package game;




import java.awt.Point;
import java.io.IOException;


import java.net.Socket;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class Control {
	//HBox[] rowDown = new HBox[10];
	//VBox[][] columnDown = new VBox[10][10];
	//HBox[] rowUp = new HBox[10];
	//VBox[][] columnUp = new VBox[10][10];
	Rectangle[][] upRec= new Rectangle[10][10];
	Rectangle[][] downRec= new Rectangle[10][10];
	TextArea txtOutput;
	TextField txtInput;
	PlayingField playingField;
	Figure[][] bfUp,bfDown;
	//VBox down,up,divider;
	GridPane down,up;
	VBox divider;
	InfoText infoText;
	Label lblInfText = new Label();
	Thread KIThread;
	Thread colorRefresh;
	Thread readGame;
	Point mousePoint = new Point(-1,-1);
	Color bgStrokeColor;
	boolean hostPlayer;
	boolean loading;
	ImagePattern[] imgBattleship= new ImagePattern[5];
	ImagePattern[] imgDreadnought= new ImagePattern[4];
	ImagePattern[] imgDestroyer= new ImagePattern[3];
	ImagePattern[] imgSubmarine= new ImagePattern[2];
	ImagePattern imgRedFlag;
	ImagePattern imgWhiteFlag;
	ImagePattern imgHit;
	Socket socket;
	
	
	public Control(GridPane down,GridPane up, VBox divider,TextArea txtOutput, TextField txtInput, 
			boolean hostPlayer, boolean loading, Socket socket, String name){
		this.down =down;
		this.up = up;
		this.divider=divider;
		this.txtInput=txtInput;
		this.txtOutput=txtOutput;
		
		this.divider.getChildren().addAll(lblInfText);
		this.hostPlayer=hostPlayer;
		this.loading =loading;
		this.socket=socket;
		IOSystem.socket=socket;
		initGame(name);	 
	}
	
	private void initGame(String name) {
		
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
				playingField.getPlayerOne().setName(name);
				playingField.getPlayerTwo().setName("KI");
				IOSystem.writeFile(playingField);
			}else{
				try {
					playingField = IOSystem.readFile();
					playingField.getPlayerTwo().setName(name);
					IOSystem.writeFile(playingField);
				} catch (ClassNotFoundException | IOException e) {
				}
				
			}
			
		}
		
		infoText = new InfoText();
		infoText.setPlayer1(playingField.getPlayerOne());
		infoText.setPlayer2(playingField.getPlayerTwo());
		txtInput.setOnAction(e->{
			playingField.addChat(name + ": " + txtInput.getText());
			txtInput.setText("");
			Mutex.setMutex(true);
			IOSystem.writeFile(playingField);
			Mutex.setMutex(false);
			
		});	
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
		//	infoText.setShip1(playingField.getPlayerOne().getShips());
			//infoText.setShip2(playingField.getPlayerTwo().getShips());
			infoText.setPlayer1(playingField.getPlayerOne());
			infoText.setPlayer2(playingField.getPlayerTwo());
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
//	private void createGUIGrid() {
//		for(int i = 0; i<10 ; i++){
//			rowDown[i] = new HBox();
//			rowDown[i].minWidthProperty().bind(down.prefWidthProperty());
//			rowDown[i].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
//			rowUp[i] = new HBox();
//			rowUp[i].minWidthProperty().bind(down.prefWidthProperty());
//			rowUp[i].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
//			down.getChildren().add(rowDown[i]);
//			up.getChildren().add(rowUp[i]);
//			for(int j = 0; j<10 ; j++){
//				columnDown[i][j] = new VBox();
//				columnDown[i][j].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
//				columnDown[i][j].minWidthProperty().bind(down.prefWidthProperty().divide(10.0));				
//				rowDown[i].getChildren().add(columnDown[i][j]);
//				columnUp[i][j] = new VBox();
//				columnUp[i][j].minHeightProperty().bind(down.prefWidthProperty().divide(10.0));
//				columnUp[i][j].minWidthProperty().bind(down.prefWidthProperty().divide(10.0));
//				Figure  f = playingField.getBattlefieldTwo()[i][j];
//				Point pos = new Point(i,j);
//				columnUp[i][j].setOnMouseClicked(e -> clickEvent(e,f,pos));
//				columnUp[i][j].setOnMouseEntered(e -> HoverEvent(e,pos));
//				rowUp[i].getChildren().add(columnUp[i][j]);
//			}
//		}
//	}
	private void createGUIGrid() {
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				upRec[i][j] = new Rectangle();
				upRec[i][j].heightProperty().bind(down.prefWidthProperty().divide(10.0));
				upRec[i][j].widthProperty().bind(down.prefWidthProperty().divide(10.0));
				Figure  f = playingField.getBattlefieldTwo()[i][j];
				Point pos = new Point(i,j);
				upRec[i][j].setOnMouseClicked(e -> clickEvent(e,f,pos));
				upRec[i][j].setOnMouseEntered(e -> HoverEvent(e,pos));
				up.add(upRec[i][j],i,j);
				downRec[i][j] = new Rectangle();
				downRec[i][j].heightProperty().bind(down.prefWidthProperty().divide(10.0));
				downRec[i][j].widthProperty().bind(down.prefWidthProperty().divide(10.0));				
				down.add(downRec[i][j],i,j);				
			}
		}
	}
	
	private void loadImageBuffer() {
		BackgroundSize bgSize = new BackgroundSize(0, 0, false, false, true, false);
		down.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		up.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));			
		imgRedFlag = new ImagePattern(new Image("/pictures/redFlag.png"));
		imgWhiteFlag = new ImagePattern(new Image("/pictures/whiteFlag.png"));
		imgHit = new ImagePattern(new Image("/pictures/hit.png"));
		
		for (int i = 0; i< 5;i++){
			imgBattleship[i] = new ImagePattern(new Image("/pictures/battleship" + i + ".png")); 
			if(i<4)imgDreadnought[i] = new ImagePattern(new Image("/pictures/dreadnought" + i + ".png")); 
			if(i<3)imgDestroyer[i] = new ImagePattern(new Image("/pictures/destroyer" + i + ".png")); 
			if(i<2)imgSubmarine[i] = new ImagePattern(new Image("/pictures/submarine" + i + ".png")); 
		}
	}
	
	private void HoverEvent(MouseEvent e, Point cord ){
			mousePoint.x = cord.x;
			mousePoint.y = cord.y;
	}
	private int NodesInCell(GridPane gridPane, int col, int row) {
	    int i=0;
		for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            i++;
	        }
	    }
	    return i;
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
							upRec[i][j].setStroke(bgStrokeColor);
							if(isAktivPlayersTurn()&& i == mousePoint.x && j == mousePoint.y){
								upRec[i][j].setStroke(Color.RED);
							}

							if (f== null){ 
								upRec[i][j].setFill(Color.TRANSPARENT);
								
							}else if (f instanceof Pin){
								upRec[i][j].setFill(imgWhiteFlag);
							}else{
								Ship s = ((Ship)f);
								int picNum = i - s.position.x + j - s.position.y;
								if(s.isDestroyed()) {
									if (f instanceof Battleship)
										upRec[i][j].setFill(imgBattleship[picNum]);
									if (f instanceof Dreadnought)
										upRec[i][j].setFill(imgDreadnought[picNum]);
									if (f instanceof Destroyer)
										upRec[i][j].setFill(imgDestroyer[picNum]);
									if (f instanceof Submarine)
										upRec[i][j].setFill(imgSubmarine[picNum]);
									if (((Ship)f).isHorizontal()) upRec[i][j].setRotate(-90);
									if(NodesInCell(up,i,j)==1){
										Rectangle hit = new Rectangle(20,20);
										hit.heightProperty().bind(down.prefWidthProperty().divide(10.0));
										hit.widthProperty().bind(down.prefWidthProperty().divide(10.0));	
										hit.setFill(imgHit);
										up.add(hit, i, j);
									}
								}else if(s.isHit(new Point(i,j))){
									upRec[i][j].setFill(imgRedFlag);
								}else{
									upRec[i][j].setFill(Color.TRANSPARENT);
								}
								
							}			
						}
						
					}
					
					for(int i = 0; i<10 ; i++){
						for(int j = 0; j<10 ; j++){
							f = bfDown[i][j];	
							downRec[i][j].setStroke(bgStrokeColor);
							if (f== null){
								downRec[i][j].setFill(Color.TRANSPARENT);
							}else if (f instanceof Pin){
								downRec[i][j].setFill(imgWhiteFlag);
							}else{
								Ship s = ((Ship)f);
								int picNum = i - s.position.x + j - s.position.y;
								if(s.isHit(new Point(i,j))){
									
									if (f instanceof Battleship)
										downRec[i][j].setFill(imgBattleship[picNum]);
									if (f instanceof Dreadnought)
										downRec[i][j].setFill(imgDreadnought[picNum]);
									if (f instanceof Destroyer)
										downRec[i][j].setFill(imgDestroyer[picNum]);
									if (f instanceof Submarine)
										downRec[i][j].setFill(imgSubmarine[picNum]);
									if(NodesInCell(up,i,j)==1){
										Rectangle hit = new Rectangle(20,20);
										hit.heightProperty().bind(down.prefWidthProperty().divide(10.0));
										hit.widthProperty().bind(down.prefWidthProperty().divide(10.0));	
										hit.setFill(imgHit);
										down.add(hit, i, j);
									}
									if (((Ship)f).isHorizontal()) downRec[i][j].setRotate(-90);
								}else{
										if (f instanceof Battleship)
											downRec[i][j].setFill(imgBattleship[picNum]);
										if (f instanceof Dreadnought)
											downRec[i][j].setFill(imgDreadnought[picNum]);
										if (f instanceof Destroyer)
											downRec[i][j].setFill(imgDestroyer[picNum]);
										if (f instanceof Submarine)
											downRec[i][j].setFill(imgSubmarine[picNum]);
										if (((Ship)f).isHorizontal()) downRec[i][j].setRotate(-90);
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
		if(!playingField.getChat().equals( txtOutput.getText())){
				txtOutput.setText(playingField.getChat());
				txtOutput.selectPositionCaret(txtOutput.getLength());
				txtOutput.deselect(); 
		}
		
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