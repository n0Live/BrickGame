package com.kry.brickgame.sound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class EngineSound {
	private final int duration = 1; // seconds
	private final int sampleRate = 8000;
	private final int numSamples = duration * sampleRate;
	private final double sample[] = new double[numSamples];
	private final double freqOfTone = 380; // hz

	private final byte generatedSnd[] = new byte[2 * numSamples];

	private final ExecutorService mSoundThread;
	private final AudioFormat mAudioFormat;
	private volatile SourceDataLine mSourceDataLine = null;
	volatile boolean stopFlag;

	/**
	 * Creates a class for playing a sound of race car's engine
	 */
	public EngineSound() {
		mAudioFormat = new AudioFormat(sampleRate, 16, 1, true, true);
		mSoundThread = Executors.newSingleThreadExecutor();
		genTone();
	}

	/**
	 * Generates a tone of engine 
	 */
	private void genTone() {
		// fill out the array
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : sample) {
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767)); //32767
			// in 16 bit wav PCM, first byte is the low order byte
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
	}

	/**
	 * Plays the {@code EngineSound} in a endless loop until {@link #stop()} called. 
	 * <br><br>
	 * See also {@link #stop()}
	 */
	public void play() {
		stopFlag = false;
		
		//execute new thread for play sound
		mSoundThread.execute(new Runnable() {
			@Override
			public void run() {
				playSound();
			}
		});
	}

	/**
	 * Starts playing a generated tone in a loop
	 * <br><br>
	 * See also {@link #genTone()}
	 */
	void playSound() {
		try (SourceDataLine line = getSourceDataLine()) {
			if (line != null) {
				line.open(mAudioFormat, generatedSnd.length);
				
				//decrease volume 
                FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
                //set to about -15 dB
                float newValue = control.getMaximum() - ((control.getMaximum() - control.getMinimum())/4);
                control.setValue(newValue);
                
				line.start();
				//loop playing
				while (!stopFlag && !Thread.currentThread().isInterrupted()) {
					line.write(generatedSnd, 0, generatedSnd.length);
				}
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a single instance of a {@link SourceDataLine}.
	 * @return instance of the {@link SettingsManager} or {@code null} if {@link AudioFormat} isn't supported.
	 */
	private synchronized SourceDataLine getSourceDataLine() {
		if (mSourceDataLine == null) {
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, mAudioFormat);
			//check format supporting
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
	 * Stops playing the {@code EngineSound}.
	 * <br><br>
	 * See also {@link #play()}
	 */
	public void stop() {
		stopFlag = true;
		
		try (SourceDataLine line = getSourceDataLine()) {
			if (line != null && line.isRunning()) {
				line.stop();
				line.flush();
			}
		}
	}

}
