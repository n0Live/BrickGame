package com.kry.brickgame.UI;

import static com.kry.brickgame.IO.SettingsManager.getSettingsManager;
import static com.kry.brickgame.UI.Drawer.getDrawer;
import static com.kry.brickgame.UI.UIConsts.deviceBgColor;
import static com.kry.brickgame.UI.UIConsts.emptyColor;
import static com.kry.brickgame.UI.UIConsts.fullColor;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
	
	transient private BufferedImage overlay;
	
	private Dimension size;
	
	transient private final MouseInputListener resizeListener = new GameMouseListener();
	
	public GameDrawPanel() {
		super(null, true);
		
		properties = new GameProperties();
		canvas = null;
		size = null;
		
		setBorder(new ResizableBorder());
		setBackground(deviceBgColor);
		
		addMouseListener(resizeListener);
		addMouseMotionListener(resizeListener);
		
		backgroundImage = UIUtils.getImage("/images/background.png");
		
		// BlinkingSquares and BlinkingPause
		new Timer(100, new ActionListener() {
			static final int tikToBlinkPause = 5;
			int blinkCount = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						blinkingSquares();
						if (blinkCount >= tikToBlinkPause) {
							blinkCount = 0;
							blinkingPauseIcon();
						} else blinkCount++;
					}
				});
			}
		}).start();
	}
	
	/**
	 * Returns the overlay image for drawing it above the gamefield
	 * 
	 * @param width
	 *            width of the overlay
	 * @param height
	 *            height of the overlay
	 * @return the overlay image
	 */
	private BufferedImage getOverlayImage(int width, int height) {
		if (null == backgroundImage) return overlay = null;
		// update overlay only if necessary
		if (null == overlay || overlay.getWidth() != width || overlay.getHeight() != height) {
			overlay = UIUtils.getCompatibleImage(width, height, Transparency.TRANSLUCENT);
			// calculating the overlay position on the backgroundImage
			Rectangle overlayRect = UIUtils.getGameFieldRectangle(backgroundImage.getWidth(),
					backgroundImage.getHeight());
			
			Graphics2D g2d = (Graphics2D) overlay.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			// get overlay from the backgroundImage
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
			g2d.drawImage(backgroundImage.getSubimage(overlayRect.x, overlayRect.y,
					overlayRect.width, overlayRect.height), 0, 0, width, height, null);
			g2d.dispose();
		}
		return overlay;
	}
	
	/**
	 * Changes the {@code showPauseIcon} flag from {@code true} to {@code false}
	 * and vice versa, for blinking "Pause" icon
	 */
	public void blinkingPauseIcon() {
		if (properties.status == Status.Paused) {
			getDrawer().showPauseIcon = !getDrawer().showPauseIcon;
			getDrawer().showHiScores = getDrawer().showPauseIcon;
			repaint(UIUtils.getGameFieldRectangle(getSize()));
		} else getDrawer().showPauseIcon = false;
	}
	
	/**
	 * Changes the {@code blinkColor} color from {@code fullColor} to
	 * {@code emptyColor} and vice versa
	 */
	public void blinkingSquares() {
		if (properties.board != null && properties.board.hasBlinkedCell()) {
			getDrawer().blinkColor = getDrawer().blinkColor.equals(fullColor) ? emptyColor : fullColor;
			repaint(UIUtils.getGameFieldRectangle(getSize()));
		}
	}
	
	/* Events */
	@Override
	public void boardChanged(GameEvent event) {
		if (properties.board == null || !properties.board.equals(event.getBoard())) {
			properties.board = event.getBoard();
			repaint(UIUtils.getGameFieldRectangle(getSize()));
		}
	}
	
	@Override
	public void exit(GameEvent event) {
		System.exit(0);
	}
	
	@Override
	public void infoChanged(GameEvent event) {
		if (event.getInfo() != null) properties.info = event.getInfo();
		if (event.gethiScores() != null) properties.hiScores = event.gethiScores();
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
		boolean needForUpdate = false;
		size = getSize();
		
		// if the main canvas is not created or the size changed
		if (null == canvas || canvas.getWidth() != size.width || canvas.getHeight() != size.height) {
			// create the new main canvas
			canvas = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
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
		
		// prepare the gamefield
		Rectangle gameFieldRect = UIUtils.getGameFieldRectangle(size);
		BufferedImage gameField = getDrawer().getDrawnGameField(gameFieldRect.width,
				gameFieldRect.height, properties);
		
		// draw the gamefield
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		g2d.drawImage(gameField, gameFieldRect.x, gameFieldRect.y, null);
		
		// draw the device background
		if (backgroundImage != null) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			if (needForUpdate) {
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g2d.drawImage(backgroundImage, 0, 0, size.width, size.height, null);
			} else g2d.drawImage(getOverlayImage(gameField.getWidth(), gameField.getHeight()),
					gameFieldRect.x, gameFieldRect.y, null);
		}
		
		g2d.dispose();
		
		/* Component graphics */
		g2d = (Graphics2D) g.create();
		g2d.drawRenderedImage(canvas, null);
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
