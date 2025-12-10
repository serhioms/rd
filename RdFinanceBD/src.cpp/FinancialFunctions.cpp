#include "FinancialFunctions.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace finance
			{
				using ca::mss::rd::excel::ExcelFunctions;
				using ca::mss::rd::mortgage::PaymentFrequency;
				using ca::mss::rd::mortgage::impl::MortgageDuration;
				using ca::mss::rd::util::UtilMath;
const std::wstring FinancialFunctions::className = FinancialFunctions::typeid::getName();

				double FinancialFunctions::loanPaymentRate(BigDecimal annualRate, int paymentFrequency)
				{
					return annualRate / paymentFrequency;
				}

				double FinancialFunctions::mortgageCompoundRate(BigDecimal annualRate, int compoundFrequency)
				{
					return pow(1 + loanPaymentRate(annualRate, compoundFrequency), compoundFrequency) - 1.0;
				}

				BigDecimal FinancialFunctions::mortgagePaymentRate(BigDecimal annualRate, int compoundFrequency, int effectiveYearPayments)
				{
					return BigDecimal(pow(1 + mortgageCompoundRate(annualRate, compoundFrequency), 1.0 / effectiveYearPayments) - 1.0);
				}

				BigDecimal FinancialFunctions::mortgagePaymentMonthly(BigDecimal paymentRate, BigDecimal principal, int amortizationYears)
				{
					return mortgagePaymentRegular(paymentRate, principal, amortizationYears, PaymentFrequency::MONTHLY::paymentsPerYear);
				}

				BigDecimal FinancialFunctions::mortgagePaymentRegular(BigDecimal paymentRate, BigDecimal principal, int totalPayments, int yearPayments)
				{
					return ExcelFunctions::PMT(paymentRate, principal, totalPayments)->negate();
				}

				BigDecimal FinancialFunctions::mortgagePaymentAccelerated(BigDecimal paymentRate, BigDecimal principal, int amortizationYears, BigDecimal monthPayments)
				{
					return mortgagePaymentMonthly(paymentRate, principal, amortizationYears).divide(monthPayments);
				}

				BigDecimal FinancialFunctions::mortgagePayment(PaymentFrequency *pf, BigDecimal principal, BigDecimal paymentRate, MortgageDuration *duration, int scale)
				{
					switch (pf)
					{
					case MONTHLY:
					case SEMI_MONTHLY:
					case BI_WEEKLY:
					case WEEKLY:
						return UtilMath::round(mortgagePaymentRegular(paymentRate, principal, duration->getPaymentsNo(pf), pf->paymentsPerYear), scale);
					case ACCELERATED_BI_WEEKLY:
					case ACCELERATED_WEEKLY:
						return UtilMath::round(mortgagePaymentAccelerated(paymentRate, principal, duration->getPaymentsNo(PaymentFrequency::MONTHLY), pf->paymentsPerMonth), scale);
					default:
						throw std::exception(std::wstring(L"Not supported payment type [") + pf + std::wstring(L"]"));
					}
				}
			}
		}
	}
}
