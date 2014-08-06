package com.kry.brickgame;

public class GameSelector extends Game {

	private String letter;
	private int number;

	public GameSelector() {
		super();
		this.letter = "A";
		this.number = 1;
	}

	/**
	 * Вставка доски с буквой или цифрой в основную доску
	 * 
	 * @param boardToInsert
	 *            - доска с буквой или цифрой
	 * @param x
	 *            - координата x места для вставки
	 * @param y
	 *            - координата y места для вставки
	 * 
	 *            В координаты (x,y) вставляется левый нижний угол
	 */
	public void insertBoard(Board boardToInsert, int x, int y) {
		Board board = getBoard();

		if ((x < 0) || (y < 0)) {
			return;
		}
		if ((x + boardToInsert.getWidth() > board.getWidth())
				|| (y + boardToInsert.getHeight() > board.getHeight())) {
			return;
		}

		for (int i = 0; i < boardToInsert.getWidth(); ++i) {
			for (int j = 0; j < boardToInsert.getHeight(); ++j) {
				board.setCell(boardToInsert.getCell(i, j), x + i, y + j);
			}
		}
		fireBoardChanged(board);
	}

	/**
	 * Вывод буквы на доску
	 */
	public void drawLetter() {
		BoardLetters boardLetter = new BoardLetters();
		boardLetter.setLetter(boardLetter.stringToLetters(letter));
		insertBoard(boardLetter,
				(BOARD_WIDTH / 2 - boardLetter.getWidth() / 2),// x
				BOARD_HEIGHT - UNSHOWED_LINES - boardLetter.getHeight());// y
	}

	/**
	 * Вывод номера на доску
	 */
	public void drawNumber() {
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
		boardNumber.setNumber(boardNumber.intToNumbers(number_1));
		insertBoard(boardNumber,
				(BOARD_WIDTH / 2 - boardNumber.getWidth() - 1),// x
				0);// y
		boardNumber.setNumber(boardNumber.intToNumbers(number_2));
		insertBoard(boardNumber, (BOARD_WIDTH / 2 + 1),// x
				0);// y
	}

	/**
	 * Следующая допустимая буква
	 */
	private void nextLetter() {
		if (letter.toCharArray()[0] < 'X') {
			letter = String.valueOf((char) (letter.toCharArray()[0] + 1));
		} else {
			letter = "A";
		}
	}

	/**
	 * Предыдущая допустимая буква
	 */
	private void prevLetter() {
		if (letter.toCharArray()[0] > 'A') {
			letter = String.valueOf((char) (letter.toCharArray()[0] - 1));
		} else {
			letter = "X";
		}
	}

	/**
	 * Следующий допустимый номер
	 */
	private void nextNumber() {
		number = (number < 99) ? number + 1 : 1;
	}

	/**
	 * Предыдущий допустимый номер
	 */
	private void prevNumber() {
		number = (number > 1) ? number - 1 : 99;
	}

	public void start() {
		super.start();
		drawLetter();
		drawNumber();
	}

	public void changeGame() {
		switch (letter) {
		case "A":
			Main.setGame(new TetrisGame());
			break;
		default:
			Main.setGame(new TetrisGame());
			break;
		}
	}

	/**
	 * Обработка нажатий кнопок
	 */
	public void keyPressed(KeyPressed key) {
		switch (key) {
		case KeyLeft:
			prevNumber();
			drawNumber();
			break;
		case KeyRight:
			nextNumber();
			drawNumber();
			break;
		case KeyRotate:
			changeGame();
			break;
		case KeyUp:
			nextLetter();
			drawLetter();
			break;
		case KeyDown:
			prevLetter();
			drawLetter();
			break;
		default:
			break;
		}

	}

}
