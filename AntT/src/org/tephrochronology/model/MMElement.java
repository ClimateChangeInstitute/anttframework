/**
* 
*/
package org.tephrochronology.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Major/Minor symbol data measurements.
 * 
 * @author Mark Royer
 *
 */
@Entity
@Table(name = "mm_elements")
//@formatter:off
@NamedQuery(name = MMElement.QUERY_MMElements_ORDER_BY_ID, 
            query = "SELECT mme FROM MMElement mme ORDER BY mme.mmElementID")
//@formatter:on
public class MMElement {

	public static final String QUERY_MMElements_ORDER_BY_ID = "org.tephrochronology.model.MMElement.OrderByID";

	@Id
	@Column(name = "mm_element_id")
	private String mmElementID;

	@JoinColumn(name = "sample_id")
	private Sample sample;

	@Column(name = "comments")
	private String comments;

	@JoinColumn(name = "method_type")
	private MethodType methodType;

	@JoinColumn(name = "iid")
	private Instrument instrument;

	@Column(name = "date_measured")
	private String dateMeasured;

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

	@OneToMany(mappedBy = "mmElement", cascade = { CascadeType.PERSIST })
	private List<MMElementData> elementData;

	/**
	 * @param mmElementID
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
	 * @param elementData
	 */
	public MMElement(String mmElementID, Sample sample, String comments,
			MethodType methodType, Instrument instrument, String dateMeasured,
			String measuredBy, int numberOfMeasurements, float originalTotal,
			float calculatedTotal, String instrumentSettings,
			List<MMElementData> elementData) {
		super();
		this.mmElementID = mmElementID;
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
		this.elementData = elementData;
	}

	public MMElement() {
	}

	public String getMmElementID() {
		return mmElementID;
	}

	public void setMmElementID(String mmElementID) {
		this.mmElementID = mmElementID;
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

	public String getDateMeasured() {
		return dateMeasured;
	}

	public void setDateMeasured(String dateMeasured) {
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

	public List<MMElementData> getElementData() {
		return elementData;
	}

	public void setElementData(List<MMElementData> elementData) {
		this.elementData = elementData;
	}

}
