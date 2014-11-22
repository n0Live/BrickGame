package com.kry.brickgame.boards;

import java.util.Random;

import com.kry.brickgame.games.GameUtils;

public class BricksWall extends Board {
	private static final long serialVersionUID = -5524132721347429556L;

	static final Cell[][][] preloadedBricks = new Cell[][][] { { { E } },//
			{ // 1
					{ E, F, E, F, E, E, F, E, F, E },
					{ E, E, F, E, E, E, E, F, E, E },//
					{ E, F, F, F, E, E, F, F, F, E },
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, E, F, F, E, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, E, E, F, F, F, F, E, E, E }, },//
			{// 2
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, F, F, F, F, F, F, F },//
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, E, E, F, F, F, F, E, E, E },
					{ E, E, E, F, F, F, F, E, E, E }, },//

			{// 3
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },//
					{ E, F, F, F, E, E, F, F, F, E },
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, F, F, F, E, E, F, F, F, E },
					{ E, F, F, F, F, F, F, F, F, E },
					{ F, F, F, F, F, F, F, F, F, F }, },//
			{// 4
					{ E, F, F, F, F, F, F, F, F, E },
					{ F, E, F, F, F, F, F, F, E, F },//
					{ F, F, E, F, F, F, F, E, F, F },
					{ F, F, F, E, F, F, E, F, F, F },
					{ F, F, E, F, F, F, F, E, F, F },
					{ F, E, F, F, F, F, F, F, E, F },
					{ E, F, F, F, F, F, F, F, F, E }, },//

			{// 5
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },//
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, F, F, F, F, F, F, F, F, E },
					{ F, F, F, F, F, F, F, F, F, F }, },//
			{// 6
					{ F, F, E, F, F, F, F, E, F, F },
					{ F, E, F, F, E, E, F, F, E, F },//
					{ E, E, F, E, F, F, E, F, E, E },
					{ E, F, F, F, F, F, F, F, F, E },
					{ F, F, F, F, E, E, F, F, F, F },
					{ F, F, F, F, E, E, F, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, E, F, F, F, F, F, F, E, E }, },//
			{// 7
					{ F, E, E, F, E, E, F, E, E, F },
					{ F, E, F, F, F, F, F, F, E, F },//
					{ F, E, F, F, F, F, F, F, E, F },
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, E, F, F, F, F, F, F, E, F },
					{ F, E, F, F, F, F, F, F, E, F },
					{ F, E, E, F, E, E, F, E, E, F }, },//
			{// 8
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, F, F, F, F, F, F, F, F, E },//
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, E, F, E, F, F, E, F, E, E },
					{ E, F, E, E, F, F, E, E, F, E },
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, E, F, F, F, F, F, F, E, E }, },//
			{ // 9
					{ F, F, E, E, F, F, E, E, F, F },
					{ E, F, F, E, F, F, E, F, F, E },//
					{ E, E, F, F, F, F, F, F, E, E },
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, F, F, F, F, F, F, F },
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, F, F, E, F, F, E, F, F, E },
					{ F, F, E, E, F, F, E, E, F, F }, },//
			{// 10
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, F, F, F, F, F, E, F, F, E },//
					{ F, F, E, E, F, F, F, F, F, F },
					{ E, F, F, E, F, F, E, E, E, E },
					{ E, F, F, F, F, F, F, F, F, E },
					{ E, E, F, F, F, F, F, F, E, E },
					{ E, E, E, F, F, F, F, E, E, E },
					{ E, E, E, E, F, F, E, E, E, E }, },//
	};

	/**
	 * Number of bricks is not broken at the current level
	 */
	private int bricksCount;

	/**
	 * Creating the bricks wall
	 * 
	 * @param level
	 *            determines the type of bricks wall. Must be from 1 to 10
	 * @param usePreloadedBricks
	 *            if {@code true}, then use preloaded bricks wall, otherwise -
	 *            generate new ones
	 */
	public BricksWall(int level, boolean usePreloadedBricks) {
		// for generated bricks wall is height from 6 on level 1, to 9 on level
		// 10
		super(preloadedBricks[level][0].length,
				usePreloadedBricks ? preloadedBricks[level].length
						: level / 2 + 5);

		if (usePreloadedBricks
				&& ((level >= 0) && (level < preloadedBricks.length)))
			setPreloadedBricks(level);
		else
			generateBricks();
	}

	/**
	 * The copy constructor of a bricks wall
	 * 
	 * @param aBricksWall
	 *            - a bricks wall for copying
	 */
	public BricksWall(BricksWall aBricksWall) {
		super(aBricksWall);

		setBricksCount(aBricksWall.getBricksCount());
	}

	/**
	 * Creating the randomly generated bricks wall
	 */
	private void generateBricks() {
		Random r = new Random();

		setBricksCount(0);

		// on one chance from 3 - the wall has a vertical symmetry
		boolean isVerticalSymmetry = (r.nextInt(3) == 0);
		// calculate center for a vertical symmetry
		// (for odd and even lines count)
		int centerPoint = getHeight() / 2 + ((getHeight() % 2 != 0) ? 1 : 0);

		for (int i = 0; i < getHeight(); i++) {
			if (isVerticalSymmetry && (i >= centerPoint)) {

				setRow(getRow(getHeight() - 1 - i), i);

				for (int j = 0; j < getWidth() / 2; j++) {
					// calculate count of the bricks
					if (getCell(j, i) == Cell.Full)
						setBricksCount(getBricksCount() + 2);
				}
			} else {
				for (int j = 0; j < getWidth() / 2; j++) {
					// the chance for full cell 2 to 3
					Cell brick = (r.nextInt(3) == 0) ? Cell.Empty : Cell.Full;
					setCell(brick, j, i);
					setCell(brick, getWidth() - 1 - j, i);

					// calculate count of the bricks
					if (brick == Cell.Full)
						setBricksCount(getBricksCount() + 2);
				}
			}
		}
	}

	/**
	 * Setting predefined bricks wall
	 */
	private void setPreloadedBricks(int level) {
		setBricksCount(0);
		for (int i = 0; i < getHeight(); i++) {
			// the bricks is filled from the bottom up
			setRow(preloadedBricks[level][getHeight() - 1 - i], i);

			// calculate count of the bricks
			for (int j = 0; j < getWidth(); j++) {
				if (getCell(j, i) == Cell.Full)
					setBricksCount(getBricksCount() + 1);
			}
		}

	}

	/**
	 * Shift the bricks wall horizontally on the delta x
	 * 
	 * @param dX
	 *            delta x, if {@code dX > 0} then shift to the right, otherwise
	 *            shift to the left
	 */
	public void shift(int dX) {
		setBoard(GameUtils.boardHorizontalShift(this, dX).getBoard());
	}

	/**
	 * Processing breaking bricks
	 * 
	 * @param x
	 *            x-coordinate of the brick for breaking
	 * @param y
	 *            y-coordinate of the brick for breaking
	 */
	public boolean breakBrick(int x, int y) {
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))
			return false;

		if (getCell(x, y) != Cell.Empty) {
			setCell(Cell.Empty, x, y);
			// decrease bricks count
			setBricksCount(getBricksCount() - 1);

			return true;
		} else
			return false;
	}

	public int getBricksCount() {
		return bricksCount;
	}

	public void setBricksCount(int bricksCount) {
		this.bricksCount = bricksCount;
	}

}
