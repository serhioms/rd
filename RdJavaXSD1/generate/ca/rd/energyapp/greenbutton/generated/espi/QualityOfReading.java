
package ca.rd.energyapp.greenbutton.generated.espi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * List of codes indicating the quality of the reading, using specification:
 * 
 *  0 - valid: data that has gone through all required validation checks and either passed them all or has been verified 
 *  7 - manually edited: Replaced or approved by a human
 *  8 - estimated using reference day: data value was replaced by a machine computed value based on analysis of historical data using the same type of measurement.
 *  9 - estimated using linear interpolation: data value was computed using linear interpolation based on the readings before and after it
 *  10 - questionable: data that has failed one or more checks
 *  11 - derived: data that has been calculated (using logic or mathematical operations), not necessarily measured directly 
 *  12 - projected (forecast): data that has been calculated as a projection or forecast of future readings
 *  13 - mixed: indicates that the quality of this reading has mixed characteristics
 *  14 - raw: data that has not gone through the validation, editing and estimation process
 *  15 - normalized for weather: the values have been adjusted to account for weather, in order to compare usage in different climates
 *  16 - other: specifies that a characteristic applies other than those defined
 *  17 - validated: data that has been validated and possibly edited and/or estimated in accordance with approved procedures
 *  18 - verified: data that failed at least one of the required validation checks but was determined to represent actual usage
 * 
 * <p>Java class for QualityOfReading complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualityOfReading">
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
@XmlType(name = "QualityOfReading", propOrder = {
    "value"
})
public class QualityOfReading {

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
