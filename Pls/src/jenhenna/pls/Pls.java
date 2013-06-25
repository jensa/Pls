package jenhenna.pls;

import Utils.Coord;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import controller.ClickHandler;

public class Pls implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private BattleScene battle;
	
	private Array<Sprite> testMarkers;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		TextureAtlas atlas = GraphicsInitializer.getTextures ();
		battle = new BattleScene (w, h, 30, atlas);
		//simply change the click handler when we go out of battle
		Gdx.input.setInputProcessor(new ClickHandler (battle));
		
		camera = new OrthographicCamera(w, h);
		
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		testMarkers = new Array<Sprite> ();//getTestMarkers (atlas);
	}

	private Array<Sprite> getTestMarkers (TextureAtlas atlas) {
		Sprite femtio = atlas.createSprite ("testmarker_femtio");
		Coord c = Coord.getTransformedSpriteCoords (599, 588, femtio.getHeight ());
		femtio.setPosition (c.x, c.y);
		
		Sprite hundra = atlas.createSprite ("testmarker_hundra");
		hundra.setPosition (100, 100);
		Array<Sprite> ret = new Array<Sprite> ();
		ret.add (hundra);
		ret.add (femtio);
		return ret;
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		battle.getBackground ().draw (batch);
		batch.end ();
		battle.getDebugGrid ();
		batch.begin ();
		for (Sprite sp : battle.getBattleSprites ())
			sp.draw(batch);
		for (Sprite sp : testMarkers)
			sp.draw (batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
