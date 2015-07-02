package game;

import java.awt.Point;

public class Destroyer  extends Ship{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Destroyer(Point position, boolean horizontal){
		super(position,horizontal);
		size = 3;
		hit = new boolean[3];
			
	}

	@Override
	public ShipTyp getName() {
		
		return ShipTyp.DESTROYER;
	}
}
