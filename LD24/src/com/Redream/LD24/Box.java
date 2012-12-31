package com.Redream.LD24;


public class Box extends Entity {
	
	public boolean held = false;
	
	public float startX;
	public float startY;

	public Box(int x, int y) {
		this.collides = true;
		this.width = 11;
		this.height = 11;
		this.xScale = 3;
		this.yScale = 3;
		this.tex = 33;
		
		this.x = x * 32;
		this.y = y * 32;
		
		this.gravity = 0.2f;
		
		this.startX = this.x;
		this.startY = this.y;
	}
	
	public boolean drop(boolean mirror){
		int movex = 0;
		
		
		if(!mirror){
			movex += 10;
		}else{
			movex -= 10;
		}
		
		this.collides = true;
		
		if(!this.move2(movex, 0)){
			return false;
		}
		
		
		return true;
	}

}
