package com.kry.brickgame.IO;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.kry.brickgame.games.GameConsts.Rotation;

/**
 * @author noLive
 */
public class SettingsManager {
	private static final String PROPERTIES_FILE = "settings.xml";
	
	/**
	 * Default properties
	 */
	private static final Properties defaults;
	static {
		defaults = new Properties();
		defaults.put("muted", Boolean.FALSE.toString());
		defaults.put("rotation", Rotation.Clockwise.toString());
		defaults.put("color", colorToHexString(Color.gray));
	}
	
	/**
	 * Converts a {@code Color} to a {@code HexString}.
	 * 
	 * @param c
	 *            Color
	 * @return HexString
	 */
	private static String colorToHexString(Color c) {
		return String.format("#%06X", (0xFFFFFF & c.getRGB()));
	}
	
	/**
	 * Converts a {@code HexString} to a {@code Color}.
	 * 
	 * @param hexString
	 *            HexString
	 * @return Color
	 */
	private static Color hexStringToColor(String hexString) {
		// skip the '#' character
		return new Color(Integer.parseInt(hexString.substring(1), 16));
	}
	
	/**
	 * Current properties
	 */
	private final Properties properties;
	
	/**
	 * Single instance of the {@code ScoresManager}
	 */
	private static SettingsManager instance;
	
	/**
	 * Delete a file with saved properties.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public static boolean deleteSettingsFile() {
		File propertiesFile = new File(PROPERTIES_FILE);
		if (propertiesFile.exists()) {
			if (propertiesFile.canWrite())
				return propertiesFile.delete();
			else
				return false;
		} else
			return true;
	}
	
	/**
	 * Get instance of the {@code SettingsManager}
	 * 
	 * @return instance of the {@code SettingsManager}
	 */
	public static SettingsManager getSettingsManager() {
		if (null == instance) {
			instance = new SettingsManager();
		}
		return instance;
	}
	
	private SettingsManager() {
		properties = new Properties(defaults);
	}
	
	/**
	 * Returns the saved "color" property
	 * 
	 * @return {@code Color} color
	 */
	public Color getColor() {
		try {
			return hexStringToColor(properties.getProperty("color"));
		} catch (Exception e) {
			e.printStackTrace();
			properties.remove("color");
			return hexStringToColor(defaults.getProperty("color"));
		}
	}
	
	/**
	 * Returns the saved "muted" property
	 * 
	 * @return {@code boolean} muted
	 */
	public boolean getMuted() {
		try {
			return Boolean.valueOf(properties.getProperty("muted"));
		} catch (Exception e) {
			e.printStackTrace();
			properties.remove("muted");
			return Boolean.valueOf(defaults.getProperty("muted"));
		}
	}
	
	/**
	 * Returns the saved "rotation" property
	 * 
	 * @return {@code Rotation} rotation
	 */
	public Rotation getRotation() {
		try {
			return Rotation.valueOf(properties.getProperty("rotation"));
		} catch (Exception e) {
			e.printStackTrace();
			properties.remove("rotation");
			return Rotation.valueOf(defaults.getProperty("rotation"));
		}
	}
	
	/**
	 * Returns the saved "size" property
	 * 
	 * @return {@code Dimension} size
	 */
	public Dimension getSize() {
		String size = properties.getProperty("size");
		if (size != null) {
			try {
				String[] sizes = size.split("x");
				int width = Integer.valueOf(sizes[0]);
				int height = Integer.valueOf(sizes[1]);
				return new Dimension(width, height);
			} catch (Exception e) {
				e.printStackTrace();
				properties.remove("size");
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Load the saved properties from a file.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public boolean loadProperties() {
		File propertiesFile = new File(PROPERTIES_FILE);
		if (propertiesFile.exists()) {
			InputStream in;
			try {
				in = new FileInputStream(propertiesFile);
				properties.loadFromXML(in);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;
	}
	
	/**
	 * Save the properties to a file.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public boolean saveProperties() {
		return saveProperties(false);
	}
	
	/**
	 * Save the properties to a file.
	 * 
	 * @param inExistFileOnly
	 *            if {@code true}, then save in an existing file only
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public boolean saveProperties(boolean inExistFileOnly) {
		File propertiesFile = new File(PROPERTIES_FILE);
		if (!inExistFileOnly || propertiesFile.exists()) {
			OutputStream os;
			try {
				os = new FileOutputStream(propertiesFile);
				properties.storeToXML(os, "Brick Game Settings");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;
	}
	
	/**
	 * Sets and saves the "color" property
	 * 
	 * @param c
	 *            color
	 */
	public void setColor(Color c) {
		properties.setProperty("color", colorToHexString(c));
		saveProperties(true);
	}
	
	/**
	 * Sets and saves the "muted" property
	 * 
	 * @param muted
	 */
	public void setMuted(boolean muted) {
		properties.setProperty("muted", String.valueOf(muted));
		saveProperties(true);
	}
	
	/**
	 * Sets and saves the "rotation" property
	 * 
	 * @param r
	 *            rotation
	 */
	public void setRotation(Rotation r) {
		properties.setProperty("rotation", r.toString());
		saveProperties(true);
	}
	
	/**
	 * Sets and saves the "size" property
	 * 
	 * @param d
	 *            size
	 */
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}
	
	/**
	 * Sets and saves the "size" property
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		properties.setProperty("size", width + "x" + height);
		saveProperties(true);
	}
}