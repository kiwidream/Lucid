package com.Redream.LD24;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Camera {
	public static boolean disableCollisionX = false;
	public static boolean disableCollisionY = false;
	public static int BoundaryX;
	public static int BoundaryY;
	public static int BoundaryStartX = 0;
	public static int BoundaryStartY = 0;

	public static float angle = 0;

	public static boolean disable = false;

	public static OrthographicCamera cam;
	public static OrthographicCamera HUDcam;
	public static Renderable target;

	public static void tick() {
		if(!disable){
			if (Gdx.input.isKeyPressed(Keys.NUM_9)) {
				cam.zoom -= 0.01;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
				cam.zoom += 0.01;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_0)) {
				cam.zoom = 1;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
				cam.rotate(-0.5f, 0, 0, 1);
				Camera.angle += 0.5;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
				cam.rotate(0.5f, 0, 0, 1);
				Camera.angle -= 0.5;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_7)) {
				Camera.reset();
				Camera.updatePos();
				Camera.angle = 0;
			}
			
			if(target != null){
				final float moveX = (float) (((target.x + (target.width*target.xScale)/2 - Game.WIDTH / 2) - cam.position.x) * 0.03);
				final float moveY = (float) (((target.y + (target.height*target.yScale)/2 - Game.HEIGHT / 2) - cam.position.y) * 0.03);
				cam.translate(moveX, moveY, 0);
			}
			
			if(Camera.cam.position.x < Camera.BoundaryStartX){
				Camera.cam.position.x = 0;
			}
			
			if(Camera.cam.position.y < Camera.BoundaryStartY){
				Camera.cam.position.y = 0;
			}
		}

		cam.update();
	}
	
	public static void setTarget(Renderable target){
		Camera.target = target;
	}

	public static void updatePos() {
		if(target == null)return;
		final float moveX = (target.x + 13 - Game.WIDTH / 2) - cam.position.x;
		final float moveY = (target.y + 35 - Game.HEIGHT / 2) - cam.position.y;
		cam.translate(moveX, moveY, 0);
	}

	public static void reset() {
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
//		Camera.ParallaxCam = new OrthographicCamera(Game.WIDTH,Game.HEIGHT);
	}
}

