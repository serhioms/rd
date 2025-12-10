
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Valid values include:
 * 
 *  0 = NotApplicable
 *  1 = TOU A
 *  2 = TOU B
 *  3 = TOU C
 *  4 = TOU D
 *  5 = TOU E
 *  6 = TOU F
 *  7 = TOU G
 *  8 = TOU H
 *  9 = TOU I
 *  10 = TOU J
 *  11 = TOU K
 *  12 = TOU L
 *  13 = TOU M
 *  14 = TOU N
 *  15 = TOU O
 * 
 * <p>Java class for TOUType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TOUType">
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
@XmlType(name = "TOUType", propOrder = {
    "value"
})
public class TOUType {

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
