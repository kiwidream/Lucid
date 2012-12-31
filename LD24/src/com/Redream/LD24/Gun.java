package com.Redream.LD24;

import java.util.Random;


public class Gun extends Tile {
	
	private int shootTimer = new Random().nextInt(100);

	public Gun(int x, int y) {
		super(x, y);
		this.tex = 19;
		this.width = 13;
		this.height = 6;
		this.fade = false;
	}
	
	public void tick(){
		shootTimer--;
		
		if(shootTimer < 0){
			shootTimer = 100;
			level.addEntity(new Bullet(x-25,y+7,-(new Random().nextInt(3))-4));
		}
	}

}
