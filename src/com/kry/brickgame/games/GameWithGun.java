package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameConsts.ANIMATION_DELAY;
import static com.kry.brickgame.games.GameUtils.checkCollision;
import static com.kry.brickgame.games.GameUtils.drawShape;
import static com.kry.brickgame.games.GameUtils.isFullLine;
import static com.kry.brickgame.games.GameUtils.playEffect;
import static com.kry.brickgame.games.GameUtils.sleep;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.boards.Board.Cell;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.shapes.GunShape;

/**
 * @author noLive
 */
public abstract class GameWithGun extends GameWithLives {
	private static final long serialVersionUID = -1845599776247419199L;
	
	/**
	 * The Gun
	 */
	protected GunShape gun;
	
	/**
	 * Array that stores the coordinates of the bullets
	 */
	protected final AtomicReferenceArray<AtomicIntegerArray> bullets;
	// is equals int[][] but atomic
	
	private final int bulletsArrayWidth, bulletsArrayHeight;
	
	/**
	 * The Game with gun
	 * 
	 * @param speed
	 *            initial value of the speed
	 * @param level
	 *            initial value of the level
	 * @param type
	 *            type of the game
	 */
	public GameWithGun(int speed, int level, int type) {
		super(speed, level, type);
		
		gun = new GunShape();
		
		bulletsArrayWidth = boardWidth;
		bulletsArrayHeight = boardHeight - gun.getHeight();
		
		bullets = new AtomicReferenceArray<>(new AtomicIntegerArray[bulletsArrayWidth]);
		initBullets(bullets);
	}
	
	/**
	 * Adding the cell to the board and increasing scores
	 * 
	 * @param board
	 *            the board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 */
	protected void addCell(Board board, int x, int y) {
		playEffect(Effects.add_cell);
		synchronized (lock) {
			// add the cell
			board.setCell(Cell.Full, x, y);
		}
		// increase scores
		setScore(getScore() + 1);
	}
	
	/**
	 * Adding the cell to the board and increasing scores, after that removing
	 * line if is filled.
	 * 
	 * @param board
	 *            the board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 */
	protected void addCellAndCheckLine(Board board, int x, int y) {
		addCell(board, x, y);
		removeFullLines(y);
	}
	
	/**
	 * Remove all bullets from the board
	 * 
	 * @param board
	 *            the board, which is necessary to remove the bullets
	 */
	protected void clearBullets(Board board) {
		synchronized (lock) {
			for (int i = 0; i < boardWidth; i++) {
				for (int j = 0; j < boardHeight; j++)
					// bullets.get(y) is blink
					if (board.getCell(i, j) == Cell.Blink) {
						board.setCell(Cell.Empty, i, j);
					}
			}
		}
	}
	
	/**
	 * Destroying (setting {@code Cell.Empty}) a single cell on the row above
	 * the specified coordinates {@code [x, y]}
	 * 
	 * @param x
	 *            x-coordinate of the cell from where shot will be made
	 * @param y
	 *            y-coordinate of the cell from where shot will be made
	 * @param hasTwoSmokingBarrels
	 *            if {@code true},then shot is made with two guns on the sides,
	 *            otherwise shot is made with one gun on the center
	 */
	protected void fire(int x, int y, boolean hasTwoSmokingBarrels) {
		if ((x < 0) || (x >= boardWidth) || (y < 0) || (y >= boardHeight)) return;
		
		for (int i = x - 1; i <= x + 1; i++) {
			if (((hasTwoSmokingBarrels) && (i == x)) || ((!hasTwoSmokingBarrels) && (i != x))) {
				continue;
			}
			if ((i >= 0) && (i < boardWidth)) {
				for (int j = 0; j < bullets.get(i).length(); j++) {
					if (bullets.get(i).get(j) == 0) {
						bullets.get(i).set(j, y);
						break;
					}
				}
			}
		}
		// playEffect(Effects.turn); <= removed due occurring audio artifacts
	}
	
	private void flight(boolean ofBullets) {
		synchronized (lock) {
			Board board = getBoard();
			clearBullets(board);
			for (int x = 0; x < bullets.length(); x++) {
				for (int y = 0; y < bullets.get(x).length(); y++) {
					if (Thread.currentThread().isInterrupted()) return;
					// if 0, than bullet is not exist
					if (bullets.get(x).get(y) > 0) {
						// if the bullet does not reach the border of the
						// board
						if (bullets.get(x).get(y) < boardHeight - 1) {
							// if under the bullet is filled cell
							if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
								if (ofBullets) {// flightOfBullets
									removeCell(board, x, bullets.get(x).get(y));
								} else {// flightOfMud
									// stop the bullet before the cell
									addCellAndCheckLine(board, x, bullets.get(x).get(y) - 1);
								}
								// remove the bullet
								bullets.get(x).set(y, 0);
							} else {
								// otherwise, continues flying
								board.setCell(Cell.Blink, x, bullets.get(x).getAndIncrement(y));
							}
						} else if (bullets.get(x).get(y) == boardHeight - 1) {
							if (ofBullets) { // flightOfBullets
								// if under the bullet is filled cell
								if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
									removeCell(board, x, bullets.get(x).get(y));
								}
								// show the bullet
								board.setCell(Cell.Blink, x, bullets.get(x).get(y));
								fireBoardChanged(board);
								sleep(ANIMATION_DELAY / 2);
								// remove the bullet
								board.setCell(Cell.Empty, x, bullets.get(x).get(y));
							} else {// flightOfMud
								// if under the bullet is filled cell
								if (board.getCell(x, bullets.get(x).get(y)) == Cell.Full) {
									// stop the bullet before the cell
									addCellAndCheckLine(board, x, bullets.get(x).get(y) - 1);
								} else {
									// stop the bullet on the border of the
									// board
									addCellAndCheckLine(board, x, bullets.get(x).get(y));
								}
							}
							// remove the bullet
							bullets.get(x).set(y, 0);
						}
						fireBoardChanged(board);
					}
				}
			}
		}
	}
	
	/**
	 * Processing the flight of bullets (destruction mode)
	 */
	protected void flightOfBullets() {
		flight(true);
	}
	
	/**
	 * Processing the flight of mud bullets (creation mode)
	 */
	protected void flightOfMud() {
		flight(false);
	}
	
	/**
	 * Filling the bullets array with zeros
	 * 
	 * @param bullets
	 *            the bullets array
	 */
	protected void initBullets(AtomicReferenceArray<AtomicIntegerArray> bullets) {
		for (int i = 0; i < bullets.length(); i++) {
			int[] array = new int[bulletsArrayHeight];
			Arrays.fill(array, 0);
			bullets.set(i, new AtomicIntegerArray(array));
		}
	}
	
	@Override
	protected void loss(int x, int y) {
		clearBullets(getBoard());
		super.loss(x, y);
	}
	
	/**
	 * Move the gun to a new location
	 * 
	 * @param x
	 *            x-coordinate position of the new location
	 * @param y
	 *            y-coordinate position of the new location
	 * @return {@code false} if there was a collision, otherwise {@code true}
	 */
	protected boolean moveGun(int x, int y) {
		if ((x < 0) || (x >= boardWidth) || (y < 0) || (y >= boardHeight)) return true;
		
		synchronized (lock) {
			Board board = getBoard();
			// Erase the gun to not interfere with the checks
			board = drawShape(board, curX, curY, gun, Cell.Empty);
			
			if (checkCollision(board, gun, x, y)) return false;
			
			// draw the gun on the new place
			setBoard(drawShape(board, x, y, gun, Cell.Full));
			
			curX = x;
			curY = y;
		}
		
		return true;
	}
	
	/**
	 * Erasing the cell from the board and increasing scores
	 * 
	 * @param board
	 *            the board
	 * @param x
	 *            x-coordinate of the cell
	 * @param y
	 *            y-coordinate of the cell
	 */
	protected void removeCell(Board board, int x, int y) {
		playEffect(Effects.hit_cell);
		synchronized (lock) {
			// remove the cell
			board.setCell(Cell.Empty, x, y);
		}
		// increase scores
		setScore(getScore() + 1);
	}
	
	/**
	 * Removing of a filled lines
	 */
	protected boolean removeFullLines(int y) {
		boolean result = false;
		
		Board board = getBoard();
		
		synchronized (lock) {
			if (isFullLine(board, y)) {
				// change status for stopping other work
				Status prevStatus = getStatus();
				setStatus(Status.DoSomeWork);
				
				playEffect(Effects.remove_line);
				
				animatedClearLine(board, curX, y);
				
				// erase the gun from the board before dropping ups lines
				board = drawShape(board, curX, curY, gun, Cell.Empty);
				
				// drop the lines up on the filled line
				for (int i = y; i > 0; i--) {
					for (int j = 0; j < boardWidth; j++) {
						board.setCell(board.getCell(j, i - 1), j, i);
					}
				}
				
				// restore the gun after dropping ups lines
				board = drawShape(board, curX, curY, gun, Cell.Full);
				
				setBoard(board);
				// increase scores
				setScore(getScore() + 10);
				// reset the bullets
				initBullets(bullets);
				
				// restore previous status
				setStatus(prevStatus);
				
				result = true;
			}
		}
		return result;
	}
	
	@Override
	protected void win() {
		clearBullets(getBoard());
		super.win();
	}
	
}
