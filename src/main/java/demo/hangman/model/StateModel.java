package demo.hangman.model;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class StateModel implements Serializable {
	
	private String status = "flase";	
	private String username = "";	
	private String selectedPhrase = "";
	private String phraseState = "";
	private int phraseNumber = 0;
	private int wrongGuessCount = 0;	
	private int guessesRemaining = 21;	
	private boolean isContinueGame = true;
	private boolean isSuccessPlay = false;
	private StringBuilder regexReturnPhrase = new StringBuilder("[^]");	
	private static ConcurrentHashMap<String, StateModel> trackUser = 
			   new ConcurrentHashMap<String, StateModel>();
	
	public static ConcurrentHashMap<String, StateModel> getTrackUser() {
		return trackUser;
	}
	
	public boolean isContinueGame() {
		return isContinueGame;
	}

	public void setContinueGame(boolean isContinueGame) {
		this.isContinueGame = isContinueGame;
	}

	public boolean isSuccessPlay() {
		return isSuccessPlay;
	}

	public void setSuccessPlay(boolean isSuccessPlay) {
		this.isSuccessPlay = isSuccessPlay;
	}

	public String getSelectedPhrase() {
		return selectedPhrase;
	}

	public void setSelectedPhrase(String selectedPhrase) {
		this.selectedPhrase = selectedPhrase;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPhraseState() {
		return phraseState;
	}
	
	public void setPhraseState(String phraseState) {
		this.phraseState = phraseState;
	}
	
	public int getPhraseNumber() {
		return phraseNumber;
	}
	
	public void setPhraseNumber(int phraseNumber) {
		this.phraseNumber = phraseNumber;
	}
	
	public int getGuessesRemaining() {
		return guessesRemaining;
	}
	
	public void setGuessesRemaining(int guessesRemaining) {
		this.guessesRemaining = guessesRemaining;
	}
	
	public int getWrongGuessCount() {
		return wrongGuessCount;
	}
	
	public void setWrongGuessCount(int wrongGuessCount) {
		this.wrongGuessCount = wrongGuessCount;
	}

	public StringBuilder getRegexReturnPhrase() {
		return regexReturnPhrase;
	}

	public void setRegexReturnPhrase(StringBuilder regexReturnPhrase) {
		this.regexReturnPhrase = regexReturnPhrase;
	}	
}
