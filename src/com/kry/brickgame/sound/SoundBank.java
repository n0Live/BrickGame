package com.kry.brickgame.sound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

/**
 * @author noLive
 */
public class SoundBank implements Iterable<AudioClip> {
	
	private final Map<String, AudioClip> clips;
	
	/**
	 * Creates an SoundBank and loads {@code files} to it.
	 * 
	 * @param files
	 *            array of file names to load
	 */
	public SoundBank(String[] files) {
		clips = new HashMap<>(files.length, 1f);
		loadSounds(files);
	}
	
	/**
	 * Gets the {@code AudioClip}, depending of the specified file name.
	 * 
	 * @param file
	 *            file name of sound file
	 * @return {@code AudioClip}
	 */
	public AudioClip getClip(String file) {
		return clips.get(file);
	}
	
	/**
	 * Returns {@code true} if this {@code SoundBank} contains no clips.
	 */
	public boolean isEmpty() {
		return clips.isEmpty();
	}
	
	@Override
	public Iterator<AudioClip> iterator() {
		return clips.values().iterator();
	}
	
	/**
	 * Loads {@code file} to the {@code SoundBank}.
	 * 
	 * @param file
	 *            file name to load
	 */
	public void loadSound(String file) {
		if (!clips.containsKey(file)) {
			AudioClip clip;
			try {
				clip = new AudioClip(file);
			} catch (NullPointerException | IllegalArgumentException | MediaException e) {
				e.printStackTrace();
				clip = null;
			}
			clips.put(file, clip);
		}
	}
	
	/**
	 * Loads {@code files} to the {@code SoundBank}.
	 * 
	 * @param files
	 *            array of file names to load
	 */
	public void loadSounds(String[] files) {
		for (String file : files) {
			loadSound(file);
		}
	}
	
}
