#pragma once

#include "MortgageAmortizationRow.h"
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
					using ca::mss::rd::mortgage::util::UtilMortgageCalendar;
					using ca::mss::rd::util::UtilDateTime;
					using ca::mss::rd::util::UtilMath;



					class MortgageAmortizationIteratorByPayment : public Iterator<MortgageAmortizationRow*>
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

						MortgageAmortization *const amortization;
						MortgageContext *const context;

					protected:
						BigDecimal balance;

					private:
						int order;
						MortgageAmortizationRow *row;

					public:
						MortgageAmortizationIteratorByPayment(MortgageAmortization *amortization);

						/* (non-Javadoc)
						 * @see java.util.Iterator#hasNext()
						 */
						virtual bool hasNext();

						/* (non-Javadoc)
						 * @see java.util.Iterator#next()
						 */
						virtual MortgageAmortizationRow *next();

						/* (non-Javadoc)
						 * @see java.util.Iterator#remove()
						 */
						virtual void remove();

					private:
						int calculateNumberOfPaymentsInMonth(Date payday, MortgageAmortizationRow *row);

					private:
						void InitializeInstanceFields();
					};



				}
			}
		}
	}
}
