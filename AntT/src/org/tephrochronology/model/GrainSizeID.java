/**
 * 
 */
package org.tephrochronology.model;

import java.io.Serializable;

/**
 * @author Mark Royer
 *
 */
public class GrainSizeID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String sample;
	String instrument;

	public GrainSizeID() {
	}

	/**
	 * @param sample
	 * @param instrument
	 */
	public GrainSizeID(String sample, String instrument) {
		super();
		this.sample = sample;
		this.instrument = instrument;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GrainSizeID) {
			GrainSizeID other = (GrainSizeID) obj;
			return sample != null && instrument != null
					&& sample.equals(other.sample)
					&& instrument.equals(other.instrument);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
