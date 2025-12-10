package ca.rd.lingvo;


public class SoundsEnglish implements HumanSounds {

	final static public String className = SoundsEnglish.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static String[] soundBar = new String[]{
		"shIp", "bEd", "tOOk", "fOOd", "flAt", "lAst", 								/*  0-5  */
		"fEEt", "pOtato", "thIRd:", "pOUr", "rOUgh", "wHAt",						/*  6-11 */
		"fEAr", "thERe", "plAY", "tOY", "lIght", "tOWer", "flOAt",					/* 12-18 */
		"Past", "Tin", "pacK", "CHicken", "Feel", "THought", "Silly", "SHop",		/* 19-26 */
		"Best", "Does", "Great", "larGE", "Volume", "THese", "Zoo", "leiSure",		/* 27-34 */
		"Meeting", "KNows", "soNG", "Heavy", "Lovely", "WRiter", "Yellow", "Wild"	/* 35-42 */
	};

	final static int[] VOWELS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
			
	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#vowels()
	 */
	@Override
	final public int[] vowels() {
		return VOWELS;
	}

	final static int[] DIPHTHONGS = new int[]{12, 13, 14, 15, 16, 17, 18};

	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#diphthongs()
	 */
	@Override
	final public int[] diphthongs() {
		return DIPHTHONGS;
	}

	final static int[] CONSONANTS = new int[]{19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42};
	
	/* (non-Javadoc)
	 * @see ca.rd.lingvo.HumanSounds#consonants()
	 */
	@Override
	final public int[] consonants() {
		return CONSONANTS;
	}  
	
	
}


