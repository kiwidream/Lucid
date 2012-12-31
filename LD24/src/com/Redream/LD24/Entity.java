package com.Redream.LD24;

import com.badlogic.gdx.math.Rectangle;


public class Entity extends Renderable{
	
	
	public float vX;
	public float vY;
	
	public float lastgroundtouchY;
	
	public boolean collided;
	public boolean groundCollision;
	
	public float gravity = 1;
	
	public Level level;
	public boolean onplatform;
	public MovingPlatform platform;
	
	public boolean glide;
	public boolean remove;
	
	public boolean collides = true;
	
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width*xScale,height*yScale);
	}
	
	public void tick(){
		this.move(vX,vY);
	}
	
	public boolean move(float vX, float vY){

		this.vY -= gravity * 0.35;
		
		if(platform != null){
			if(platform.moveY<0){
				y = platform.y+platform.height*platform.yScale;
			}
			this.move2(vX + platform.moveX, 0);
			this.move2(0, vY + platform.moveY);
		}else{
			this.move2(vX, 0);
			this.move2(0, vY);
		}
		return true;
	}
	
	public boolean move2(float vX, float vY){
		if(vX == 0 && vY == 0){
			return true;
		}
		this.x += vX;
		this.y += vY;
		
		if(!this.collides)return true;
		
		Rectangle b = this.getBounds();
		
		for(int i=0;i<level.tiles.length;i++){
			Tile t = level.tiles[i];
			
			if(t == null || !t.collides)continue;
			
			Rectangle tb = t.getBounds();
			
			if(b.overlaps(tb)){
				if(t.collision(this)){
					this.collide();
					if(platform == null)this.x -= vX;
					this.y -= vY;
					if(vX == 0)this.vY = 0;
					if(vY == 0 && platform == null)this.vX = 0;
					this.collided = true;
					
					if(b.y > tb.y){
						this.groundCollision = true;
						this.lastgroundtouchY = b.y;
					}
					return false;
				}
			}
		}
		
		for(Entity e : level.entities){
			if(e == null || !e.collides || e == this)continue;
			
			Rectangle tb = e.getBounds();
			
			if(b.overlaps(tb)){
				
				if(e.collision(this)){
					this.collide();
					if(platform == null)this.x -= vX;
					this.y -= vY;
					if(vX == 0)this.vY = 0;
					if(vY == 0 && platform == null)this.vX = 0;
					this.collided = true;
					
					if(b.y > tb.y){
						this.groundCollision = true;
						this.lastgroundtouchY = b.y;
					}
					return false;
				}
			}
		}
		
		
		this.groundCollision = false;
		this.collided = false;
		return true;
	}
	
	public void collide(){
		
	}
}
