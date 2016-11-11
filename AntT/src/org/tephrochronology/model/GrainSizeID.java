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

	Sample sample;
	Instrument instrument;

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
