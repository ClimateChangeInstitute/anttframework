/**
 * 
 */
package org.tephrochronology.model;

import static javax.persistence.DiscriminatorType.CHAR;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * All samples in the database extend this type.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "samples")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "sample_type", discriminatorType = CHAR)
@XmlSeeAlso({ BIASample.class, IceCoreSample.class, LakeSample.class,
		MarineSample.class, OutcropSample.class })
@XmlRootElement
//@formatter:off
@NamedQuery(name = Sample.QUERY_GET_SAMPLE_INFO, query = 
  "SELECT NEW org.tephrochronology.model.SampleInfo(TYPE(s), "
+ "s.sampleID, s.secondaryID, s.sampledBy, s.comments, "
+ "s.collectionDate, s.category.categoryID, s.instrument.id) "
+ "FROM Sample s "
+ "ORDER BY TYPE(s), s.sampleID, s.collectionDate")
//@formatter:on
public abstract class Sample implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_GET_SAMPLE_INFO = "org.tephrochronology.model.Sample.getSampleInfo";

	@Id
	@Column(name = "sample_id")
	private String sampleID;

	@Column(name = "secondary_id")
	private String secondaryID;

	@Column(name = "sampled_by")
	private String sampledBy;

	@Column(name = "collection_date")
	private LocalDate collectionDate;

	@Column(name = "comments")
	private String comments;

	@JoinColumn(name = "category_id")
	private Category category;

	@JoinColumn(name = "iid")
	private Instrument instrument;

	/**
	 * A sample may have many references, and a reference may be used by many
	 * samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_refs", joinColumns = {
			@JoinColumn(name = "sample_id") }, inverseJoinColumns = {
					@JoinColumn(name = "doi") })
	private List<Ref> refs;

	/**
	 * A sample may have many images, and an image may be used by many samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_images", joinColumns = {
			@JoinColumn(name = "sample_id") }, inverseJoinColumns = {
					@JoinColumn(name = "image_id") })
	private List<Image> images;

	public Sample() {
	}

	public Sample(String sampleID, String secondaryID, String sampledBy,
			LocalDate collectionDate, String comments, Category category,
			Instrument instrument, List<Ref> refs, List<Image> images) {
		super();
		this.sampleID = sampleID;
		this.secondaryID = secondaryID;
		this.sampledBy = sampledBy;
		this.collectionDate = collectionDate;
		this.comments = comments;
		this.category = category;
		this.instrument = instrument;
		this.refs = refs;
		this.images = images;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getSecondaryID() {
		return secondaryID;
	}

	public void setSecondaryID(String secondaryID) {
		this.secondaryID = secondaryID;
	}

	public String getSampledBy() {
		return sampledBy;
	}

	public void setSampledBy(String sampledBy) {
		this.sampledBy = sampledBy;
	}

	public LocalDate getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(LocalDate collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public List<Ref> getRefs() {
		return refs;
	}

	public void setRefs(List<Ref> refs) {
		this.refs = refs;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
