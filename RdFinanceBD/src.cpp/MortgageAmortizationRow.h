#pragma once

#include "MortgageAmortization.h"
#include "MortgageContext.h"
#include <string>

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
					using ca::mss::rd::mortgage::ExtraPaymentOrder;
					using ca::mss::rd::util::UtilDateTime;



					class MortgageAmortizationRow
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

						MortgageAmortization *const amortization;
						MortgageContext *const context;

						int nop;
						Date balanceInDate;
						BigDecimal balanceIn;
						BigDecimal interest;
						BigDecimal interestPrc;
						BigDecimal principal;
						BigDecimal principalPrc;
						Date payday;
						BigDecimal payment;
						BigDecimal paymentMo;
						BigDecimal extraPayment;
						BigDecimal extraPaymentMo;
						BigDecimal extraPaymentYear;
						BigDecimal extraPrc;
						BigDecimal extraPrcMo;
						BigDecimal extraPrcYear;
						BigDecimal fullPayment;
						BigDecimal fullPrincipal;
						BigDecimal fullPrincipalPrc;
						BigDecimal totalFullPayment;
						BigDecimal totalExtraPayment;
						BigDecimal totalExtraPrc;
						BigDecimal totalPayment;
						BigDecimal totalInterest;
						BigDecimal totalInterestPrc;
						BigDecimal totalPrincipal;
						BigDecimal totalPrincipalPrc;
						BigDecimal balanceOut;
						Date balanceOutDate;
						int balanceOutYear;
						int nextBalanceOutYear; // For hasNext filtering
						std::wstring balanceOutMonth;
						std::wstring balanceOutMonthYear;
						std::wstring balanceOutMonthDay;
						std::wstring balanceOutMonthDayYear;
						std::wstring balanceOutQuoter;
						int balanceTerm;
						int nextBalanceTerm; // For hasNext filtering

						int payInMo;
						Date paydayPrev;
						Date paydayNext;

						MortgageAmortizationRow(MortgageAmortization *amortization);

						bool ifProcessedExtrasBefore();

						bool ifProcessedExtrasAfter();


					private:
						void InitializeInstanceFields();
					};



				}
			}
		}
	}
}
