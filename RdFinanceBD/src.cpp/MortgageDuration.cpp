#include "MortgageDuration.h"

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
					using ca::mss::rd::mortgage::PaymentFrequency;

					MortgageDuration::MortgageDuration(int years, int month) : years(years), month(month)
					{
					}

					int MortgageDuration::getPaymentsNo(PaymentFrequency *pf)
					{
						return getPaymentsNo(years_Renamed, month, pf);
					}

					int MortgageDuration::monthes()
					{
						return years_Renamed*12 + month;
					}

					int MortgageDuration::years()
					{
						return years_Renamed + month / 12;
					}

					int MortgageDuration::getPaymentsNo(int years, int month, PaymentFrequency *pf)
					{
						return (years + month / 12)*pf->paymentsPerYear + (month % 12)*pf->paymentsPerMonth;
					}

					std::wstring MortgageDuration::toString()
					{
						return (years_Renamed > 0? years_Renamed + std::wstring(L" y"): L"") + (month > 0? month + std::wstring(L" mo"): L"");
					}
				}
			}
		}
	}
}
