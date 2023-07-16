package com.ficuno.PSYKIES;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(30);
		config.setWindowedMode(1280, 720);

		config.useVsync(true);
		config.setTitle("PSYKIES");
		config.setWindowIcon("android/res/mipmap-mdpi/ic_launcher.png");
		new Lwjgl3Application(new Creature(), config);
	}
}
