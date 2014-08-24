package com.kry.brickgame;


public class BoardGamesAnimation extends Board {

	public BoardGamesAnimation(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	enum GamesForAnimation {
		None, Dancing, Racing, Arcanoid, Snakes, Frogger, Tetris
	};

	private final static int width = Game.BOARD_WIDTH;
	private final static int height = 5;
	private final static int frames = 5;
	private final static Cell cE = Cell.Empty;
	private final static Cell cF = Cell.Full;

	private GamesForAnimation animation;

	/**
	 * Board with an animation
	 */
	private Cell[][] board = new Cell[width][height];

	/**
	 * Table animation to draw them on the board 10x5: [index][frame][y][x]
	 */
	private final static Cell[][][][] lettersTable = new Cell[][][][] { //
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
