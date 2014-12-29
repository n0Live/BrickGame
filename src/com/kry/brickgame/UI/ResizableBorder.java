package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.lineNormaStroke;
import static com.kry.brickgame.UI.UIConsts.lineOverStroke;
import static com.kry.brickgame.UI.UIConsts.resizerNormalColor;
import static com.kry.brickgame.UI.UIConsts.resizerOverColor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.border.AbstractBorder;

/**
 * Border with resize triangle in lower-left corner
 * 
 * @author noLive
 */
public class ResizableBorder extends AbstractBorder {
	private static final long serialVersionUID = 3299590802539449546L;
	
	private int triangleSize;
	private boolean mouseOver = false;
	
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
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);
		// calculate the resize triangle size
		triangleSize = width / 20;
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setStroke(mouseOver ? lineOverStroke : lineNormaStroke);
		g2d.setPaint(mouseOver ? resizerOverColor : resizerNormalColor);
		
		// drawing three lines
		g2d.drawLine(width - triangleSize, height, width, height - triangleSize);
		g2d.drawLine(width - triangleSize * 2 / 3, height, width, height - triangleSize * 2 / 3);
		g2d.drawLine(width - triangleSize / 3, height, width, height - triangleSize / 3);
		
		g2d.dispose();
	}
	
}
