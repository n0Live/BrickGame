package com.kry.brickgame.IO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noLive
 */
public final class ScoresManager {
	/**
	 * Name of file containing the high scores
	 */
	private final static String HI_SCORE_FILE = "hiscores.dat";
	/**
	 * Single instance of the {@code ScoresManager}
	 */
	private static ScoresManager instance;
	
	/**
	 * Comparison of the game class and high scores
	 */
	private Map<String, Integer> hiScores;
	
	/**
	 * Delete a high scores file.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	private static boolean deleteScores() {
		return IOUtils.deleteFile(HI_SCORE_FILE);
	}
	
	/**
	 * Get instance of the {@code ScoresManager}
	 * 
	 * @return instance of the {@code ScoresManager}
	 */
	public static synchronized ScoresManager getScoresManager() {
		if (null == instance) {
			instance = new ScoresManager();
		}
		return instance;
	}
	
	/**
	 * Singleton class, which manages the high scores
	 */
	private ScoresManager() {
		if (!loadScores()) {
			hiScores = new HashMap<>();
		}
	}
	
	/**
	 * Get stored high score of the specified game
	 * 
	 * @param className
	 *            name of class of the game
	 * @return high score
	 */
	public int getHiScore(String className) {
		if (hiScores.containsKey(className)) return hiScores.get(className);
		return 0;
	}
	
	/**
	 * Read the high scores from the file (deserialization)
	 * 
	 * @return {@code true} if loading is success
	 */
	@SuppressWarnings("unchecked")
	private boolean loadScores() {
		try (InputStream is = IOUtils.getInputStream(HI_SCORE_FILE);
				ObjectInputStream ois = new ObjectInputStream(is)) {
			hiScores = (HashMap<String, Integer>) ois.readObject();
			return true;
		} catch (ReflectiveOperationException | ObjectStreamException | ClassCastException e) {
			System.err.println("The hiscores file was corrupted and will be removed:\n" + e);
			// delete corrupted file
			deleteScores();
			return false;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Write the high scores to the file (serialization)
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	private boolean saveScores() {
		try (OutputStream os = IOUtils.getOutputStream(HI_SCORE_FILE);
				ObjectOutputStream oos = new ObjectOutputStream(os)) {
			oos.writeObject(hiScores);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Set the high score of the specified game when it more then previously
	 * stored value.
	 * <p>
	 * Each call of that method causes writing high scores to the file
	 * (serialization)
	 * 
	 * @param className
	 *            name of class of the game
	 * @param score
	 *            new score
	 * @return new score if it more then previously high score, otherwise -
	 *         return previously high score
	 */
	public int setHiScore(String className, int score) {
		int prevScore = getHiScore(className);
		if (prevScore < score) {
			hiScores.put(className, score);
			saveScores();
			return score;
		}
		return prevScore;
	}
	
}
