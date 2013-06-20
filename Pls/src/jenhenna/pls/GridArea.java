package jenhenna.pls;

import Utils.Coord;

public class GridArea {
	
	public Coord middle;
	public int radius;
	
	public GridArea (int middleX, int middleY, int radius){
		middle = new Coord (middleX, middleY);
		this.radius = radius;
	}

}
