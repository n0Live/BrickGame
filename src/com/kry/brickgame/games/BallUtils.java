package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.drawShape;

import java.awt.Point;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.shapes.Shape;
import com.kry.brickgame.shapes.Shape.RotationAngle;

/**
 * @author noLive
 */
public enum BallUtils {
	;
	
	/**
	 * Draw the ball to a new position and erase it from the old position
	 * 
	 * @param board
	 *            the board for drawing
	 * @param ball
	 *            the ball
	 * @param oldX
	 *            x-coordinate of the old position
	 * @param oldY
	 *            y-coordinate of the old position
	 * @param newX
	 *            x-coordinate of the new position
	 * @param newY
	 *            y-coordinate of the new position
	 * @return the board after drawing the ball
	 */
	static Board drawBall(Board board, Shape ball, int oldX, int oldY, int newX, int newY) {
		// Create a temporary board, a copy of the basic board
		Board newBoard;
		
		// Erase the ball and draw on the new place
		newBoard = drawShape(board, oldX, oldY, ball, Cell.Empty);
		newBoard = drawShape(newBoard, newX, newY, ball, ball.getFill());
		
		return newBoard;
	}
	
	/**
	 * Return the new ball object - one cell shape
	 * 
	 * @param fill
	 *            type of fill of the ball
	 * @return the new ball
	 */
	static Shape getBall(Cell fill) {
		Shape ball = new Shape(1);
		ball.setCoord(0, new int[] { 0, 0 });
		ball.setFill(fill);
		
		return ball;
	}
	
	/**
	 * Calculating the new coordinates of the ball after movement in specified
	 * direction
	 * 
	 * @param x
	 *            x-coordinate of the old position
	 * @param y
	 *            y-coordinate of the old position
	 * @param horizontalDirection
	 *            the horizontal direction of the ball
	 * @param verticalDirection
	 *            the vertical direction of the ball
	 * @return the new coordinates of the ball
	 */
	static Point moveBall(int x, int y, RotationAngle horizontalDirection,
	        RotationAngle verticalDirection) {
		Point newCoords = new Point(x, y);
		
		if (horizontalDirection == RotationAngle.d90) {
			newCoords.x++;
		} else if (horizontalDirection == RotationAngle.d270) {
			newCoords.x--;
		}
		
		if (verticalDirection == RotationAngle.d0) {
			newCoords.y++;
		} else if (verticalDirection == RotationAngle.d180) {
			newCoords.y--;
		}
		
		return newCoords;
	}
	
}
