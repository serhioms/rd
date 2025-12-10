#include "UtilMortgageCalendar.h"

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
const std::wstring UtilMortgageCalendar::className = UtilMortgageCalendar::typeid::getName();

					Date UtilMortgageCalendar::getNextPayday(int hawManyPayments, PaymentFrequency *frequency, Date baseDate)
					{
						switch (frequency)
						{
						case MONTHLY:
							return UtilDateTime::getDayStart(baseDate, 0, hawManyPayments, 0);
						case SEMI_MONTHLY:
							return UtilDateTime::getDayStart(baseDate, hawManyPayments*SEMIMONTHLY_DAYS, 0, 0);
						case BI_WEEKLY:
						case ACCELERATED_BI_WEEKLY:
							return UtilDateTime::getDayStart(baseDate, hawManyPayments*BI_WEEKLE_DAYS, 0, 0);
						case WEEKLY:
						case ACCELERATED_WEEKLY:
							return UtilDateTime::getDayStart(baseDate, hawManyPayments*WEEKLE_DAYS, 0, 0);
						default:
							throw std::exception(std::wstring(L"Can not calculate next payday for [payment#=") + hawManyPayments + std::wstring(L"][baseDate=") + UtilDateTime::format(baseDate) + std::wstring(L"][frequency=") + frequency + std::wstring(L"]"));
						}
					}

					Date UtilMortgageCalendar::getPrevPayday(int hawManyPayments, PaymentFrequency *frequency, Date baseDate)
					{
						return getNextPayday(-hawManyPayments, frequency, baseDate);
					}
				}
			}
		}
	}
}
