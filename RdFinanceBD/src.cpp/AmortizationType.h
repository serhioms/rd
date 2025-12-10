#pragma once

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace mortgage
			{

				enum class AmortizationType
				{

//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_PAYMENT(L"By Payment"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_MONTH(L"Monthly"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_QUOTER(L"Quoterly"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_YEAR(L"Annually"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_TERM(L"By Term"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values cannot be strings in C++:
					BY_AMORTIZATION(L"Full Amortization");


//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String title;

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					private AmortizationType(String title)
				//	{
				//		this.title = title;
				//	}

					/* (non-Javadoc)
					 * @see java.lang.Enum#toString()
					 */
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public String toString()
				//	{
				//		return title;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public String toString(int year, int term)
				//	{
				//		return title + (year==0?"":" [year="+year+"]") + (term==0?"":" [term="+term+"]");
				//	}

				};



			}
		}
	}
}
