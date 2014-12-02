package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class SnakeSplash extends Splash {
	private static final long serialVersionUID = -3561093655966432475L;
	
	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, F, B, E, B, E, E, E } }, {
			// 1
			{ E, E, E, E, E, E, E, B, E, E },//
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, B, E, E, E } }, {
			// 2
			{ E, E, E, E, E, E, E, B, E, E },//
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, B, E, E }, //
			{ E, E, F, F, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, F, F, E, E } }, {
			// 3
			{ E, E, E, E, E, E, E, B, E, E },//
			{ E, E, F, F, E, E, E, F, E, E }, //
			{ E, E, F, F, E, E, E, F, E, E }, //
			{ E, E, F, F, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E } } }; //
	
	public SnakeSplash() {
		super(frameTable);
	}
	
}
