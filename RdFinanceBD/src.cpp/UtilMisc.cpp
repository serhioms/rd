#include "UtilMisc.h"

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{

				std::wstring UtilMisc::toString(std::wstring arr[], const std::wstring &separator, const std::wstring &flex)
				{
					std::wstring result = L"";

					for (int i = 0; i < sizeof(arr) / sizeof(arr[0]); i++)
					{
						if (i > 0)
						{
							result += separator;
						}
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						result += flex + arr[i].toString() + flex;
					}

					return result;
				}

				std::wstring UtilMisc::toString(std::vector<void*> &v, const std::wstring &separator, const std::wstring &flex)
				{
					std::wstring result = L"";

					for (int i = 0, max = v.size(); i < max; i++)
					{
						if (i > 0)
						{
							result += separator;
						}
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						result += flex + v[i]->toString() + flex;
					}

					return result;
				}

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Set<String> toSet(String... vals)
				Set<std::wstring> *UtilMisc::toSet(...)
				{
					Set<std::wstring> *set = std::set<std::wstring>();

					for (std::wstring v : vals)
					{
						set->add(v);
					}

					return set;
				}

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Vector<String> toVector(String... args)
				std::vector<std::wstring> UtilMisc::toVector(...)
				{
					const std::vector<std::wstring> v = std::vector<std::wstring>();

					for (int i = 0; i < args::length; i++)
					{
						v.push_back(args[i]);
					}

					return v;
				}

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, String> toMap(String... args)
				Map<std::wstring, std::wstring> *UtilMisc::toMap(...)
				{
					Map<std::wstring, std::wstring> *map = std::unordered_map<std::wstring, std::wstring>();

					for (int i = 0; i < args::length; i += 2)
					{
						map->put(args[i + 0], args[i + 1]);
					}

					return map;
				}

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, Object> toMapo(Object... args)
				Map<std::wstring, void*> *UtilMisc::toMapo(...)
				{
					Map<std::wstring, void*> *map = std::unordered_map<std::wstring, void*>();

					for (int i = 0; i < args::length; i += 2)
					{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						map->put(args[i + 0]->toString(), args[i + 1]);
					}

					return map;
				}

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, Object> toMap(Object... args)
				Map<std::wstring, void*> *UtilMisc::toMap(...)
				{
					Map<std::wstring, void*> *map = std::unordered_map<std::wstring, void*>();

					for (int i = 0; i < args::length; i += 2)
					{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
						map->put(args[i + 0]->toString(), args[i + 1]);
					}

					return map;
				}

				bool UtilMisc::isEmpty(void *o)
				{
					return o == nullptr;
				}

				bool UtilMisc::isEmpty(const std::wstring &s)
				{
//JAVA TO C++ CONVERTER TODO TASK: There is no direct native C++ equivalent to the Java String 'trim' method:
					return s == L"" || s.trim()->length() == 0;
				}

				bool UtilMisc::isSameOrEmpty(const std::wstring &s1, const std::wstring &s2)
				{
					if (isEmpty(s1))
					{
						if (isEmpty(s2))
						{
							return true;
						}
						else
						{
							return false;
						}
					}

					if (isEmpty(s2))
					{
						return false;
					}
					else if (s1 == s2)
					{
						return true;
					}
					else
					{
						return false;
					}
				}

				bool UtilMisc::isEmpty(Set<void*> *s)
				{
					return s == nullptr || s->size() == 0;
				}

				bool UtilMisc::isEmpty(std::vector<void*> &list)
				{
					return list.empty() || list.empty();
				}

				bool UtilMisc::isTrue(const std::wstring &s, bool defaultValue)
				{
					if (!UtilMisc::isEmpty(s))
					{
						if ((std::wstring(L"true"))->equalsIgnoreCase(s))
						{
							return true;
						}
						else if ((std::wstring(L"false"))->equalsIgnoreCase(s))
						{
							return false;
						}
					}
					return defaultValue;
				}

				std::wstring UtilMisc::emptyIfNull(void *o)
				{
//JAVA TO C++ CONVERTER TODO TASK: There is no native C++ equivalent to 'toString':
					return o == nullptr ? L"" : o->toString();
				}

				std::wstring UtilMisc::emptyIfNull(void *o, void *r)
				{
					return o == nullptr ? L"" : emptyIfNull(r);
				}
			}
		}
	}
}
