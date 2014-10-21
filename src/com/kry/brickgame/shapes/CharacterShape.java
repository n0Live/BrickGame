package com.kry.brickgame.shapes;

import java.util.Random;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public abstract class CharacterShape extends Shape {

	/**
	 * Type of the character
	 */
	protected int type;

	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private int[][][] charactersTable;
	
	protected int[][][] getCharactersTable(){
		return charactersTable;
	}

	/**
	 * Constructor of the player character
	 * 
	 * @param type
	 *            type of the character
	 * @param length
	 *            maximal number of the points of the figure
	 */
	public CharacterShape(int type, int length) {
		super(length);
		this.type = type;
		setType(type, RotationAngle.d0, Cell.Full);
	}

	/**
	 * Copy constructor of the player character
	 * 
	 * @param aShape
	 *            a shape for copying
	 */
	public CharacterShape(CharacterShape aShape) {
		super(aShape);
		this.type = aShape.getType();
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
	protected CharacterShape setType(int type, RotationAngle rotationAngle,
			Cell fill) {
		for (int i = 0; i < getCharactersTable()[type].length; i++) {
			setX(i, getCharactersTable()[type][i][0]);
			setY(i, getCharactersTable()[type][i][1]);
		}

		switch (rotationAngle) {
		case d90:
			rotateRight();
			break;
		case d270:
			rotateLeft();
			break;
		case d180:
			turnAround();
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
	public int getType() {
		return type;
	}

	public CharacterShape changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}

	/**
	 * Selection of a random rotation angle
	 */
	public CharacterShape setRandomRotate() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 4;
		RotationAngle[] values = RotationAngle.values();
		return setType(getType(), values[x], getFill());
	}

	@Override
	public String toString() {
		// the type and rotation angle
		return "CharacterShape [" + this.getType() + ", "
				+ this.getRotationAngle()
				// width and height
				+ ", width:" + getWidth() + ", height:" + getHeight() + "]\n"
				+ super.toString();
	}

}
