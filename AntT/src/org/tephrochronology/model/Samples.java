/**
 * 
 */
package org.tephrochronology.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used for {@link SampleInfo} XML results.
 * 
 * @author Mark Royer
 *
 */
@XmlRootElement(name="samples")
public class Samples {

	/**
	 * 
	 */
	@XmlElement(name="sample")
	List<SampleInfo> samples;
	
	public Samples(){
	}

	public Samples(List<SampleInfo> samples) {
		super();
		this.samples = samples;
	}



	public List<SampleInfo> getSamples() {
		return samples;
	}

	public void setSamples(List<SampleInfo> samples) {
		this.samples = samples;
	}
	
	
}
