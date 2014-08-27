package com.kry.brickgame;

/**
 * @author noLive
 * 
 */
public class Shape {

	/**
	 * Rotation angle of a figure (in degrees)
	 */
	enum RotationAngle {
		/**
		 * North
		 */
		d0,
		/**
		 * East
		 */
		d90,
		/**
		 * South
		 */
		d180,
		/**
		 * West
		 */
		d270;

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

	/**
	 * A set of coordinates of a points of a figures:
	 * [index][coordinate:0-x,1-y]
	 */
	private int[][] coords;

	protected int[][] getCoords() {
		return coords;
	}

	protected void setCoords(int[][] coords) {
		this.coords = coords;
	}

	/**
	 * Set the coordinates of the single point
	 * 
	 * @param i
	 *            index of a point
	 * @param value
	 *            coordinate value
	 */
	protected void setCoord(int i, int[] value) {
		this.coords[i] = value;
	}

	/**
	 * Get the coordinates of the single point
	 * 
	 * @param i
	 *            index of a point
	 * @return coordinate value
	 */
	protected int[] getCoord(int i) {
		return this.coords[i];
	}

	/**
	 * 
	 * @param length
	 *            number of the points of the figure
	 */
	public Shape(int length) {
		super();
		coords = new int[length][2];
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

	public int x(int index) {
		return getCoords()[index][0];
	}

	public int y(int index) {
		return getCoords()[index][1];
	}

	/**
	 * @return The minimum value of the x-coordinates of all points of the
	 *         figure
	 */
	public int minX() {
		int result = getCoords()[0][0];
		for (int i = 0; i < getCoords().length; i++) {
			result = Math.min(result, getCoords()[i][0]);
		}
		return result;
	}

	/**
	 * @return The minimum value of the y-coordinates of all points of the
	 *         figure
	 */
	public int minY() {
		int result = getCoords()[0][1];
		for (int i = 0; i < getCoords().length; i++) {
			result = Math.min(result, getCoords()[i][1]);
		}
		return result;
	}

	/**
	 * @return The maximum value of the x-coordinates of all points of the
	 *         figure
	 */
	public int maxX() {
		int result = getCoords()[0][0];
		for (int i = 0; i < getCoords().length; i++) {
			result = Math.max(result, getCoords()[i][0]);
		}
		return result;
	}

	/**
	 * @return The maximum value of the y-coordinates of all points of the
	 *         figure
	 */
	public int maxY() {
		int result = getCoords()[0][1];
		for (int i = 0; i < getCoords().length; i++) {
			result = Math.max(result, getCoords()[i][1]);
		}
		return result;
	}

	/**
	 * Counterclockwise rotation of a figure
	 * 
	 * @param shape
	 *            a figure
	 * @return a figure after rotation
	 */
	public Shape rotateLeft(Shape shape) {
		Shape result = new Shape(shape.getCoords().length);

		for (int i = 0; i < shape.getCoords().length; i++) {
			result.setX(i, shape.y(i));
			result.setY(i, -shape.x(i));
		}
		return result;
	}

	/**
	 * Clockwise rotation of a figure
	 * 
	 * @param shape
	 *            a figure
	 * @return a figure after rotation
	 */
	public Shape rotateRight(Shape shape) {
		Shape result = new Shape(shape.getCoords().length);

		for (int i = 0; i < shape.getCoords().length; i++) {
			result.setX(i, -shape.y(i));
			result.setY(i, shape.x(i));
		}
		return result;
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
				for (int k = 0; k < getCoords().length; k++) {
					if ((getCoords()[k][0] == x) && (getCoords()[k][1] == y)) {
						// see previous comment
						line[x + (0 - min_x)] = '0';
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

}
