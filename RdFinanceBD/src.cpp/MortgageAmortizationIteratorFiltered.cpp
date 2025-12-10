#include "MortgageAmortizationIteratorFiltered.h"

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

const std::wstring MortgageAmortizationIteratorFiltered::className = MortgageAmortizationIteratorFiltered::typeid::getName();

					MortgageAmortizationIteratorFiltered::MortgageAmortizationIteratorFiltered(MortgageAmortization *amortization, int year, int term) : MortgageAmortizationIteratorByPaymentExtra(amortization), year(year), term(term)
					{
					}

					bool MortgageAmortizationIteratorFiltered::hasNext()
					{
						if (MortgageAmortizationIteratorByPaymentExtra::hasNext())
						{
							if (row == nullptr)
							{
								return true;
							}
							else if (year > 0 && row->nextBalanceOutYear > year)
							{
								return false;
							}
							else if (term > 0 && row->nextBalanceTerm > term)
							{
								return false;
							}
							else
							{
								return true;
							}
						}
						else
						{
							return false;
						}
					}

					ca::mss::rd::mortgage::impl::MortgageAmortizationRow *MortgageAmortizationIteratorFiltered::next()
					{
						while (true)
						{
							row = MortgageAmortizationIteratorByPaymentExtra::next();
							if (year > 0 && row->balanceOutYear < year)
							{
								continue;
							}
							else if (term > 0 && row->balanceTerm < term)
							{
								continue;
							}
							else
							{
								return row;
							}
						}
					}
				}
			}
		}
	}
}
