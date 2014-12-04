package com.kry.brickgame.UI;

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
	protected static final Color deviceBgColor = Color.darkGray;// new Color(60,
	// 60, 60);

	protected static final Color lineNormalColor = UIUtils.getBWInverted(deviceBgColor);
	protected static final Color lineOverColor = UIUtils.getReduced(lineNormalColor);

	protected static final Stroke lineNormaStroke = new BasicStroke(2f);
	protected static final Stroke lineOverStroke = new BasicStroke(3f);
}
