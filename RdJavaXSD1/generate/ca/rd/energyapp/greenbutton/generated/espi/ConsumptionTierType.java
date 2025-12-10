
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Valid values include:
 * 
 *  0 = Not Applicable
 *  1 = Block Tier 1
 *  2 = Block Tier 2
 *  3 = Block Tier 3
 *  4 = Block Tier 4
 *  5 = Block Tier 5
 *  6 = Block Tier 6
 *  7 = Block Tier 7
 *  8 = Block Tier 8
 *  9 = Block Tier 9
 *  10 = Block Tier 10
 *  11 = Block Tier 11
 *  12 = Block Tier 12
 *  13 = Block Tier 13
 *  14 = Block Tier 14
 *  15 = Block Tier 15
 *  16 = Block Tier 16
 * 
 * <p>Java class for ConsumptionTierType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsumptionTierType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://naesb.org/espi>UInt8">
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsumptionTierType", propOrder = {
    "value"
})
public class ConsumptionTierType {

    @XmlValue
    protected short value;

    /**
     * Unsigned integer, max inclusive 255 (2^8-1), same as xs:unsignedByte
     * 
     */
    public short getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(short value) {
        this.value = value;
    }

}
