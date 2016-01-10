package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class DanceSplash extends Splash {
	private static final long serialVersionUID = 4698308435944509589L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E } }, {
	        // 1
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, F, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, E, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E } }, {
	        // 2
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E } }, {
	        // 3
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } }, {
	        // 4
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E } }, {
	        // 5
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, F, E, E }, //
	        { E, E, E, F, E, E, E, E, E } }, {
	        // 6
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E } }, {
	        // 7
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E } }, {
	        // 8
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E } }, {
	        // 9
	        { E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, F, E, E }, //
	        { E, E, F, F, E, F, F, E, E } }, {
	        // 10
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E } }, {
	        // 11
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E } }, {
	        // 12
	        { E, E, F, F, F, E, E, E, E }, //
	        { E, F, E, E, E, F, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E } }, {
	        // 13
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } }, {
	        // 14
	        { E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, F, E, E, E, F, E }, //
	        { E, E, E, E, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E } }, {
	        // 15
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } }, {
	        // 16
	        { E, E, E, F, F, E, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, E, F, F, E, F, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E } }, {
	        // 17
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } }, {
	        // 18
	        { E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, E, F, F, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E } }, {
	        // 19
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, F, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } }, {
	        // 20
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E }, //
	        { E, F, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, F, F, E, F, F, E, E } }, {
	        // 22
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, E, E, F, E, E }, //
	        { E, E, E, F, F, F, E, E, E }, //
	        { E, E, F, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, F, E, E }, //
	        { E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, F, E, F, E, E, E } } };
	
	public DanceSplash() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 120;
		return SPLASH_DELAY;
	}
	
}
