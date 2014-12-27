package com.kry.brickgame.UI;

import static com.kry.brickgame.IO.SettingsManager.getSettingsManager;
import static com.kry.brickgame.UI.UIUtils.getBWInverted;
import static com.kry.brickgame.UI.UIUtils.getEnhanced;
import static com.kry.brickgame.UI.UIUtils.getReduced;
import static com.kry.brickgame.UI.UIUtils.isDarkColor;

import java.awt.BasicStroke;
import java.awt.Color;
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
	protected static final Color deviceBgColor = getSettingsManager().getColor();
	
	protected static final Color lineNormalColor = getBWInverted(deviceBgColor);
	protected static final Color lineOverColor = getReduced(lineNormalColor);
	
	// white if dark device color or same as device color otherwise
	protected static final Color resizerNormalColor = isDarkColor(deviceBgColor) ? Color.white
	        : deviceBgColor;
	protected static final Color resizerOverColor = isDarkColor(deviceBgColor) ? getReduced(resizerNormalColor)
	        : getEnhanced(resizerNormalColor);
	
	protected static final Stroke lineNormaStroke = new BasicStroke(2f);
	protected static final Stroke lineOverStroke = new BasicStroke(3f);
	
	protected static final int TYPICAL_DEVICE_WIDTH = 1080;
	protected static final int TYPICAL_DEVICE_HEIGHT = (int) (TYPICAL_DEVICE_WIDTH * DEVICE_ASPECT_RATIO);
	
	protected static final int MIN_WIDTH = 187;
	protected static final int MIN_HEIGHT = (int) (MIN_WIDTH * DEVICE_ASPECT_RATIO);
	// Insets
	static final int INSET_TOP = 5;
	static final int INSET_LEFT = 45;
	static final int INSET_BOTTOM = 100;
	static final int INSET_RIGHT = 45;
	// Buttons size
	/**
	 * Window buttons (minimize, close) size
	 */
	static final int WINDOW_BTN_SIZE = 45;
	/**
	 * Control buttons size
	 */
	static final int CONTROL_BTN_SIZE = 125;
	/**
	 * Rotate button size
	 */
	static final int ROTATE_BTN_SIZE = 225;
	/**
	 * Menu buttons (Shutdown, Reset, Mute) size
	 */
	static final int MENU_BTN_SIZE = 85;
	/**
	 * Start button size
	 */
	static final int START_BTN_SIZE = 120;
	/**
	 * The ratio of the width of the outer device's frame to the whole width of
	 * the device
	 */
	static final float outBorderSpaceRatio = (float) 1 / 12;
	/**
	 * The ratio of the width of the side inner (gamefield) device's frame to
	 * the whole width of the device
	 */
	static final float inBorderHorSpaceRatio = (float) 1 / 18;
	/**
	 * The ratio of the width of the top inner (gamefield) device's frame to the
	 * whole width of the device
	 */
	static final float inBorderVertSpaceRatio = (float) 1 / 18;
}
