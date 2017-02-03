/**
 * 
 */
package org.tephrochronology;

import static java.lang.Math.min;
import static java.lang.Math.round;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Mark Royer
 *
 */
public class Images {

	/**
	 * Default thumbnail size for the images
	 */
	public final static int DEFAULT_THUMB_SIZE = 75;

	/**
	 * @param bi
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream asOutputStream(BufferedImage bi)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.flush();
		return out;
	}
	
	public static BufferedImage scaleAndCrop(BufferedImage img, int newSize) {

		float w = img.getWidth();
		float h = img.getHeight();

		float rat = w / h;

		int MIN_SIZE = 75;

		float newW = rat > 1 ? rat * MIN_SIZE : MIN_SIZE;
		float newH = rat <= 1 ? rat * MIN_SIZE : MIN_SIZE;

		BufferedImage newImg = scale(img, newW, newH);

		float extra = rat > 1 ? newW - MIN_SIZE :  MIN_SIZE - newH;

		int x = 0;
		int y = 0;
		int finalW = 0;
		int finalH = 0;
		
		// Width greater than height crop sides
		if (rat > 1) {
			x = (int) extra / 2;
			y = 0;
			finalW = min(round(newW - extra / 2), MIN_SIZE);
			finalH = min(round(newH), MIN_SIZE);
		} else {
			x = 0;
			y = (int) extra / 2;
			finalW = min(round(newW), MIN_SIZE);
			finalH = min(round(newH - extra / 2), MIN_SIZE);
		}
		
		return newImg.getSubimage(x,y,finalW,finalH);

	}
	

	/**
	 * @param img
	 * @param w
	 * @param h
	 * @return
	 */
	public static BufferedImage scale(BufferedImage img, float w, float h) {
		BufferedImage result = new BufferedImage(Math.round(w), Math.round(h),
				img.getType());
		Graphics2D g = result.createGraphics();
		AffineTransform at = AffineTransform
				.getScaleInstance(w / img.getWidth(), h / img.getHeight());
		g.drawRenderedImage(img, at);
		return result;
	}
	
}
