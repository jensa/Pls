package jenhenna.pls;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Hero.Hero;
import Utils.Constants;
import Utils.Coord;
import Utils.GameReader;
import Utils.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

import entities.BattleEntity;
import entities.BattleUnit;
import entities.Obstacle;

public class BattleScene {
	
	final int OBSTACLE = 1;
	final int MAX_OBSTACLES = 4;
	final int UNIT_PLACEMENT_PARTITION = 4;
	final int TOP_BOTTOM_SPACE_PARTITION = 6;
	
	private int[][] battleGrid;
	private Map<Integer, BattleEntity> entities = new HashMap<Integer, BattleEntity> ();
	private BattleEntity marked;
	
	private BattleWindow window;
	
	private int gridHeight;
	private int gridWidth;
	float w, h;
	int wI, hI;
	

	public int cellSize;
	
	Sprite bg;
	
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
				battleGrid[x][y] = -1;
			}
		}
		GameReader reader = new GameReader (atlas);
		initStatics (atlas, reader);
		initBackground (atlas);
		initTestUnits (atlas, 
				new Hero (Test.getTestUnits (reader.readBattleUnits (cellSize))), 
				new Hero (Test.getTestUnits (reader.readBattleUnits (cellSize))));

		printGrid ();
	}
	
	public void getDebugGrid (){
		ShapeRenderer r = new ShapeRenderer ();
		r.begin (ShapeType.Line);
		int maxW = Gdx.graphics.getWidth ();
		int maxH = Gdx.graphics.getHeight ();
		r.setColor (0f, 0f, 0f, 255);
		for (int x=0;x<=maxW;x+=cellSize){
			r.line (x, 0, x, maxH);
		}
		for (int y=0;y<=maxH;y+=cellSize){
			r.line (0, y, maxW, y);
		}
		r.end ();
	}
	
	private void initBackground (TextureAtlas atlas) {
		bg = new Sprite (atlas.createSprite ("background"));
		//set background
		bg.setPosition(-w/2,-h/2);
		bg.setSize (w, h);
		
	}

	private void initStatics (TextureAtlas atlas, GameReader reader) {
		//set obstacles
		Random r = new Random ();
		int numObstacles = r.nextInt (MAX_OBSTACLES);
		Array<Obstacle> obstacles = reader.readObstacles (cellSize);
		for (int i=0;i<numObstacles;i++){
			Obstacle original = obstacles.get (r.nextInt (obstacles.size));
			Obstacle copy = new Obstacle (original, reader.getCurrentIDCount ());
			addObstacleAtRandomSpot (copy, r);
		}
	}
	
	private void initTestUnits (TextureAtlas atlas, Hero h1, Hero h2){
		Array<BattleUnit> leftUnits = h1.getBattleUnits ();
		Array<BattleUnit> rightUnits = h2.getBattleUnits ();
		int x = 3;
		int y = 3;
		for (BattleUnit b : leftUnits){
			b.initSprite (atlas, true);
			//set unit position
			Coord pos = getDrawingCoordinates (x, y, b.getSprite ().getHeight ());
			b.setPosition (pos);
			setGridObject (x, y+b.getGridHeight ()+1, b.getGridWidth (), b.getGridHeight (), b.getID ());
			y += 3;
			entities.put (b.getID (), b);
		}
		
		x = gridWidth - 3;
		y = 3;
		for (BattleUnit b : rightUnits){
			b.initSprite (atlas, false);
			//set unit position
			Coord pos = getDrawingCoordinates (x, y, b.getSprite ().getHeight ());
			b.setPosition (pos);
			setGridObject (x, y+b.getGridHeight ()+1, b.getGridWidth (), b.getGridHeight (), b.getID ());
			y += 3;
			entities.put (b.getID (), b);
		}
	}
	
	private void addObstacleAtRandomSpot (Obstacle o, Random r) {
		int xLimit = gridWidth / UNIT_PLACEMENT_PARTITION;
		int yLimit = gridHeight / TOP_BOTTOM_SPACE_PARTITION;
		int xPos = r.nextInt (gridWidth-xLimit*2)+xLimit;
		int yPos = r.nextInt (gridHeight-yLimit*2)+yLimit;
		Coord pos = getDrawingCoordinates (xPos, yPos, o.getSprite ().getHeight ());
		o.setPosition (pos);
		boolean isSet = setGridObject (xPos, yPos, o.getGridWidth (), o.getGridHeight (), o.getID ());
		if (isSet){
			entities.put (o.getID (), o);
		}
	}

	private void printGrid () {
		if (!Constants.DEBUG)
			return;
		for(int i=0;i<gridHeight;i++){
			System.out.print ("|");
			for (int j=0;j<gridWidth;j++){
				if (battleGrid[j][i] < 0 )
					System.out.print ("[Ø]");
				else
					System.out.print ("["+battleGrid[j][i] + "]");
			}
			System.out.print ("|\n");
		}
	}

	//Set objects in the battle grid, return false if it can't fit
	private boolean setGridObject (int xPos, int yPos, int w, int h, int id) {
		if (h+yPos > gridHeight || w+xPos > gridWidth)
			return false;
		for (int y=yPos;y<h+yPos;y++){
			for (int x=xPos;x<w+xPos;x++){
				if (battleGrid[x][y] != -1)
					return false;
			}
		}
		
		for (int y=yPos;y<h+yPos;y++){
			for (int x=xPos;x<w+xPos;x++){
				battleGrid[x][y] = id;
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
	
	public Sprite getBackground (){
		return bg;
	}
	
	public Array<Sprite> getBattleSprites (){
		Array<Sprite> sprites = new Array<Sprite> ();
		for (BattleEntity be : entities.values ())
			sprites.add (be.getSprite ());
//		Helper.log (sprites.size+" entities sent to renderer");
		return sprites;
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
		if (gridC.x > gridWidth || gridC.y > gridHeight)
			return;
		int clickedID = battleGrid[(int) gridC.x][(int) gridC.y];
		if (marked == null){
			BattleEntity entity = entities.get (clickedID);
			if (entity == null)
				return;
			boolean selected = entity.selected ();
			marked = selected ? entity : null;
		} else{
			GridArea area = marked.getEffectArea ();
			Array<BattleEntity> ents = getAffectedEntities (area);
			boolean finished = marked.actionPerformedOn (ents); 
			//TESTING ONLY
			finished = true;
			if (finished)
				marked = null;
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
