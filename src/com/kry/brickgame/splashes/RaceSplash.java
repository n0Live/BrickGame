package com.kry.brickgame.splashes;

/**
 * @author noLive
 * 
 */
public class RaceSplash extends Splash {

	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E } }, {
			// 1
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 2
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 3
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } } };

	public RaceSplash() {
		super(frameTable);
	}

}
