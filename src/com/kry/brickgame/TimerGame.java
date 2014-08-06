package com.kry.brickgame;

import java.util.Timer;
import java.util.TimerTask;

public class TimerGame {

	private Timer timer = null;
	private long period = 0;

	public TimerGame() {
	}

	public TimerGame(long period) {
		this.period = period;
	}

	protected Timer getTimer() {
		return timer;
	}

	protected long getPeriod() {
		return period;
	}

	protected void setPeriod(long period) {
		this.period = period;
	}

	protected void start(TimerTask task) {
		if (period != 0)
			start(task, period);
	}

	/**
	 * Starts the specified task to
	 * {@link java.util.Timer#schedule(TimerTask, long, long) Timer.schedule}
	 * with delay = 0.
	 * 
	 * @param task
	 *            task to be scheduled. Recommended only a new instance of
	 *            {@code TimerTask}, to avoid {@code IllegalStateException}.
	 * @param period
	 *            time in milliseconds between successive task executions.
	 * 
	 */
	protected void start(TimerTask task, long period) {
		timer = new Timer("TTimerThread");
		try {
			timer.schedule(task, 0, period);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Terminates this timer, discarding any currently scheduled tasks. Does not
	 * interfere with a currently executing task (if it exists).
	 * 
	 * @see java.util.Timer#cancel()
	 */
	protected void stop() {
		timer.cancel();
	}

}
