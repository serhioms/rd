#pragma once

#include <string>
#include <cmath>
#include <stdexcept>

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




				class FinancialFunctions
				{

				public:
					static const std::wstring className;
					static const long long serialVersionUID = className.hashCode();

					static double loanPaymentRate(BigDecimal annualRate, int paymentFrequency);

					static double mortgageCompoundRate(BigDecimal annualRate, int compoundFrequency);

					static BigDecimal mortgagePaymentRate(BigDecimal annualRate, int compoundFrequency, int effectiveYearPayments);

					static BigDecimal mortgagePaymentMonthly(BigDecimal paymentRate, BigDecimal principal, int amortizationYears);

					static BigDecimal mortgagePaymentRegular(BigDecimal paymentRate, BigDecimal principal, int totalPayments, int yearPayments);

					static BigDecimal mortgagePaymentAccelerated(BigDecimal paymentRate, BigDecimal principal, int amortizationYears, BigDecimal monthPayments);

					static BigDecimal mortgagePayment(PaymentFrequency *pf, BigDecimal principal, BigDecimal paymentRate, MortgageDuration *duration, int scale);


				};

			}
		}
	}
}
