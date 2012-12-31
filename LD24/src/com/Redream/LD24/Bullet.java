package com.Redream.LD24;

import com.badlogic.gdx.math.Rectangle;


public class Bullet extends Entity {
	
	public Gun parent;

	public Bullet(float x, float y, int speed) {
		this.x = x;
		this.y = y;
		this.vX = speed;
		this.tex = 24;
		this.width = 8;
		this.height = 7;
		this.xScale = 3;
		this.yScale = 3;
		
		this.gravity = 0;
	}
	
	public void tick(){
		super.tick();
		Rectangle b = getBounds();
		b.x -= 4;
		b.width += 8;
		b.y -= 3;
		b.height += 6;
		if(this.level.player.getBounds().overlaps(b)){
			this.remove = true;
			this.level.player.kill();
		}
	}
	
	public void collide(){
		this.remove = true;
	}

}
