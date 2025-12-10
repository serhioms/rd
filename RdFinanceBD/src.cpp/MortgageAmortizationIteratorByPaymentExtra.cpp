#include "MortgageAmortizationIteratorByPaymentExtra.h"

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
const std::wstring MortgageAmortizationIteratorByPaymentExtra::className = MortgageAmortizationIteratorByPayment::typeid::getName();

					MortgageAmortizationIteratorByPaymentExtra::MortgageAmortizationIteratorByPaymentExtra(MortgageAmortization *amortization) : MortgageAmortizationIteratorByPayment(amortization)
					{
					}

					ca::mss::rd::mortgage::impl::MortgageAmortizationRow *MortgageAmortizationIteratorByPaymentExtra::next()
					{

						row = MortgageAmortizationIteratorByPayment::next();

						if (!UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
						{
							row->extraPaymentMo = ExcelFunctions::ZERO;
							row->extraPrcMo = ExcelFunctions::ZERO;
						}
						if (!UtilDateTime::isSameYear(row->payday, row->paydayPrev))
						{
							row->extraPaymentYear = ExcelFunctions::ZERO;
							row->extraPrcYear = ExcelFunctions::ZERO;
						}

						bool ifProcessedExtrasBefore = false;
						bool ifProcessedExtrasAfter = false;

						/* check for extra payments before and after payment */
						if (row->ifProcessedExtrasBefore())
						{
							ifProcessedExtrasBefore = true;
							if (row->balanceOut.compare(row->extraPayment) >= 0)
							{
								row->balanceIn = row->balanceIn.subtract(row->extraPayment);

								/* revert */ 
								row->totalInterest = row->totalInterest.subtract(row->interest);
								row->totalPrincipal = row->totalPrincipal.subtract(row->principal);
								row->totalPayment = row->totalPayment.subtract(row->payment);
								row->paymentMo = row->paymentMo.subtract(row->payment);

								row->interest = row->balanceIn.multiply(context->getAmortizationRate(amortization->paymentFrequency));
								row->principal = context->getPayment(amortization->paymentFrequency).subtract(row->interest);
								row->balanceOut = row->balanceIn.subtract(row->principal);

								// Goto #1
							}
							else
							{
								row->extraPayment = row->balanceIn; // pay full balance
								row->balanceIn = row->balanceIn.subtract(row->extraPayment)->max(ExcelFunctions::ZERO);

								/* revert */ 
								row->totalInterest = row->totalInterest.subtract(row->interest);
								row->totalPrincipal = row->totalPrincipal.subtract(row->principal);
								row->totalPayment = row->totalPayment.subtract(row->payment);
								row->paymentMo = row->paymentMo.subtract(row->payment);

								row->interest = row->balanceIn.multiply(context->getAmortizationRate(amortization->paymentFrequency));
								row->principal = context->getPayment(amortization->paymentFrequency).subtract(row->interest);
								row->balanceOut = row->balanceIn.subtract(row->principal);
							}
						}
						else if (row->ifProcessedExtrasAfter())
						{
							ifProcessedExtrasAfter = true;
							if (row->balanceOut.compare(row->extraPayment) < 0)
							{
								row->extraPayment = row->balanceOut; // pay full balance
							}
						}
						else
						{
							/* No extras */
							row->fullPayment = row->payment;
							row->totalFullPayment = row->totalFullPayment.add(row->fullPayment);
							return row;
						}

						/* Calculate month/year extras */
						if (row->paydayPrev == nullptr || UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
						{
							row->extraPaymentMo = row->extraPaymentMo.add(row->extraPayment);
						}
						if (row->paydayPrev == nullptr || UtilDateTime::isSameYear(row->payday, row->paydayPrev))
						{
							row->extraPaymentYear = row->extraPaymentYear.add(row->extraPayment);
						}

						/* Calculate year % an check year % limitation */
						row->extraPrcYear = row->extraPaymentYear.multiply(ExcelFunctions::HUNDRED)->divide(context->principal);
						if (row->extraPrcYear.compare(ExcelFunctions::ZERO) > 0 && context->maxExtraYear.compare(ExcelFunctions::ZERO) > 0 && row->extraPrcYear.compare(context->maxExtraYear) > 0)
						{
							BigDecimal deltaPrc = row->extraPrcYear.subtract(context->maxExtraYear);
							BigDecimal deltaPayment = row->extraPayment.min(deltaPrc.multiply(context->principal)->divide(ExcelFunctions::HUNDRED));

							/* extra delta revert */
							row->extraPayment = row->extraPayment.subtract(deltaPayment);

							if (UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
							{
								row->extraPaymentMo = row->extraPaymentMo.subtract(deltaPayment);
							}

							if (UtilDateTime::isSameYear(row->payday, row->paydayPrev))
							{
								row->extraPaymentYear = row->extraPaymentYear.subtract(deltaPayment);
							}

							if (ifProcessedExtrasBefore)
							{
									row->balanceIn = row->balanceIn.add(deltaPayment);

									/* revert */ 
									row->totalInterest = row->totalInterest.subtract(row->interest);
									row->totalPrincipal = row->totalPrincipal.subtract(row->principal);
									row->totalPayment = row->totalPayment.subtract(row->payment);

									row->interest = row->balanceIn.multiply(context->getAmortizationRate(amortization->paymentFrequency));
									row->principal = context->getPayment(amortization->paymentFrequency).subtract(row->interest);
									row->balanceOut = row->balanceIn.subtract(row->principal);

									// Goto #1
							}
						}

						/* Calculate month % an check month % limitation */
						if (row->paymentMo.compare(ExcelFunctions::ZERO) > 0)
						{
							row->extraPrcMo = row->extraPaymentMo.multiply(ExcelFunctions::HUNDRED)->divide(row->paymentMo);
							if (row->extraPrcMo.compare(row->extraPrcMo) > 0 && context->maxExtraMonth.compare(ExcelFunctions::ZERO) > 0 && row->extraPrcMo.compare(context->maxExtraMonth) > 0)
							{
								BigDecimal deltaPrc = row->extraPrcMo.subtract(context->maxExtraMonth);
								BigDecimal deltaPayment = row->extraPayment.min(deltaPrc.multiply(row->paymentMo)->divide(ExcelFunctions::HUNDRED));

								/* extra delta revert */
								row->extraPayment = row->extraPayment.subtract(deltaPayment);

								if (UtilDateTime::isSameMonth(row->payday, row->paydayPrev))
								{
									row->extraPaymentMo = row->extraPaymentMo.subtract(deltaPayment);
								}

								if (UtilDateTime::isSameYear(row->payday, row->paydayPrev))
								{
									row->extraPaymentYear = row->extraPaymentYear.subtract(deltaPayment);
								}

								if (ifProcessedExtrasBefore)
								{
										row->balanceIn = row->balanceIn.add(deltaPayment);

										/* revert */ 
										row->totalInterest = row->totalInterest.subtract(row->interest);
										row->totalPrincipal = row->totalPrincipal.subtract(row->principal);
										row->totalPayment = row->totalPayment.subtract(row->payment);

										row->interest = row->balanceIn.multiply(context->getAmortizationRate(amortization->paymentFrequency));
										row->principal = context->getPayment(amortization->paymentFrequency).subtract(row->interest);
										row->balanceOut = row->balanceIn.subtract(row->principal);

										// Goto #1
								}
							}
						}
						else
						{
							row->extraPrcMo = ExcelFunctions::HUNDRED;
						}

						/* Goto here */
						if (ifProcessedExtrasBefore)
						{
							// label #1
							if (row->balanceOut.compare(MortgageAmortization::MINIMUM_PAYMENT) <= 0)
							{
								row->principal = row->principal.add(row->balanceOut);
								row->balanceOut = ExcelFunctions::ZERO;
							}

							row->payment = row->interest.add(row->principal);

							/* apply */ 
							row->totalInterest = row->totalInterest.add(row->interest);
							row->totalPrincipal = row->totalPrincipal.add(row->principal);
							row->totalPayment = row->totalPayment.add(row->payment);
						}
						else if (ifProcessedExtrasAfter)
						{
							if (row->balanceOut.compare(row->extraPayment) >= 0)
							{
								// label #2
								row->balanceOut = row->balanceOut.subtract(row->extraPayment);
							}
						}

						/* All % limits are passed */
						row->extraPrcMo = row->paymentMo.compare(ExcelFunctions::ZERO) > 0? row->extraPaymentMo.multiply(ExcelFunctions::HUNDRED)->divide(row->paymentMo): ExcelFunctions::HUNDRED;
						row->extraPrcYear = row->extraPaymentYear.multiply(ExcelFunctions::HUNDRED)->divide(context->principal);

						row->totalExtraPayment = row->totalExtraPayment.add(row->extraPayment);
						row->fullPayment = row->payment.add(row->extraPayment);
						row->totalFullPayment = row->totalFullPayment.add(row->fullPayment);

						row->principalPrc = row->principal.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment);
						row->interestPrc = row->interest.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment);
						row->extraPrc = row->extraPayment.multiply(ExcelFunctions::HUNDRED)->divide(row->fullPayment);

						row->totalPrincipalPrc = row->totalPrincipal.multiply(ExcelFunctions::HUNDRED)->divide(row->totalFullPayment);
						row->totalInterestPrc = row->totalInterest.multiply(ExcelFunctions::HUNDRED)->divide(row->totalFullPayment);
						row->totalExtraPrc = row->totalExtraPayment.multiply(ExcelFunctions::HUNDRED)->divide(row->totalFullPayment);

						row->fullPrincipal = row->principal.add(row->extraPayment);
						row->fullPrincipalPrc = row->principalPrc.add(row->extraPrc);

						/* update out balance */
						balance = row->balanceOut;

						return row;
					}
				}
			}
		}
	}
}
