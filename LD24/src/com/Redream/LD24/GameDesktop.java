package com.Redream.LD24;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameDesktop {
	public static void main(String[] args) {
		//System.setProperty("org.lwjgl.util.Debug","true");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.vSyncEnabled = false;
		config.title = "Lucid";
		config.useGL20 = false;
		config.height = 480;
		config.width = 800;
		new LwjglApplication(new Game(), config);
	}
}