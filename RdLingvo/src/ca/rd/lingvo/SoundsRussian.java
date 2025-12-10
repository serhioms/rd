package ca.rd.lingvo;


public class SoundsRussian implements HumanSounds {

	final static public String className = SoundsEnglish.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static String[] soundBar = new String[]{
		"А", "И", "О", "У", "Ы", "Э", 									/*  0-5  */
		"Е", "Ё",	"Ю", "Я", 											/*  6-9  */
		"Б",  "В",  "Г",  "Д",   "З",  "М",  "Р",					  	/* 10-16 */  	  
		"П",  "Ф",  "К",  "Т",   "C",  "Н",  "Л",  "Х",  "Ж",  			/* 17-25 */
														 "Ш", "Ц", "Й", /* 26-28 */          
		"БЬ", "ВЬ", "ГЬ", "ДЬ", "ЗЬ", "МЬ", "РЬ",  	 					/* 29-35 */
		"ПЬ", "ФЬ", "КЬ", "ТЬ", "CЬ", "НЬ", "ЛЬ", "ХЬ", "ЧЬ",			/* 36-44 */
		                                                 "Щ"			/* 45 */
	};

	final static int[] VOWELS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
			
	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#vowels()
	 */
	@Override
	final public int[] vowels() {
		return VOWELS;
	}

	final static int[] DIPHTHONGS = new int[]{6, 7, 8, 9};

	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#diphthongs()
	 */
	@Override
	final public int[] diphthongs() {
		return DIPHTHONGS;
	}

	final static int[] CONSONANTS = new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
	
	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#consonants()
	 */
	@Override
	final public int[] consonants() {
		return CONSONANTS;
	}
	
}


