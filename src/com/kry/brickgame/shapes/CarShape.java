package com.kry.brickgame.shapes;

public class CarShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
		{ { -1, -1 }, { 1, -1 }, { 0, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 },
				{ 0, 2 } }, // car
		}; //
	}

	public CarShape() {
		super(0);
	}

}
