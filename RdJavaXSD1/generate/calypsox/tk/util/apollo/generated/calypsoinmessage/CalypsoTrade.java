
package calypsox.tk.util.apollo.generated.calypsoinmessage;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="system" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}SystemType"/>
 *         &lt;element name="trans_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="leg_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}StatusType"/>
 *         &lt;element name="trade_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="start_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="maturity_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="buy_sell" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}BuySellType"/>
 *         &lt;element name="org_unit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="book" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="base_counterparty_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="counterparty_id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="counterparty_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currency" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}CurrencyType"/>
 *         &lt;element name="notional" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reference_long" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coupon" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="standard_tenor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="basis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accrual_calendar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accrual_bdc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pay_instrument" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rec_instrument" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mtm" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="mtm_ccy" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}CurrencyType"/>
 *         &lt;element name="mtm_in_usd" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="val_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="cds_index_flag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="trader" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="execution_timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="additional_price_notation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="termination_reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allocated_swap" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="post_allocated_swap" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="is_cleared" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="is_bespoke" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="execution_venue" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}ExecutionVenueType"/>
 *         &lt;element name="is_allocated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="clearing_venue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="usi_name_space" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usi_trans_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cpty_usi_name_space" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpty_usi_trans_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="intent_to_clear" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="corporate_action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="broker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="broker_location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cleared_product_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cleared_timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="uti_name_space" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uti_trans_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mtm_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cash_flow_values" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}CashFlowElementsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.cibc.com/schemas/Apollo/CalypsoInMessage/1}PositiveIntegerType" fixed="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "system",
    "transId",
    "legId",
    "status",
    "tradeDate",
    "startDate",
    "maturityDate",
    "buySell",
    "orgUnit",
    "book",
    "baseCounterpartyId",
    "counterpartyId",
    "counterpartyName",
    "currency",
    "notional",
    "reference",
    "referenceLong",
    "coupon",
    "type",
    "frequency",
    "standardTenor",
    "basis",
    "accrualCalendar",
    "accrualBdc",
    "payInstrument",
    "recInstrument",
    "mtm",
    "mtmCcy",
    "mtmInUsd",
    "valDate",
    "cdsIndexFlag",
    "trader",
    "executionTimestamp",
    "additionalPriceNotation",
    "terminationReason",
    "comment",
    "allocatedSwap",
    "postAllocatedSwap",
    "isCleared",
    "isBespoke",
    "executionVenue",
    "isAllocated",
    "clearingVenue",
    "version",
    "usiNameSpace",
    "usiTransId",
    "cptyUsiNameSpace",
    "cptyUsiTransId",
    "intentToClear",
    "corporateAction",
    "broker",
    "brokerLocation",
    "clearedProductId",
    "clearedTimestamp",
    "utiNameSpace",
    "utiTransId",
    "mtmType",
    "cashFlowValues"
})
@XmlRootElement(name = "CalypsoTrade")
public class CalypsoTrade {

    @XmlElement(required = true)
    protected String system;
    @XmlElement(name = "trans_id")
    protected int transId;
    @XmlElement(name = "leg_id")
    protected int legId;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(name = "trade_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tradeDate;
    @XmlElement(name = "start_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "maturity_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maturityDate;
    @XmlElement(name = "buy_sell", required = true)
    protected String buySell;
    @XmlElement(name = "org_unit")
    protected int orgUnit;
    @XmlElement(required = true)
    protected String book;
    @XmlElement(name = "base_counterparty_id")
    protected int baseCounterpartyId;
    @XmlElement(name = "counterparty_id")
    protected int counterpartyId;
    @XmlElement(name = "counterparty_name", required = true)
    protected String counterpartyName;
    @XmlElement(required = true)
    protected String currency;
    @XmlElement(required = true)
    protected BigDecimal notional;
    @XmlElement(required = true)
    protected String reference;
    @XmlElement(name = "reference_long")
    protected String referenceLong;
    @XmlElement(required = true)
    protected BigDecimal coupon;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String frequency;
    @XmlElement(name = "standard_tenor", required = true)
    protected String standardTenor;
    @XmlElement(required = true)
    protected String basis;
    @XmlElement(name = "accrual_calendar")
    protected String accrualCalendar;
    @XmlElement(name = "accrual_bdc", required = true)
    protected String accrualBdc;
    @XmlElement(name = "pay_instrument", required = true)
    protected String payInstrument;
    @XmlElement(name = "rec_instrument", required = true)
    protected String recInstrument;
    @XmlElement(required = true)
    protected BigDecimal mtm;
    @XmlElement(name = "mtm_ccy", required = true)
    protected String mtmCcy;
    @XmlElement(name = "mtm_in_usd")
    protected double mtmInUsd;
    @XmlElement(name = "val_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar valDate;
    @XmlElement(name = "cds_index_flag")
    protected boolean cdsIndexFlag;
    @XmlElement(required = true)
    protected String trader;
    @XmlElement(name = "execution_timestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar executionTimestamp;
    @XmlElement(name = "additional_price_notation")
    protected boolean additionalPriceNotation;
    @XmlElement(name = "termination_reason")
    protected String terminationReason;
    protected String comment;
    @XmlElement(name = "allocated_swap")
    protected boolean allocatedSwap;
    @XmlElement(name = "post_allocated_swap")
    protected boolean postAllocatedSwap;
    @XmlElement(name = "is_cleared")
    protected boolean isCleared;
    @XmlElement(name = "is_bespoke")
    protected boolean isBespoke;
    @XmlElement(name = "execution_venue", required = true)
    protected String executionVenue;
    @XmlElement(name = "is_allocated")
    protected boolean isAllocated;
    @XmlElement(name = "clearing_venue")
    protected String clearingVenue;
    protected int version;
    @XmlElement(name = "usi_name_space", required = true)
    protected String usiNameSpace;
    @XmlElement(name = "usi_trans_id", required = true)
    protected String usiTransId;
    @XmlElement(name = "cpty_usi_name_space")
    protected String cptyUsiNameSpace;
    @XmlElement(name = "cpty_usi_trans_id")
    protected String cptyUsiTransId;
    @XmlElement(name = "intent_to_clear")
    protected boolean intentToClear;
    @XmlElement(name = "corporate_action")
    protected String corporateAction;
    @XmlElement(required = true)
    protected String broker;
    @XmlElement(name = "broker_location", required = true)
    protected String brokerLocation;
    @XmlElement(name = "cleared_product_id")
    protected String clearedProductId;
    @XmlElement(name = "cleared_timestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar clearedTimestamp;
    @XmlElement(name = "uti_name_space")
    protected String utiNameSpace;
    @XmlElement(name = "uti_trans_id")
    protected String utiTransId;
    @XmlElement(name = "mtm_type", required = true)
    protected String mtmType;
    @XmlElement(name = "cash_flow_values")
    protected CashFlowElementsType cashFlowValues;
    @XmlAttribute(name = "version", required = true)
    protected BigInteger messageVer;

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

    /**
     * Gets the value of the transId property.
     * 
     */
    public int getTransId() {
        return transId;
    }

    /**
     * Sets the value of the transId property.
     * 
     */
    public void setTransId(int value) {
        this.transId = value;
    }

    /**
     * Gets the value of the legId property.
     * 
     */
    public int getLegId() {
        return legId;
    }

    /**
     * Sets the value of the legId property.
     * 
     */
    public void setLegId(int value) {
        this.legId = value;
    }

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
     * Gets the value of the tradeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTradeDate() {
        return tradeDate;
    }

    /**
     * Sets the value of the tradeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTradeDate(XMLGregorianCalendar value) {
        this.tradeDate = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the maturityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaturityDate() {
        return maturityDate;
    }

    /**
     * Sets the value of the maturityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaturityDate(XMLGregorianCalendar value) {
        this.maturityDate = value;
    }

    /**
     * Gets the value of the buySell property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuySell() {
        return buySell;
    }

    /**
     * Sets the value of the buySell property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuySell(String value) {
        this.buySell = value;
    }

    /**
     * Gets the value of the orgUnit property.
     * 
     */
    public int getOrgUnit() {
        return orgUnit;
    }

    /**
     * Sets the value of the orgUnit property.
     * 
     */
    public void setOrgUnit(int value) {
        this.orgUnit = value;
    }

    /**
     * Gets the value of the book property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBook() {
        return book;
    }

    /**
     * Sets the value of the book property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBook(String value) {
        this.book = value;
    }

    /**
     * Gets the value of the baseCounterpartyId property.
     * 
     */
    public int getBaseCounterpartyId() {
        return baseCounterpartyId;
    }

    /**
     * Sets the value of the baseCounterpartyId property.
     * 
     */
    public void setBaseCounterpartyId(int value) {
        this.baseCounterpartyId = value;
    }

    /**
     * Gets the value of the counterpartyId property.
     * 
     */
    public int getCounterpartyId() {
        return counterpartyId;
    }

    /**
     * Sets the value of the counterpartyId property.
     * 
     */
    public void setCounterpartyId(int value) {
        this.counterpartyId = value;
    }

    /**
     * Gets the value of the counterpartyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounterpartyName() {
        return counterpartyName;
    }

    /**
     * Sets the value of the counterpartyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounterpartyName(String value) {
        this.counterpartyName = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the notional property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNotional() {
        return notional;
    }

    /**
     * Sets the value of the notional property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNotional(BigDecimal value) {
        this.notional = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the referenceLong property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceLong() {
        return referenceLong;
    }

    /**
     * Sets the value of the referenceLong property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceLong(String value) {
        this.referenceLong = value;
    }

    /**
     * Gets the value of the coupon property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoupon() {
        return coupon;
    }

    /**
     * Sets the value of the coupon property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoupon(BigDecimal value) {
        this.coupon = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrequency(String value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the standardTenor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardTenor() {
        return standardTenor;
    }

    /**
     * Sets the value of the standardTenor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardTenor(String value) {
        this.standardTenor = value;
    }

    /**
     * Gets the value of the basis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasis() {
        return basis;
    }

    /**
     * Sets the value of the basis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasis(String value) {
        this.basis = value;
    }

    /**
     * Gets the value of the accrualCalendar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccrualCalendar() {
        return accrualCalendar;
    }

    /**
     * Sets the value of the accrualCalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccrualCalendar(String value) {
        this.accrualCalendar = value;
    }

    /**
     * Gets the value of the accrualBdc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccrualBdc() {
        return accrualBdc;
    }

    /**
     * Sets the value of the accrualBdc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccrualBdc(String value) {
        this.accrualBdc = value;
    }

    /**
     * Gets the value of the payInstrument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayInstrument() {
        return payInstrument;
    }

    /**
     * Sets the value of the payInstrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayInstrument(String value) {
        this.payInstrument = value;
    }

    /**
     * Gets the value of the recInstrument property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecInstrument() {
        return recInstrument;
    }

    /**
     * Sets the value of the recInstrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecInstrument(String value) {
        this.recInstrument = value;
    }

    /**
     * Gets the value of the mtm property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMtm() {
        return mtm;
    }

    /**
     * Sets the value of the mtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMtm(BigDecimal value) {
        this.mtm = value;
    }

    /**
     * Gets the value of the mtmCcy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtmCcy() {
        return mtmCcy;
    }

    /**
     * Sets the value of the mtmCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMtmCcy(String value) {
        this.mtmCcy = value;
    }

    /**
     * Gets the value of the mtmInUsd property.
     * 
     */
    public double getMtmInUsd() {
        return mtmInUsd;
    }

    /**
     * Sets the value of the mtmInUsd property.
     * 
     */
    public void setMtmInUsd(double value) {
        this.mtmInUsd = value;
    }

    /**
     * Gets the value of the valDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValDate() {
        return valDate;
    }

    /**
     * Sets the value of the valDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValDate(XMLGregorianCalendar value) {
        this.valDate = value;
    }

    /**
     * Gets the value of the cdsIndexFlag property.
     * 
     */
    public boolean isCdsIndexFlag() {
        return cdsIndexFlag;
    }

    /**
     * Sets the value of the cdsIndexFlag property.
     * 
     */
    public void setCdsIndexFlag(boolean value) {
        this.cdsIndexFlag = value;
    }

    /**
     * Gets the value of the trader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrader() {
        return trader;
    }

    /**
     * Sets the value of the trader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrader(String value) {
        this.trader = value;
    }

    /**
     * Gets the value of the executionTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExecutionTimestamp() {
        return executionTimestamp;
    }

    /**
     * Sets the value of the executionTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExecutionTimestamp(XMLGregorianCalendar value) {
        this.executionTimestamp = value;
    }

    /**
     * Gets the value of the additionalPriceNotation property.
     * 
     */
    public boolean isAdditionalPriceNotation() {
        return additionalPriceNotation;
    }

    /**
     * Sets the value of the additionalPriceNotation property.
     * 
     */
    public void setAdditionalPriceNotation(boolean value) {
        this.additionalPriceNotation = value;
    }

    /**
     * Gets the value of the terminationReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminationReason() {
        return terminationReason;
    }

    /**
     * Sets the value of the terminationReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminationReason(String value) {
        this.terminationReason = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the allocatedSwap property.
     * 
     */
    public boolean isAllocatedSwap() {
        return allocatedSwap;
    }

    /**
     * Sets the value of the allocatedSwap property.
     * 
     */
    public void setAllocatedSwap(boolean value) {
        this.allocatedSwap = value;
    }

    /**
     * Gets the value of the postAllocatedSwap property.
     * 
     */
    public boolean isPostAllocatedSwap() {
        return postAllocatedSwap;
    }

    /**
     * Sets the value of the postAllocatedSwap property.
     * 
     */
    public void setPostAllocatedSwap(boolean value) {
        this.postAllocatedSwap = value;
    }

    /**
     * Gets the value of the isCleared property.
     * 
     */
    public boolean isIsCleared() {
        return isCleared;
    }

    /**
     * Sets the value of the isCleared property.
     * 
     */
    public void setIsCleared(boolean value) {
        this.isCleared = value;
    }

    /**
     * Gets the value of the isBespoke property.
     * 
     */
    public boolean isIsBespoke() {
        return isBespoke;
    }

    /**
     * Sets the value of the isBespoke property.
     * 
     */
    public void setIsBespoke(boolean value) {
        this.isBespoke = value;
    }

    /**
     * Gets the value of the executionVenue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutionVenue() {
        return executionVenue;
    }

    /**
     * Sets the value of the executionVenue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutionVenue(String value) {
        this.executionVenue = value;
    }

    /**
     * Gets the value of the isAllocated property.
     * 
     */
    public boolean isIsAllocated() {
        return isAllocated;
    }

    /**
     * Sets the value of the isAllocated property.
     * 
     */
    public void setIsAllocated(boolean value) {
        this.isAllocated = value;
    }

    /**
     * Gets the value of the clearingVenue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearingVenue() {
        return clearingVenue;
    }

    /**
     * Sets the value of the clearingVenue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearingVenue(String value) {
        this.clearingVenue = value;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Gets the value of the usiNameSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsiNameSpace() {
        return usiNameSpace;
    }

    /**
     * Sets the value of the usiNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsiNameSpace(String value) {
        this.usiNameSpace = value;
    }

    /**
     * Gets the value of the usiTransId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsiTransId() {
        return usiTransId;
    }

    /**
     * Sets the value of the usiTransId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsiTransId(String value) {
        this.usiTransId = value;
    }

    /**
     * Gets the value of the cptyUsiNameSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCptyUsiNameSpace() {
        return cptyUsiNameSpace;
    }

    /**
     * Sets the value of the cptyUsiNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCptyUsiNameSpace(String value) {
        this.cptyUsiNameSpace = value;
    }

    /**
     * Gets the value of the cptyUsiTransId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCptyUsiTransId() {
        return cptyUsiTransId;
    }

    /**
     * Sets the value of the cptyUsiTransId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCptyUsiTransId(String value) {
        this.cptyUsiTransId = value;
    }

    /**
     * Gets the value of the intentToClear property.
     * 
     */
    public boolean isIntentToClear() {
        return intentToClear;
    }

    /**
     * Sets the value of the intentToClear property.
     * 
     */
    public void setIntentToClear(boolean value) {
        this.intentToClear = value;
    }

    /**
     * Gets the value of the corporateAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorporateAction() {
        return corporateAction;
    }

    /**
     * Sets the value of the corporateAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorporateAction(String value) {
        this.corporateAction = value;
    }

    /**
     * Gets the value of the broker property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroker() {
        return broker;
    }

    /**
     * Sets the value of the broker property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroker(String value) {
        this.broker = value;
    }

    /**
     * Gets the value of the brokerLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrokerLocation() {
        return brokerLocation;
    }

    /**
     * Sets the value of the brokerLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrokerLocation(String value) {
        this.brokerLocation = value;
    }

    /**
     * Gets the value of the clearedProductId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearedProductId() {
        return clearedProductId;
    }

    /**
     * Sets the value of the clearedProductId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearedProductId(String value) {
        this.clearedProductId = value;
    }

    /**
     * Gets the value of the clearedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClearedTimestamp() {
        return clearedTimestamp;
    }

    /**
     * Sets the value of the clearedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClearedTimestamp(XMLGregorianCalendar value) {
        this.clearedTimestamp = value;
    }

    /**
     * Gets the value of the utiNameSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtiNameSpace() {
        return utiNameSpace;
    }

    /**
     * Sets the value of the utiNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtiNameSpace(String value) {
        this.utiNameSpace = value;
    }

    /**
     * Gets the value of the utiTransId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtiTransId() {
        return utiTransId;
    }

    /**
     * Sets the value of the utiTransId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtiTransId(String value) {
        this.utiTransId = value;
    }

    /**
     * Gets the value of the mtmType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtmType() {
        return mtmType;
    }

    /**
     * Sets the value of the mtmType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMtmType(String value) {
        this.mtmType = value;
    }

    /**
     * Gets the value of the cashFlowValues property.
     * 
     * @return
     *     possible object is
     *     {@link CashFlowElementsType }
     *     
     */
    public CashFlowElementsType getCashFlowValues() {
        return cashFlowValues;
    }

    /**
     * Sets the value of the cashFlowValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashFlowElementsType }
     *     
     */
    public void setCashFlowValues(CashFlowElementsType value) {
        this.cashFlowValues = value;
    }

    /**
     * Gets the value of the messageVer property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMessageVer() {
        if (messageVer == null) {
            return new BigInteger("1");
        } else {
            return messageVer;
        }
    }

    /**
     * Sets the value of the messageVer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMessageVer(BigInteger value) {
        this.messageVer = value;
    }

}
