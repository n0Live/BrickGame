package com.kry.brickgame.UI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
		return isDarkColor(c) ? Color.white : Color.black;
	}
	
	/**
	 * Returns a {@code BufferedImage} that supports the specified transparency
	 * and has a data layout and color model compatible with default
	 * {@code GraphicsConfiguration}.
	 * 
	 * @param width
	 *            the width of the returned BufferedImage
	 * @param height
	 *            the height of the returned BufferedImage
	 * @param transparency
	 *            the specified transparency mode.<br>
	 *            {@code transparency = 0} for the completely opaque image.
	 * @return a <code>BufferedImage</code> whose data layout and color model is
	 *         compatible with this <code>GraphicsConfiguration</code> and also
	 *         supports the specified transparency.
	 * @throws IllegalArgumentException
	 *             if the transparency is not a valid value
	 * @see Transparency#OPAQUE
	 * @see Transparency#BITMASK
	 * @see Transparency#TRANSLUCENT
	 */
	protected static BufferedImage getCompatibleImage(int width, int height, int transparency) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();
		BufferedImage result;
		if (transparency != 0) {
			result = config.createCompatibleImage(width, height, transparency);
		} else {
			result = config.createCompatibleImage(width, height);
		}
		return result;
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
		int calcWidth = originalAspectRatio >= aspectRatio ? d.width : (int) Math.ceil(d.height
		        / aspectRatio);
		int calcHeight = originalAspectRatio <= aspectRatio ? d.height : (int) Math.ceil(d.width
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
		return isDarkColor(c) ? c.darker() : c.brighter();
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
	 * Returns a rectangle indicating the position of the gamefield on the main
	 * canvas
	 * 
	 * @param canvasSize
	 *            size of the main canvas
	 * @return a rectangle indicating the position of the gamefield
	 */
	protected static Rectangle getGameFieldRectangle(Dimension canvasSize) {
		int outBorderSpace = Math.round(canvasSize.width * UIConsts.outBorderSpaceRatio);
		int inBorderHorSpace = Math.round(canvasSize.width * UIConsts.inBorderHorSpaceRatio);
		int inBorderVertSpace = Math.round(canvasSize.width * UIConsts.inBorderVertSpaceRatio);
		
		int x = outBorderSpace + inBorderHorSpace;
		int y = outBorderSpace + inBorderVertSpace;
		int width = canvasSize.width - (outBorderSpace + inBorderHorSpace) * 2;
		int height = Math.round(width * UIConsts.GAME_FIELD_ASPECT_RATIO);
		
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Returns a rectangle indicating the position of the gamefield on the main
	 * canvas
	 * 
	 * @param width
	 *            width of the main canvas
	 * @param height
	 *            height of the main canvas
	 * @return a rectangle indicating the position of the gamefield
	 */
	protected static Rectangle getGameFieldRectangle(int width, int height) {
		return getGameFieldRectangle(new Dimension(width, height));
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
		try (InputStream inputStream = UIUtils.class.getResourceAsStream(source)) {
			return ImageIO.read(inputStream);
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
		return (float) value * 100 / whole;
	}
	
	/**
	 * Reduces color: bright colors become darker, dark - brighter.
	 * 
	 * @param c
	 *            original color
	 * @return reduced color
	 */
	protected static Color getReduced(Color c) {
		return isDarkColor(c) ? c.brighter() : c.darker();
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
		return r + g + b < 255 * 3 / 2;
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
