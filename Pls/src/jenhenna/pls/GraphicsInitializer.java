package jenhenna.pls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class GraphicsInitializer {
	
	public static final String imagePath = "data/images";
	
	public static TextureAtlas getTextures (){
		Settings settings = new Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        TexturePacker2.process(settings, imagePath, imagePath+"/packed", "game");
        return new TextureAtlas(Gdx.files.internal(imagePath+"/packed/game.atlas"));
		
	}

}
