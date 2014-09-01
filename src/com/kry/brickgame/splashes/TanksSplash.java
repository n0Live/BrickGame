package com.kry.brickgame.splashes;

import com.kry.brickgame.Board.Cell;

/**
 * @author noLive
 * 
 */
public class TanksSplash extends Splash {

	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ F, F, E, E, E, E, E, E }, //
			{ E, F, F, E, E, E, E, E }, //
			{ F, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, F }, //
			{ E, E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, E, F, F } }, {
			// 1
			{ E, E, E, F, E, F, E, E }, //
			{ E, E, E, F, F, F, E, E }, //
			{ E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, F, F, F, E }, //
			{ E, E, E, E, F, F, F, E } }, {
			// 2
			{ E, E, E, F, E, F, E, E }, //
			{ E, E, E, F, F, F, E, E }, //
			{ E, E, E, E, F, F, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, F, F, F, E }, //
			{ E, E, E, E, F, F, F, E } }, {
			// 3
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, F, F, F, E }, //
			{ E, E, E, E, F, F, F, E } } };

	public TanksSplash() {
		super(frameTable);
	}

}
