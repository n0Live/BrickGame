package com.kry.brickgame.games;

import static com.kry.brickgame.games.GameUtils.playEffect;

import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.GameConsts.Rotation;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameUtils.Effects;
import com.kry.brickgame.shapes.TetrisShape;

/** 
 * @author noLive
 */
public class TetrisGameK extends TetrisGameI {
	private static final long serialVersionUID = -2765291209922783752L;
	
	/**
	 * The Tetris with the changing of the figures instead of rotating
	 * 
	 * @see TetrisGameI#TetrisGameI(int, int, Rotation, int)
	 */
	public TetrisGameK(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}
	
	/**
	 * Processing of key presses
	 */
	@Override
	protected void processKeys() {
		if (getStatus() == Status.None || keys.isEmpty()) return;
		if (getStatus() == Status.Running && !isFallingFinished
		        && containsKey(KeyPressed.KeyRotate) && !curPiece.isSuperShape()) {
			TetrisShape rotatedPiece;
			if (getRotation() == Rotation.Counterclockwise)
				rotatedPiece = TetrisShape.getPrevTetraminoes(curPiece);
			else
				rotatedPiece = TetrisShape.getNextTetraminoes(curPiece);
			
			if (tryMove(rotatedPiece, curX, curY)) playEffect(Effects.turn);
			keys.remove(KeyPressed.KeyRotate);
		} else
			super.processKeys();
	}
	
}
