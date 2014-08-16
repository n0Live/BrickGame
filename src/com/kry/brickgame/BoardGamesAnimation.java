package com.kry.brickgame;

import com.kry.brickgame.Board.Cells;

public class BoardGamesAnimation extends Board {

	enum GamesForAnimation {
		None, Dancing, Racing, Arcanoid, Snakes, Frogger, Tetris
	};

	private final static int width = Game.BOARD_WIDTH;
	private final static int height = 5;
	private final static int frames = 5;
	private final static Cells cE = Cells.Empty;
	private final static Cells cF = Cells.Full;

	private GamesForAnimation animation;

	/**
	 * Board with an animation
	 */
	private Cells[][] board = new Cells[width][height];

	/**
	 * Table animation to draw them on the board 10x5: [index][frame][y][x]
	 */
	private final static Cells[][][][] lettersTable = new Cells[][][][] { //
		{// None
			{ //None-1
				{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE } }
		},
		{// Dancing
			{ //Dancing-1
				{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE },
					{ cE, cE, cE, cE, cE, cE, cE, cE, cE, cE } }}
		};
}
