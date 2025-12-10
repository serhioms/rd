#pragma once

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace mortgage
			{


				enum class PaymentFrequency
				{

//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					MONTHLY(L"Monthly", L"Mo", 12, 12, 1),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					SEMI_MONTHLY(L"Semi-monthly", L"sMo", 24, 24, 2),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					BI_WEEKLY(L"Bi-weekly", L"BiW", 26, 26, 2),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					WEEKLY(L"Weekly", L"Wi", 52, 52, 4),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					ACCELERATED_BI_WEEKLY(L"Accelerated-bi-weekly", L"AccBiW", 26, 12, 2),
//JAVA TO C++ CONVERTER TODO TASK: Enum values must be single integer values in C++:
					ACCELERATED_WEEKLY(L"Accelerated-weekly", L"AccWi", 52, 12, 4);


//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final int paymentsPerYear;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final int paymentsPerYearEffective;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final java.math.BigDecimal paymentsPerMonth;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String title;
//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain fields in C++:
//					public final String shortTitle;

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					private PaymentFrequency(String title, String shortTitle, int paymentsPerYear, int paymentsPerYearEffective, int paymentsPerMonth)
				//	{
				//		this.title = title;
				//		this.shortTitle = shortTitle;
				//		this.paymentsPerYear = paymentsPerYear;
				//		this.paymentsPerYearEffective = paymentsPerYearEffective;
				//		this.paymentsPerMonth = new BigDecimal(paymentsPerMonth);
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public static PaymentFrequency getFrequencyByName(String name)
				//	{
				//		final PaymentFrequency[] frc = values();
				//		for(PaymentFrequency fr : frc)
				//		{
				//			if(fr.getTitle().equalsIgnoreCase(name))
				//			{
				//				return fr;
				//			}
				//		}
				//
				//		return nullptr;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public String getTitle()
				//	{
				//		return title;
				//	}

//JAVA TO C++ CONVERTER TODO TASK: Enums cannot contain methods in C++:
//					public int getPaymentsPerYear()
				//	{
				//		return paymentsPerYear;
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
