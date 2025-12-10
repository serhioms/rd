#pragma once

#include "MortgageDuration.h"
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
					using ca::mss::rd::mortgage::ExtraPaymentFrequency;
					using ca::mss::rd::mortgage::ExtraPaymentOrder;
					using ca::mss::rd::mortgage::PaymentFrequency;
					using ca::mss::rd::util::UtilDateTime;



					class MortgageContext
					{
					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();
						const BigDecimal principal;
						MortgageDuration *const duration;
						const BigDecimal annualRate;

//JAVA TO C++ CONVERTER WARNING: Since the array size is not known in this declaration, Java to C++ Converter has converted this array to a pointer.  You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public final ca.mss.rd.mortgage.PaymentFrequency[] paymentType;
						const PaymentFrequency *paymentType;
//JAVA TO C++ CONVERTER WARNING: Since the array size is not known in this declaration, Java to C++ Converter has converted this array to a pointer.  You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public final java.math.BigDecimal[] paymentAmount;
						const BigDecimal *paymentAmount;
//JAVA TO C++ CONVERTER WARNING: Since the array size is not known in this declaration, Java to C++ Converter has converted this array to a pointer.  You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public final java.math.BigDecimal[] paymentRate;
						const BigDecimal *paymentRate;
//JAVA TO C++ CONVERTER WARNING: Since the array size is not known in this declaration, Java to C++ Converter has converted this array to a pointer.  You will need to call 'delete[]' where appropriate:
//ORIGINAL LINE: public final java.math.BigDecimal[] amortizationRate;
						const BigDecimal *amortizationRate;

						const BigDecimal extraPayment;
						ExtraPaymentFrequency *const extraFrequency;
						ExtraPaymentOrder *const extraOrder;

						const BigDecimal maxExtraMonth;
						const BigDecimal maxExtraYear;

						const bool minimizeMoPayments;

						Date startDate;
						BigDecimal termYears;

					private:
						NumberFormat *const numberFormatter;

					public:
//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
						MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears); //this(annualRate, principal, duration, startDate, termYears, ExcelFunctions.ZERO, nullptr, nullptr, ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);

//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
						MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears, BigDecimal extraPayment, ExtraPaymentFrequency *extraFrequency, ExtraPaymentOrder *extraOrder); //this(annualRate, principal, duration, startDate, termYears, extraPayment, extraFrequency, extraOrder, ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);

						MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration *duration, Date startDate, BigDecimal termYears, BigDecimal extraPayment, ExtraPaymentFrequency *extraFrequency, ExtraPaymentOrder *extraOrder, BigDecimal maxExtraMonth, BigDecimal maxExtraYear, bool minimizeMoPayments);

						BigDecimal getPayment(int index);

						BigDecimal getPayment(PaymentFrequency *pt);

						BigDecimal getPaymentRate(PaymentFrequency *pt);

						BigDecimal getAmortizationRate(PaymentFrequency *pt);

						std::wstring format(const std::wstring &s);

						std::wstring format(int n);

						std::wstring format(BigDecimal d);

						std::wstring format(Date d);

						/// <returns> the startDay </returns>
						Date getStartDate();

						/// <returns> the yearTerm </returns>
						BigDecimal getYearTerm();

						PaymentFrequency *getPaymentType(int index);

						int getPaymentTypeLength();

					};
				}
			}
		}
	}
}
