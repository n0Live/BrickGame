package com.kry.brickgame.shapes;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.kry.brickgame.boards.Board.Cell;

/**
 * @author noLive
 */
public class TetrisShape extends Shape {
	public enum Figures {
		NoShape, //
		// Trominoes
		TroLineShape, CornerShape,
		// Tetraminoes
		ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape,
		// Pentominoes
		PentoPShape, MirroredPentoPShape, PentoQShape, MirroredPentoQShape, //
		PentoRShape, MirroredPentoRShape, PentoSShape, MirroredPentoSShape, //
		PentoTShape, PentoUShape, PentoVShape, PentoWShape, PentoXShape, //
		PentoYShape, MirroredPentoYShape, PentoZShape, MirroredPentoZShape, //
		// SuperFigures
		SuperPoint, SuperGun, SuperMudGun, SuperBomb;
		
		public final static int REF_TO_FIRST_TROMINOES = 1;
		public final static int REF_TO_FIRST_TETRAMINOES = 3;
		public final static int REF_TO_FIRST_PENTOMINOES = 10;
		public final static int REF_TO_FIRST_SUPER_SHAPE = 27;
	}
	
	public enum Polyominoes {
		Trominoes, Tetraminoes, Pentominoes
	}
	
	private static final long serialVersionUID = -4165024906477016657L;
	private static final int[] emptySuperShapes = new int[0];
	
	/**
	 * A set of coordinates of a points of the figures:
	 * [shapeIndex][pointIndex][coordinate:0-x,1-y]
	 */
	private static final int[][][] coordsTable = new int[][][] { { { 0, 0 } }, // NoShape
	        // Trominoes
	        { { -1, -1 }, { 0, 0 }, { 1, 1 } }, // TroLineShape
	        { { 0, 1 }, { 0, 0 }, { 1, 0 } }, // CornerShape
	        // Tetraminoes
	        { { -1, 1 }, { -1, 0 }, { 0, 0 }, { 0, -1 } }, // ZShape
	        { { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } }, // SShape
	        { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, // LineShape
	        { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, // TShape
	        { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, // SquareShape
	        { { 0, -1 }, { 1, 0 }, { 1, -1 }, { 1, 1 } }, // LShape
	        { { 0, -1 }, { 1, -1 }, { 0, 0 }, { 0, 1 } }, // MirroredLShape
	        // Pentominoes
	        { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, // PentoPShape
	        { { 1, -1 }, { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, // MirroredPentoPShape
	        { { 1, -1 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 2 } }, // PentoQShape
	        { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } }, // MirroredPentoQShape
	        { { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 0 }, { 2, 1 } }, // PentoRShape
	        { { 1, -1 }, { 1, 0 }, { 1, 1 }, { 2, 0 }, { 0, 1 } }, // MirroredPentoRShape
	        { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 1 } }, // PentoSShape
	        { { -1, 1 }, { 0, 1 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // MirroredPentoSShape
	        { { 0, -1 }, { 0, 0 }, { 0, 1 }, { -1, 1 }, { 1, 1 } }, // PentoTShape
	        { { -1, 0 }, { -1, 1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } }, // PentoUShape
	        { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 }, { 2, 0 } }, // PentoVShape
	        { { -1, 1 }, { -1, 0 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, // PentoWShape
	        { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } }, // PentoXShape
	        { { -2, 0 }, { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 0 } }, // PentoYShape
	        { { -1, 0 }, { 0, 1 }, { 0, 0 }, { 1, 0 }, { 2, 0 } }, // MirroredPentoYShape
	        { { -1, 1 }, { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, // PentoZShape
	        { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, 1 } }, // MirroredPentoZShape
	        // SuperFigures
	        { { 0, 0 } }, // SuperPoint
	        { { 0, 0 }, { 0, 1 } }, // SuperGun
	        { { 0, 0 }, { 0, 1 }, { 0, 2 } }, // SuperMudGun
	        { { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } } // SuperBomb
	};
	
	/**
	 * Getting points number of the specified figure
	 * 
	 * @param shape
	 *            the figure
	 * @return number of the points
	 */
	private static int getLength(Figures shape) {
		return coordsTable[shape.ordinal()].length;
	}
	
	/**
	 * Get instance of next shape in {@link Figures}. If the
	 * {@code aTetrisShape} is the last item in Figures, then returns the first
	 * item with the next rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the next
	 * @param includingSuperShapes
	 *            whether to include the super figure in the result
	 * @return the next figure from {@code aTetrisShape}
	 */
	public static TetrisShape getNextShape(TetrisShape aTetrisShape, boolean includingSuperShapes) {
		Figures shape;
		RotationAngle rotationAngle;
		
		int lastItem = includingSuperShapes ? Figures.values().length
		        : Figures.REF_TO_FIRST_SUPER_SHAPE;
		
		if (aTetrisShape.getShape().ordinal() < lastItem - 1) {
			shape = Figures.values()[aTetrisShape.getShape().ordinal() + 1];
			rotationAngle = aTetrisShape.getRotationAngle();
		} else {
			shape = Figures.values()[Figures.REF_TO_FIRST_TROMINOES];
			rotationAngle = aTetrisShape.getRotationAngle().getRight();
		}
		
		return new TetrisShape(shape, rotationAngle, aTetrisShape.getFill());
	}
	
	/**
	 * Get instance of next shape in Tetraminoes. If the {@code aTetrisShape} is
	 * the last item in Tetraminoes, then returns the first item with the next
	 * rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the next
	 * @return the next figure from {@code aTetrisShape}
	 */
	public static TetrisShape getNextTetraminoes(TetrisShape aTetrisShape) {
		Figures shape;
		RotationAngle rotationAngle;
		
		int itemPosition = aTetrisShape.getShape().ordinal();
		if (itemPosition < Figures.REF_TO_FIRST_TETRAMINOES
		        || itemPosition >= Figures.REF_TO_FIRST_PENTOMINOES) {
			itemPosition = Figures.REF_TO_FIRST_TETRAMINOES;
		}
		
		int lastItem = Figures.REF_TO_FIRST_PENTOMINOES - 1;
		
		if (itemPosition < lastItem) {
			shape = Figures.values()[itemPosition + 1];
			rotationAngle = aTetrisShape.getRotationAngle();
		} else {
			shape = Figures.values()[Figures.REF_TO_FIRST_TETRAMINOES];
			rotationAngle = aTetrisShape.getRotationAngle().getRight();
		}
		
		return new TetrisShape(shape, rotationAngle, aTetrisShape.getFill());
	}
	
	/**
	 * Get instance of previous shape in {@link Figures}. If the
	 * {@code aTetrisShape} is the first item in Figures, then returns the last
	 * item with the previous rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the previous
	 * @param includingSuperShapes
	 *            whether to include the super figure in the result
	 * @return the previous figure from {@code aTetrisShape}
	 */
	public static TetrisShape getPrevShape(TetrisShape aTetrisShape, boolean includingSuperShapes) {
		Figures shape;
		RotationAngle rotationAngle;
		
		int lastItem = includingSuperShapes ? Figures.values().length
		        : Figures.REF_TO_FIRST_SUPER_SHAPE;
		
		if (aTetrisShape.getShape().ordinal() > Figures.REF_TO_FIRST_TROMINOES) {
			shape = Figures.values()[aTetrisShape.getShape().ordinal() - 1];
			rotationAngle = aTetrisShape.getRotationAngle();
		} else {
			shape = Figures.values()[lastItem - 1];
			rotationAngle = aTetrisShape.getRotationAngle().getLeft();
		}
		
		return new TetrisShape(shape, rotationAngle, aTetrisShape.getFill());
	}
	
	/**
	 * Get instance of previous shape in Tetraminoes. If the
	 * {@code aTetrisShape} is the first item in Tetraminoes, then returns the
	 * last item with the previous rotation angle.
	 * 
	 * @param aTetrisShape
	 *            the figure for which get the previous
	 * @return the previous figure from {@code aTetrisShape}
	 */
	public static TetrisShape getPrevTetraminoes(TetrisShape aTetrisShape) {
		Figures shape;
		RotationAngle rotationAngle;
		
		int itemPosition = aTetrisShape.getShape().ordinal();
		if (itemPosition < Figures.REF_TO_FIRST_TETRAMINOES
		        || itemPosition >= Figures.REF_TO_FIRST_PENTOMINOES) {
			itemPosition = Figures.REF_TO_FIRST_TETRAMINOES;
		}
		
		int lastItem = Figures.REF_TO_FIRST_PENTOMINOES - 1;
		
		if (itemPosition > Figures.REF_TO_FIRST_TETRAMINOES) {
			shape = Figures.values()[itemPosition - 1];
			rotationAngle = aTetrisShape.getRotationAngle();
		} else {
			shape = Figures.values()[lastItem];
			rotationAngle = aTetrisShape.getRotationAngle().getLeft();
		}
		
		return new TetrisShape(shape, rotationAngle, aTetrisShape.getFill());
	}
	
	/**
	 * Get instance of a random figure given in the specified Polyominoes and
	 * the {@code superShapes} array
	 * 
	 * @param polyominoes
	 *            list of types of Polyominoes figures to be included in the
	 *            selection
	 * @param superShapes
	 *            the array of numbered super figures (from 0 to 4)
	 * @return instance of a random figure
	 */
	public static TetrisShape getRandomPolyominoesAndSuper(Set<Polyominoes> polyominoes,
	        int[] superShapes) {
		final int TrominoesCount = Figures.REF_TO_FIRST_TETRAMINOES
		        - Figures.REF_TO_FIRST_TROMINOES;
		final int TetraminoesCount = Figures.REF_TO_FIRST_PENTOMINOES
		        - Figures.REF_TO_FIRST_TETRAMINOES;
		final int PentominoesCount = Figures.REF_TO_FIRST_SUPER_SHAPE
		        - Figures.REF_TO_FIRST_PENTOMINOES;
		final boolean withTrominoes = polyominoes.contains(Polyominoes.Trominoes);
		final boolean withTetraminoes = polyominoes.contains(Polyominoes.Tetraminoes);
		final boolean withPentominoes = polyominoes.contains(Polyominoes.Pentominoes);
		final boolean withSuperShapes = superShapes.length > 0;
		
		if (!polyominoes.contains(Polyominoes.Trominoes)
		        && !polyominoes.contains(Polyominoes.Tetraminoes)
		        && !polyominoes.contains(Polyominoes.Pentominoes))
		    return getRandomShapeOfSpecified(superShapes);
		
		int[] shapes = new int[
		// length of Trominoes
		(withTrominoes ? TrominoesCount : 0)
		// length of Tetraminoes
		        + (withTetraminoes ? TetraminoesCount : 0)
		        // length of Pentominoes
		        + (withPentominoes ? PentominoesCount : 0)
		        // length of SuperFigures
		        + (withSuperShapes ? superShapes.length : 0)];
		
		int i = 0;
		while (i < shapes.length) {
			// fill in the values of Trominoes
			if (withTrominoes) {
				for (int j = 0; j < TrominoesCount; j++) {
					shapes[i++] = Figures.values()[Figures.REF_TO_FIRST_TROMINOES + j].ordinal();
				}
			}
			// fill in the values of Tetraminoes
			if (withTetraminoes) {
				for (int j = 0; j < TetraminoesCount; j++) {
					shapes[i++] = Figures.values()[Figures.REF_TO_FIRST_TETRAMINOES + j].ordinal();
				}
			}
			// fill in the values of Pentominoes
			if (withPentominoes) {
				for (int j = 0; j < PentominoesCount; j++) {
					shapes[i++] = Figures.values()[Figures.REF_TO_FIRST_PENTOMINOES + j].ordinal();
				}
			}
			// fill in the values of specified SuperFigures
			if (withSuperShapes) {
				for (int superShape : superShapes) {
					shapes[i++] = Figures.values()[Figures.REF_TO_FIRST_SUPER_SHAPE + superShape]
					        .ordinal();
				}
			}
		}
		
		return getRandomShapeOfSpecified(shapes);
	}
	
	/**
	 * Get instance of a {@link #getRandomShapeInstance random figure} with a
	 * {@link #setRandomRotate random rotation angle}
	 * 
	 * @see #getRandomShapeInstance
	 * @see #setRandomRotate
	 */
	public static TetrisShape getRandomShapeAndRotate() {
		return getRandomShapeInstance().setRandomRotate();
	}
	
	/**
	 * Get instance of a random Polyominoes figure or super figure
	 */
	public static TetrisShape getRandomShapeAndSuper(int[] superShapes) {
		final Set<Polyominoes> polyominoes = EnumSet.of(Polyominoes.Trominoes, Polyominoes
				.Tetraminoes, Polyominoes.Pentominoes);
		return getRandomPolyominoesAndSuper(polyominoes, superShapes);
	}
	
	/**
	 * Get instance of a random figure
	 */
	public static TetrisShape getRandomShapeInstance() {
		final Set<Polyominoes> polyominoes = EnumSet.of(Polyominoes.Trominoes, Polyominoes
				.Tetraminoes, Polyominoes.Pentominoes);
		return getRandomPolyominoesAndSuper(polyominoes, emptySuperShapes);
	}
	
	/**
	 * Get instance of a random figure given in the {@code shapes} array
	 * 
	 * @param shapes
	 *            the array of numbered figures
	 * @return instance of a random figure
	 */
	public static TetrisShape getRandomShapeOfSpecified(int[] shapes) {
		Random r = new Random();
		int x = shapes[r.nextInt(shapes.length)];
		
		if (x < 0 || x >= Figures.values().length) {
			x = 0;
		}
		
		Figures[] values = Figures.values();
		TetrisShape newTetrisShape = new TetrisShape(values[x]);
		// for super figures
		if (newTetrisShape.isSuperShape()) {
			newTetrisShape.setFill(Cell.Blink);
		}
		return newTetrisShape;
	}
	
	/**
	 * Get instance of a random super figure
	 */
	public static TetrisShape getRandomSuperShape() {
		int[] superShapes = new int[4];
		for (int i = 0; i < superShapes.length; i++) {
			superShapes[i] = Figures.REF_TO_FIRST_SUPER_SHAPE + i;
		}
		
		return getRandomShapeOfSpecified(superShapes);
	}
	
	/**
	 * Get instance of a random super figure given in the {@code shapes} array
	 */
	public static TetrisShape getRandomSuperShape(int[] shapes) {
		return getRandomShapeOfSpecified(shapes);
	}
	
	/**
	 * Get instance of a random Tetraminoes figure
	 */
	public static TetrisShape getRandomTetraminoes() {
		final Set<Polyominoes> polyominoes = EnumSet.of(Polyominoes.Tetraminoes);
		return getRandomPolyominoesAndSuper(polyominoes, emptySuperShapes);
	}
	
	/**
	 * Get instance of a random Tetraminoes figure and specified super figures
	 * 
	 * @param superShapes
	 *            the array of numbered super figures (from 0 to 4)
	 */
	public static TetrisShape getRandomTetraminoesAndSuper(int[] superShapes) {
		final Set<Polyominoes> polyominoes = EnumSet.of(Polyominoes.Tetraminoes);
		return getRandomPolyominoesAndSuper(polyominoes, superShapes);
	}
	
	/**
	 * Keeps fill type of the board cells on which the figure is located
	 */
	private Cell[] boardFill;
	
	private Figures shape;
	
	public TetrisShape(Figures shape) {
		this(shape, RotationAngle.d0, Cell.Full);
	}
	
	public TetrisShape(Figures shape, RotationAngle rotationAngle, Cell fill) {
		super(getLength(shape));
		boardFill = new Cell[getLength(shape)];
		clearBoardFill();
		setShape(shape, rotationAngle, fill);
	}
	
	/**
	 * The copy constructor of a TetrisShape
	 * 
	 * @param aTetrisShape
	 *            a TetrisShape object for copying
	 */
	public TetrisShape(TetrisShape aTetrisShape) {
		super(getLength(aTetrisShape.shape));
		setBoardFill(aTetrisShape.getBoardFill());
		setShape(aTetrisShape.shape, aTetrisShape.getRotationAngle(), aTetrisShape.getFill());
	}
	
	/**
	 * Filling the {@code boardFill} parameter with empty values
	 * 
	 * @see #boardFill
	 */
	private void clearBoardFill() {
		for (int i = 0; i < boardFill.length; i++) {
			boardFill[i] = Cell.Empty;
		}
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
	private TetrisShape setShape(Figures shape, RotationAngle rotationAngle, Cell fill) {
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
		
		// not for super shape
		if (shape.ordinal() < Figures.REF_TO_FIRST_SUPER_SHAPE) {
			// Center the figures, which were shifted to aside after
			// rotation
			if (shape != Figures.LineShape // except LineShape
			        && (minX() < -1 || maxX() <= 0)) {
				for (int i = 0; i < getLength(shape); i++) {
					setX(i, x(i) + 1);
				}
			}
			if (minY() < 0 || maxY() <= 0) {
				for (int i = 0; i < getLength(shape); i++) {
					setY(i, y(i) + 1);
				}
			}
		}
		
		this.shape = shape;
		setRotationAngle(rotationAngle);
		setFill(fill);
		
		return this;
	}
	
	@Override
	public TetrisShape clone() {
		TetrisShape cloned = (TetrisShape) super.clone();
		cloned.boardFill = boardFill.clone();
		return cloned;
	}
	
	/**
	 * Returns {@code true} if this figure contains in the specified list of
	 * figures.
	 * 
	 * @param listOfFigures
	 *            specified list of figures for searching
	 * @return {@code true} if this figure contains in the specified list of
	 *         figures
	 */
	public boolean containsIn(Figures[] listOfFigures) {
		Figures[] prepList = listOfFigures.clone();
		Arrays.sort(prepList);
		return Arrays.binarySearch(prepList, getShape()) >= 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		final TetrisShape other = (TetrisShape) obj;
		if (!Arrays.equals(boardFill, other.boardFill)) return false;
		return shape == other.shape;
	}
	
	public Cell[] getBoardFill() {
		return boardFill.clone();
	}
	
	public Figures getShape() {
		return shape;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(boardFill);
		result = prime * result + (shape == null ? 0 : shape.hashCode());
		return result;
	}
	
	/**
	 * Returns {@code true} if this figure refers to super figures
	 */
	public boolean isSuperShape() {
		return shape.ordinal() >= Figures.REF_TO_FIRST_SUPER_SHAPE;
	}
	
	/**
	 * Counterclockwise rotation of the figure
	 */
	@Override
	public TetrisShape rotateLeft() {
		if (getShape() != Figures.SquareShape && getShape() != Figures.PentoXShape
		        && !isSuperShape()) {
			setShape(getShape(), getRotationAngle().getLeft(), getFill());
			clearBoardFill();
		}
		return this;
	}
	
	/**
	 * Clockwise rotation of the figure
	 */
	@Override
	public TetrisShape rotateRight() {
		if (getShape() != Figures.SquareShape && getShape() != Figures.PentoXShape
		        && !isSuperShape()) {
			setShape(getShape(), getRotationAngle().getRight(), getFill());
			clearBoardFill();
		}
		return this;
	}
	
	/**
	 * Sets the background fill under the figure
	 */
	public void setBoardFill(Cell[] boardFill) {
		this.boardFill = boardFill.clone();
	}
	
	/**
	 * Set a random rotation angle
	 */
	public TetrisShape setRandomRotate() {
		Random r = new Random();
		int x = r.nextInt(4);
		RotationAngle[] values = RotationAngle.values();
		return setShape(getShape(), values[x], getFill());
	}
	
	@Override
	public String toString() {
		// the figure and its rotation angle
		return "TetrisShape [" + getShape() + ", " + getRotationAngle()
		// the lower left corner
		        + ", [" + minX() + ";" + minY() + "]]\n" + super.toString();
	}
	
}
