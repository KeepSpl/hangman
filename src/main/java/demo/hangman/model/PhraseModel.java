package demo.hangman.model;

public class PhraseModel {
	
	
	//This should be in a database
	private static final int PHRASE_TOTAL = 4;	

	private static final String PHRASE_ONE = "Back To the Drawing Board.";
	private static final String PHRASE_TWO = "Between a Rock and a Hard Place.";
	private static final String PHRASE_THREE = "Cry Wolf.";
	private static final String PHRASE_FOUR = "Cut To The Chase.";		
	private static final String PHRASE_DEFAULT = "Actions speak louder than words.";
	
	public static String getPhrase(int phraseNumber) {
		String phrase = PHRASE_DEFAULT;
		
		switch(phraseNumber) {
			case 1: phrase = PHRASE_ONE;
					break;
			case 2: phrase = PHRASE_TWO;
					break;
			case 3: phrase = PHRASE_THREE;
					break;
			case 4: phrase = PHRASE_FOUR;
					break;
			default: phrase = PHRASE_DEFAULT;
					break;	
		}			
		
		return phrase;
	}
	
	public static int getPhraseTotal() {
		return PHRASE_TOTAL;
	}
}
