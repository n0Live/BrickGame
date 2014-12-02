package com.kry.brickgame.shapes;

/**
 * @author noLive
 */
public class TankShape extends CoordinatedShape {
	private static final long serialVersionUID = 3770507302625800510L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
	// 0 - tank
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 },
					{ 0, 1 } },
			// 1 - enemy tank
			{ { -1, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
			// 2 - tank in box (for check)
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 },
					{ -1, 1 }, { 0, 1 }, { 1, 1 } } }; //
	
	/**
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, 1 - enemy tank, 2 - tank in the box (for
	 *            check)
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
	
	@Override
	public TankShape clone() {
		TankShape newTank = new TankShape(this);
		return newTank;
	}
	
	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}
	
}
