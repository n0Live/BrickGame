package com.kry.brickgame;

import java.net.URL;
import java.util.EnumSet;

import javafx.scene.media.AudioClip;

public class SoundManager {

	public enum Sounds {
		welcome, select, start, win, game_over, move, turn, hit_cell, add_cell, kaboom, bonus, fall, fall_super, remove_line;
	}

	public enum Backgrounds {
		race, tetris;
	}

	public enum Melodies {
		melody1, melody2, melody3, melody4, melody5, melody6, melody7, melody8, melody9;
	}

	private final static String soundFolder = "/sounds/";
	private final static String soundExtension = ".mp3";

	// Дожидается окончания проигрывания звука
	public void join() {
	}

	public static <E extends Enum<E>> String[] enumToResourceArray(
			Class<E> enumClass) {
		EnumSet<E> values = EnumSet.allOf(enumClass);
		int i = 0;
		String[] result = new String[values.size()];

		for (E value : values) {
			result[i++] = getResourceFromName(value.toString());
		}
		return result;
	}

	public static <E extends Enum<E>> SoundBank loadSounds(SoundBank soundBank,
			Class<E> soundsEnumClass) {

		soundBank.loadSounds(enumToResourceArray(soundsEnumClass));
		return soundBank;
	}

	public static String getResourceFromName(String soundName) {
		URL resource = SoundManager.class.getResource(soundFolder + soundName
				+ soundExtension);
		return resource.toExternalForm();
	}

	public static void stopAll(SoundBank soundBank, boolean exceptLoop) {
		if (exceptLoop)
			soundBank.stopExceptLooping();
		else
			soundBank.stopAll();
	}

	// Статический метод, для удобства
	public static <E extends Enum<E>> void playSound(SoundBank soundBank,
			Enum<E> value, boolean waitForStart, boolean waitForEnd) {

		final AudioClip clip = soundBank.getClip(getResourceFromName(value
				.toString()));
		clip.play();
		if (waitForStart) {
			while (!clip.isPlaying()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					// do nothing
				}
			}
		}
		if (waitForEnd) {
			while (clip.isPlaying()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					// do nothing
				}
			}
		}
	}

	public static <E extends Enum<E>> void loopSound(SoundBank soundBank,
			Enum<E> value) {

		final AudioClip clip = soundBank.getClip(getResourceFromName(value
				.toString()));
		clip.setCycleCount(AudioClip.INDEFINITE);
		clip.play();
	}

}
