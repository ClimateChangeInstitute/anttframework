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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "images")
@NamedQuery(name = Image.QUERY_IMAGES_BY_ID, query = "SELECT i FROM Image i ORDER BY i.imageID")
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_IMAGES_BY_ID = "org.tephrochronology.model.Image.OrderByID";

	@Id
	@Column(name = "image_id")
	private String imageID;

	@Column(name = "comments")
	private String comments;

	// Marked as XMLTransient on getBytes method
	@Column(name = "bytes")
	private byte[] bytes;

	// Marked as XMLTransient on getThumbBtypes method
	@Column(name = "thumbBytes")
	private byte[] thumbBytes;

	/**
	 * A sample may have many images, and an image may be used by many samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_images", joinColumns = {
			@JoinColumn(name = "image_id") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id") })
	@XmlInverseReference(mappedBy = "images")
	private List<Sample> samplesUsedBy;

	public Image() {
	}

	/**
	 * @param imageID
	 * @param comments
	 * @param bytes
	 * @param thumbBytes
	 * @param samplesUsedBy
	 */
	public Image(String imageID, String comments, byte[] bytes,
			byte[] thumbBytes, List<Sample> samplesUsedBy) {
		super();
		this.imageID = imageID;
		this.comments = comments;
		this.bytes = bytes;
		this.thumbBytes = thumbBytes;
		this.samplesUsedBy = samplesUsedBy;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@XmlTransient
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

	@XmlTransient
	public byte[] getThumbBytes() {
		return thumbBytes;
	}

	public void setThumbBytes(byte[] thumbBytes) {
		this.thumbBytes = thumbBytes;
	}
}
