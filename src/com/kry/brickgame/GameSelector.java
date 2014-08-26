package com.kry.brickgame;

/**
 * The selection screen of a game
 */
public class GameSelector extends Game {

	private String letter;
	private int number;

	public GameSelector() {
		super();
		this.letter = "A";
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
		boardLetter.setLetter(boardLetter.stringToLetters(letter));
		insertBoard(boardLetter,
				(BOARD_WIDTH / 2 - boardLetter.getWidth() / 2),// x
				BOARD_HEIGHT - UNSHOWED_LINES - boardLetter.getHeight());// y
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
		insertBoard(boardNumber,
				(BOARD_WIDTH / 2 - boardNumber.getWidth() - 1),// x
				0);// y

		// 2nd number
		boardNumber.setNumber(boardNumber.intToNumbers(number_2));
		insertBoard(boardNumber, (BOARD_WIDTH / 2 + 1),// x
				0);// y
	}

	/**
	 * Next allowable letter
	 */
	private void nextLetter() {
		if (letter.toCharArray()[0] < 'X') {
			letter = String.valueOf((char) (letter.toCharArray()[0] + 1));
		} else {
			letter = "A";
		}
	}

	/**
	 * Previous allowable letter
	 */
	private void prevLetter() {
		if (letter.toCharArray()[0] > 'A') {
			letter = String.valueOf((char) (letter.toCharArray()[0] - 1));
		} else {
			letter = "X";
		}
	}

	/**
	 * Next allowable number
	 */
	private void nextNumber() {
		number = (number < 99) ? number + 1 : 1;
	}

	/**
	 * Previous allowable number
	 */
	private void prevNumber() {
		number = (number > 1) ? number - 1 : 99;
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
		case "A":
			Main.setGame(new TetrisGame(getSpeed(), getLevel()));
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
