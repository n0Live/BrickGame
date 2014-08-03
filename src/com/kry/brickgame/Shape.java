package com.kry.brickgame;


/**
 * <p>
 * Фабричный класс для фигур
 * </p>
 */
public class Shape {

	/**
	 * Набор координат фигур: [индекс_точки][координата:0-x,1-y]
	 */
	private int coords[][];

	protected int[][] getCoords() {
		return coords;
	}

	protected void setCoords(int[][] coords) {
		this.coords = coords;
	}

	/**
	 * @param length
	 *            - количество точек фигуры
	 */
	public Shape(int length) {
		super();
		coords = new int[length][2];
	}

	/**
	 * Устанавливает значение координаты x точки фигуры
	 * 
	 * @param index
	 *            - индекс точки фигуры
	 * @param x
	 *            - значение координаты x
	 */
	protected void setX(int index, int x) {
		coords[index][0] = x;
	}

	/**
	 * Устанавливает значение координаты y точки фигуры
	 * 
	 * @param index
	 *            - индекс точки фигуры
	 * @param y
	 *            - значение координаты y
	 */
	protected void setY(int index, int y) {
		coords[index][1] = y;
	}

	public int x(int index) {
		return coords[index][0];
	}

	public int y(int index) {
		return coords[index][1];
	}

	/**
	 * @return Минимальное значение координаты "x" среди всех точек фигуры
	 */
	public int minX() {
		int result = coords[0][0];
		for (int i = 0; i < coords.length; i++) {
			result = Math.min(result, coords[i][0]);
		}
		return result;
	}

	/**
	 * @return Минимальное значение координаты "y" среди всех точек фигуры
	 */
	public int minY() {
		int result = coords[0][1];
		for (int i = 0; i < coords.length; i++) {
			result = Math.min(result, coords[i][1]);
		}
		return result;
	}

	/**
	 * @return Максимальное значение координаты "x" среди всех точек фигуры
	 */
	public int maxX() {
		int result = coords[0][0];
		for (int i = 0; i < coords.length; i++) {
			result = Math.max(result, coords[i][0]);
		}
		return result;
	}

	/**
	 * @return Максимальное значение координаты "y" среди всех точек фигуры
	 */
	public int maxY() {
		int result = coords[0][1];
		for (int i = 0; i < coords.length; i++) {
			result = Math.max(result, coords[i][1]);
		}
		return result;
	}

	/**
	 * Вращение фигуры против часовой стрелки
	 * 
	 * @param shape
	 *            - вращаемая фигура
	 */
	public Shape rotateLeft(Shape shape) {
		Shape result = new Shape(shape.coords.length);

		for (int i = 0; i < shape.coords.length; ++i) {
			result.setX(i, shape.y(i));
			result.setY(i, -shape.x(i));
		}
		return result;
	}

	/**
	 * Вращение фигуры по часовой стрелке
	 * 
	 * @param shape
	 *            - вращаемая фигура
	 */
	public Shape rotateRight(Shape shape) {
		Shape result = new Shape(shape.coords.length);

		for (int i = 0; i < shape.coords.length; ++i) {
			result.setX(i, -shape.y(i));
			result.setY(i, shape.x(i));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append("Shape\n");

		int min_x = minX();
		int max_x = maxX();
		int min_y = minY();
		int max_y = maxY();

		// Перебираем значение от max_y до min_y (сверху вниз) и
		// от min_x до max_x (слева направо) и, если значение x и y совпадут со
		// значением координат в какой-либо точке, то по адресу x,y рисуем 0,
		// иначе " " (пробел).
		// [y + (0 - min_x) - сдвигаем точку отсчета для массива символов к
		// нулю.
		// Результаты:
		// _0_ | 0__ | 00
		// 000 | 000 | 00
		for (int x = max_y; x >= min_y; --x) {
			char line[] = new char[(max_x - min_x) + 1];
			for (int y = min_x; y <= max_x; ++y) {
				line[y + (0 - min_x)] = ' ';
				for (int k = 0; k < coords.length; ++k) {
					if ((coords[k][0] == y) && (coords[k][1] == x)) {
						line[y + (0 - min_x)] = '0';
						break;
					}
				}
			}
			result.append(line).append("\n");
		}
		return result.toString();
	}

}
