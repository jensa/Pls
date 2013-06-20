package controller;

import jenhenna.pls.BattleScene;
import Utils.Coord;

import com.badlogic.gdx.InputAdapter;

public class ClickHandler extends InputAdapter{
	
	private boolean isKeyDown;
	BattleScene bs;
	
	public ClickHandler (BattleScene bs){
		this.bs = bs;
	}
	
	private void handleClick (Coord c){
		bs.sceneClicked (c);
	}
	
	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		if (isKeyDown)
			return false;
		isKeyDown = true;
		return true;
	}
	
	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if (isKeyDown){
			Coord c = new Coord (x, y);
			handleClick (c);
			isKeyDown = false;
			return true;
		}
		return false;
	}

}
