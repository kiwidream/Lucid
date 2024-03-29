package com.Redream.LD24;


import com.Redream.LD24.Resources.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Font extends Renderable{
	private String txt;
	
	public static int POS_LEFT = 0;
	public static int POS_RIGHT = 1;
	public static int POS_CENTER = 2;
	
	private int pos = 0;
	
	public Font(String txt,float x,float y,int pos, Color color, boolean cam){
		this.applyCam = cam;
		this.txt = txt;
		this.x = x;
		this.y = y;
		this.pos = pos;
		this.z = 0;
		this.color = color;
	}
	
	public void render(SpriteBatch batch){
		float x = this.x - Game.WIDTH / 2;
		float y = this.y - Game.HEIGHT / 2 + 29;
		if(pos == POS_RIGHT) x -= Resources.font.getBounds(txt).width;
		if(pos == POS_CENTER) x -= Resources.font.getBounds(txt).width/2;
		Resources.font.setColor(color);
		Resources.font.draw(batch, txt, x, y);
	}
}
