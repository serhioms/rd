#pragma once

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

					using ca::mss::rd::mortgage::PaymentFrequency;

					class MortgageDuration
					{

					public:
//JAVA TO C++ CONVERTER NOTE: The variable years was renamed since C++ does not allow variables with the same name as methods:
						const int years_Renamed, month;

						MortgageDuration(int years, int month);

						int getPaymentsNo(PaymentFrequency *pf);

						int monthes();

						int years();

						static int getPaymentsNo(int years, int month, PaymentFrequency *pf);

						virtual std::wstring toString() override;


					};

				}
			}
		}
	}
}
