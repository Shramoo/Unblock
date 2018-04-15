package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyUnblockMe;

public class DesktopLauncher {


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = MyUnblockMe.RESOLUTION;
		config.height = MyUnblockMe.RESOLUTION;
		new LwjglApplication(new MyUnblockMe(), config);

	}
}
