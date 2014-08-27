package com.kry.brickgame;

/**
 * @author noLive
 * 
 */
public class BoardLetters extends Board {

	enum Letters {
		None, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X
	};

	private final static int width = 5;
	private final static int height = 5;

	private Letters letter;

	/**
	 * Board with a drawn letter
	 */
	private Cell[][] board = new Cell[width][height];

	/**
	 * Table letters to draw them on the board 5x5: [index][y][x]
	 */
	private final static Cell[][][] lettersTable = new Cell[][][] {
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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// [height - y - 1] - draw upside down
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
	 * Convert a string to a Letters object
	 * 
	 * @param str
	 *            - string like "A", "B", "C", ...
	 */
	protected Letters stringToLetters(String str) {
		Letters result = Letters.None;

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
