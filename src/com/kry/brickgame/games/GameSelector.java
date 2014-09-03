package com.kry.brickgame.games;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.kry.brickgame.Board;
import com.kry.brickgame.BoardLetters;
import com.kry.brickgame.BoardNumbers;
import com.kry.brickgame.Main;
import com.kry.brickgame.splashes.Splash;
import com.kry.brickgame.splashes.SplashScreen;

/**
 * The selection screen of a game
 * 
 * @author noLive
 * 
 */
public class GameSelector extends Game {
	/**
	 * List of games with the letters associated with them
	 */
	private static Map<Character, Class> gamesList;
	static {
		gamesList = new HashMap<Character, Class>();
		try {
			gamesList.put('A',
					Class.forName("com.kry.brickgame.games.DanceGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('A', null);
		}
		try {
			gamesList.put('B',
					Class.forName("com.kry.brickgame.games.TanksGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('B', null);
		}
		try {
			gamesList.put('C',
					Class.forName("com.kry.brickgame.games.ArkanoidGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('C', null);
		}
		try {
			gamesList.put('D',
					Class.forName("com.kry.brickgame.games.RaceGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('D', null);
		}
		try {
			gamesList
					.put('E', Class.forName("com.kry.brickgame.games.GunGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('E', null);
		}
		try {
			gamesList.put('F',
					Class.forName("com.kry.brickgame.games.SnakeGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('F', null);
		}
		try {
			gamesList.put('G',
					Class.forName("com.kry.brickgame.games.FroggerGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('G', null);
		}
		try {
			gamesList.put('H',
					Class.forName("com.kry.brickgame.games.InvadersGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('H', null);
		}
		try {
			gamesList.put('I',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('I', null);
		}
		try {
			gamesList.put('J',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('J', null);
		}
		try {
			gamesList.put('K',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('K', null);
		}
		try {
			gamesList.put('L',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('L', null);
		}
		try {
			gamesList.put('M',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('M', null);
		}
		try {
			gamesList.put('N',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('N', null);
		}
		try {
			gamesList.put('O',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('O', null);
		}
		try {
			gamesList.put('P',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('P', null);
		}
		try {
			gamesList.put('Q',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('Q', null);
		}
		try {
			gamesList.put('R',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('R', null);
		}
		try {
			gamesList.put('S',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('S', null);
		}
		try {
			gamesList.put('T',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('T', null);
		}
		try {
			gamesList.put('U',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('U', null);
		}
		try {
			gamesList.put('V',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('V', null);
		}
		try {
			gamesList.put('W',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('W', null);
		}
		try {
			gamesList.put('X',
					Class.forName("com.kry.brickgame.games.TetrisGame"));
		} catch (ClassNotFoundException e) {
			gamesList.put('X', null);
		}
	}

	/**
	 * Letter - kind of game
	 */
	private char letter;
	/**
	 * Number - subtypes of game
	 */
	private int number;

	/**
	 * Number of subtypes for the current game
	 */
	private int maxNumber;

	/**
	 * Class of the current game
	 */
	private Class<Game> c;

	/**
	 * Timer for the splash screen of the game
	 */
	private Timer splashTimer;

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
	protected void drawLetter(char letter) {
		BoardLetters boardLetter = new BoardLetters();
		boardLetter.setLetter(boardLetter.charToLetters(letter));
		insertBoard(boardLetter, (boardWidth / 2 - BoardLetters.width / 2 - 1),// x
				boardHeight - BoardLetters.height);// y
	}

	/**
	 * Displays a two numbers at the bottom of the basic board
	 */
	protected void drawNumber(int number) {
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
		insertBoard(boardNumber, (boardWidth / 2 - BoardNumbers.width - 1),// x
				0);// y

		// 2nd number
		boardNumber.setNumber(boardNumber.intToNumbers(number_2));
		insertBoard(boardNumber, (boardWidth / 2),// x
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
		if (drawAll())
			setStatus(Status.DoSomeWork);
		else
			setStatus(Status.ComingSoon);
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
		if (drawAll())
			setStatus(Status.DoSomeWork);
		else
			setStatus(Status.ComingSoon);
	}

	/**
	 * Next allowable number
	 */
	private void nextNumber() {
		number = (number < maxNumber) ? number + 1 : 1;
		drawNumber(number);
	}

	/**
	 * Previous allowable number
	 */
	private void prevNumber() {
		number = (number > 1) ? number - 1 : maxNumber;
		drawNumber(number);
	}

	/**
	 * Displays all the necessary information on the game: letter, number,
	 * splash screen
	 */
	private boolean drawAll() {
		// stop the splash animation timer
		if (splashTimer != null)
			splashTimer.cancel();

		drawLetter(letter);

		c = gamesList.get(letter);

		if (c != null) {
			// trying to get number of subtypes from the class of the game
			try {
				maxNumber = c.getField("subtypesNumber").getInt(c);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				// if unable - sets 1
				maxNumber = 1;
			}

			// trying to get the splash screen instance from the class of
			// the game
			try {
				splash = (Splash) c.getField("splash").get(c);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				splash = null;
			}

			if (splash != null) {
				// starts the timer to show splash screen of the game
				splashTimer = new Timer(splash.getClass().getName(), true);
				splashTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								drawGameSplash(splash);
							}
						});
					}
				}, 0, 500);
			} else {
				// if unable - clears the rectangle of the splash screen
				drawGameSplash(null);
			}
		} else {
			maxNumber = 1;
			drawGameSplash(null);
		}

		// checks that the current number does not exceed the maximum number
		if (number > maxNumber) {
			number = 1;
		}
		drawNumber(number);

		return (c != null);
	}

	/**
	 * Displays one frame of the splash screen of the game
	 * <p>
	 * If splash is {@code null} then clears the rectangle of the splash screen
	 * 
	 * @param splash
	 *            splash screen instance
	 */
	private void drawGameSplash(Splash splash) {
		if (splash != null) {
			insertBoard(splash.getNextFrame(), 0,// x
					BoardNumbers.height + 1);// y
		} else {
			Board clear = new Board(Splash.width, Splash.height);
			insertBoard(clear, 0,// x
					BoardNumbers.height + 1);// y
		}
	}

	public void start() {
		super.start();
		if (drawAll())
			setStatus(Status.DoSomeWork);
		else
			setStatus(Status.ComingSoon);
	}

	/**
	 * Launching a game depending on the chosen letters and numbers
	 */
	public void changeGame() {
		// if the class for the current game was found
		if (c != null) {
			try {
				// gets constructor(speed, level)
				Class[] paramTypes = new Class[] { int.class, int.class,
						int.class };
				Constructor<Game> constructor = (Constructor<Game>) c
						.getConstructor(paramTypes);

				// gets parameters
				Object[] args = new Object[] { new Integer(getSpeed()),
						new Integer(getLevel()), new Integer(number) };
				// creates an instance of the game
				Game game = constructor.newInstance(args);
				// stop the splash animation timer
				if (splashTimer != null)
					splashTimer.cancel();
				// starts the selected game
				Main.setGame(game);
			} catch (Exception e) {
				setStatus(Status.ComingSoon);
			}
		}
	}

	@Override
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
			break;
		case KeyUp:
			prevNumber();
			break;
		case KeyDown:
			nextNumber();
			break;
		case KeyStart:
			changeGame();
			break;
		case KeyReset:
			// stop the splash animation timer
			if (splashTimer != null)
				splashTimer.cancel();
			Main.setGame(new SplashScreen());
			break;
		default:
			break;
		}

	}
}
