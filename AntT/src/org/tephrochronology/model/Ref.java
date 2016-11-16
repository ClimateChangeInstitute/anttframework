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
 * TODO This class will need additional properties to match the database.
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

	/**
	 * A sample may have many references, and a reference may be used by many
	 * samples.
	 */
	@ManyToMany
	@JoinTable(name = "samples_refs", joinColumns = {
			@JoinColumn(name = "doi") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id") })
	@XmlInverseReference(mappedBy="refs")
	private List<Sample> samples;

	@ManyToMany
	@JoinTable(name = "grain_sizes_refs", joinColumns = {
			@JoinColumn(name = "doi") }, inverseJoinColumns = {
					@JoinColumn(name = "sample_id", referencedColumnName = "sample_id"),
					@JoinColumn(name = "iid", referencedColumnName = "iid") })
	@XmlInverseReference(mappedBy="refs")
	private List<GrainSize> grainSizes;

	public Ref() {
	}

	/**
	 * @param doi
	 */
	public Ref(String doi, List<Sample> samples, List<GrainSize> grainSizes) {
		super();
		this.doi = doi;
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

}
