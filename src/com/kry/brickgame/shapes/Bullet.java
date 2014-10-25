package com.kry.brickgame.shapes;

public class Bullet extends CoordinatedShape {
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] { { { 0, 0 } } };

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	public Bullet(int x, int y, RotationAngle direction) {
		super(0, charactersTable[0].length, x, y, direction);

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
		move(getDirection());
		return this;
	}

}
