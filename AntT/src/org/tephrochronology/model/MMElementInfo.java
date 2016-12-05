/**
 * 
 */
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Used for shallow {@link MMElement} query results.
 * 
 * @author Mark Royer
 *
 */
public class MMElementInfo {

	private String longsampleID;

	private String sampleID;

	private String comments;

	private MethodType methodType;

	private String iid;

	private LocalDate dateMeasured;

	private String measuredBy;

	private int numberOfMeasurements;

	private float originalTotal;

	private float calculatedTotal;

	private String instrumentSettings;

	private Float h2o_plus;

	private Float h2o_minus;

	private Float loi;

	private Map<Element, MMElementData> elementData;

	public MMElementInfo(String longsampleID, String sampleID, String comments,
			MethodType methodType, String iid, LocalDate dateMeasured,
			String measuredBy, int numberOfMeasurements, float originalTotal,
			float calculatedTotal, String instrumentSettings, Float h2o_plus,
			Float h2o_minus, Float loi,
			Map<Element, MMElementData> elementData) {
		super();
		this.longsampleID = longsampleID;
		this.sampleID = sampleID;
		this.comments = comments;
		this.methodType = methodType;
		this.iid = iid;
		this.dateMeasured = dateMeasured;
		this.measuredBy = measuredBy;
		this.numberOfMeasurements = numberOfMeasurements;
		this.originalTotal = originalTotal;
		this.calculatedTotal = calculatedTotal;
		this.instrumentSettings = instrumentSettings;
		this.h2o_plus = h2o_plus;
		this.h2o_minus = h2o_minus;
		this.loi = loi;
		this.elementData = elementData;
	}

	public String getLongsampleID() {
		return longsampleID;
	}

	public void setLongsampleID(String longsampleID) {
		this.longsampleID = longsampleID;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public MethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public LocalDate getDateMeasured() {
		return dateMeasured;
	}

	public void setDateMeasured(LocalDate dateMeasured) {
		this.dateMeasured = dateMeasured;
	}

	public String getMeasuredBy() {
		return measuredBy;
	}

	public void setMeasuredBy(String measuredBy) {
		this.measuredBy = measuredBy;
	}

	public int getNumberOfMeasurements() {
		return numberOfMeasurements;
	}

	public void setNumberOfMeasurements(int numberOfMeasurements) {
		this.numberOfMeasurements = numberOfMeasurements;
	}

	public float getOriginalTotal() {
		return originalTotal;
	}

	public void setOriginalTotal(float originalTotal) {
		this.originalTotal = originalTotal;
	}

	public float getCalculatedTotal() {
		return calculatedTotal;
	}

	public void setCalculatedTotal(float calculatedTotal) {
		this.calculatedTotal = calculatedTotal;
	}

	public String getInstrumentSettings() {
		return instrumentSettings;
	}

	public void setInstrumentSettings(String instrumentSettings) {
		this.instrumentSettings = instrumentSettings;
	}

	public Float getH2o_plus() {
		return h2o_plus;
	}

	public void setH2o_plus(Float h2o_plus) {
		this.h2o_plus = h2o_plus;
	}

	public Float getH2o_minus() {
		return h2o_minus;
	}

	public void setH2o_minus(Float h2o_minus) {
		this.h2o_minus = h2o_minus;
	}

	public Float getLoi() {
		return loi;
	}

	public void setLoi(Float loi) {
		this.loi = loi;
	}

	public Map<Element, MMElementData> getElementData() {
		return elementData;
	}

	public void setElementData(Map<Element, MMElementData> elementData) {
		this.elementData = elementData;
	}

}
