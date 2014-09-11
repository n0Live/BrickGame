package com.kry.brickgame.games;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGameJ extends TetrisGame {

	public TetrisGameJ(int speed, int level, int type) {
		super(speed, level, type);
	}

	@Override
	public void start() {

		// create timer for bullets
		Timer addLineTimer = new Timer("AddLineTicTac", true);
		addLineTimer.schedule(new TimerTask() {
			int timeToAddLine = 30;

			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					fireInfoChanged("-" + String.valueOf(timeToAddLine) + "-");
					if (timeToAddLine == 0) {
						setStatus(Status.DoSomeWork);
						addLines();
						timeToAddLine = 30;
						setStatus(Status.Running);
					} else {
						timeToAddLine--;
					}
				}
			}
		}, 0, 1000);

		super.start();

		addLineTimer.cancel();

	}

}
