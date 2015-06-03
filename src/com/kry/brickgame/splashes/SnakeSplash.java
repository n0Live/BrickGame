package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class SnakeSplash extends Splash {
	private static final long serialVersionUID = -3561093655966432475L;
	/**
	 * Delay between frames
	 */
	private static final int SNAKE_SPLASH_DELAY = 200;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, F, F, B, E, E, E, E } }, {
	// 1
	{ E, E, E, E, F, F, B, E, E, E } }, {
	// 2
	{ E, E, E, E, E, F, F, B, E, E } }, {
	        // 3
	        { E, E, F, F, E, E, E, E, B, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 4
	        { E, E, E, E, E, E, E, F, B, E }, //
	        { E, E, E, E, E, E, F, F, E, E } }, {
	        // 5
	        { E, E, F, F, E, E, E, E, B, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, F, E, E } }, {
	        // 6
	        { E, E, F, F, E, E, E, E, B, E }, //
	        { E, E, F, F, E, E, E, E, B, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 7
	        { E, E, E, E, E, B, E, E, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, B, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 8
	        { E, E, E, E, E, E, E, E, B, E },//
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 9
	        { E, E, E, E, E, B, E, E, B, E },//
	        { E, E, E, E, E, E, E, E, F, E },//
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 10
	        { E, E, E, E, E, B, E, B, F, E },//
	        { E, E, E, E, E, E, E, E, F, E },//
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 11
	        { E, E, E, E, E, B, B, F, F, E },//
	        { E, E, E, E, E, E, E, E, F, E },//
	        { E, E, F, F, E, E, E, E, F, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 12
	        { E, E, E, E, E, B, B, F, F, E },//
	        { E, E, E, E, E, E, E, E, F, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 13
	        { E, E, E, E, E, B, F, F, F, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 14
	        { E, E, E, E, B, F, F, F, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 15
	        { E, E, E, B, F, F, F, E, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 16
	        { E, E, B, F, F, F, E, E, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 17
	        { E, B, F, F, F, E, E, E, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 18
	        { E, F, F, F, E, E, E, E, E, E },//
	        { E, B, E, E, E, E, E, E, E, E },//
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 19
	        { E, F, F, E, E, E, E, E, E, E },//
	        { E, F, E, E, E, E, E, E, E, E },//
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 20
	        { E, F, E, E, E, E, E, E, E, E },//
	        { E, F, E, E, E, E, E, E, E, E },//
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, F, E, E, E, E, E, E, E, E },//
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 22
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, E, E, E, E, E, E, E, E, E },//
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, B, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 23
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 24
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, B, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E } }, {
	        // 25
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, B, E, E }, //
	        { E, F, B, E, E, E, E, E, E, E } }, {
	        // 26
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, F, B, B, E, E, E, E, E, E } }, {
	// 27
	{ E, E, F, B, B, E, E, E, E, E } }, }; //
	
	public SnakeSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		return SNAKE_SPLASH_DELAY;
	}
}
