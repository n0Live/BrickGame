package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class DancerShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
		{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 },
				{ 1, 1 } } // dance
		}; //
	}

	/**
	 * Constructor for the Dancer
	 */
	public DancerShape() {
		super(0);
	}

}
