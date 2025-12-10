
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Valid values include:
 * 
 *  0 = Not Applicable
 *  1 = Forward
 *  19 = Reverse
 * 
 * <p>Java class for FlowDirectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowDirectionType">
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
@XmlType(name = "FlowDirectionType", propOrder = {
    "value"
})
public class FlowDirectionType {

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
