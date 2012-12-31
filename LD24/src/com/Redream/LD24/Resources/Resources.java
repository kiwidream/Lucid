package com.Redream.LD24.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Resources {
	public static TextureAtlas atlas = new TextureAtlas(file("pack"));
	
	public static final Pixmap level = loadPixmap("level.png");

	public static boolean[] flipX = new boolean[500];
	public static boolean[] flipY = new boolean[500];

	public static AtlasRegion[] regions = new AtlasRegion[500];

	public static BitmapFont font = new BitmapFont(file("volter.fnt"),file("volter.png"),false);
	
	public static final Sound music = Gdx.audio.newSound(file("music.wav"));
	
	public static FileHandle file(String src) {
		FileHandle fh;
		fh = Gdx.files.internal("com/Redream/LD24/Resources/" + src);
		if(!fh.exists())fh = Gdx.files.internal(src);
		return fh;
	}
	
	private static Pixmap loadPixmap(String src) {
		return new Pixmap(file(src));
	}

}
