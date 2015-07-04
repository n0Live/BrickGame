package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class PentixSplash extends Splash {
	private static final long serialVersionUID = -3040854159282989785L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, E, F, F, E, E, E, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, F, E, F, F, F }, //
			{ F, F, F, E, F, F, E, F, F, F } }, {
			// 1
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, F, F, E, E, E, E, E },//
			{ F, F, F, F, E, F, E, F, F, F },//
			{ F, F, F, F, F, F, E, F, F, F } }, {
			// 2
			{ E, E, E, E, E, F, F, F, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E },//
			{ E, E, E, E, E, E, F, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ E, E, E, E, E, E, E, E, E, E },//
			{ F, E, E, F, F, E, E, E, E, E },//
			{ F, F, F, F, E, F, E, F, F, F },//
			{ F, F, F, F, F, F, E, F, F, F } }, {
			// 3
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, F, F, F, F, F, E, E },//
			{ F, F, F, F, E, F, F, F, F, F } } };
	
	public PentixSplash() {
		super(frameTable);
	}
	
}
