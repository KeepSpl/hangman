<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>  
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Hangman Demo</title>  
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
  </script>
  <script>
  	$(document).ready(function($){  		
  		$('.selt_char').click(function(event) {
  			//Prevent form submit
  			event.preventDefault();
  			var charValue = $(this).text();
  			var sendData = "gchar=" + charValue;
  			$(this).css('color','red');  			
  			$.ajax({
  				type : "POST",
  				url : "/hangman/mainview/guessedchar",
  				data: sendData,
  				success : function(data) {  					
  					if(data == "redirectState") {
  						//Like deactivate event.preventDefault() and submit form
  						$("#mainform").unbind('submit').submit();
  					} else {
  						//Return the guessing phrase, and empty status variables  						
  						$("#phrase").html(data);  						
  						$("#phraseState").html("");
  						$("#failed").html("");  						
  						$("#originphrase").html("");
  						$("#remaining").html(""); 
  						$("#wrongguesses").html(""); 
  					}					
				}
  			});  			
  		});	     
	});	
  </script>	
 </head>
 <body> 
 	<form id="mainform" action="/hangman/mainview/state" method="post">   
 	<center>
 		<h2>Hangman Simple Version</h2>
 		<h3>${username}'s game</h3>  
 		<table>
 			<tr> 			  		
 			  <td><button id="A" class="selt_char">A</button></td>
 			  <td><button id="B" class="selt_char">B</button></td>
 			  <td><button id="C" class="selt_char">C</button></td>
 			  <td><button id="D" class="selt_char">D</button></td>
 			  <td><button id="E" class="selt_char">E</button></td>
 			  <td><button id="F" class="selt_char">F</button></td>
 			  <td><button id="G" class="selt_char">G</button></td>
 			  <td><button id="H" class="selt_char">H</button></td>
 			  <td><button id="I" class="selt_char">I</button></td>
 			  <td><button id="J" class="selt_char">J</button></td>
 			  <td><button id="K" class="selt_char">K</button></td>
 			  <td><button id="L" class="selt_char">L</button></td>
 			  <td><button id="M" class="selt_char">M</button></td>
 			</tr>
 			<tr> 			 
 			  <td><button id="N" class="selt_char">N</button></td>
 			  <td><button id="O" class="selt_char">O</button></td>
 			  <td><button id="P" class="selt_char">P</button></td>
 			  <td><button id="Q" class="selt_char">Q</button></td>
 			  <td><button id="R" class="selt_char">R</button></td>
 			  <td><button id="S" class="selt_char">S</button></td>
 			  <td><button id="T" class="selt_char">T</button></td>
 			  <td><button id="U" class="selt_char">U</button></td>
 			  <td><button id="V" class="selt_char">V</button></td>
 			  <td><button id="W" class="selt_char">W</button></td>
 			  <td><button id="X" class="selt_char">X</button></td>
 			  <td><button id="Y" class="selt_char">Y</button></td>
 			  <td><button id="Z" class="selt_char">Z</button></td>
 			</tr> 			
 		</table>
 		<h4>
 		  <div id="phrase"></div>
 		  <div id="phraseState">${phraseState}</div> 		  
 		  <c:if test="${status}">
 		    <div>
 		      <c:choose> 			    
 		        <c:when test="${succeed}">
 		          <p id="succeed">Congratulations you guessed it :)!</p>
 		        </c:when> 
 		        <c:otherwise>
 		          <p id="failed">Sorry, you have been hanged! The answer was:</p>
 		          <p id="originphrase">${originphrase}</p>
 		        </c:otherwise>
 		      </c:choose>		      
 		      <p id="wrongguesses">Unsuccessful guesses: ${wrongguesses}</p>
 		      <p id="remaining">Remaining guesses: ${remaining}</p>
 		      <p><a href="/hangman/mainview/initial?username=${username}&nexttry=true">Try again</a></p>
 		    </div>
 		  </c:if>											
		</h4>				
	</center>	
	</form>
 </body>
</html>