package com.kry.brickgame.UI;

import java.awt.BasicStroke;
import java.awt.Color;
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
	private final Color out = Color.GRAY; // new Color(162, 162, 162);
	private final Color over = Color.LIGHT_GRAY; // new Color(192, 192, 192);
	private final Stroke outStroke = new BasicStroke(2f);
	private final Stroke overStroke = new BasicStroke(3f);
	private final int triangleSize = 20;

	private boolean mouseOver = false;

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {

		// don't draw triangle if size of the component less than triangle size
		if (width > triangleSize && height > triangleSize) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();

			g2d.setStroke(mouseOver ? overStroke : outStroke);
			g2d.setPaint(mouseOver ? over : out);

			// drawing three lines (or two lines and dot?)
			g2d.drawLine(width - triangleSize - 1, height, width, height
					- triangleSize - 1);
			g2d.drawLine(width - triangleSize / 2 - 1, height, width, height
					- triangleSize / 2 - 1);
			g2d.drawLine(width - 3, height, width, height - 3);

			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
		}

	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
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

		if (p != null && p.x >= c.getWidth() - triangleSize
				&& p.y >= c.getHeight() - triangleSize) {
			cur = Cursor.SE_RESIZE_CURSOR;
			mouseOver = true;
		} else {
			mouseOver = false;
		}

		return cur;
	}

}
