#pragma once

#include "MortgageContext.h"
#include "MortgageAmortizationRow.h"
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


					using ca::mss::rd::mortgage::AmortizationType;
					using ca::mss::rd::mortgage::PaymentFrequency;
					using ca::mss::rd::util::UtilMisc;



					class MortgageAmortization
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

						static const int DEFAULT_COLUMN_WIDTH = 11;

						static const BigDecimal MINIMUM_PAYMENT;

						static const int C_NOP = 0;
						static const int C_BALANCE_IN_DATE = 1;
						static const int C_BALANCE_IN = 2;
						static const int C_INTEREST = 3;
						static const int C_INTEREST_PRC = 4;
						static const int C_PRINCIPAL = 5;
						static const int C_PRINCIPAL_PRC = 6;
						static const int C_PAYMENT_DATE = 7;
						static const int C_PAYMENT = 8;
						static const int C_PAYMENT_MO = 9;

						static const int C_PAYMENT_EXTRA = 10;
						static const int C_PAYMENT_EXTRA_MO = 11;
						static const int C_PAYMENT_EXTRA_YEAR = 12;

						static const int C_PAYMENT_EXTRA_PRC = 13;
						static const int C_PAYMENT_EXTRA_PRC_MO = 14;
						static const int C_PAYMENT_EXTRA_PRC_YEAR = 15;

						static const int C_FULL_PRINCIPAL = 16;
						static const int C_FULL_PRINCIPAL_PRC = 17;
						static const int C_PAYMENT_FULL = 18;

						static const int C_TOTAL_FULL_PAYMENT = 19;
						static const int C_TOTAL_EXTRA_PAYMENT = 20;
						static const int C_TOTAL_EXTRA_PRC = 21;
						static const int C_TOTAL_PAYMENT = 22;
						static const int C_TOTAL_INTEREST = 23;
						static const int C_TOTAL_INTEREST_PRC = 24;

						static const int C_TOTAL_PRINCIPAL = 25;
						static const int C_TOTAL_PRINCIPAL_PRC = 26;

						static const int C_BALANCE_OUT = 27;
						static const int C_BALANCE_OUT_DATE = 28;
						static const int C_BALANCE_OUT_YEAR = 29;
						static const int C_BALANCE_OUT_MONTH = 30;
						static const int C_BALANCE_OUT_MONTH_YEAR = 31;
						static const int C_BALANCE_OUT_MONTH_DAY = 32;
						static const int C_BALANCE_OUT_QUOTER = 33;
						static const int C_BALANCE_TERM = 34;

						static const int C_PAY_IN_MO = 35;
						static const int C_PAYMENT_DATE_PREV = 36;
						static const int C_PAYMENT_DATE_NEXT = 37;

						static const std::wstring defTitle[38];

						static Map<std::wstring, std::wstring> *const title;

//JAVA TO C++ CONVERTER NOTE: The variable isVisible was renamed since C++ does not allow variables with the same name as methods:
						static const bool isVisible_Renamed[sizeof(defTitle) / sizeof(defTitle[0])];
						static const int columnNumber[sizeof(defTitle) / sizeof(defTitle[0])];

						PaymentFrequency *const paymentFrequency;
						const int termNumberOfPayments, totalNumberOfPayments;
						MortgageContext *const context;

						MortgageAmortization(MortgageContext *context, PaymentFrequency *paymentFrequency);

						static int getColumnWidth(int order);

						static int getColumnWidth();

						static std::wstring getColumnTitle(int order);

						Iterator<std::wstring[]> *getIteratorStar(AmortizationType *at);

						Iterator<std::wstring[]> *getIteratorStar(AmortizationType *at, int year, int term);

						Iterator<MortgageAmortizationRow*> *getIterator(AmortizationType *at, int year, int term);

						Iterator<MortgageAmortizationRow*> *getIterator(AmortizationType *at);

						static void setVisible(int index, bool b);

						static void setColumnNumber(int index, int n);

						static bool isVisible(int index);

						static std::wstring columnLabels(int column[]);


					};



				}
			}
		}
	}
}
