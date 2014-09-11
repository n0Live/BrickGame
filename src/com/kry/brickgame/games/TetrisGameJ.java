package com.kry.brickgame.games;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author noLive
 * 
 */
public class TetrisGameJ extends TetrisGame {

	public TetrisGameJ(int speed, int level, int type) {
		super(speed, level, type);
	}

	@Override
	public void start() {

		// create timer for addition of lines
		Timer addLineTimer = new Timer("AddLineTicTac", true);
		addLineTimer.schedule(new TimerTask() {
			final int TIME_BETWEEN_ADDING_LINE = 5;
			int time = TIME_BETWEEN_ADDING_LINE;

			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					setStatus(Status.DoSomeWork);
					fireInfoChanged("-" + String.valueOf(time) + "-");
					if (time == 0) {
						addLines();
						time = TIME_BETWEEN_ADDING_LINE;
						sleep(ANIMATION_DELAY);
					} else {
						time--;
					}
					setStatus(Status.Running);
				}
			}
		}, 0, 1000);

		super.start();

		addLineTimer.cancel();
	}

	@Override
	protected void addLines() {
		super.addLines();
		// the current y-coordinate lifts by one cell upward
		if (curY < boardHeight)
			curY++;
	}

}
