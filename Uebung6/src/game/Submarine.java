package game;

import java.awt.Point;

public class Submarine  extends Ship{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Submarine(Point position, boolean horizontal){
		this.size = 2;
		hit = new boolean[5];
		this.position=position;
		this.horizontal = horizontal;		
	}
}
