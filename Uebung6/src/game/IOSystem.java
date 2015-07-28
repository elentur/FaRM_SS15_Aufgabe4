package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOSystem  {
	public static Socket socket=null;
	
	public static PlayingField readFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		if(socket==null){
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Game1.obj"));
			PlayingField readedPlayingField = (PlayingField)in.readObject();
			in.close();
			return readedPlayingField;
		}else{
			ObjectInputStream  in = new ObjectInputStream(socket.getInputStream());
			PlayingField readedPlayingField = (PlayingField)in.readObject();
			return readedPlayingField;
		}
		
	}
	
	public static void writeFile(PlayingField playingField) {
		try {
			if(socket==null){
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Game1.obj"));
				out.writeObject(playingField);
				out.close();
			}else{
				OutputStream out =socket.getOutputStream();
				ObjectOutputStream  writer = new ObjectOutputStream(out);
				writer.writeObject(playingField);
			}
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben.");		}
		
	}
	
	public static void writeTurnMap(PlayingField playingField) {
		try {
			FileWriter writer = new FileWriter("Game1.map", true);
			writer.write("###################\r\n");
			writer.write(playingField.print());
			writer.close();
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben.");		}
		
	}
}
