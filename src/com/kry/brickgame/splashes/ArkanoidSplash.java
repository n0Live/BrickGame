package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class ArkanoidSplash extends Splash {
	private static final long serialVersionUID = 5591689903182020140L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, F, F, F, F, E, E, E, E, E } }, {
			// 1
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, F, F, F, E, E, E, E, E } }, {
			// 2
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, F, F, F, E, E, E, E } }, {
			// 3
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 4
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 5
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 6
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 7
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, F, F, F, F } }, {
			// 8
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 9
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 10
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 11
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 12
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 13
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 14
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 15
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 16
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 17
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 18
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 19
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, F, F, F, F } }, {
			// 20
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, F, F, F } }, {
			// 21
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, F, F, F } }, {
			// 22
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, F, E, F, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 23
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, F, F, F, E } }, {
			// 24
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, F, F, F, E, E } }, {
			// 25
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, F, F, F, E, E, E } }, {
			// 26
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, F, F, E, E, E, E } }, {
			// 27
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, F, F, F, E, E, E, E } }, {
			// 28
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, F, F, F, E, E, E, E, E } }, {
			// 29
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, F, F, F, E, E, E, E, E, E } }, {
			// 30
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, F, F, F, E, E, E, E, E } }, {
			// 31
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, F, F, F, E, E, E, E, E } }, {
			// 32
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, F, F, F, E, E, E, E } }, {
			// 33
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, F, E, F, E, F, E, F, E, E }, //
			{ E, E, F, E, F, E, F, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, F, F, E, E, E, E } }, };
	
	public ArkanoidSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 150;
		return SPLASH_DELAY;
	}
}
