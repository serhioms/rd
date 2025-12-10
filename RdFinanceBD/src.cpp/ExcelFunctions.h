#pragma once

#include <string>
#include <cmath>

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace excel
			{


				class ExcelFunctions
				{

				public:
					static const std::wstring className;
					static const long long serialVersionUID = className.hashCode();

					static const BigDecimal ZERO;
					static const BigDecimal ONE;
					static const BigDecimal HUNDRED;

					static BigDecimal PMT(BigDecimal interestRate, BigDecimal principalValue, int numberOfPeriods);

				};

			}
		}
	}
}
