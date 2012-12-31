package com.Redream.LD24;

import com.Redream.LD24.Resources.Resources;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Game implements ApplicationListener, InputListener{

	public static int WIDTH;
	public static int HEIGHT;
	
	public static float DIAGDIST;
	
	public static float screenRatioY;
	public static float screenRatioX;
	public static boolean debug;
	
	private SpriteBatch batch;
	private Display display;
	private Input input;
	
	private double unprocessed;
	public static final double TICK_TIME = 0.0166667;
	public static int gprog = 0;
	
	public Level level;
	private long musicid;
	private boolean mute;

	public void create() {
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		Camera.HUDcam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		
		this.batch = new SpriteBatch();
		this.display = new Display(this.batch);
		
		this.input = new Input();
		Gdx.input.setInputProcessor(input);
		
		Input.registerListener(this);
		
		level = new Level();
		
		musicid = Resources.music.play();
		Resources.music.setVolume(musicid, 0.5f);
		Resources.music.setLooping(musicid,true);
	}

	public void dispose() {
	}

	public void pause() {
	}

	public void render() {
		if (this.unprocessed < 3) {
			this.unprocessed += Game.timeDelta();
		}

		if (this.unprocessed > 1) {
			this.tick();
			this.unprocessed -= 1;
		}
		
		this.batch.begin();

		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl10.glClearColor(1,1,1,1);
		
		if(gprog == 1){
			this.level.queueRender(display);
		}else if(gprog == 0){
			Renderable logo = new Renderable();
			logo.tex = 20;
			logo.xScale = 6;
			logo.yScale = 6;
			logo.width = 103;
			logo.height = 19;
			logo.queueRender(display);
			
			logo.x = Game.WIDTH/2-309;
			logo.y = Game.HEIGHT - 150;
			
			new Font("press SPACE to begin", Game.WIDTH/2, 80, Font.POS_CENTER, Color.GRAY, false).queueRender(display);
			new Font("created in 48H for Ludum Dare #24, Theme: Evolution", Game.WIDTH/2, 20, Font.POS_CENTER, Color.LIGHT_GRAY, false).queueRender(display);
		}else{
			Renderable logo = new Renderable();
			logo.tex = 20;
			logo.xScale = 6;
			logo.yScale = 6;
			logo.width = 103;
			logo.height = 19;
			logo.applyCam = false;
			logo.queueRender(display);
			
			logo.x = Game.WIDTH/2-309;
			logo.y = Game.HEIGHT - 150;
			
			new Font("Thank you for playing!", Game.WIDTH/2, 100, Font.POS_CENTER, Color.GRAY, false).queueRender(display);
		}
		
		this.display.render();
		this.display.renderQueue.clear();
		this.display.renderQueueHUD.clear();

		this.batch.end();
	}

	public static double timeDelta() {
		return Gdx.graphics.getDeltaTime() / TICK_TIME;
	}

	private void tick() {
		if(gprog == 1){
			level.tick();
			Camera.tick();
		}
	}

	public void resize(int width, int height) {
		float dratio = (float)width/(float)height;

		Game.WIDTH = (int) (480 * dratio);
		Game.HEIGHT = 480;
		
		Game.screenRatioX = (float)Game.WIDTH / (float)width;
		Game.screenRatioY = (float)Game.HEIGHT / (float)height;
		
		Gdx.graphics.setDisplayMode(width, height, false);
		
		DIAGDIST = (float) Math.abs(Math.sqrt(Math.pow(-Game.WIDTH, 2.0D) + Math.pow(Game.HEIGHT, 2.0D)));
		
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		Camera.HUDcam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
	}

	public void resume() {
	}

	@Override
	public boolean touchCollTest() {
		return false;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE){
			Game.gprog=1;
		}
		
		if(keycode == Keys.M){
			if(!mute){
				Resources.music.stop();
				mute = true;
			}else{
				musicid = Resources.music.play();
				Resources.music.setLooping(musicid,true);
				mute= false;
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
