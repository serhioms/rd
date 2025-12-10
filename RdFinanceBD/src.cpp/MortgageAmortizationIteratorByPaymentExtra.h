#pragma once

#include "MortgageAmortizationIteratorByPayment.h"
#include "MortgageAmortizationRow.h"
#include "MortgageAmortization.h"
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
					using ca::mss::rd::util::UtilDateTime;



					class MortgageAmortizationIteratorByPaymentExtra : public MortgageAmortizationIteratorByPayment
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

					private:
						MortgageAmortizationRow *row;

					public:
						MortgageAmortizationIteratorByPaymentExtra(MortgageAmortization *amortization);

						/* (non-Javadoc)
						 * @see java.util.Iterator#next()
						 */
						virtual MortgageAmortizationRow *next() override;

					};



				}
			}
		}
	}
}
