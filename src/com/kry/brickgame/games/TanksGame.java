package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkBoardCollision;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;

import java.awt.Point;
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
	 * Controlled-by-the-player playerTank
	 */
	private TankShape playerTank;

	private TankShape enemyTanks[];

	private int enemiesCount;

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

		playerTank = new TankShape(0);

		// ==define the parameters of the types of game==
		// 3 tanks on type 1, 6 - on type 4
		if (getType() % 4 == 0)
			enemiesCount = 6;
		else
			enemiesCount = getType() % 4 + 2;
		enemyTanks = new TankShape[enemiesCount];
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
			if (enemyTankNumber >= enemiesCount)
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

		// draws the playerTank
		drawTank(getBoard(), playerTank);

		setStatus(Status.Running);
	}

	/**
	 * Setting the coordinates of the start point of the Player Tank
	 */
	private void initPlayerTank() {
		playerTank.setX(boardWidth / 2 - 1);
		playerTank.setY(boardHeight / 2 - playerTank.getHeight());
	}

	/**
	 * Nulling the enemyTanks' array
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

			if (checkForShot(enemyTank)) {
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

	/**
	 * Create a new enemy tank
	 * 
	 * @param tankNumber
	 *            number of tank for creation
	 * @return {@code true} if creation is success
	 */
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

		System.out.println(directions[0]);
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
			chance += rate * 2;

		if (chance > 0.99)
			chance = 0.99;
		else if (chance < 0.01)
			chance = 0.01;

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

		for (int i = 0; i < enemiesCount; i++) {
			if (enemyTanks[i] != null
					&& enemyTanks[i] != tank
					&& isTankAhead(new Point(tank.x(), tank.y()), distance,
							viewDirection, enemyTanks[i]))
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
