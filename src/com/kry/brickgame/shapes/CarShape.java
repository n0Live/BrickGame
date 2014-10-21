package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class CarShape extends CharacterShape {
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
	{ { -1, -1 }, { 1, -1 }, { 0, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 0, 2 } }, // car
	}; //

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	/**
	 * Constructor for the Car
	 */
	public CarShape() {
		super(0, charactersTable[0].length);
	}

}
