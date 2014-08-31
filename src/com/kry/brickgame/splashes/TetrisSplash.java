package com.kry.brickgame.splashes;


/**
 * @author noLive
 * 
 */
public class TetrisSplash extends GameSplash {

	private static Cell[][][] tetrisFrameTable = new Cell[][][] { { { E } },// 0
			{
					// 1
					{ E, E, E, F, E, E, E, E, E, E }, //
					{ E, E, F, F, E, E, E, E, E, E }, //
					{ E, E, E, F, E, E, E, E, E, E }, //
					{ E, E, E, E, E, E, E, E, E, E }, //
					{ E, E, E, E, E, E, E, E, E, E }, //
					{ F, E, E, E, E, E, E, E, E, E }, //
					{ F, F, E, E, E, F, E, F, F, F }, //
					{ F, F, F, E, F, F, E, F, F, F } }, {
					// 2
					{ F, E, E, F, E, E, E, E, E, E },//
					{ F, F, F, F, E, F, E, F, F, F },//
					{ F, F, F, F, F, F, E, F, F, F } }, {
					// 3
					{ E, E, E, E, E, F, F, E, E, E }, //
					{ E, E, E, E, E, E, F, E, E, E },//
					{ E, E, E, E, E, E, F, E, E, E },//
					{ E, E, E, E, E, E, E, E, E, E },//
					{ E, E, E, E, E, E, E, E, E, E },//
					{ F, E, E, F, E, E, E, E, E, E },//
					{ F, F, F, F, E, F, E, F, F, F },//
					{ F, F, F, F, F, F, E, F, F, F } }, {
					// 4
					{ F, E, E, F, E, F, F, E, E, E },//
					{ F, F, F, F, E, F, F, F, F, F } } };

	public TetrisSplash() {
		super(tetrisFrameTable);
	}

}
