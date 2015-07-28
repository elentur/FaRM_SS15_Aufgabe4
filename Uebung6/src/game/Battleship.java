package game;

import java.awt.Point;

import exception.WrongPlaceException;

public class Battleship extends Ship {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Battleship(Point position, boolean horizontal){
		super(position,horizontal);
		size = 5;
		hit = new boolean[5];
		name ="Kriegsschiff";
	}
	
}
