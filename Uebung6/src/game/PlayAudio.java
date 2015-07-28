package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayAudio {
	static MediaPlayer shipWav = new MediaPlayer(new Media(PlayAudio.class.getResource("/audio/ship.wav").toString()));
	static MediaPlayer waterWav = new MediaPlayer(new Media(PlayAudio.class.getResource("/audio/water.wav").toString()));
	
	public static void playShip(){
	shipWav.stop();
	shipWav.play();
	}
	
	public static void playWater(){
		waterWav.stop();
		waterWav.play();
		}
}
