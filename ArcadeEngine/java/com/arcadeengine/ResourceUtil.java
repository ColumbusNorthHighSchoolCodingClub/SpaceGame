package com.arcadeengine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class provides utility methods for loading resources
 * 
 * @author D.Baker
 * @author Byron Zaharako
 */
public abstract class ResourceUtil {

	/**
	 * Get a BufferedImage from an image within the classpath
	 * 
	 * @param packageName
	 * @param fileName
	 * @return
	 */
	public static BufferedImage loadInternalImage(String packageName, String fileName) {
		try {
			String pathName = packageName.replaceAll("\\.", "/") + "/" + fileName;
			URL path = ClassLoader.getSystemClassLoader().getResource(pathName);
			return ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
			return getNullImage();
		}
	}

	/**
	 * Get the null image to show something went wrong when loading
	 */
	private static BufferedImage getNullImage() {
		return loadInternalImage("com.arcadeengine.res", "null.png");
	}

	/**
	 * Gets an Image from outside the classpath
	 */
	public static BufferedImage loadExternalImage(String folder, String fileName) {
		return loadExternalImage(folder + "/" + fileName);
	}

	public static BufferedImage loadExternalImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return getNullImage();
		}
	}

	/**
	 * Safely attempts to get the requested sound file.
	 * 
	 * @param filename
	 *            Name of the file.
	 * @param ob
	 *            Class Being Used in, Example : Player.class
	 * @return The desired sound file.
	 */
	public static AudioInputStream loadClip(String packageName, String fileName) {
		try {
			String pathName = packageName.replaceAll("\\.", "/") + "/" + fileName;
			URL path = ClassLoader.getSystemClassLoader().getResource(pathName);
			return AudioSystem.getAudioInputStream(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
