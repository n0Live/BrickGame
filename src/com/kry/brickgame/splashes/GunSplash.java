package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class GunSplash extends Splash {
	private static final long serialVersionUID = -8553198050273372439L;
	/**
	 * Delay between frames
	 */
	private static final int GUN_SPLASH_DELAY = 100;
	
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
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 2
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 3
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 4
	        { E, F, E, F, F, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 5
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 6
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
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 9
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, B, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 10
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, B, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 11
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
	        { E, E, E, E, E, B, E, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 14
	        { E, E, E, E, E, B, E, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 15
	        { E, E, E, E, E, B, E, F, F, F }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 16
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
	        { E, E, E, E, E, E, E, E, B, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, B }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, F, F } }, {
	        // 20
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, B }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, F, F } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, B }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, B }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, F, F } }, {
	        // 22
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
	        { E, F, E, E, F, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 25
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 26
	        { E, E, E, E, E, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 27
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E } }, {
	        // 28
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 29
	        { E, E, E, E, B, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 30
	        { E, E, E, E, B, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 31
	        { E, E, E, E, B, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 32
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 33
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 34
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
		return GUN_SPLASH_DELAY;
	}
}
