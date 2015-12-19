package com.kry.brickgame.splashes;

/**
 * @author noLive
 */
public class PentixSplashQ extends Splash {
	private static final long serialVersionUID = 534555333873259137L;
	
	private static final Cell[][][] frameTable = new Cell[][][] { {
	        // 0
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, {
	        // 1
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, {
	        // 2
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, E, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, {
	        // 3
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, {
	        // 4
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, F, E, E, E }, //
	        { E, E, E, F, F, F, F, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, {
	        // 5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 6
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 7
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 8
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 9
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 10
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { F, F, E, E, E, E, F, F, E, E }, //
	        { E, E, E, F, F, E, F, F, F, F }, //
	        { E, E, F, F, F, F, F, F, F, F } }, {
	        // 11
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 11.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 12
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 13
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 14
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 15
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 16
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 17
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, F, F, F, F, E, F, F, F, F } }, {
	        // 18
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 19
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 20
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, F, F, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 21
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 22
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 23
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, F, F, F, F, E, E }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 24
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 25
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 26
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 27
	        { E, F, F, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 28
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 29
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, E, E }, //
	        { F, F, F, E, E, E, E, E, E, E }, //
	        { F, E, E, E, E, E, E, E, F, F }, //
	        { E, E, E, E, F, F, F, F, F, F }, //
	        { E, F, F, F, F, F, F, F, F, F } }, {
	        // 30
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 30.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 31
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 32
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, F, E, E, E, E }, //
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 33
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, F, E, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 34
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, E, E, E, E, F, F }, //
	        { F, F, F, E, F, F, F, F, F, F } }, {
	        // 35
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 35.5
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F }, //
	        { B, B, B, B, B, B, B, B, B, B } }, {
	        // 36
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 37
	        { E, E, E, E, F, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, F, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 38
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, F, F, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 39
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, F, F, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 40
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, E, E, E, E, F, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, E, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 41
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, F, F, F }, //
	        { E, E, E, F, F, E, E, F, E, E }, //
	        { E, E, F, F, F, E, E, E, F, F } }, {
	        // 42
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, E, E, E, E, E, E, E }, //
	        { E, E, E, F, F, E, E, F, F, F }, //
	        { E, E, F, F, F, E, E, F, F, F } }, };
	
	public PentixSplashQ() {
		super(frameTable);
	}
	
	@Override
	public int getDelay() {
		final int SPLASH_DELAY = 200;
		return SPLASH_DELAY;
	}
	
}
