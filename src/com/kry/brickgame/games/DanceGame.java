package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.playMusic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.games.GameUtils.Music;
import com.kry.brickgame.shapes.DancerShape;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.splashes.DanceSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class DanceGame extends Game {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new DanceSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 2;

	final private static int[] columns = new int[] { 0, 3, 6, 9 };

	final private static Map<RotationAngle, KeyPressed> keysToRotate;
	static {
		keysToRotate = new HashMap<>();
		keysToRotate.put(LEFT, KeyPressed.KeyLeft);
		keysToRotate.put(RIGHT, KeyPressed.KeyRight);
		keysToRotate.put(UP, KeyPressed.KeyUp);
		keysToRotate.put(DOWN, KeyPressed.KeyDown);
	}

	private class DancePosition {
		public int y;
		private boolean caught;
		public final DancerShape leftShape;
		public final DancerShape upShape;
		public final DancerShape downShape;
		public final DancerShape rightShape;

		public DancePosition(int y, boolean left, boolean up, boolean down,
				boolean right) {
			this.caught = false;

			this.y = y;
			this.leftShape = (left) ? new DancerShape(LEFT) : null;
			this.upShape = (up) ? new DancerShape(UP) : null;
			this.downShape = (down) ? new DancerShape(DOWN) : null;
			this.rightShape = (right) ? new DancerShape(RIGHT) : null;
		}

		protected boolean isCaught() {
			return caught;
		}

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

	private DancePosition[] positions;

	private int bonus;
	
	private int stepsGone;

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

		bonus = -1;
		
		stepsGone = 0;

		// for type 2 - draw inverted board
		setDrawInvertedBoard(type == 2);
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {

		int currentSpeed;

		playMusic(Music.start);

		setStatus(Status.Running);

		while (!interrupted() && (getStatus() != Status.GameOver)) {
			if (stepsGone >= 10 + getSpeed()){
				stepsGone = 0;
				if (isMuted())
					setSpeed(getSpeed() + 1);
			}
			
			currentSpeed = getSpeed(true);

			if ((getStatus() != Status.Paused) && (elapsedTime(currentSpeed))) {
				move();
			}
			// processing of key presses
			processKeys();
		}
	}

	protected void move() {
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] == null) {
				positions[i] = createPosition();
			} else if (positions[i].y + DancerShape.maxY < 0) {
				stepsGone++;
				
				if (!positions[i].isCaught())
					bonus = -1;
				
				positions[i] = createPosition();
			} else {
				positions[i].y--;
			}
			draw();
		}
	}

	protected DancePosition createPosition() {
		// from 4 to 1
		int interval = (4 - getSpeed() / 3);
		interval += 1 + DancerShape.maxY - DancerShape.minY;

		int upperBorder = boardHeight - getLevel();

		Random r = new Random();

		byte pos;
		// 1 chance from 20
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
				pos = (byte) (r.nextInt(12) + 1);
			} while (pos == 7 || pos == 11);
		}

		int prevY = upperBorder - interval;
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] != null) {
				prevY = Math.max(prevY, positions[i].y);
			}
		}
		int newY = prevY + interval;

		return new DancePosition(newY,//
				(pos & 8) == 8,// 1000
				(pos & 4) == 4,// 0100
				(pos & 2) == 2,// 0010
				(pos & 1) == 1);// 0001
	}

	protected void draw() {
		Board drawBoard = new Board(boardWidth, boardHeight - getLevel() + 1);
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] != null) {
				int y = positions[i].y;
				if (positions[i].leftShape != null)
					drawBoard = drawShape(drawBoard, columns[0], y,
							positions[i].leftShape,
							positions[i].leftShape.getFill());
				if (positions[i].upShape != null)
					drawBoard = drawShape(drawBoard, columns[1], y,
							positions[i].upShape,
							positions[i].upShape.getFill());
				if (positions[i].downShape != null)
					drawBoard = drawShape(drawBoard, columns[2], y,
							positions[i].downShape,
							positions[i].downShape.getFill());
				if (positions[i].rightShape != null)
					drawBoard = drawShape(drawBoard, columns[3], y,
							positions[i].rightShape,
							positions[i].rightShape.getFill());
			}
		}
		Board mainBoard = new Board(boardWidth, boardHeight);
		insertCellsToBoard(mainBoard, drawBoard.getBoard(), 0, 0);
		setBoard(mainBoard);
	}

	protected DancePosition getPosition() {
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] != null && positions[i].y < DancerShape.height
					&& positions[i].y >= 0) {
				if ((positions[i].leftShape != null && positions[i].leftShape
						.getFill() == Cell.Blink) //
						|| (positions[i].upShape != null && positions[i].upShape
								.getFill() == Cell.Blink) //
						|| (positions[i].downShape != null && positions[i].downShape
								.getFill() == Cell.Blink) //
						|| (positions[i].rightShape != null && positions[i].rightShape
								.getFill() == Cell.Blink)) //
					return null;
				else
					return positions[i];
			}
		}
		return null;
	}

	protected boolean checkDanceStep(DancerShape dancer) {
		if (dancer == null)
			return true;

		return keys.contains(keysToRotate.get(dancer.getRotationAngle()));
	}

	protected void increaseScores(int score) {
		playEffect(Effects.turn);

		setScore(getScore() + score + bonus);

		draw();
	}
	
	@Override
	protected void setSpeed(int speed) {
		super.setSpeed(speed);
		
		if (getSpeed() == 1)
			setLevel(getLevel() + 1);
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
				if (!checkDanceStep(position.leftShape)
						|| !checkDanceStep(position.upShape)
						|| !checkDanceStep(position.downShape)
						|| !checkDanceStep(position.rightShape)) {
					return;
				} else {
					int score = 0;
					position.setCaught(true);

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

					if (++bonus > 4)
						bonus = 4;

					increaseScores(score);
				}
			}
		}
	}

}
