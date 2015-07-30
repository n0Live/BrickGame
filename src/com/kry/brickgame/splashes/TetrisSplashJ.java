package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class TetrisSplashJ extends Splash {
	private static final long serialVersionUID = 534555333873259137L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 1
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 2
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 3
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 4
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, F, F, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 6
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E } }, {
	        // 7
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E } }, {
	        // 8 +
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 9
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 10
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { E, F, E, F, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 11
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 12
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 13
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 14
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, F, E, E, E, E, F, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 15
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, F, F, E, E, E }, //
	        { F, F, E, F, E, E, F, E, E, E }, //
	        { F, F, F, F, F, E, F, E, E, E }, //
	        { F, F, F, F, F, F, E, F, F, E }, //
	        { F, F, F, F, E, F, E, F, E, F } }, {
	        // 16
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 17
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 18
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 19
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 20
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, F, E, E, E, E, E, E, E, F }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 22
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, E, E, F }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 23
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 23.1
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 24
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 25
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 26
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F } }, {
	        // 27 +
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 28
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F }, //
	        { F, F, F, F, F, F, F, F, E, F } }, {
	        // 29
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F }, //
	        { F, F, F, F, F, F, F, F, E, F } }, {
	        // 30
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, F, E, E, E, E, E, E, F, E }, //
	        { F, F, E, F, E, E, E, E, E, E }, //
	        { F, F, F, F, E, F, F, F, E, F }, //
	        { F, F, F, F, F, F, F, F, E, F } }, {
	        // 31
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, F, F, E }, //
	        { F, F, E, F, E, E, E, E, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F }, //
	        { F, F, F, F, F, F, F, F, E, F } }, {
	        // 32
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 32.1
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 33
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F } }, {
	        // 34
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F } }, {
	        // 35
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F } }, {
	        // 36
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F } }, {
	        // 37
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, E, E, E, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, F } }, {
	        // 38
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 38.1
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E }, //
	        { B, B, B, B, B, B, B, B, B, B } }, };
	
	public TetrisSplashJ() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 200;
		return SPLASH_DELAY;
	}
	
}
