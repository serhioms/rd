package ca.rd.lingvo;


public class Transliteration {
	final static public String className = Transliteration.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public String[] ENGLISH_TO_RUSSIAN = new String[]{
	//  "shIp", "bEd", "tOOk", "fOOd", "flAt", "lAst", 								/*  0-5  */
		"И",    "Е",   "У",    "УУ",   "Э",    "АА", 
	//	"fEEt", "pOtato", "thIRd:", "pOUr", "rOUgh", "wHAt",						/*  6-11 */
		"ИИ",   "Э",      "ЁО",     "УЭ",   "А",     "О",
	//	"fEAr", "thERe", "plAY", "tOY", "lIght", "tOWer", "flOAt",					/* 12-18 */
		"ИЭ",   "ЭА",    "ЭЙ",   "ОЙ",  "АЙ",    "АУ",    "ОА", 
	//	"Past", "Tin", "pacK", "CHicken", "Feel", "THought", "Silly", "SHop",		/* 19-26 */
		"П",    "Т",   "К",    "Ч",       "Ф",    "ДС",      "С",     "Ш",
	//	"Best", "Does", "Great", "larGE", "Volume", "THese", "Zoo", "leiSure",		/* 27-34 */
		"Б",    "Д",    "Г",     "ДЖ",    "В",      "ДЗ",    "З",   "Ж",
	//	"Meeting", "KNows", "soNG", "Heavy", "Lovely", "WRiter", "Yellow", "Wild"	/* 35-42 */
		 "М",      "Н",     "НГ",   "Х",     "Л",      "Р",      "ЙЕ",     "УВ"
	};
	
	final static public String[] RUSSIAN_TO_ENGLISH = new String[]{
		"OU", "shIp", "wHAt", "tOOk", "shIp", "flAt",
		"Е", "Ё",	"Ю", "Я", 											/*  6-9  */
		"", 
		"Б",  "В",  "Г",  "Д",   "З",  "М",  "Р",					  	/* 10-16 */  	  
		"П",  "Ф",  "К",  "Т",   "C",  "Н",  "Л",  "Х",  "Ж",  			/* 17-25 */
														 "Ш", "Ц", "Й", /* 26-28 */          
		"БЬ", "ВЬ", "ГЬ", "ДЬ", "ЗЬ", "МЬ", "РЬ",  	 					/* 29-35 */
		"ПЬ", "ФЬ", "КЬ", "ТЬ", "CЬ", "НЬ", "ЛЬ", "ХЬ", "ЧЬ",			/* 36-44 */
		                                                 "Щ"			/* 45 */
	};
	
	final static public String english2russian(int english){
		return ENGLISH_TO_RUSSIAN[english];
	}
}


