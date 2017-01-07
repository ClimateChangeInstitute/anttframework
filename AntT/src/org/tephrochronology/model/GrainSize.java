/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "grain_sizes")
@IdClass(GrainSizeID.class)
public class GrainSize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JoinColumn(name = "sample_id")
	private Sample sample;

	@Id
	@JoinColumn(name = "iid")
	private Instrument instrument;

	@Column(name = "comments")
	private String comments;

	@Column(name = "range")
	private String range;

	@Column(name = "sample_date")
	private String sampleDate; // LocalDate?

	@ManyToMany
	@JoinTable(name = "grain_sizes_refs", joinColumns = {
			@JoinColumn(name = "sample_id", referencedColumnName = "sample_id"),
			@JoinColumn(name = "iid", referencedColumnName = "iid") }, inverseJoinColumns = {
					@JoinColumn(name = "doi") })
	private List<Ref> refs;

	public GrainSize() {
	}

	/**
	 * @param sample
	 * @param instrument
	 * @param comments
	 * @param range
	 * @param sampleDate
	 * @param refs
	 */
	public GrainSize(Sample sample, Instrument instrument, String name,
			String range, String sampleDate, List<Ref> refs) {
		super();
		this.sample = sample;
		this.instrument = instrument;
		this.comments = name;
		this.range = range;
		this.sampleDate = sampleDate;
		this.refs = refs;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(String sampleDate) {
		this.sampleDate = sampleDate;
	}

	public List<Ref> getRefs() {
		return refs;
	}

	public void setRefs(List<Ref> refs) {
		this.refs = refs;
	}

}
