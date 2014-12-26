package com.kry.brickgame.UI;

import static com.kry.brickgame.IO.SettingsManager.getSettingsManager;
import static com.kry.brickgame.UI.Drawer.getDrawer;
import static com.kry.brickgame.UI.UIConsts.TYPICAL_DEVICE_WIDTH;
import static com.kry.brickgame.UI.UIConsts.deviceBgColor;
import static com.kry.brickgame.UI.UIConsts.emptyColor;
import static com.kry.brickgame.UI.UIConsts.fullColor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import com.kry.brickgame.games.GameConsts.Status;
import com.kry.brickgame.games.GameSelector;
import com.kry.brickgame.games.GameWithLives;
import com.kry.brickgame.games.TetrisGameI;

public class GameDrawPanel extends JPanel implements GameListener {
	private static final long serialVersionUID = 1007369595836803061L;
	
	private final GameProperties properties;
	
	transient private BufferedImage canvas;
	transient private final BufferedImage backgroundImage;
	
	private Dimension size;
	
	transient MouseInputListener resizeListener = new GameMouseListener();
	
	public GameDrawPanel() {
		properties = new GameProperties();
		canvas = null;
		size = null;
		
		setBorder(new ResizableBorder());
		
		addMouseListener(resizeListener);
		addMouseMotionListener(resizeListener);
		
		backgroundImage = UIUtils.getImage("/images/background.png");
		
		new Timer("BlinkingSquares", true).schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
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
					@Override
					public void run() {
						blinkingPauseIcon();
					}
				});
			}
		}, 0, 500);
		
	}
	
	/**
	 * Changes the {@code showPauseIcon} flag from {@code true} to {@code false}
	 * and vice versa, for blinking "Pause" icon
	 */
	public void blinkingPauseIcon() {
		if (properties.status == Status.Paused) {
			getDrawer().showPauseIcon = !getDrawer().showPauseIcon;
			getDrawer().showHiScores = getDrawer().showPauseIcon;
		} else {
			getDrawer().showPauseIcon = false;
		}
		repaint();
	}
	
	/**
	 * Changes the {@code blinkColor} color from {@code fullColor} to
	 * {@code emptyColor} and vice versa
	 */
	public void blinkingSquares() {
		if (getDrawer().blinkColor.equals(fullColor)) {
			getDrawer().blinkColor = emptyColor;
		} else {
			getDrawer().blinkColor = fullColor;
		}
		repaint();
	}
	
	/* Events */
	@Override
	public void boardChanged(GameEvent event) {
		properties.board = event.getBoard();
	}
	
	@Override
	public void exit(GameEvent event) {
		System.exit(0);
	}
	
	@Override
	public void infoChanged(GameEvent event) {
		if (event.getInfo() != null) {
			properties.info = event.getInfo();
		}
		if (event.gethiScores() != null) {
			properties.hiScores = event.gethiScores();
		}
	}
	
	@Override
	public void levelChanged(GameEvent event) {
		properties.level = event.getLevel();
	}
	
	@Override
	public void muteChanged(GameEvent event) {
		properties.mute = event.isMute();
		// save muted state
		getSettingsManager().setMuted(properties.mute);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		final float outBorderSpaceFactor = (float) 1 / 12;
		final float inBorderHorSpaceFactor = (float) 1 / 18;
		final float inBorderVertSpaceFactor = (float) 1 / 18;
		boolean needForUpdate = false;
		size = getSize();
		
		// prepare the gamefield
		int outBorderSpace = Math.round(size.width * outBorderSpaceFactor);
		int inBorderHorSpace = Math.round(size.width * inBorderHorSpaceFactor);
		int inBorderVertSpace = Math.round(size.width * inBorderVertSpaceFactor);
		int gameFieldWidth = size.width - (outBorderSpace + inBorderHorSpace) * 2;
		int gameFieldHeight = Math.round(gameFieldWidth * UIConsts.GAME_FIELD_ASPECT_RATIO);
		int gameFieldX = outBorderSpace + inBorderHorSpace;
		int gameFieldY = outBorderSpace + inBorderVertSpace;
		BufferedImage gameField = getDrawer().getDrawnGameField(gameFieldWidth, gameFieldHeight,
		        properties);
		
		// if the main canvas is not created or the size changed
		if (null == canvas || canvas.getWidth() != size.width || canvas.getHeight() != size.height) {
			// create the new main canvas
			canvas = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
			needForUpdate = true;
		}
		
		/* Canvas graphics */
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (needForUpdate) {
			// fill the background
			g2d.setBackground(deviceBgColor);
			g2d.clearRect(0, 0, size.width, size.height);
		}
		
		// draw gamefield
		g2d.drawImage(gameField, gameFieldX, gameFieldY, gameField.getWidth(),
		        gameField.getHeight(), this);
		
		// draw device
		if (backgroundImage != null) {
			if (needForUpdate) {
				g2d.drawImage(backgroundImage, 0, 0, size.width, size.height, this);
			} else {
				int overlayOutBorderSpace = Math.round(TYPICAL_DEVICE_WIDTH * outBorderSpaceFactor);
				int overlayInBorderHorSpace = Math.round(TYPICAL_DEVICE_WIDTH
				        * inBorderHorSpaceFactor);
				int overlayInBorderVertSpace = Math.round(TYPICAL_DEVICE_WIDTH
				        * inBorderVertSpaceFactor);
				int overlayX = overlayOutBorderSpace + overlayInBorderHorSpace;
				int overlayY = overlayOutBorderSpace + overlayInBorderVertSpace;
				int overlayWidth = TYPICAL_DEVICE_WIDTH
				        - (overlayOutBorderSpace + overlayInBorderHorSpace) * 2;
				int overlayHeight = Math.round(overlayWidth * UIConsts.GAME_FIELD_ASPECT_RATIO);
				
				BufferedImage overlay = backgroundImage.getSubimage(overlayX, overlayY,
				        overlayWidth, overlayHeight);
				g2d.drawImage(overlay, gameFieldX, gameFieldY, gameField.getWidth(),
				        gameField.getHeight(), this);
			}
		}
		
		g2d.dispose();
		
		/* Component graphics */
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).drawRenderedImage(canvas, null);
	}
	
	@Override
	public void previewChanged(GameEvent event) {
		properties.preview = event.getPreview();
		getDrawer().showLives = event.getSource() instanceof GameWithLives;
		getDrawer().showNext = event.getSource() instanceof TetrisGameI;
	}
	
	@Override
	public void rotationChanged(GameEvent event) {
		properties.rotation = event.getRotation();
	}
	
	@Override
	public void speedChanged(GameEvent event) {
		properties.speed = event.getSpeed();
	}
	
	@Override
	public void statusChanged(GameEvent event) {
		properties.status = event.getStatus();
		getDrawer().showHiScores = event.getSource() instanceof GameSelector;
	}
}
