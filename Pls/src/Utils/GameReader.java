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
	
	private int entityID = 0;
	
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
						Sprite s = atlas.createSprite (OBSTACLE_ID+img);
						s.setSize (width*cellSize, height*cellSize);
						obstacles.add (new Obstacle (s, entityID++, width, height));
					}
					name = line.substring (1, line.indexOf ("]")).trim ();
				}
				else if (line.startsWith ("img")){
					img = line.substring (line.indexOf ("img=")+4);
				}
				else if (line.startsWith ("width")){
					width = Integer.parseInt (line.substring (line.indexOf ("width=")+6));
				}
				else if (line.startsWith ("height")){
					height = Integer.parseInt (line.substring (line.indexOf ("height=")+7));
				}
				line = br.readLine ();
			}
			if (name != null){
				Sprite s = atlas.createSprite (OBSTACLE_ID+img);
				s.setSize (width*cellSize, height*cellSize);
				obstacles.add (new Obstacle (s, entityID++, width, height));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obstacles;
	}
	
	public Map<String,BattleUnit> readBattleUnits (int cellSize){
		Map<String, BattleUnit> units = new HashMap<String, BattleUnit> ();
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
					//Start of unit, save the old
					if (name != null){
						Sprite s = atlas.createSprite (OBSTACLE_ID+img);
						s.setSize (width*cellSize, height*cellSize);
						obstacles.add (new Obstacle (s, entityID++, width, height));
					}
					name = line.substring (1, line.indexOf ("]")).trim ();
				}
				else if (line.startsWith ("img")){
					img = line.substring (line.indexOf ("img=")+4);
				}
				else if (line.startsWith ("width")){
					width = Integer.parseInt (line.substring (line.indexOf ("width=")+6));
				}
				else if (line.startsWith ("height")){
					height = Integer.parseInt (line.substring (line.indexOf ("height=")+7));
				}
				line = br.readLine ();
			}
			if (name != null){
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return units;
	}
	
	public int getCurrentIDCount (){
		return entityID++;
		
	}
	
	

}
