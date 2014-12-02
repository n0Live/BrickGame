package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.lineNormaStroke;
import static com.kry.brickgame.UI.UIConsts.lineNormalColor;
import static com.kry.brickgame.UI.UIConsts.lineOverColor;
import static com.kry.brickgame.UI.UIConsts.lineOverStroke;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;

/**
 * Border with resize triangle in lower-left corner
 * 
 * @author noLive
 */
public class ResizableBorder implements Border {
	private final int triangleSize = 15;
	
	private boolean mouseOver = false;
	
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}
	
	/**
	 * Returns resize cursor when mouse move over resize triangle
	 * 
	 * @param e
	 *            MouseEvent
	 * @return {@code Cursor.SE_RESIZE_CURSOR} when mouse move over resize
	 *         triangle, {@code Cursor.DEFAULT_CURSOR} otherwise
	 */
	public int getCursor(MouseEvent e) {
		Component c = e.getComponent();
		Point p = c.getMousePosition();
		
		int cur = Cursor.DEFAULT_CURSOR;
		
		if (p != null && p.x >= c.getWidth() - triangleSize && p.y >= c.getHeight() - triangleSize) {
			cur = Cursor.SE_RESIZE_CURSOR;
			mouseOver = true;
		} else {
			mouseOver = false;
		}
		
		return cur;
	}
	
	@Override
	public boolean isBorderOpaque() {
		return false;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		// don't draw triangle if size of the component less than triangle size
		if (width > triangleSize && height > triangleSize) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();
			
			g2d.setStroke(mouseOver ? lineOverStroke : lineNormaStroke);
			g2d.setPaint(mouseOver ? lineOverColor : lineNormalColor);
			
			// drawing three lines (or two lines and dot?)
			g2d.drawLine(width - triangleSize, height, width, height - triangleSize);
			g2d.drawLine(width - (triangleSize * 2 / 3), height, width, height
					- (triangleSize * 2 / 3));
			g2d.drawLine(width - (triangleSize / 3), height, width, height - (triangleSize / 3));
			
			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
		}
		
	}
	
}
