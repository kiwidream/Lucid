package com.Redream.LD24;

import java.util.ArrayList;
import java.util.List;


public class MovingPlatform extends Tile {
	
	public List<Entity> passengers = new ArrayList<Entity>();
	public List<Entity> toremove = new ArrayList<Entity>();
	
	public float rangeX;
	public float rangeY;
	public float baseX;
	public float baseY;
	
	public boolean forward = true;
	
	public boolean playerActivated = false;
	public float moveX;
	public float moveY;
	
	public int timer;

	public MovingPlatform(int x, int y, float rx, float ry, boolean pa,int tex, int time) {
		super(x, y);
		this.tex = 9;
		this.width=12;
		this.height=4;
		this.x -= 2*xScale;
		this.y += 4*yScale;
		
		this.timer = time;
		
		this.playerActivated = pa;
		this.rangeX = rx;
		this.rangeY = ry;
		this.fade = false;
		
		this.baseX = this.x;
		this.baseY = this.y;
	}
	
	public boolean collision(Entity e){
		this.passengers.add(e);
		if(e.y - e.vY + 6 > (this.y+height*yScale)){
			e.y = this.y+height*yScale;
			return true;
		}else{
			return false;
		}
		
	}
	
	public void tick(){
		moveY = rangeY / timer;
		moveX = rangeX / timer;
		
		if(!forward){
			moveY = -moveY;
			moveX = -moveX;
		}
		
		for(Entity e:passengers){
			if(Math.abs(e.y - (y+height*yScale)) > 2){
				toremove.add(e);
			}else{
				e.platform = this;
			}
		}
		
		if(!playerActivated || passengers.size()>0){
			this.y += moveY;
			this.x += moveX;
		}
		
		for(Entity e:toremove){
			passengers.remove(e);
			e.platform = null;
		}
		toremove.clear();
		
		if(Math.abs(x - baseX) > Math.abs(rangeX) ||  Math.abs(y - baseY) > Math.abs(rangeY)){
			forward = !forward;
		}
		
		if(Math.abs(x-baseX-rangeX) > Math.abs(rangeX) || Math.abs(y-baseY-rangeY) > Math.abs(rangeY)){
			forward = !forward;
		}
	}

}
