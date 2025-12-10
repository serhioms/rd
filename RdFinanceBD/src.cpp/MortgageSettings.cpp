#include "MortgageSettings.h"

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
					using ca::mss::rd::mortgage::MortgageType;
const std::wstring MortgageSettings::className = MortgageSettings::typeid::getName();
java::math::MathContext *const MortgageSettings::MATH_CONTEXT = new java::math::MathContext(SCALE);

					MortgageSettings::MortgageSettings()
					{
						this->compoundType = MortgageType::CANADIAN;
					}

					MortgageType *MortgageSettings::getCompoundType()
					{
						return compoundType;
					}

					void MortgageSettings::setCompoundType(MortgageType *compoundType)
					{
						this->compoundType = compoundType;
					}
				}
			}
		}
	}
}
