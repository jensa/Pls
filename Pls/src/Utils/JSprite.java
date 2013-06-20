package Utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class JSprite extends Sprite{

	public JSprite (Sprite createSprite) {
		super (createSprite);
	}
	
	@Override
	public void setPosition (float x, float y){
		super.setPosition (x - getWidth()/2, y - getHeight()/2);
	}

}
