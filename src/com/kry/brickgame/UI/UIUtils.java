package com.kry.brickgame.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UIUtils {
	
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
		int calcWidth = (originalAspectRatio >= aspectRatio) ? d.width : (int) Math.ceil(d.height
				/ aspectRatio);
		int calcHeight = (originalAspectRatio <= aspectRatio) ? d.height : (int) Math.ceil(d.width
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
	 * Returns a {@code BufferedImage} as the result of decoding file from a
	 * supplied source string.
	 * 
	 * @param source
	 *            a source (file name) to read from.
	 * @return a {@code BufferedImage} containing the decoded contents of the
	 *         source, or null.
	 */
	protected static BufferedImage getImage(String source) {
		try {
			return ImageIO.read(UIConsts.class.getResourceAsStream(source));
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
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
