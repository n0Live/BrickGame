package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class FroggerSplash extends Splash {
	private static final long serialVersionUID = -5173782844759282L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, E, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, B, E, E, E, E, E }, }, {
	        // 1
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, E, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, B, E, E, E, E, E, E }, }, {
	        // 2
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, B, E, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 3
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, E, B, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 4
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, B, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 5
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, E, B, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 6
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, F, F, F, B, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, F, F, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 7
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, F, F, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 8
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, E, E, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, B, E, E, E, E, E }, }, {
	        // 9
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, E, E, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, B, E, E, E, E, E, E }, }, {
	        // 10
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, B, E, F, F, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 11
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, E, E, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, B, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, E, F, F, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 12
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, B, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 13
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, B, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 14
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 15
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, F, F, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, B, E, E, E, E, E }, }, {
	        // 16
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, E, F, F, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, F, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, B, E, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 17
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, E, F, F, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, F, F, F, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, F, F, F, E, E, B, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 18
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, F, F, F, B, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 19
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, E, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, E, F, F, F, E, B, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 20
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, E, E, E, F, F, B }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, {
	        // 21
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, E, E, E, F, F, F, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, E, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, }, };
	
	public FroggerSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 350;
		return SPLASH_DELAY;
	}
}
