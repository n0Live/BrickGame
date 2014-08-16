package com.kry.brickgame;

import java.util.ArrayList;

import com.kry.brickgame.Shape.RotationAngle;

public class SnakeShape {
	/**
	 * Initial size of the snake
	 */
	private final static int INITIAL_LENGTH = 2;

	/**
	 * Current size of the snake
	 */
	private int length;

	/**
	 * Direction of movement of the snake
	 */
	private RotationAngle direction;

	/**
	 * A set of coordinates of a points of a figures:
	 * [index][coordinate:0-x,1-y]
	 */
	private ArrayList<int[]> sections = new ArrayList<int[]>();

	public SnakeShape() {
		this.length = INITIAL_LENGTH;
		this.direction = RotationAngle.d270;

		// Initialization of the snake
		for (int i = 0; i < length; i++) {
			sections.add(new int[] { i, 0 });
		}
	}

	protected int[][] getCoords() {
		int[][] result = new int[sections.size()][2];

		for (int i = 0; i < sections.size(); i++) {
			for (int j = 0; j < 2; j++) {
				result[i][j] = sections.get(i)[j];
			}
		}
		return result;
	}
	
	public SnakeShape rotateLeft() {
		this.direction.getLeft();
		return this;
	}
	
	public SnakeShape rotateRight() {
		this.direction.getRight();
		return this;
	}


}
