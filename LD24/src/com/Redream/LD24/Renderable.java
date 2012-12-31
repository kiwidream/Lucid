package com.Redream.LD24;

import com.Redream.LD24.Resources.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;

public class Renderable implements InputListener{
	public float x;
	public float y;
	public int z;
	
	public int tex;
	
	public int width;
	public int height;
	
	public boolean applyCam = true;
	
	public Color color;
	
	public boolean mirrorX;
	public boolean mirrorY;
	
	public AtlasRegion ar;
	
	public int loadedTex;
	
	public float xScale;
	public float yScale;
	
	public float rot;
	public float origX;
	public float origY;
	public boolean collides;
	
	public AtlasRegion flipX(AtlasRegion ar,boolean x) {
		
		if((!Resources.flipX[this.tex] && x) || (Resources.flipX[this.tex] && !x)){
			ar.flip(true, false);
		}
		
		this.mirrorX = x;
		
		Resources.flipX[this.tex] = x;
		
		return ar;
	}
	
	public AtlasRegion flipY(AtlasRegion ar,boolean y){
		
		if((!Resources.flipY[this.tex] && y) || (Resources.flipY[this.tex] && !y)){
			ar.flip(false, true);
		}
		
		this.mirrorY = y;
		Resources.flipY[this.tex] = y;
		return ar;
	}
	
	public AtlasRegion flip(AtlasRegion ar,boolean x, boolean y){
		return flipX(flipY(ar,y),x);
	}

	public void queueRender(Display display) {
		display.queueRender(this);
	}

	public void tick() {

	}

	public AtlasRegion getTexture() {
		if(loadedTex != this.tex){
			if(Resources.regions[this.tex] == null){
				ar = Resources.atlas.findRegion(Integer.toString(this.tex));
				System.out.println(this.tex+" "+ar);
				Resources.regions[this.tex] = ar;
			}else{
				ar = Resources.regions[this.tex];
			}
		}
		return flip(ar,this.mirrorX,this.mirrorY);
	}
	
	public void render(SpriteBatch batch){
		if(tex == 0)return;
		
		AtlasRegion ar = getTexture();
		
		float x = this.x - Game.WIDTH / 2;
		float y = this.y - Game.HEIGHT / 2;

		if(width == 0 || height == 0){
			width = ar.getRegionWidth();
			height = ar.getRegionHeight();
		}
		
		batch.setColor(Color.WHITE);
		
		if(color != null)batch.setColor(color);

		batch.draw(ar, x, y, origX, origY, width, height, xScale, yScale, rot);
	}

	public boolean touchCollTest() {
		return false;
	}

	public Rectangle getBounds() {
		return new Rectangle();
	}

	public boolean keyDown(int keycode) {
		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int x, int y, int pointer) {
		return false;
	}

	public boolean touchUp(int x, int y, int pointer) {
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	public boolean touchMoved(int x, int y) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}

	public boolean collision(Entity entity) {
		return true;
	}
	
}
