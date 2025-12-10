#pragma once

#include "MortgageAmortizationIteratorByPaymentExtra.h"
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



					class MortgageAmortizationIteratorFiltered : public MortgageAmortizationIteratorByPaymentExtra
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

					private:
						const int year;
						const int term;

						MortgageAmortizationRow *row;

					public:
						MortgageAmortizationIteratorFiltered(MortgageAmortization *amortization, int year, int term);

						/* (non-Javadoc)
						 * @see com.prosperica.mc.impl.MortgageAmortizationIteratorByPayment#hasNext()
						 */
						virtual bool hasNext() override;

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
