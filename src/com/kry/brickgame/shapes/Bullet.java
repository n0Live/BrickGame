package com.kry.brickgame.shapes;

/**
 * @author noLive
 */
public class Bullet extends CoordinatedShape {
	private static final long serialVersionUID = 8131536153963633602L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] { { { 0, 0 } } };
	
	/**
	 * Copy constructor of the bullet
	 * 
	 * @param aTank
	 *            a tank for copying
	 */
	public Bullet(Bullet aBullet) {
		super(aBullet);
	}
	
	/**
	 * Constructor for the Bullet
	 * 
	 * @param x
	 *            x-coordinate of the position
	 * @param y
	 *            y-coordinate of the position
	 * @param direction
	 *            direction of the movement
	 */
	public Bullet(int x, int y, RotationAngle direction) {
		super(0, charactersTable[0].length, x, y, direction);
		
		this.x = x;
		this.y = y;
	}
	
	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}
	
	@Override
	public Bullet clone() {
		Bullet cloned = (Bullet) super.clone();
		return cloned;
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
