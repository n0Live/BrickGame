package com.kry.brickgame.sound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class EngineSound {
	private final double freqOfTone = 370; // hz

	private final float duration = 0.1f; // seconds
	private final float sampleRate = 8000;
	private final int numSamples = (int) (duration * sampleRate);
	private final double sample[] = new double[numSamples];
	private final int frameSize = 2; // bytes per frame
	private final float frameRate = sampleRate / frameSize;

	private final int tonesCount = 11;
	private final byte generatedSnd[][] = new byte[tonesCount][frameSize * numSamples];

	private final ExecutorService mSoundThread;
	private final AudioFormat mAudioFormat;
	private volatile SourceDataLine mSourceDataLine = null;
	private final AtomicBoolean isPlaying;

	private int currentTone = -1;

	/**
	 * Creates a class for playing a sound of race car's engine
	 */
	public EngineSound() {
		mAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				sampleRate, 16, 1, frameSize, frameRate, true);
		mSoundThread = Executors.newSingleThreadExecutor();
		isPlaying = new AtomicBoolean(false);
		genTone();
	}

	/**
	 * Generates a tone of engine
	 */
	private void genTone() {
		// fill out the array
		for (int i = 0; i < tonesCount; i++) {
			int idx = 0;
			double currFreqOfTone;
			if (i == 0) {
				currFreqOfTone = freqOfTone + 35;
			} else {
				currFreqOfTone = freqOfTone + (i + i * 2);
			}
			for (int j = 0; j < numSamples; ++j) {
				sample[j] = Math.sin(2 * Math.PI * j
						/ (sampleRate / (currFreqOfTone)));
				// convert to 16 bit pcm sound array
				// assumes the sample buffer is normalised.
				// scale from maximum amplitude 32767
				final short val = (short) ((sample[j] * 8192));
				// in 16 bit wav PCM, first byte is the low order byte
				generatedSnd[i][idx++] = (byte) (val & 0x00ff);
				generatedSnd[i][idx++] = (byte) ((val & 0xff00) >>> 8);
			}
		}
	}

	/**
	 * Plays the {@code EngineSound} in a endless loop until {@link #stop()}
	 * called. <br>
	 * <br>
	 * See also {@link #stop()}
	 * 
	 * @param tone
	 *            sound tone (number from 0 to 10)
	 */
	public void play(final int tone) {
		currentTone = tone;
		if (!isPlaying.get()) {
			// execute new thread for play sound
			mSoundThread.execute(new Runnable() {
				@Override
				public void run() {
					playSound();
				}
			});
		}
	}

	/**
	 * Starts playing a generated tone in a loop <br>
	 * <br>
	 * See also {@link #genTone()}
	 */
	void playSound() {
		try (SourceDataLine line = getSourceDataLine()) {
			if (line != null) {
				isPlaying.set(true);
				
				line.open(mAudioFormat, generatedSnd[currentTone].length);

				// decrease volume
				FloatControl control = (FloatControl) line
						.getControl(FloatControl.Type.MASTER_GAIN);
				// set to 3/5 of maximum dB
				float newValue = control.getMaximum()
						- (2 *(control.getMaximum() - control.getMinimum()) / 5);
				control.setValue(newValue);

				line.start();

				// loop playing
				while (isPlaying.get() && !Thread.currentThread().isInterrupted()) {
					line.write(generatedSnd[currentTone], 0,
							generatedSnd[currentTone].length);
				}
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a single instance of a {@link SourceDataLine}.
	 * 
	 * @return instance of the {@link SettingsManager} or {@code null} if
	 *         {@link AudioFormat} isn't supported.
	 */
	private synchronized SourceDataLine getSourceDataLine() {
		if (mSourceDataLine == null) {
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, mAudioFormat);
			// check format supporting
			if (!AudioSystem.isLineSupported(dataLineInfo)) {
				System.err.println("Line matching: '" + dataLineInfo
						+ "' isn't supported.");
				mSourceDataLine = null;
			} else {
				try {
					mSourceDataLine = (SourceDataLine) AudioSystem
							.getLine(dataLineInfo);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					mSourceDataLine = null;
				}
			}

		}
		return mSourceDataLine;
	}

	/**
	 * Stops playing the {@code EngineSound}. <br>
	 * <br>
	 * See also {@link #play()}
	 */
	public void stop() {
		isPlaying.set(false);
		try (SourceDataLine line = getSourceDataLine()) {
			if (line != null && line.isRunning()) {
				line.stop();
				line.flush();
			}
		}
	}

}
