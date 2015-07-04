package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class RacingSplash extends Splash {
	private static final long serialVersionUID = -8749547688773888492L;
	
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
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E } }, {
			// 10
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E } }, {
			// 11
			{ E, E, E, E, F, E, E }, //
			{ E, E, E, F, F, F, E }, //
			{ E, E, E, E, F, E, E }, //
			{ E, E, E, F, E, F, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E } }, {
			// 12
			{ E, E, F, F, F, E, E }, //
			{ E, E, E, F, E, E, E }, //
			{ E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E } }, {
			// 14
			{ E, E, F, E, E, E, E }, //
			{ E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, F, E } }, {
			// 15
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, F, F } }, {
			// 16
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, E, F, E, E, F, E } }, {
			// 17
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, E, F, E, E, F, E }, //
			{ E, E, E, E, F, E, F } }, {
			// 18
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, F, F }, //
			{ E, F, E, E, E, F, E }, //
			{ F, E, F, E, F, E, F }, //
			{ E, E, E, E, E, E, E } }, {
			// 19
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, F, F }, //
			{ E, F, E, E, E, F, E }, //
			{ F, E, F, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 20
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, E, F }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 21
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, E, F }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 22
			{ E, E, E, E, F, F, F }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E } }, {
			// 23
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, E, F }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E } }, {
			// 24
			{ E, F, E, E, F, E, F }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, F, F } }, {
			// 25
			{ E, F, F, F, E, E, E }, //
			{ E, E, F, E, E, E, E }, //
			{ E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, F, F } }, {
			// 26
			{ E, E, F, F, F, E, E }, //
			{ E, E, E, F, E, E, E }, //
			{ E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, F, F, E, E, F, E } }, {
			// 27
			{ E, E, E, E, F, E, E }, //
			{ E, E, E, F, E, F, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, F, F }, //
			{ E, F, E, E, E, F, E } }, {
			// 28
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, F, F, E, F, F, F }, //
			{ E, F, E, E, E, F, E }, //
			{ F, E, F, E, F, E, F } }, {
			// 29
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, E, F, E, E, F, E }, //
			{ E, E, E, E, F, E, F } }, {
			// 30
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, F, E }, //
			{ E, F, E, E, F, F, F }, //
			{ F, E, F, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E } }, {
			// 31
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, E, F, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E } }, {
			// 32
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, F, E }, //
			{ F, E, F, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E } }, {
			// 33
			{ F, F, F, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E } }, {
			// 34
			{ E, F, E, E, E, E, E }, //
			{ F, E, F, E, E, F, E }, //
			{ E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, F, E }, //
			{ E, E, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E } }, {
			// 35
			{ F, E, F, E, E, E, E }, //
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
		final int SPLASH_DELAY = 80;
		return SPLASH_DELAY;
	}
}
