package com.kry.brickgame.shapes;

public class DancerShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
		{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 },
				{ 1, 1 } } // dance
		}; //
	}

	public DancerShape() {
		super(0);
	}

}
