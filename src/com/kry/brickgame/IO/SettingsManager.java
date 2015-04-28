package com.kry.brickgame.IO;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

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
		defaults.put("rotation", Rotation.CLOCKWISE.toString());
		// random color by default
		defaults.put("color", colorToHexString(new Color(new Random().nextInt(0xFFFFFF))));
		defaults.put("exit_confirmation", Boolean.TRUE.toString());
		defaults.put("show_fps", Boolean.FALSE.toString());
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
	 * Converts a {@code Color} to a {@code HexString}.
	 * 
	 * @param c
	 *            Color
	 * @return HexString
	 */
	private static String colorToHexString(Color c) {
		return String.format("#%06X", 0xFFFFFF & c.getRGB());
	}
	
	/**
	 * Delete a file with saved properties.
	 * 
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public static boolean deleteSettingsFile() {
		return IOUtils.deleteFile(PROPERTIES_FILE);
	}
	
	/**
	 * Get instance of the {@code SettingsManager}
	 * 
	 * @return instance of the {@code SettingsManager}
	 */
	public static synchronized SettingsManager getSettingsManager() {
		if (null == instance) {
			instance = new SettingsManager();
		}
		return instance;
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
	
	private SettingsManager() {
		properties = new Properties();
		loadProperties();
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
			properties.remove("color");
			return hexStringToColor(defaults.getProperty("color"));
		}
	}
	
	/**
	 * Returns the saved "exit_confirmation" property
	 * 
	 * @return {@code boolean} exit confirmation
	 */
	public boolean getExitConfirmation() {
		try {
			return Boolean.valueOf(properties.getProperty("exit_confirmation"));
		} catch (Exception e) {
			properties.remove("exit_confirmation");
			return Boolean.valueOf(defaults.getProperty("exit_confirmation"));
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
			String rotation = properties.getProperty("rotation");
			if (rotation != null) {
				rotation = rotation.toUpperCase(Locale.ENGLISH);
			}
			return Rotation.valueOf(rotation);
		} catch (Exception e) {
			properties.remove("rotation");
			return Rotation.valueOf(defaults.getProperty("rotation"));
		}
	}
	
	/**
	 * Returns the saved "show fps" property
	 * 
	 * @return whether or not to show FPS
	 */
	public boolean getShowFPS() {
		try {
			return Boolean.valueOf(properties.getProperty("show_fps"));
		} catch (Exception e) {
			properties.remove("show_fps");
			return Boolean.valueOf(defaults.getProperty("show_fps"));
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
				int width = Integer.parseInt(sizes[0]);
				int height = Integer.parseInt(sizes[1]);
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
		try (InputStream in = IOUtils.getInputStream(PROPERTIES_FILE)) {
			properties.loadFromXML(in);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
			try (OutputStream os = IOUtils.getOutputStream(PROPERTIES_FILE)) {
				properties.storeToXML(os, "Brick Game Settings");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
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
	 * Sets and saves the "exit_confirmation" property
	 * 
	 * @param needConfirmation
	 *            need to exit confirmation
	 */
	public void setExitConfirmation(boolean needConfirmation) {
		properties.setProperty("exit_confirmation", String.valueOf(needConfirmation));
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
	 * Sets and saves the "muted" property
	 * 
	 * @param muted
	 */
	public void setShowFPS(boolean showFPS) {
		properties.setProperty("show_fps", String.valueOf(showFPS));
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
