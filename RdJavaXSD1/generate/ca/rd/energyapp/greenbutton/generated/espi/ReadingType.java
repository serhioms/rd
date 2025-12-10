
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Characteristics associated with all Readings included in a MeterReading.
 * 
 * <p>Java class for ReadingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadingType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="accumulationBehaviour" type="{http://naesb.org/espi}AccumulationBehaviourType" minOccurs="0"/>
 *         &lt;element name="commodity" type="{http://naesb.org/espi}CommodityType" minOccurs="0"/>
 *         &lt;element name="consumptionTier" type="{http://naesb.org/espi}ConsumptionTierType" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://naesb.org/espi}CurrencyCode" minOccurs="0"/>
 *         &lt;element name="dataQualifier" type="{http://naesb.org/espi}DataQualifierType" minOccurs="0"/>
 *         &lt;element name="defaultQuality" type="{http://naesb.org/espi}QualityOfReading" minOccurs="0"/>
 *         &lt;element name="flowDirection" type="{http://naesb.org/espi}FlowDirectionType" minOccurs="0"/>
 *         &lt;element name="intervalLength" type="{http://naesb.org/espi}UInt32" minOccurs="0"/>
 *         &lt;element name="kind" type="{http://naesb.org/espi}KindType" minOccurs="0"/>
 *         &lt;element name="phase" type="{http://naesb.org/espi}PhaseCode" minOccurs="0"/>
 *         &lt;element name="powerOfTenMultiplier" type="{http://naesb.org/espi}PowerOfTenMultiplierType" minOccurs="0"/>
 *         &lt;element name="timeAttribute" type="{http://naesb.org/espi}TimeAttributeType" minOccurs="0"/>
 *         &lt;element name="tou" type="{http://naesb.org/espi}TOUType" minOccurs="0"/>
 *         &lt;element name="uom" type="{http://naesb.org/espi}UomType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingType", propOrder = {
    "accumulationBehaviour",
    "commodity",
    "consumptionTier",
    "currency",
    "dataQualifier",
    "defaultQuality",
    "flowDirection",
    "intervalLength",
    "kind",
    "phase",
    "powerOfTenMultiplier",
    "timeAttribute",
    "tou",
    "uom"
})
public class ReadingType
    extends IdentifiedObject
{

    protected AccumulationBehaviourType accumulationBehaviour;
    protected CommodityType commodity;
    protected ConsumptionTierType consumptionTier;
    protected CurrencyCode currency;
    protected DataQualifierType dataQualifier;
    protected QualityOfReading defaultQuality;
    protected FlowDirectionType flowDirection;
    protected Long intervalLength;
    protected KindType kind;
    protected PhaseCode phase;
    protected Byte powerOfTenMultiplier;
    protected TimeAttributeType timeAttribute;
    protected TOUType tou;
    protected UomType uom;

    /**
     * Gets the value of the accumulationBehaviour property.
     * 
     * @return
     *     possible object is
     *     {@link AccumulationBehaviourType }
     *     
     */
    public AccumulationBehaviourType getAccumulationBehaviour() {
        return accumulationBehaviour;
    }

    /**
     * Sets the value of the accumulationBehaviour property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccumulationBehaviourType }
     *     
     */
    public void setAccumulationBehaviour(AccumulationBehaviourType value) {
        this.accumulationBehaviour = value;
    }

    /**
     * Gets the value of the commodity property.
     * 
     * @return
     *     possible object is
     *     {@link CommodityType }
     *     
     */
    public CommodityType getCommodity() {
        return commodity;
    }

    /**
     * Sets the value of the commodity property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommodityType }
     *     
     */
    public void setCommodity(CommodityType value) {
        this.commodity = value;
    }

    /**
     * Gets the value of the consumptionTier property.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionTierType }
     *     
     */
    public ConsumptionTierType getConsumptionTier() {
        return consumptionTier;
    }

    /**
     * Sets the value of the consumptionTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionTierType }
     *     
     */
    public void setConsumptionTier(ConsumptionTierType value) {
        this.consumptionTier = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyCode }
     *     
     */
    public CurrencyCode getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyCode }
     *     
     */
    public void setCurrency(CurrencyCode value) {
        this.currency = value;
    }

    /**
     * Gets the value of the dataQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link DataQualifierType }
     *     
     */
    public DataQualifierType getDataQualifier() {
        return dataQualifier;
    }

    /**
     * Sets the value of the dataQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataQualifierType }
     *     
     */
    public void setDataQualifier(DataQualifierType value) {
        this.dataQualifier = value;
    }

    /**
     * Gets the value of the defaultQuality property.
     * 
     * @return
     *     possible object is
     *     {@link QualityOfReading }
     *     
     */
    public QualityOfReading getDefaultQuality() {
        return defaultQuality;
    }

    /**
     * Sets the value of the defaultQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityOfReading }
     *     
     */
    public void setDefaultQuality(QualityOfReading value) {
        this.defaultQuality = value;
    }

    /**
     * Gets the value of the flowDirection property.
     * 
     * @return
     *     possible object is
     *     {@link FlowDirectionType }
     *     
     */
    public FlowDirectionType getFlowDirection() {
        return flowDirection;
    }

    /**
     * Sets the value of the flowDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowDirectionType }
     *     
     */
    public void setFlowDirection(FlowDirectionType value) {
        this.flowDirection = value;
    }

    /**
     * Gets the value of the intervalLength property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIntervalLength() {
        return intervalLength;
    }

    /**
     * Sets the value of the intervalLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIntervalLength(Long value) {
        this.intervalLength = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link KindType }
     *     
     */
    public KindType getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link KindType }
     *     
     */
    public void setKind(KindType value) {
        this.kind = value;
    }

    /**
     * Gets the value of the phase property.
     * 
     * @return
     *     possible object is
     *     {@link PhaseCode }
     *     
     */
    public PhaseCode getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhaseCode }
     *     
     */
    public void setPhase(PhaseCode value) {
        this.phase = value;
    }

    /**
     * Gets the value of the powerOfTenMultiplier property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPowerOfTenMultiplier() {
        return powerOfTenMultiplier;
    }

    /**
     * Sets the value of the powerOfTenMultiplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPowerOfTenMultiplier(Byte value) {
        this.powerOfTenMultiplier = value;
    }

    /**
     * Gets the value of the timeAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link TimeAttributeType }
     *     
     */
    public TimeAttributeType getTimeAttribute() {
        return timeAttribute;
    }

    /**
     * Sets the value of the timeAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeAttributeType }
     *     
     */
    public void setTimeAttribute(TimeAttributeType value) {
        this.timeAttribute = value;
    }

    /**
     * Gets the value of the tou property.
     * 
     * @return
     *     possible object is
     *     {@link TOUType }
     *     
     */
    public TOUType getTou() {
        return tou;
    }

    /**
     * Sets the value of the tou property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOUType }
     *     
     */
    public void setTou(TOUType value) {
        this.tou = value;
    }

    /**
     * Gets the value of the uom property.
     * 
     * @return
     *     possible object is
     *     {@link UomType }
     *     
     */
    public UomType getUom() {
        return uom;
    }

    /**
     * Sets the value of the uom property.
     * 
     * @param value
     *     allowed object is
     *     {@link UomType }
     *     
     */
    public void setUom(UomType value) {
        this.uom = value;
    }

}
