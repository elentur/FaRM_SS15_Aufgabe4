package game;

import java.awt.Point;

public abstract class Ship implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected int size;
	protected boolean horizontal;
	protected boolean destroyed=false;
	protected boolean[] hit;
	protected Point position;
	public Ship(Point position,boolean horizontal){
		this.position = position;
		this.horizontal=horizontal;
	}
	public abstract ShipTyp getName();
	public boolean isHorizontal(){
		return horizontal;
	}
	
	public int getSize(){
		return size;
	}
	
	public boolean isDestroyed(){
		return destroyed;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public boolean isHit(Point cord){
		int index;
		if(horizontal) index = cord.x - position.x;
		else index = cord.y - position.y;
		return hit[index];
	}
	public boolean hitShip(Point cord){
		int index;
		if(horizontal) index = cord.x - position.x;
		else index = cord.y - position.y;
		if (hit[index]) return false;
		hit[index]=true;
		destroyed = true;
		for ( int i = 0; i < size; i++)
			if (!hit[i]){
				destroyed = false;
				continue;

			}
		return true;
	}
}
