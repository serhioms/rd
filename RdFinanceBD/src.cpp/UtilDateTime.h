#pragma once

#include <string>
#include <stdexcept>

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{



				/// <summary>
				/// @author moskovsk
				/// 
				/// </summary>
				class UtilDateTime
				{

				public:
					static const std::wstring className;
					static const long long serialVersionUID = className.hashCode();

					static const std::wstring DATE_FORMAT_DEFAULT;

					static const std::wstring DATE_FORMAT_YYYYMMDD;
					static const std::wstring DATE_FORMAT_MMDDYYY;
					static const std::wstring DATE_FORMAT_DMMDDYYY;

					static Date parse(const std::wstring &date, const std::wstring &format);

					static Date parse(const std::wstring &date);

					static std::wstring format(Date date);


					static std::wstring format(Date date, const std::wstring &format);

					static std::wstring format(const std::wstring &date, const std::wstring &format);

					static std::wstring formatYMD(Date date);

					static std::wstring formatMDY(Date date);

					static std::wstring formatDMDY(Date date);

					static Date setDay(Date date, int day);

					static Date getDayStart(Date day, int daysLater, int monthsLater, int yearsLater);

					static int getDayOfMonth(Date date);

					static int getDayOfWeek(Date date);

					static int getHour(Date date);

					static int getMinute(Date date);

					static int getSecond(Date date);

					static int getQuoter(Date date);

					static int getDayOfYear(Date date);

					static int getWeek(Date date);

					static int getWeekOfMonth(Date date);

					static int getBiWeek(Date date);

					static int getSemiMo(Date date);

					static int getSemiYear(Date date);

					static int getMonth(Date date);

					static int getYear(Date date);

					static bool isSameYear(Date d1, Date d2);

					static bool isSameHour(Date d1, Date d2);

					static bool isSameSec(Date d1, Date d2);

					static bool isSameQuoter(Date d1, Date d2);

					static bool isSameMonth(Date d1, Date d2);

					static bool isSameWeek(Date d1, Date d2);

					static Date now();

					static Date getYearStart(Date date, int daysLater, int monthsLater, int yearsLater);

					static Date getYearStart(int year);

					static Date getYearStart(Date date);

					static long long compare(Date from, Date thru);

					static bool before(Date from, Date thru);

					static bool beforeEqual(Date from, Date thru);

					static bool after(Date from, Date thru);

					static bool afterEqual(Date from, Date thru);

					static int intervalDays(Date from, Date thru);


					static bool isMn2Fr(Date date);

					static bool isWeekEnd(Date date);

					static int getDayOfWeekCounter(int dayOfMonth);

					static std::wstring getDay(int day);

					/// <summary>
					/// Computes the date of Easter Sunday. The algorithm used was invented by 
					/// the mathematician Carl Friedrich Gauss in 1800.
					/// Let y be the year (such as 1800 or 2001).
					/// </summary>
					static int getEasterSunday(int year);

					static Date toDate(XMLGregorianCalendar *date);

				};
			}
		}
	}
}
