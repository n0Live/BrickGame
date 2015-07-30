package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class TetrisSplashP extends Splash {
	private static final long serialVersionUID = -5478214198285461170L;
	
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
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 2
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 3
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E } }, {
	        // 4
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E }, //
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
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, F, F, E, F } }, {
	        // 8
	        { E, E, E, F, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, F, F, E, F } }, {
	        // 9 +
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, F, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 10
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, F, F, E, F }, //
	        { F, F, F, E, F, E, F, E, F, F } }, {
	        // 11
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E, E }, //
	        { F, F, F, F, E, F, E, E, E, E }, //
	        { F, F, F, F, F, E, F, F, E, F }, //
	        { F, F, F, E, F, E, F, E, F, F } }, {
	        // 12
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, E, E, E, E }, //
	        { F, F, F, F, F, F, F, F, E, F }, //
	        { F, F, F, E, F, F, F, E, F, F } }, {
	        // 13
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, F, E, E, E, E, F }, //
	        { F, F, F, F, F, F, F, E, F, F }, //
	        { F, F, E, F, F, F, E, F, F, F } }, {
	        // 14
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 15
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 16
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 17
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 18
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 19
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { F, E, E, E, E, E, F, E, E, E }, //
	        { F, F, F, F, E, E, E, E, F, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 20
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, F, F, F, E, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, E, F, F, F, E, F, F, F, F } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { E, F, F, F, E, F, F, F, F, F } }, {
	        // 22
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 23
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 24
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 25
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 26
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 27
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, F, E }, //
	        { F, F, F, F, F, E, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 28
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, F, E }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 29
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, E, F, F, F, F, F, E, F } }, {
	        // 30
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, E, F, F, F, F, F, E, F } }, {
	        // 31
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { F, F, E, F, F, F, F, F, E, F } }, {
	        // 32 +
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { F, F, E, F, F, F, F, F, E, F }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 33
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { F, F, E, F, F, F, F, F, E, F }, //
	        { F, F, E, F, F, F, F, F, F, F } }, {
	        // 34
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { F, F, E, F, F, F, F, F, E, F }, //
	        { F, F, E, F, F, F, F, F, F, F } }, {
	        // 35
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, F, E, E }, //
	        { F, F, E, F, F, F, F, F, E, F }, //
	        { F, F, E, F, F, F, F, F, F, F } }, {
	        // 36
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, F, E, E, E, E }, //
	        { E, E, F, E, F, F, E, F, E, E }, //
	        { F, F, F, F, F, F, F, F, E, F }, //
	        { F, F, E, F, F, F, F, F, F, F } }, {
	        // 37
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, F, F, E, F, F, E, F, E, E }, //
	        { F, F, F, F, F, F, F, F, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F } }, {
	        // 38
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, E, F, E, E, E }, //
	        { F, F, F, F, F, F, F, E, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 39
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { F, E, F, F, E, F, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 40
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { F, E, F, F, E, F, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F } }, {
	        // 41
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { F, E, F, F, E, F, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F } }, {
	        // 42
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { F, E, F, F, E, F, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F } }, {
	        // 43
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E, E }, //
	        { F, E, F, F, E, F, E, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F } }, {
	        // 44
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, F, E, E, F, F, E, E }, //
	        { F, E, F, F, E, F, F, E, E, F }, //
	        { F, F, F, F, F, F, E, F, F, F } }, {
	        // 45
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, F, E, E, E }, //
	        { F, E, F, F, E, F, F, F, E, F }, //
	        { F, F, F, F, F, F, F, F, F, F } }, {
	        // 46
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, F, E, E, E, E }, //
	        { E, F, F, E, F, F, F, E, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 47
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, F, E, E, E, E, E }, //
	        { F, F, E, F, F, F, E, F, F, E }, //
	        { B, B, B, B, B, B, B, B, B, B } }, };
	
	public TetrisSplashP() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 200;
		return SPLASH_DELAY;
	}
	
}
