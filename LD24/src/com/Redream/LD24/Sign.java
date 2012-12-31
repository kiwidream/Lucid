package com.Redream.LD24;


public class Sign extends Tile {

	public Sign(int x, int y) {
		super(x, y);
		this.width = 12;
		this.height = 13;
		this.rot = -45;
		this.collides = false;
		this.origX = 6;
		this.tex = 22;
		this.x += 16;
	}

}
