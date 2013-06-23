package entities;

import jenhenna.pls.GridArea;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Obstacle implements BattleEntity{
	
	private Sprite sprite;
	private int id;
	public Sprite altSprite;
	private int gridW, gridH;

	public Obstacle (Sprite s, int id, int width, int height){
		this.sprite = s;
		this.id = id;
		this.gridH = height;
		this.gridW = width;
	}
	
	public Obstacle (Obstacle o, int ID){
		this.sprite = new Sprite (o.getSprite ());
		this.id = ID;
		this.gridH = o.getGridHeight ();
		this.gridW = o.getGridWidth ();
	}

	@Override
	public boolean selected () {
		System.out.println ("OBSTACLE CLICKED");
		if (altSprite != null){
			Sprite tmp = sprite;
			sprite = altSprite;
			altSprite = tmp;
		}
		//we can't select an obstacle...
		return false;
		
	}
	
	@Override
	public boolean actionPerformedOn (Array<BattleEntity> entities) {
		//Should never happen...
		System.err.println ("trying to act with an obstacle");
		return false;
	}

	@Override
	public GridArea getEffectArea () {
		return null;
	}

	@Override
	public Sprite getSprite () {
		return sprite;
	}

	@Override
	public int getID () {
		return id;
	}

	@Override
	public void setPosition (Coord c) {
		sprite.setPosition (c.x, c.y);
		
	}

	@Override
	public int getGridHeight () {
		return gridH;
	}

	@Override
	public int getGridWidth () {
		return gridW;
	}

}
