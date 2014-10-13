package com.kry.brickgame.shapes;

import java.util.Random;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public class Obstacle extends ÑharacterShape {

	static {
		charactersTable = new int[][][] {//
		// 0 - square
				{ { 0, 0 } },
				// 1 - corner
				{ { 1, 0 }, { 0, 0 }, { 0, 1 } },
				// 2 - rectangle
				{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, }; //
	}

	/**
	 * Constructor of the Obstacle
	 * 
	 * @param type
	 *            type of the obstacle
	 */
	public Obstacle(int type) {
		super(type);
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
	@Override
	protected Obstacle setType(int type, RotationAngle rotationAngle, Cell fill) {
		super.setType(type, rotationAngle, fill);

		// sets the lower left corner to the coordinates [0, 0]
		while (minX() < 0) {
			for (int i = 0; i < charactersTable[type].length; i++) {
				setX(i, x(i) + 1);
			}
		}
		while (minY() < 0) {
			for (int i = 0; i < charactersTable[type].length; i++) {
				setY(i, y(i) + 1);
			}
		}

		return this;
	}

	public Obstacle changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}

	/**
	 * Get instance of a random obstacle
	 */
	public static Obstacle getRandomTypeInstance() {
		Random r = new Random();
		int x = r.nextInt(charactersTable.length - 1) + 1;
		return new Obstacle(x);
	}

	/**
	 * Get instance of a {@link #setRandomShape random obstacle} and a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #setRandomShape
	 * @see #setRandomRotate
	 */
	public static Obstacle setRandomShapeAndRotate() {
		return (Obstacle) getRandomTypeInstance().setRandomRotate();
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
