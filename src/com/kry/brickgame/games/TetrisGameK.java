package com.kry.brickgame.games;

import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.shapes.TetrisShape.Figures;

/**
 * @author noLive
 * 
 */
public class TetrisGameK extends TetrisGame {

	/**
	 * The Tetris with the changing of the figures instead of rotating
	 * 
	 * @see TetrisGame#TetrisGame(int, int, Rotation, int)
	 */
	public TetrisGameK(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	public void keyPressed(KeyPressed key) {
		// alternative processing of the KeyRotate
		if ((key == KeyPressed.KeyRotate) && (getStatus() == Status.Running)
				&& (!isFallingFinished)) {
			// if we have the super gun
			if (curPiece.getShape() == Figures.SuperGun) {
				// than shoot of it
				shoot(curX, curY + curPiece.minY());
			} else if (curPiece.getShape() == Figures.SuperMudGun) {
				mudShoot(curX, curY + curPiece.minY());
				// if the super point, than do nothing
			} else if (curPiece.getShape() != Figures.SuperPoint) {
				TetrisShape rotatedPiece;
				if (getRotation() == Rotation.Counterclockwise)
					rotatedPiece = TetrisShape.getPrevTetraminoes(curPiece);
				else
					rotatedPiece = TetrisShape.getNextTetraminoes(curPiece);
				tryMove(rotatedPiece, curX, curY);
			}
		} else
			keys.add(key);
	}

}
