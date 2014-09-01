package com.kry.brickgame.splashes;

/**
 * @author noLive
 * 
 */
public class TetrisSplash extends Splash {

	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, F, E, F, F, F }, //
			{ F, F, F, E, F, F, E, F, F, F } }, {
			// 1
			{ F, E, E, F, E, E, E, E, E, E },//
			{ F, F, F, F, E, F, E, F, F, F },//
			{ F, F, F, F, F, F, E, F, F, F } }, {
			// 2
			{ E, E, E, E, E, F, F, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E },//
			{ E, E, E, E, E, E, F, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ F, E, E, F, E, E, E, E, E, E },//
			{ F, F, F, F, E, F, E, F, F, F },//
			{ F, F, F, F, F, F, E, F, F, F } }, {
			// 3
			{ F, E, E, F, E, F, F, E, E, E },//
			{ F, F, F, F, E, F, F, F, F, F } } };

	public TetrisSplash() {
		super(frameTable);
	}

}
