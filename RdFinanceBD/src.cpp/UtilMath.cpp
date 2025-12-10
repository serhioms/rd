#include "UtilMath.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{

const std::wstring UtilMath::className = UtilMath::typeid::getName();

				BigDecimal UtilMath::round(BigDecimal bd, int scale)
				{
					return bd.setScale(scale, BigDecimal::ROUND_HALF_UP);
				}

				BigDecimal UtilMath::ceil(BigDecimal bd, int scale)
				{
					return bd.setScale(scale, RoundingMode::CEILING);
				}

				double UtilMath::round(double value, int scale)
				{
					return round(BigDecimal(value), scale);
				}

				double UtilMath::ceil(double value, int scale)
				{
					return ceil(BigDecimal(value), scale);
				}

				int UtilMath::ceil(double value)
				{
					return ceil(BigDecimal(value), 0);
				}

				double UtilMath::max(double a, double b)
				{
					return (a > b)?a:b;
				}

				double UtilMath::min(double a, double b)
				{
					return (b <= a)?b:a;
				}

				BigDecimal UtilMath::max(BigDecimal a, BigDecimal b)
				{
					return a.max(b);
				}

				BigDecimal UtilMath::min(BigDecimal a, BigDecimal b)
				{
					return a.min(b);
				}

				int UtilMath::max(int a, int b)
				{
					return (a >= b)?a:b;
				}

				int UtilMath::min(int a, int b)
				{
					return (a <= b)?a:b;
				}
			}
		}
	}
}
