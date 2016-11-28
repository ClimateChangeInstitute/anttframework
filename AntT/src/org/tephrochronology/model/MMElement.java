/**
* 
*/
package org.tephrochronology.model;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Major/Minor element data measurements.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "mm_elements")
public class MMElement {

	@Id
	@Column(name = "longsample_id")
	private String longsampleID;

	@JoinColumn(name = "sample_id")
	private Sample sample;

	@Column(name = "comments")
	private String comments;

	@JoinColumn(name = "method_type")
	private MethodType methodType;

	@JoinColumn(name = "iid")
	private Instrument instrument;

	@Column(name = "date_measured")
	private LocalDate dateMeasured;

	@Column(name = "measured_by")
	private String measuredBy;

	@Column(name = "number_of_measurements")
	private int numberOfMeasurements;

	@Column(name = "original_total")
	private float originalTotal;

	@Column(name = "calculated_total")
	private float calculatedTotal;

	@Column(name = "instrument_settings")
	private String instrumentSettings;

	@Column(name = "h2o_plus")
	private Float h2o_plus;

	@Column(name = "h2o_minus")
	private Float h2o_minus;

	@Column(name = "loi")
	private Float loi;

	@OneToMany
	@JoinTable(name = "mm_elements_data", joinColumns = {
			@JoinColumn(name = "longsample_id", referencedColumnName = "longsample_id") })
	@MapKeyJoinColumn(name = "element", updatable = false, insertable = false)
	@CollectionTable(name = "mm_elements_data", joinColumns = {
			@JoinColumn(name = "longsample_id", referencedColumnName = "longsample_id", updatable = false, insertable = false),
			@JoinColumn(name = "element", referencedColumnName = "element_name", updatable = false, insertable = false) })
	private Map<Element, MMElementData> elementData;

	/**
	 * @param longsampleID
	 * @param sample
	 * @param comments
	 * @param methodType
	 * @param instrument
	 * @param dateMeasured
	 * @param measuredBy
	 * @param numberOfMeasurements
	 * @param originalTotal
	 * @param calculatedTotal
	 * @param instrumentSettings
	 * @param h2o_plus
	 * @param h2o_minus
	 * @param loi
	 * @param elementData
	 */
	public MMElement(String longsampleID, Sample sample, String comments,
			MethodType methodType, Instrument instrument,
			LocalDate dateMeasured, String measuredBy, int numberOfMeasurements,
			float originalTotal, float calculatedTotal,
			String instrumentSettings, Float h2o_plus, Float h2o_minus,
			Float loi, Map<Element, MMElementData> elementData) {
		super();
		this.longsampleID = longsampleID;
		this.sample = sample;
		this.comments = comments;
		this.methodType = methodType;
		this.instrument = instrument;
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

	public MMElement() {
	}

	public String getLongsampleID() {
		return longsampleID;
	}

	public void setLongsampleID(String longsampleID) {
		this.longsampleID = longsampleID;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
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

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
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
