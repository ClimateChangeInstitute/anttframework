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

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * Represents tephrochronology references.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "refs")
public class Ref implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "doi")
	private String doi;

	@Column(name = "ref")
	private String ref;

	/**
	 * A sample may have many references, and a reference may be used by many
	 * samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_refs", joinColumns = {
			@JoinColumn(name = "doi") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id") })
	@XmlInverseReference(mappedBy = "refs")
	private List<Sample> samples;

	@ManyToMany
	@JoinTable(name = "grain_sizes_refs", joinColumns = {
			@JoinColumn(name = "doi") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id", referencedColumnName = "sample_id"),
					@JoinColumn(name = "iid", referencedColumnName = "iid") })
	@XmlInverseReference(mappedBy = "refs")
	private List<GrainSize> grainSizes;

	public Ref() {
	}

	/**
	 * @param doi
	 * @param ref
	 * @param samples
	 * @param grainSizes
	 */
	public Ref(String doi, String ref, List<Sample> samples,
			List<GrainSize> grainSizes) {
		super();
		this.doi = doi;
		this.ref = ref;
		this.samples = samples;
		this.grainSizes = grainSizes;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public List<GrainSize> getGrainSizes() {
		return grainSizes;
	}

	public void setGrainSizes(List<GrainSize> grainSizes) {
		this.grainSizes = grainSizes;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

}
