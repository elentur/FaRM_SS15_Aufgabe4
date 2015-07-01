package game;

import java.awt.Point;

public class Dreadnought  extends Ship {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dreadnought(Point position, boolean horizontal){
		super(position,horizontal);
		size = 4;
		hit = new boolean[4];
			
	}
}
