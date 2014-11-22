package com.kry.brickgame;

import com.kry.brickgame.games.GameConsts.KeyPressed;

/**
 * @author noLive
 *
 */
public class CommandAdapter {

	public static void keyPressed(KeyPressed key) {
		Main.getGame().keyPressed(key);
	}

	public static void keyReleased(KeyPressed key) {
		Main.getGame().keyReleased(key);
	}

}
