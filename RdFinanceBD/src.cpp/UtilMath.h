#pragma once

#include <string>

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{



				class UtilMath
				{

				public:
					static const std::wstring className;
					static const long long serialVersionUID = className.hashCode();

					static BigDecimal round(BigDecimal bd, int scale);

					static BigDecimal ceil(BigDecimal bd, int scale);

					static double round(double value, int scale);

					static double ceil(double value, int scale);

					static int ceil(double value);

					static double max(double a, double b);

					static double min(double a, double b);

					static BigDecimal max(BigDecimal a, BigDecimal b);

					static BigDecimal min(BigDecimal a, BigDecimal b);

					static int max(int a, int b);

					static int min(int a, int b);

				};

			}
		}
	}
}
