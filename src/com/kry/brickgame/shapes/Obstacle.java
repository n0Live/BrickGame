package com.kry.brickgame.shapes;

import com.kry.brickgame.boards.Board.Cell;

import java.util.Random;

/**
 * @author noLive
 */
public class Obstacle extends CharacterShape {
	private static final long serialVersionUID = 3456812497392122162L;
	/**
	 * A set of the coordinates of points of the player character:
	 * [type][point][coordinate:0-x,1-y]
	 */
	private static int[][][] charactersTable = new int[][][] {//
	// 0 - square
			{ { 0, 0 } },
			// 1 - corner
			{ { 1, 0 }, { 0, 0 }, { 0, 1 } },
			// 2 - rectangle
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, }; //
	
	/**
	 * Get instance of a random obstacle
	 */
	public static Obstacle getRandomTypeInstance() {
		Random r = new Random();
		int x = r.nextInt(charactersTable.length - 1) + 1;
		return new Obstacle(x);
	}
	
	/**
	 * Get instance of a {@link #getRandomTypeInstance random obstacle} and a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #getRandomTypeInstance
	 * @see #setRandomRotate
	 */
	public static Obstacle getRandomShapeAndRotate() {
		return (Obstacle) getRandomTypeInstance().setRandomRotate();
	}
	
	/**
	 * Constructor of the Obstacle
	 * 
	 * @param type
	 *            type of the obstacle
	 */
	public Obstacle(int type) {
		super(type, charactersTable[type].length);
	}
	
	@Override
	public Obstacle changeRotationAngle(RotationAngle rotationAngle) {
		return setType(getType(), rotationAngle, getFill());
	}
	
	@Override
	protected int[][][] getCharactersTable() {
		return charactersTable;
	}
	
	/**
	 * Selection of the obstacle
	 * 
	 * @param type
	 *            type of the obstacle
	 * @param rotationAngle
	 *            rotation angle of the obstacle
	 * @param fill
	 *            type of fill of the obstacle
	 */
	@Override
	protected Obstacle setType(int type, RotationAngle rotationAngle, Cell fill) {
		super.setType(type, rotationAngle, fill);
		
		// sets the lower left corner to the coordinates [0, 0]
		while (minX() < 0) {
			for (int i = 0; i < getCharactersTable()[type].length; i++) {
				setX(i, x(i) + 1);
			}
		}
		while (minY() < 0) {
			for (int i = 0; i < getCharactersTable()[type].length; i++) {
				setY(i, y(i) + 1);
			}
		}
		
		return this;
	}
	
}
