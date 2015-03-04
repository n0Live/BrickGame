package com.kry.brickgame.sound;

import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;

public final class SoundManager {
	private final static String soundFolder = "/sounds/";
	private final static String soundExtension = ".m4a";
	
	private static final int DELAY_BEFORE_PLAY_NEW_SOUND = 500;
	
	private static final Map<String, Long> playedClips = new HashMap<>();
	private static final Map<String, String> nameToResource = new HashMap<>();
	
	private static boolean canPlay(String clip, int delay) {
		long now = System.currentTimeMillis();
		if (playedClips.containsKey(clip) && now < playedClips.get(clip) + delay) return false;
		playedClips.put(clip, now);
		return true;
	}
	
	/**
	 * Gets the {@code AudioClip}, depending of the specified {@code enum}
	 * value, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 * @return {@code AudioClip}
	 */
	private static <E extends Enum<E>> AudioClip getClip(SoundBank soundBank, Enum<E> value) {
		String key = value.toString();
		String fileName = nameToResource.get(key);
		if (fileName == null) {
			fileName = getResourceFromName(key);
			nameToResource.put(key, fileName);
		}
		return soundBank.getClip(fileName);
	}
	
	/**
	 * Play the {@code AudioClip} at once in normal rate, depending of the
	 * specified {@code enum} value, from the specified {@code soundBank}. If
	 * the {@code AudioClip} is already playing, then stopped it at first.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void breakingPlay(SoundBank soundBank, Enum<E> value) {
		final AudioClip clip = getClip(soundBank, value);
		if (clip.isPlaying()) {
			clip.stop();
		}
		clip.setCycleCount(1);
		clip.play();
	}
	
	/**
	 * Gets the string array of resources from the {@code enum} values.
	 * 
	 * @param enumClass
	 *            class of the {@code enum}
	 * @return string array of resources
	 */
	public static <E extends Enum<E>> String[] enumToResourceArray(Class<E> enumClass) {
		EnumSet<E> values = EnumSet.allOf(enumClass);
		String[] result = new String[values.size()];
		
		int i = 0;
		for (E value : values) {
			result[i++] = getResourceFromName(value.toString());
		}
		return result;
	}
	
	/**
	 * Gets the resource name from {@code soundName}.
	 * <p>
	 * The {@link #soundFolder} and {@link #soundExtension} appended to the
	 * {@code soundName}.
	 * 
	 * @param soundName
	 *            name of sound
	 * @return name of resource
	 */
	public static String getResourceFromName(String soundName) {
		URL resource = SoundManager.class.getResource(soundFolder + soundName + soundExtension);
		return resource.toExternalForm();
	}
	
	/**
	 * Indicate whether any {@code AudioClip} in the specified {@code soundBank}
	 * is playing.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @return {@code true} if any {@code AudioClip} in {@code soundBank} is
	 *         playing
	 */
	public static boolean isPlaying(SoundBank soundBank) {
		for (AudioClip clip : soundBank) {
			if (clip.isPlaying()) return true;
		}
		return false;
	}
	
	/**
	 * Indicate whether the {@code AudioClip}, depending of the specified
	 * {@code enum} value, from the specified {@code soundBank}, is playing.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 * @return {@code true} if the {@code AudioClip} is playing
	 */
	public static <E extends Enum<E>> boolean isPlaying(SoundBank soundBank, Enum<E> value) {
		return getClip(soundBank, value).isPlaying();
	}
	
	/**
	 * Loads files to the SoundBank from the {@code enum} values.
	 * 
	 * @param soundBank
	 *            SoundBank for the loading
	 * @param enumClass
	 *            class of the {@code enum}
	 */
	public static <E extends Enum<E>> void loadSounds(SoundBank soundBank, Class<E> enumClass) {
		soundBank.loadSounds(enumToResourceArray(enumClass));
	}
	
	/**
	 * Play the {@code AudioClip} in a circle, depending of the specified
	 * {@code enum} value, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void loop(SoundBank soundBank, Enum<E> value) {
		// play(soundBank, value, AudioClip.INDEFINITE, 1.0,
		// Thread.MAX_PRIORITY); <== don't use for loops
		
		final AudioClip clip = getClip(soundBank, value);
		clip.setCycleCount(AudioClip.INDEFINITE);
		// Use default parameters except priority
		clip.play(clip.getVolume(), clip.getBalance(), 1.0, clip.getPan(), Thread.MAX_PRIORITY);
		
	}
	
	/**
	 * Play the {@code AudioClip} at once in normal rate, depending of the
	 * specified {@code enum} value, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void play(SoundBank soundBank, Enum<E> value) {
		play(soundBank, value, Thread.NORM_PRIORITY);
	}
	
	/**
	 * Play the {@code AudioClip} at once in specified {@code rate}, depending
	 * of the specified {@code enum} value, from the specified
	 * {@code soundBank.}
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 * @param rate
	 *            playback rate multiplier: 1.0 will play at the normal rate
	 *            while 2.0 will double the rate. Valid range is 0.125 (1/8
	 *            speed) to 8.0 (8x speed).
	 */
	public static <E extends Enum<E>> void play(SoundBank soundBank, Enum<E> value, double rate) {
		play(soundBank, value, 1, rate, Thread.MAX_PRIORITY);
	}
	
	/**
	 * Play the {@code AudioClip} at once in normal rate, depending of the
	 * specified {@code enum} value, from the specified {@code soundBank}, with
	 * specified {@code priority}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 * @param priority
	 *            the new playback priority
	 */
	public static <E extends Enum<E>> void play(SoundBank soundBank, Enum<E> value, int priority) {
		play(soundBank, value, 1, 1.0, priority);
	}
	
	/**
	 * Play the {@code AudioClip} in specified {@code cycleCount} , depending of
	 * the specified {@code enum} value, from the specified {@code soundBank},
	 * with specified {@code rate} and {@code priority}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 * @param cycleCount
	 *            new cycle count for playing the {@code AudioClip}
	 * @param rate
	 *            playback rate multiplier: 1.0 will play at the normal rate,
	 *            while 2.0 will double the rate. Valid range is 0.125 (1/8
	 *            speed) to 8.0 (8x speed).
	 * @param priority
	 *            the new playback priority
	 */
	public static <E extends Enum<E>> void play(SoundBank soundBank, Enum<E> value, int cycleCount,
	        double rate, int priority) {
		if (!canPlay(value.toString(), DELAY_BEFORE_PLAY_NEW_SOUND - priority * 10)) return;
		final AudioClip clip = getClip(soundBank, value);
		clip.setCycleCount(cycleCount);
		// Use default parameters except priority
		clip.play(clip.getVolume(), clip.getBalance(), rate, clip.getPan(), priority);
	}
	
	/**
	 * Play the {@code AudioClip}, depending of the specified {@code enum}
	 * value, from the specified {@code soundBank} and wait for its ending.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void playAndWait(SoundBank soundBank, Enum<E> value) {
		final AudioClip clip = getClip(soundBank, value);
		clip.setCycleCount(1);
		clip.play();
		while (clip.isPlaying()) {
			Thread.yield();
			// do nothing
		}
	}
	
	/**
	 * Play the {@code AudioClip}, depending of the specified {@code enum}
	 * value, from the specified {@code soundBank} with zero volume.
	 * <p>
	 * Workaround for delay in first call to an {@code AudioClip}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void prepare(SoundBank soundBank, Enum<E> value) {
		final AudioClip clip = getClip(soundBank, value);
		clip.play(0); // silent play
	}
	
	/**
	 * Stop playing the {@code AudioClip}, depending of the specified
	 * {@code enum} value, from the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 * @param value
	 *            {@code enum} value, containing the name of the sound
	 */
	public static <E extends Enum<E>> void stop(SoundBank soundBank, Enum<E> value) {
		final AudioClip clip = getClip(soundBank, value);
		if (clip.isPlaying()) {
			clip.stop();
		}
	}
	
	/**
	 * Stops playing for all sounds in the specified {@code soundBank}.
	 * 
	 * @param soundBank
	 *            specified SoundBank
	 */
	public static void stopAll(SoundBank soundBank) {
		for (AudioClip clip : soundBank) {
			if (clip.isPlaying()) {
				clip.stop();
			}
		}
	}
	
}
