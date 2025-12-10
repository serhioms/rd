
package calypsox.tk.util.apollo.generated.calypsoinmessage;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1}StatusType"/>
 *         &lt;element name="errors" type="{http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1}ErrorsElementType" minOccurs="0"/>
 *         &lt;element name="warnings" type="{http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1}WarningsElementType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1}PositiveIntegerType" fixed="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "errors",
    "warnings"
})
@XmlRootElement(name = "ApolloResponseMessage", namespace = "http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1")
public class ApolloResponseMessage {

    @XmlElement(namespace = "http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1", required = true)
    protected String status;
    @XmlElement(namespace = "http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1")
    protected ErrorsElementType errors;
    @XmlElement(namespace = "http://www.cibc.com/schemas/Apollo/ApolloResponseMessage/1")
    protected WarningsElementType warnings;
    @XmlAttribute(required = true)
    protected BigInteger version;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorsElementType }
     *     
     */
    public ErrorsElementType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorsElementType }
     *     
     */
    public void setErrors(ErrorsElementType value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link WarningsElementType }
     *     
     */
    public WarningsElementType getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningsElementType }
     *     
     */
    public void setWarnings(WarningsElementType value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVersion() {
        if (version == null) {
            return new BigInteger("1");
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

}
