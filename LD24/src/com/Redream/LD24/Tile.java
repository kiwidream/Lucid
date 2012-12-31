package com.Redream.LD24;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Tile extends Renderable{
	public int type = TILE_BLANK;
	public static int TILE_BLANK = 1;
	public int stage;
	
	public int lx;
	public int ly;
	
	public Level level;
	
	public boolean collides = true;
	
	public boolean topcoll = false;
	
	public boolean fade = true;
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width*xScale,height*yScale);
	}
	
	public Tile(int x,int y){
		this.width = 8;
		this.height = 8;
		
		this.xScale = 4;
		this.yScale = 4;
		
		this.lx = x;
		this.ly = y;
		
		this.x = lx * width * xScale;
		this.y = ly * width * yScale;
		
		updateTex();
	}
	
	public void render(SpriteBatch batch){
		if(fade){
			float dy = Math.abs(y-level.player.y);
			float dx = Math.abs(x-level.player.x);
			float val = (float) Math.min(Math.abs(Math.sqrt(dx*dx+dy*dy))/level.player.fog,1f);
			this.color = new Color(1,1,1,1-val);
		}
		super.render(batch);
	}
	
	public void updateTex(){
		this.tex = 7;
	}

	public boolean collision(Entity entity) {
		if(entity.tex > 3 && entity.tex < 7 && tex == 7){
			this.tex = 11;
		}
		return true;
	}
}
