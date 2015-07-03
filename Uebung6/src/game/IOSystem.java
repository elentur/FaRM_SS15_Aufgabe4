package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class IOSystem  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Player readFile(Player player) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(player.getName()+".obj"));
			Player readedPlayer = (Player)in.readObject();
			in.close();
			return readedPlayer;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Fehler beim lesen.");
		}
		return null;
		
	}
	
	public static void writeFile(Player player) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(player.getName() + ".obj"));
			out.writeObject(player);
			out.close();
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben.");		}
		
	}
}
