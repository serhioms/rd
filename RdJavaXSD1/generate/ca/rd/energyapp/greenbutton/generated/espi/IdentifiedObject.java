
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * This is a root class to provide common naming attributes for all classes needing naming attributes.
 * 
 * <p>Java class for IdentifiedObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentifiedObject">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="BatchItemInfo" type="{http://naesb.org/espi}BatchItemInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifiedObject", propOrder = {
    "batchItemInfo"
})
@XmlSeeAlso({
    Authorization.class,
    ApplicationInformation.class,
    UsagePoint.class,
    MeterReading.class,
    ElectricPowerUsageSummary.class,
    ReadingType.class,
    Subscription.class,
    ElectricPowerQualitySummary.class,
    IntervalBlock.class
})
public class IdentifiedObject
    extends Object
{

    @XmlElement(name = "BatchItemInfo")
    protected BatchItemInfo batchItemInfo;

    /**
     * Gets the value of the batchItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BatchItemInfo }
     *     
     */
    public BatchItemInfo getBatchItemInfo() {
        return batchItemInfo;
    }

    /**
     * Sets the value of the batchItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchItemInfo }
     *     
     */
    public void setBatchItemInfo(BatchItemInfo value) {
        this.batchItemInfo = value;
    }

}
