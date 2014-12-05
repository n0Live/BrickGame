package com.kry.brickgame.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.kry.brickgame.Main;

/**
 * @author noLive
 */
public class CloseOptionPane {
	
	private static MouseListener clickMouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			UIUtils.launchBrowser("https://play.google.com/store/apps/details?id="
					+ Main.class.getPackage().getName());
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	};
	
	private static JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 5));
		
		// text label
		JLabel exitLabel = new JLabel("Are you sure you want to exit?");
		
		ImageIcon image;
		try {
			image = new ImageIcon(UIUtils.getImage("/images/app.png"));
		} catch (NullPointerException e) {
			e.printStackTrace();
			image = null;
		}
		// available in Google Play label
		JLabel googlePlayLabel = new JLabel(image);
		googlePlayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		googlePlayLabel.addMouseListener(clickMouseListener);
		
		panel.add(exitLabel, BorderLayout.NORTH);
		panel.add(googlePlayLabel);
		
		return panel;
	}
	
	public static int show(Component parentComponent) {
		return JOptionPane.showConfirmDialog(parentComponent, getPanel(), "Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
}