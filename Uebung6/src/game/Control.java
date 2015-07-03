package game;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private static final int MAX_PLAYFIELD_NR = 10;
	VBox vBoxPlayFieldDown, vBoxPlayFieldUp, vBoxPlayFieldDivider;
	HBox[] hBoxRowPlayFieldDown = new HBox[10];
	VBox[][] columnPlayFieldDown = new VBox[10][10];
	HBox[] hBoxRowPlayFieldUp = new HBox[10];
	VBox[][] columnPlayFieldUp = new VBox[10][10];
	Player me, enemie;
	Figure[][] myBattlefield, enemieBattlefield;
	InfoText infoText;
	Label lblInfText = new Label();
	int[][] kiPlan = new int[10][10];
	Point kiShootPos = new Point(0, 0);

	public Control(VBox down, VBox up, VBox divider) {
		vBoxPlayFieldDown = down;
		vBoxPlayFieldUp = up;
		vBoxPlayFieldDivider = divider;
		vBoxPlayFieldDivider.getChildren().addAll(lblInfText);
		vBoxPlayFieldDown.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		vBoxPlayFieldUp.setBackground(new Background(new BackgroundImage(new Image("/pictures/background.jpg"), null, null, null, null)));
		me = new Player("me");
		enemie = new Player("enemie");
		infoText = new InfoText(me, enemie);
		myBattlefield = me.getBattlefield().getBattlefield();
		enemieBattlefield = enemie.getBattlefield().getBattlefield();
		initializeGui();
		setColor();
		setText();

	}

	private void initializeGui() {
		for (int rowNr = 0; rowNr < MAX_PLAYFIELD_NR; rowNr++) {
			// create 10 rows with 400 width, 40 height for each PlayField
			hBoxRowPlayFieldDown[rowNr] = new HBox();
			hBoxRowPlayFieldDown[rowNr].setMinSize(400, 40);
			hBoxRowPlayFieldUp[rowNr] = new HBox();
			hBoxRowPlayFieldUp[rowNr].setMinSize(400, 40);
			// give the hole PlayField its rows
			vBoxPlayFieldDown.getChildren().add(hBoxRowPlayFieldDown[rowNr]);
			vBoxPlayFieldUp.getChildren().add(hBoxRowPlayFieldUp[rowNr]);
			for (int columnNr = 0; columnNr < 10; columnNr++) {
				// create 10 columns for each row, with size: 40 width, 40
				// height
				columnPlayFieldDown[rowNr][columnNr] = new VBox();
				columnPlayFieldDown[rowNr][columnNr].setMinSize(40, 40);
				columnPlayFieldUp[rowNr][columnNr] = new VBox();
				columnPlayFieldUp[rowNr][columnNr].setMinSize(40, 40);
				hBoxRowPlayFieldDown[rowNr].getChildren().add(columnPlayFieldDown[rowNr][columnNr]);
				hBoxRowPlayFieldUp[rowNr].getChildren().add(columnPlayFieldUp[rowNr][columnNr]);
				Figure f = myBattlefield[rowNr][columnNr];
				Point pos = new Point(rowNr, columnNr);
				columnPlayFieldUp[rowNr][columnNr].setOnMouseClicked(e -> clickEvent(e, f, pos));
				columnPlayFieldUp[rowNr][columnNr].setOnMouseEntered(e -> HoverEvent(e, pos, true));
				columnPlayFieldUp[rowNr][columnNr].setOnMouseExited(e -> HoverEvent(e, pos, false));
				columnPlayFieldUp[rowNr][columnNr].setPrefHeight(40);
				columnPlayFieldUp[rowNr][columnNr].setPrefWidth(40);

			}
		}
	}

	private void HoverEvent(MouseEvent e, Point cord, boolean enter) {
		if (enter) {
			columnPlayFieldUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
		} else {
			columnPlayFieldUp[cord.x][cord.y].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		}
	}

	private void setColor() {
		// Enemie PlayField
		for (int rowNr = 0; rowNr < MAX_PLAYFIELD_NR; rowNr++) {
			for (int columnNr = 0; columnNr < 10; columnNr++) {
				Figure enemieFigure = enemieBattlefield[rowNr][columnNr];
				columnPlayFieldUp[rowNr][columnNr].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
				if (enemieFigure == null) {
					columnPlayFieldUp[rowNr][columnNr].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

				} else {
					if (enemieFigure instanceof Pin) {
						columnPlayFieldUp[rowNr][columnNr].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null,
								null, null)));

					} else if (enemieFigure instanceof Ship) {

						Ship s = ((Ship) enemieBattlefield[rowNr][columnNr]);
						ShipTyp type = s.getName();
						int picNum = rowNr - s.position.x + columnNr - s.position.y;
						if (s.isDestroyed()) {
							setDestroyedImages(columnPlayFieldUp, type, rowNr, columnNr, picNum);
							if (!s.isHorizontal())
								columnPlayFieldUp[rowNr][columnNr].setRotate(-90);
							columnPlayFieldUp[rowNr][columnNr].setBackground(new Background(columnPlayFieldUp[rowNr][columnNr].getBackground().getImages()
									.get(0), (new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, null))));
						} else if (s.isHit(new Point(rowNr, columnNr))) {
							columnPlayFieldUp[rowNr][columnNr].setBackground(new Background(new BackgroundImage(new Image("/pictures/redFlag.png"), null, null,
									null, null)));
						}
					}
				}

			}
		}
		// My PlayField
		for (int rowNr = 0; rowNr < MAX_PLAYFIELD_NR; rowNr++) {
			for (int columnNr = 0; columnNr < 10; columnNr++) {
				Figure myFigure = myBattlefield[rowNr][columnNr];
				columnPlayFieldDown[rowNr][columnNr].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
				if (myFigure == null) {
					columnPlayFieldDown[rowNr][columnNr].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				} else if (myFigure instanceof Pin) {
					columnPlayFieldDown[rowNr][columnNr].setBackground(new Background(new BackgroundImage(new Image("/pictures/whiteFlag.png"), null, null,
							null, null)));
				} else {
					Ship s = ((Ship) myFigure);
					int picNum = rowNr - s.position.x + columnNr - s.position.y;
					if (s.isHit(new Point(rowNr, columnNr))) {
						columnPlayFieldDown[rowNr][columnNr].setBackground(new Background(columnPlayFieldDown[rowNr][columnNr].getBackground().getImages()
								.get(0), (new BackgroundImage(new Image("/pictures/hit.png"), null, null, null, null))));
					} else {
						ShipTyp type = s.getName();
						setDestroyedImages(columnPlayFieldDown, type, rowNr, columnNr, picNum);
						if (!((Ship) myFigure).isHorizontal())
							columnPlayFieldDown[rowNr][columnNr].setRotate(-90);
					}
				}

			}
		}

	}

	private void setDestroyedImages(VBox[][] columnPlayField, ShipTyp type, int rowNr, int columnNr, int picNum) {
		switch (type) {
		case BATTLESHIP:
			columnPlayField[rowNr][columnNr].setBackground(type.getImageBackground(picNum));
			break;
		case DREADNOUGHT:
			columnPlayField[rowNr][columnNr].setBackground(type.getImageBackground(picNum));
			break;
		case DESTROYER:
			columnPlayField[rowNr][columnNr].setBackground(type.getImageBackground(picNum));
			break;
		case SUBMARINE:
			columnPlayField[rowNr][columnNr].setBackground(type.getImageBackground(picNum));
			break;
		default:
			break;
		}

	}

	private void setText() {
		lblInfText.setText(infoText.info());
	}

	public void clickEvent(MouseEvent e, Figure f, Point pos) {
		if (e.getClickCount() == 2) {
			infoText.setMsg("");
			if (!enemie.getBattlefield().shoot(pos)) {
				infoText.setMsg("Darauf hast du bereits geschossen");
				return;
			}
			checkVictory(enemie);
			writeFile(enemie);
			readFile(me);
			shootKI();
			checkVictory(me);

			infoText.setLap(infoText.getLap() + 1);
			setColor();
			setText();
			// schreibe meinen spielenstand
			writeFile(me);
			// lade das Spiel vom Gegner für meinen nächsten Zug
			readFile(enemie);
		}

	}

	private void readFile(Player player) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(player.getName() + "obj"));
			if (player == me) {
				me = (Player) in.readObject();
			} else {
				enemie = (Player) in.readObject();
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkVictory(Player player) {
		if (player == me) {
			infoText.setShip1(10);
			for (int i = 0; i < player.getShips().size(); i++) {
				if (player.getShips().get(i).isDestroyed())
					infoText.setShip1(infoText.getShip1() - 1);
			}
			if (infoText.getShip1() == 0)
				infoText.setMsg(enemie.getName() + " hat gewonnen.");

		} else {
			infoText.setShip2(10);
			for (int i = 0; i < player.getShips().size(); i++) {
				if (player.getShips().get(i).isDestroyed())
					infoText.setShip2(infoText.getShip2() - 1);
			}
			if (infoText.getShip2() == 0)
				infoText.setMsg(me.getName() + " hat gewonnen.");

		}

		setText();
	}

	private void writeFile(Player player) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(player.getName() + ".obj"));
			out.writeObject(player);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void shootKI() {

		// TODO read file
		boolean hit = false;
		// (Point pos = new Point(kiShootPos.x,kiShootPos.y);
		Point pos = new Point();
		Random rnd = new Random();
		while (!hit) {

			/*
			 * if (pos.x > 9 || pos.x < 0) pos.x = rnd.nextInt(10); if (pos.y >
			 * 9 || pos.y < 0) pos.x = rnd.nextInt(10); if(kiPlan[pos.x][pos.y]
			 * == 0){ hit =me.getBattlefield().shoot(pos); if
			 * (me.getBattlefield().getBattlefield()[pos.x][pos.y] instanceof
			 * Ship){ kiPlan[pos.x][pos.y] =1; }else{ kiPlan[pos.x][pos.y] =-1;
			 * }
			 * 
			 * }else if(kiPlan[pos.x][pos.y] == -1){ pos.x += rnd.nextInt(2);
			 * pos.y += rnd.nextInt(2); }else{ //wenn ausrichtung bekannt Ship s
			 * = ((Ship)me.getBattlefield().getBattlefield()[pos.x][pos.y]); if
			 * (s.isHorizontal()){
			 * 
			 * } }
			 */
			pos.x = rnd.nextInt(10);
			pos.y = rnd.nextInt(10);
			hit = me.getBattlefield().shoot(pos);
		}
		kiShootPos.x = pos.x;
		kiShootPos.y = pos.y;

	}
}