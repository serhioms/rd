#include "MortgageAmortizationIteratorByPayment.h"
#include "MortgageSettings.h"

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
const std::wstring MortgageAmortizationIteratorByPayment::className = MortgageAmortizationIteratorByPayment::typeid::getName();

					MortgageAmortizationIteratorByPayment::MortgageAmortizationIteratorByPayment(MortgageAmortization *amortization) : amortization(amortization), context(amortization->context)
					{
						InitializeInstanceFields();
						this->row = new MortgageAmortizationRow(amortization);
						this->balance = amortization->context->principal;
						this->order = 1;
					}

					bool MortgageAmortizationIteratorByPayment::hasNext()
					{
						return balance.compare(ExcelFunctions::ZERO) == 1;
					}

					ca::mss::rd::mortgage::impl::MortgageAmortizationRow *MortgageAmortizationIteratorByPayment::next()
					{

						if (row->nop == 0)
						{
							row->payday = amortization->context->getStartDate();
							row->paydayNext = UtilMortgageCalendar::getNextPayday(1, amortization->paymentFrequency, row->payday);
						}
						else
						{
							row->paydayPrev = row->payday;
							row->payday = row->paydayNext;
							row->paydayNext = UtilMortgageCalendar::getNextPayday(1, amortization->paymentFrequency, row->payday);
						}

						row->nop = order++;

						row->balanceIn = balance;
						row->balanceInDate = row->payday;

						row->interest = UtilMath::round(row->balanceIn.multiply(amortization->context->getAmortizationRate(amortization->paymentFrequency)), MortgageSettings::SCALE);
						row->principal = amortization->context->getPayment(amortization->paymentFrequency).subtract(row->interest);

						row->balanceOutDate = row->payday;
						row->balanceOutYear = static_cast<int>(UtilDateTime::format(row->payday, L"yyyy"));
						row->balanceOutMonth = UtilDateTime::format(row->payday, L"MMM");
						row->balanceOutMonthDay = UtilDateTime::format(row->payday, L"MMM d");
						row->balanceOutMonthYear = UtilDateTime::format(row->payday, L"MMM yyyy");
						row->balanceOutQuoter = UtilDateTime::getQuoter(row->payday) + std::wstring(L"/") + UtilDateTime::format(row->payday, L"yy");
						row->balanceOut = row->balanceIn.subtract(row->principal);
						row->balanceTerm = (row->nop - 1) / amortization->termNumberOfPayments + 1;

						if (!UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
						{
							row->payInMo = calculateNumberOfPaymentsInMonth(row->payday, row);
						}

						if (row->balanceOut.compare(MortgageAmortization::MINIMUM_PAYMENT) <= 0)
						{
							row->principal = row->principal.add(row->balanceOut);
							row->balanceOut = ExcelFunctions::ZERO;
						}

						row->payment = row->interest.add(row->principal);
						if (UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
						{
							row->paymentMo = row->paymentMo.add(row->payment);
						}
						else
						{
							row->paymentMo = row->payment;
						}

						row->totalInterest = row->totalInterest.add(row->interest);
						row->totalPrincipal = row->totalPrincipal.add(row->principal);
						row->totalPayment = row->totalPayment.add(row->payment);

						row->principalPrc = row->principal.multiply(ExcelFunctions::HUNDRED)->divide(row->payment, BigDecimal::ROUND_UP);
						row->interestPrc = row->interest.multiply(ExcelFunctions::HUNDRED)->divide(row->payment, BigDecimal::ROUND_UP);
						row->totalPrincipalPrc = row->totalPrincipal.multiply(ExcelFunctions::HUNDRED)->divide(row->totalPayment, BigDecimal::ROUND_UP);
						row->totalInterestPrc = row->totalInterest.multiply(ExcelFunctions::HUNDRED)->divide(row->totalPayment, BigDecimal::ROUND_UP);

						/* next iteration */
						balance = row->balanceOut;
						row->nextBalanceOutYear = static_cast<int>(UtilDateTime::format(row->paydayNext, L"yyyy"));
						row->nextBalanceTerm = (order - 1) / amortization->termNumberOfPayments + 1;

						return row;
					}

					void MortgageAmortizationIteratorByPayment::remove()
					{
						/* nothing */
					}

					int MortgageAmortizationIteratorByPayment::calculateNumberOfPaymentsInMonth(Date payday, MortgageAmortizationRow *row)
					{
						int payInMo = 1;

						for (Date next = row->paydayNext; UtilDateTime::isSameMonth(payday, next); next = UtilMortgageCalendar::getNextPayday(1, amortization->paymentFrequency, next))
						{
								payInMo++;
						}

						for (Date prev = row->paydayPrev; UtilDateTime::isSameMonth(payday, prev); prev = UtilMortgageCalendar::getNextPayday(-1, amortization->paymentFrequency, prev))
						{
								payInMo++;
						}

						return payInMo;
					}

					void MortgageAmortizationIteratorByPayment::InitializeInstanceFields()
					{
						balance = java::math::BigDecimal(L"0.0");
						order = 0;
					}
				}
			}
		}
	}
}
