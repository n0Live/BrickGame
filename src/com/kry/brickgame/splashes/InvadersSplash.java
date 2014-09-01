package com.kry.brickgame.splashes;

/**
 * @author noLive
 * 
 */
public class InvadersSplash extends Splash {

	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 1
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 2
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, F, F, F, E, E, E } }, {
			// 3
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, E, F, F, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, F, F, F, E, E, E } } };

	public InvadersSplash() {
		super(frameTable);
	}

}
