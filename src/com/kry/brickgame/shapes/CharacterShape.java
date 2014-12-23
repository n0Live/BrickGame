package com.kry.brickgame.shapes;

import java.util.Random;

import com.kry.brickgame.boards.Board.Cell;

/**
 * @author noLive
 */
public abstract class CharacterShape extends Shape {
	private static final long serialVersionUID = -3023350297252336349L;
	
	/**
	 * Type of the character
	 */
	protected int type;
	
	/**
	 * Copy constructor of the player character
	 * 
	 * @param aShape
	 *            a shape for copying
	 */
	public CharacterShape(CharacterShape aShape) {
		super(aShape);
		type = aShape.getType();
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
	
	public CharacterShape changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}
	
	abstract protected int[][][] getCharactersTable();
	
	/**
	 * @return type of the character
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Selection of a random rotation angle
	 */
	public CharacterShape setRandomRotate() {
		Random r = new Random();
		RotationAngle[] values = RotationAngle.values();
		int x = r.nextInt(values.length);
		return setType(getType(), values[x], getFill());
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
	protected CharacterShape setType(int type, RotationAngle rotationAngle, Cell fill) {
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
		setRotationAngle(rotationAngle);
		setFill(fill);
		
		return this;
	}
	
	@Override
	public String toString() {
		// the type and rotation angle
		return "CharacterShape [" + getType() + ", " + getRotationAngle()
		// width and height
				+ ", width:" + getWidth() + ", height:" + getHeight() + "]\n" + super.toString();
	}
	
}
