package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkBoardCollision;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.shapes.TankShape;
import com.kry.brickgame.splashes.Splash;
import com.kry.brickgame.splashes.TanksSplash;

/**
 * @author noLive
 * 
 */
public class TanksGame extends GameWithLives {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new TanksSplash();
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 4;
	/**
	 * Controlled-by-the-player tank
	 */
	private TankShape tank;

	private TankShape enemyTanks[];

	/**
	 * The Gun Game
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game type of the game:
	 *            <ol>
	 *            <li>...;
	 */
	public TanksGame(int speed, int level, int type) {
		super(speed, level, type);

		tank = new TankShape(0);

		// ==define the parameters of the types of game==
		int capacity;
		// 3 tanks on type 1, 6 - on type 4
		if (getType() % 4 == 0)
			capacity = 6;
		else
			capacity = getType() % 4 + 2;
		enemyTanks = new TankShape[capacity];
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		loadNewLevel();

		int currentSpeed;
		int enemyTankNumber = 0;

		while (!interrupted() && (getStatus() != Status.GameOver)) {

			currentSpeed = getSpeed(true) * 2;
			if (enemyTankNumber >= enemyTanks.length)
				enemyTankNumber = 0;

			if (getStatus() != Status.Paused) {
				// moving
				if (elapsedTime(currentSpeed)) {
					enemyTurn(enemyTankNumber++);
				}
			}
			// processing of key presses
			processKeys();
		}

	}

	/**
	 * Loading or reloading the specified level
	 */
	protected void loadNewLevel() {
		// starting position - the middle of the board
		initPlayerTank();

		initEnemyTanks();

		// draws the tank
		drawTank(getBoard(), tank);

		setStatus(Status.Running);
	}

	private void initPlayerTank() {
		tank.setX(boardWidth / 2 - 1);
		tank.setY(boardHeight / 2 - tank.getHeight());
	}

	private void initEnemyTanks() {
		for (int i = 0; i < enemyTanks.length; i++) {
			enemyTanks[i] = null;
		}
	}

	private void enemyTurn(int tankNumber) {
		System.out.println(tankNumber);

		TankShape enemyTank = enemyTanks[tankNumber];
		if (enemyTank == null) {
			if (respawnEnemyTank(tankNumber))
				System.out.println("+");
			else
				System.out.println("x");
		} else {
			int x = enemyTank.x();
			int y = enemyTank.y();

			if (isTimeForShoot(enemyTank)) {
				// TODO
				enemyTank = rotateTank(enemyTank,
						getDirectionForShoot(enemyTank));
				System.out.println("*[" + x + "," + y + "]"
						+ enemyTank.getRotationAngle());
			} else {
				enemyTank = moveTank(enemyTank,
						getDirectionForMovement(enemyTank));
				System.out.println("=[" + x + "," + y + "]"
						+ enemyTank.getRotationAngle());
			}
			enemyTanks[tankNumber] = enemyTank;
		}
	}

	private boolean respawnEnemyTank(int tankNumber) {
		final int[][] spawnPoints = new int[][] {
				//
				{ 0, 0 }, { boardWidth - 1, 0 }, // bottom
				{ 0, boardHeight / 2 }, { boardWidth - 1, boardHeight / 2 },// middle
				{ 0, boardHeight - 1 }, { boardWidth - 1, boardHeight - 1 } // top
		};

		TankShape newTank = new TankShape(1);

		int tryCount = 5;

		int newX, newY;
		int spawnPoint;

		do {
			spawnPoint = new Random().nextInt(spawnPoints.length);
			newX = spawnPoints[spawnPoint][0];
			newY = spawnPoints[spawnPoint][1];

			if (newX + newTank.minX() < 0)
				newX -= newTank.minX();
			else if (newX + newTank.maxX() >= boardWidth)
				newX -= newTank.maxX();
			if (newY + newTank.minY() < 0)
				newY -= newTank.minY();
			else if (newY + newTank.maxY() >= boardHeight)
				newY -= newTank.maxY();

			newTank.setX(newX);
			newTank.setY(newY);
			newTank.changeRotationAngle(getDirectionForShoot(newTank));
			tryCount--;
		} while ((checkCollision(getBoard(), newTank, newX, newY))
				&& (tryCount > 0));

		if (tryCount <= 0)
			return false;

		enemyTanks[tankNumber] = newTank;
		drawTank(getBoard(), newTank);

		return true;
	}

	private RotationAngle getDirection(int x, int y, boolean forMovement) {
		RotationAngle result;

		if (Math.abs(x - tank.x()) < Math.abs(y - tank.y())) {
			if (forMovement)
				result = ((x - tank.x() > 0) ? LEFT : RIGHT);
			else
				result = ((y - tank.y() > 0) ? DOWN : UP);
		} else {
			if (forMovement)
				result = ((y - tank.y() > 0) ? DOWN : UP);
			else
				result = ((x - tank.x() > 0) ? LEFT : RIGHT);
		}

		return result;
	}

	private RotationAngle getDirectionForShoot(TankShape tank) {
		return getDirection(tank.x(), tank.y(), false);
	}

	private RotationAngle getDirectionForMovement(TankShape tank) {
		return getDirection(tank.x(), tank.y(), true);
	}

	private boolean isTimeForShoot(TankShape enemyTank) {
		final int minChance = 4;

		int chance = minChance;
		int distance = Math.min(Math.abs(enemyTank.x() - tank.x()),
				Math.abs(enemyTank.y() - tank.y()));

		chance -= distance;

		RotationAngle shotDirection = getDirectionForShoot(enemyTank);

		if ((((shotDirection == UP) || (shotDirection == DOWN))//
				&& (Math.abs(enemyTank.x() - tank.x()) <= tank.getWidth() / 2))//

				|| (((shotDirection == LEFT) || (shotDirection == RIGHT))//
				&& (Math.abs(enemyTank.y() - tank.y()) <= tank.getHeight() / 2)))
			return true;

		RotationAngle moveDirection = getDirectionForMovement(enemyTank);

		if ((moveDirection == enemyTank.getRotationAngle())
				&& (moveDirection != shotDirection))
			chance -= distance / 2;

		if ((shotDirection == UP) || (shotDirection == DOWN)) {
			if (((enemyTank.x() - tank.x() < 0) && (tank.getRotationAngle() == LEFT))
					|| ((enemyTank.x() - tank.x() >= 0) && (tank
							.getRotationAngle() == RIGHT)))
				chance += distance / 2;
		} else {
			if (((enemyTank.y() - tank.y() < 0) && (tank.getRotationAngle() == DOWN))
					|| ((enemyTank.y() - tank.y() >= 0) && (tank
							.getRotationAngle() == UP)))
				chance += distance / 2;
		}

		return chance >= new Random().nextInt(minChance + 1);
	}

	private TankShape moveTank(TankShape tank, RotationAngle direction) {
		Board board = getBoard().clone();
		TankShape newTank = tank.clone();

		// Erase the gun to not interfere with the checks
		board = eraseTank(board, newTank);

		if (newTank.getRotationAngle() != direction) {
			newTank.changeRotationAngle(direction);
			if (checkCollision(board, newTank, newTank.x(), newTank.y()))
				newTank.move(direction);
		} else {
			newTank.move(direction);
		}

		if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
				|| (checkCollision(board, newTank, newTank.x(), newTank.y())))
			return tank;

		// draw the tank on the new place
		setBoard(drawTank(board, newTank));

		return newTank;
	}

	private TankShape rotateTank(TankShape tank, RotationAngle direction) {
		Board board = getBoard().clone();
		TankShape newTank = tank.clone();

		// Erase the gun to not interfere with the checks
		board = eraseTank(board, newTank);

		newTank.changeRotationAngle(direction);
		if (checkCollision(board, newTank, newTank.x(), newTank.y()))
			newTank.move(direction);

		if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
				|| (checkCollision(board, newTank, newTank.x(), newTank.y())))
			return tank;

		// draw the tank on the new place
		setBoard(drawTank(board, newTank));

		return newTank;
	}

	/**
	 * Draw the tank to the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param tank
	 *            the tank for drawing
	 * @return the board after drawing the tank
	 */
	private static Board drawTank(Board board, TankShape tank) {
		return drawShape(board, tank.x(), tank.y(), tank, tank.getFill());
	}

	/**
	 * Erase the tank from the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param tank
	 *            the tank for drawing
	 * @return the board after drawing the tank
	 */
	private static Board eraseTank(Board board, TankShape tank) {
		return drawShape(board, tank.x(), tank.y(), tank, Cell.Empty);
	}

	private void movePlayerTank(RotationAngle direction) {
		tank = moveTank(tank, direction);
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

			if (keys.contains(KeyPressed.KeyLeft)) {
				movePlayerTank(LEFT);
				keys.remove(KeyPressed.KeyLeft);
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyRight)) {
				movePlayerTank(RIGHT);
				keys.remove(KeyPressed.KeyRight);
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyDown)) {
				movePlayerTank(DOWN);
				keys.remove(KeyPressed.KeyDown);
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyUp)) {
				movePlayerTank(UP);
				keys.remove(KeyPressed.KeyUp);
				sleep(ANIMATION_DELAY);
			}
			if (keys.contains(KeyPressed.KeyRotate)) {
				// fire(curX, curY + gun.maxY() + 1, hasTwoSmokingBarrels);
				sleep(ANIMATION_DELAY * 3);
			}
		}
	}

}
