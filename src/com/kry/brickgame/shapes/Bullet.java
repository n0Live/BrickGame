package com.kry.brickgame.shapes;

import com.kry.brickgame.Board.Cell;

public class Bullet extends Shape {
	/**
	 * X-coordinate position
	 */
	public int x;
	/**
	 * Y-coordinate position
	 */
	public int y;

	public Bullet(int x, int y, RotationAngle direction) {
		super(1, direction, Cell.Full);
		this.setCoord(0, new int[] { 0, 0 });

		this.x = x;
		this.y = y;
	}

	/**
	 * Setting the coordinates of the new position
	 * 
	 * @param x
	 *            x-coordinate of the new position
	 * @param y
	 *            y-coordinate of the new position
	 */
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Setting the coordinates of the new position depending of the direction
	 * 
	 * @param direction
	 *            movement direction
	 */
	public Bullet flight() {
		switch (getRotationAngle()) {
		case d0:
			y++;
			break;
		case d90:
			x++;
			break;
		case d180:
			y--;
			break;
		case d270:
			x--;
			break;
		}
		return this;
	}

	/**
	 * Returns the movement direction of the Bullet
	 * 
	 * @return movement direction
	 */
	public RotationAngle getDirection() {
		return this.getRotationAngle();
	}

}
