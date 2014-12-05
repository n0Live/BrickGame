package com.kry.brickgame.UI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

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
	 * Returns the dimension string for using as componentConstraints in
	 * MigLayout
	 * 
	 * @param width
	 *            the specified width
	 * @param height
	 *            the specified height
	 * @return formated dimension string
	 */
	protected static String getDimensionInPercents(int width, int height) {
		float pWidth = getPercent(width, UIConsts.TYPICAL_DEVICE_WIDTH);
		float pHeight = getPercent(height, UIConsts.TYPICAL_DEVICE_HEIGHT);
		
		return String.format(Locale.ENGLISH, "width %f%%!, height %f%%!", pWidth, pHeight);
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
	 * Returns the formated percent string for using in MigLayout
	 * 
	 * @param value
	 *            calculating part value
	 * @param whole
	 *            whole value
	 * @return percent of the {@code value} in the {@code whole}
	 */
	protected static String getFormatedPercent(int value, int whole) {
		return String.format(Locale.ENGLISH, "%f%%", getPercent(value, whole));
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
	 * Returns the insets string for using as layoutConstraints in MigLayout
	 * 
	 * @param top
	 *            the inset from the top
	 * @param left
	 *            the inset from the left
	 * @param bottom
	 *            the inset from the bottom
	 * @param right
	 *            the inset from the right
	 * @return formated insets string
	 */
	protected static String getInsetsInPercents(int top, int left, int bottom, int right) {
		float pTop = getPercent(top, UIConsts.TYPICAL_DEVICE_HEIGHT);
		float pLeft = getPercent(left, UIConsts.TYPICAL_DEVICE_WIDTH);
		float pBottom = getPercent(bottom, UIConsts.TYPICAL_DEVICE_HEIGHT);
		float pRight = getPercent(right, UIConsts.TYPICAL_DEVICE_WIDTH);
		
		return String.format(Locale.ENGLISH, "insets %f%% %f%% %f%% %f%%", pTop, pLeft, pBottom,
				pRight);
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
	 * Calculates what percentage of the {@code whole} is the {@code value}
	 * 
	 * @param value
	 *            calculating part value
	 * @param whole
	 *            whole value
	 * @return percent of the {@code value} in the {@code whole}
	 */
	protected static float getPercent(int value, int whole) {
		return ((float) value * 100 / whole);
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
	
	/**
	 * Open browser on specified URL
	 * 
	 * @param uriStr
	 *            URL
	 */
	protected static void launchBrowser(String uriStr) {
		Desktop desktop;
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				// launch browser
				URI uri;
				try {
					uri = new URI(uriStr);
					desktop.browse(uri);
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
