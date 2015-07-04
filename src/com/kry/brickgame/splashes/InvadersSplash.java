package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class InvadersSplash extends Splash {
	private static final long serialVersionUID = 8121034490271030749L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 1
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 2
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 3
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, E, E }, //
	        { B, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 4
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { B, F, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 5
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { B, F, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, B, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 6
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, E, E }, //
	        { E, B, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, B, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 7
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, B, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, B, E, E, F, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 8
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, E, E }, //
	        { E, E, B, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 9
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 10
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, B, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, F, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, {
	        // 11
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, F, E, E, E, E, E } }, {
	        // 12
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E } }, {
	        // 13
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, B, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 14
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, B, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 15
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 16
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 17
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 18
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, B, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E } }, {
	        // 19
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, B, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E } }, {
	        // 20
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, B, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 21
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, B, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 22
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 23
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 24
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F } }, {
	        // 25
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, F, E, F, F, F } }, {
	        // 26
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, F, F, F, F, E } }, {
	        // 27
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E } }, {
	        // 28
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 29
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E } }, {
	        // 30
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, B, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E } }, {
	        // 31
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 32
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E } }, {
	        // 33
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E } }, {
	        // 34
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E } }, };
	
	public InvadersSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 120;
		return SPLASH_DELAY;
	}
}
