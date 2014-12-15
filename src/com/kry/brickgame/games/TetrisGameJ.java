package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkBoardCollisionVertical;

import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 */
public class TetrisGameJ extends TetrisGameI {
	private static final long serialVersionUID = 4703415063910078444L;

	private static final int TIME_BETWEEN_ADDING_LINE = 30;
	private volatile int time;
	volatile boolean isTimeToAddLine;

	/**
	 * The Tetris with the addition of new lines every few seconds
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameJ(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
		isTimeToAddLine = false;
		time = TIME_BETWEEN_ADDING_LINE;
	}

	@Override
	protected void doRepetitiveWork() {
		// if it's time to add a line, trying to add a line
		if ((isTimeToAddLine) && (tryAddLine())) {
			setTime(TIME_BETWEEN_ADDING_LINE);
			isTimeToAddLine = false;
		} else {
			super.doRepetitiveWork();
		}
	}

	int getTime() {
		return time;
	}

	@Override
	public void run() {
		// create timer for addition of lines
		Timer addLineTimer = new Timer("AddLineTicTac", true);
		addLineTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					fireInfoChanged("-" + String.format("%02d", getTime())
							+ "-");
					if (getTime() == 0) {
						isTimeToAddLine = true;
					} else {
						setTime(getTime() - 1);
					}
				}
			}
		}, 0, 1000);

		super.run();

		addLineTimer.cancel();
	}

	void setTime(int time) {
		this.time = time;
	}

	private boolean tryAddLine() {
		if ((!checkBoardCollisionVertical(getBoard(), curPiece, curY + 1, true))
				&& (addLines())) {
			// the current y-coordinate lifts by one cell upward
			curY++;
			return true;
		}
		return false;
	}

}
