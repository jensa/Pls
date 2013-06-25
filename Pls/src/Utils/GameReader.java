package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import entities.BattleUnit;
import entities.Obstacle;

public class GameReader {
	
	private static int entityID = 0;
	private final String GRID_DIM = "grid_dimensions=";
	private final String SPRITE_DIM = "sprite_dimensions=";
	private final String IMG = "image_name=";
	private final String NUM_IMAGES = "images=";
	
	private final String OBSTACLE_ID = "obstacle_";
	private final String UNIT_ID = "unit_";
	
	private final String CONF_BASE = "data/conf/";
	private final String OBSTACLES = "obstacles.pls";
	private final String UNITS = "units.pls";
	TextureAtlas atlas;
	
	public GameReader (TextureAtlas atlas){
		this.atlas = atlas;
	}
	/**
	 * Read all possible obstacles with size BUT NOT position set from config files.
	 * TODO: different obstacles for different terrain
	 * @param cellSize size of a grid cell in pixels
	 * @return an array of all obstacles
	 */
	public Array<Obstacle> readObstacles (int cellSize){
		Array<Obstacle> obstacles = new Array<Obstacle> ();
		try {
			FileInputStream fis = new FileInputStream (new File (CONF_BASE+OBSTACLES));
			InputStreamReader isr = new InputStreamReader (fis, "ISO-8859-1");
			BufferedReader br = new BufferedReader (isr);
			String line = br.readLine ();
			String name = null;
			String img = null;
			int width = 0;
			int height = 0;
			while (line != null){
				Helper.log (line);
				if (line.startsWith ("[")){
					//Start of obstacle, save the old
					if (name != null){
						addObstacle (obstacles, atlas, img, cellSize, width, height);
					}
					name = line.substring (1, line.indexOf ("]")).trim ();
				}
				else if (line.startsWith (IMG)){
					img = line.substring (line.indexOf (IMG)+IMG.length ());
				}
				else if (line.startsWith (GRID_DIM)){
					String[] dims = line.substring (line.indexOf (GRID_DIM)+GRID_DIM.length ()).split (",");
					width = Integer.parseInt (dims[0]);
					height = Integer.parseInt (dims[1]);
				}
				line = br.readLine ();
			}
			if (name != null){
				addObstacle (obstacles, atlas, img, cellSize, width, height);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obstacles;
	}
	
	private void addObstacle (Array<Obstacle> obstacles, TextureAtlas atlas, String img, 
			int cellSize, int width, int height){
		Helper.log (OBSTACLE_ID+img);
		Sprite s = atlas.createSprite (OBSTACLE_ID+img);
		s.setSize (width*cellSize, height*cellSize);
		obstacles.add (new Obstacle (s, entityID++, width, height));
	}
	
	
	public Map<String,BattleUnit> readBattleUnits (int cellSize){
		Map<String, BattleUnit> units = new HashMap<String, BattleUnit> ();
		try {
			FileInputStream fis = new FileInputStream (new File (CONF_BASE+UNITS));
			InputStreamReader isr = new InputStreamReader (fis, "ISO-8859-1");
			BufferedReader br = new BufferedReader (isr);
			String line = br.readLine ();
			String name = null;
			String img = null;
			int numImages = 0;
			int width = 0;
			int height = 0;
			float spriteW = 0;
			float spriteH = 0;
			while (line != null){
				Helper.log (line);
				if (line.startsWith ("[")){
					//Start of unit, save the old
					if (name != null){
						addUnit (units, atlas, img, name,  width, height, numImages, spriteW, spriteH);
					}
					name = line.substring (1, line.indexOf ("]")).trim ();
				}
				else if (line.startsWith (IMG)){
					img = line.substring (line.indexOf (IMG)+IMG.length ());
				}
				else if (line.startsWith (GRID_DIM)){
					String[] dims = line.substring (line.indexOf (GRID_DIM)+GRID_DIM.length ()).split (",");
					width = Integer.parseInt (dims[0]);
					height = Integer.parseInt (dims[1]);
				}
				else if (line.startsWith (SPRITE_DIM)){
					String[] dims = line.substring (line.indexOf (SPRITE_DIM)+SPRITE_DIM.length ()).split (",");
					spriteW = Float.parseFloat (dims[0]) * cellSize;
					spriteH = Float.parseFloat (dims[1]) * cellSize;
				}
				else if (line.startsWith (NUM_IMAGES)){
					numImages = Integer.parseInt (line.substring (line.indexOf (NUM_IMAGES)+NUM_IMAGES.length ()));
				}
				line = br.readLine ();
			}
			if (name != null){
				addUnit (units, atlas, img, name, numImages, width, height, spriteW, spriteH);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return units;
	}
	
	private void addUnit (Map<String, BattleUnit> units, TextureAtlas atlas, String img, String name, int numImages,
			int width, int height, float spriteW, float spriteH){
		units.put (name, new BattleUnit (entityID++,UNIT_ID+img, width, height, numImages, spriteW, spriteH));
	}
	
	public int getCurrentIDCount (){
		return entityID++;
		
	}
	
	

}
