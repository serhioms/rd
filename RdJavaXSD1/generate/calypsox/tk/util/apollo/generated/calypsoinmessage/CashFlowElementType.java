
package calypsox.tk.util.apollo.generated.calypsoinmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Cash flow
 * 
 * <p>Java class for CashFlowElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashFlowElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cash_flow_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cash_flow_date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="cash_flow_amount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="cash_flow_currency" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}CurrencyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashFlowElementType", propOrder = {
    "cashFlowType",
    "cashFlowDate",
    "cashFlowAmount",
    "cashFlowCurrency"
})
public class CashFlowElementType {

    @XmlElement(name = "cash_flow_type")
    protected String cashFlowType;
    @XmlElement(name = "cash_flow_date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar cashFlowDate;
    @XmlElement(name = "cash_flow_amount")
    protected Double cashFlowAmount;
    @XmlElement(name = "cash_flow_currency")
    protected String cashFlowCurrency;

    /**
     * Gets the value of the cashFlowType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashFlowType() {
        return cashFlowType;
    }

    /**
     * Sets the value of the cashFlowType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashFlowType(String value) {
        this.cashFlowType = value;
    }

    /**
     * Gets the value of the cashFlowDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCashFlowDate() {
        return cashFlowDate;
    }

    /**
     * Sets the value of the cashFlowDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCashFlowDate(XMLGregorianCalendar value) {
        this.cashFlowDate = value;
    }

    /**
     * Gets the value of the cashFlowAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCashFlowAmount() {
        return cashFlowAmount;
    }

    /**
     * Sets the value of the cashFlowAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCashFlowAmount(Double value) {
        this.cashFlowAmount = value;
    }

    /**
     * Gets the value of the cashFlowCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashFlowCurrency() {
        return cashFlowCurrency;
    }

    /**
     * Sets the value of the cashFlowCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashFlowCurrency(String value) {
        this.cashFlowCurrency = value;
    }

}
