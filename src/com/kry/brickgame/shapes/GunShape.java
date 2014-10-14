package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class GunShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
		{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // gun
		}; //
	}

	/**
	 * Constructor for the Gun
	 */
	public GunShape() {
		super(0);
	}

}
