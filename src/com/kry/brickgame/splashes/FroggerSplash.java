package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class FroggerSplash extends Splash {
	private static final long serialVersionUID = -5173782844759282L;
	
	private static Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ F, F, E, E, E, E, F, F, E, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, E, E, F, F, E, E, E, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, B, E, E, E, F, F, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, }, {
			// 1
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, F, F, E, E, E, E, F, F, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, E, B, E, F, F, E, E, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, F, F, E, E, E, E, F, F }, //
			{ F, F, F, F, F, F, F, F, F, F }, }, {
			// 2
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, F, F, E, E, E, E, F, F }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ F, E, E, E, B, E, F, F, E, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, E, F, F, E, E, E, E, F }, //
			{ F, F, F, F, F, F, F, F, F, F }, }, {
			// 3
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ E, E, E, F, F, E, E, E, E, F }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ F, F, E, B, E, E, E, F, F, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, //
			{ F, E, E, E, F, F, E, E, E, E }, //
			{ F, F, F, F, F, F, F, F, F, F }, } };
	
	public FroggerSplash() {
		super(frameTable);
	}
	
}
