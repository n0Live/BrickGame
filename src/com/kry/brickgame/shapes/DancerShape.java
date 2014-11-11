package com.kry.brickgame.shapes;

/**
 * @author noLive
 * 
 */
public class DancerShape extends CharacterShape {
	private static final long serialVersionUID = 8546393398653335390L;

	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 },
					{ 1, 1 } }, // up-down
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 } } // left-right
	}; //

	public final static int height;
	public final static int minY;
	public final static int maxY;

	static {
		int cMinY = charactersTable[0][0][1];
		int cMaxY = charactersTable[0][0][1];
		for (int i = 1; i < charactersTable[0].length; i++) {
			cMinY = Math.min(cMinY, charactersTable[0][i][1]);
			cMaxY = Math.max(cMaxY, charactersTable[0][i][1]);
		}
		minY = cMinY;
		maxY = cMaxY;
		height = maxY - minY + 1;
	};

	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}

	/**
	 * Constructor for the Dancer
	 */
	public DancerShape(RotationAngle rotation) {
		// type 0 for up-down rotation, type 1 - for left-right
		this(rotation == RotationAngle.d0 || rotation == RotationAngle.d180 ? 0
				: 1);

		changeRotationAngle(rotation);
	}

	private DancerShape(int type) {
		super(type, charactersTable[type].length);
	}

}
