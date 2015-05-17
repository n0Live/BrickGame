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
	static final String HI = "HI";
	static final String SCORE = "SCORE";
	static final String NEXT = "NEXT";
	static final String LIVES = "LIVES";
	static final String SPEED = "SPEED";
	static final String LEVEL = "LEVEL";
	static final String ROTATE = "ROTATE";
	static final String PAUSE = "PAUSE";
	static final String GAME_OVER = "GAME OVER";
	static final String COMING = "COMING";
	static final String SOON = "SOON";
	
	static final String SCORE_SUBSTRATE = "18888";
	static final String NUMBER_SUBSTRATE = "18";
	
	/* ICONS */
	static final String ICON_MUSIC = "\ue602";
	static final String ICON_ROTATE_RIGHT = "\ue600";
	static final String ICON_ROTATE_LEFT = "\ue601";
	static final String ICON_PAUSE = "\ue603";
	
	static final String KEY_PREFFIX = "Key";
	
	/**
	 * Aspect ratio of the game field (4/3)
	 */
	static final float GAME_FIELD_ASPECT_RATIO = (float) 4 / 3;
	/**
	 * Aspect ratio of the game device (16/9)
	 */
	static final float DEVICE_ASPECT_RATIO = (float) 16 / 9;
	
	/**
	 * Color of inactive elements
	 */
	static final Color emptyColor = new Color(60, 60, 60, 20);
	/**
	 * Color of active elements
	 */
	static final Color fullColor = new Color(40, 40, 40, 255);
	/**
	 * Game field background color
	 */
	static final Color bgColor = new Color(136, 153, 107);
	/**
	 * Device background color
	 */
	static final Color deviceBgColor = getSettingsManager().getColor();
	
	static final Color lineNormalColor = getBWInverted(deviceBgColor);
	static final Color lineOverColor = getReduced(lineNormalColor);
	
	// white if dark device color or same as device color otherwise
	static final Color resizerNormalColor = isDarkColor(deviceBgColor) ? Color.white
	        : deviceBgColor;
	static final Color resizerOverColor = isDarkColor(deviceBgColor) ? getReduced(resizerNormalColor)
	        : getEnhanced(resizerNormalColor);
	
	static final Stroke lineNormaStroke = new BasicStroke(2f);
	static final Stroke lineOverStroke = new BasicStroke(3f);
	
	static final int TYPICAL_DEVICE_WIDTH = 1080;
	static final int TYPICAL_DEVICE_HEIGHT = (int) Math.ceil(TYPICAL_DEVICE_WIDTH
	        * DEVICE_ASPECT_RATIO);
	
	static final int MIN_WIDTH = 190;
	static final int MIN_HEIGHT = (int) Math.ceil(MIN_WIDTH * DEVICE_ASPECT_RATIO);
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
	/**
	 * Whether or not to show FPS for debug
	 */
	static final boolean SHOW_FPS = getSettingsManager().getShowFPS();
}
