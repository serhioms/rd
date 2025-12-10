
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Follows codes defined in ISO 4217. Full list at tiny.cc/4217.
 * 
 *  0 - Not Applicable
 *  36 - Australian Dollar
 *  124 - Canadian Dollar
 *  840 - US Dollar
 *  978 - Euro
 * 
 * <p>Java class for CurrencyCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrencyCode">
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
@XmlType(name = "CurrencyCode", propOrder = {
    "value"
})
public class CurrencyCode {

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
