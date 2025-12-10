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


					using ca::mss::rd::mortgage::MortgageType;


					class MortgageSettings
					{
					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();
						static const int SCALE = 2;
						static const int SCALE_PRC = 2;
						static MathContext *const MATH_CONTEXT;
					private:
						MortgageType *compoundType;

						/// <summary>
						/// Canadian mortgage by default
						/// </summary>
					public:
						MortgageSettings();

						/// <returns> the compoundType </returns>
						MortgageType *getCompoundType();

						/// <param name="compoundType"> the compoundType to set </param>
						void setCompoundType(MortgageType *compoundType);
					};
				}
			}
		}
	}
}
