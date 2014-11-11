package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class ArkanoidPlatformShape extends CharacterShape {
	private static final long serialVersionUID = -4308280159216035580L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] { //
	{ { 0, 0 } }, // 0 - platform1
			{ { 0, 0 }, { 1, 0 } }, // 1 - platform2
			{ { -1, 0 }, { 0, 0 }, { 1, 0 } }, // 2 - platform3
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // 3 - platform4
	}; //

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	/**
	 * Constructor for the Arkanoid Platform
	 * 
	 * @param type
	 *            <b>0</b> - 1-cell platform, <b>1</b> - 2-cell platform,
	 *            <b>2</b> - 3-cell platform, <b>3</b> - 4-cell platform
	 */
	public ArkanoidPlatformShape(int type) {
		super(type, charactersTable[type].length);
	}

}
