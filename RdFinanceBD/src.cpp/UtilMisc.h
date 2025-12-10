#pragma once

#include <string>
#include <unordered_map>
#include <vector>
#include <set>

namespace ca
{
	namespace mss
	{
		namespace rd
		{
			namespace util
			{


				class UtilMisc final
				{

				public:
					static std::wstring toString(std::wstring arr[], const std::wstring &separator, const std::wstring &flex);

					static std::wstring toString(std::vector<void*> &v, const std::wstring &separator, const std::wstring &flex);

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Set<String> toSet(String... vals)
					static Set<std::wstring> *toSet(...);

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Vector<String> toVector(String... args)
					static std::vector<std::wstring> toVector(...);

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, String> toMap(String... args)
					static Map<std::wstring, std::wstring> *toMap(...);

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, Object> toMapo(Object... args)
					static Map<std::wstring, void*> *toMapo(...);

//JAVA TO C++ CONVERTER TODO TASK: Use 'va_start', 'va_arg', and 'va_end' to access the parameter array within this method:
//ORIGINAL LINE: public static java.util.Map<String, Object> toMap(Object... args)
					static Map<std::wstring, void*> *toMap(...);

					static bool isEmpty(void *o);

					static bool isEmpty(const std::wstring &s);

					static bool isSameOrEmpty(const std::wstring &s1, const std::wstring &s2);

					static bool isEmpty(Set<void*> *s);

					static bool isEmpty(std::vector<void*> &list);

					static bool isTrue(const std::wstring &s, bool defaultValue);

					static std::wstring emptyIfNull(void *o);

					static std::wstring emptyIfNull(void *o, void *r);

				};

			}
		}
	}
}
