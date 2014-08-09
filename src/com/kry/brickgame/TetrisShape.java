package com.kry.brickgame;

import java.util.Random;

public class TetrisShape extends Shape implements Cloneable {

	/**
	 * Number of the points of the figures ({@value} )
	 */
	private final static int LENGTH = 4;

	enum Tetrominoes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
	};

	/**
	 * Rotation angle of a figure (in degrees)
	 */
	enum RotationAngle {
		d0, d90, d180, d270;

		/**
		 * The next clockwise rotation angle
		 */
		public RotationAngle getLeft() {
			return this.ordinal() > 0 ? RotationAngle.values()[this.ordinal() - 1]
					: RotationAngle.values()[RotationAngle.values().length - 1];
		}

		/**
		 * The next counterclockwise rotation angle
		 */
		public RotationAngle getRight() {
			return this.ordinal() < RotationAngle.values().length - 1 ? RotationAngle
					.values()[this.ordinal() + 1] : RotationAngle.values()[0];
		}
	};

	private Tetrominoes shape;

	private RotationAngle rotationAngle;

	/**
	 * A set of coordinates of a points of a figures:
	 * [shapeIndex][pointIndex][coordinate:0-x,1-y]
	 */
	private final static int[][][] coordsTable = new int[][][] {
			{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // NoShape
			{ { -1, 0 }, { 0, -1 }, { 0, 0 }, { -1, 1 } }, // ZShape
			{ { -1, -1 }, { 0, 0 }, { -1, 0 }, { 0, 1 } }, // SShape
			{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, // LineShape
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // TShape
			{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, // SquareShape
			{ { 0, -1 }, { 1, 0 }, { 1, -1 }, { 1, 1 } }, // LShape
			{ { 0, -1 }, { 1, -1 }, { 0, 0 }, { 0, 1 } } }; // MirroredLShape

	public TetrisShape() {
		super(LENGTH);
		setShape(Tetrominoes.NoShape);
	}

	/**
	 * The copy constructor of a TetrisShape
	 * 
	 * @param aTetrisShape
	 *            a TetrisShape object for copying
	 */
	public TetrisShape(TetrisShape aTetrisShape) {
		super(LENGTH);
		setShape(aTetrisShape.shape, aTetrisShape.rotationAngle);
	}

	public TetrisShape clone() {
		TetrisShape newTetrisShape = new TetrisShape(this);
		return newTetrisShape;

	}

	/**
	 * Selection of a figure
	 * 
	 * @param shape
	 *            a figure
	 * @param rotationAngle
	 *            rotation angle of a figure
	 */
	public void setShape(Tetrominoes shape, RotationAngle rotationAngle) {
		for (int i = 0; i < LENGTH; ++i) {
			switch (rotationAngle) {
			case d0:
				setX(i, coordsTable[shape.ordinal()][i][0]);
				setY(i, coordsTable[shape.ordinal()][i][1]);
				break;
			case d90:
				setX(i, coordsTable[shape.ordinal()][i][1]);
				setY(i, -coordsTable[shape.ordinal()][i][0]);
				break;
			case d180:
				setX(i, -coordsTable[shape.ordinal()][i][0]);
				setY(i, -coordsTable[shape.ordinal()][i][1]);
				break;
			case d270:
				setX(i, -coordsTable[shape.ordinal()][i][1]);
				setY(i, coordsTable[shape.ordinal()][i][0]);
				break;
			}
		}

		// Center the figures, which were shifted to aside after rotation
		if ((shape != Tetrominoes.LineShape) // except LineShape
				&& ((minX() < -1) || (maxX() <= 0))) {
			for (int i = 0; i < LENGTH; ++i) {
				setX(i, x(i) + 1);
			}
		}
		if ((minY() < 0) || (maxY() <= 0)) {
			for (int i = 0; i < LENGTH; ++i) {
				setY(i, y(i) + 1);
			}
		}

		this.shape = shape;
		this.rotationAngle = rotationAngle;
	}

	/**
	 * Selection of a figure (without rotation)
	 * 
	 * @param shape
	 *            a figure
	 */
	public void setShape(Tetrominoes shape) {
		setShape(shape, RotationAngle.d0);
	}

	public Tetrominoes getShape() {
		return shape;
	}

	/**
	 * Selection of a random figure
	 */
	public void setRandomShape() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}

	/**
	 * Selection of a random rotation angle
	 */
	public void setRandomRotate() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 3 + 1;
		RotationAngle[] values = RotationAngle.values();
		setShape(getShape(), values[x]);
	}

	/**
	 * Selection of a {@link #setRandomShape random figure} and a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #setRandomShape
	 * @see #setRandomRotate
	 */
	public void setRandomShapeAndRotate() {
		setRandomShape();
		setRandomRotate();
	}

	/**
	 * Counterclockwise rotation of the figure
	 */
	public TetrisShape rotateLeft() {
		if (getShape() != Tetrominoes.SquareShape)
			setShape(getShape(), this.rotationAngle.getLeft());
		return this;
	}

	/**
	 * Clockwise rotation of the figure
	 */
	public TetrisShape rotateRight() {
		if (getShape() != Tetrominoes.SquareShape)
			setShape(getShape(), this.rotationAngle.getRight());
		return this;
	}

	@Override
	public String toString() {
		// the figure and its rotation angle
		return "TetrisShape [" + this.getShape() + ", " + this.rotationAngle
				// the lower left corner
				+ ", (" + minX() + ";" + minY() + ")]\n"
				+ super.toString();
	}

}
