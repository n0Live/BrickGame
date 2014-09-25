package com.kry.brickgame.games;

import java.util.Random;

import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.shapes.TetrisShape;
import com.kry.brickgame.splashes.PentixSplash;
import com.kry.brickgame.splashes.Splash;

/**
 * @author noLive
 * 
 */
public class PentixGame extends TetrisGame {
	/**
	 * Animated splash for game
	 */
	public static final Splash splash = new PentixSplash();

	/**
	 * The Tetris with the Pentominoes figures
	 * 
	 * @see TetrisGame#TetrisGame(int, int, Rotation, int)
	 */
	public PentixGame(int speed, int level, Rotation rotation, int type) {
		super(speed, level, rotation, type);
	}

	@Override
	protected TetrisShape setPieceFromType(int type) {
		TetrisShape newPiece = null;
		Random r = new Random();

		switch (type) {
		case 2:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0 });
			break;
		case 3:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 1 });
			break;
		case 4:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 1 });
			break;
		case 5:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 2 });
			break;
		case 6:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 2 });
			break;
		case 7:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 1, 2 });
			break;
		case 8:
			newPiece = TetrisShape
					.getRandomShapeAndSuper(new int[] { 0, 1, 2 });
			break;
		case 9:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 3 });
			break;
		case 10:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 3 });
			break;
		case 11:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 1, 3 });
			break;
		case 12:
			newPiece = TetrisShape
					.getRandomShapeAndSuper(new int[] { 0, 1, 3 });
			break;
		case 13:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 2, 3 });
			break;
		case 14:
			newPiece = TetrisShape
					.getRandomShapeAndSuper(new int[] { 0, 2, 3 });
			break;
		case 15:
			newPiece = TetrisShape
					.getRandomShapeAndSuper(new int[] { 1, 2, 3 });
			break;
		case 16:
			newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 1, 2,
					3 });
			break;
		case 17:
		case 33:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			}
			break;
		case 18:
		case 34:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0 });
			break;
		case 19:
		case 35:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 1 });
			break;
		case 20:
		case 36:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 0, 1 });
			break;
		case 21:
		case 37:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 2 });
			break;
		case 22:
		case 38:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				;
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 0, 2 });
			break;
		case 23:
		case 39:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 1, 2 });
			break;
		case 24:
		case 40:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomTetraminoes();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 1,
						2 });
			break;
		case 25:
		case 41:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 3 });
			break;
		case 26:
		case 42:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 0, 3 });
			break;
		case 27:
		case 43:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 1, 3 });
			break;
		case 28:
		case 44:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 1,
						3 });
			break;
		case 29:
		case 45:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape
						.getRandomShapeAndSuper(new int[] { 2, 3 });
			break;
		case 30:
		case 46:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 2,
						3 });
			break;
		case 31:
		case 47:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 1, 2,
						3 });
			break;
		case 32:
		case 48:
		case 49:
		case 50:
			if (r.nextInt(7) == 0) {
				newPiece = TetrisShape.getRandomShapeAndRotate();
				newPiece.setFill(Cell.Blink);
			} else
				newPiece = TetrisShape.getRandomShapeAndSuper(new int[] { 0, 1,
						2, 3 });
			break;
		default:
			newPiece = TetrisShape.getRandomShapeAndRotate();
			;
			break;
		}

		return newPiece;
	}

}
