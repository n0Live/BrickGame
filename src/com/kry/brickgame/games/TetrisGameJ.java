package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkBoardCollisionVertical;

import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 */
public class TetrisGameJ extends TetrisGameI {
	private static final long serialVersionUID = 4703415063910078444L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.TetrisSplashJ";
	
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
	public Game call() {
		// create timer for addition of lines
		ScheduledFuture<?> addLineTimer = scheduledExecutors.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if (!Thread.currentThread().isInterrupted() && getStatus() == Status.Running) {
					fireInfoChanged("-" + String.format(Locale.ENGLISH, "%02d", getTime()) + "-");
					if (getTime() == 0) {
						isTimeToAddLine = true;
					} else {
						setTime(getTime() - 1);
					}
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		
		nextGame = super.call();
		
		addLineTimer.cancel(true);
		
		return nextGame;
	}
	
	@Override
	protected void doRepetitiveWork() {
		// if it's time to add a line, trying to add a line
		if (isTimeToAddLine && tryAddLine()) {
			setTime(TIME_BETWEEN_ADDING_LINE);
			isTimeToAddLine = false;
		} else {
			super.doRepetitiveWork();
		}
	}
	
	int getTime() {
		return time;
	}
	
	void setTime(int time) {
		this.time = time;
	}
	
	private boolean tryAddLine() {
		if (!checkBoardCollisionVertical(getBoard(), curPiece, curY + 1, true) && addLines()) {
			// the current y-coordinate lifts by one cell upward
			curY++;
			return true;
		}
		return false;
	}
	
}
