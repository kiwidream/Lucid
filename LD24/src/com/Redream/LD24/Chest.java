package com.Redream.LD24;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;


public class Chest extends Tile {

	private boolean opened = false;
	
	public int opencycles = 0;
	public int opentimer = 5;

	private int changeTo;

	public Chest(int x, int y, int changeTo, int tex) {
		super(x, y);
		this.changeTo = changeTo;
		this.tex = tex;
		this.x -= 2;
		this.collides = false;
		this.fade = false;
		Input.registerListener(this);
	}
	
	public void tick(){
		if(opened && opencycles < 9){
			opentimer--;
			if(opentimer < 0){
				opencycles++;
				opentimer = opencycles*3+5;
				if(opencycles % 2 == 0){
					pcycle2();
				}else{
					pcycle1();
				}
				
				if(opencycles == 9){
					level.player.targetfog = 400;
					if(changeTo == 13){
						for(Tile t:level.tiles){
							if(t == null)continue;
							if(t.tex == 11 || t.tex == 7){
								t.tex = 16;
							}
							
							if(t.tex == 10){
								t.tex = 17;
							}
							
							if(t.tex == 9){
								t.tex = 18;
							}
						}
						level.showDialog(17);
						level.lprog = 6;
					}else if(changeTo == 29){
						for(Tile t:level.tiles){
							if(t == null)continue;
							if(t.tex == 16 || t.tex == 11 || t.tex == 7){
								t.tex = 27;
							}
							
							if(t.tex == 17 || t.tex == 10){
								t.tex = 28;
							}
						}
						level.showDialog(21);
						level.lprog++;
					}else if(changeTo == 34){
						for(Tile t:level.tiles){
							if(t == null)continue;
							if(t.tex == 16 || t.tex == 11 || t.tex == 7 || t.tex == 27){
								t.tex = 37;
							}
							
							if(t.tex == 17 || t.tex == 10 || t.tex == 28){
								t.tex = 38;
							}
						}
						level.showDialog(24);
						level.lprog++;
					}else{
						level.showDialog(5);
					}
				}
			}
		}
	}
	
	public void queueRender(Display display){
		super.queueRender(display);

		if(!opened && Math.abs(this.x - level.player.x) < 100){
			new Font("E",this.x+16,this.y + 30,Font.POS_CENTER,Color.GRAY,true).queueRender(display);
		}
	}
	
	public boolean keyDown(int keycode) {
		if(!opened && keycode == Keys.E && Math.abs(this.x - level.player.x) < 150){
			this.opened = true;
			if(changeTo == 13){
				level.player.targetfog = 0;
			}else{
				level.player.targetfog = 120;
			}
			if(changeTo == 13){
				level.showDialog(18);
			}else if(changeTo == 29){
				level.showDialog(20);
			}else if(changeTo == 34){
				level.showDialog(23);
			}else{
				level.showDialog(4);
			}
		}
		return true;
	}
	
	public void pcycle1(){
		level.player.basetex = changeTo;
		level.player.tex = level.player.basetex;
		level.player.width = 11;
		level.player.height = 14;
		level.player.x -= 5;
	}
	
	public void pcycle2(){
		level.player.basetex = 1;
		level.player.tex = level.player.basetex;
		level.player.width = 9;
		level.player.height = 11;
		level.player.x += 5;
	}

}
