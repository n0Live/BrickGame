package com.kry.brickgame.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author User
 */
public class IOUtils {

	/**
	 * Delete the specified file.
	 * 
	 * @param fileName
	 *            a filename string
	 * @return {@code true} if success; {@code false} otherwise
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			if (file.canWrite())
				return file.delete();
			else
				return false;
		} else
			return true;
	}

	/**
	 * Creates an InputStream by opening a connection to the specified file.
	 * 
	 * @param fileName
	 *            a filename string
	 * @return FileInputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		return new FileInputStream(file);
	}

	/**
	 * Creates a file output stream to write to the specified file.
	 * 
	 * @param fileName
	 *            a filename string
	 * @return FileOutputStream
	 * @throws FileNotFoundException
	 */
	public static OutputStream getOutputStream(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		return new FileOutputStream(file);
	}

}
