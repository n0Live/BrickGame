package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class DanceSplash extends Splash {
	private static final long serialVersionUID = 4698308435944509589L;
	
	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, F, E, F }, //
			{ E, F, F, F, E }, //
			{ F, E, F, E, E }, //
			{ E, E, F, E, E }, //
			{ E, E, F, E, E }, //
			{ E, E, F, E, E }, //
			{ E, F, E, F, E }, //
			{ F, E, E, F, E } }, {
			// 1
			{ F, E, F, E, E }, //
			{ E, F, F, F, E }, //
			{ E, E, F, E, F }, //
			{ E, E, F, E, E }, //
			{ E, E, F, E, E }, //
			{ E, E, F, E, E }, //
			{ E, F, E, F, E }, //
			{ E, F, E, E, F } } };
	
	public DanceSplash() {
		super(frameTable);
	}
	
}
