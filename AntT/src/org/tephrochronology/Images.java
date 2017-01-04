/**
 * 
 */
package org.tephrochronology;

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

		float extra = rat > 1 ? newW - MIN_SIZE : newH - MIN_SIZE;

		// Width greater than height crop sides
		if (rat > 1) {
			return newImg.getSubimage((int) extra / 2, 0,
					Math.min(Math.round(newW - extra / 2), MIN_SIZE),
					Math.round(newH));
		} else {
			return newImg.getSubimage(0, (int) extra / 2, Math.round(newW),
					Math.min(Math.round(newH - extra / 2), MIN_SIZE));
		}
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
