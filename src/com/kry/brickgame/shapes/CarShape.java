package com.kry.brickgame.shapes;

/**
 * @author noLive
 */
public class CarShape extends CharacterShape {
	private static final long serialVersionUID = -2252007263544922105L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static final int[][][] charactersTable = new int[][][] {//
	{ { -1, -1 }, { 1, -1 }, { 0, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 0, 2 } }, // car
	}; //
	
	/**
	 * Constructor for the Car
	 */
	public CarShape() {
		super(0, charactersTable[0].length);
	}
	
	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}
	
}
