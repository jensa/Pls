package entities;

import jenhenna.pls.GridArea;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Obstacle implements BattleEntity{
	
	private Sprite sprite;
	private int id;
	public Sprite altSprite;

	public Obstacle (Sprite s, int id){
		this.sprite = s;
		this.id = id;
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

}
