package com.kry.brickgame.boards;

/**
 * @author noLive
 */
public class BoardLetters extends Board {
	enum Letters {
		None, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
	}
	
	private static final long serialVersionUID = -2320950183789497365L;;
	
	public final static int width = 5;
	public final static int height = 5;
	
	/**
	 * Convert a character to a Letters object
	 * 
	 * @param ch
	 *            - character like 'A', 'B', 'C', ...
	 */
	public static Letters charToLetters(char ch) {
		Letters result = Letters.None;
		
		try {
			result = Letters.valueOf(String.valueOf(ch).toUpperCase());
		} catch (IllegalArgumentException e) {
			result = Letters.None;
		}
		
		return result;
	}
	
	/**
	 * Convert a string to a Letters object
	 * 
	 * @param str
	 *            - string like "A", "B", "C", ...
	 */
	protected static Letters stringToLetters(String str) {
		Letters result = Letters.None;
		
		try {
			result = Letters.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			result = Letters.None;
		}
		
		return result;
	}
	
	private Letters letter;
	
	/**
	 * Table letters to draw them on the board 5x5: [index][y][x]
	 */
	private final static Cell[][][] lettersTable = new Cell[][][] { {
			// None
			{ E, E, E, E, E },//
			{ E, E, E, E, E },//
			{ E, E, E, E, E },//
			{ E, E, E, E, E },//
			{ E, E, E, E, E } }, {
			// A
			{ E, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, F, F, F, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F } }, {
			// B
			{ F, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, F, F, F, E } }, {
			// C
			{ E, F, F, F, F },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E },//
			{ E, F, F, F, F } }, {
			// D
			{ F, F, F, F, E },//
			{ E, F, E, E, F },//
			{ E, F, E, E, F },//
			{ E, F, E, E, F },//
			{ F, F, F, F, E } }, {
			// E
			{ F, F, F, F, F },//
			{ F, E, E, E, E },//
			{ F, F, F, F, E },//
			{ F, E, E, E, E },//
			{ F, F, F, F, F } }, {
			// F
			{ F, F, F, F, F },//
			{ F, E, E, E, E },//
			{ F, F, F, F, F },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E } }, {
			// G
			{ E, F, F, F, F },//
			{ F, E, E, E, E },//
			{ F, E, E, F, F },//
			{ F, E, E, E, F },//
			{ E, F, F, F, F } }, {
			// H
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, F, F, F, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F } }, {
			// I
			{ E, F, F, F, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ E, F, F, F, E } }, {
			// J
			{ F, F, F, F, F },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ F, E, F, E, E },//
			{ E, F, E, E, E } }, {
			// K
			{ F, E, E, E, F },//
			{ F, E, E, F, E },//
			{ F, F, F, E, E },//
			{ F, E, E, F, E },//
			{ F, E, E, E, F } }, {
			// L
			{ F, E, E, E, E },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E },//
			{ F, F, F, F, F } }, {
			// M
			{ F, E, E, E, F },//
			{ F, F, E, F, F },//
			{ F, E, F, E, F },//
			{ F, E, F, E, F },//
			{ F, E, E, E, F } }, {
			// N
			{ F, E, E, E, F },//
			{ F, F, E, E, F },//
			{ F, E, F, E, F },//
			{ F, E, E, F, F },//
			{ F, E, E, E, F } }, {
			// O
			{ E, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ E, F, F, F, E } }, {
			// P
			{ F, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, F, F, F, E },//
			{ F, E, E, E, E },//
			{ F, E, E, E, E } }, {
			// Q
			{ E, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, E, E, F, F },//
			{ E, F, F, F, E } }, {
			// R
			{ F, F, F, F, E },//
			{ F, E, E, E, F },//
			{ F, F, F, F, E },//
			{ F, E, E, F, E },//
			{ F, E, E, E, F } }, {
			// S
			{ E, F, F, F, F },//
			{ F, E, E, E, E },//
			{ E, F, F, F, E },//
			{ E, E, E, E, F },//
			{ F, F, F, F, E } }, {
			// T
			{ F, F, F, F, F },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E } }, {
			// U
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ E, F, F, F, E } }, {
			// V
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ F, E, E, E, F },//
			{ E, F, E, F, E },//
			{ E, E, F, E, E } }, {
			// W
			{ F, E, E, E, F },//
			{ F, E, F, E, F },//
			{ F, E, F, E, F },//
			{ F, F, E, F, F },//
			{ F, E, E, E, F } }, {
			// X
			{ F, E, E, E, F },//
			{ E, F, E, F, E },//
			{ E, E, F, E, E },//
			{ E, F, E, F, E },//
			{ F, E, E, E, F } }, {
			// Y
			{ F, E, E, E, F },//
			{ E, F, E, F, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E },//
			{ E, E, F, E, E } }, {
			// Z
			{ F, F, F, F, F },//
			{ E, E, E, F, E },//
			{ E, E, F, E, E },//
			{ E, F, E, E, E },//
			{ F, F, F, F, F } }
	
	};
	
	public BoardLetters() {
		super(width, height);
		setLetter(Letters.None);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		BoardLetters other = (BoardLetters) obj;
		if (letter != other.letter) return false;
		return true;
	}
	
	protected Letters getLetter() {
		return letter;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((letter == null) ? 0 : letter.hashCode());
		return result;
	}
	
	public void setLetter(Letters letter) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// [height - y - 1] - draw upside down
				setCell(lettersTable[letter.ordinal()][height - y - 1][x], x, y);
			}
		}
		this.letter = letter;
	}
	
}
