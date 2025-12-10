
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Valid values include:
 * 
 *  0 = Not Applicable
 *  3 = Currency
 *  4 = Current
 *  5 = CurrentAngle
 *  7 = Date
 *  8 = Demand
 *  12 = Energy
 *  15 = Frequency
 *  37 = Power
 *  38 = PowerFactor
 *  40 = QuantityPower
 *  54 = Voltage
 *  55 = VoltageAngle
 *  64 = DistortionPowerFactor
 *  155 = VolumetricFlow
 * 
 * <p>Java class for KindType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KindType">
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
@XmlType(name = "KindType", propOrder = {
    "value"
})
public class KindType {

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
