package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOSystem  {

	
	public static PlayingField readFile() throws FileNotFoundException, IOException, ClassNotFoundException {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Game1.obj"));
			PlayingField readedPlayingField = (PlayingField)in.readObject();
			in.close();
			return readedPlayingField;
		
	}
	
	public static void writeFile(PlayingField playingField) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Game1.obj"));
			out.writeObject(playingField);
			out.close();
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben.");		}
		
	}
}
