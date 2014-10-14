package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class CarShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
		{ { -1, -1 }, { 1, -1 }, { 0, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 },
				{ 0, 2 } }, // car
		}; //
	}

	/**
	 * Constructor for the Car
	 */
	public CarShape() {
		super(0);
	}

}
