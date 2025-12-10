#include "UtilDateTime.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{

const std::wstring UtilDateTime::className = UtilDateTime::typeid::getName();
const std::wstring UtilDateTime::DATE_FORMAT_DEFAULT = L"z MMM dd, yyyy kk:mm:ss";
const std::wstring UtilDateTime::DATE_FORMAT_YYYYMMDD = L"yyyy/MM/dd";
const std::wstring UtilDateTime::DATE_FORMAT_MMDDYYY = L"MMM d, yyyy";
const std::wstring UtilDateTime::DATE_FORMAT_DMMDDYYY = L"E MMM d, yyyy";

				Date UtilDateTime::parse(const std::wstring &date, const std::wstring &format)
				{
					try
					{
						SimpleDateFormat *df = new SimpleDateFormat(format);
						return df->parse(date);
					}
					catch (std::exception &e)
					{
						throw std::exception(std::wstring(L"Can not parse date [") + date + std::wstring(L"] as [") + format + std::wstring(L"]"));
					}
				}

				Date UtilDateTime::parse(const std::wstring &date)
				{
					return UtilDateTime::parse(date, DATE_FORMAT_DEFAULT);
				}

				std::wstring UtilDateTime::format(Date date)
				{
					return format(date, DATE_FORMAT_DEFAULT);
				}

				std::wstring UtilDateTime::format(Date date, const std::wstring &format)
				{
					SimpleDateFormat *df = new SimpleDateFormat(format);
					return df->format(date);
				}

				std::wstring UtilDateTime::format(const std::wstring &date, const std::wstring &format)
				{
					SimpleDateFormat *df = new SimpleDateFormat(format);
					return df->format(parse(date, DATE_FORMAT_YYYYMMDD));
				}

				std::wstring UtilDateTime::formatYMD(Date date)
				{
					return format(date, DATE_FORMAT_YYYYMMDD);
				}

				std::wstring UtilDateTime::formatMDY(Date date)
				{
					return format(date, DATE_FORMAT_MMDDYYY);
				}

				std::wstring UtilDateTime::formatDMDY(Date date)
				{
					return format(date, DATE_FORMAT_DMMDDYYY);
				}

				Date UtilDateTime::setDay(Date date, int day)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					cal->set(Calendar::DAY_OF_MONTH, day);
					return cal->getTime();
				}

				Date UtilDateTime::getDayStart(Date day, int daysLater, int monthsLater, int yearsLater)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(day);
					cal->add(Calendar::YEAR, yearsLater);
					cal->add(Calendar::MONTH, monthsLater);
					cal->add(Calendar::DAY_OF_MONTH, daysLater);
					return cal->getTime();
				}

				int UtilDateTime::getDayOfMonth(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::DAY_OF_MONTH);
				}

				int UtilDateTime::getDayOfWeek(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::DAY_OF_WEEK);
				}

				int UtilDateTime::getHour(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::HOUR_OF_DAY);
				}

				int UtilDateTime::getMinute(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::MINUTE);
				}

				int UtilDateTime::getSecond(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::SECOND);
				}

				int UtilDateTime::getQuoter(Date date)
				{
					return getMonth(date) / 3 + 1;
				}

				int UtilDateTime::getDayOfYear(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::DAY_OF_YEAR);
				}

				int UtilDateTime::getWeek(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::WEEK_OF_YEAR);
				}

				int UtilDateTime::getWeekOfMonth(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::WEEK_OF_MONTH);
				}

				int UtilDateTime::getBiWeek(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return (cal->get(Calendar::WEEK_OF_MONTH) - 1) / 2 + 1;

				}

				int UtilDateTime::getSemiMo(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::DAY_OF_MONTH) < 15? 1: 2;

				}

				int UtilDateTime::getSemiYear(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::DAY_OF_YEAR) <= 177? 1: 2;

				}

				int UtilDateTime::getMonth(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::MONTH);
				}

				int UtilDateTime::getYear(Date date)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					return cal->get(Calendar::YEAR);
				}

				bool UtilDateTime::isSameYear(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return UtilDateTime::getYear(d1) == UtilDateTime::getYear(d2);
					}
					else
					{
						return false;
					}
				}

				bool UtilDateTime::isSameHour(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return UtilDateTime::getHour(d1) == UtilDateTime::getHour(d2);
					}
					else
					{
						return false;
					}
				}

				bool UtilDateTime::isSameSec(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return UtilDateTime::getSecond(d1) == UtilDateTime::getSecond(d2);
					}
					else
					{
						return false;
					}
				}

				bool UtilDateTime::isSameQuoter(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return UtilDateTime::getMonth(d1) / 3 == UtilDateTime::getMonth(d2) / 3;
					}
					else
					{
						return false;
					}
				}

				bool UtilDateTime::isSameMonth(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return isSameYear(d1, d2) && UtilDateTime::getMonth(d1) == UtilDateTime::getMonth(d2);
					}
					else
					{
						return false;
					}
				}

				bool UtilDateTime::isSameWeek(Date d1, Date d2)
				{
					if (d1 != nullptr && d2 != nullptr)
					{
						return isSameYear(d1, d2) && UtilDateTime::getWeek(d1) == UtilDateTime::getWeek(d2);
					}
					else
					{
						return false;
					}
				}

				Date UtilDateTime::now()
				{
					return Date();
				}

				Date UtilDateTime::getYearStart(Date date, int daysLater, int monthsLater, int yearsLater)
				{
					Calendar *cal = Calendar::getInstance();
					cal->setTime(date);
					cal->set(cal->get(Calendar::YEAR), Calendar::JANUARY, 1, 0, 0, 0);
					cal->add(Calendar::YEAR, yearsLater);
					cal->add(Calendar::MONTH, monthsLater);
					cal->add(Calendar::DAY_OF_MONTH, daysLater);
					return cal->getTime();
				}

				Date UtilDateTime::getYearStart(int year)
				{
					Calendar *cal = Calendar::getInstance();
					cal->set(Calendar::YEAR, year);
					cal->set(Calendar::MONTH, Calendar::JANUARY);
					cal->set(Calendar::DAY_OF_MONTH, 1);
					return cal->getTime();
				}

				Date UtilDateTime::getYearStart(Date date)
				{
					return getYearStart(date, 0, 0, 0);
				}

				long long UtilDateTime::compare(Date from, Date thru)
				{
					return thru.getTime() - from.getTime();
				}

				bool UtilDateTime::before(Date from, Date thru)
				{
					return compare(from, thru) < 0;
				}

				bool UtilDateTime::beforeEqual(Date from, Date thru)
				{
					return compare(from, thru) <= 0;
				}

				bool UtilDateTime::after(Date from, Date thru)
				{
					return compare(from, thru) > 0;
				}

				bool UtilDateTime::afterEqual(Date from, Date thru)
				{
					return compare(from, thru) >= 0;
				}

				int UtilDateTime::intervalDays(Date from, Date thru)
				{
					int days = 2;

					return days;
				}

				bool UtilDateTime::isMn2Fr(Date date)
				{
					switch (UtilDateTime::getDayOfWeek(date))
					{
					case Calendar::MONDAY:
					case Calendar::TUESDAY:
					case Calendar::WEDNESDAY:
					case Calendar::THURSDAY:
					case Calendar::FRIDAY:
						return true;
					}
					return false;
				}

				bool UtilDateTime::isWeekEnd(Date date)
				{
					switch (UtilDateTime::getDayOfWeek(date))
					{
					case Calendar::SATURDAY:
					case Calendar::SUNDAY:
						return true;
					}
					return false;
				}

				int UtilDateTime::getDayOfWeekCounter(int dayOfMonth)
				{
					return dayOfMonth / 7 + (dayOfMonth % 7 > 0? 1: 0);
				}

				std::wstring UtilDateTime::getDay(int day)
				{
					switch (day)
					{
					case Calendar::MONDAY:
						return L"Mon";
					case Calendar::TUESDAY:
						return L"Tue";
					case Calendar::WEDNESDAY:
						return L"Wed";
					case Calendar::THURSDAY:
						return L"Thu";
					case Calendar::FRIDAY:
						return L"Fri";
					case Calendar::SATURDAY:
						return L"Sat";
					case Calendar::SUNDAY:
						return L"Sun";
					}
					return L"";
				}

				int UtilDateTime::getEasterSunday(int year)
				{
					// Divide y by 19 and call the remainder a. Ignore the quotient.
					int a = year % 19;
					// Divide y by 100 to get a quotient b and a remainder c.
					int b = year / 100;
					int c = year % 100;
					// Divide b by 4 to get a quotient d and a remainder e.
					int d = b / 4;
					int e = b % 4;
					// Divide 8 * b + 13 by 25 to get a quotient g. Ignore the remainder.
					int g = (8 * b + 13) / 25;
					// Divide 19 * a + b - d - g + 15 by 30 to get a remainder h. Ignore the quotient.
					int h = (19 * a + b - d - g + 15) % 30;
					// Divide c by 4 to get a quotient j and a remainder k.
					int j = c / 4;
					int k = c % 4;
					// Divide a + 11 * h by 319 to get a quotient m. Ignore the remainder.
					int m = (a + 11 * h) / 319;
					// Divide 2 * e + 2 * j - k - h + m + 32 by 7 to get a remainder r. Ignore the quotient.
					int r = (2 * e + 2 * j - k - h + m + 32) % 7;
					// Divide h - m + r + 90 by 25 to get a quotient n. Ignore the remainder.
					int month = (h - m + r + 90) / 25;
					// Divide h - m + r + n + 19 by 32 to get a remainder p.
					int day = (h - m + r + month + 19) % 32;

					Calendar *cal = Calendar::getInstance();
					cal->set(Calendar::YEAR, year);
					cal->set(Calendar::MONTH, month - 1);
					cal->set(Calendar::DAY_OF_MONTH, day);

					return cal->get(Calendar::DAY_OF_YEAR);
				}

				Date UtilDateTime::toDate(XMLGregorianCalendar *date)
				{
					return date->toGregorianCalendar()->getTime();
				}
			}
		}
	}
}
