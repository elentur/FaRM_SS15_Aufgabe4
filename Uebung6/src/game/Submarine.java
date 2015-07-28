package game;

import java.awt.Point;

public class Submarine  extends Ship{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Submarine(Point position, boolean horizontal){
		super(position,horizontal);
		size = 2;
		hit = new boolean[2];
		name = "U-Boot";
			
	}
}
