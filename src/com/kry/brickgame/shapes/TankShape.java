package com.kry.brickgame.shapes;

public class TankShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
				{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 },
						{ 1, 0 }, { 0, 1 } }, // 0 - tank
				{ { -1, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 },
						{ 0, 1 } }, // 1 - enemy tank
		}; //
	}
	private int coordX;
	private int coordY;

	/**
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, enemy tank
	 */
	public TankShape(int type) {
		super(type);

		setX(0 - minX());
		setY(0 - minY());
	}

	/**
	 * Copy constructor of the tank
	 * 
	 * @param aTank
	 *            a tank for copying
	 */
	public TankShape(TankShape aTank) {
		super(aTank);

		setX(aTank.x());
		setY(aTank.y());
	}

	public TankShape clone() {
		TankShape newTank = new TankShape(this);
		return newTank;
	}

	/**
	 * Setting the coordinates of the new position depending of the direction
	 * 
	 * @param direction
	 *            movement direction
	 */
	public void move(RotationAngle direction) {
		switch (direction) {
		case d0:
			setY(y() + 1);
			break;
		case d90:
			setX(x() + 1);
			break;
		case d180:
			setY(y() - 1);
			break;
		case d270:
			setX(x() - 1);
			break;
		}
	}

	/**
	 * Returns the movement direction of the Tank
	 * 
	 * @return movement direction
	 */
	public RotationAngle getDirection() {
		return this.getRotationAngle();
	}

	/**
	 * X-coordinate position on the board
	 */
	public int x() {
		return coordX;
	}

	/**
	 * Setting the x-coordinate position on the board
	 * 
	 * @param coordX
	 *            - the x-coordinate
	 */
	public void setX(int coordX) {
		this.coordX = coordX;
	}

	/**
	 * Y-coordinate position on the board
	 */
	public int y() {
		return coordY;
	}

	/**
	 * Setting the y-coordinate position on the board
	 * 
	 * @param coordY
	 *            - the y-coordinate
	 */
	public void setY(int coordY) {
		this.coordY = coordY;
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "[" + this.x() + ", " + this.y() + "]\n" + super.toString();
	}

}
