package com.Redream.LD24;


public class CheckPoint extends Tile {

	public CheckPoint(int x, int y) {
		super(x, y);
		this.height = 12;
		this.width = 5;
		this.tex = 23;
		this.collides = false;
	}
	
	public void tick(){
		if(this.level.player.getBounds().overlaps(getBounds())){
			level.spawnX = x;
			level.spawnY = y+1;
		}
	}

}
