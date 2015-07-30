package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.boardHorizontalShift;

import com.kry.brickgame.boards.Board;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;

/**
 * @author noLive
 */
public class TetrisGameN extends TetrisGameJ {
	private static final long serialVersionUID = -5071920013183520180L;
	/**
	 * Animated splash for game
	 */
	public static final String splash = "com.kry.brickgame.splashes.TetrisSplashN";
	
	/**
	 * The Tetris with the addition of new lines every few seconds and the
	 * shifting board content
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameN(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}
	
	@Override
	void pieceDropped() {
		super.pieceDropped();
		if (getStatus() != Status.GameOver) {
			int dX = getRotation() == Rotation.CLOCKWISE ? 1 : -1;
			Board board = boardHorizontalShift(getBoard(), dX);
			setBoard(board);
		}
	}
	
}
