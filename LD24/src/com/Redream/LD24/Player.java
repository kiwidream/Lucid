package com.Redream.LD24;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;


public class Player extends Entity{
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	public boolean shift;
	
	public Box box;
	
	public int basetex = 1;
	
	public int timer = 10;
	
	public float fog;
	public float targetfog;
	private int respawnTimer;

	public Player(float x,float y){
		this.tex = 1;
		this.width = 9;
		this.height = 11;
		this.xScale = 4;
		this.yScale = 4;
		this.targetfog = 200;
		this.collides = true;
		
		this.x = x;
		this.y = y;
		
		Input.registerListener(this);
		Camera.setTarget(this);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+10,y,(width*xScale)-20,height*yScale);
	}
	
	public void tick(){
		this.fog += (this.targetfog-this.fog)*0.05;
		if(up && groundCollision == true){
			this.vY = 7f;
			
			if(basetex == 4 || basetex == 34){
				this.vY = 10f;
			}
		}
		
		if(left){
			this.vX = -2.4f;
			if((basetex == 13 || basetex == 34) && shift){
				this.vX = -6f;
			}
		}
		if(right){
			this.vX = 2.4f;
			if((basetex == 13 || basetex == 34) && shift){
				this.vX = 6f;
			}
		}
		if(!left && !right && !level.inCutscene){			
			if(basetex == 29 && shift){
				this.vX *= 0.98;
			}else if(groundCollision){
				this.vX = 0;
			}else{
				this.vX *= 0.9;
			}
		}
		
		if(vX < 0){
			this.mirrorX = true;
		}else if(vX > 0){
			this.mirrorX = false;
		}
		
		if(vX != 0){
			this.timer--;
			if(timer < 0){
				if(this.tex == basetex){
					this.tex = basetex+1;
				}else if(this.tex == basetex+2){
					this.tex = basetex;
				}else if(this.tex == basetex+1){
					this.tex = basetex+2;
				}
				timer = 5;
			}
		}else{
			this.tex = basetex;
			timer = 5;
		}
		
		if(x < Camera.BoundaryStartX){
			x = Camera.BoundaryStartX;
		}
		
		if(y<-500){
			this.y = level.spawnY;
			this.x = level.spawnX;
		}
		
		if(respawnTimer > 0){
			respawnTimer--;
			if(respawnTimer == 0){
				this.targetfog = 350;
			}
		}
		
		if(box != null){
			box.x = x + (mirrorX ? -20 : 30);
			box.y = y + 10;
			box.z = 10;
			box.collides = false;
		}
		
		super.tick();
	}
	
	public boolean keyDown(int keycode) {
		if(!level.inCutscene){
			if(keycode == Keys.W){
				up = true;
			}
			if(keycode == Keys.A){
				left = true;
			}
			if(keycode == Keys.S){
				down = true;
			}
			if(keycode == Keys.D){
				right = true;
			}
			if(keycode == Keys.SHIFT_LEFT){
				shift = true;
			}
			
			if(keycode == Keys.SPACE && (basetex == 29 || basetex == 34)){
				
				if(box == null){
					for(Entity e:level.entities){
						if(e == null)continue;
						float minrange = 75;
						if(e.tex == 33){
							float dx = Math.abs(e.x - x + (mirrorX ? -20 : 30));
							float dy = Math.abs(e.y - y + 10);
							float dist = (float) Math.sqrt(dx*dx+dy*dy);
							if(((mirrorX && e.x < x) || (!mirrorX && e.x > x)) && dist < minrange){
								if(level.lprog == 9 && !level.dialogcanfade)level.dialogcanfade = true;
								Box b = (Box)e;
								this.box = b;
								minrange = dist;
							}
						}
					}
				}else{
					if(box.drop(mirrorX)){
						this.vX = 0;
						this.vY = 0;
						box = null;
					}
				}
			}
		}
		
		return true;
	}

	public boolean keyUp(int keycode) {
		if(keycode == Keys.W){
			up = false;
		}
		if(keycode == Keys.A){
			left = false;
		}
		if(keycode == Keys.S){
			down = false;
		}
		if(keycode == Keys.D){
			right = false;
		}
		if(keycode == Keys.SHIFT_LEFT){
			shift = false;
		}
		return true;
	}

	public void kill() {
		this.targetfog = 0;
		this.x = level.spawnX;
		this.y = level.spawnY;
		respawnTimer = 20;
		this.fog = 0;
		
		box = null;
		
		for(Entity e : level.entities){
			if(e.tex == 33){
				Box b = (Box)e;
				b.x = b.startX;
				b.y = b.startY;
				b.collides = true;
			}
		}
		
		for(Tile t : level.tiles){
			if(t == null)continue;
			if(t instanceof BreakableTile){
				t.tex = 25;
				t.collides = true;
			}
		}
	}

}
