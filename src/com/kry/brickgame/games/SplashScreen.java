package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameUtils.insertCellsToBoard;
import static com.kry.brickgame.games.GameUtils.music;
import static com.kry.brickgame.games.GameUtils.playMusic;
import static com.kry.brickgame.games.GameUtils.sleep;
import static com.kry.brickgame.games.GameUtils.stopAllSounds;

import com.kry.brickgame.Main;
import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.boards.BoardNumbers;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Music;
import com.kry.brickgame.sound.SoundManager;

/**
 * @author noLive
 */
public class SplashScreen extends Game {
	private static final long serialVersionUID = 6213953274430176604L;
	
	private final Music welcome = Music.welcome;
	
	/**
	 * Whether to show a SplashScreen again?
	 */
	private boolean resetFlag;
	
	public SplashScreen() {
		super();
		SoundManager.prepare(music, welcome);
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
		sleep(ANIMATION_DELAY * 2);
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
			
			if (Thread.currentThread().isInterrupted()) return;
			
			clearBoard();
			sleep(ANIMATION_DELAY * 5);
			insertNumbers();
			sleep(ANIMATION_DELAY * 6);
		}
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
		boolean isRightDirection = (toX >= fromX);
		
		// left to right
		if (isRightDirection) {
			for (int i = fromX; i <= toX; i++) {
				
				if (Thread.currentThread().isInterrupted()) return false;
				
				// invert cells
				board.setCell(((board.getCell(i, y) == Cell.Empty) ? Cell.Full : Cell.Empty), i, y);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
			// right to left
		} else {
			for (int i = fromX; i >= toX; i--) {
				
				if (Thread.currentThread().isInterrupted()) return false;
				
				// invert cells
				board.setCell(((board.getCell(i, y) == Cell.Empty) ? Cell.Full : Cell.Empty), i, y);
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
		Board board = getBoard().clone();
		BoardNumbers boardNumber = new BoardNumbers();
		
		boardNumber.setNumber(BoardNumbers.intToNumbers(9));
		
		// upper left
		insertCellsToBoard(board, boardNumber.getBoard(), 1,
				board.getHeight() - boardNumber.getHeight() - 1);
		// lower left
		insertCellsToBoard(board, boardNumber.getBoard(), 1, boardNumber.getHeight());
		// upper right
		insertCellsToBoard(board, boardNumber.getBoard(), board.getWidth() - boardNumber.getWidth()
				- 1, board.getHeight() - boardNumber.getHeight() * 2);
		// lower right
		insertCellsToBoard(board, boardNumber.getBoard(), board.getWidth() - boardNumber.getWidth()
				- 1, 1);
		
		if (!Thread.currentThread().isInterrupted()) {
			setBoard(board);
		}
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None) return;
		
		if (containsKey(KeyPressed.KeyOnOff)) {
			keys.remove(KeyPressed.KeyOnOff);
			quit();
			return;
		}
		
		if (containsKey(KeyPressed.KeyMute)) {
			keys.remove(KeyPressed.KeyMute);
			setMuted(!isMuted());
		}
		
		if (containsKey(KeyPressed.KeyReset)) {
			resetFlag = true;
		}
		
		if (!keys.isEmpty()) {
			setStatus(Status.None);
		}
	}
	
	@Override
	protected void start() {
		setStatus(Status.DoSomeWork);
		
		playMusic(welcome);
		sleep(ANIMATION_DELAY);
		
		insertNumbers();
		
		// Splash screen will be run in a separate thread
		Thread splashScreenThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					animatedInvertBoard();
					blinkNumbers(5);
				}
			}
		}, "SplashScreen");
		
		splashScreenThread.start();
		
		// by pressing any key status sets to Status.None
		while (getStatus() == Status.DoSomeWork) {
			processKeys();
		}
		
		stopAllSounds();
		
		splashScreenThread.interrupt();
		// Waits for end of interrupting splashScreenThread
		while (splashScreenThread.isAlive()) {
			;// wait
		}
		
		if (resetFlag) {
			SplashScreen ss = new SplashScreen();
			// show actual speed and level
			ss.setLevel(getLevel());
			ss.setSpeed(getSpeed());
			Main.setGame(ss);
		} else {
			Main.setGame(Main.gameSelector.restart());
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
		boolean isUpDirection = (toY >= fromY);
		
		// bottom to top
		if (isUpDirection) {
			for (int i = fromY; i <= toY; i++) {
				
				if (Thread.currentThread().isInterrupted()) return false;
				
				// invert cells
				board.setCell(((board.getCell(x, i) == Cell.Empty) ? Cell.Full : Cell.Empty), x, i);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
			// top to bottom
		} else {
			for (int i = fromY; i >= toY; i--) {
				
				if (Thread.currentThread().isInterrupted()) return false;
				
				// invert cells
				board.setCell(((board.getCell(x, i) == Cell.Empty) ? Cell.Full : Cell.Empty), x, i);
				fireBoardChanged(board);
				sleep(ANIMATION_DELAY);
			}
		}
		return true;
	}
	
}
