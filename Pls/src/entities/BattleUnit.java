package entities;


import jenhenna.pls.GridArea;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;


public class BattleUnit implements BattleEntity{
	
	private Sprite sprite;
	private int id;
	private int gridHeight, gridWidth;
	private float spriteW, spriteH;
	private String spriteName;
	
	public BattleUnit (Sprite s, int id, int width, int height){
		sprite = s;
		this.id = id;
	}
	
	public BattleUnit (int id, String spriteName, int width, int height, float spriteW, float spriteH){
		this.id = id;
		this.spriteH = spriteH;
		this.spriteW = spriteW;
		gridWidth = width;
		gridHeight = height;
	}
	
	public void initSprite (TextureAtlas a){
		sprite = a.createSprite (spriteName);
		sprite.setSize (spriteW, spriteH);
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

	@Override
	public int getGridHeight () {
		return gridHeight;
	}

	@Override
	public int getGridWidth () {
		return gridWidth;
	}

}
