package jenhenna.pls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import entities.BattleEntity;

public class BattleScene {
	final int OBSTACLE = 1;
	final int MAX_OBSTACLES = 5;
	
	private int[][] battleGrid;
	private Map<Integer, BattleEntity> entities = new HashMap<Integer, BattleEntity> ();
	private BattleEntity marked;
	
	private BattleWindow window;
	
	private int gridHeight;
	private int gridWidth;
	int cellSize;
	float w, h;
	int wI, hI;
	
	Sprite bg;
	private Array<Sprite> staticSprites = new Array<Sprite> ();
	
	public BattleScene (double screenW, double screenH, int cellsize, TextureAtlas atlas){
		this.cellSize = cellsize;
		this.h = (float) screenH; this.w = (float) screenW;
		wI = (int) Math.floor (w);
		hI = (int) Math.floor (h);
		gridWidth = (int) (screenW / cellsize -2);
		gridHeight = (int) (screenH / cellsize -2);
		battleGrid = new int[gridWidth][gridHeight];
		for (int y=0;y<gridHeight;y++){
			for (int x=0;x<gridWidth;x++){
				battleGrid[x][y] = 0;
			}
		}
		initStatics (atlas);
		initBackground (atlas);
	}
	
	private void initBackground (TextureAtlas atlas) {
		bg = new Sprite (atlas.createSprite ("background"));
		//set background
		bg.setPosition(-w/2,-h/2);
		bg.setSize (w, h);
		
	}

	private void initStatics (TextureAtlas atlas) {
		//set obstacles
		Random r = new Random ();
		int numObstacles = r.nextInt (MAX_OBSTACLES);
		numObstacles = 5;
		File images = new File (GraphicsInitializer.imagePath);
		Array<Sprite> obstacleSprites = new Array<Sprite> ();
		for (String f : images.list ()){
			if (!f.contains (".") || f.contains ("test"))
				continue;
			System.out.println (f);
			String name = f.substring (0, f.indexOf ("."));
			obstacleSprites.add (new Sprite (atlas.createSprite (name)));
		}
		for (int i=0;i<numObstacles;i++){
			Sprite obstacle = obstacleSprites.get (r.nextInt (obstacleSprites.size));
			int xPos = r.nextInt (gridWidth);
			int yPos = r.nextInt (gridHeight);
			obstacle.setSize (cellSize*3, cellSize*3);
			Coord pos = getDrawingCoordinates (xPos, yPos, obstacle.getHeight ());
			obstacle.setPosition (pos.x, pos.y);
			boolean isSet = setGridObstacle (xPos, yPos, 3, 3);
			if (isSet)
				staticSprites.add (new Sprite (obstacle));
		}
		for(int i=0;i<gridHeight;i++){
			System.out.print ("|");
			for (int j=0;j<gridWidth;j++){
				System.out.print ("["+battleGrid[j][i] + "]");
			}
			System.out.print ("|\n");
		}
	}
	
	//Set obstacles in the battle grid
	private boolean setGridObstacle (int xPos, int yPos, int w, int h) {
		if (h+yPos > gridHeight || w+xPos > gridWidth)
			return false;
		for (int y=yPos;y<h+yPos;y++){
			for (int x=xPos;x<w+xPos;x++){
				battleGrid[x][y] = OBSTACLE;
			}
		}
		return true;
	}
	// Get coordinates for drawing on the surface.
	//supply x, y in battle grid coordinates
	private Coord getDrawingCoordinates (int x, int y, float height){
		float xd = x * cellSize;
		float yd = y * cellSize;
		return Coord.getTransformedSpriteCoords (xd, yd, height);
	}
	

	private Array<Sprite> getStaticSprites (){
		return staticSprites;
	}
	
	public Sprite getBackground (){
		return bg;
	}
	
	public Array<Sprite> getBattleSprites (){
		return getStaticSprites ();
	}
	
	private Coord getGridCoords (Coord c){
		return new Coord (c.x / cellSize, c.y / cellSize);
	}

	public void sceneClicked (Coord c) {
		Coord gridC = getGridCoords (c);
		if (window != null){
			doWindowOptions (c);
			return;
		}
		int clickedID = battleGrid[(int) gridC.x][(int) gridC.y];
		if (marked == null){

			BattleEntity entity = entities.get (clickedID);
			if (entity == null)
				return;
			entity.selected ();
		} else{
			GridArea area = marked.getEffectArea ();
			Array<BattleEntity> ents = getAffectedEntities (area);
			marked.actionPerformedOn (ents); 
		}
	}
	
	//Returns all entities in the specified area
	private Array<BattleEntity> getAffectedEntities (GridArea area) {
		
		return null;
	}

	private void doWindowOptions (Coord c) {
		//Figure out whats going on in the window and perform actions...
	}
}
