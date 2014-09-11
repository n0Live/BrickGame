package com.kry.brickgame.shapes;

import java.util.Random;

import com.kry.brickgame.Board.Cell;

public class ÑharacterShape extends Shape {

	public static enum Ñharacters {
		Platform, Gun, Car, DanceShape
	};

	/**
	 * Type of the character
	 */
	private Ñharacters type;

	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private final static int[][][] charactersTable = new int[][][] {
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // platform
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // gun
			{ { 0, 0 }, { 2, 0 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 },
					{ 1, 3 } }, // car
			{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 },
					{ 1, 1 } } // dance
	}; //

	/**
	 * Constructor of the player character
	 * 
	 * @param type
	 *            type of the character
	 */
	public ÑharacterShape(Ñharacters type) {
		super(charactersTable[type.ordinal()].length);
		this.type = type;
		setType(type, RotationAngle.d0, Cell.Full);
	}

	/**
	 * Selection of the player character
	 * 
	 * @param type
	 *            type of the character
	 * @param rotationAngle
	 *            rotation angle of the character
	 * @param fill
	 *            type of fill of the character
	 */
	private ÑharacterShape setType(Ñharacters type, RotationAngle rotationAngle,
			Cell fill) {
		for (int i = 0; i < charactersTable[type.ordinal()].length; i++) {
			setX(i, charactersTable[type.ordinal()][i][0]);
			setY(i, charactersTable[type.ordinal()][i][1]);
		}

		switch (rotationAngle) {
		case d90:
			rotateRight();
			break;
		case d270:
			rotateLeft();
			break;
		case d180:
			rotateRight();
			rotateRight();
			break;
		default:
			break;
		}

		this.type = type;
		this.setRotationAngle(rotationAngle);
		this.setFill(fill);

		return this;
	}

	/**
	 * @return type of the character
	 */
	public Ñharacters getType() {
		return type;
	}

	public ÑharacterShape changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}

	/**
	 * Selection of a random rotation angle
	 */
	public ÑharacterShape setRandomRotate() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 4;
		RotationAngle[] values = RotationAngle.values();
		return setType(getType(), values[x], getFill());
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "ÑharacterShape [" + this.getType() + ", "
				+ this.getRotationAngle()
				// width and height
				+ ", width:" + getWidth() + ", height:" + getHeight() + "]\n"
				+ super.toString();
	}

}
