/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

/**
 * @author Mark Royer
 *
 */
public class Image {

	private int imageID;

	private String comments;

	private byte[] bytes;

	/**
	 * A sample may have many images, and an image may be used by many samples.
	 */
	private List<Sample> samplesUsedBy;

	/**
	 * @param imageID
	 * @param comments
	 * @param bytes
	 * @param samplesUsedBy
	 */
	public Image(int imageID, String comments, byte[] bytes,
			List<Sample> samplesUsedBy) {
		super();
		this.imageID = imageID;
		this.comments = comments;
		this.bytes = bytes;
		this.samplesUsedBy = samplesUsedBy;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public List<Sample> getSamplesUsedBy() {
		return samplesUsedBy;
	}

	public void setSamplesUsedBy(List<Sample> samplesUsedBy) {
		this.samplesUsedBy = samplesUsedBy;
	}
}
