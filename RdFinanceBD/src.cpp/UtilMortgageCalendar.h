#pragma once

#include <string>
#include <stdexcept>

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace mortgage
			{
				namespace util
				{


					using ca::mss::rd::mortgage::PaymentFrequency;
					using ca::mss::rd::util::UtilDateTime;



					class UtilMortgageCalendar
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

						static const int SEMIMONTHLY_DAYS = 15;
						static const int BI_WEEKLE_DAYS = 14;
						static const int WEEKLE_DAYS = 7;


						static Date getNextPayday(int hawManyPayments, PaymentFrequency *frequency, Date baseDate);

						static Date getPrevPayday(int hawManyPayments, PaymentFrequency *frequency, Date baseDate);

					};



				}
			}
		}
	}
}
