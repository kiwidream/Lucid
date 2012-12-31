package com.Redream.LD24;

import java.util.ArrayList;
import java.util.List;

import com.Redream.LD24.Resources.Resources;
import com.badlogic.gdx.graphics.Color;


public class Level {
	public Tile[] tiles;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public int w;
	public int h;
	
	private String[] dialog = new String[]{
			"*phew* almost.. there..",
			"I don't think I can see it anymore..",
			"Oh look! A box. Somebody must have left it here.",
			"Wha.. What's happening?",
			"I feel.. strange.",
			"Oh well. That was fun while it lasted.",
			"Ouch.. Where am I?",
			"You are trapped inside of your subconscious.",
			"It's an interesting place in here, isn't it?",
			"Oh, I guess you want to get out of here. That's fine.",
			"Remember when you first arrived, and something was chasing you?",
			"That was you.",
			"Everything in here is populated by your mind, including me.",
			"The only way out is to master the skills hidden within you.",
			"Try not to lose your mind while attempting this task.",
			"Good luck, you'll need it.",
			"I feel.. so much POWER!",
			"Arrghh..",
			"Use LEFT SHIFT to sprint, SPACE to break blocks",
			"...",
			"I feel so STRONG!",
			"Press SPACE to move and drop boxes",
			"Ergggh..",
			"The end.. it's close. I can feel it.",
			"You made it. You are free.",
	};
	
	private String activedialog = "*phew*";
	private int dialogshow = 0;
	private int dialogprogress = 0;
	private int dialogtimer = 2;
	private float dialogfadeprogress = 0;
	
	public boolean inCutscene;
	private int cutsceneTimer = 400;
	
	public int lprog = 0;
	
	public Player player;
	public float spawnY;
	public float spawnX;
	private int nextDialog = 8;
	public boolean dialogcanfade = true;
	
	public Level(){
		generateLevel();
		this.inCutscene = true;
		this.showDialog(1);
	}

	private void generateLevel() {
		w = Resources.level.getWidth();
		h = Resources.level.getHeight();
		
		this.tiles = new Tile[w*h];
		
		for(int i=0;i<w*h;i++){
			int x = i%w;
			int y = i/w;
			Color c = new Color();
			Color.rgba8888ToColor(c, Resources.level.getPixel(x, y));
			
			y = h - y;
			
			int id = (int) (c.r*255);
			
			if(id == 1)tiles[i] = new Tile(x,y);
			if(id == 5){
				tiles[i] = new Tile(x,y);
				tiles[i].tex = 10; 
			}
			if(id == 6){
				tiles[i] = new Tile(x,y);
				tiles[i].tex = 11; 
				tiles[i].fade = false;
			}
			if(id == 8){
				tiles[i] = new Tile(x,y);
				tiles[i].tex = 16; 
				tiles[i].fade = false;
			}
			if(id == 16){
				tiles[i] = new Tile(x,y);
				tiles[i].tex = 27; 
				tiles[i].fade = false;
			}
			if(id == 18){
				tiles[i] = new Tile(x,y);
				tiles[i].tex = 37; 
				tiles[i].fade = false;
			}
			if(id == 12)tiles[i] = new Sign(x,y);
			if(id == 2)tiles[i] = new Chest(x,y,4,8);
			if(id == 7)tiles[i] = new Chest(x,y,13,12);
			if(id == 3)tiles[i] = new MovingPlatform(x,y,100,0,false,9,100);
			if(id == 4)tiles[i] = new MovingPlatform(x,y,-100,0,false,9,100);
			if(id == 9)tiles[i] = new MovingPlatform(x,y,0,210,false,19,200);
			if(id == 10)tiles[i] = new Gun(x,y);
			if(id == 11)tiles[i] = new Invisitile(x,y);
			if(id == 13)tiles[i] = new CheckPoint(x,y);
			if(id == 14)tiles[i] = new BreakableTile(x,y);
			if(id == 15)tiles[i] = new Chest(x,y,29,26);
			if(id == 17)this.addEntity(new Box(x,y));
			if(id == 19)tiles[i] = new Chest(x,y,34,39);
			
			if(tiles[i] != null){
				tiles[i].level = this;
			}
		}
		player = new Player(10,324);
		player.vX = 2;
		
		this.addEntity(player);
	}
	
	public void queueRender(Display display){
		for(int i=0;i<tiles.length;i++){
			if(tiles[i] != null){
				tiles[i].queueRender(display);
			}
		}
		
		for(Entity e:entities){
			e.queueRender(display);
		}
		if(lprog == 7){
			new Font(activedialog, Game.WIDTH/2, 100, Font.POS_CENTER,new Color(0,0,0,dialogfadeprogress*0.01f),false).queueRender(display);
		}else{
			new Font(activedialog, Game.WIDTH/2, Game.HEIGHT-100, Font.POS_CENTER,new Color(0,0,0,dialogfadeprogress*0.01f),false).queueRender(display);
		}
	}
	
	public void tick(){
		for(int i=0;i<entities.size();i++){
			entities.get(i).tick();
			if(entities.get(i).remove){
				entities.remove(i--);
			}
		}
		
		for(int i=0;i<tiles.length;i++){
			if(tiles[i] != null){
				tiles[i].tick();
			}
		}
		
		if(dialogshow != 0){
			dialogtimer--;
			if(dialogtimer < 0){
				dialogtimer = 2;
				if(dialogprogress == this.dialog[dialogshow-1].length()){
					this.dialogshow = 0;
					this.dialogprogress = 0;
				}else{
					this.activedialog = this.activedialog + this.dialog[dialogshow-1].charAt(dialogprogress);
					this.dialogprogress++;
				}
			}
		}
		
		if(this.inCutscene && lprog == 0){
			if(this.dialogfadeprogress < 0){
				player.vX = 0;
				player.mirrorX = true;
				this.showDialog(2);
			}
			this.cutsceneTimer--;
			if(this.cutsceneTimer < 0){
				this.inCutscene = false;
			}
		}
		
		if(lprog == 0 && player.x > 900){
			this.showDialog(3);
			this.lprog++;
		}
		
		if(lprog == 6 && dialogfadeprogress <= 0 && player.basetex == 13){
			this.showDialog(19);
			lprog++;
			this.dialogcanfade = false;
		}
		
		if(dialogfadeprogress > 0 && dialogshow == 0 && dialogcanfade ){
			dialogfadeprogress -= 0.6f;
		}
		
		if(player.x > 3400 && player.y > 1100 && lprog == 1){
			player.basetex = 1;
			player.tex = 1;
			player.width = 9;
			player.height = 11;
			player.x += 5;
			player.targetfog = 350;
			lprog++;
			showDialog(6);
		}
		
		if(lprog == 3 && player.groundCollision){
			showDialog(7);
			lprog++;
		}
		
		if(lprog == 4){
			if(this.dialogfadeprogress <= 0){
				if(nextDialog == 17){
					nextDialog = 0;
					lprog++;
				}else{
					if(nextDialog == 16){
						Chest c = new Chest(151, 22, 13, 12);
						c.level = this;
						tiles[22*w+151] = c;
					}
					this.showDialog(nextDialog);
					nextDialog++;
				}
			}
		}

		if(lprog == 10 && player.y > 1280 && player.x > 10700){
			this.showDialog(25);
			player.basetex = 1;
			player.tex = player.basetex;
			player.width = 9;
			player.height = 11;
			player.x += 5;
			player.gravity = 0;
			player.vY = 3;
			lprog++;
		}
		
		if(lprog == 11 && player.y > 1500 && dialogfadeprogress <= 0){
			Game.gprog = 2;
		}
		
		if(lprog == 8 && dialogfadeprogress <= 0 && player.basetex == 29){
			this.showDialog(22);
			this.dialogcanfade = false;
			lprog++;
		}
	}
	
	public void showDialog(int id){
		this.activedialog = "";
		this.dialogshow = id;
		this.dialogfadeprogress = 100;
		this.dialogprogress = 0;
	}
	
	public void addEntity(Entity e){
		e.level = this;
		this.entities.add(e);
	}
}
