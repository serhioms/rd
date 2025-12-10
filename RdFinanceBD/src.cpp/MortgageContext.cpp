#include "MortgageContext.h"
#include "MortgageSettings.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace mortgage
			{
				namespace impl
				{
					using ca::mss::rd::excel::ExcelFunctions;
					using ca::mss::rd::mortgage::ExtraPaymentFrequency;
					using ca::mss::rd::mortgage::ExtraPaymentOrder;
					using ca::mss::rd::mortgage::PaymentFrequency;
					using ca::mss::rd::util::UtilDateTime;
const std::wstring MortgageContext::className = MortgageContext::typeid::getName();

//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
					MortgageContext::MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears)
					{
					}

//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
					MortgageContext::MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears, BigDecimal extraPayment, ExtraPaymentFrequency *extraFrequency, ExtraPaymentOrder *extraOrder)
					{
					}

					MortgageContext::MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears, BigDecimal extraPayment, ExtraPaymentFrequency *extraFrequency, ExtraPaymentOrder *extraOrder, BigDecimal maxExtraMonth, BigDecimal maxExtraYear, bool minimizeMoPayments) : principal(principal), duration(duration), annualRate(annualRate), paymentType(PaymentFrequency::values()), paymentAmount(new BigDecimal[paymentType->length]), paymentRate(new BigDecimal[paymentType->length]), amortizationRate(new BigDecimal[paymentType->length]), extraPayment(extraPayment), extraFrequency(extraFrequency), extraOrder(extraOrder), maxExtraMonth(maxExtraMonth), maxExtraYear(maxExtraYear), minimizeMoPayments(minimizeMoPayments), numberFormatter(NumberFormat::getInstance())
					{


						this->startDate = startDate;
						this->termYears = termYears;



						this->numberFormatter->setMaximumFractionDigits(MortgageSettings::SCALE);
					}

					BigDecimal MortgageContext::getPayment(int index)
					{
						return paymentAmount[index];
					}

					BigDecimal MortgageContext::getPayment(PaymentFrequency *pt)
					{
						return paymentAmount[pt->ordinal()];
					}

					BigDecimal MortgageContext::getPaymentRate(PaymentFrequency *pt)
					{
						return paymentRate[pt->ordinal()];
					}

					BigDecimal MortgageContext::getAmortizationRate(PaymentFrequency *pt)
					{
						return amortizationRate[pt->ordinal()];
					}

					std::wstring MortgageContext::format(const std::wstring &s)
					{
						return s;
					}

					std::wstring MortgageContext::format(int n)
					{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						return int::toString(n);
					}

					std::wstring MortgageContext::format(BigDecimal d)
					{
						return numberFormatter->format(d);
					}

					std::wstring MortgageContext::format(Date d)
					{
						return UtilDateTime::format(d);
					}

					Date MortgageContext::getStartDate()
					{
						return startDate;
					}

					BigDecimal MortgageContext::getYearTerm()
					{
						return termYears;
					}

					PaymentFrequency *MortgageContext::getPaymentType(int index)
					{
						return paymentType[index];
					}

					int MortgageContext::getPaymentTypeLength()
					{
						return paymentType->length;
					}
				}
			}
		}
	}
}
