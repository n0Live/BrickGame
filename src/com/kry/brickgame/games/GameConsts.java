package com.kry.brickgame.games;

import com.kry.brickgame.shapes.Shape.RotationAngle;

/**
 * @author User
 */
public final class GameConsts {
	public static enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyReset, KeyMute, KeyShutdown
	};
	
	public static enum Rotation {
		None, Clockwise, Counterclockwise;
		
		/**
		 * Get the next rotation
		 * <p>
		 * If current rotation is {@code None}, then return {@code None}
		 * 
		 * @return the next rotation
		 */
		public Rotation getNext() {
			// if None then getNext() return None
			if (this == Rotation.None) return this;
			return ordinal() < Rotation.values().length - 1 ? Rotation.values()[ordinal() + 1]
					: Rotation.values()[1];
		}
	};
	
	public static enum Status {
		None, Running, Paused, GameOver, DoSomeWork, ComingSoon
	};
	
	// ** Direction constants **
	protected static final RotationAngle UP = RotationAngle.d0;
	protected static final RotationAngle DOWN = RotationAngle.d180;
	protected static final RotationAngle RIGHT = RotationAngle.d90;
	protected static final RotationAngle LEFT = RotationAngle.d270;
	// **
	
	/**
	 * Width of the default board ({@value} )
	 */
	protected static final int BOARD_WIDTH = 10;
	/**
	 * Height of the default board ({@value} )
	 */
	protected static final int BOARD_HEIGHT = 20;
	/**
	 * Width of the default preview board ({@value} )
	 */
	protected static final int PREVIEW_WIDTH = 4;
	/**
	 * Height of the default preview board ({@value} )
	 */
	protected static final int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	protected static final int ANIMATION_DELAY = 30;
	
	// ** anumatedClearBoard constants **
	protected static final int CB_GAME_OVER = 6000;
	protected static final int CB_WIN = 5240;
	protected static final int CB_LOSE = 1200;
	// **
}
