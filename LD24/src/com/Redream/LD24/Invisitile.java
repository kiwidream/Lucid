package com.Redream.LD24;


public class Invisitile extends Tile {

	public Invisitile(int x, int y) {
		super(x, y);
		this.tex = 0;
		this.collides = false;
	}
	
	public void tick(){
		System.out.println(tex);
		if(Math.abs(x-level.player.x) < 70){
			System.out.println("pew");
			this.collides = true;
			if(level.lprog == 2){
				level.spawnX = 4848;
				level.spawnY = 1024;
				level.lprog++;
			}
			tex = 10;
		}
	}

}
