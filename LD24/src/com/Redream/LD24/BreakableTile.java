package com.Redream.LD24;

import com.badlogic.gdx.Input.Keys;


public class BreakableTile extends Tile {

	public BreakableTile(int x, int y) {
		super(x, y);
		Input.registerListener(this);
		this.tex = 25;
	}
	
	public boolean keyDown(int keycode) {
		if((level.player.basetex == 13 || level.player.basetex == 34) && keycode == Keys.SPACE && Math.abs(this.x - level.player.x) < 60 && Math.abs(this.y - level.player.y) < 60){
			this.tex = 0;
			this.collides = false;
			
			if(level.lprog == 7 && !level.dialogcanfade){
				level.dialogcanfade = true;
			}
		}
		return true;
	}
	
}
