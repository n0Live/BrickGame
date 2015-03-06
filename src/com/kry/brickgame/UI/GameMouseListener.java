package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.DEVICE_ASPECT_RATIO;
import static com.kry.brickgame.UI.UIConsts.MIN_HEIGHT;
import static com.kry.brickgame.UI.UIConsts.MIN_WIDTH;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * MouseListener for the Game
 * <p>
 * Support resizing and moving the parent frame
 * 
 * @author noLive
 */
public class GameMouseListener extends MouseInputAdapter {
	private int cursor;
	private Point startPos = null;
	private int initWidth = 0;
	private int initHeight = 0;
	private boolean onBorder = false;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (startPos != null) {
			// get parent frame
			Component c = e.getComponent();
			Container frame = SwingUtilities.getAncestorOfClass(JFrame.class, c);
			
			int dx = e.getX() - startPos.x;
			int dy = e.getY() - startPos.y;
			
			if (cursor == Cursor.SE_RESIZE_CURSOR) {
				int width = Math.max(initWidth + dx, MIN_WIDTH);
				int height = Math.max(initHeight + dy, MIN_HEIGHT);
				
				Dimension size = UIUtils.getDimensionWithAspectRatio(new Dimension(width, height),
				        DEVICE_ASPECT_RATIO);
				
				// should remain at least 10 visible pixels on the screen
				if (frame.getY() + size.height > 10 && frame.getX() + size.width > 10) {
					// resizing frame in compliance with DEVICE_ASPECT_RATIO
					frame.setSize(UIUtils.getDimensionWithAspectRatio(new Dimension(width, height),
					        DEVICE_ASPECT_RATIO));
				}
			} else {
				// moving frame
				frame.setLocation(frame.getX() + dx, frame.getY() + dy);
			}
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		JPanel c = (JPanel) e.getComponent();
		ResizableBorder border = (ResizableBorder) c.getBorder();
		// change cursor when mouse moved over ResizableBorder resize triangle
		int cursor = border.getCursor(e);
		c.setCursor(Cursor.getPredefinedCursor(cursor));
		// update the component only if mouse entered or exited from the resize
		// triangle
		if (!onBorder && cursor == Cursor.SE_RESIZE_CURSOR) {
			onBorder = true;
			c.repaint();
		} else if (onBorder && cursor != Cursor.SE_RESIZE_CURSOR) {
			onBorder = false;
			c.repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		startPos = e.getPoint();
		
		JPanel c = (JPanel) e.getComponent();
		ResizableBorder border = (ResizableBorder) c.getBorder();
		cursor = border.getCursor(e);
		
		if (cursor == Cursor.SE_RESIZE_CURSOR) {
			// for resizing frame
			initWidth = c.getWidth();
			initHeight = c.getHeight();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		startPos = null;
	}
}
