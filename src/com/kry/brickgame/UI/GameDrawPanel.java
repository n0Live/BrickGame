package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.deviceBgColor;
import static com.kry.brickgame.UI.UIConsts.emptyColor;
import static com.kry.brickgame.UI.UIConsts.fullColor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import com.kry.brickgame.GameListener;
import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSelector;
import com.kry.brickgame.games.GameWithLives;
import com.kry.brickgame.games.TetrisGameI;

public class GameDrawPanel extends JPanel implements GameListener {
	private static final long serialVersionUID = 1007369595836803061L;

	private GameProperties properties;
	private BufferedImage canvas;
	private BufferedImage backgroundImage;
	private Dimension size;

	public GameDrawPanel() {
		properties = new GameProperties();
		canvas = null;
		size = null;

		setBorder(new ResizableBorder());

		addMouseListener(resizeListener);
		addMouseMotionListener(resizeListener);

		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream(
					"/images/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
			backgroundImage = null;
		}

		new Timer("BlinkingSquares", true).schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						blinkingSquares();
					}
				});
			}
		}, 0, 10);

		new Timer("BlinkingPause", true).schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						blinkingPauseIcon();
					}
				});
			}
		}, 0, 500);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		size = getSize();
		// if the main canvas is not created or the size changed
		if ((canvas == null) || (canvas.getWidth() != size.width)
				|| (canvas.getHeight() != size.height)) {
			// create the main canvas
			canvas = new BufferedImage(size.width, size.height,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = (Graphics2D) canvas.getGraphics();
			if (backgroundImage != null) {
				g2d.drawImage(backgroundImage, 0, 0, size.width, size.height,
						deviceBgColor, null);
			} else {
				g2d.setBackground(deviceBgColor);
				g2d.clearRect(0, 0, size.width, size.height);
			}
			g2d.dispose();
		}

		int outBorderSpace = Math.round(size.width / 12);
		int inBorderHorSpace = Math.round(size.width / 24);
		int inBorderVertSpace = Math.round(size.width / 18);

		int gameFieldWidth = size.width - (outBorderSpace + inBorderHorSpace) * 2;
		int gameFieldHeight = Math.round(gameFieldWidth * UIConsts.GAME_FIELD_ASPECT_RATIO);
		if (gameFieldWidth >= 90 && gameFieldHeight >= 120) {
			BufferedImage gameField = Drawer.getDrawnGameField(gameFieldWidth,
					gameFieldHeight, properties);

			Graphics2D g2d = (Graphics2D) canvas.getGraphics();

			g2d.drawImage(gameField, outBorderSpace + inBorderHorSpace, outBorderSpace
					+ inBorderVertSpace, gameField.getWidth(), gameField.getHeight(),
					null);

			g2d.dispose();
		}

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawRenderedImage(canvas, null);
		g2d.dispose();
	}

	/**
	 * Changes the {@code blinkColor} color from {@code fullColor} to
	 * {@code emptyColor} and vice versa
	 */
	public void blinkingSquares() {
		if (Drawer.blinkColor.equals(fullColor))
			Drawer.blinkColor = emptyColor;
		else
			Drawer.blinkColor = fullColor;

		repaint();
	}

	/**
	 * Changes the {@code showPauseIcon} flag from {@code true} to {@code false}
	 * and vice versa, for blinking "Pause" icon
	 */
	public void blinkingPauseIcon() {
		if (properties.status == Status.Paused) {
			Drawer.showPauseIcon = !Drawer.showPauseIcon;
			Drawer.showHiScores = Drawer.showPauseIcon;
		} else
			Drawer.showPauseIcon = false;

		repaint();
	}

	/* Events */
	@Override
	public void boardChanged(GameEvent event) {
		properties.board = event.getBoard();
	}

	@Override
	public void previewChanged(GameEvent event) {
		properties.preview = event.getPreview();
		Drawer.isGameWithLives = (event.getSource() instanceof GameWithLives);
		Drawer.isTetris = (event.getSource() instanceof TetrisGameI);
	}

	@Override
	public void statusChanged(GameEvent event) {
		properties.status = event.getStatus();
		Drawer.showHiScores = (event.getSource() instanceof GameSelector);
	}

	@Override
	public void infoChanged(GameEvent event) {
		if (event.getInfo() != null)
			properties.info = event.getInfo();
		if (event.gethiScores() != null)
			properties.hiScores = event.gethiScores();
	}

	@Override
	public void speedChanged(GameEvent event) {
		properties.speed = event.getSpeed();
	}

	@Override
	public void levelChanged(GameEvent event) {
		properties.level = event.getLevel();
	}

	@Override
	public void rotationChanged(GameEvent event) {
		properties.rotation = event.getRotation();
	}

	@Override
	public void muteChanged(GameEvent event) {
		properties.mute = event.isMute();
	}

	@Override
	public void exit(GameEvent event) {
		System.exit(0);
	}

	MouseInputListener resizeListener = new GameMouseListener();
}
