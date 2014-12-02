package com.kry.brickgame.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;

/**
 * @author noLive
 */
public class UIConsts {
	/* LABELS */
	protected static final String HI = "HI";
	protected static final String SCORE = "SCORE";
	protected static final String NEXT = "NEXT";
	protected static final String LIVES = "LIVES";
	protected static final String SPEED = "SPEED";
	protected static final String LEVEL = "LEVEL";
	protected static final String ROTATE = "ROTATE";
	protected static final String PAUSE = "PAUSE";
	protected static final String GAME_OVER = "GAME OVER";
	protected static final String COMING = "COMING";
	protected static final String SOON = "SOON";
	
	protected static final String SCORE_SUBSTRATE = "18888";
	protected static final String NUMBER_SUBSTRATE = "18";
	
	/* ICONS */
	protected static final String ICON_MUSIC = "\ue602";
	protected static final String ICON_ROTATE_RIGHT = "\ue600";
	protected static final String ICON_ROTATE_LEFT = "\ue601";
	protected static final String ICON_PAUSE = "\ue603";
	
	/**
	 * Aspect ratio of the game field (4/3)
	 */
	protected static final float GAME_FIELD_ASPECT_RATIO = (float) 4 / 3;
	/**
	 * Aspect ratio of the game device (16/9)
	 */
	protected static final float DEVICE_ASPECT_RATIO = (float) 16 / 9;
	
	/**
	 * Color of inactive elements
	 */
	protected static final Color emptyColor = new Color(60, 60, 60, 20);
	/**
	 * Color of active elements
	 */
	protected static final Color fullColor = new Color(40, 40, 40, 255);
	/**
	 * Game field background color
	 */
	protected static final Color bgColor = new Color(136, 153, 107);
	/**
	 * Device background color
	 */
	protected static final Color deviceBgColor = Color.darkGray;// new Color(60,
																// 60, 60);
	
	protected static final Color lineNormalColor = getBWInverted(deviceBgColor);
	protected static final Color lineOverColor = getReduced(lineNormalColor);
	
	protected static final Stroke lineNormaStroke = new BasicStroke(2f);
	protected static final Stroke lineOverStroke = new BasicStroke(3f);
	
	/**
	 * Returns the black or white color is inverted to a given.
	 * 
	 * @param c
	 *            original color
	 * @return inverted black&white color
	 */
	protected static Color getBWInverted(Color c) {
		return (isDarkColor(c) ? Color.white : Color.black);
	}
	
	/**
	 * Calculate new dimension (width and height) depending on specified
	 * original dimension and aspect ratio.
	 * 
	 * @param d
	 *            original dimension
	 * @param aspectRatio
	 *            aspect ratio
	 * @return dimension with target aspect ratio
	 */
	protected static Dimension getDimensionWithAspectRatio(Dimension d, float aspectRatio) {
		float originalAspectRatio = (float) d.height / d.width;
		
		// rounding to the nearest highest integer
		int calcWidth = (int) Math.ceil((originalAspectRatio >= aspectRatio) ? d.width : d.height
				/ aspectRatio);
		int calcHeight = (int) Math.ceil((originalAspectRatio <= aspectRatio) ? d.height : d.width
				* aspectRatio);
		
		return new Dimension(calcWidth, calcHeight);
	}
	
	/**
	 * Enhances color: bright colors become brighter, dark - darker.
	 * 
	 * @param c
	 *            original color
	 * @return enhanced color
	 */
	protected static Color getEnhanced(Color c) {
		return (isDarkColor(c)) ? c.darker() : c.brighter();
	}
	
	/**
	 * Returns the color is inverted to a given.
	 * 
	 * @param c
	 *            original color
	 * @return inverted color
	 */
	protected static Color getInverted(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int alpha = c.getAlpha();
		
		return new Color(Math.abs(255 - r), Math.abs(255 - g), Math.abs(255 - b), alpha);
	}
	
	/**
	 * Reduces color: bright colors become darker, dark - brighter.
	 * 
	 * @param c
	 *            original color
	 * @return reduced color
	 */
	protected static Color getReduced(Color c) {
		return (isDarkColor(c)) ? c.brighter() : c.darker();
	}
	
	/**
	 * Determines if the specified color is dark
	 * 
	 * @param c
	 *            specified color
	 * @return {@code true} if that color is dark; {@code false} otherwise
	 */
	protected static boolean isDarkColor(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		// if brightness of all color components less then half of max
		// brightness
		return (r + g + b) < (255 * 3) / 2;
	}
}
