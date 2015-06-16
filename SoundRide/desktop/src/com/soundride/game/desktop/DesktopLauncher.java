package com.soundride.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import controller.game.SRGame;
/**
 * Launcher for the desktop version of the game (there are no others, but it's easy to create 
 * other ones - for android, ios etc.
 */
public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Sound Ride";
		config.width = 500; // scales the basic size set in the GameRenderer
		config.height = 400;
		new LwjglApplication(new SRGame(), config);
	}
}
