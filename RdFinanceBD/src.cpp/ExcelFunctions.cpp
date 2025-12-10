#include "ExcelFunctions.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace excel
			{

const std::wstring ExcelFunctions::className = ExcelFunctions::typeid::getName();
const java::math::BigDecimal ExcelFunctions::ZERO = java::math::BigDecimal(L"0.0");
const java::math::BigDecimal ExcelFunctions::ONE = java::math::BigDecimal(L"1.0");
const java::math::BigDecimal ExcelFunctions::HUNDRED = java::math::BigDecimal(L"100.0");

				BigDecimal ExcelFunctions::PMT(BigDecimal interestRate, BigDecimal principalValue, int numberOfPeriods)
				{
					double pow = pow(interestRate.add(ONE), numberOfPeriods);
					BigDecimal pow1 = BigDecimal(pow);
					BigDecimal pow2 = (BigDecimal(pow)).subtract(ExcelFunctions::ONE);
					return interestRate.multiply(principalValue.multiply(pow1.divide(pow2, BigDecimal::ROUND_UP)))->negate();
				}
			}
		}
	}
}
