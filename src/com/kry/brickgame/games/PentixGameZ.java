package com.kry.brickgame.games;

import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.Board;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Figures;

/**
 * @author noLive
 * 
 */
public class PentixGameZ extends PentixGame {
	final int TIME_BETWEEN_ADDING_LINE = 30;
	volatile int time;
	volatile boolean isTimeToAddLine;

	/**
	 * The Tetris with the Pentominoes figures, the addition of new lines every
	 * few seconds, the changing of the figures instead of rotating and the
	 * shifting board content
	 */
	public PentixGameZ(int speed, int level, int type) {
		super(speed, level, type);
		isTimeToAddLine = false;
		time = TIME_BETWEEN_ADDING_LINE;
	}

	/*
	 * addition of new lines every few seconds
	 */

	@Override
	public void start() {

		// create timer for addition of lines
		Timer addLineTimer = new Timer("AddLineTicTac", true);
		addLineTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					fireInfoChanged("-" + String.format("%02d", time) + "-");
					if (time == 0) {
						isTimeToAddLine = true;
					} else {
						time--;
					}
				}
			}
		}, 0, 1000);

		super.start();

		addLineTimer.cancel();
	}

	@Override
	protected void doRepetitiveWork() {
		// if it's time to add a line, trying to add a line
		if ((isTimeToAddLine) && (tryAddLine())) {
			time = TIME_BETWEEN_ADDING_LINE;
			isTimeToAddLine = false;
		} else {
			super.doRepetitiveWork();
		}
	}

	protected boolean tryAddLine() {
		if ((!checkBoardCollisionVertical(curPiece, curY + 1, true))
				&& (addLines())) {
			// the current y-coordinate lifts by one cell upward
			curY++;
			return true;
		} else
			return false;
	}

	/*
	 * shifting board content
	 */

	@Override
	protected void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver) {
			Board board = getBoard().clone();
			board = horizontalShift(board, 1);
			setBoard(board);
		}
	}

	/*
	 * changing of the figures instead of rotating
	 */

	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			ExitToMainMenu();
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			pause();
			return;
		}

		if ((getStatus() == Status.Running) && (!isFallingFinished)) {
			if (keys.contains(KeyPressed.KeyLeft)) {
				tryMove(curPiece, curX - 1, curY);
				sleep(ANIMATION_DELAY * 3);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				tryMove(curPiece, curX + 1, curY);
				sleep(ANIMATION_DELAY * 3);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				// if we have the super gun
				if (curPiece.getShape() == Figures.SuperGun) {
					// than shoot of it
					shoot(curX, curY + curPiece.minY());
				} else if (curPiece.getShape() == Figures.SuperMudGun) {
					mudShoot(curX, curY + curPiece.minY());
					// if the super point, than do nothing
				} else if (curPiece.getShape() != Figures.SuperPoint) {
					TetrisShape rotatedPiece = TetrisShape.getNextShape(
							curPiece, false);
					tryMove(rotatedPiece, curX, curY);
				}
				keys.remove(KeyPressed.KeyRotate);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				oneLineDown();
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyUp)) {
				dropDown();
				keys.remove(KeyPressed.KeyUp);
			}
		}
	}

}
