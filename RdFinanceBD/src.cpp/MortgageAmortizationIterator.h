#pragma once

#include "MortgageAmortizationIteratorFiltered.h"
#include "MortgageAmortizationRow.h"
#include "MortgageAmortization.h"
#include <string>
#include <stdexcept>

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
					using ca::mss::rd::mortgage::AmortizationType;
					using ca::mss::rd::util::UtilDateTime;



					class MortgageAmortizationIterator : public MortgageAmortizationIteratorFiltered
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

					private:
						AmortizationType *const at;

						int order;
						MortgageAmortizationRow *row;
						MortgageAmortizationRow *payrow;

					public:
//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
						MortgageAmortizationIterator(MortgageAmortization *amortization, AmortizationType *at); //this(amortization, at, 0, 0);

						MortgageAmortizationIterator(MortgageAmortization *amortization, AmortizationType *at, int year, int term);

						/* (non-Javadoc)
						 * @see java.util.Iterator#next()
						 */
						virtual MortgageAmortizationRow *next() override;


					private:
						bool isNextPaymentSamePeriod();



					private:
						void InitializeInstanceFields();
					};



				}
			}
		}
	}
}
