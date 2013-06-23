package entities;


import jenhenna.pls.GridArea;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;


public class BattleUnit implements BattleEntity{
	
	private Sprite sprite;
	private int id;
	
	public BattleUnit (Sprite s, int id){
		sprite = s;
		this.id = id;
	}

	@Override
	public boolean selected () {
		return true;
	}

	@Override
	public boolean actionPerformedOn (Array<BattleEntity> entities) {
		return true;
	}

	@Override
	public GridArea getEffectArea () {
		// TODO Auto-generated method stub
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
