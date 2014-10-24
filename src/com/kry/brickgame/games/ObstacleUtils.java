package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;

import java.util.Random;

import com.kry.brickgame.Board;
import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.Obstacle;

public class ObstacleUtils extends Game {

	private static class PlacedObstacle {
		public final Obstacle obstacle;
		public final int x;
		public final int y;

		public PlacedObstacle(Obstacle obstacle, int x, int y) {
			this.obstacle = obstacle;
			this.x = x;
			this.y = y;
		}
	}

	public static PlacedObstacle[][] snakeObstacles = new PlacedObstacle[][] {
			{ null },// 0
			{ null },// 1
			{// 2
					new PlacedObstacle(new Obstacle(1), 1, 3),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 8, 8) },
			{// 3
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(LEFT), 6, 3),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 0, 8) },
			{// 4
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(DOWN), 6, 15),
					new PlacedObstacle(new Obstacle(2), 3, 11) },
			{// 5
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(RIGHT), 1, 15),
					new PlacedObstacle(new Obstacle(2), 3, 6) },
			{// 6
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(LEFT), 6, 3),
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(RIGHT), 1, 15),
					new PlacedObstacle(new Obstacle(2), 3, 11),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 8, 8) },
			{// 7
					new PlacedObstacle(new Obstacle(1), 1, 3),
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(DOWN), 6, 15),
					new PlacedObstacle(new Obstacle(2), 3, 6),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 0, 8) },
			{// 8
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(RIGHT), 1, 15),
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(DOWN), 6, 15),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 0, 8),
					new PlacedObstacle(new Obstacle(2), 8, 8) },
			{// 9
					new PlacedObstacle(new Obstacle(1), 1, 3),
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(LEFT), 6, 3),
					new PlacedObstacle(new Obstacle(2), 3, 11),
					new PlacedObstacle(
							new Obstacle(2).changeRotationAngle(LEFT), 0, 8) },
			{// 10
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(LEFT), 6, 3),
					new PlacedObstacle(
							new Obstacle(1).changeRotationAngle(DOWN), 6, 15),
					new PlacedObstacle(new Obstacle(2), 3, 6),
					new PlacedObstacle(new Obstacle(2), 3, 11) }, };

	/**
	 * Creates an obstacles and places it on the board randomly
	 * 
	 * @param board
	 *            board for the addition an obstacles
	 * @param count
	 *            count of the adding obstacles
	 * @param leftBorderSpace
	 *            distance from the left border on which can be placed an
	 *            obstacles
	 * @param rightBorderSpace
	 *            distance from the righ border on which can be placed an
	 *            obstacles
	 * @param bottomBorderSpace
	 *            distance from the bottom border on which can be placed an
	 *            obstacles
	 * @param topBorderSpace
	 *            distance from the top border on which can be placed an
	 *            obstacles
	 * @return board with the obstacles
	 */
	public static Board getRandomObstacles(Board board, int count,
			int leftBorderSpace, int rightBorderSpace, int bottomBorderSpace,
			int topBorderSpace) {

		Board result = board.clone();

		// plus one random obstacle
		for (int i = 0; i < count; i++) {
			int k = 10; // maximum of attempts
			Board boardWithObstacle = result.clone();
			do {
				boardWithObstacle = generateObstacle(boardWithObstacle,
						new Random().nextInt(3), leftBorderSpace,
						rightBorderSpace, bottomBorderSpace, topBorderSpace);
				k--;
			} while (result.equals(boardWithObstacle)  && k > 0);
			result = boardWithObstacle;
		}
		return result;
	}

	/**
	 * Sets the prepared obstacles to the board
	 * 
	 * @param board
	 *            board for the addition the obstacles
	 * @param obstacles
	 *            array of the prepared obstacles
	 * @return board with the obstacles
	 */
	public static Board getPreparedObstacles(Board board,
			PlacedObstacle[] obstacles) {
		Board result = board.clone();
		for (int i = 0; i < obstacles.length; i++) {
			PlacedObstacle obstacle = obstacles[i];
			result = drawShape(result, obstacle.x, obstacle.y,
					obstacle.obstacle, Cell.Full);
		}
		return result;
	}

	/**
	 * Creates an obstacle and places it on the board randomly
	 * 
	 * @param board
	 *            board for the addition an obstacle
	 * @param type
	 *            type of the obstacle
	 * @param leftBorderSpace
	 *            distance from the left border on which can be placed an
	 *            obstacles
	 * @param rightBorderSpace
	 *            distance from the righ border on which can be placed an
	 *            obstacles
	 * @param bottomBorderSpace
	 *            distance from the bottom border on which can be placed an
	 *            obstacles
	 * @param topBorderSpace
	 *            distance from the top border on which can be placed an
	 *            obstacles
	 * @return board with the obstacle
	 */
	private static Board generateObstacle(Board board, int type,
			int leftBorderSpace, int rightBorderSpace, int bottomBorderSpace,
			int topBorderSpace) {
		Random r = new Random();
		int x, y;

		Obstacle obstacle = new Obstacle(type);

		// finds empty cells
		int k = 10; // maximum of attempts
		do {
			obstacle.setRandomRotate();

			x = r.nextInt(board.getWidth() - obstacle.getWidth()
					- (leftBorderSpace + rightBorderSpace))
					+ leftBorderSpace;
			y = r.nextInt(board.getHeight() - obstacle.getHeight()
					- (bottomBorderSpace + topBorderSpace))
					+ bottomBorderSpace;

			k--;
		} while (checkCollision(board, obstacle, x, y, true) && k > 0);

		if (k > 0) {
			return drawShape(board, x, y, obstacle, Cell.Full);
		} else
			return board;
	}

}
