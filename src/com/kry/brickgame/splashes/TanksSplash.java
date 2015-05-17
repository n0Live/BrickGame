package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class TanksSplash extends Splash {
	private static final long serialVersionUID = -5081031885912858164L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
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
