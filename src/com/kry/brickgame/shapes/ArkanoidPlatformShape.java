package com.kry.brickgame.shapes;

public class ArkanoidPlatformShape extends ÑharacterShape {

	static {
		charactersTable = new int[][][] { { { 0, 0 } }, // platform1
				{ { 0, 0 }, { 1, 0 } }, // platform2
				{ { -1, 0 }, { 0, 0 }, { 1, 0 } }, // platform3
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // platform4
		}; //
	}

	public ArkanoidPlatformShape(int type) {
		super(type);
	}

}
