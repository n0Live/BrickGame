package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.boards.BoardNumbers;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSound.Music;
import com.kry.brickgame.sound.SoundManager;

/**
 * @author noLive
 */
public class SplashScreen extends Game {
	private static final long serialVersionUID = 6213953274430176604L;
	
	private static final ExecutorService splashScreenThread = Executors.newSingleThreadExecutor();
	
	private final Music welcome = Music.welcome;
	
	/**
	 * Whether to show a SplashScreen again?
	 */
	private boolean resetFlag;
	
	public SplashScreen() {
		super();
		SoundManager.prepare(GameSound.music, welcome);
		resetFlag = false;
	}
	
	/**
	 * Animated walking in a spiral with inverting cells on the main board
	 */
	void animatedInvertBoard() {
		Board board = getBoard();
		// x: 0 --> board.width
		int fromX = 0;
		int toX = board.getWidth() - 1;
		// y: board.height --> 0
		int fromY = board.getHeight() - 1;
		int toY = 0;
		
		// until it reaches the middle of the board
		while (fromX != board.getWidth() / 2) {
			// spiral motion with a gradually narrowing
			if (!horizontalMove(fromX, toX, fromY--) || !verticalMove(fromY, toY, toX--)
					|| !horizontalMove(toX, fromX, toY++) || !verticalMove(toY, fromY, fromX++))
				return;
		}
		sleep(ANIMATION_DELAY * 5);
	}
	
	/**
	 * Blinking "9999" on the board specified number of times
	 * 
	 * @param repeatCount
	 *            the number of repeats of blinks
	 */
	void blinkNumbers(int repeatCount) {
		if (repeatCount <= 0) return;
		
		for (int i = 0; i < repeatCount; i++) {
			if (isInterrupted()) return;
			
			clearBoard();
			sleep(ANIMATION_DELAY * 5);
			insertNumbers();
			sleep(ANIMATION_DELAY * 6);
		}
	}
	
	@Override
	public Game call() {
		super.init();
		
		setStatus(Status.DoSomeWork);
		GameSound.playMusic(welcome);
		sleep(ANIMATION_DELAY);
		
		insertNumbers();
		
		// Splash screen will be run in a separate thread
		Future<?> splashScreenHandler = splashScreenThread.submit(new Runnable() {
			@Override
			public void run() {
				while (!isInterrupted()) {
					animatedInvertBoard();
					blinkNumbers(5);
				}
			}
		});
		
		// by pressing any key status sets to Status.None
		while (getStatus() == Status.DoSomeWork) {
			processKeys();
		}
		
		stopAllSounds();
		
		splashScreenHandler.cancel(true);
		
		if (resetFlag) {
			nextGame = new SplashScreen();
			// show actual speed and level
			nextGame.setLevel(getLevel());
			nextGame.setSpeed(getSpeed());
		} else {
			nextGame = new GameSelector();
		}
		return getNextGame();
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
	 * Animated horizontal moving and inverting cells
	 * 
	 * @param fromX
	 *            the starting x-coordinate
	 * @param toX
	 *            the finishing x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @return {@code false} if the current thread was interrupted, otherwise -
	 *         {@code true}
	 */
	private boolean horizontalMove(int fromX, int toX, int y) {
		Board board = getBoard();
		// define the direction by coordinates
		boolean isRightDirection = toX >= fromX;
		
		// left to right
		if (isRightDirection) {
			for (int i = fromX; i <= toX; i++) {
				if (isInterrupted()) return false;
				
				// invert cells
				board.setCell(board.getCell(i, y) == Cell.Empty ? Cell.Full : Cell.Empty, i, y);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
			// right to left
		} else {
			for (int i = fromX; i >= toX; i--) {
				if (isInterrupted()) return false;
				
				// invert cells
				board.setCell(board.getCell(i, y) == Cell.Empty ? Cell.Full : Cell.Empty, i, y);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
		}
		return true;
	}
	
	/**
	 * Draws a "9999" on the main board
	 */
	private void insertNumbers() {
		Board board = getBoard();
		
		BoardNumbers[] boardNumbers = new BoardNumbers[4];
		for (int i = 0; i < boardNumbers.length; i++) {
			boardNumbers[i] = new BoardNumbers();
		}
		
		/* Easter (New Year) egg */
		int year = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER
				&& calendar.get(Calendar.DAY_OF_MONTH) == 31) {
			year = Integer.parseInt(dateFormat.format(calendar.getTime())) + 1;
		} else if (calendar.get(Calendar.MONTH) == Calendar.JANUARY
				&& calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			year = Integer.parseInt(dateFormat.format(calendar.getTime()));
		}
		
		char[] numbers;
		if (year != 0) {
			numbers = Integer.toString(year).toCharArray();
		} else {
			numbers = "9999".toCharArray();
		}
		for (int i = 0; i < boardNumbers.length; i++) {
			boardNumbers[i].setNumber(BoardNumbers.charToNumbers(numbers[i]));
		}
		
		int k;
		// upper left
		k = 0;
		board = insertCellsToBoard(board, boardNumbers[k].getBoard(), 1, board.getHeight()
				- boardNumbers[k].getHeight() - 1);
		// upper right
		k = 1;
		board = insertCellsToBoard(board, boardNumbers[k].getBoard(), board.getWidth()
				- boardNumbers[k].getWidth() - 1, board.getHeight() - boardNumbers[k].getHeight()
				* 2);
		// lower left
		k = 2;
		board = insertCellsToBoard(board, boardNumbers[k].getBoard(), 1,
				boardNumbers[k].getHeight());
		// lower right
		k = 3;
		board = insertCellsToBoard(board, boardNumbers[k].getBoard(), board.getWidth()
				- boardNumbers[k].getWidth() - 1, 1);
		
		if (!isInterrupted()) {
			setBoard(board);
		}
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (keys.isEmpty() || getStatus() == Status.None) return;
		
		if (containsKey(KeyPressed.KeyMute)) {
			keys.remove(KeyPressed.KeyMute);
            if (!isMuted()){
                mute();
            }else{
                unmute();
            }
		}
		
		if (containsKey(KeyPressed.KeyReset)) {
			resetFlag = true;
		}
		
		// when status set to None - stop showing the SplashScreen
		setStatus(Status.None);
		
		if (containsKey(KeyPressed.KeyShutdown)) {
			keys.remove(KeyPressed.KeyShutdown);
			quit();
		}
		
	}
	
	/**
	 * Animated vertical moving and inverting cells
	 * 
	 * @param fromY
	 *            the starting y-coordinate
	 * @param toY
	 *            the finishing y-coordinate
	 * @param x
	 *            the x-coordinate
	 * @return {@code false} if the current thread was interrupted, otherwise -
	 *         {@code true}
	 */
	private boolean verticalMove(int fromY, int toY, int x) {
		Board board = getBoard();
		// define the direction by coordinates
		boolean isUpDirection = toY >= fromY;
		
		// bottom to top
		if (isUpDirection) {
			for (int i = fromY; i <= toY; i++) {
				if (isInterrupted()) return false;
				
				// invert cells
				board.setCell(board.getCell(x, i) == Cell.Empty ? Cell.Full : Cell.Empty, x, i);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
			// top to bottom
		} else {
			for (int i = fromY; i >= toY; i--) {
				if (isInterrupted()) return false;
				
				// invert cells
				board.setCell(board.getCell(x, i) == Cell.Empty ? Cell.Full : Cell.Empty, x, i);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
		}
		return true;
	}
	
}
