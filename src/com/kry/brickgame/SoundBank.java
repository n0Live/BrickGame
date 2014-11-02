package com.kry.brickgame;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

public class SoundBank {

	private Map<String, AudioClip> clips;

	public SoundBank() {
		clips = new HashMap<>();
	}

	public SoundBank(String[] files) {
		this();
		loadSounds(files);
	}

	public void loadSounds(String[] files) {
		for (int i = 0; i < files.length; i++) {
			loadSound(files[i]);
		}
	}

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

	public void stopAll() {
		for (AudioClip clip : clips.values()) {
			if (clip != null && clip.isPlaying())
				clip.stop();
		}
	}

	public void stopExceptLooping() {
		for (AudioClip clip : clips.values()) {
			if (clip != null && clip.isPlaying()
					&& clip.getCycleCount() != AudioClip.INDEFINITE)
				clip.stop();
		}
	}

	public AudioClip getClip(String file) {
		if (!clips.containsKey(file)) {
			loadSound(file);
		}
		return clips.get(file);
	}

}
