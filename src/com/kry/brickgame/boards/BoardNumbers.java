package com.kry.brickgame.boards;

/**
 * @author noLive
 */
public class BoardNumbers extends Board {
	enum Numbers {
		NONE, N0, N1, N2, N3, N4, N5, N6, N7, N8, N9
	}
	
	private static final long serialVersionUID = 5903070508565392642L;
	
	public final static int width = 3;
	public final static int height = 5;
	
	/**
	 * Table numbers to draw them on the board 5x5: [index][y][x]
	 */
	private final static Cell[][][] numbersTable = new Cell[][][] { {
	        // None
	        { E, E, E },//
	        { E, E, E },//
	        { E, E, E },//
	        { E, E, E },//
	        { E, E, E } }, {
	        // 0
	        { F, F, F },//
	        { F, E, F },//
	        { F, E, F },//
	        { F, E, F },//
	        { F, F, F } }, {
	        // 1
	        { E, F, E },//
	        { F, F, E },//
	        { E, F, E },//
	        { E, F, E },//
	        { F, F, F } }, {
	        // 2
	        { F, F, F },//
	        { E, E, F },//
	        { F, F, F },//
	        { F, E, E },//
	        { F, F, F } }, {
	        // 3
	        { F, F, F },//
	        { E, E, F },//
	        { F, F, F },//
	        { E, E, F },//
	        { F, F, F } }, {
	        // 4
	        { E, E, F },//
	        { E, F, F },//
	        { F, E, F },//
	        { F, F, F },//
	        { E, E, F } }, {
	        // 5
	        { F, F, F },//
	        { F, E, E },//
	        { F, F, F },//
	        { E, E, F },//
	        { F, F, F } }, {
	        // 6
	        { F, F, F },//
	        { F, E, E },//
	        { F, F, F },//
	        { F, E, F },//
	        { F, F, F } }, {
	        // 7
	        { F, F, F },//
	        { E, E, F },//
	        { E, F, E },//
	        { E, F, E },//
	        { E, F, E } }, {
	        // 8
	        { F, F, F },//
	        { F, E, F },//
	        { F, F, F },//
	        { F, E, F },//
	        { F, F, F } }, {
	        // 9
	        { F, F, F },//
	        { F, E, F },//
	        { F, F, F },//
	        { E, E, F },//
	        { F, F, F } } };
	
	/**
	 * Convert a string to a Numbers object
	 * 
	 * @param str
	 *            string like "0" .. "9"
	 */
	public static Numbers stringToNumbers(String str) {
		final String PREFIX = "N";
		Numbers result;
		
		try {
			result = Numbers.valueOf(PREFIX + str);
		} catch (IllegalArgumentException e) {
			result = Numbers.NONE;
		}
		
		return result;
	}
	
	/**
	 * Convert a character to a Numbers object
	 * 
	 * @param ch
	 *            character like '0' .. '9'
	 */
	public static Numbers charToNumbers(char ch) {
		return stringToNumbers(String.valueOf(ch));
	}
	
	/**
	 * Convert an integer to a Numbers object
	 * 
	 * @param i
	 *            integer from 0 to 9
	 */
	public static Numbers intToNumbers(int i) {
		return stringToNumbers(String.valueOf(i));
	}
	
	/**
	 * Convert a Numbers object to a integer
	 * 
	 * @param number
	 *            a Numbers object
	 * @return an integer representation of a Numbers object, or {@code -1} if
	 *         number is {@code NONE}
	 */
	public static int numbersToInt(Numbers number) {
		if (number.equals(Numbers.NONE)) return -1;
		
		char[] numbers = number.toString().toCharArray();
		return Integer.parseInt(String.valueOf(numbers[1]));
	}
	
	private Numbers number;
	
	public BoardNumbers() {
		super(width, height);
		setNumber(Numbers.NONE);
	}
	
	public Numbers getNumber() {
		return number;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		BoardNumbers other = (BoardNumbers) obj;
		return number == other.number;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (number == null ? 0 : number.hashCode());
		return result;
	}
	
	public void setNumber(Numbers number) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// [height - y - 1] - draw upside down
				setCell(numbersTable[number.ordinal()][height - y - 1][x], x, y);
			}
		}
		this.number = number;
	}
	
}
