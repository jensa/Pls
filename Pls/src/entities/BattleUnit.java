package entities;


import jenhenna.pls.GridArea;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;


public class BattleUnit implements BattleEntity{
	
	private Sprite sprite;
	
	public BattleUnit (Sprite s){
		sprite = s;
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

}
