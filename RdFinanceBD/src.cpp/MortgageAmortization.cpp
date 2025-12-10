#include "MortgageAmortization.h"
#include "MortgageAmortizationIteratorStar.h"
#include "MortgageAmortizationIterator.h"

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
					using ca::mss::rd::mortgage::AmortizationType;
					using ca::mss::rd::mortgage::PaymentFrequency;
					using ca::mss::rd::util::UtilMisc;
const std::wstring MortgageAmortization::className = MortgageAmortization::typeid::getName();
const java::math::BigDecimal MortgageAmortization::MINIMUM_PAYMENT = java::math::BigDecimal(L"10.0");
const std::wstring MortgageAmortization::defTitle[38] = {L"NOP", L"InDay", L"InBalance", L"Interest", L"Intrst,%", L"Principal", L"Prncpl,%", L"Payday", L"Payment", L"PaymentMo", L"ExtraPmnt", L"ExtraMo", L"ExtraYear", L"Extra,%", L"ExtraMo,%", L"ExtraYe,%", L"FullPrncpl", L"FullPrncpl,%", L"FullPmnt", L"TtlFullPmnt", L"TtlExtraPmnt", L"TtlExtra,%", L"TtlPayment", L"TtlInterest", L"TtlIntrst,%", L"TtlPrincipal", L"TtlPrncpl,%", L"OutBalance", L"OutDate", L"OutYear", L"OutMonth", L"OutMonthYear", L"OutMonthDay", L"OutQuoter", L"Term", L"Pay#", L"Prev", L"Next"};
java::util::Map<std::wstring, std::wstring> *const MortgageAmortization::title = ca::mss::rd::util::UtilMisc::toMap(L"NOP", L"no", L"InDay", L"Start", L"InBalance", L"Balance", L"Interest", L"Interest", L"Intrst,%", L"Int,%", L"Principal", L"Principal", L"Prncpl,%", L"Pri,%", L"Payday", L"Date", L"Payment", L"Pay", L"PaymentMo", L"PayMo", L"ExtraPmnt", L"Extra", L"ExtraMo", L"ExtrMo", L"ExtraYear", L"ExtrYr", L"Extra,%", L"Ex,%", L"ExtraMo,%", L"ExM,%", L"ExtraYe,%", L"ExY,%", L"FullPrncpl", L"Principal", L"FullPrncpl,%", L"Pri,%", L"FullPmnt", L"Pay", L"TtlFullPmnt", L"Payment", L"TtlExtraPmnt", L"ExtraTTL", L"TtlExtra,%", L"ExTTL,%", L"TtlPayment", L"PaymentTTL", L"TtlInterest", L"InterestTTL", L"TtlIntrst,%", L"IntTTL,%", L"TtlPrincipal", L"PrincipalTTL", L"TtlPrncpl,%", L"PriTTL,%", L"OutBalance", L"Balance", L"OutDate", L"Date", L"OutYear", L"Year", L"OutMonth", L"Month", L"OutMonthYear", L"Date", L"OutMonthDay", L"Date", L"OutQuoter", L"Quoter", L"Term", L"Term", L"Pay#", L"P#", L"Prev", L"Prev", L"Next", L"Next");

					MortgageAmortization::MortgageAmortization(MortgageContext *context, PaymentFrequency *paymentFrequency) : paymentFrequency(paymentFrequency), termNumberOfPayments(MortgageDuration::getPaymentsNo(context->termYears, 0, paymentFrequency)), totalNumberOfPayments(context->duration->getPaymentsNo(paymentFrequency)), context(context)
					{
					}

					int MortgageAmortization::getColumnWidth(int order)
					{
						switch (order)
						{
						case C_NOP:
						case C_BALANCE_TERM:
							return 3;
						case C_BALANCE_IN_DATE:
						case C_BALANCE_OUT_DATE:
						case C_PAYMENT_DATE:
							return 9;
						case C_BALANCE_OUT_YEAR:
							return 5;
						case C_BALANCE_OUT_MONTH:
							return 4;
						case C_BALANCE_OUT_MONTH_YEAR:
						case C_BALANCE_OUT_MONTH_DAY:
						case C_BALANCE_OUT_QUOTER:
							return 6;
						default:
							return DEFAULT_COLUMN_WIDTH;
						}
					}

					int MortgageAmortization::getColumnWidth()
					{
						return DEFAULT_COLUMN_WIDTH;
					}

					std::wstring MortgageAmortization::getColumnTitle(int order)
					{
						return title->get(defTitle[order]);
					}

					Iterator<std::wstring[]> *MortgageAmortization::getIteratorStar(AmortizationType *at)
					{
						return getIteratorStar(at, 0, 0);
					}

					Iterator<std::wstring[]> *MortgageAmortization::getIteratorStar(AmortizationType *at, int year, int term)
					{
						return new MortgageAmortizationIteratorStar(this, at, year, term);
					}

					Iterator<MortgageAmortizationRow*> *MortgageAmortization::getIterator(AmortizationType *at, int year, int term)
					{
						return new MortgageAmortizationIterator(this, at, year, term);
					}

					Iterator<MortgageAmortizationRow*> *MortgageAmortization::getIterator(AmortizationType *at)
					{
						return getIterator(at, 0, 0);
					}

					void MortgageAmortization::setVisible(int index, bool b)
					{
						MortgageAmortization::isVisible_Renamed[index] = b;
					}

					void MortgageAmortization::setColumnNumber(int index, int n)
					{
						MortgageAmortization::columnNumber[index] = n;
						MortgageAmortization::isVisible_Renamed[index] = true;
					}

					bool MortgageAmortization::isVisible(int index)
					{
						return MortgageAmortization::isVisible_Renamed[index];
					}

					std::wstring MortgageAmortization::columnLabels(int column[])
					{
						std::wstring labels = L"";

						for (int i = 0; i < sizeof(column) / sizeof(column[0]); i++)
						{
							if (i > 0)
							{
								labels += std::wstring(L",");
							}
							labels += getColumnTitle(column[i]);
						}

						return labels;
					}
				}
			}
		}
	}
}
