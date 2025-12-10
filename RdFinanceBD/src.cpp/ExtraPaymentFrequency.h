#pragma once

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace mortgage
			{

				using ca::mss::rd::util::UtilMisc;

				enum class ExtraPaymentFrequency
				{

//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					ANNUAL(L"Annual", L"An", 12),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					SEMI_ANNUAL(L"Semi-annual", L"SA", 6),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					QUOTERLY(L"Quoterly", L"Qo", 3),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					MONTHLY(L"Monthly", L"Mo", 1);

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String title;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String ident;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final int month;

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					private ExtraPaymentFrequency(String title, String ident, int month)
				//	{
				//		this.title = title;
				//		this.ident = ident;
				//		this.month = month;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public static ExtraPaymentFrequency getByIdent(String ident)
				//	{
				//		if(!UtilMisc.isEmpty(ident))
				//			for(ExtraPaymentFrequency v : values())
				//				if(v.ident.equalsIgnoreCase(ident))
				//					return v;
				//		return nullptr;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public String getTitle()
				//	{
				//		return title;
				//	}

					/* (non-Javadoc)
					 * @see java.lang.Enum#toString()
					 */
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public String toString()
				//	{
				//		return title;
				//	}
				};



			}
		}
	}
}
