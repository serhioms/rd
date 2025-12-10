#include "MortgageAmortizationRow.h"

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
					using ca::mss::rd::mortgage::ExtraPaymentOrder;
					using ca::mss::rd::util::UtilDateTime;
const std::wstring MortgageAmortizationRow::className = MortgageAmortizationRow::typeid::getName();

					MortgageAmortizationRow::MortgageAmortizationRow(MortgageAmortization *amortization) : amortization(amortization), context(amortization->context)
					{
						InitializeInstanceFields();
					}

					bool MortgageAmortizationRow::ifProcessedExtrasBefore()
					{
						extraPayment = ExcelFunctions::ZERO;
						if (context->extraPayment.compare(ExcelFunctions::ZERO) == 1 && !(context->minimizeMoPayments && payInMo > amortization->paymentFrequency->paymentsPerMonth))
						{
							if (context->extraOrder == ExtraPaymentOrder::BEFORE_PAYMENTS)
							{
								if (nop == 1 || !UtilDateTime::isSameMonth(payday, paydayPrev))
								{
									switch (context->extraFrequency)
									{
									case ANNUAL:
										extraPayment = (UtilDateTime::getMonth(payday) - 1) == 0? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case SEMI_ANNUAL:
										extraPayment = (UtilDateTime::getMonth(payday) - 1) % 6 == 0? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case QUOTERLY:
										extraPayment = (UtilDateTime::getMonth(payday) - 1) % 3 == 0? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case MONTHLY:
										extraPayment = context->extraPayment;
										break;
									}
									return true;
								}
							}
						}
						return false;
					}

					bool MortgageAmortizationRow::ifProcessedExtrasAfter()
					{
						extraPayment = ExcelFunctions::ZERO;
						if (context->extraPayment.compare(ExcelFunctions::ZERO) > 0 && !(context->minimizeMoPayments && payInMo > amortization->paymentFrequency->paymentsPerMonth))
						{
							if (context->extraOrder == ExtraPaymentOrder::AFTER_PAYMENTS)
							{
								if (!UtilDateTime::isSameMonth(payday, paydayNext))
								{
									switch (context->extraFrequency)
									{
									case ANNUAL:
										extraPayment = UtilDateTime::getMonth(payday) == 12? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case SEMI_ANNUAL:
										extraPayment = UtilDateTime::getMonth(payday) % 6 == 0? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case QUOTERLY:
										extraPayment = UtilDateTime::getMonth(payday) % 3 == 0? context->extraPayment: ExcelFunctions::ZERO;
										break;
									case MONTHLY:
										extraPayment = context->extraPayment;
										break;
									}
									return true;
								}
							}
						}
						return false;
					}

					void MortgageAmortizationRow::InitializeInstanceFields()
					{
						nop = 0;
						balanceIn = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						interest = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						interestPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						principal = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						principalPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						payment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						paymentMo = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPayment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPaymentMo = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPaymentYear = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPrcMo = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						extraPrcYear = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						fullPayment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						fullPrincipal = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						fullPrincipalPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalFullPayment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalExtraPayment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalExtraPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalPayment = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalInterest = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalInterestPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalPrincipal = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						totalPrincipalPrc = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						balanceOut = java::math::BigDecimal(L"0.0", MortgageSettings::MATH_CONTEXT);
						balanceOutYear = 0;
						nextBalanceOutYear = 0;
						balanceTerm = 0;
						nextBalanceTerm = 0;
						payInMo = 0;
					}
				}
			}
		}
	}
}
