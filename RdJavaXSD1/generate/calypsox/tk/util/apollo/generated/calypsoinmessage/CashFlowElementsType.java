
package calypsox.tk.util.apollo.generated.calypsoinmessage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Cash flows elements
 * 
 * <p>Java class for CashFlowElementsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashFlowElementsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cash_flow_value" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}CashFlowElementType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashFlowElementsType", propOrder = {
    "cashFlowValue"
})
public class CashFlowElementsType {

    @XmlElement(name = "cash_flow_value")
    protected List<CashFlowElementType> cashFlowValue;

    /**
     * Gets the value of the cashFlowValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cashFlowValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCashFlowValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CashFlowElementType }
     * 
     * 
     */
    public List<CashFlowElementType> getCashFlowValue() {
        if (cashFlowValue == null) {
            cashFlowValue = new ArrayList<CashFlowElementType>();
        }
        return this.cashFlowValue;
    }

}
