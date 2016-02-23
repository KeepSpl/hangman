package demo.hangman.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.hangman.business.GuessHandler;
import demo.hangman.model.PhraseModel;
import demo.hangman.model.StateModel;

import java.io.Serializable;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

//Implements Serializable to utilize tomcat serialization feature,
//when server restarts tomcat serializes the session state
//I would prefer to use any kind of a database (relational or NoSQL)
@Controller
@Scope("session")
@RequestMapping(value = "/mainview")
public class GuessCharController implements Serializable {	 
	private GuessHandler guessH = new GuessHandler();
	private StateModel stModel = guessH.getStateModel();
	private String sessionId = "";	
	
	@RequestMapping(value = "/initial")	
	public ModelAndView userinfo(
			@RequestParam(value = "username", required = true, defaultValue = "") String username,
			@RequestParam(value = "nexttry", required = true, defaultValue = "false") String nexttry,
			@CookieValue(value = "hangmanSId", defaultValue = "Non") String hangmanSId, HttpServletResponse response) {	
		ModelAndView mv = new ModelAndView("mainview");	
		
		handleCookieTracking(hangmanSId, response);
		
		if(nexttry.equals("true")) {			
			int nextPhrase = guessH.nextPhrase(stModel.getPhraseNumber());			
			stModel.setSelectedPhrase(PhraseModel.getPhrase(nextPhrase));
			stModel.setStatus("false"); 
			stModel.setWrongGuessCount(0);
			stModel.setGuessesRemaining(21);
			stModel.setRegexReturnPhrase(new StringBuilder("[^]"));	
			guessH.setStateModel(stModel);
			stModel.setPhraseState(guessH.showGuessedChars(null, nextPhrase));
			
			//Update the session state
			StateModel.getTrackUser().put(sessionId, stModel);
			
			mv.addObject("status", "false");
			mv.addObject("username", stModel.getUsername());
			mv.addObject("phraseState", stModel.getPhraseState());			
		} else if(hangmanSId.trim().equals("Non")) {			
			stModel.setUsername(username);
			stModel.setSelectedPhrase(PhraseModel.getPhrase(0));
			stModel.setPhraseState(guessH.showGuessedChars(null, 0));//Default first				
			
			mv.addObject("status", stModel.getStatus());
			mv.addObject("username", stModel.getUsername());
			mv.addObject("phraseState", stModel.getPhraseState());			
		} else {			
			mv.addObject("status", stModel.getStatus());
			mv.addObject("username", stModel.getUsername());
			mv.addObject("phraseState", stModel.getPhraseState());
			mv.addObject("wrongguesses", stModel.getWrongGuessCount());
			mv.addObject("remaining", stModel.getGuessesRemaining());
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/guessedchar")
	@ResponseBody
	public String guessedchar(
			@RequestParam(value = "gchar", required = true, defaultValue = "") String gchar) {		
		stModel.setContinueGame(guessH.continueGame());
		stModel.setSuccessPlay(guessH.successPlay(stModel.getPhraseState(), stModel.getSelectedPhrase()));
		
		//Update the session state
		StateModel.getTrackUser().put(sessionId, stModel);	
		
		//If guesses remaining continue, else return the status 
		if(stModel.isContinueGame() && !stModel.isSuccessPlay()) {
			String phrase = guessH.showGuessedChars(gchar, stModel.getPhraseNumber()); //Check the new guess
			stModel.setPhraseState(phrase);
			stModel.setContinueGame(guessH.continueGame());
			stModel.setSuccessPlay(guessH.successPlay(stModel.getPhraseState(), stModel.getSelectedPhrase()));
			
			//Update the session state
			StateModel.getTrackUser().put(sessionId, stModel);	
			
			//If STILL guesses remaining continue, else return the status
			if(stModel.isContinueGame() && !stModel.isSuccessPlay()) {				
				return phrase;
			} else {				
				return "redirectState";
			}
		} else {		
			return "redirectState";
		}				
	}
	
	@RequestMapping(value = "/state")	
	public ModelAndView state() {
		ModelAndView mvState = new ModelAndView("mainview");				
		stModel.setStatus("true");
		
		//Update the session state
		StateModel.getTrackUser().put(sessionId, stModel);	
		
		if(stModel.isSuccessPlay()) {
			mvState.addObject("succeed", "true");
		}
		
		mvState.addObject("status", stModel.getStatus());
		mvState.addObject("username", stModel.getUsername());		
		mvState.addObject("originphrase", stModel.getSelectedPhrase());
		mvState.addObject("phraseState", stModel.getPhraseState());
		mvState.addObject("remaining", stModel.getGuessesRemaining());
		mvState.addObject("wrongguesses", stModel.getWrongGuessCount());
		
		return mvState;
	}	
	
	private void handleCookieTracking(String sessionId, HttpServletResponse resp) {
		this.sessionId = sessionId;
		stModel = StateModel.getTrackUser().get(sessionId);		
		
		if(stModel == null) {
			final String newSessionId = UUID.randomUUID().toString().replaceAll("-", "");
			this.sessionId = newSessionId;
			stModel = guessH.getStateModel();
			
			StateModel.getTrackUser().put(newSessionId, stModel);
			
			// create cookie and set it in response
	        Cookie cookie = new Cookie("hangmanSId", newSessionId);	        
	        cookie.setMaxAge(24*60*60); //24 hours cookie lifetime
	        resp.addCookie(cookie);
		} else {
			guessH.setStateModel(stModel);
		}		
	}
}
