package com.kry.brickgame.shapes;


public class TankShape extends CoordinatedShape {

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

	/**
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, enemy tank
	 */
	public TankShape(int type) {
		super(type, charactersTable[type].length);
	}

	/**
	 * Copy constructor of the tank
	 * 
	 * @param aTank
	 *            a tank for copying
	 */
	public TankShape(TankShape aTank) {
		super(aTank);
	}

	public TankShape clone() {
		TankShape newTank = new TankShape(this);
		return newTank;
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "TankShape [" + this.x() + ", " + this.y() + "]\n" + super.toString();
	}

}
