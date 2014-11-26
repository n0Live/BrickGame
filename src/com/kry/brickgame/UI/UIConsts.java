package com.kry.brickgame.UI;

import java.awt.Color;
import java.awt.Dimension;

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
	 * Background color
	 */
	protected static final Color bgColor = new Color(136, 153, 107);

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
	protected static Dimension getDimensionWithAspectRatio(Dimension d,
			float aspectRatio) {
		float originalAspectRatio = (float) d.height / d.width;

		int calcWidth = (int) ((originalAspectRatio >= aspectRatio) ? d.width
				: d.height / aspectRatio);
		int calcHeight = (int) ((originalAspectRatio <= aspectRatio) ? d.height
				: d.width * aspectRatio);

		return new Dimension(calcWidth, calcHeight);
	}

}
