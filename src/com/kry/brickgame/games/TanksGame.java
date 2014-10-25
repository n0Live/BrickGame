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
	/**
	 * Spawn point of enemy tanks
	 */
	final private static int[][] spawnPoints = new int[][] {//
	{ 0, 0 }, { BOARD_WIDTH - 1, 0 }, // bottom
			{ 0, BOARD_HEIGHT / 2 }, { BOARD_WIDTH - 1, BOARD_HEIGHT / 2 },// middle
			{ 0, BOARD_HEIGHT - 1 }, { BOARD_WIDTH - 1, BOARD_HEIGHT - 1 } // top
	};
	/**
	 * Quantity of destroyed tanks to enter to the next level
	 */
	final private static int killsToNextLevel = 20;
	/**
	 * Controlled-by-the-player tank
	 */
	private TankShape playerTank;
	/**
	 * Group of enemy tanks
	 */
	private TankShape enemyTanks[];
	/**
	 * Count of enemy tanks
	 */
	private int enemiesCount;
	/**
	 * Bullets fired by the enemy tanks
	 */
	private volatile Bullet[] enemyBullets;
	/**
	 * Bullets fired by the player tank
	 */
	private volatile Bullet[] playerBullets;
	/**
	 * Count of destroyed enemy tanks on this level
	 */
	private int enemiesKilled;
	/**
	 * Use preloaded levels or generate new ones?
	 */
	private boolean usePreloadedLevels;

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
	 *            <li>Tanks War with 3 enemy and preloaded field;
	 *            <li>Tanks War with 4 enemy and preloaded field;
	 *            <li>Tanks War with 5 enemy and preloaded field;
	 *            <li>Tanks War with 6 enemy and preloaded field;
	 *            <li>Tanks War with 3 enemy and randomly generated field;
	 *            <li>Tanks War with 4 enemy and randomly generated field;
	 *            <li>Tanks War with 5 enemy and randomly generated field;
	 *            <li>Tanks War with 6 enemy and randomly generated field.
	 */
	public TanksGame(int speed, int level, int type) {
		super(speed, level, type);

		// ==define the parameters of the types of game==
		// for types 1-4
		usePreloadedLevels = (getType() <= 4);
		// 3 tanks on type 1, 6 - on type 4
		enemiesCount = (getType() % 4 == 0) ? 6 : getType() % 4 + 2;
		enemyTanks = new TankShape[enemiesCount];

		// two bullets for each enemy
		enemyBullets = new Bullet[enemiesCount * 2];
		// four bullets for player
		playerBullets = new Bullet[4];
	}

	/**
	 * Launching the game
	 */
	@Override
	public void start() {
		loadNewLevel();

		int currentSpeed;

		// Number of the enemy tanks whose turn
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
			currentSpeed = getSpeed(true) * 3;

			if (getStatus() != Status.Paused) {
				if (elapsedTime(currentSpeed)) {
					if (enemiesKilled >= killsToNextLevel)
						win();
					else {
						int tryCount = enemiesCount;
						do {
							if (enemyTankNumber >= enemiesCount)
								enemyTankNumber = 0;
							tryCount--;
						} while (!aiTurn(enemyTankNumber++) && (tryCount > 0));
					}
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
		resetBullets();
		resetPlayerTank();
		resetEnemyTanks();

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
	private void resetPlayerTank() {
		playerTank = new TankShape(0);
		// starting position - the middle of the board
		playerTank.setX(boardWidth / 2 - 1);
		playerTank.setY(boardHeight / 2 - playerTank.getHeight());
	}

	/**
	 * Clearing the enemyTanks array
	 */
	private void resetEnemyTanks() {
		for (int i = 0; i < enemiesCount; i++) {
			enemyTanks[i] = null;
		}
	}

	/**
	 * Clearing the bullets array
	 */
	private void resetBullets() {
		for (int i = 0; i < enemyBullets.length; i++) {
			enemyBullets[i] = null;
		}
		for (int i = 0; i < playerBullets.length; i++) {
			playerBullets[i] = null;
		}
	}

	/**
	 * AI turn: spawn of a new tank or moving the existing one
	 * 
	 * @param tankNumber
	 *            tank number for turn
	 * @return {@code true} when move is success, otherwise - {@code false}
	 */
	private boolean aiTurn(int tankNumber) {
		// whether the tank shoot when creating?
		boolean isBornWithGun = false;

		if (enemyTanks[tankNumber] == null) {
			// chance for respawn 3 from 4
			if (new Random().nextInt(4) != 0 && respawn(tankNumber)) {
				// chance for shooting after spawn 1 from enemiesCount
				if (new Random().nextInt(enemiesCount) == 0)
					isBornWithGun = true;
			} else {
				return false;
			}
		}

		boolean result = true;

		TankShape enemyTank = enemyTanks[tankNumber];

		RotationAngle moveDirection = getDirectionForMovement(enemyTank);

		if (isBornWithGun || checkForShot(enemyTank)) {
			RotationAngle shotDirection = getDirectionForShoot(enemyTank);
			// if the movement and shooting direction is the same, the tank
			// fired on the move
			if (shotDirection == moveDirection) {
				enemyTank = moveTank(enemyTank, moveDirection, false);
			} else {
				enemyTank = moveTank(enemyTank, shotDirection, true);
			}

			sleep(ANIMATION_DELAY * 2);
			result = fire(enemyTank);
		} else {
			enemyTank = moveTank(enemyTank, moveDirection, false);
		}
		enemyTanks[tankNumber] = enemyTank;
		return result;
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

		int newX, newY;
		int spawnPoint;

		// trying to create a tank on one of the spawn points
		int tryCount = 3;
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

		// weight for each direction,
		// to calculate the chance of movement in this direction
		float[] weightOfDirection = new float[] { 0.25f, 0.25f, 0.25f, 0.25f };

		int horizontalDistance = x - playerTank.x();
		int verticalDistance = y - playerTank.y();

		boolean isHorizontalMinDistance = (Math.abs(horizontalDistance) < Math
				.abs(verticalDistance));
		// increases the weight of the current direction
		weightOfDirection[tank.getDirection().ordinal()] += 0.45f;
		// defines the direction to get closer to the player tank vertically and
		// horizontally
		RotationAngle prefHorDirection = (horizontalDistance < 0) ? RIGHT
				: LEFT;
		RotationAngle prefVertDirection = (verticalDistance < 0) ? UP : DOWN;

		// increases the weight of the preferred directions to 0.25
		// and weight of the direction for minimal distance to 0.5
		if (horizontalDistance != 0)
			weightOfDirection[prefHorDirection.ordinal()] += isHorizontalMinDistance ? 0.5f
					: 0.25f;
		if (verticalDistance != 0)
			weightOfDirection[prefVertDirection.ordinal()] += isHorizontalMinDistance ? 0.25f
					: 0.5f;

		// decreases the weight of direction when it crosses the direction of
		// movement of the player tank
		if (playerTank.getDirection() == LEFT
				|| playerTank.getDirection() == RIGHT) {
			weightOfDirection[prefVertDirection.ordinal()] -= 0.25f;
		} else {
			weightOfDirection[prefHorDirection.ordinal()] -= 0.25f;
		}

		// decreases the weight of direction when it isn't possible for
		// tank.getWidth() to 0.25
		// and when it isn't possible for one move to 0.75
		for (int i = 0; i < weightOfDirection.length; i++) {
			Point nearPoint = tank.getCoordinates();
			Point farPoint = tank.getCoordinates();

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
				weightOfDirection[i] -= 0.25f;

				if (!isPossibleMove(tank, nearPoint, checkingDir))
					weightOfDirection[i] -= 0.75f;
			} else {
				weightOfDirection[i] += 0.25f;
			}

		}

		// Sorting the direction to descending order of their weights
		RotationAngle[] directions = descShakerSort(weightOfDirection);
		// roll the dice for each direction
		for (int i = 0; i < directions.length; i++) {
			float chance = weightOfDirection[directions[i].ordinal()];
			// chance from 0 to 0.95
			if (chance > 0.95f)
				chance = 0.95f;
			else if (chance < 0)
				chance = 0;

			if (new Random().nextFloat() <= chance)
				return directions[i];
		}
		// when nothing is selected, returns the direction with the highest
		// weight
		return directions[0];
	}

	/**
	 * Sorting an array of directions in descending order of their weights using
	 * shaker (cocktail) sort
	 * 
	 * @param weights
	 *            weights of each direction
	 * @return sorted array of directions
	 */
	private static RotationAngle[] descShakerSort(float[] weights) {
		RotationAngle[] result = RotationAngle.values();

		int left = 0;
		int right = result.length - 1;

		do {
			for (int i = right; i > left; i--) {
				if (weights[result[i].ordinal()] > weights[result[i - 1]
						.ordinal()]) {
					RotationAngle temp = result[i];
					result[i] = result[i - 1];
					result[i - 1] = temp;
				}
			}
			left++;
			for (int i = left; i < right; i++) {
				if (weights[result[i].ordinal()] < weights[result[i + 1]
						.ordinal()]) {
					RotationAngle temp = result[i];
					result[i] = result[i + 1];
					result[i + 1] = temp;
				}
			}
			right--;
		} while (left <= right);

		return result;
	}

	/**
	 * Calculating the possibility of making a shot
	 * 
	 * @param tank
	 *            enemy tank
	 * @return {@code true} when can be make a shot
	 */
	private boolean checkForShot(TankShape tank) {
		// maximum value of distance at which can be make a shot
		final int minDistanceLimit = 4;
		final int maxDistanceLimit = 9;
		// step of increasing chance of a shot
		final float rate = (1 / (float) (minDistanceLimit + maxDistanceLimit));

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
		// chance of shot decreases with distance
		float chance = 1 - (minDistance + maxDistance - 1) * rate;
		// chance of shot increases when the player tank going closer
		if ((horizontalDistance < 0 && playerTank.getDirection() == LEFT)
				|| (horizontalDistance > 0 && playerTank.getDirection() == RIGHT)
				|| (verticalDistance < 0 && playerTank.getDirection() == DOWN)
				|| (verticalDistance > 0 && playerTank.getDirection() == UP))
			chance += rate;

		RotationAngle shotDirection = getDirectionForShoot(tank);

		// chance decreases when ahead of a friendly tank
		if (isFrendlyTankAhead(tank, (shotDirection == LEFT
				|| shotDirection == RIGHT ? horizontalDistance
				: verticalDistance), shotDirection))
			chance -= rate * 2;
		// chance greatly increases when should get a shot at the player tank
		else if (minDistance <= playerTank.getWidth() / 2)
			chance += rate * 6;

		// chance from rate to 1 - rate
		if (chance > 1 - rate)
			chance = 1 - rate;
		else if (chance < rate)
			chance = rate;

		return new Random().nextFloat() <= chance;
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

		// returns false when the tank's position and the startPoint is the same
		if (startPoint.equals(tank.getCoordinates()))
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
					&& isTankAhead(tank.getCoordinates(), distance,
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
	 * @param rotationOnly
	 *            only rotate the tank, without moving
	 * @return the tank after the moving
	 */
	private TankShape moveTank(TankShape tank, RotationAngle direction,
			boolean rotationOnly) {
		Board board = getBoard().clone();
		TankShape newTank = tank.clone();

		// Erase the tank to not interfere with the checks
		board = eraseTank(board, newTank);

		if (rotationOnly || newTank.getRotationAngle() != direction) {
			newTank.changeRotationAngle(direction);
			if (checkCollision(board, newTank, newTank.x(), newTank.y()))
				newTank.move(direction);
		} else {
			newTank.move(direction);
		}

		if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
				|| checkCollision(board, newTank, newTank.x(), newTank.y())) {
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
				|| checkCollision(board, checkingTank, newPlace.x, newPlace.y))
			return false;

		return true;
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

	/**
	 * Move the player tank to a new location from the movement direction
	 * 
	 * @param direction
	 *            movement direction
	 */
	private void movePlayerTank(RotationAngle direction) {
		playerTank = moveTank(playerTank, direction, false);
	}

	/**
	 * Processing the flight of bullets
	 */
	private synchronized void flightOfBullets() {
		for (int i = 0; i < enemyBullets.length; i++) {
			flightOfBullet(enemyBullets[i], false);
		}
		for (int i = 0; i < playerBullets.length; i++) {
			flightOfBullet(playerBullets[i], true);
		}
	}

	/**
	 * Processing the flight of one bullet
	 * 
	 * @param bullet
	 *            flying bullet
	 * @param isPlayerBullet
	 *            {@code true} when bullet fired by the player tank
	 */
	private synchronized void flightOfBullet(Bullet bullet,
			boolean isPlayerBullet) {
		Bullet result = bullet;

		if (result != null) {
			Board board = getBoard().clone();
			// erase bullet from the board
			board = drawShape(board, result.x(), result.y(), result, Cell.Empty);
			// move bullet to the new position
			result = result.flight();

			if (checkBoardCollision(board, result, result.x(), result.y())) {
				// if the bullet has left the board
				destroyBullet(result);
			} else {
				// if the bullet hit something
				if (board.getCell(result.x(), result.y()) != Cell.Empty) {
					// bullet hit a simple obstacle?
					boolean isObstacle = true;
					// collision check with one of another bullets
					Bullet checkBullet = checkCollisionWithAllBullets(result);
					if (checkBullet != null) {
						destroyBullet(checkBullet);
					} else {
						if (!isPlayerBullet
								&& checkCollisionWithTank(playerTank, result)) {
							// when the enemy bullet hit the player tank
							loss(result.x(), result.y());
							return;
						}
						for (int i = 0; i < enemyTanks.length; i++) {
							TankShape enemyTank = enemyTanks[i];
							if (checkCollisionWithTank(enemyTank, result)) {
								if (isPlayerBullet) {
									// when the player bullet hit the enemy tank
									board = eraseTank(board, enemyTank);
									destroyEnemyTank(i);
								}
								isObstacle = false;
								break;
							}
						}
					}
					if (isObstacle) {
						board.setCell(Cell.Empty, result.x(), result.y());
					}
					destroyBullet(result);
				} else {
					board.setCell(result.getFill(), result.x(), result.y());
				}
			}
			setBoard(board);
		}
	}

	/**
	 * Remove the bullet from the bullets arrays
	 * 
	 * @param bullet
	 *            removed bullet
	 */
	private void destroyBullet(Bullet bullet) {
		for (int i = 0; i < enemyBullets.length; i++) {
			if (enemyBullets[i] == bullet) {
				enemyBullets[i] = null;
				break;
			}
		}
		for (int i = 0; i < playerBullets.length; i++) {
			if (playerBullets[i] == bullet) {
				playerBullets[i] = null;
				break;
			}
		}
	}

	/**
	 * Remove destroyed enemy tank from the enemyTanks array and increase score
	 * 
	 * @param tankNumber
	 *            number of the removed tank
	 */
	private void destroyEnemyTank(int tankNumber) {
		if (enemyTanks[tankNumber] != null)
			enemyTanks[tankNumber] = null;

		enemiesKilled++;
		setScore(getScore() + 1);
	}

	/**
	 * Collision check of the {@code tank} with the {@code bullet}
	 * 
	 * @param tank
	 *            checking tank
	 * @param bullet
	 *            checking bullet
	 * @return {@code true} if there is a collision
	 */
	private static boolean checkCollisionWithTank(TankShape tank, Bullet bullet) {
		if (tank == null || bullet == null)
			return false;

		if ((Math.abs(tank.y() - bullet.y()) > tank.getHeight() / 2)
				&& (Math.abs(tank.x() - bullet.x()) > tank.getWidth() / 2))
			return false;

		Board checkBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		checkBoard = drawShape(checkBoard, tank.x(), tank.y(), tank,
				tank.getFill());
		return checkCollision(checkBoard, bullet, bullet.x(), bullet.y());
	}

	/**
	 * Collision check of the {@code bullet} with the each another bullet
	 * 
	 * @param bullet
	 *            checking bullet
	 * @return {@code true} if there is a collision
	 */
	private Bullet checkCollisionWithAllBullets(Bullet bullet) {
		if (bullet == null)
			return null;
		Bullet result = checkCollisionWithBullets(bullet, enemyBullets);
		if (result != null)
			return result;
		else
			return checkCollisionWithBullets(bullet, playerBullets);
	}

	/**
	 * Collision check of the {@code bullet} with the each bullet from the
	 * {@code bullets} array
	 * 
	 * @param bullet
	 *            checking bullet
	 * @param bullets
	 *            array of the checking bullets
	 * @return {@code true} if there is a collision
	 */
	private static Bullet checkCollisionWithBullets(Bullet bullet,
			Bullet[] bullets) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet checkBullet = bullets[i];
			if (checkBullet != null
					&& checkBullet != bullet
					&& checkBullet.getCoordinates().equals(
							bullet.getCoordinates()))
				return checkBullet;
		}
		return null;
	}

	/**
	 * Fires a new bullet in front of the tank
	 * 
	 * @param tank
	 *            shooting tank
	 * 
	 * @return {@code true} when successful shot, otherwise - {@code false}
	 */
	private synchronized boolean fire(TankShape tank) {
		boolean isPlayerBullet = (tank == playerTank);
		Bullet[] bullets = isPlayerBullet ? playerBullets : enemyBullets;

		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i] == null) {
				int bulletX = tank.x();
				int bulletY = tank.y();

				switch (tank.getDirection()) {
				case d0:
					bulletY += 1;
					break;
				case d90:
					bulletX += 1;
					break;
				case d180:
					bulletY -= 1;
					break;
				case d270:
					bulletX -= 1;
					break;
				}

				bullets[i] = new Bullet(bulletX, bulletY, tank.getDirection());

				flightOfBullet(bullets[i], isPlayerBullet);

				Board board = getBoard();
				board.setCell(tank.getFill(), bulletX, bulletY);
				setBoard(board);

				return true;
			}
		}
		return false;
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
				fire(playerTank);
				sleep(ANIMATION_DELAY * 6);
			}
		}
	}

}
