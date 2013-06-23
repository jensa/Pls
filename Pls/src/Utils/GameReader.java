package Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import entities.Obstacle;

public class GameReader {
	
	private int entityID = 0;
	
	private final String CONF_BASE = "data/conf/";
	private final String OBSTACLES = "obstacles.pls";
	private final String UNITS = "units.pls";
	TextureAtlas atlas;
	
	public GameReader (TextureAtlas atlas){
		this.atlas = atlas;
	}
	/**
	 * Read all possible obstacles from config files
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
			float width = 0f;
			float height = 0f;
			while (line != null){
				if (line.startsWith ("[")){
					//Start of obstacle, save the old
					if (name != null){
						Sprite s = atlas.createSprite (img);
						s.setSize (width*cellSize, height*cellSize);
						obstacles.add (new Obstacle (s, entityID++));
					}
					name = line.substring (1, line.indexOf ("]")).trim ();
				}
				else if (line.startsWith ("img")){
					img = line.substring (line.indexOf ("img=")+4);
				}
				else if (line.startsWith ("width")){
					width = Float.parseFloat (line.substring (line.indexOf ("width=")+6));
				}
				else if (line.startsWith ("height")){
					height = Float.parseFloat (line.substring (line.indexOf ("height=")+7));
				}
				line = br.readLine ();
			}
			if (name != null){
				Sprite s = atlas.createSprite (img);
				s.setSize (width*cellSize, height*cellSize);
				obstacles.add (new Obstacle (s, entityID++));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obstacles;
	}
	
	

}
