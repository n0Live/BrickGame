package com.kry.brickgame.splashes;

/**
 * @author noLive
 * 
 */
public class SnakeSplash extends GameSplash {

	private static Cell[][][] snakeFrameTable = new Cell[][][] { { { E } },// 0
			{
					// 1
					{ E, E, F, F, E, E, E, E, E, E }, //
					{ E, E, F, F, E, E, E, E, E, E }, //
					{ E, E, E, E, E, E, E, E, E, E }, //
					{ E, E, F, F, B, E, B, E, E, E } }, {
					// 2
					{ E, E, E, E, E, E, E, B, E, E },//
					{ E, E, E, E, E, E, E, E, E, E },//

					{ E, E, F, F, E, E, E, E, E, E }, //
					{ E, E, F, F, E, E, E, E, E, E }, //
					{ E, E, E, E, E, E, E, E, E, E }, //
					{ E, E, E, F, F, F, B, E, E, E } }, {
					// 3
					{ E, E, E, E, E, E, E, B, E, E },//
					{ E, E, E, E, E, E, E, E, E, E },//
					{ E, E, F, F, E, E, E, B, E, E }, //
					{ E, E, F, F, E, E, E, F, E, E }, //
					{ E, E, E, E, E, E, E, F, E, E }, //
					{ E, E, E, E, E, E, F, F, E, E } }, {
					// 4
					{ E, E, E, E, E, E, E, B, E, E },//
					{ E, E, E, E, E, E, E, F, E, E },//
					{ E, E, F, F, E, E, E, F, E, E }, //
					{ E, E, F, F, E, E, E, F, E, E }, //
					{ E, E, E, E, E, E, E, F, E, E }, //
					{ E, E, E, E, E, E, E, E, E, E } } //
	};

	public SnakeSplash() {
		super(snakeFrameTable);
	}

}
