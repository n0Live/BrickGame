package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class GunSplash extends Splash {
	private static final long serialVersionUID = -8553198050273372439L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
			// 0
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, E, E, E, E, E } }, {
			// 1
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, E, E, E, E, E } }, {
			// 2
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, E, E, E, E, E } }, {
			// 3
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E, E, E, E } }, {
			// 4
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ E, F, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E, E, E, E } }, {
			// 5
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ E, F, E, F, F, E, E, F, F, F }, //
			{ B, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, F, F, F, E, E, E, E, E, E } }, {
			// 6
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ B, F, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, F, F, F, E, E, E, E, E, E } }, {
			// 7
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, F, E, F, F, E, E, F, F, F }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, B, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, F, E, E, E, E, E } }, {
			// 8
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, B, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, F, E, E, E, E, E } }, {
			// 9
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, F, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, B, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 10
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, F, F, E, E, F, F, F }, //
			{ E, E, E, B, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 11
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, E, F, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, F, F, F, E, E, E } }, {
			// 12
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, B, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, F, F, F, E, E, E } }, {
			// 13
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, B, E, E, E, E }, //
			{ E, E, E, E, E, E, B, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, F, F, F, E, E } }, {
			// 14
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, B, E, E, E, E }, //
			{ E, E, E, E, E, E, B, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, F, F, F, E, E } }, {
			// 15
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, F, F, E, F }, //
			{ E, E, E, E, E, B, E, F, F, F }, //
			{ E, E, E, E, E, E, B, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, F, F, F, E } }, {
			// 16
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, B, F, F, E, F }, //
			{ E, E, E, E, E, E, B, F, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, F, F, F, E } }, {
			// 17
			{ F, F, E, F, F, B, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, F, F, F }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, B, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, F, F, F } }, {
			// 18
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, F, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, B, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, F, F, F } }, {
			// 19
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, F, F }, //
			{ E, E, E, E, E, E, E, E, B, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, F, F } }, {
			// 20
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, F, F } }, {
			// 21
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, F }, //
			{ E, E, E, E, E, E, E, E, F, F } }, {
			// 22
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, F, F, F } }, {
			// 23
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, F }, //
			{ E, E, E, E, E, E, E, E, E, B }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, F, E }, //
			{ E, E, E, E, E, E, E, F, F, F } }, {
			// 24
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, F, F, F, E } }, {
			// 25
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, F, E, E }, //
			{ E, E, E, E, E, E, F, F, F, E } }, {
			// 26
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, F, E, E }, //
			{ E, E, E, E, E, E, E, B, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, F, E, E, E }, //
			{ E, E, E, E, E, F, F, F, E, E } }, {
			// 27
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, F, E, E, E, E }, //
			{ E, E, E, E, F, F, F, E, E, E } }, {
			// 28
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 29
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, F, E, E, E, E, E }, //
			{ E, E, E, F, F, F, E, E, E, E } }, {
			// 30
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, F, E, E, E, E, E, E }, //
			{ E, E, F, F, F, E, E, E, E, E } }, {
			// 31
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, F, E, E, E, E, E }, //
			{ E, E, E, E, B, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, F, E, E, E, E, E, E, E }, //
			{ E, F, F, F, E, E, E, E, E, E } }, {
			// 32
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E, E, E, E } }, {
			// 33
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ F, F, F, E, E, E, E, E, E, E } }, {
			// 34
			{ F, F, E, E, F, E, F, F, E, F }, //
			{ F, F, E, F, F, E, E, F, F, F }, //
			{ E, F, E, E, E, E, E, E, E, E }, //
			{ E, B, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ E, E, E, E, E, E, E, E, E, E }, //
			{ F, E, E, E, E, E, E, E, E, E }, //
			{ F, F, E, E, E, E, E, E, E, E } }, };
	
	public GunSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 80;
		return SPLASH_DELAY;
	}
}
