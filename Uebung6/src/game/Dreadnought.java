package game;

import java.awt.Point;

public class Dreadnought  extends Ship {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dreadnought(Point position, boolean horizontal){
		this.size = 4;
		hit = new boolean[5];
		this.position=position;
		this.horizontal = horizontal;		
	}
}
