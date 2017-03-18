/**
 * 
 */
package org.tephrochronology.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Category} results that only include a small amount of sample
 * information.
 * 
 * @author Mark Royer
 *
 */
public class CategoryInfo {

	private String categoryType;

	private String categoryID;

	private List<SampleInfo> samples;

	public CategoryInfo() {
	}

	public CategoryInfo(String categoryType, Category c) {
		super();
		this.categoryType = categoryType;
		this.categoryID = c.getCategoryID();

		samples = new ArrayList<>();

		for (Sample s : c.getSamples()) {
			samples.add(new SampleInfo(c.getCategoryID(), s.getSampleID(),
					s.getSecondaryID(), s.getSampledBy(), s.getComments(),
					s.getCollectionDate(), c.getCategoryID(),
					c.getSite().getLatitude(), c.getSite().getLongitude()));
		}
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setSampleType(String sampleType) {
		this.categoryType = sampleType;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public List<SampleInfo> getSamples() {
		return samples;
	}

	public void setSamples(List<SampleInfo> samples) {
		this.samples = samples;
	}

}
