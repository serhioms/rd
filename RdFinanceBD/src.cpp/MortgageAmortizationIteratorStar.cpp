#include "MortgageAmortizationIteratorStar.h"

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
const std::wstring MortgageAmortizationIteratorStar::className = MortgageAmortizationIteratorStar::typeid::getName();

//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
					MortgageAmortizationIteratorStar::MortgageAmortizationIteratorStar(MortgageAmortization *amortization, AmortizationType *at)
					{
					}

					MortgageAmortizationIteratorStar::MortgageAmortizationIteratorStar(MortgageAmortization *amortization, AmortizationType *at, int year, int term) : context(amortization->context), iterator(amortization->getIterator(at, year, term))
					{
					}

					bool MortgageAmortizationIteratorStar::hasNext()
					{
						return iterator->hasNext();
					}

					std::wstring *MortgageAmortizationIteratorStar::next()
					{

						MortgageAmortizationRow *obj = iterator->next();

//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						row[MortgageAmortization::C_NOP] = int::toString(obj->nop);
						row[MortgageAmortization::C_BALANCE_IN_DATE] = context->format(obj->balanceInDate);
						row[MortgageAmortization::C_BALANCE_IN] = context->format(obj->balanceIn);
						row[MortgageAmortization::C_INTEREST] = context->format(obj->interest);
						row[MortgageAmortization::C_INTEREST_PRC] = context->format(obj->interestPrc);
						row[MortgageAmortization::C_PRINCIPAL] = context->format(obj->principal);
						row[MortgageAmortization::C_PRINCIPAL_PRC] = context->format(obj->principalPrc);
						row[MortgageAmortization::C_PAYMENT_DATE] = context->format(obj->payday);

						row[MortgageAmortization::C_PAYMENT] = context->format(obj->payment);
						row[MortgageAmortization::C_PAYMENT_MO] = context->format(obj->paymentMo);

						row[MortgageAmortization::C_PAYMENT_EXTRA] = context->format(obj->extraPayment);
						row[MortgageAmortization::C_PAYMENT_EXTRA_MO] = context->format(obj->extraPaymentMo);
						row[MortgageAmortization::C_PAYMENT_EXTRA_YEAR] = context->format(obj->extraPaymentYear);
						row[MortgageAmortization::C_PAYMENT_EXTRA_PRC] = context->format(obj->extraPrc);
						row[MortgageAmortization::C_PAYMENT_EXTRA_PRC_MO] = context->format(obj->extraPrcMo);
						row[MortgageAmortization::C_PAYMENT_EXTRA_PRC_YEAR] = context->format(obj->extraPrcYear);

						row[MortgageAmortization::C_FULL_PRINCIPAL] = context->format(obj->fullPrincipal);
						row[MortgageAmortization::C_FULL_PRINCIPAL_PRC] = context->format(obj->fullPrincipalPrc);
						row[MortgageAmortization::C_PAYMENT_FULL] = context->format(obj->fullPayment);

						row[MortgageAmortization::C_TOTAL_EXTRA_PAYMENT] = context->format(obj->totalExtraPayment);
						row[MortgageAmortization::C_TOTAL_FULL_PAYMENT] = context->format(obj->totalFullPayment);
						row[MortgageAmortization::C_TOTAL_EXTRA_PRC] = context->format(obj->totalExtraPrc);
						row[MortgageAmortization::C_TOTAL_PAYMENT] = context->format(obj->totalPayment);
						row[MortgageAmortization::C_TOTAL_INTEREST] = context->format(obj->totalInterest);
						row[MortgageAmortization::C_TOTAL_INTEREST_PRC] = context->format(obj->totalInterestPrc);
						row[MortgageAmortization::C_TOTAL_PRINCIPAL] = context->format(obj->totalPrincipal);
						row[MortgageAmortization::C_TOTAL_PRINCIPAL_PRC] = context->format(obj->totalPrincipalPrc);

						row[MortgageAmortization::C_BALANCE_OUT] = context->format(obj->balanceOut);
						row[MortgageAmortization::C_BALANCE_OUT_DATE] = context->format(obj->balanceOutDate);
						row[MortgageAmortization::C_BALANCE_OUT_YEAR] = context->format(obj->balanceOutYear);
						row[MortgageAmortization::C_BALANCE_OUT_MONTH] = context->format(obj->balanceOutMonth);
						row[MortgageAmortization::C_BALANCE_OUT_MONTH_YEAR] = context->format(obj->balanceOutMonthYear);
						row[MortgageAmortization::C_BALANCE_OUT_MONTH_DAY] = context->format(obj->balanceOutMonthDay);
						row[MortgageAmortization::C_BALANCE_OUT_QUOTER] = context->format(obj->balanceOutQuoter);
						row[MortgageAmortization::C_BALANCE_TERM] = context->format(obj->balanceTerm);

						row[MortgageAmortization::C_PAY_IN_MO] = context->format(obj->payInMo);
						if (obj->paydayPrev != nullptr)
						{
							row[MortgageAmortization::C_PAYMENT_DATE_PREV] = context->format(obj->paydayPrev);
						}
						row[MortgageAmortization::C_PAYMENT_DATE_NEXT] = context->format(obj->paydayNext);

						return row;
					}

					void MortgageAmortizationIteratorStar::remove()
					{
						/* nothing */
					}
				}
			}
		}
	}
}
