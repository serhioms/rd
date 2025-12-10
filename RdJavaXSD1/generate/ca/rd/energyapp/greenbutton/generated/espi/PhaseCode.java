
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Valid values include:
 * 
 *  0 = Not Applicable
 *  129 = Phase AN
 *  128 = Phase A
 *  132 = Phase AB
 *  64 = Phase BN
 *  64 = Phase B
 *  32 = Phase CN
 *  32 = Phase C
 *  224 = Phase ABC
 *  66 = Phase BC
 *  40 = Phase CA
 *  512 = Phase S1
 *  256 = Phase S2
 *  768 = Phase S1S2
 *  513 = Phase S1N
 *  257 = Phase S2N
 *  769 = Phase S1S2N
 * 
 * <p>Java class for PhaseCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhaseCode">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://naesb.org/espi>UInt16">
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhaseCode", propOrder = {
    "value"
})
public class PhaseCode {

    @XmlValue
    protected int value;

    /**
     * Unsigned integer, max inclusive 65535 (2^16-1), same as xs:unsignedShort
     * 
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(int value) {
        this.value = value;
    }

}
