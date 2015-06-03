package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class RacingSplash extends Splash {
	private static final long serialVersionUID = -8749547688773888492L;
	
	/**
	 * Delay between frames
	 */
	private static final int RACE_SPLASH_DELAY = 150;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
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
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, E, F }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 2
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, E, F }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 3
			{ E, E, E, E, F, F, F }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 4
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E } }, {
			// 5
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, E, F }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F } }, {
			// 6
			{ E, F, E, E, F, E, F }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E } }, {
			// 7
			{ F, E, F, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F } }, {
			// 8
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E } }, {
			// 9
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E } }, {
			// 10
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E } }, };
	
	public RacingSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		return RACE_SPLASH_DELAY;
	}
}
