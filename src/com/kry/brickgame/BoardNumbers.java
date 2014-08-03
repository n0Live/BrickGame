package com.kry.brickgame;

public class BoardNumbers extends Board {

	/**
	 * Фигуры - цифры
	 */
	enum Numbers {
		None, n0, n1, n2, n3, n4, n5, n6, n7, n8, n9
	};

	private final static int width = 3;
	private final static int height = 5;

	/**
	 * Цифра
	 */
	private Numbers number;

	/**
	 * Доска, с нарисованной цифрой
	 */
	private Cells[][] board = new Cells[width][height];

	/**
	 * Таблица рисунков цифр на доске:
	 * [индекс_цифры][координата_y][координата_x]
	 */
	private final static Cells[][][] numbersTable = new Cells[][][] {
			{ { Empty, Empty, Empty },// None
					{ Empty, Empty, Empty },//
					{ Empty, Empty, Empty },//
					{ Empty, Empty, Empty },//
					{ Empty, Empty, Empty } },//
			{ { Full, Full, Full },// 0
					{ Full, Empty, Full },//
					{ Full, Empty, Full },//
					{ Full, Empty, Full },//
					{ Full, Full, Full } },//
			{ { Empty, Full, Empty },// 1
					{ Full, Full, Empty },//
					{ Empty, Full, Empty },//
					{ Empty, Full, Empty },//
					{ Full, Full, Full } },//
			{ { Full, Full, Full },// 2
					{ Empty, Empty, Full },//
					{ Full, Full, Full },//
					{ Full, Empty, Empty },//
					{ Full, Full, Full } },//
			{ { Full, Full, Full },// 3
					{ Empty, Empty, Full },//
					{ Full, Full, Full },//
					{ Empty, Empty, Full },//
					{ Full, Full, Full } },//
			{ { Empty, Empty, Full },// 4
					{ Empty, Full, Full },//
					{ Full, Empty, Full },//
					{ Full, Full, Full },//
					{ Empty, Empty, Full } },//
			{ { Full, Full, Full },// 5
					{ Full, Empty, Empty },//
					{ Full, Full, Full },//
					{ Empty, Empty, Full },//
					{ Full, Full, Full } },//
			{ { Full, Full, Full },// 6
					{ Full, Empty, Empty },//
					{ Full, Full, Full },//
					{ Full, Empty, Full },//
					{ Full, Full, Full } },//
			{ { Full, Full, Full },// 7
					{ Empty, Empty, Full },//
					{ Empty, Full, Empty },//
					{ Empty, Full, Empty },//
					{ Empty, Full, Empty } },//
			{ { Full, Full, Full },// 8
					{ Full, Empty, Full },//
					{ Full, Full, Full },//
					{ Full, Empty, Full },//
					{ Full, Full, Full } }, //
			{ { Full, Full, Full },// 9
					{ Full, Empty, Full },//
					{ Full, Full, Full },//
					{ Empty, Empty, Full },//
					{ Full, Full, Full } } };

	protected void setNumber(Numbers number) {
		// Рисуем на доске цифру из #numbersTable
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// [height - y - 1] - рисуем вверх ногами (доска заполняется
				// снизу вверх)
				board[x][y] = numbersTable[number.ordinal()][height - y - 1][x];
			}
		}
		setBoard(board);
		this.number = number;
	}

	protected Numbers getLetter() {
		return number;
	}

	/**
	 * Преобразование строки в объект Numbers
	 * 
	 * @param str
	 *            - число в виде строки от "0" до "9"
	 */
	protected Numbers stringToNumbers(String str) {
		Numbers result;

		result = Numbers.None;

		try {
			result = Numbers.valueOf("n" + str);
		} catch (IllegalArgumentException e) {
			result = Numbers.None;
		}

		return result;
	}

	/**
	 * Преобразование числа в объект Numbers
	 * 
	 * @param i
	 *            - целое число от 0 до 9
	 */
	protected Numbers intToNumbers(int i) {
		return stringToNumbers(String.valueOf(i));
	}

	public BoardNumbers() {
		super(width, height);
		setNumber(Numbers.None);
	}

}
