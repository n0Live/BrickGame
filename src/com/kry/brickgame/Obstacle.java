package com.kry.brickgame;

import java.util.Random;

import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.Shape.RotationAngle;

/**
 * @author noLive
 * 
 */
public class Obstacle extends Shape {

	/**
	 * Type of the obstacle
	 */
	private int type;

	/**
	 * A set of the coordinates of points of the obstacles:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private final static int[][][] obstaclesTable = new int[][][] {
			{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // Type0
			{ { 1, 0 }, { 0, 0 }, { 0, 1 } }, // Type1
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, // Type2
	}; //

	/**
	 * Constructor of the Obstacle
	 * 
	 * @param type
	 *            type of the obstacle
	 */
	public Obstacle(int type) {
		super(obstaclesTable[type].length);
		this.type = type;
		setType(type);
	}

	/**
	 * Selection of the obstacle
	 * 
	 * @param type
	 *            type of the obstacle
	 * @param rotationAngle
	 *            rotation angle of the obstacle
	 * @param fill
	 *            type of fill of the obstacle
	 */
	public Obstacle setType(int type, RotationAngle rotationAngle, Cell fill) {
		for (int i = 0; i < obstaclesTable[type].length; i++) {
			setX(i, obstaclesTable[type][i][0]);
			setY(i, obstaclesTable[type][i][1]);
		}

		switch (rotationAngle) {
		case d90:
			rotateRight();
			break;
		case d270:
			rotateLeft();
			break;
		case d180:
			rotateRight();
			rotateRight();
			break;
		default:
			break;
		}

		// sets the lower left corner to the coordinates [0, 0]
		while (minX() < 0) {
			for (int i = 0; i < obstaclesTable[type].length; i++) {
				setX(i, x(i) + 1);
			}
		}
		while (minY() < 0) {
			for (int i = 0; i < obstaclesTable[type].length; i++) {
				setY(i, y(i) + 1);
			}
		}

		this.type = type;
		this.setRotationAngle(rotationAngle);
		this.setFill(fill);

		return this;
	}

	/**
	 * Selection of the obstacle (with type of fill is {@code Full})
	 * 
	 * @param type
	 *            type of the obstacle
	 * @param rotationAngle
	 *            rotation angle of the obstacle
	 */
	public Obstacle setType(int type, RotationAngle rotationAngle) {
		return setType(type, rotationAngle, Cell.Full);
	}

	/**
	 * Selection of the obstacle (without rotation and type of fill is
	 * {@code Full})
	 * 
	 * @param type
	 *            type of the obstacle
	 */
	public Obstacle setType(int type) {
		return setType(type, RotationAngle.d0, Cell.Full);
	}

	/**
	 * @return type of the obstacle
	 */
	public int getType() {
		return type;
	}

	public Obstacle changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}

	/**
	 * Selection of a random obstacle
	 */
	public Obstacle setRandomType() {
		Random r = new Random();
		int x = r.nextInt(obstaclesTable.length - 1) + 1;
		return setType(x);
	}

	/**
	 * Selection of a random rotation angle
	 */
	public Obstacle setRandomRotate() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 4;
		RotationAngle[] values = RotationAngle.values();
		return setType(getType(), values[x]);
	}

	/**
	 * Selection of a {@link #setRandomShape random obstacle} and a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #setRandomShape
	 * @see #setRandomRotate
	 */
	public Obstacle setRandomShapeAndRotate() {
		return setRandomType().setRandomRotate();
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "Obstacle [" + this.getType() + ", " + this.getRotationAngle()
				// width and height
				+ ", width:" + getWidth() + ", height:" + getHeight() + "]\n"
				+ super.toString();
	}

}
