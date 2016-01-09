package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class PentixSplashT extends Splash {
	private static final long serialVersionUID = 534555333873259137L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, {
	        // 1
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, {
	        // 2
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, {
	        // 3
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, E, E, F, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, {
	        // 4
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, E, E, F, E, F, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, {
	        // 5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E } }, {
	        // 6
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E } }, {
	        // 7
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, F, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E } }, {
	        // 8
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E } }, {
	        // 9
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E } }, {
	        // 10 +
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { F, E, E, E, F, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 11
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, F, F, F, F, E }, //
	        { F, E, E, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 12
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, F, F, F, F, E }, //
	        { F, E, E, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 13
	        { E, E, F, E, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, F, F, F, F, E }, //
	        { F, E, E, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 14
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, F, F, F, F, E }, //
	        { F, E, E, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 15
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, F, F, E }, //
	        { E, E, E, E, E, F, F, F, F, E }, //
	        { F, E, E, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 16
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, F, F, E }, //
	        { E, F, F, F, E, F, F, F, F, E }, //
	        { F, E, F, E, F, F, F, F, F, F }, //
	        { F, E, E, F, E, F, F, F, F, E } }, {
	        // 17
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 17.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 18
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 19
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 20
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, F, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, F, F, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 22
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 23
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E }, //
	        { E, F, E, E, E, E, E, F, F, E }, //
	        { E, E, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 24
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, F, F, E }, //
	        { E, F, F, E, E, F, F, F, F, E }, //
	        { F, E, F, F, E, F, F, F, F, E } }, {
	        // 25
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, E, E, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, E } }, {
	        // 26
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, E, E, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, E } }, {
	        // 27
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, E, E, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, E } }, {
	        // 28
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { F, E, E, E, E, F, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, E, E, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, E } }, {
	        // 29
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, F, F, E, E, F, F, E }, //
	        { F, F, F, E, F, F, F, F, F, E }, //
	        { F, F, F, F, E, F, F, F, F, E } }, {
	        // 30
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E } }, {
	        // 31
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E } }, {
	        // 32
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E } }, {
	        // 33
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E } }, {
	        // 34
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, F, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E } }, {
	        // 35 +
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { E, E, E, E, E, E, E, E, E, F }, //
	        { F, E, E, E, E, E, E, E, E, F }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { F, F, F, F, F, F, F, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E } }, {
	        // 36
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, F }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, E } }, {
	        // 37
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, F, F, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 37.1
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 37.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 38
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 39
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 40
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 41
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 42
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, F, E, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 43
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, E, E, E }, //
	        { F, E, E, F, E, E, E, F, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 44
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 44.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 45
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F } }, {
	        // 46
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F } }, {
	        // 47
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F } }, {
	        // 48
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, F, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { F, F, F, F, E, E, E, F, F, F } }, {
	        // 49
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 49.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 42
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { F, E, E, E, F, E, F, E, E, E } }, };
	
	public PentixSplashT() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 200;
		return SPLASH_DELAY;
	}
	
}
