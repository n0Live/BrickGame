package com.kry.brickgame;

import java.util.HashMap;
import java.util.Map;

/**
 * The selection screen of a game
 * 
 * @author noLive
 * 
 */
public class GameSelector extends Game {
	
	/**
	 * Number of subtypes for each game type
	 */
	private static Map<Character, Integer> maxNumbersForGames;
	static {
		maxNumbersForGames = new HashMap<Character, Integer>();
		maxNumbersForGames.put('A', 2);
		maxNumbersForGames.put('B', 4);
		maxNumbersForGames.put('C', 32);
		maxNumbersForGames.put('D', 4);
		maxNumbersForGames.put('E', 8);
		maxNumbersForGames.put('F', 2);
		maxNumbersForGames.put('G', 16);
		maxNumbersForGames.put('H', 8);
		maxNumbersForGames.put('I', 48);
		maxNumbersForGames.put('J', 48);
		maxNumbersForGames.put('K', 48);
		maxNumbersForGames.put('L', 48);
		maxNumbersForGames.put('M', 48);
		maxNumbersForGames.put('N', 48);
		maxNumbersForGames.put('O', 48);
		maxNumbersForGames.put('P', 48);
		maxNumbersForGames.put('Q', 48);
		maxNumbersForGames.put('R', 48);
		maxNumbersForGames.put('S', 48);
		maxNumbersForGames.put('T', 48);
		maxNumbersForGames.put('U', 48);
		maxNumbersForGames.put('V', 48);
		maxNumbersForGames.put('W', 48);
		maxNumbersForGames.put('X', 48);
	}

	private char letter;
	private int number;

	public GameSelector() {
		super();
		this.letter = 'A';
		this.number = 1;
	}

	/**
	 * Insert a Letters or a Numbers board in the basic board. Coordinate (
	 * {@code x, y}) is set a point, which gets the lower left corner of the
	 * {@code boardToInsert}
	 * 
	 * @param boardToInsert
	 *            a Letters or a Numbers board
	 * @param x
	 *            x-coordinate for the insertion
	 * @param y
	 *            y-coordinate for the insertion
	 * 
	 */
	private void insertBoard(Board boardToInsert, int x, int y) {
		insertCells(boardToInsert.getBoard(), x, y);
	}

	/**
	 * Displays a letter at the top of the basic board
	 */
	protected void drawLetter() {
		BoardLetters boardLetter = new BoardLetters();
		boardLetter.setLetter(boardLetter.charToLetters(letter));
		insertBoard(boardLetter, (boardWidth / 2 - boardLetter.getWidth() / 2),// x
				boardHeight - boardLetter.getHeight());// y
		if (number > maxNumbersForGames.get(letter)){
			number = 1;
			drawNumber();
		}
	}

	/**
	 * Displays a two numbers at the bottom of the basic board
	 */
	protected void drawNumber() {
		int number_1;
		int number_2;

		if (number < 10) {
			number_1 = 0;
			number_2 = number;
		} else {
			number_1 = number / 10;
			number_2 = number % 10;
		}

		BoardNumbers boardNumber = new BoardNumbers();

		// 1st number
		boardNumber.setNumber(boardNumber.intToNumbers(number_1));
		insertBoard(boardNumber, (boardWidth / 2 - boardNumber.getWidth() - 1),// x
				0);// y

		// 2nd number
		boardNumber.setNumber(boardNumber.intToNumbers(number_2));
		insertBoard(boardNumber, (boardWidth / 2 + 1),// x
				0);// y
	}

	/**
	 * Next allowable letter
	 */
	private void nextLetter() {
		if (letter < 'X') {
			letter++;
		} else {
			letter = 'A';
		}
	}

	/**
	 * Previous allowable letter
	 */
	private void prevLetter() {
		if (letter > 'A') {
			letter--;
		} else {
			letter = 'X';
		}
	}

	/**
	 * Next allowable number
	 */
	private void nextNumber() {
		number = (number < maxNumbersForGames.get(letter)) ? number + 1 : 1;
	}

	/**
	 * Previous allowable number
	 */
	private void prevNumber() {
		number = (number > 1) ? number - 1 : maxNumbersForGames.get(letter);
	}

	public void start() {
		super.start();
		drawLetter();
		drawNumber();
	}

	/**
	 * Launching a game depending on the chosen letters and numbers
	 */
	public void changeGame() {
		switch (letter) {
		case 'A':
			Main.setGame(new TetrisGame(getSpeed(), getLevel()));
			break;
		case 'B':
			Main.setGame(new SnakeGame(getSpeed(), getLevel()));
			break;
		default:
			Main.setGame(new TetrisGame(getSpeed(), getLevel()));
			break;
		}
	}

	public void keyPressed(KeyPressed key) {
		switch (key) {
		case KeyLeft:
			setSpeed(getSpeed() + 1);
			break;
		case KeyRight:
			setLevel(getLevel() + 1);
			break;
		case KeyRotate:
			nextLetter();
			drawLetter();
			break;
		case KeyUp:
			prevNumber();
			drawNumber();
			break;
		case KeyDown:
			nextNumber();
			drawNumber();
			break;
		case KeyStart:
			changeGame();
			break;
		default:
			break;
		}

	}
}
