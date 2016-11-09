/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;

/**
 * All samples in the database extend this type.
 * 
 * @author Mark Royer
 *
 */
public class Sample {

	private String sampleID;
	
	private String longName;
	
	private String sampledBy;
	
	private LocalDate collectionDate;
	
	private String comments;
	
	private Site site;
	
	private Instrument instrument;

	/**
	 * @param sampleID
	 * @param longName
	 * @param sampledBy
	 * @param collectionDate
	 * @param comments
	 * @param site
	 * @param instrument
	 */
	public Sample(String sampleID, String longName, String sampledBy,
			LocalDate collectionDate, String comments, Site site,
			Instrument instrument) {
		super();
		this.sampleID = sampleID;
		this.longName = longName;
		this.sampledBy = sampledBy;
		this.collectionDate = collectionDate;
		this.comments = comments;
		this.site = site;
		this.instrument = instrument;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
}
