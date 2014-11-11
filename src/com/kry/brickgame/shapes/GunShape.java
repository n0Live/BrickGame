package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class GunShape extends CharacterShape {
	private static final long serialVersionUID = -3399573299437538347L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
	{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // gun
	}; //

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	/**
	 * Constructor for the Gun
	 */
	public GunShape() {
		super(0, charactersTable[0].length);
	}

}
