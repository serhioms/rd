#pragma once

#include "MortgageSettings.h"
#include "MortgageContext.h"
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

					using ca::mss::rd::finance::FinancialFunctions;

					class Mortgage
					{

					public:
						static const std::wstring className;
						static const long long serialVersionUID = className.hashCode();

					private:
						MortgageSettings *const settings;

						/// 
					public:
						Mortgage(MortgageSettings *settings);

						void computate(MortgageContext *context);

					};



				}
			}
		}
	}
}
