package com.kry.brickgame.games;

import com.kry.brickgame.shapes.Shape.RotationAngle;

/**
 * @author User
 */
public enum GameConsts {
	;
	
	public enum KeyPressed {
		KeyNone, KeyLeft, KeyRight, KeyUp, KeyDown, KeyRotate, KeyStart, KeyReset, KeyMute, KeyShutdown
	}
	
	public enum Rotation {
		NONE, CLOCKWISE, COUNTERCLOCKWISE;
		
		/**
		 * Get the next rotation
		 * <p>
		 * If current rotation is {@code None}, then return {@code None}
		 * 
		 * @return the next rotation
		 */
		public Rotation getNext() {
			// if None then getNext() return None
			if (this == Rotation.NONE) return this;
			return ordinal() < Rotation.values().length - 1 ? Rotation.values()[ordinal() + 1]
			        : Rotation.values()[1];
		}
	}
	
	public enum Status {
		None, Running, Paused, GameOver, DoSomeWork, ComingSoon
	}
	
	// ** Direction constants **
	public static final RotationAngle UP = RotationAngle.d0;
	public static final RotationAngle DOWN = RotationAngle.d180;
	public static final RotationAngle RIGHT = RotationAngle.d90;
	public static final RotationAngle LEFT = RotationAngle.d270;
	// **
	
	/**
	 * Width of the default board ({@value} )
	 */
	public static final int BOARD_WIDTH = 10;
	/**
	 * Height of the default board ({@value} )
	 */
	public static final int BOARD_HEIGHT = 20;
	/**
	 * Width of the default preview board ({@value} )
	 */
	public static final int PREVIEW_WIDTH = 4;
	/**
	 * Height of the default preview board ({@value} )
	 */
	public static final int PREVIEW_HEIGHT = 4;
	/**
	 * Animation delay in milliseconds
	 */
	public static final int ANIMATION_DELAY = 30;
	
	// ** anumatedClearBoard constants **
	public static final int CB_GAME_OVER = 6000;
	public static final int CB_WIN = 5240;
	public static final int CB_LOSE = 1200;
	// **
	
	public static final int MAX_GAME_SPEED = 10;
	public static final int MAX_GAME_LEVEL = 10;
	public static final int MAX_GAME_LIVES = 4;
	public static final int MAX_VISIBLE_SCORES = 19999;
}
