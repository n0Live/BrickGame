package com.kry.brickgame;

public class BoardNumbers extends Board {

	enum Numbers {
		None, n0, n1, n2, n3, n4, n5, n6, n7, n8, n9
	};

	private final static int width = 3;
	private final static int height = 5;

	private Numbers number;

	/**
	 * Board with a drawn number
	 */
	private Cell[][] board = new Cell[width][height];

	/**
	 * Table numbers to draw them on the board 5x5: [index][y][x]
	 */
	private final static Cell[][][] numbersTable = new Cell[][][] {
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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// [height - y - 1] - draw upside down
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
	 * Convert a string to a Numbers object
	 * 
	 * @param str
	 *            - string like "0" .. "9"
	 */
	protected Numbers stringToNumbers(String str) {
		Numbers result = Numbers.None;

		try {
			result = Numbers.valueOf("n" + str);
		} catch (IllegalArgumentException e) {
			result = Numbers.None;
		}

		return result;
	}

	/**
	 * Convert an integer to a Numbers object
	 * 
	 * @param i
	 *            - integer from 0 to 9
	 */
	protected Numbers intToNumbers(int i) {
		return stringToNumbers(String.valueOf(i));
	}

	public BoardNumbers() {
		super(width, height);
		setNumber(Numbers.None);
	}

}
