package com.kry.brickgame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

/**
 * 
 * @author noLive
 * 
 */
public class SoundBank implements Iterable<AudioClip> {

	private Map<String, AudioClip> clips;

	/**
	 * Create an empty SoundBank.
	 */
	public SoundBank() {
		clips = new HashMap<>();
	}

	/**
	 * Creates an SoundBank and loads {@code files} to it.
	 * 
	 * @param files
	 *            array of file names to load
	 */
	public SoundBank(String[] files) {
		this();
		loadSounds(files);
	}

	/**
	 * Loads {@code files} to the {@code SoundBank}.
	 * 
	 * @param files
	 *            array of file names to load
	 */
	public void loadSounds(String[] files) {
		for (int i = 0; i < files.length; i++) {
			loadSound(files[i]);
		}
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
			} catch (NullPointerException | IllegalArgumentException
					| MediaException e) {
				e.printStackTrace();
				clip = null;
			}
			clips.put(file, clip);
		}
	}

	/**
	 * Stops playing for all sounds in the {@code SoundBank}.
	 */
	public void stopAll() {
		for (AudioClip clip : clips.values()) {
			if (clip != null && clip.isPlaying())
				clip.stop();			
		}
	}

	/**
	 * Gets the {@code AudioClip}, depending of the specified file name.
	 * 
	 * @param file
	 *            file name of sound file
	 * @return {@code AudioClip}
	 */
	public AudioClip getClip(String file) {
		if (!clips.containsKey(file)) {
			loadSound(file);
		}
		return clips.get(file);
	}
	
	@Override
	public Iterator<AudioClip> iterator() {
		return clips.values().iterator();
	}

}
