package demo.hangman.business;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import demo.hangman.model.PhraseModel;
import demo.hangman.model.StateModel;

public class GuessHandler implements Serializable {		
	private StateModel stateModel = new StateModel();
	private StringBuilder regexReturnPhrase = stateModel.getRegexReturnPhrase();
	private int wrongGCount = 0;
	private int gRemaining = 21; 

	public String showGuessedChars(String guessedChar, int phraseNumber) {		
		String phrase = PhraseModel.getPhrase(phraseNumber);
		String phraseResult = guessedChars(guessedChar, phrase);
		StringBuilder sb = new StringBuilder();//Temp to add spaces
		
		char[] modifyPhrase = phraseResult.toCharArray();
		
		//Add spaces
		for(int i=0; i<modifyPhrase.length; i++) {
			if(" ".equals(String.valueOf(modifyPhrase[i]))){
				sb.append(" - ");
			} else {
				sb.append(modifyPhrase[i]).append(" ");
			}		
		}
		sb.deleteCharAt(sb.length() -1);
		phraseResult = sb.toString();
		
		return phraseResult;
	}
	
	public String guessedChars(String guessedChar, String phrase) {		
		guessedChar =  guessedChar == null ? "\\." : guessedChar; //Initial call		
		boolean hit = false;
		String lowerCaseChar = guessedChar.toLowerCase();
		String upperCaseChar = guessedChar.toUpperCase();
		String regexGuessedChar = lowerCaseChar+ "|" + upperCaseChar;		
		Set<String> hitList = new HashSet<String>();
		
		gRemaining = stateModel.getGuessesRemaining();
		gRemaining--; 
		stateModel.setGuessesRemaining(gRemaining);
		
		Matcher charMatcher = Pattern.compile(regexGuessedChar).matcher(phrase);		
		
		while(charMatcher.find()) {			
			hitList.add(charMatcher.group(0));
			hit = true;
		}
		
		//If not hit increase wrong guess
		wrongGCount = stateModel.getWrongGuessCount();
		wrongGCount = hit ? wrongGCount : ++wrongGCount;		
		stateModel.setWrongGuessCount(wrongGCount);		
		
		regexReturnPhrase = stateModel.getRegexReturnPhrase();
		regexReturnPhrase.deleteCharAt(regexReturnPhrase.length() -1);	
		
		//Prepare regex pattern for new return of the phrase
		for(Iterator<String> hitIt = hitList.iterator(); hitIt.hasNext();) {
			regexReturnPhrase.append(hitIt.next());			
		}		
		
		regexReturnPhrase.append("]");
		stateModel.setRegexReturnPhrase(regexReturnPhrase);
				
		String[] phraseArray = phrase.split("\\s");
		StringBuilder returnPhrase = new StringBuilder();
		
		//Replaces all characters in the phrase with underscore, except found chars and white spaces
		for(int i=0; i<phraseArray.length; i++){
			String word = phraseArray[i].replaceAll(regexReturnPhrase.toString(), "_");			
			returnPhrase.append(word).append(" ");			
		}		
		
		returnPhrase.deleteCharAt(returnPhrase.length() -1);	
		
		return returnPhrase.toString();		
	}	
	
	public boolean continueGame() {
		wrongGCount = stateModel.getWrongGuessCount();
		
		if(wrongGCount >= 6) {
			return false;
		} else {
			return true;
		}
	}
	
	public int nextPhrase(int phraseNumber) {
		phraseNumber++;
		
		//If max limits of phrase is exceeded. Start over again
		if(phraseNumber > PhraseModel.getPhraseTotal()) {
			stateModel.setPhraseNumber(0);
		} else {
			stateModel.setPhraseNumber(phraseNumber);
		}
		
		//Reset state and regular expression for the new phrase		
		wrongGCount = 0;
		gRemaining = 21;
		regexReturnPhrase = stateModel.getRegexReturnPhrase();
		regexReturnPhrase.delete(3, regexReturnPhrase.length()); //Holding only [^]
		stateModel.setRegexReturnPhrase(regexReturnPhrase);
		
		return phraseNumber;
	}
	
	public boolean successPlay(String actualPhrase, String expectedPhrase) {		
		String act = actualPhrase.replaceAll("\\s+","").replaceAll("-", "");
		String exp = expectedPhrase.replaceAll("\\s+","");
		
		if(exp.equals(act)) {
			return true;
		} else {
			return false;
		}		
	}

	public StateModel getStateModel() {
		return stateModel;
	}	
	
	public void setStateModel(StateModel stateModel) {
		this.stateModel = stateModel;
	}	
}
