/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mark Royer
 *
 */
public class GrainSize {

	private Sample sample;
	
	private Instrument instrument;
	
	private String name;
	
	private String range;
	
	private LocalDate date;
	
	private List<Ref> refs;

	/**
	 * @param sample
	 * @param instrument
	 * @param name
	 * @param range
	 * @param date
	 * @param refs
	 */
	public GrainSize(Sample sample, Instrument instrument, String name,
			String range, LocalDate date, List<Ref> refs) {
		super();
		this.sample = sample;
		this.instrument = instrument;
		this.name = name;
		this.range = range;
		this.date = date;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Ref> getRefs() {
		return refs;
	}

	public void setRefs(List<Ref> refs) {
		this.refs = refs;
	}
	
}
