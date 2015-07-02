package game;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

public enum ShipTyp {
BATTLESHIP,DESTROYER, DREADNOUGHT, SUBMARINE;

public Background getImageBackground(int picNum){
	if(this == BATTLESHIP){
		return new Background(new BackgroundImage(new Image("/pictures/battleship" + picNum + ".png"), null,
				null, null, null));
	}
	if(this == DESTROYER){
		return new Background(new BackgroundImage(new Image("/pictures/destroyer" + picNum + ".png"), null,
				null, null, null));
	}
	if(this == DREADNOUGHT){
		return new Background(new BackgroundImage(new Image("/pictures/dreadnought" + picNum + ".png"), null,
				null, null, null));
	}
	if(this == SUBMARINE){
		return new Background(new BackgroundImage(new Image("/pictures/submarine" + picNum + ".png"), null,
				null, null, null));
	}
	
	return null;
}

}
