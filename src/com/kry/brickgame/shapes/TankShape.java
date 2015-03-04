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
	        { { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
	        // 1 - enemy tank
	        { { -1, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
	        // 2 - tank in box (for check)
	        { { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { -1, 1 }, { 0, 1 },
	                { 1, 1 } } }; //
	
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
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, 1 - enemy tank, 2 - tank in the box (for
	 *            check)
	 * @param x
	 *            x-coordinate of the position
	 * @param y
	 *            y-coordinate of the position
	 */
	public TankShape(int type, int x, int y) {
		super(type, charactersTable[type].length, x, y, RotationAngle.d0);
	}
	
	/**
	 * Constructor for the Tank
	 * 
	 * @param type
	 *            0 - player's tank, 1 - enemy tank, 2 - tank in the box (for
	 *            check)
	 * @param x
	 *            x-coordinate of the position
	 * @param y
	 *            y-coordinate of the position
	 * @param direction
	 *            direction of the movement
	 */
	public TankShape(int type, int x, int y, RotationAngle direction) {
		super(type, charactersTable[type].length, x, y, direction);
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
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}
	
	@Override
	public TankShape clone() {
		TankShape cloned = (TankShape) super.clone();
		return cloned;
	}
	
}
