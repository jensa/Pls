package Utils;

import com.badlogic.gdx.Gdx;

public class Coord {
	public float x;
	public float y;
	
	public Coord (float ix, float iy){
		x = ix;
		y = iy;
	}
	
	//x behaves, y does not, so correct for height
	public static Coord getTransformedSpriteCoords (float ix, float iy, float hs){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		float ex = ix;
		if (ex < w/2){
			ex = (w/2) - ex;
			ex = -ex;
		}
		else{
			ex = ex - w/2;
		}
		
		float ey = iy;
		if (ey > h/2){
			ey = ey - (h/2);
			ey = -ey - hs;
		}
		else{
			ey = h/2 - ey - hs;
		}
		return new Coord (ex, ey);
	}
}
