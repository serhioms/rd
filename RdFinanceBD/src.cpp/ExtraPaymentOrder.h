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

				enum class ExtraPaymentOrder
				{

//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					BEFORE_PAYMENTS(L"FirstDay", L"f"),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					AFTER_PAYMENTS(L"Last Day", L"l");

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String title;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String ident;

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					private ExtraPaymentOrder(String title, String ident)
				//	{
				//		this.title = title;
				//		this.ident = ident;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public static ExtraPaymentOrder getByIdent(String ident)
				//	{
				//		if(!UtilMisc.isEmpty(ident))
				//			for(ExtraPaymentOrder v : values())
				//				if(v.ident.equalsIgnoreCase(ident))
				//					return v;
				//		return nullptr;
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
