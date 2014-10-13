package com.kry.brickgame.shapes;

public class TankShape extends CharacterShape {

	static {
		charactersTable = new int[][][] {//
				{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 },
						{ 1, 0 }, { 0, 1 } }, // 0 - tank
				{ { -1, -1 }, { 1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 },
						{ 0, 1 } }, // 1 - enemy tank
		}; //
	}
	private int coordX;
	private int coordY;
	
	public TankShape(int type) {
		super(type);
	}

	/**
	 * X-coordinate position on the board
	 */
	public int x() {
		return coordX;
	}

	public void setX(int coordX) {
		this.coordX = coordX;
	}

	/**
	 * Y-coordinate position on the board
	 */
	public int y() {
		return coordY;
	}

	public void setY(int coordY) {
		this.coordY = coordY;
	}

}
