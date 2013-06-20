package entities;

import jenhenna.pls.GridArea;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Obstacle implements BattleEntity{
	
	private Sprite sprite;

	public Obstacle (Sprite s){
		this.sprite = s;
	}

	@Override
	public boolean selected () {
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

}
