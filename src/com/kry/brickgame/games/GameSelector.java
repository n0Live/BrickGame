package com.kry.brickgame.games;

import static com.kry.brickgame.IO.ScoresManager.getScoresManager;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.playEffect;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.kry.brickgame.Main;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.BoardLetters;
import com.kry.brickgame.boards.BoardNumbers;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
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
	private static Map<Character, String> gamesList;
	static {
		gamesList = new HashMap<Character, String>();
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
		gamesList.put('Y', "com.kry.brickgame.games.???");
		gamesList.put('Z', "com.kry.brickgame.games.???");
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
	Splash splash;
	
	/**
	 * Timer for the splash screen of the game
	 */
	transient private Timer splashTimer;
	
	public GameSelector() {
		super();
		splash = null;
		restart();
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
					paramTypes = new Class[] { int.class, int.class, Rotation.class, int.class };
					constructor = c.getConstructor(paramTypes);
					// gets parameters
					args = new Object[] { getSpeed(), getLevel(), getRotation(), number };
				} catch (NoSuchMethodException e) {
					// if constructor with rotation is not exist,
					// gets constructor(speed, level, type)
					paramTypes = new Class[] { int.class, int.class, int.class };
					constructor = c.getConstructor(paramTypes);
					// gets parameters without rotation
					args = new Object[] { getSpeed(), getLevel(), number };
				}
				
				// creates an instance of the game
				Game game = constructor.newInstance(args);
				// stop the splash animation timer
				if (splashTimer != null) {
					splashTimer.cancel();
				}
				// starts the selected game
				Main.setGame(game);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
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
			splashTimer.cancel();
		}
		
		drawLetter(letter);
		
		try {
			c = (Class<Game>) Class.forName(gamesList.get(letter));
			
			// trying to get number of subtypes from the class of the game
			try {
				maxNumber = c.getField("subtypesNumber").getInt(c);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
					| SecurityException e) {
				e.printStackTrace();
				// if unable - sets 1
				maxNumber = 1;
			}
			
			// trying to get the splash screen instance from the class of
			// the game
			try {
				splash = (Splash) c.getField("splash").get(c);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
					| SecurityException e) {
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
							@Override
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
			
			// show high scores
			fireInfoChanged(String.valueOf("HI"
					+ getScoresManager().getHiScore(c.getCanonicalName())));
			
		} catch (ClassNotFoundException e) {
			c = null;
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
	void drawGameSplash(Splash splash) {
		if (splash != null) {
			insertBoard(splash.getNextFrame(), 0,// x
					BoardNumbers.height + 1);// y
		} else {
			Board clear = new Board(Splash.width, Splash.height);
			insertBoard(clear, 0,// x
					BoardNumbers.height + 1);// y
		}
	}
	
	/**
	 * Displays a letter at the top of the basic board
	 */
	private void drawLetter(char letter) {
		BoardLetters boardLetter = new BoardLetters();
		boardLetter.setLetter(BoardLetters.charToLetters(letter));
		insertBoard(boardLetter, (boardWidth / 2 - BoardLetters.width / 2 - 1),// x
				boardHeight - BoardLetters.height);// y
	}
	
	/**
	 * Displays a two numbers at the bottom of the basic board
	 */
	private void drawNumber(int number) {
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
		boardNumber.setNumber(BoardNumbers.intToNumbers(number_1));
		insertBoard(boardNumber, (boardWidth / 2 - BoardNumbers.width - 1),// x
				0);// y
		
		// 2nd number
		boardNumber.setNumber(BoardNumbers.intToNumbers(number_2));
		insertBoard(boardNumber, (boardWidth / 2),// x
				0);// y
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
	 */
	private void insertBoard(Board boardToInsert, int x, int y) {
		insertCellsToBoard(getBoard(), boardToInsert.getBoard(), x, y);
	}
	
	@Override
	public void keyPressed(KeyPressed key) {
		switch (key) {
		case KeyLeft:
			playEffect(Effects.select);
			setSpeed(getSpeed() + 1);
			break;
		case KeyRight:
			playEffect(Effects.select);
			setLevel(getLevel() + 1);
			break;
		case KeyRotate:
			playEffect(Effects.select);
			if (getRotation() == Rotation.Counterclockwise) {
				prevLetter();
			} else {
				nextLetter();
			}
			break;
		case KeyUp:
			playEffect(Effects.select);
			nextNumber();
			break;
		case KeyDown:
			playEffect(Effects.select);
			prevNumber();
			break;
		case KeyStart:
			playEffect(Effects.select);
			changeGame();
			break;
		case KeyMute:
			playEffect(Effects.select);
			setMuted(!isMuted());
			break;
		case KeyReset:
			// stop the splash animation timer
			if (splashTimer != null) {
				splashTimer.cancel();
			}
			SplashScreen ss = new SplashScreen();
			// show actual speed and level
			ss.setLevel(getLevel());
			ss.setSpeed(getSpeed());
			Main.setGame(ss);
			break;
		case KeyOnOff:
			if (splashTimer != null) {
				splashTimer.cancel();
			}
			quit();
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * Next allowable letter
	 */
	private void nextLetter() {
		if (letter < 'Z') {
			letter++;
		} else {
			letter = 'A';
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
		number = (number < maxNumber) ? number + 1 : 1;
		drawNumber(number);
	}
	
	/**
	 * Previous allowable letter
	 */
	private void prevLetter() {
		if (letter > 'A') {
			letter--;
		} else {
			letter = 'Z';
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
		number = (number > 1) ? number - 1 : maxNumber;
		drawNumber(number);
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
	
	@Override
	public void setRotation(Rotation rotation) {
		super.setRotation(rotation);
	}
	
	@Override
	public void start() {
		clearBoard();
		clearPreview();
		
		// show default rotation
		fireRotationChanged(getRotation());
		
		if (drawAll()) {
			setStatus(Status.DoSomeWork);
		} else {
			setStatus(Status.ComingSoon);
		}
	}
}
