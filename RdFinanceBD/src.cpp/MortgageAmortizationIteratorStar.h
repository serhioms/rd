#pragma once

#include "MortgageContext.h"
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


					using ca::mss::rd::mortgage::AmortizationType;


					class MortgageAmortizationIteratorStar : public Iterator<std::wstring[]>
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

					private:
						MortgageContext *const context;
						Iterator<MortgageAmortizationRow*> *const iterator;

						std::wstring row[sizeof(MortgageAmortization::defTitle) / sizeof(MortgageAmortization::defTitle[0])];

					public:
//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
						MortgageAmortizationIteratorStar(MortgageAmortization *amortization, AmortizationType *at); //this(amortization, at, 0, 0);

						MortgageAmortizationIteratorStar(MortgageAmortization *amortization, AmortizationType *at, int year, int term);

						/* (non-Javadoc)
						 * @see java.util.Iterator#hasNext()
						 */
						virtual bool hasNext();

						/* (non-Javadoc)
						 * @see java.util.Iterator#next()
						 */
						std::wstring *next();

						/* (non-Javadoc)
						 * @see java.util.Iterator#remove()
						 */
						void remove();
					};
				}
			}
		}
	}
}
