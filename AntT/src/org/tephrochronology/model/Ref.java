/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

/**
 * TODO This class will need additional properties to match the database.
 * 
 * @author Mark Royer
 *
 */
public class Ref {

	private String doi;

	/**
	 * A sample may have many references, and a reference may be used by many samples.
	 */
	private List<Sample> samples;
	
	private List<GrainSize> grainSizes;
	
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
