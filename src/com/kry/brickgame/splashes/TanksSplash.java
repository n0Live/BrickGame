package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class TanksSplash extends Splash {
	private static final long serialVersionUID = -5081031885912858164L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E } }, {
	        // 1
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, B, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 2
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, B, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 3
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, B, E, E, E, E }, //
	        { F, F, E, E, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F } }, {
	        // 4
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, F, E, B, E, E, E }, //
	        { F, F, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F } }, {
	        // 5
	        { F, F, E, E, F, E, E, E, E, E }, //
	        { E, F, F, F, F, F, E, B, E, E }, //
	        { F, F, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 6
	        { F, F, E, E, F, F, E, E, E, E }, //
	        { E, F, F, F, F, F, E, E, B, E }, //
	        { F, F, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, B, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 7
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, B }, //
	        { E, E, E, E, F, F, E, E, B, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 8
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, B, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 9
	        { E, E, E, E, E, E, E, E, B, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 10
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, F, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, F }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 11
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, F, F, B, E, F, F }, //
	        { E, E, E, F, F, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, B, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 12
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, B, F, F }, //
	        { E, E, E, E, F, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, B, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 13
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, B, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E } }, {
	        // 14
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, F, E, E, E, B, E, E, E }, //
	        { F, E, F, E, E, E, E, E, E, E } }, {
	        // 15
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, E, E, E, E }, //
	        { F, E, F, E, F, E, E, B, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 16
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, F, F, F, E, E, E, E }, //
	        { F, F, E, F, F, F, E, E, B, E }, //
	        { E, E, E, E, F, E, E, E, E, E } }, {
	        // 17
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, F, F, E, E, E, E }, //
	        { F, F, E, F, F, F, E, E, E, B }, //
	        { E, E, E, E, F, F, E, E, E, E } }, {
	        // 18
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, F, F, E, E, E, E }, //
	        { F, F, B, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E } }, };
	
	public TanksSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 200;
		return SPLASH_DELAY;
	}
}
