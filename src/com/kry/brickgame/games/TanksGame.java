package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkBoardCollision;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.ObstacleUtils.getPreparedObstacles;
import static com.kry.brickgame.games.ObstacleUtils.getRandomObstacles;
import static com.kry.brickgame.games.ObstacleUtils.tanksObstacles;

import java.awt.Point;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Bullet;
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
	public static final int subtypesNumber = 8;

	final static int[][] spawnPoints = new int[][] {//
	{ 0, 0 }, { BOARD_WIDTH - 1, 0 }, // bottom
			{ 0, BOARD_HEIGHT / 2 }, { BOARD_WIDTH - 1, BOARD_HEIGHT / 2 },// middle
			{ 0, BOARD_HEIGHT - 1 }, { BOARD_WIDTH - 1, BOARD_HEIGHT - 1 } // top
	};

	/**
	 * Controlled-by-the-player playerTank
	 */
	private TankShape playerTank;

	private TankShape enemyTanks[];

	private int enemiesCount;
	/**
	 * Use preloaded levels or generate new ones?
	 */
	private boolean usePreloadedLevels;

	private volatile Bullet[] freeBullets;

	final private int killsToNextLevel = 20;

	private int enemiesKilled;

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

		// ==define the parameters of the types of game==
		// for types 1-4
		usePreloadedLevels = (getType() <= 4);
		// 3 tanks on type 1, 6 - on type 4
		if (getType() % 4 == 0)
			enemiesCount = 6;
		else
			enemiesCount = getType() % 4 + 2;
		enemyTanks = new TankShape[enemiesCount];

		freeBullets = new Bullet[enemiesCount * 4];
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		loadNewLevel();

		int currentSpeed;
		int enemyTankNumber = 0;

		// create timer for bullets
		Timer bulletSwarm = new Timer("BulletSwarm", true);
		bulletSwarm.schedule(new TimerTask() {
			@Override
			public void run() {
				if (getStatus() == Status.Running) {
					flightOfBullets();
				}
			}
		}, 0, ANIMATION_DELAY * 3);

		while (!interrupted() && (getStatus() != Status.GameOver)) {

			currentSpeed = getSpeed(true) * 2;
			if (enemyTankNumber >= enemiesCount)
				enemyTankNumber = 0;

			if (getStatus() != Status.Paused) {
				// moving
				if (elapsedTime(currentSpeed)) {
					if (enemiesKilled >= killsToNextLevel)
						win();
					else
						enemyTurn(enemyTankNumber++);
				}
			}
			// processing of key presses
			processKeys();
		}

		bulletSwarm.cancel();
	}

	/**
	 * Loading or reloading the specified level
	 */
	protected void loadNewLevel() {
		// starting position - the middle of the board
		initPlayerTank();

		initEnemyTanks();

		// draws the playerTank
		setBoard(drawTank(getBoard(), playerTank));

		if (usePreloadedLevels)
			loadPreparedObstacles();
		else
			loadRandomObstacles();

		enemiesKilled = 0;

		setStatus(Status.Running);
	}

	/**
	 * Loading predefined obstacles
	 */
	private void loadPreparedObstacles() {
		if (getLevel() > 1) {
			setBoard(getPreparedObstacles(getBoard(),
					tanksObstacles[getLevel()]));
		}
	}

	/**
	 * Generating random obstacles
	 */
	private void loadRandomObstacles() {
		if (getLevel() > 1) {
			setBoard(getRandomObstacles(getBoard(), getLevel() - 1, 0, 0, 3, 3));
		}
	}

	/**
	 * Setting the coordinates of the start point of the Player Tank
	 */
	private void initPlayerTank() {
		playerTank = new TankShape(0);
		playerTank.setX(boardWidth / 2 - 1);
		playerTank.setY(boardHeight / 2 - playerTank.getHeight());
	}

	/**
	 * Clearing the enemyTanks' array
	 */
	private void initEnemyTanks() {
		for (int i = 0; i < enemiesCount; i++) {
			enemyTanks[i] = null;
		}
	}

	/**
	 * AI turn
	 * 
	 * @param tankNumber
	 *            tank number for turn
	 */
	private void enemyTurn(int tankNumber) {
		TankShape enemyTank = enemyTanks[tankNumber];

		if (enemyTank == null) {
			if (new Random().nextInt(4) != 0)
				respawn(tankNumber);
		} else {
			RotationAngle moveDirection = getDirectionForMovement(enemyTank);
			if (checkForShot(enemyTank)) {
				RotationAngle shotDirection = getDirectionForShoot(enemyTank);

				if (shotDirection == moveDirection) {
					enemyTank = moveTank(enemyTank, moveDirection);
				} else {
					enemyTank = rotateTank(enemyTank, shotDirection);
				}

				sleep(ANIMATION_DELAY);

				Bullet bullet = enemyTank.fire();
				if (bullet != null) {
					setBoard(drawShape(getBoard(), bullet.x, bullet.y, bullet,
							bullet.getFill()));
				}
			} else {
				enemyTank = moveTank(enemyTank, moveDirection);
			}
			enemyTanks[tankNumber] = enemyTank;
		}
	}

	/**
	 * Create a new enemy tank
	 * 
	 * @param tankNumber
	 *            number of tank for creation
	 * @return {@code true} if creation is success
	 */
	private boolean respawn(int tankNumber) {
		TankShape newTank = new TankShape(1);

		int tryCount = 3;

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

	/**
	 * Getting the optimal direction for the shooting
	 * 
	 * @param tank
	 *            enemy tank
	 * @return shooting direction
	 */
	private RotationAngle getDirectionForShoot(TankShape tank) {
		int x = tank.x();
		int y = tank.y();

		RotationAngle result;

		if (Math.abs(x - playerTank.x()) < Math.abs(y - playerTank.y())) {
			result = ((y - playerTank.y() > 0) ? DOWN : UP);
		} else {
			result = ((x - playerTank.x() > 0) ? LEFT : RIGHT);
		}

		return result;
	}

	/**
	 * Getting the optimal direction for the movement
	 * 
	 * @param tank
	 *            enemy tank
	 * @return movement direction
	 */
	private RotationAngle getDirectionForMovement(TankShape tank) {
		int x = tank.x();
		int y = tank.y();

		double[] weightOfDirection = new double[] { 0.25, 0.25, 0.25, 0.25 };

		int horizontalDistance = x - playerTank.x();
		int verticalDistance = y - playerTank.y();

		boolean isHorizontalMinDistance = (Math.abs(horizontalDistance) < Math
				.abs(verticalDistance));

		weightOfDirection[tank.getDirection().ordinal()] += 0.45;

		RotationAngle prefHorDirection = (horizontalDistance < 0) ? RIGHT
				: LEFT;
		RotationAngle prefVertDirection = (verticalDistance < 0) ? UP : DOWN;

		if (horizontalDistance != 0)
			weightOfDirection[prefHorDirection.ordinal()] += isHorizontalMinDistance ? 0.5
					: 0.25;
		if (verticalDistance != 0)
			weightOfDirection[prefVertDirection.ordinal()] += isHorizontalMinDistance ? 0.25
					: 0.5;

		if (playerTank.getDirection() == LEFT
				|| playerTank.getDirection() == RIGHT) {
			weightOfDirection[prefVertDirection.ordinal()] -= 0.25;
		} else {
			weightOfDirection[prefHorDirection.ordinal()] -= 0.25;
		}

		for (int i = 0; i < weightOfDirection.length; i++) {
			Point nearPoint = new Point(tank.x(), tank.y());
			Point farPoint = new Point(tank.x(), tank.y());

			RotationAngle checkingDir = RotationAngle.values()[i];

			switch (checkingDir) {
			case d0:// Up
				nearPoint.y++;
				farPoint.y += tank.getHeight();
				break;
			case d90:// Right
				nearPoint.x++;
				farPoint.x += tank.getWidth();
				break;
			case d180:// Down
				nearPoint.y--;
				farPoint.y -= tank.getHeight();
				break;
			case d270:// Left
				nearPoint.x--;
				farPoint.x -= tank.getWidth();
				break;
			}

			if (!isPossibleMove(tank, farPoint, checkingDir)) {
				weightOfDirection[i] -= 0.25;

				if (!isPossibleMove(tank, nearPoint, checkingDir))
					weightOfDirection[i] -= 0.75;
			} else {
				weightOfDirection[i] += 0.25;
			}

		}

		RotationAngle[] directions = descSort(weightOfDirection);

		Random r = new Random();

		for (int i = 0; i < directions.length; i++) {
			double chance = weightOfDirection[directions[i].ordinal()];

			if (chance > 0.95)
				chance = 0.95;
			else if (chance < 0)
				chance = 0;

			if (r.nextDouble() <= chance)
				return directions[i];
		}

		return directions[0];
	}

	/**
	 * Sorting an array of directions in descending order of their weights using
	 * bubble sort
	 * 
	 * @param weights
	 *            weights of each direction
	 * @return sorted array of directions
	 */
	private static RotationAngle[] descSort(double[] weights) {
		RotationAngle[] result = RotationAngle.values();

		for (int i = 0; i < weights.length - 1; i++) {
			boolean flag = false;
			for (int j = 0; j < weights.length - i - 1; j++) {
				if (weights[result[j].ordinal()] < weights[result[j + 1]
						.ordinal()]) {
					RotationAngle temp = result[j];
					result[j] = result[j + 1];
					result[j + 1] = temp;
					flag = true;
				}
			}
			if (!flag)
				break;
		}

		return result;
	}

	/**
	 * Checking the possibility of make a shot
	 * 
	 * @param tank
	 *            enemy tank
	 * @return {@code true} if need to shoot
	 */
	private boolean checkForShot(TankShape tank) {
		final int minDistanceLimit = 4;
		final int maxDistanceLimit = 9;
		final double rate = (1 / (double) (minDistanceLimit + maxDistanceLimit));

		int horizontalDistance = tank.x() - playerTank.x();
		int verticalDistance = tank.y() - playerTank.y();

		int minDistance, maxDistance;
		if (Math.abs(horizontalDistance) < Math.abs(verticalDistance)) {
			minDistance = Math.abs(horizontalDistance);
			maxDistance = Math.abs(verticalDistance);
		} else {
			minDistance = Math.abs(verticalDistance);
			maxDistance = Math.abs(horizontalDistance);
		}

		double chance = 1 - (minDistance + maxDistance - 1) * rate;

		if ((horizontalDistance < 0 && playerTank.getDirection() == LEFT)
				|| (horizontalDistance > 0 && playerTank.getDirection() == RIGHT)
				|| (verticalDistance < 0 && playerTank.getDirection() == DOWN)
				|| (verticalDistance > 0 && playerTank.getDirection() == UP))
			chance += rate;

		RotationAngle shotDirection = getDirectionForShoot(tank);

		if (isFrendlyTankAhead(tank, (shotDirection == LEFT
				|| shotDirection == RIGHT ? horizontalDistance
				: verticalDistance), shotDirection))
			chance -= rate * 2;
		else if (minDistance <= playerTank.getWidth() / 2)
			chance += rate * 6;

		if (chance > 1 - rate)
			chance = 1 - rate;
		else if (chance < rate)
			chance = rate;

		return new Random().nextDouble() <= chance;
	}

	/**
	 * Checking if the {@code tank} is located in the {@code viewDirection} from
	 * the {@code startPoint} no further the {@code distance}
	 * 
	 * @param startPoint
	 *            starting point for the check
	 * @param distance
	 *            maximal distance for the check
	 * @param viewDirection
	 *            direction for the check
	 * @param tank
	 *            checking tank
	 * @return {@code true} if the {@code tank} is located in the
	 *         {@code viewDirection} from the {@code startPoint} no further the
	 *         {@code distance}
	 */
	private static boolean isTankAhead(Point startPoint, int distance,
			RotationAngle viewDirection, TankShape tank) {

		if (tank.x() == startPoint.x && tank.y() == startPoint.y)
			return false;

		switch (viewDirection) {
		case d90: // Right
		case d270: // Left
			if ((Math.abs(tank.y() - startPoint.y) <= tank.getHeight() / 2)
					&& (tank.x() - startPoint.x)
							* (viewDirection == LEFT ? -1 : 1) <= distance)
				return true;
			break;
		case d0: // Up
		case d180: // Down
			if ((Math.abs(tank.x() - startPoint.x) <= tank.getWidth() / 2)
					&& (tank.y() - startPoint.y)
							* (viewDirection == DOWN ? -1 : 1) <= distance)
				return true;
			break;
		}

		return false;
	}

	/**
	 * Checking if one of the enemy tanks is located in the
	 * {@code viewDirection} from the another enemy {@code tank} no further the
	 * {@code distance}
	 * 
	 * @param tank
	 *            enemy tank
	 * @param distance
	 *            maximal distance for the check
	 * @param viewDirection
	 *            direction for the check
	 * @return if one of the enemy tanks is located in the {@code viewDirection}
	 *         from the {@code tank}
	 */
	private boolean isFrendlyTankAhead(TankShape tank, int distance,
			RotationAngle viewDirection) {
		for (TankShape enemyTank : enemyTanks) {
			if (enemyTank != null
					&& enemyTank != tank
					&& isTankAhead(new Point(tank.x(), tank.y()), distance,
							viewDirection, enemyTank))
				return true;
		}
		return false;
	}

	/**
	 * Move the tank to a new location from the movement direction
	 * 
	 * @param tank
	 *            tank for the moving
	 * @param direction
	 *            movement direction
	 * @return the tank after the moving
	 */
	private TankShape moveTank(TankShape tank, RotationAngle direction) {
		Board board = getBoard().clone();
		TankShape newTank = tank.clone();

		// Erase the tank to not interfere with the checks
		board = eraseTank(board, newTank);

		if (newTank.getRotationAngle() != direction) {
			newTank.changeRotationAngle(direction);
			if (checkCollision(board, newTank, newTank.x(), newTank.y()))
				newTank.move(direction);
		} else {
			newTank.move(direction);
		}

		if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
				|| (checkCollision(board, newTank, newTank.x(), newTank.y()))) {
			return tank;
		}

		// draw the playerTank on the new place
		setBoard(drawTank(board, newTank));

		return newTank;
	}

	/**
	 * Checking the possibility of moving the tank to the new location from the
	 * movement direction
	 * 
	 * @param tank
	 *            tank for the moving
	 * @param newPlace
	 *            coordinates of the new location
	 * @param direction
	 *            movement direction
	 * @return {@code true} if moving is possible
	 */
	private boolean isPossibleMove(TankShape tank, Point newPlace,
			RotationAngle direction) {
		Board board = getBoard().clone();
		TankShape checkingTank = tank.clone();

		// Erase the tank to not interfere with the checks
		board = eraseTank(board, tank);

		checkingTank.changeRotationAngle(direction);

		if (checkBoardCollision(board, checkingTank, newPlace.x, newPlace.y)
				|| (checkCollision(board, checkingTank, newPlace.x, newPlace.y)))
			return false;

		return true;
	}

	/**
	 * Rotate the tank to the new direction
	 * 
	 * @param tank
	 *            tank for the rotation
	 * @param direction
	 *            direction of the rotation
	 * @return the tank after the rotation
	 */
	private TankShape rotateTank(TankShape tank, RotationAngle direction) {
		Board board = getBoard().clone();
		TankShape newTank = tank.clone();

		// Erase the tank to not interfere with the checks
		board = eraseTank(board, newTank);

		newTank.changeRotationAngle(direction);
		if (checkCollision(board, newTank, newTank.x(), newTank.y()))
			newTank.move(direction);

		if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
				|| (checkCollision(board, newTank, newTank.x(), newTank.y())))
			return tank;

		// draw the playerTank on the new place
		setBoard(drawTank(board, newTank));

		return newTank;
	}

	/**
	 * Draw the playerTank to the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param playerTank
	 *            the playerTank for drawing
	 * @return the board after drawing the playerTank
	 */
	private static Board drawTank(Board board, TankShape tank) {
		return drawShape(board, tank.x(), tank.y(), tank, tank.getFill());
	}

	/**
	 * Erase the playerTank from the board
	 * 
	 * @param board
	 *            the board for drawing
	 * @param playerTank
	 *            the playerTank for drawing
	 * @return the board after drawing the playerTank
	 */
	private static Board eraseTank(Board board, TankShape tank) {
		return drawShape(board, tank.x(), tank.y(), tank, Cell.Empty);
	}

	private void movePlayerTank(RotationAngle direction) {
		playerTank = moveTank(playerTank, direction);
	}

	/**
	 * Processing the flight of bullets
	 */
	private synchronized void flightOfBullets() {
		for (int i = 0; i < freeBullets.length; i++) {
			flightOfBullet(freeBullets[i], false);
		}
		if (playerTank != null) {
			for (int i = playerTank.getBullets().length - 1; i >= 0; i--) {
				flightOfBullet(playerTank.getBullets()[i], true);
			}
		}
		for (TankShape enemyTank : enemyTanks) {
			if (enemyTank != null) {
				for (Bullet bullet : enemyTank.getBullets()) {
					flightOfBullet(bullet, false);
				}
			}
		}

	}

	private synchronized void flightOfBullet(Bullet bullet,
			boolean isPlayerBullet) {
		Bullet result = bullet;
		if (result != null) {
			Board board = getBoard().clone();
			board = drawShape(board, result.x, result.y, result, Cell.Empty);

			result = result.flight();
			if (checkBoardCollision(board, result, result.x, result.y)) {
				destroyBullet(result);
			} else {
				if (board.getCell(result.x, result.y) != Cell.Empty) {
					Bullet checkBullet = checkCollisionWithAllBullets(result);
					boolean isNotTank = true;
					if (checkBullet != null) {
						destroyBullet(checkBullet);
					} else {
						if (!isPlayerBullet
								&& checkCollisionWithTank(playerTank, result)) {
							loss(result.x, result.y);
							return;
						}
						for (int i = 0; i < enemyTanks.length; i++) {
							TankShape enemyTank = enemyTanks[i];
							if (checkCollisionWithTank(enemyTank, result)) {
								if (isPlayerBullet) {
									board = eraseTank(board, enemyTank);
									destroyEnemyTank(i);
								}
								isNotTank = false;
								break;
							}
						}
					}
					if (isNotTank) {
						board.setCell(Cell.Empty, result.x, result.y);
					}
					destroyBullet(result);
				} else {
					board.setCell(result.getFill(), result.x, result.y);
				}
			}
			setBoard(board);
		}
	}

	private void destroyBullet(Bullet bullet) {
		for (int i = 0; i < freeBullets.length; i++) {
			if (freeBullets[i] == bullet) {
				freeBullets[i] = null;
				return;
			}
		}
		if (playerTank != null) {
			playerTank.destroyBullet(bullet);
		}
		for (TankShape enemyTank : enemyTanks) {
			if (enemyTank != null) {
				enemyTank.destroyBullet(bullet);
			}
		}
	}

	private void destroyEnemyTank(int tankNumber) {
		if (enemyTanks[tankNumber] != null) {
			for (Bullet bullet : enemyTanks[tankNumber].getBullets()) {
				if (bullet != null)
					setBulletToFreeFly(bullet);
			}
			enemyTanks[tankNumber] = null;
		}

		enemiesKilled++;
		setScore(getScore() + 1);
	}

	private void setBulletToFreeFly(Bullet bullet) {
		for (int i = 0; i < freeBullets.length; i++) {
			if (freeBullets[i] == null) {
				freeBullets[i] = bullet;
			}
		}
	}

	private static boolean checkCollisionWithTank(TankShape tank, Bullet bullet) {
		if (tank == null || bullet == null)
			return false;

		if ((Math.abs(tank.y() - bullet.y) > tank.getHeight() / 2)
				&& (Math.abs(tank.x() - bullet.x) > tank.getWidth() / 2))
			return false;

		Board checkBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		checkBoard = drawShape(checkBoard, tank.x(), tank.y(), tank,
				tank.getFill());
		return checkCollision(checkBoard, bullet, bullet.x, bullet.y);
	}

	private static Bullet checkCollisionWithTankBullets(TankShape tank,
			Bullet bullet) {
		if (tank == null || bullet == null)
			return null;
		if (tank != null) {
			for (Bullet checkBullet : tank.getBullets()) {
				if (checkBullet != null && checkBullet != bullet
						&& checkBullet.x == bullet.x
						&& checkBullet.y == bullet.y)
					return checkBullet;
			}
		}
		return null;
	}

	private Bullet checkCollisionWithAllBullets(Bullet bullet) {
		if (bullet == null)
			return null;
		Bullet checkBullet;
		for (TankShape enemyTank : enemyTanks) {
			checkBullet = checkCollisionWithTankBullets(enemyTank, bullet);
			if (checkBullet != null)
				return checkBullet;
		}

		return checkCollisionWithTankBullets(playerTank, bullet);
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
				Bullet bullet = playerTank.fire();
				if (bullet != null)
					setBoard(drawShape(getBoard(), bullet.x, bullet.y, bullet,
							bullet.getFill()));
				sleep(ANIMATION_DELAY * 6);
			}
		}
	}

}
