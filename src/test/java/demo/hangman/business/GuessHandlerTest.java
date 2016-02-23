package demo.hangman.business;

import junit.framework.Assert;

import org.junit.Test;

import demo.hangman.model.StateModel;

public class GuessHandlerTest {
	
	@Test
	public void testShowGuessedChars() {		
		GuessHandler gh = new GuessHandler();		
		String viewPhrase = gh.showGuessedChars(null, 10);		
				
		Assert.assertEquals("_ _ _ _ _ _ _  - _ _ _ _ _  - _ _ _ _ _ _  - _ _ _ _  - _ _ _ _ _ .", viewPhrase);		
		
		viewPhrase = gh.showGuessedChars("n", 5);
		
		Assert.assertEquals("_ _ _ _ _ n _  - _ _ _ _ _  - _ _ _ _ _ _  - _ _ _ n  - _ _ _ _ _ .", viewPhrase);		
	}	
	
	@Test
	public void testGuessedChars() {
		GuessHandler gh = new GuessHandler();		
		String phrase = "Actions speak louder than words";
		String result1 = gh.guessedChars("n", phrase);
		
		Assert.assertEquals("_____n_ _____ ______ ___n _____", result1);
		
		String result2 = gh.guessedChars("o", phrase);
		
		Assert.assertEquals("____on_ _____ _o____ ___n _o___", result2);		
	}
	
	@Test
	public void testSuccessPlay() {
		GuessHandler gh = new GuessHandler();
		
		String actualPhrase = "A c t i o n s - s p e a k - l o u d e r - t h a n - w o r d s.";
		String expectedPhrase = "Actions speak louder than words.";
		
		boolean test = gh.successPlay(actualPhrase, expectedPhrase);
		
		Assert.assertTrue(test);
	}
}
