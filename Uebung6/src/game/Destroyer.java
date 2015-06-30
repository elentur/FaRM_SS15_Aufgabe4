package game;

import java.awt.Point;

public class Destroyer  extends Ship{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Destroyer(Point position, boolean horizontal){
		this.size = 3;
		hit = new boolean[5];
		this.position=position;
		this.horizontal = horizontal;		
	}
}
