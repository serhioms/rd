#include "Mortgage.h"

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
					using ca::mss::rd::finance::FinancialFunctions;
const std::wstring Mortgage::className = Mortgage::typeid::getName();

					Mortgage::Mortgage(MortgageSettings *settings) : settings(settings)
					{
					}

					void Mortgage::computate(MortgageContext *context)
					{
						for (int i = 0; i < context->paymentType->length; i++)
						{
							context->amortizationRate[i] = FinancialFunctions::mortgagePaymentRate(context->annualRate, settings->getCompoundType()->conversionPeriods, context->paymentType[i]->paymentsPerYear);
							context->paymentRate[i] = FinancialFunctions::mortgagePaymentRate(context->annualRate, settings->getCompoundType()->conversionPeriods, context->paymentType[i]->paymentsPerYearEffective);
							context->paymentAmount[i] = FinancialFunctions::mortgagePayment(context->paymentType[i], context->principal, context->paymentRate[i], context->duration, MortgageSettings::SCALE);
						}
					}
				}
			}
		}
	}
}
