package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameConsts.BOARD_HEIGHT;
import static com.kry.brickgame.games.GameConsts.BOARD_WIDTH;
import static com.kry.brickgame.games.GameConsts.DOWN;
import static com.kry.brickgame.games.GameConsts.LEFT;
import static com.kry.brickgame.games.GameConsts.RIGHT;
import static com.kry.brickgame.games.GameConsts.UP;
import static com.kry.brickgame.games.GameUtils.checkBoardCollision;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.checkTwoShapeCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.setKeyDelay;
import static com.kry.brickgame.games.GameUtils.sleep;
import static com.kry.brickgame.games.ObstacleUtils.getPreparedObstacles;
import static com.kry.brickgame.games.ObstacleUtils.getRandomObstacles;
import static com.kry.brickgame.games.ObstacleUtils.getTanksObstacles;

import java.awt.Point;
import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.shapes.Bullet;
import com.kry.brickgame.shapes.Shape.RotationAngle;
import com.kry.brickgame.shapes.TankShape;

/**
 * @author noLive
 */
public class TanksGame extends GameWithLives {
	private static final long serialVersionUID = 538052340777294912L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.TanksSplash";
	/**
	 * Number of subtypes
	 */
	public static final int subtypesNumber = 8;
	/**
	 * Spawn point of enemy tanks
	 */
	final private static int[][] spawnPoints = new int[][] { { 0, 0 },
			{ BOARD_WIDTH - 1, 0 }, // bottom
			{ 0, BOARD_HEIGHT / 2 }, { BOARD_WIDTH - 1, BOARD_HEIGHT / 2 },// middle
			{ 0, BOARD_HEIGHT - 1 }, { BOARD_WIDTH - 1, BOARD_HEIGHT - 1 } // top
	};
	/**
	 * Quantity of destroyed tanks to enter to the next level
	 */
	final private static int KILLS_TO_NEXT_LEVEL = 20;
	/**
	 * Maximum number of the simultaneous firing player's bullets
	 */
	final private static int PLAYER_BULLETS_COUNT = 4;

	/**
	 * Controlled-by-the-player tank
	 */
	private TankShape playerTank;

	/**
	 * Group of enemy tanks
	 */
	private final TankShape enemyTanks[];

	/**
	 * Count of enemy tanks
	 */
	private final int enemiesCount;

	/**
	 * Number of the enemy tanks whose turn
	 */
	private int enemyTankNumber;

	/**
	 * Bullets fired by the enemy tanks
	 */
	private final Bullet[] enemyBullets;

	/**
	 * Bullets fired by the player tank
	 */
	private final Bullet[] playerBullets;

	/**
	 * Count of destroyed enemy tanks on this level
	 */
	private int enemiesKilled;
	/**
	 * Count of enemy tanks on the board now
	 */
	private int enemiesOnBoard;
	/**
	 * Use preloaded levels or generate new ones?
	 */
	private final boolean usePreloadedLevels;

	/**
	 * The flag is set at a loss and initiates a call loss()
	 */
	private volatile boolean isDead;

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
	 * Remove the bullet from the bullets array
	 * 
	 * @param bullets
	 *            array of the checking bullets
	 * @param bulletIndex
	 *            index of a removing bullet
	 */
	private static void destroyBullet(Bullet[] bullets, int bulletIndex) {
		bullets[bulletIndex] = null;
	}

	/**
	 * Check collision of the {@code bullet} with the each bullet from the
	 * {@code bullets} array and destroy a collided bullet
	 * 
	 * @param bullet
	 *            checking bullet
	 * @param bullets
	 *            array of the checking bullets
	 * @return {@code true} if there is a collision
	 */
	private static boolean destroyCollidedBullets(Bullet bullet,
			Bullet[] bullets) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet checkBullet = bullets[i];
			if (checkBullet != null
					&& checkBullet != bullet
					&& checkBullet.getCoordinates().equals(
							bullet.getCoordinates())) {
				destroyBullet(bullets, i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checking distance between the {@code startPoint} and a nearest obstacle
	 * in the specified {@code direction}.
	 * 
	 * @param startPoint
	 *            starting point for the check
	 * @param direction
	 *            direction for the check
	 * @param board
	 *            the board for checking
	 * @return distance to a nearest obstacle or {@code -1} if not obstacles
	 *         found.
	 */
	private static int distanceToObstacle(Point startPoint,
			RotationAngle direction, Board board) {
		int distance = 0;
		int signX = 0, signY = 0;

		switch (direction) {
		case d90: // Right
			distance = board.getWidth() - startPoint.x;
			signX = 1;
			break;
		case d270: // Left
			distance = startPoint.x;
			signX = -1;
			break;
		case d0: // Up
			distance = board.getHeight() - startPoint.y;
			signY = 1;
			break;
		case d180: // Down
			distance = startPoint.y;
			signY = -1;
			break;
		}

		for (int i = 1; i < distance; i++) {
			if (board.getCell(startPoint.x + i * signX, startPoint.y + i
					* signY) == Cell.Full) return i;
		}

		return -1;
	}

	/**
	 * Draw a tank to a board
	 * 
	 * @param board
	 *            a board for drawing
	 * @param tank
	 *            a tank for drawing
	 * @return a board after drawing a tank
	 */
	private static Board drawTank(Board board, TankShape tank) {
		return drawShape(board, tank, tank.getFill());
	}

	/**
	 * Erase a tank from a board
	 * 
	 * @param board
	 *            a board for drawing
	 * @param tank
	 *            a tank for drawing
	 * @return a board after erasing a tank
	 */
	private static Board eraseTank(Board board, TankShape tank) {
		return drawShape(board, tank, Cell.Empty);
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
		if (startPoint.equals(tank.getCoordinates())) return false;

		switch (viewDirection) {
		case d90: // Right
		case d270: // Left
			if (Math.abs(tank.y() - startPoint.y) <= tank.getHeight() / 2
					&& (tank.x() - startPoint.x)
							* (viewDirection == LEFT ? -1 : 1) <= distance)
				return true;
			break;
		case d0: // Up
		case d180: // Down
			if (Math.abs(tank.x() - startPoint.x) <= tank.getWidth() / 2
					&& (tank.y() - startPoint.y)
							* (viewDirection == DOWN ? -1 : 1) <= distance)
				return true;
			break;
		}

		return false;
	}

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
		usePreloadedLevels = getType() <= 4;
		// 3 tanks on type 1, 6 - on type 4
		enemiesCount = getType() % 4 == 0 ? 6 : getType() % 4 + 2;
		enemyTanks = new TankShape[enemiesCount];

		// one bullet for each enemy
		enemyBullets = new Bullet[enemiesCount];
		// four bullets for player
		playerBullets = new Bullet[PLAYER_BULLETS_COUNT];

		enemyTankNumber = 0;
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
			if (enemiesKilled + enemiesOnBoard >= KILLS_TO_NEXT_LEVEL)
				return false;
			// chance for respawn 3 from 4
			if (r.nextInt(4) != 0 && respawn(tankNumber)) {
				enemiesOnBoard++;
				// chance for shooting after spawn:
				// 1 from 20 on the first level and
				// 1 from 11 on the last level
				int chance = 10 + 11 - getLevel();
				if (r.nextInt(chance) == 0) {
					isBornWithGun = true;
				} else return true;
			} else return false;
		}

		synchronized (lock) {
			RotationAngle moveDirection = getDirectionForMovement(enemyTanks[tankNumber]);
			if (isBornWithGun || checkForShot(enemyTanks[tankNumber])) {
				RotationAngle shotDirection = getDirectionForShoot(enemyTanks[tankNumber]);
				// if the movement and shooting direction is the same, the tank
				// fired on the move
				if (shotDirection == moveDirection && !isBornWithGun) {
					enemyTanks[tankNumber] = moveTank(enemyTanks[tankNumber],
							moveDirection, false);
				} else {
					enemyTanks[tankNumber] = moveTank(enemyTanks[tankNumber],
							shotDirection, true);
				}

				// delay before a shoot for the new respawned tank
				if (isBornWithGun) {
					sleep(ANIMATION_DELAY * 2);
				}
				fire(enemyTanks[tankNumber]);
			} else {
				enemyTanks[tankNumber] = moveTank(enemyTanks[tankNumber],
						moveDirection, false);
			}
		}

		return true;
	}

	/**
	 * Launching the game
	 */
	@Override
	public Game call() {
		super.init();

		if (!desirialized) {
			loadNewLevel();
		}

		// create timer for bullets
		ScheduledFuture<?> bulletSwarm = scheduledExecutors
				.scheduleWithFixedDelay(new Runnable() {
					@Override
					public void run() {
						if (getStatus() == Status.Running) {
							flightOfBullets();
						}
					}
				}, 0, ANIMATION_DELAY * 4, TimeUnit.MILLISECONDS);

		while (!isInterrupted() && getStatus() != Status.GameOver) {
			if (getStatus() == Status.Running && isStarted) {
				// deferred pause
				if (deferredPauseFlag) pause();

				if (getStatus() == Status.Running) {
					if (isDead) {
						// must be in the main thread
						loss(playerTank.x(), playerTank.y());
					} else if (enemiesKilled >= KILLS_TO_NEXT_LEVEL) {
						win();
					} else if (elapsedTime(getSpeed(true))) {
						int tryCount = enemiesCount;
						do {
							if (enemyTankNumber >= enemiesCount) {
								enemyTankNumber = 0;
							}
							tryCount--;
						} while (!aiTurn(enemyTankNumber++) && tryCount > 0);
					}
				}
			}
			// processing of key presses
			processKeys();
		}
		bulletSwarm.cancel(true);
		return getNextGame();
	}

	/**
	 * Collision check of one of the tanks with the {@code bullet}
	 * 
	 * @param bullet
	 *            checking bullet
	 * @return {@code true} if there is a collision
	 */
	private boolean checkCollisionWithTank(Bullet bullet, boolean isPlayerTank) {
		if (isPlayerTank) return checkTwoShapeCollision(playerTank, bullet);
		for (TankShape enemyTank : enemyTanks) {
			if (checkTwoShapeCollision(enemyTank, bullet)) return true;
		}
		return false;
	}

	/**
	 * Calculating the possibility of making a shot
	 * 
	 * @param tank
	 *            enemy tank
	 * @return {@code true} when can be make a shot
	 */
	private boolean checkForShot(TankShape tank) {
		if (tank == null) return false;

		// maximum value of distance at which can be make a shot
		final int minDistanceLimit = 4;
		final int maxDistanceLimit = 9;
		// step of increasing chance of a shot
		final float rate = 1 / (float) (minDistanceLimit + maxDistanceLimit);

		int horizontalDistance = tank.x() - playerTank.x();
		int verticalDistance = tank.y() - playerTank.y();

		int minDistance = Math.min(Math.abs(horizontalDistance),
				Math.abs(verticalDistance));
		int maxDistance = Math.max(Math.abs(horizontalDistance),
				Math.abs(verticalDistance));

		// chance of shot decreases with distance, but not less than zero
		float chance = Math.max(1 - (minDistance + maxDistance - 1) * rate, 0);
		// chance of shot increases when the player tank going closer
		if (horizontalDistance < 0 && playerTank.getDirection() == LEFT
				|| horizontalDistance > 0 && playerTank.getDirection() == RIGHT
				|| verticalDistance < 0 && playerTank.getDirection() == DOWN
				|| verticalDistance > 0 && playerTank.getDirection() == UP) {
			chance += rate;
		}

		RotationAngle shotDirection = getDirectionForShoot(tank);

		// chance decreases when ahead of a friendly tank
		if (isFrendlyTankAhead(tank, shotDirection == LEFT
				|| shotDirection == RIGHT ? horizontalDistance
				: verticalDistance, shotDirection)) {
			chance -= rate * 2;
		} else if (minDistance <= playerTank.getWidth() / 2) {
			chance += rate * 10;
		} else {
			// increase a chance to shoot at near obstacle
			int dist = distanceToObstacle(tank.getCoordinates(), shotDirection,
					eraseTank(getBoard(), tank));
			if (dist > 0 && dist <= 5) {
				chance += rate * (9 - dist);
			}
		}

		// chance from rate to 1 - rate
		if (chance > 1 - rate) {
			chance = 1 - rate;
		} else if (chance < rate) {
			chance = rate;
		}

		return r.nextFloat() <= chance;
	}

	/**
	 * Check collision of the {@code bullet} with each another bullet and
	 * destroy a collided bullet
	 * 
	 * @param bullet
	 *            checking bullet
	 * @return {@code true} if there is a collision
	 */
	private boolean destroyCollidedBullets(Bullet bullet) {
		boolean result = destroyCollidedBullets(bullet, enemyBullets);
		if (!result) {
			result = destroyCollidedBullets(bullet, playerBullets);
		}
		return result;
	}

	/**
	 * Remove destroyed enemy tank from the enemyTanks array and increase score
	 * 
	 * @param tankNumber
	 *            number of the removed tank
	 */
	private void destroyEnemyTank(int tankNumber) {
		GameSound.playEffect(Effects.hit_cell);

		if (enemyTanks[tankNumber] != null) {
			enemyTanks[tankNumber] = null;
		}

		enemiesKilled++;
		enemiesOnBoard--;
		setScore(getScore() + 1);
	}

	/**
	 * Fires a new bullet in front of the tank
	 * 
	 * @param tank
	 *            shooting tank
	 * @return {@code true} when successful shot, otherwise - {@code false}
	 */
	private boolean fire(TankShape tank) {
		if (tank == null) return false;

		boolean isPlayerBullet = tank == playerTank;
		Bullet[] bullets = isPlayerBullet ? playerBullets : enemyBullets;

		synchronized (lock) {
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

			Bullet bullet = new Bullet(bulletX, bulletY, tank.getDirection());
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i] == null) {
					if (!isInterrupted()) {
						bullets[i] = bullet;
						flightOfBullet(isPlayerBullet, i);
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Processing the flight of one bullet
	 * 
	 * @param isPlayerBullet
	 *            {@code true} when bullet fired by the player tank
	 * @param bulletIndex
	 *            index of a flying bullet
	 * @return {@code false} if an enemy bullet hit the player tank, or
	 *         {@code true} otherwise
	 */
	private boolean flightOfBullet(boolean isPlayerBullet, int bulletIndex) {
		synchronized (lock) {
			Bullet bullet = isPlayerBullet ? playerBullets[bulletIndex]
					: enemyBullets[bulletIndex];
			if (bullet == null) return true;
			// erase bullet from the board only if it has flew out of the
			// tank
			if (!checkCollisionWithTank(bullet, isPlayerBullet)) {
				setBoard(drawShape(getBoard(), bullet, Cell.Empty));
			}
			Board board = getBoard();
			// move bullet to the new position
			Bullet result = bullet.flight();

			if (checkBoardCollision(board, result, result.x(), result.y())) {
				// if the bullet has left the board
				destroyBullet(isPlayerBullet ? playerBullets : enemyBullets,
						bulletIndex);
			} else {
				// if the bullet hit something
				if (board.getCell(result.x(), result.y()) != Cell.Empty) {
					GameSound.playEffect(Effects.hit_cell);
					// bullet hit a simple obstacle?
					boolean isObstacle = true;

					// collision check with one of another bullets
					boolean collisionWithBullets;
					if (isPlayerBullet) {
						collisionWithBullets = destroyCollidedBullets(result,
								enemyBullets);
					} else {
						collisionWithBullets = destroyCollidedBullets(result);
					}

					if (!collisionWithBullets) {
						if (!isPlayerBullet
								&& checkTwoShapeCollision(playerTank, result)) {
							// when the enemy bullet hit the player tank
							isDead = true;
							return false;
						}
						for (int i = 0; i < enemyTanks.length; i++) {
							TankShape enemyTank = enemyTanks[i];
							if (checkTwoShapeCollision(enemyTank, result)) {
								if (isPlayerBullet) {
									// when the player bullet hit the enemy
									// tank
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
					destroyBullet(
							isPlayerBullet ? playerBullets : enemyBullets,
							bulletIndex);
				} else {
					board.setCell(result.getFill(), result.x(), result.y());
				}
			}
			setBoard(board);
		}
		return true;
	}

	/**
	 * Processing the flight of bullets
	 */
	void flightOfBullets() {
		for (int i = 0; i < enemyBullets.length; i++) {
			if (isInterrupted() || !flightOfBullet(false, i)) return;
		}
		for (int i = 0; i < playerBullets.length; i++) {
			if (isInterrupted() || !flightOfBullet(true, i)) return;
		}
	}

	/**
	 * Getting the optimal direction for the movement
	 * 
	 * @param tank
	 *            enemy tank
	 * @return movement direction
	 */
	private RotationAngle getDirectionForMovement(TankShape tank) {
		if (tank == null) return null;

		int x = tank.x();
		int y = tank.y();

		// weight for each direction,
		// to calculate the chance of movement in this direction
		float[] weightOfDirection = new float[RotationAngle.values().length];
		Arrays.fill(weightOfDirection, 0.25f);

		int horizontalDistance = x - playerTank.x();
		int verticalDistance = y - playerTank.y();

		boolean isHorizontalMinDistance = Math.abs(horizontalDistance) < Math
				.abs(verticalDistance);

		// defines the direction to get closer to the player tank vertically and
		// horizontally
		RotationAngle prefHorDirection = horizontalDistance < 0 ? RIGHT : LEFT;
		RotationAngle prefVertDirection = verticalDistance < 0 ? UP : DOWN;

		boolean[] blockedWays = new boolean[weightOfDirection.length];

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

			if (!isPossibleMove(tank, nearPoint, checkingDir)) {
				weightOfDirection[i] -= 0.50f;
				blockedWays[i] = true;
				if (!isPossibleMove(tank, farPoint, checkingDir)) {
					weightOfDirection[i] -= 0.25f;
				}
			} else {
				weightOfDirection[i] += 0.25f;
			}
		}
		// increases the weight of the current direction
		if (!blockedWays[tank.getDirection().ordinal()]) {
			weightOfDirection[tank.getDirection().ordinal()] += 0.25f;
		}

		// increases the weight of the preferred directions to 0.25
		// and weight of the direction for minimal distance to 0.5
		if (horizontalDistance != 0) {
			weightOfDirection[prefHorDirection.ordinal()] += isHorizontalMinDistance
					&& !blockedWays[prefHorDirection.ordinal()] ? 0.5f : 0.25f;
		}
		if (verticalDistance != 0) {
			weightOfDirection[prefVertDirection.ordinal()] += !isHorizontalMinDistance
					&& !blockedWays[prefVertDirection.ordinal()] ? 0.5f : 0.25f;
		}

		// decreases the weight of direction when it crosses the direction of
		// movement of the player tank
		if (playerTank.getDirection() == LEFT
				|| playerTank.getDirection() == RIGHT) {
			weightOfDirection[prefVertDirection.ordinal()] -= 0.25f;
		} else {
			weightOfDirection[prefHorDirection.ordinal()] -= 0.25f;
		}

		// Sorting the direction to descending order of their weights
		RotationAngle[] directions = descShakerSort(weightOfDirection);
		// roll the dice for each direction
		for (RotationAngle direction : directions) {
			float chance = weightOfDirection[direction.ordinal()];
			// chance from 0 to 0.95
			if (chance > 0.95f) {
				chance = 0.95f;
			} else if (chance < 0) {
				chance = 0;
			}

			if (r.nextFloat() <= chance) return direction;
		}
		// when nothing is selected, returns the direction with the highest
		// weight
		return directions[0];
	}

	/**
	 * Getting the optimal direction for the shooting
	 * 
	 * @param tank
	 *            enemy tank
	 * @return shooting direction
	 */
	private RotationAngle getDirectionForShoot(TankShape tank) {
		if (tank == null) return null;

		int x = tank.x();
		int y = tank.y();

		RotationAngle result;

		if (Math.abs(x - playerTank.x()) < Math.abs(y - playerTank.y())) {
			result = y - playerTank.y() > 0 ? DOWN : UP;
		} else {
			result = x - playerTank.x() > 0 ? LEFT : RIGHT;
		}

		return result;
	}

	@Override
	protected int getSpeedOfFirstLevel() {
		return 1500;
	}

	@Override
	protected int getSpeedOfTenthLevel() {
		return 350;// 240
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
							viewDirection, enemyTank)) return true;
		}
		return false;
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
		Board board = getBoard();

		TankShape checkingTank = tank.clone();

		// Erase the tank to not interfere with the checks
		board = eraseTank(board, tank);

		checkingTank.changeRotationAngle(direction);

		return !(checkBoardCollision(board, checkingTank, newPlace.x,
				newPlace.y) || checkCollision(board, checkingTank, newPlace.x,
				newPlace.y));
	}

	/**
	 * Loading or reloading the specified level
	 */
	@Override
	void loadNewLevel() {
		// reset isDead flag
		isDead = false;

		resetBullets();
		resetPlayerTank();
		resetEnemyTanks();

		synchronized (lock) {
			// draws the playerTank
			setBoard(drawTank(getBoard(), playerTank));

			if (usePreloadedLevels) {
				loadPreparedObstacles();
			} else {
				loadRandomObstacles();
			}
		}

		enemiesKilled = 0;
		enemiesOnBoard = 0;

		super.loadNewLevel();
	}

	/**
	 * Loading predefined obstacles
	 */
	private void loadPreparedObstacles() {
		if (getLevel() > 1) {
			setBoard(getPreparedObstacles(getBoard(),
					getTanksObstacles()[getLevel()]));
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
	 * Move the player tank to a new location from the movement direction
	 * 
	 * @param direction
	 *            movement direction
	 */
	private void movePlayerTank(RotationAngle direction) {
		playerTank = moveTank(playerTank, direction, false);
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
		if (tank == null) return null;

		TankShape newTank = tank.clone();
		synchronized (lock) {
			Board board = getBoard();

			// Erase the tank to not interfere with the checks
			board = eraseTank(board, newTank);

			if (rotationOnly || newTank.getRotationAngle() != direction) {
				newTank.changeRotationAngle(direction);
				if (checkCollision(board, newTank, newTank.x(), newTank.y())) {
					newTank.move(direction);
				}
			} else {
				newTank.move(direction);
			}

			if (checkBoardCollision(board, newTank, newTank.x(), newTank.y())
					|| checkCollision(board, newTank, newTank.x(), newTank.y()))
				return tank;

			// draw the playerTank on the new place
			setBoard(drawTank(board, newTank));
		}
		return newTank;
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;

		super.processKeys();

		if (getStatus() == Status.Running && !isInterrupted()) {
			RotationAngle movementDirection = null;
			KeyPressed key = null;

			if (containsKey(KeyPressed.KeyLeft)) {
				movementDirection = LEFT;
				key = KeyPressed.KeyLeft;
			} else if (containsKey(KeyPressed.KeyRight)) {
				movementDirection = RIGHT;
				key = KeyPressed.KeyRight;
			} else if (containsKey(KeyPressed.KeyUp)) {
				movementDirection = UP;
				key = KeyPressed.KeyUp;
			} else if (containsKey(KeyPressed.KeyDown)) {
				movementDirection = DOWN;
				key = KeyPressed.KeyDown;
			}

			if (movementDirection != null) {
				if (playerTank.getDirection() == movementDirection) {
					setKeyDelay(key, ANIMATION_DELAY * 2);
				} else {
					keys.remove(key);
				}
				movePlayerTank(movementDirection);
			}

			if (isStarted && containsKey(KeyPressed.KeyRotate)) {
				GameSound.playEffect(Effects.move);
				fire(playerTank);
				keys.remove(KeyPressed.KeyRotate);
			}
		}
	}

	/**
	 * Clearing the bullets array
	 */
	private void resetBullets() {
		Arrays.fill(enemyBullets, null);
		Arrays.fill(playerBullets, null);
	}

	/**
	 * Clearing the enemyTanks array
	 */
	private void resetEnemyTanks() {
		Arrays.fill(enemyTanks, null);
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
			spawnPoint = r.nextInt(spawnPoints.length);
			newX = spawnPoints[spawnPoint][0];
			newY = spawnPoints[spawnPoint][1];

			if (newX + newTank.minX() < 0) {
				newX -= newTank.minX();
			} else if (newX + newTank.maxX() >= boardWidth) {
				newX -= newTank.maxX();
			}
			if (newY + newTank.minY() < 0) {
				newY -= newTank.minY();
			} else if (newY + newTank.maxY() >= boardHeight) {
				newY -= newTank.maxY();
			}

			newTank.setX(newX);
			newTank.setY(newY);

			newTank.changeRotationAngle(getDirectionForShoot(newTank));
			tryCount--;
		} while (checkCollision(getBoard(), new TankShape(2), newX, newY)
				&& tryCount > 0);

		if (tryCount <= 0) return false;

		enemyTanks[tankNumber] = newTank;
		setBoard(drawTank(getBoard(), newTank));

		return true;
	}

}
