package com.kry.brickgame.splashes;

/**
 * @author noLive
 * 
 */
public class SnakeSplash extends Splash {

	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ E, E, E, B, E, B, F, F, E, E } }, {
			// 1
			{ E, E, B, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ E, E, E, B, F, F, F, E, E, E } }, {
			// 2
			{ E, E, B, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, F, F, E, E },//
			{ E, E, B, E, E, E, F, F, E, E },//
			{ E, E, F, E, E, E, F, F, E, E },//
			{ E, E, F, E, E, E, E, E, E, E },//
			{ E, E, F, F, E, E, E, E, E, E } }, {
			// 3
			{ E, E, B, E, E, E, E, E, E, E },//
			{ E, E, F, E, E, E, F, F, E, E },//
			{ E, E, F, E, E, E, F, F, E, E },//
			{ E, E, F, E, E, E, F, F, E, E },//
			{ E, E, F, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E } } };

	public SnakeSplash() {
		super(frameTable);
	}

}
