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
	 * @return The provided image as an ouput stream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream asOutputStream(BufferedImage bi)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.flush();
		return out;
	}
	
	public static BufferedImage scaleAndCrop(BufferedImage img, final int NEW_SIZE) {

		float w = img.getWidth();
		float h = img.getHeight();

		float rat = w / h;
		
		float newW = NEW_SIZE;
		float newH = NEW_SIZE;
		int x = 0;
		int y = 0;		

		if (rat > 1) { // w > h
			newW = rat * NEW_SIZE;
			// X needs to be translated by the difference
			x = (int)(newW - NEW_SIZE)/2;
		} else { // w <= h
			newH = newW / rat;
			// Y needs to be translated by the difference
			y = (int)(newH - NEW_SIZE)/2;
		}
		
		BufferedImage newImg = scale(img, newW, newH);

		BufferedImage cropped = newImg.getSubimage(x,y,NEW_SIZE,NEW_SIZE);
		
		return cropped;
	}
	

	/**
	 * @param img
	 * @param w
	 * @param h
	 * @return An image scaled to the provided width and height
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
