package com.kry.brickgame.shapes;

public class TankShape extends CharacterShape {

	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
	// 0 - tank
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 },
					{ 0, 1 } },
			// 1 - enemy tank
			{ { -1, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, }; //

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	private int x;
	private int y;

	private Bullet[] bullets;

	/**
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, enemy tank
	 */
	public TankShape(int type) {
		super(type, charactersTable[type].length);

		setX(0 - minX());
		setY(0 - minY());

		this.bullets = new Bullet[(type == 0) ? 4 : 1];
		for (int i = 0; i < this.bullets.length; i++) {
			this.bullets[i] = null;
		}
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

		this.bullets = aTank.getBullets();
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
		return x;
	}

	/**
	 * Setting the x-coordinate position on the board
	 * 
	 * @param x
	 *            - the x-coordinate
	 */
	public void setX(int coordX) {
		this.x = coordX;
	}

	/**
	 * Y-coordinate position on the board
	 */
	public int y() {
		return y;
	}

	/**
	 * Setting the y-coordinate position on the board
	 * 
	 * @param y
	 *            - the y-coordinate
	 */
	public void setY(int coordY) {
		this.y = coordY;
	}

	public Bullet[] getBullets() {
		return bullets;
	}

	/**
	 * Create a new bullet in front of the tank
	 * 
	 * @return new bullet if it created, otherwise - {@code null}
	 */
	public Bullet fire() {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i] == null) {
				int bulletX = x;
				int bulletY = y;

				if (getDirection() == RotationAngle.d0)
					bulletY += 2;
				else if (getDirection() == RotationAngle.d90)
					bulletX += 2;
				else if (getDirection() == RotationAngle.d180)
					bulletY -= 2;
				else if (getDirection() == RotationAngle.d270)
					bulletX -= 2;

				bullets[i] = new Bullet(bulletX, bulletY, getDirection());
				return bullets[i];
			}
		}
		return null;
	}

	/**
	 * Destroy the bullet (change to {@code null})
	 * 
	 * @param bullet
	 *            bullet for the destroying
	 */
	public void destroyBullet(Bullet bullet) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet checkBullet = bullets[i];
			if (bullet == checkBullet) {
				bullets[i] = null;
				return;
			}
		}
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "[" + this.x() + ", " + this.y() + "]\n" + super.toString();
	}

}
