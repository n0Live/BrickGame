package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class ArkanoidSplash extends Splash {
	private static final long serialVersionUID = 5591689903182020140L;
	
	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 1
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 2
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, F, F, F, F, F, F, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 3
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, F, F, F, F, F, F, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } } };
	
	public ArkanoidSplash() {
		super(frameTable);
	}
	
}
