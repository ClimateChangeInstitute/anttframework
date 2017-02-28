package org.tephrochronology.model;

/**
 * Simple {@link Sample} results.
 * 
 * @author Mark Royer
 *
 */
public class SampleInfo {

	private String sampleID;

	private String sampleType;

	private String secondaryID;

	private String sampledBy;

	private String collectionDate;

	private String comments;

	private String categoryID;

	public SampleInfo() {
	}

	public SampleInfo(String sampleType, String sampleID, String longName,
			String sampledBy, String comments, String collectionDate,
			String categoryID) {
		super();
		this.sampleType = sampleType;
		this.sampleID = sampleID;
		this.secondaryID = longName;
		this.sampledBy = sampledBy;
		this.comments = comments;
		this.collectionDate = collectionDate;
		this.categoryID = categoryID;
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

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

}
