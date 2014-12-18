package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.DOWN;
import static com.kry.brickgame.games.GameConsts.LEFT;
import static com.kry.brickgame.games.GameConsts.RIGHT;
import static com.kry.brickgame.games.GameConsts.UP;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.melodies;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.playMelody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.games.GameUtils.Melodies;
import com.kry.brickgame.shapes.DancerShape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.sound.SoundManager;
import com.kry.brickgame.splashes.DanceSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 */
public class DanceGame extends Game {
	/**
	 * Class describes a dance position
	 */
	private static class DancePosition implements Serializable {
		private static final long serialVersionUID = 8936725072827465123L;
		/**
		 * Positions of the {@code DancerShape} on y-axis
		 */
		public int y;
		/**
		 * Has taken the dance position?
		 */
		private boolean caught;
		/**
		 * {@code DancerShape} leftward
		 */
		public final DancerShape leftShape;
		/**
		 * {@code DancerShape} upward
		 */
		public final DancerShape upShape;
		/**
		 * {@code DancerShape} downward
		 */
		public final DancerShape downShape;
		/**
		 * {@code DancerShape} rightward
		 */
		public final DancerShape rightShape;

		/**
		 * Dance position
		 * 
		 * @param y
		 *            positions on y-axis
		 * @param left
		 *            whether is a {@code DancerShape} leftward?
		 * @param up
		 *            whether is a {@code DancerShape} upward?
		 * @param down
		 *            whether is a {@code DancerShape} downward?
		 * @param right
		 *            whether is a {@code DancerShape} rightward?
		 */
		public DancePosition(int y, boolean left, boolean up, boolean down,
				boolean right) {
			caught = false;

			this.y = y;
			leftShape = (left) ? new DancerShape(LEFT) : null;
			upShape = (up) ? new DancerShape(UP) : null;
			downShape = (down) ? new DancerShape(DOWN) : null;
			rightShape = (right) ? new DancerShape(RIGHT) : null;
		}

		/**
		 * Has taken the dance position?
		 * 
		 * @return {@code true} if the dance position was caught
		 */
		protected boolean isCaught() {
			return caught;
		}

		/**
		 * Caught the dance position.
		 * <p>
		 * Caught position starts blinking
		 * 
		 * @param caught
		 *            has taken the dance position?
		 */
		protected void setCaught(boolean caught) {
			this.caught = caught;
			if (caught) {
				if (leftShape != null) {
					leftShape.setFill(Cell.Blink);
				}
				if (upShape != null) {
					upShape.setFill(Cell.Blink);
				}
				if (downShape != null) {
					downShape.setFill(Cell.Blink);
				}
				if (rightShape != null) {
					rightShape.setFill(Cell.Blink);
				}
			}
		}
	}

	private static final long serialVersionUID = 1329642134274377275L;
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new DanceSplash();

	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 2;
	/**
	 * Positions of the {@code DancerShape}s on x-axis
	 */
	final private static int[] columns = new int[] { 0, 3, 6, 9 };
	/**
	 * Comparison of the keys and directions of the {@code DancerShape}s
	 */
	final private static Map<RotationAngle, KeyPressed> keysToRotate;

	static {
		keysToRotate = new HashMap<>();
		keysToRotate.put(LEFT, KeyPressed.KeyLeft);
		keysToRotate.put(RIGHT, KeyPressed.KeyRight);
		keysToRotate.put(UP, KeyPressed.KeyUp);
		keysToRotate.put(DOWN, KeyPressed.KeyDown);
	}

	/**
	 * Dance positions
	 */
	private final DancePosition[] positions;

	/**
	 * Count of bonus points
	 */
	private int bonus;

	/**
	 * Count of positions passed
	 */
	private int stepsGone;
	/**
	 * Number of the current melody
	 */
	private int melodyNumber;
	/**
	 * Playback rate of the current melody
	 */
	private double rate;

	/**
	 * The Dance game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game:
	 *            <ol>
	 *            <li>dance with normal board;
	 *            <li>dance with inverted board.
	 */
	public DanceGame(int speed, int level, int type) {
		super(speed, level, type);

		positions = new DancePosition[(boardHeight / DancerShape.height)];
		bonus = -1; // will change to 0 at first caught
		stepsGone = 0;
		// set random first melody
		melodyNumber = new Random().nextInt(Melodies.values().length);
		rate = calculateRate();

		// for type 1 - draw inverted board
		setDrawInvertedBoard(type == 1);

		move();
		setStatus(Status.Running);
	}

	/**
	 * Get the current playback rate
	 * 
	 * @return playback rate
	 */
	private double calculateRate() {
		return (1 + (double) getSpeed() / 10);
	}

	/**
	 * Checking the coincidence of the {@code DancerShape} direction and
	 * pressing keys
	 * 
	 * @param dancer
	 *            checking {@code DancerShape}
	 * @return {@code true} if is the coincidence
	 */
	private boolean checkDanceStep(DancerShape dancer) {
		if (dancer == null)
			return true;

		return containsKey(keysToRotate.get(dancer.getRotationAngle()));
	}

	/**
	 * Create a new dance position
	 * 
	 * @return a new dance position
	 */
	private DancePosition createPosition() {
		// interval between positions from 3 to 1
		int interval = (3 - getSpeed() / 3);
		if (interval <= 0) {
			interval = 1;
		}
		interval += 1 + DancerShape.maxY - DancerShape.minY;

		int upperBorder = boardHeight - getLevel();

		byte pos;
		// 1 chance from 20 to create a triple position
		if (r.nextInt(20) == 0) {
			switch (r.nextInt(4)) {
			case 0:
				pos = 7;// 0111 in binary system
				break;
			case 1:
				pos = 11;// 1011
				break;
			case 2:
				pos = 13;// 1101
				break;
			default:
				pos = 14;// 1110
				break;
			}
		} else {
			do {
				// get random double position
				pos = (byte) (r.nextInt(12) + 1);
			} while (pos == 7 || pos == 11);
		}

		// calculate y-coordinates of the previous position
		int prevY = upperBorder - interval - DancerShape.height;
		for (DancePosition position : positions) {
			if (position != null) {
				prevY = Math.max(prevY, position.y);
			}
		}
		int newY = prevY + interval;

		return new DancePosition(newY,//
				(pos & 8) == 8,// 1000
				(pos & 4) == 4,// 0100
				(pos & 2) == 2,// 0010
				(pos & 1) == 1);// 0001
	}

	/**
	 * Drawing the dance positions on the board
	 */
	private void draw() {
		// create new board of suitable size
		Board drawBoard = new Board(boardWidth, boardHeight - getLevel() + 1);
		// draw positions
		for (DancePosition position : positions) {
			if (position != null) {
				int y = position.y;
				if (position.leftShape != null) {
					drawBoard = drawShape(drawBoard, columns[0], y,
							position.leftShape, position.leftShape.getFill());
				}
				if (position.upShape != null) {
					drawBoard = drawShape(drawBoard, columns[1], y,
							position.upShape, position.upShape.getFill());
				}
				if (position.downShape != null) {
					drawBoard = drawShape(drawBoard, columns[2], y,
							position.downShape, position.downShape.getFill());
				}
				if (position.rightShape != null) {
					drawBoard = drawShape(drawBoard, columns[3], y,
							position.rightShape, position.rightShape.getFill());
				}
			}
		}
		// create a board of full size
		Board mainBoard = new Board(boardWidth, boardHeight);
		// insert drawBoard to mainBoard
		mainBoard = insertCellsToBoard(mainBoard, drawBoard.getBoard(), 0, 0);
		setBoard(mainBoard);
	}

	/**
	 * Get the current melody
	 * 
	 * @return current melody
	 */
	private Melodies getMelody() {
		if (melodyNumber >= Melodies.values().length) {
			melodyNumber = 0;
		}
		return Melodies.values()[melodyNumber];
	}

	/**
	 * Get the currently required dance position (position at the lower edge of
	 * the board)
	 * 
	 * @return a dance position or {@code null} if no suitable position
	 */
	private DancePosition getPosition() {
		for (DancePosition position : positions) {
			// if that position at the lower edge of the board
			if (position != null && position.y <= DancerShape.height
					&& position.y >= 0) {
				// if that position is already caught - return null
				if (position.isCaught())
					return null;
				return position;
			}
		}
		return null;
	}

	@Override
	protected int getSpeedOfFirstLevel() {
		return 300;
	}

	@Override
	protected int getSpeedOfTenthLevel() {
		return 80;
	}

	/**
	 * Increase the scores and play sound
	 * 
	 * @param score
	 *            points for adding
	 */
	private void increaseScores(int score) {
		playEffect(Effects.turn);

		setScore(getScore() + score + bonus);

		draw();
	}

	/**
	 * Moving of the dance positions
	 */
	private void move() {
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] == null) {
				positions[i] = createPosition();
			} else if (positions[i].y + DancerShape.maxY < 0) {
				if (isMuted()) {
					stepsGone++;
				}
				// if position is gone and no catch, then bonus resets
				if (!positions[i].isCaught()) {
					bonus = -1;
				}
				positions[i] = createPosition();
			} else {
				positions[i].y--;
			}
			draw();
		}
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None)
			return;

		super.processKeys();

		if (getStatus() == Status.Running) {
			DancePosition position = getPosition();
			if (position != null) {
				// checking the coincidence of the all DancerShape in the
				// position and pressing keys
				if (!checkDanceStep(position.leftShape)
						|| !checkDanceStep(position.upShape)
						|| !checkDanceStep(position.downShape)
						|| !checkDanceStep(position.rightShape))
					return;
				int score = 0;
				position.setCaught(true);

				// add points for every caught DancerShape
				if (position.leftShape != null) {
					score++;
				}
				if (position.upShape != null) {
					score++;
				}
				if (position.downShape != null) {
					score++;
				}
				if (position.rightShape != null) {
					score++;
				}
				// increase bonus
				if (++bonus > 4) {
					bonus = 4;
				}
				increaseScores(score);
			}
		}
	}

	@Override
	public void resume() {
		if (getStatus() == Status.Paused) {
			playMelody(getMelody(), rate);
		}
		super.resume();
	}

	/**
	 * Launching the game
	 */
	@Override
	public void run() {
		super.run();
		if (getStatus() == Status.Running) {
			// play the first melody
			playMelody(getMelody(), rate);
		}
		while (!Thread.currentThread().isInterrupted()
				&& (getStatus() != Status.GameOver)) {
			if ((getStatus() != Status.Paused) && (elapsedTime(getSpeed(true)))) {

				// change speed and melody after finished playing the melody
				if (!isMuted() && !SoundManager.isPlaying(melodies)) {
					setSpeed(getSpeed() + 1);
					if (getSpeed() == 1) {
						melodyNumber++;
					}
					rate = calculateRate();
					playMelody(getMelody(), rate);
					// if muted, then speed changes every 10 + getSpeed()
					// positions
				} else if (stepsGone >= 10 + getSpeed()) {
					stepsGone = 0;
					setSpeed(getSpeed() + 1);
					// after unmuted will be a new melody
					if (getSpeed() == 1) {
						melodyNumber++;
					}
				}
				// move positions
				move();
			}
			// processing of key presses
			processKeys();
		}
	}

	@Override
	protected void setSpeed(int speed) {
		super.setSpeed(speed);

		if (getSpeed() == 1) {
			setLevel(getLevel() + 1);
		}
	}

}
