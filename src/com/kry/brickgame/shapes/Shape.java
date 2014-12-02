package com.kry.brickgame.shapes;

import java.io.Serializable;
import java.util.Arrays;

import com.kry.brickgame.boards.Board.Cell;

/**
 * @author noLive
 */
public class Shape implements Cloneable, Serializable {
	/**
	 * Rotation angle of a figure (in degrees)
	 */
	public enum RotationAngle {
		/**
		 * North/Up
		 */
		d0,
		/**
		 * East/Right
		 */
		d90,
		/**
		 * South/Down
		 */
		d180,
		/**
		 * West/Left
		 */
		d270;
		
		/**
		 * The next counterclockwise rotation angle
		 */
		public RotationAngle getLeft() {
			return ordinal() > 0 ? RotationAngle.values()[ordinal() - 1]
					: RotationAngle.values()[RotationAngle.values().length - 1];
		}
		
		/**
		 * The direction opposite to the current direction
		 */
		public RotationAngle getOpposite() {
			return getRight().getRight();
		}
		
		/**
		 * The next clockwise rotation angle
		 */
		public RotationAngle getRight() {
			return ordinal() < RotationAngle.values().length - 1 ? RotationAngle
					.values()[ordinal() + 1] : RotationAngle.values()[0];
		}
	}
	
	private static final long serialVersionUID = 842374378117995961L;
	
	/**
	 * Rotation angle of the figure
	 */
	private RotationAngle rotationAngle;
	/**
	 * Type of fill of the figure
	 */
	private Cell fill;
	
	/**
	 * A set of coordinates of a points of a figures:
	 * [index][coordinate:0-x,1-y]
	 */
	private int[][] coords;
	
	/**
	 * Constructor of the shape
	 * 
	 * @param length
	 *            maximal number of the points of the figure
	 */
	public Shape(int length) {
		this(length, RotationAngle.d0, Cell.Empty);
	}
	
	/**
	 * Constructor of the shape
	 * 
	 * @param length
	 *            maximal number of the points of the figure
	 * @param rotationAngle
	 *            rotation angle of the figure
	 * @param fill
	 *            type of fill of the figure
	 */
	public Shape(int length, RotationAngle rotationAngle, Cell fill) {
		coords = new int[length][2];
		this.rotationAngle = rotationAngle;
		this.fill = fill;
	}
	
	/**
	 * Copy constructor of the shape
	 * 
	 * @param aShape
	 *            a shape for copying
	 */
	public Shape(Shape aShape) {
		super();
		rotationAngle = aShape.rotationAngle;
		fill = aShape.fill;
		
		coords = new int[aShape.coords.length][aShape.coords[0].length];
		for (int i = 0; i < aShape.coords.length; i++) {
			coords[i] = aShape.coords[i].clone();
		}
	}
	
	@Override
	public Shape clone() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Shape newShape = new Shape(this);
		return newShape;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Shape other = (Shape) obj;
		if (!Arrays.deepEquals(coords, other.coords)) return false;
		return true;
	}
	
	/**
	 * Get the coordinates of the single point
	 * 
	 * @param i
	 *            index of a point
	 * @return coordinate value
	 */
	public int[] getCoord(int i) {
		return coords[i];
	}
	
	public int[][] getCoords() {
		return coords;
	}
	
	public Cell getFill() {
		return fill;
	}
	
	/**
	 * @return the height of the figure
	 */
	public int getHeight() {
		return maxY() - minY() + 1;
	}
	
	/**
	 * Getting points number of the current figure
	 */
	public int getLength() {
		return coords.length;
	}
	
	/**
	 * @return rotation angle of the figure
	 */
	public RotationAngle getRotationAngle() {
		return rotationAngle;
	}
	
	/**
	 * @return the width of the figure
	 */
	public int getWidth() {
		return maxX() - minX() + 1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coords);
		return result;
	}
	
	/**
	 * @return The maximum value of the x-coordinates of all points of the
	 *         figure
	 */
	public int maxX() {
		int result = x(0);
		for (int i = 1; i < getLength(); i++) {
			result = Math.max(result, x(i));
		}
		return result;
	}
	
	/**
	 * @return The maximum value of the y-coordinates of all points of the
	 *         figure
	 */
	public int maxY() {
		int result = y(0);
		for (int i = 1; i < getLength(); i++) {
			result = Math.max(result, y(i));
		}
		return result;
	}
	
	/**
	 * @return The minimum value of the x-coordinates of all points of the
	 *         figure
	 */
	public int minX() {
		int result = x(0);
		for (int i = 1; i < getLength(); i++) {
			result = Math.min(result, x(i));
		}
		return result;
	}
	
	/**
	 * @return The minimum value of the y-coordinates of all points of the
	 *         figure
	 */
	public int minY() {
		int result = y(0);
		for (int i = 1; i < getLength(); i++) {
			result = Math.min(result, y(i));
		}
		return result;
	}
	
	/**
	 * Counterclockwise rotation of the figure
	 * 
	 * @return the figure after rotation
	 */
	public Shape rotateLeft() {
		Shape originalShape = new Shape(this);
		
		for (int i = 0; i < getLength(); i++) {
			setX(i, -originalShape.y(i));
			setY(i, originalShape.x(i));
		}
		rotationAngle = rotationAngle.getLeft();
		return this;
	}
	
	/**
	 * Clockwise rotation of the figure
	 * 
	 * @return the figure after rotation
	 */
	public Shape rotateRight() {
		Shape originalShape = new Shape(this);
		
		for (int i = 0; i < getLength(); i++) {
			setX(i, originalShape.y(i));
			setY(i, -originalShape.x(i));
		}
		rotationAngle = rotationAngle.getRight();
		return this;
	}
	
	/**
	 * Set the coordinates of the single point
	 * 
	 * @param i
	 *            index of a point
	 * @param value
	 *            coordinate value
	 */
	public void setCoord(int i, int[] value) {
		coords[i] = value.clone();
	}
	
	protected void setCoords(int[][] coords) {
		this.coords = new int[coords.length][coords[0].length];
		for (int i = 0; i < coords.length; i++) {
			this.coords[i] = coords[i].clone();
		}
	}
	
	public void setFill(Cell fill) {
		this.fill = fill;
	}
	
	/**
	 * Sets rotation angle of the figure
	 * 
	 * @param rotationAngle
	 *            new rotation angle of the figure
	 * @return the figure after rotation
	 */
	protected Shape setRotationAngle(RotationAngle rotationAngle) {
		this.rotationAngle = rotationAngle;
		return this;
	}
	
	/**
	 * Setting the value of the x-coordinate of a points of the figure
	 * 
	 * @param index
	 *            index of the point of the figures
	 * @param x
	 *            the x-coordinate value
	 */
	protected void setX(int index, int x) {
		coords[index][0] = x;
	}
	
	/**
	 * Setting the value of the y-coordinate of a points of the figure
	 * 
	 * @param index
	 *            index of the point of the figures
	 * @param y
	 *            the y-coordinate value
	 */
	protected void setY(int index, int y) {
		coords[index][1] = y;
	}
	
	/**
	 * Loop through the value of {@code maxY()} to {@code minY()} (downwards)
	 * and from {@code minX()} to {@code maxX()} (left to right), if the value
	 * of x and y match with coordinates value in any point of the figure then
	 * print "0" otherwise " " (space)
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Shape\n");
		
		int min_x = minX();
		int max_x = maxX();
		int min_y = minY();
		int max_y = maxY();
		
		for (int y = max_y; y >= min_y; y--) {
			char line[] = new char[(max_x - min_x) + 1];
			
			for (int x = min_x; x <= max_x; x++) {
				// [x + (0 - min_x)]: because x can be less than 0, then x is
				// shifted to 0
				line[x + (0 - min_x)] = ' ';
				for (int k = 0; k < getLength(); k++) {
					if ((x(k) == x) && (y(k) == y)) {
						// see previous comment
						line[x + (0 - min_x)] = (getFill() == Cell.Blink) ? '*'
								: '0';
						break;
					}
				}
			}
			result.append(line).append("\n");
		}
		return result.toString();
		// Results:
		// _0_ | 0__ | 00
		// 000 | 000 | 00
	}
	
	/**
	 * Turn the figure to the opposite direction
	 * 
	 * @return the figure after rotation
	 */
	public Shape turnAround() {
		Shape originalShape = new Shape(this);
		
		for (int i = 0; i < getLength(); i++) {
			setX(i, -originalShape.x(i));
			setY(i, -originalShape.y(i));
		}
		rotationAngle = rotationAngle.getOpposite();
		return this;
	}
	
	public int x(int index) {
		return getCoords()[index][0];
	}
	
	public int y(int index) {
		return getCoords()[index][1];
	}
	
}
