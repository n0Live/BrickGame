package com.kry.brickgame.UI;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.listener.XInputDeviceListener;
import com.kry.brickgame.Main;
import com.kry.brickgame.games.Game;

public class XInputListener implements XInputDeviceListener {
	private final Robot robot;

	public XInputListener() {
		robot = getNewRobot();
	}

	private final static Map<XInputButton, Integer> keycodeMap;
	static {
		keycodeMap = new LinkedHashMap<>();
		keycodeMap.put(XInputButton.DPAD_LEFT, KeyEvent.VK_LEFT);
		keycodeMap.put(XInputButton.DPAD_RIGHT, KeyEvent.VK_RIGHT);
		keycodeMap.put(XInputButton.DPAD_DOWN, KeyEvent.VK_DOWN);
		keycodeMap.put(XInputButton.DPAD_UP, KeyEvent.VK_UP);
		keycodeMap.put(XInputButton.START, KeyEvent.VK_ENTER);
		keycodeMap.put(XInputButton.BACK, KeyEvent.VK_ESCAPE);
		keycodeMap.put(XInputButton.A, KeyEvent.VK_SPACE);
		keycodeMap.put(XInputButton.B, KeyEvent.VK_BACK_SPACE);
		keycodeMap.put(XInputButton.X, KeyEvent.VK_SPACE);
		keycodeMap.put(XInputButton.Y, KeyEvent.VK_UP);
		keycodeMap.put(XInputButton.RIGHT_SHOULDER, KeyEvent.VK_M);
		keycodeMap.put(XInputButton.LEFT_SHOULDER, KeyEvent.VK_M);
	}

	/**
	 * Returns a new Robot object
	 */
	private static Robot getNewRobot() {
		try {
			return new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void connected() {
		// set game paused
		Game game = Main.getGame();
		if (game != null) game.pause();
	}

	@Override
	public void disconnected() {
		// set game paused
		Game game = Main.getGame();
		if (game != null) game.pause();
	}

	@Override
	public void buttonChanged(XInputButton button, boolean pressed) {
		if (pressed) keyPressed(button);
		else keyReleased(button);
	}

	/**
	 * Called when a XInputButton has been pressed.
	 * 
	 * @param button
	 *            the button
	 */
	public void keyPressed(XInputButton button) {
		if (robot != null && keycodeMap.containsKey(button)) {
			robot.keyPress(keycodeMap.get(button));
		}
	}

	/**
	 * Called when a XInputButton has been released.
	 * 
	 * @param button
	 *            the button
	 */
	public void keyReleased(XInputButton button) {
		if (robot != null && keycodeMap.containsKey(button)) {
			robot.keyRelease(keycodeMap.get(button));
		}
	}

}
