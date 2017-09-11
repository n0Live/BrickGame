package com.kry.brickgame.games;

import static com.kry.brickgame.IO.ScoresManager.getScoresManager;
import static com.kry.brickgame.IO.SettingsManager.getSettingsManager;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.BoardLetters;
import com.kry.brickgame.boards.BoardNumbers;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Effects;
import com.kry.brickgame.splashes.Splash;

/**
 * The selection screen of a game
 * 
 * @author noLive
 */
@SuppressWarnings("rawtypes")
public class GameSelector extends Game {
	private static final long serialVersionUID = -2388591192036308490L;
	/**
	 * List of games with the letters associated with them
	 */
	private static final Map<Character, String> gamesList;
    private static final char FIRST_GAME_LETTER = 'A';
    private static final char LAST_GAME_LETTER = 'X';
    
	static {
	       // Check letter constants
        assert (LAST_GAME_LETTER <= FIRST_GAME_LETTER);
        
		gamesList = new HashMap<>();
		gamesList.put('A', "com.kry.brickgame.games.DanceGame");
		gamesList.put('B', "com.kry.brickgame.games.TanksGame");
		gamesList.put('C', "com.kry.brickgame.games.ArkanoidGame");
		gamesList.put('D', "com.kry.brickgame.games.RacingGame");
		gamesList.put('E', "com.kry.brickgame.games.GunGame");
		gamesList.put('F', "com.kry.brickgame.games.SnakeGame");
		gamesList.put('G', "com.kry.brickgame.games.FroggerGame");
		gamesList.put('H', "com.kry.brickgame.games.InvadersGame");
		gamesList.put('I', "com.kry.brickgame.games.TetrisGameI");
		gamesList.put('J', "com.kry.brickgame.games.TetrisGameJ");
		gamesList.put('K', "com.kry.brickgame.games.TetrisGameK");
		gamesList.put('L', "com.kry.brickgame.games.TetrisGameL");
		gamesList.put('M', "com.kry.brickgame.games.TetrisGameM");
		gamesList.put('N', "com.kry.brickgame.games.TetrisGameN");
		gamesList.put('O', "com.kry.brickgame.games.TetrisGameO");
		gamesList.put('P', "com.kry.brickgame.games.TetrisGameP");
		gamesList.put('Q', "com.kry.brickgame.games.PentixGameQ");
		gamesList.put('R', "com.kry.brickgame.games.PentixGameR");
		gamesList.put('S', "com.kry.brickgame.games.PentixGameS");
		gamesList.put('T', "com.kry.brickgame.games.PentixGameT");
		gamesList.put('U', "com.kry.brickgame.games.PentixGameU");
		gamesList.put('V', "com.kry.brickgame.games.PentixGameV");
		gamesList.put('W', "com.kry.brickgame.games.PentixGameW");
		gamesList.put('X', "com.kry.brickgame.games.PentixGameX");
		//gamesList.put('Y', "com.kry.brickgame.games.???");
		//gamesList.put('Z', "com.kry.brickgame.games.???");
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
	 * Animated splash for a game
	 */
	private Splash splash;

	/**
	 * Timer for the splash screen of the game
	 */
	transient private ScheduledFuture<?> splashTimer;

	public GameSelector() {
		super();
		setRotation(getSettingsManager().getRotation());

		splash = null;
		restart();
	}

	public GameSelector(int speed, int level, String gameClassName, int type) {
		super(speed, level, getSettingsManager().getRotation(), type);
		setGameAndType(gameClassName, type);
	}

	@Override
	public Game call() {
		super.init();

		if (drawAll()) {
			setStatus(Status.DoSomeWork);
		} else {
			setStatus(Status.ComingSoon);
		}

		while (!isInterrupted()) {
			processKeys();
		}
		// stop the splash animation timer
		if (splashTimer != null) {
			splashTimer.cancel(true);
		}

		return getNextGame();
	}

	/**
	 * Launching a game depending on the chosen letters and numbers
	 */
	private void changeGame() {
		// if the class for the current game was found
		if (c != null) {
			try {
				Class[] paramTypes;
				Constructor<Game> constructor;
				Object[] args;

				try {
					// gets constructor(speed, level, rotation, type)
					paramTypes = new Class[] { int.class, int.class,
							Rotation.class, int.class };
					constructor = c.getConstructor(paramTypes);
					// gets parameters
					args = new Object[] { getSpeed(), getLevel(),
							getRotation(), number };
				} catch (NoSuchMethodException e) {
					// if constructor with rotation is not exist,
					// gets constructor(speed, level, type)
					paramTypes = new Class[] { int.class, int.class, int.class };
					constructor = c.getConstructor(paramTypes);
					// gets parameters without rotation
					args = new Object[] { getSpeed(), getLevel(), number };
				}
				// creates an instance of the game
				nextGame = constructor.newInstance(args);
				// starts the selected game
				exitToMainMenuFlag = true;
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				setStatus(Status.ComingSoon);
			}
		}
	}

	/**
	 * Displays all the necessary information on the game: letter, number,
	 * splash screen
	 */
	@SuppressWarnings("unchecked")
	private boolean drawAll() {
		// stop the splash animation timer
		if (splashTimer != null) {
			splashTimer.cancel(true);
		}

		Board board = getBoard();

		board = drawLetter(board, letter);

		try {
			c = (Class<Game>) Class.forName(gamesList.get(letter));

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
				String splashClassName = (String) c.getField("splash").get(c);
				Class<Splash> splashClass = (Class<Splash>) Class
						.forName(splashClassName);
				splash = splashClass.newInstance();
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException
					| InstantiationException e) {
				e.printStackTrace();
				splash = null;
			}

			// show high scores
			fireInfoChanged(String.valueOf("HI"
					+ getScoresManager().getHiScore(c.getCanonicalName())));

		} catch (ClassNotFoundException e) {
			c = null;
			splash = null;
			maxNumber = 1;
		}

		// checks that the current number does not exceed the maximum number
		if (number > maxNumber) {
			number = 1;
		}
		board = drawNumber(board, number);
		setBoard(board);

		// draw splash
		if (splash != null) {
			// starts the timer to show splash screen of the game
			final Splash localSplash = splash;
			splashTimer = scheduledExecutors.scheduleWithFixedDelay(
					new Runnable() {
						@Override
						public void run() {
							drawGameSplash(localSplash);
						}
					}, 0, localSplash.getDelay(), TimeUnit.MILLISECONDS);
		} else {
			// if unable - clears the rectangle of the splash screen
			drawGameSplash(null);
		}

		return c != null;
	}

	/**
	 * Displays one frame of the splash screen of the game
	 * <p>
	 * If splash is {@code null} then clears the rectangle of the splash screen
	 * 
	 * @param splash
	 *            splash screen instance
	 */
	void drawGameSplash(Splash splash) {
		Board board = getBoard();
		if (splash != null) {
			board = insertCellsToBoard(board, splash.getNextFrame().getBoard(),
					0, BoardNumbers.height + 1);
		} else {
			Board clear = new Board(Splash.width, Splash.height);
			board = insertCellsToBoard(board, clear.getBoard(), 0,
					BoardNumbers.height + 1);
		}
		setBoard(board);
	}

	/**
	 * Displays a letter at the top of the basic board
	 * 
	 * @param board
	 *            a board for drawing a letter
	 * @param letter
	 *            a letter
	 * @return a board with a drawn letter
	 */
	private Board drawLetter(Board board, char letter) {
		BoardLetters boardLetter = new BoardLetters();
		boardLetter.setLetter(BoardLetters.charToLetters(letter));
		Board result = insertCellsToBoard(board, boardLetter.getBoard(),
				boardWidth / 2 - BoardLetters.width / 2 - 1, boardHeight
						- BoardLetters.height);
		return result;
	}

	/**
	 * Displays a two numbers at the bottom of the basic board
	 * 
	 * @param board
	 *            a board for drawing a numbers
	 * @param number
	 *            a number
	 * @return a board with a drawn numbers
	 */
	private Board drawNumber(Board board, int number) {
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
		Board result = board;

		// 1st number
		boardNumber.setNumber(BoardNumbers.intToNumbers(number_1));
		result = insertCellsToBoard(result, boardNumber.getBoard(), boardWidth
				/ 2 - BoardNumbers.width - 1,// x
				0);
		// 2nd number
		boardNumber.setNumber(BoardNumbers.intToNumbers(number_2));
		result = insertCellsToBoard(result, boardNumber.getBoard(),
				boardWidth / 2,// x
				0);

		return result;
	}

	@Override
	protected int getSpeedOfFirstLevel() {
		return 0;
	}

	@Override
	protected int getSpeedOfTenthLevel() {
		return 0;
	}

	/**
	 * Next allowable letter
	 */
	private void nextLetter() {
        if (letter < LAST_GAME_LETTER) {
            letter++;
        } else {
            letter = FIRST_GAME_LETTER;
        }
        if (drawAll()) {
            setStatus(Status.DoSomeWork);
        } else {
            setStatus(Status.ComingSoon);
        }
	}

	/**
	 * Next allowable number
	 */
	private void nextNumber() {
		number = number < maxNumber ? number + 1 : 1;
		setBoard(drawNumber(getBoard(), number));
	}

	/**
	 * Previous allowable letter
	 */
	private void prevLetter() {
        if (letter > FIRST_GAME_LETTER) {
            letter--;
        } else {
            letter = LAST_GAME_LETTER;
        }
        if (drawAll()) {
            setStatus(Status.DoSomeWork);
        } else {
            setStatus(Status.ComingSoon);
        }
	}

	/**
	 * Previous allowable number
	 */
	private void prevNumber() {
		number = number > 1 ? number - 1 : maxNumber;
		setBoard(drawNumber(getBoard(), number));
	}

	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		// decrease CPU loading
		sleep(30);

		if (keys.isEmpty()) return;

		if (keys.contains(KeyPressed.KeyShutdown)) {
			keys.remove(KeyPressed.KeyShutdown);
			quit();
			return;
		}

		if (keys.contains(KeyPressed.KeyReset)) {
			keys.remove(KeyPressed.KeyReset);
			returnToSplashScreen();
			return;
		}

		// if keys contains any other key
		GameSound.playEffect(Effects.select);

		if (keys.contains(KeyPressed.KeyMute)) {
			keys.remove(KeyPressed.KeyMute);
			if (!isMuted()) {
				mute();
			} else {
				unmute();
			}
			return;
		}

		if (keys.contains(KeyPressed.KeyStart)) {
			keys.remove(KeyPressed.KeyStart);
			changeGame();
			return;
		}

		if (keys.contains(KeyPressed.KeyLeft)) {
			keys.remove(KeyPressed.KeyLeft);
			setSpeed(getSpeed() + 1);
		}

		if (keys.contains(KeyPressed.KeyRight)) {
			keys.remove(KeyPressed.KeyRight);
			setLevel(getLevel() + 1);
		}

		if (keys.contains(KeyPressed.KeyUp)) {
			keys.remove(KeyPressed.KeyUp);
			nextNumber();
		}

		if (keys.contains(KeyPressed.KeyDown)) {
			keys.remove(KeyPressed.KeyDown);
			prevNumber();
		}

		if (keys.contains(KeyPressed.KeyRotate)) {
			keys.remove(KeyPressed.KeyRotate);
			if (getRotation() == Rotation.COUNTERCLOCKWISE) {
				prevLetter();
			} else {
				nextLetter();
			}
		}
	}

	/**
	 * Returns {@code GameSelector} with displayed first letter and number.
	 * 
	 * @return {@code GameSelector}
	 */
	protected GameSelector restart() {
		letter = 'A';
		number = 1;
		return this;
	}

	/**
	 * Close game selector and start splash screen
	 */
	private void returnToSplashScreen() {
		nextGame = new SplashScreen();
		// show actual speed and level
		nextGame.setLevel(getLevel());
		nextGame.setSpeed(getSpeed());

		exitToMainMenuFlag = true;
	}

	/**
	 * Sets displaying letter and number related to specified game class name
	 * and game type.
	 * 
	 * @param gameClassName
	 *            canonical class name of a game
	 * @param type
	 *            type of a game
	 */
	private void setGameAndType(String gameClassName, int type) {
		number = type;
		for (Entry<Character, String> entry : gamesList.entrySet()) {
			if (entry.getValue().equals(gameClassName)) {
				letter = entry.getKey();
				break;
			}
		}
	}
}
