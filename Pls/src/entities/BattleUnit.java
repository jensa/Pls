package entities;


import jenhenna.pls.GridArea;

import Hero.UnitStats;
import Utils.Coord;
import Utils.Helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class BattleUnit implements BattleEntity{
	
	private Sprite sprite;
	private TextureRegion[] frames;
	
	private int frameIndex = 0;
	private int id;
	private int gridHeight, gridWidth;
	private float spriteW, spriteH;
	private String spriteName;
	private int numImages;
	
	private UnitStats stats;
	
	public BattleUnit (Sprite s, int id, int width, int height){
		sprite = s;
		this.id = id;
	}
	
	public BattleUnit (int id, String spriteName, int width, int height, int numImages,float spriteW, float spriteH){
		this.id = id;
		this.spriteName = spriteName;
		this.numImages = numImages;
		this.spriteH = spriteH;
		this.spriteW = spriteW;
		gridWidth = width;
		gridHeight = height;
	}
	
	public void initSprite (TextureAtlas a, boolean flip){
		sprite = a.createSprite (spriteName);
		int framePixels = (int) (sprite.getWidth () / numImages);
		frames = sprite.split (framePixels, (int) sprite.getHeight ())[0];
		for (TextureRegion r : frames){
			r.flip (flip, false);
		}
		sprite.flip (flip, false);
		sprite.setSize (spriteW, spriteH);
		sprite.setRegion (frames[0]);
	}
	
	@Override
	public boolean selected () {
		int index = frameIndex++ % frames.length;

		Helper.log ("Troll with ID "+getID ()+" selected, index="+index);
		TextureRegion t = frames[index];
		sprite.setRegion (t);
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

	@Override
	public int getGridHeight () {
		return gridHeight;
	}

	@Override
	public int getGridWidth () {
		return gridWidth;
	}
	
	public void flip (){
		for (TextureRegion r : frames){
			r.flip (true, false);
		}
	}

}
