package com.kry.brickgame;

public class BoardLetters extends Board {

	/**
	 * Фигуры - буквы
	 */
	enum Letters {
		None, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X
	};

	private final static int width = 5;
	private final static int height = 5;

	/**
	 * Буква
	 */
	private Letters letter;

	/**
	 * Доска, с нарисованной буквой
	 */
	private Cells[][] board = new Cells[width][height];

	/**
	 * Таблица рисунков букв на доске:
	 * [индекс_буквы][координата_y][координата_x]
	 */
	private final static Cells[][][] lettersTable = new Cells[][][] {
			{
					{ Empty, Empty, Empty, Empty, Empty },// None
					{ Empty, Empty, Empty, Empty, Empty },
					{ Empty, Empty, Empty, Empty, Empty },
					{ Empty, Empty, Empty, Empty, Empty },
					{ Empty, Empty, Empty, Empty, Empty } },
			{
					{ Empty, Full, Full, Full, Empty },// A
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Full, Full, Full, Full, Empty },// B
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty } },
			{
					{ Empty, Full, Full, Full, Full },// C
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Empty, Full, Full, Full, Full } },
			{
					{ Full, Full, Full, Full, Empty },// D
					{ Empty, Full, Empty, Empty, Full },
					{ Empty, Full, Empty, Empty, Full },
					{ Empty, Full, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty } },
			{
					{ Full, Full, Full, Full, Full },// E
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Full, Full, Full, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Full, Full, Full, Full } },
			{
					{ Full, Full, Full, Full, Full },// F
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Full, Full, Full, Full },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty } },
			{
					{ Empty, Full, Full, Full, Full },// G
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Full, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Empty, Full, Full, Full, Full } },
			{
					{ Full, Empty, Empty, Empty, Full },// H
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Empty, Full, Full, Full, Empty },// I
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Full, Full, Full, Empty } },
			{
					{ Full, Full, Full, Full, Full },// J
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Full, Empty, Full, Empty, Empty },
					{ Empty, Full, Empty, Empty, Empty } },
			{
					{ Full, Empty, Empty, Empty, Full },// K
					{ Full, Empty, Empty, Full, Empty },
					{ Full, Full, Full, Empty, Empty },
					{ Full, Empty, Empty, Full, Empty },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Full, Empty, Empty, Empty, Empty },// L
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Full, Full, Full, Full } },
			{
					{ Full, Empty, Empty, Empty, Full },// M
					{ Full, Full, Empty, Full, Full },
					{ Full, Empty, Full, Empty, Full },
					{ Full, Empty, Full, Empty, Full },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Full, Empty, Empty, Empty, Full },// N
					{ Full, Full, Empty, Empty, Full },
					{ Full, Empty, Full, Empty, Full },
					{ Full, Empty, Empty, Full, Full },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Empty, Full, Full, Full, Empty },// O
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Empty, Full, Full, Full, Empty } },
			{
					{ Full, Full, Full, Full, Empty },// P
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty },
					{ Full, Empty, Empty, Empty, Empty },
					{ Full, Empty, Empty, Empty, Empty } },
			{
					{ Empty, Full, Full, Full, Empty },// Q
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Full, Full },
					{ Empty, Full, Full, Full, Empty } },
			{
					{ Full, Full, Full, Full, Empty },// R
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty },
					{ Full, Empty, Empty, Full, Empty },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Empty, Full, Full, Full, Full },// S
					{ Full, Empty, Empty, Empty, Empty },
					{ Empty, Full, Full, Full, Empty },
					{ Empty, Empty, Empty, Empty, Full },
					{ Full, Full, Full, Full, Empty } },
			{
					{ Full, Full, Full, Full, Full },// T
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Empty, Full, Empty, Empty } },
			{
					{ Full, Empty, Empty, Empty, Full },// U
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Empty, Full, Full, Full, Empty } },
			{
					{ Full, Empty, Empty, Empty, Full },// V
					{ Full, Empty, Empty, Empty, Full },
					{ Full, Empty, Empty, Empty, Full },
					{ Empty, Full, Empty, Full, Empty },
					{ Empty, Empty, Full, Empty, Empty } },
			{
					{ Full, Empty, Empty, Empty, Full },// W
					{ Full, Empty, Full, Empty, Full },
					{ Full, Empty, Full, Empty, Full },
					{ Full, Full, Empty, Full, Full },
					{ Full, Empty, Empty, Empty, Full } },
			{
					{ Full, Empty, Empty, Empty, Full },// X
					{ Empty, Full, Empty, Full, Empty },
					{ Empty, Empty, Full, Empty, Empty },
					{ Empty, Full, Empty, Full, Empty },
					{ Full, Empty, Empty, Empty, Full } } };

	protected void setLetter(Letters letter) {
		// Рисуем на доске букву из #lettersTable
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// [height - y - 1] - рисуем вверх ногами (доска заполняется
				// снизу вверх)
				board[x][y] = lettersTable[letter.ordinal()][height - y - 1][x];
			}
		}
		setBoard(board);
		this.letter = letter;
	}

	protected Letters getLetter() {
		return letter;
	}

	/**
	 * Преобразование строки в объект Letters
	 * 
	 * @param str
	 *            - строка вида "A", "B", "C", ...
	 */
	protected Letters stringToLetters(String str) {
		Letters result;

		result = Letters.None;

		try {
			result = Letters.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			result = Letters.None;
		}

		return result;
	}

	public BoardLetters() {
		super(width, height);
		setLetter(Letters.None);
	}

}
