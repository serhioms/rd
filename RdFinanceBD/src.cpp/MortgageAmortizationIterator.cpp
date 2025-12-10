#include "MortgageAmortizationIterator.h"

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
const std::wstring MortgageAmortizationIterator::className = MortgageAmortizationIterator::typeid::getName();

//JAVA TO C++ CONVERTER TODO TASK: Calls to same-class constructors are not supported in C++ prior to C++11:
					MortgageAmortizationIterator::MortgageAmortizationIterator(MortgageAmortization *amortization, AmortizationType *at)
					{
						InitializeInstanceFields();
					}

					MortgageAmortizationIterator::MortgageAmortizationIterator(MortgageAmortization *amortization, AmortizationType *at, int year, int term) : MortgageAmortizationIteratorFiltered(amortization, year, term), at(at)
					{
						InitializeInstanceFields();
						this->order = 1;
						this->row = new MortgageAmortizationRow(amortization);
					}

					ca::mss::rd::mortgage::impl::MortgageAmortizationRow *MortgageAmortizationIterator::next()
					{

						payrow = MortgageAmortizationIteratorFiltered::next();

						row->nop = order++;

						row->paydayPrev = payrow->paydayPrev;
						row->payday = payrow->payday;
						row->paydayNext = payrow->paydayNext;

						row->balanceIn = payrow->balanceIn;
						row->balanceInDate = payrow->balanceInDate;

						row->interest = payrow->interest;
						row->principal = payrow->principal;
						row->payment = payrow->payment;

						row->extraPayment = payrow->extraPayment;
						row->extraPaymentMo = payrow->extraPaymentMo;
						row->extraPaymentYear = payrow->extraPaymentYear;
						row->fullPayment = payrow->fullPayment;

						while (isNextPaymentSamePeriod())
						{

							if (MortgageAmortizationIteratorFiltered::hasNext())
							{
								payrow = MortgageAmortizationIteratorFiltered::next();
							}
							else
							{
								break;
							}

							/* For proper calculation */
							row->interest = row->interest.add(payrow->interest);
							row->principal = row->principal.add(payrow->principal);
							row->payment = row->payment.add(payrow->payment);
							row->extraPayment = row->extraPayment.add(payrow->extraPayment);
							row->fullPayment = row->fullPayment.add(payrow->fullPayment);

						}

						row->paymentMo = payrow->paymentMo;
						row->extraPaymentMo = payrow->extraPaymentMo;
						row->extraPaymentYear = payrow->extraPaymentYear;
						row->extraPrcMo = payrow->extraPrcMo;
						row->extraPrcYear = payrow->extraPrcYear;

						row->principalPrc = row->principal.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment, BigDecimal::ROUND_UP);
						row->interestPrc = row->interest.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment, BigDecimal::ROUND_UP);
						row->extraPrc = row->extraPayment.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment, BigDecimal::ROUND_UP);

						row->totalInterest = payrow->totalInterest;
						row->totalPrincipal = payrow->totalPrincipal;
						row->totalPayment = payrow->totalPayment;
						row->totalExtraPayment = payrow->totalExtraPayment;
						row->totalExtraPrc = payrow->totalExtraPrc;
						row->totalFullPayment = payrow->totalFullPayment;
						row->totalPrincipalPrc = payrow->totalPrincipalPrc;
						row->totalInterestPrc = payrow->totalInterestPrc;

						row->balanceOut = payrow->balanceOut;
						row->balanceOutDate = payrow->balanceOutDate;

						row->balanceOutMonth = payrow->balanceOutMonth;
						row->balanceOutMonthYear = payrow->balanceOutMonthYear;
						row->balanceOutMonthDay = payrow->balanceOutMonthDay;
						row->balanceOutQuoter = payrow->balanceOutQuoter;
						row->balanceOutYear = payrow->balanceOutYear;
						row->nextBalanceOutYear = payrow->nextBalanceOutYear;
						row->balanceTerm = payrow->balanceTerm;
						row->nextBalanceTerm = payrow->nextBalanceTerm;

						row->fullPrincipal = row->principal.add(row->extraPayment);
						row->fullPrincipalPrc = row->principalPrc.add(row->extraPrc);

						row->payInMo = payrow->payInMo;

						return row;

					}

					bool MortgageAmortizationIterator::isNextPaymentSamePeriod()
					{
							switch (at)
							{
							case BY_PAYMENT:
								return false;
							case BY_MONTH:
								return UtilDateTime::isSameMonth(payrow->paydayNext, payrow->payday);
							case BY_QUOTER:
								return UtilDateTime::isSameQuoter(payrow->paydayNext, payrow->payday);
							case BY_YEAR:
								return UtilDateTime::isSameYear(payrow->paydayNext, payrow->payday);
							case BY_TERM:
								return payrow->balanceTerm == payrow->nextBalanceTerm;
							case BY_AMORTIZATION:
								return true;
							default:
								throw std::exception(std::wstring(L"Unexpected amortization type [") + at + std::wstring(L"]"));
							}
					}

					void MortgageAmortizationIterator::InitializeInstanceFields()
					{
						order = 0;
					}
				}
			}
		}
	}
}
