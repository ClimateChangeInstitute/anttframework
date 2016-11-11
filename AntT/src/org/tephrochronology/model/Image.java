/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "images")
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "image_id")
	private int imageID;

	@Column(name = "comments")
	private String comments;

	@Column(name = "bytes")
	private byte[] bytes;

	/**
	 * A sample may have many images, and an image may be used by many samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_images", joinColumns = {
			@JoinColumn(name = "image_id") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id") })
	private List<Sample> samplesUsedBy;

	public Image() {
	}

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
