package com.kry.brickgame.shapes;

import java.util.Random;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public class TetrisShape extends Shape implements Cloneable {

	/**
	 * Maximal number of the points of the figures ({@value} )
	 */
	private final static int MAX_LENGTH = 5;

	public static enum Tetrominoes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape, //
		SuperPoint, SuperGun, SuperMudGun, SuperBomb, SuperLine
	};

	private Tetrominoes shape;

	/**
	 * A set of coordinates of a points of the figures:
	 * [shapeIndex][pointIndex][coordinate:0-x,1-y]
	 */
	private final static int[][][] coordsTable = new int[][][] { { { 0, 0 } }, // NoShape
			{ { -1, 0 }, { 0, -1 }, { 0, 0 }, { -1, 1 } }, // ZShape
			{ { -1, -1 }, { 0, 0 }, { -1, 0 }, { 0, 1 } }, // SShape
			{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, // LineShape
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // TShape
			{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, // SquareShape
			{ { 0, -1 }, { 1, 0 }, { 1, -1 }, { 1, 1 } }, // LShape
			{ { 0, -1 }, { 1, -1 }, { 0, 0 }, { 0, 1 } }, // MirroredLShape
			{ { 0, 0 } }, // SuperPoint
			{ { 0, 0 }, { 0, 1 } }, // SuperGun
			{ { 0, 0 }, { 0, 1 }, { 0, 2 } }, // SuperMudGun
			{ { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } } // SuperBomb
	};

	/**
	 * Keeps fill type of the board cells on which the figure is located
	 */
	private Cell[] boardFill;

	public TetrisShape() {
		super(MAX_LENGTH);
		boardFill = new Cell[MAX_LENGTH];
		setShape(Tetrominoes.NoShape);
	}

	/**
	 * The copy constructor of a TetrisShape
	 * 
	 * @param aTetrisShape
	 *            a TetrisShape object for copying
	 */
	public TetrisShape(TetrisShape aTetrisShape) {
		this();
		setShape(aTetrisShape.shape, aTetrisShape.getRotationAngle(),
				aTetrisShape.getFill());
		setBoardFill(aTetrisShape.getBoardFill());
	}

	public TetrisShape clone() {
		TetrisShape newTetrisShape = new TetrisShape(this);
		return newTetrisShape;

	}

	/**
	 * Selection of the figure
	 * 
	 * @param shape
	 *            the figure
	 * @param rotationAngle
	 *            rotation angle of the figure
	 * @param fill
	 *            type of fill of the figure
	 */
	public TetrisShape setShape(Tetrominoes shape, RotationAngle rotationAngle,
			Cell fill) {
		for (int i = 0; i < getLength(shape); i++) {
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
	
		setLength(getLength(shape));
		
		// not for super shape
		if (shape.ordinal() <= 7) {
			// Center the figures, which were shifted to aside after rotation
			if ((shape != Tetrominoes.LineShape) // except LineShape
					&& ((minX() < -1) || (maxX() <= 0))) {
				for (int i = 0; i < getLength(shape); i++) {
					setX(i, x(i) + 1);
				}
			}
			if ((minY() < 0) || (maxY() <= 0)) {
				for (int i = 0; i < getLength(shape); i++) {
					setY(i, y(i) + 1);
				}
			}
		}

		// initializing board fill
		clearBoardFill();

		this.shape = shape;
		this.setRotationAngle(rotationAngle);
		this.setFill(fill);

		return this;
	}

	/**
	 * Selection of the figure (with current fill)
	 * 
	 * @param shape
	 *            the figure
	 * @param rotationAngle
	 *            rotation angle of the figure
	 */
	public TetrisShape setShape(Tetrominoes shape, RotationAngle rotationAngle) {
		return setShape(shape, rotationAngle, getFill());
	}

	/**
	 * Selection of the figure (without rotation)
	 * 
	 * @param shape
	 *            the figure
	 * @param fill
	 *            type of fill of the figure
	 */
	public TetrisShape setShape(Tetrominoes shape, Cell fill) {
		return setShape(shape, RotationAngle.d0, fill);
	}

	/**
	 * Selection of the figure (without rotation and current fill is
	 * {@code Full})
	 * 
	 * @param shape
	 *            the figure
	 */
	public TetrisShape setShape(Tetrominoes shape) {
		return setShape(shape, RotationAngle.d0, Cell.Full);
	}

	public Tetrominoes getShape() {
		return shape;
	}

	public Cell[] getBoardFill() {
		return boardFill;
	}

	public void setBoardFill(Cell[] boardFill) {
		this.boardFill = boardFill.clone();
	}
	
	/**
	 * Filling the {@code boardFill} parameter with empty values
	 * @see #boardFill
	 */
	private void clearBoardFill(){
		for (int i = 0; i < boardFill.length; i++) {
			boardFill[i] = Cell.Empty;
		}
	}

	/**
	 * Getting points number of the specified figure 
	 * @param shape the figure
	 * @return number of the points
	 */
	private int getLength(Tetrominoes shape) {
		return coordsTable[shape.ordinal()].length;
	}

	@Override
	public int getLength() {
		return getLength(this.shape);
	}

	/**
	 * Selection of a random figure
	 */
	public TetrisShape setRandomShape() {
		Random r = new Random();
		int x = r.nextInt(7) + 1;
		Tetrominoes[] values = Tetrominoes.values();
		return setShape(values[x]);
	}

	/**
	 * Selection of a random super figure given in the {@code shapes} array
	 * @param shapes the array of numbered figures (0 to 3) 
	 */
	public TetrisShape setRandomSuperShape(int[] shapes) {
		Random r = new Random();
		int x = shapes[r.nextInt(shapes.length)] + 8;

		if (x >= Tetrominoes.values().length)
			x = 0;

		Tetrominoes[] values = Tetrominoes.values();
		return setShape(values[x], RotationAngle.d0, Cell.Blink);
	}

	/**
	 * Selection of a random super figure
	 */
	public TetrisShape setRandomSuperShape() {
		return setRandomSuperShape(new int[] { 0, 1, 2, 3 });
	}

	/**
	 * Selection of a random rotation angle
	 */
	public TetrisShape setRandomRotate() {
		Random r = new Random();
		int x = r.nextInt(4);
		RotationAngle[] values = RotationAngle.values();
		return setShape(getShape(), values[x]);
	}

	/**
	 * Selection of a {@link #setRandomShape random figure} and a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #setRandomShape
	 * @see #setRandomRotate
	 */
	public TetrisShape setRandomShapeAndRotate() {
		return setRandomShape().setRandomRotate();
	}

	/**
	 * Counterclockwise rotation of the figure
	 */
	@Override
	public TetrisShape rotateLeft() {
		if ((getShape() != Tetrominoes.SquareShape)
				&& (getShape().ordinal() <= 7))
			setShape(getShape(), this.getRotationAngle().getLeft());
		return this;
	}

	/**
	 * Clockwise rotation of the figure
	 */
	@Override
	public TetrisShape rotateRight() {
		if ((getShape() != Tetrominoes.SquareShape)
				&& (getShape().ordinal() <= 7))
			setShape(getShape(), this.getRotationAngle().getRight());
		return this;
	}

	@Override
	public String toString() {
		// the figure and its rotation angle
		return "TetrisShape [" + this.getShape() + ", "
				+ this.getRotationAngle()
				// the lower left corner
				+ ", [" + minX() + ";" + minY() + "]]\n" + super.toString();
	}

}
