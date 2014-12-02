package com.kry.brickgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameSelector;
import com.kry.brickgame.games.SplashScreen;

public class GameLoader {
	private final static String SAVED_GAME_FILE = "lastgame.sav";
	
	/**
	 * Delete a file of the saved game.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public static boolean deleteSavedGame() {
		File savedGameFile = new File(SAVED_GAME_FILE);
		if (savedGameFile.exists()) {
			if (savedGameFile.canWrite())
				return savedGameFile.delete();
			else
				return false;
		} else
			return true;
	}
	
	/**
	 * Load the saved game from a file.
	 * 
	 * @return {@code Game} if success; {@code null} otherwise
	 */
	public static Game loadGame() {
		File savedGameFile = new File(SAVED_GAME_FILE);
		if (savedGameFile.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(savedGameFile))) {
				return (Game) in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Save the game to a file.
	 * 
	 * @param game
	 *            the game for saving
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public static <T extends Game> boolean saveGame(T game) {
		if (game instanceof GameSelector || game instanceof SplashScreen) return false;
		
		File savedGameFile = new File(SAVED_GAME_FILE);
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savedGameFile))) {
			out.writeObject(game);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
