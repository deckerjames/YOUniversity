package com.libertymutual.goforcode.youniversity.contollers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.libertymutual.goforcode.youniversity.apiControllers.SessionApiController;
import com.libertymutual.goforcode.youniversity.apiControllers.SessionApiController.Credentials;
import com.libertymutual.goforcode.youniversity.models.User;

public class SessionApiControllerTests {

	
	private UserDetailsService userDetails;
    private AuthenticationManager authenticator;
    
    private SessionApiController controller;
    private Authentication auth;
    private Credentials credentials;
    
     
    
    
    @Before
	public void setUp() {
    	userDetails = mock(UserDetailsService.class);
    	authenticator = mock(AuthenticationManager.class);
    	auth = mock(Authentication.class);
    	credentials = new Credentials();
 
    	controller = new SessionApiController(userDetails, authenticator);
    }
	
    @Test
    public void test_getId_returns_logged_in_users_id() {
    	//Arrange
    	User user = new User();
    	user.setId(7L);
    	when(auth.getPrincipal()).thenReturn(user);
    	//Act
    	Long actual = controller.getLoggedInUserIdBecauseThatSoundsFunEvenThoughItMayNotActuallyBeFunInTheTrueSenseOfTheWord(auth);
    	//Assert
    	assertThat(user.getId()).isEqualTo(actual);
    	
    }
    
    @Test
    public void test_getId_returns_null_when_not_logged_in() {
    	//Arrange
    	
    	//Act
    	Long actual = controller.getLoggedInUserIdBecauseThatSoundsFunEvenThoughItMayNotActuallyBeFunInTheTrueSenseOfTheWord(null);
    	//Assert
    	assertThat(actual).isNull();
    	
    }
    
    @Test
    public void test_getAuthorities_logs_in_a_user() {
    	//Arrange
    	
    	credentials.setPassword("password");
    	credentials.setUsername("Lamar");
    	
    	
    	User user = new User();
    	user.setUsername("Lamar");
    	
    	
    	when(userDetails.loadUserByUsername("Lamar")).thenReturn(user);
    	
    	
    	//Act
    	UserDetails actual = controller.login(credentials);
    	
    	
    	//Assert
    	verify(authenticator).authenticate(any(UsernamePasswordAuthenticationToken.class));
    	assertThat(actual).isSameAs(user);
    }
    
    @Test
    public void test_logout_logs_out_a_user() {
    	//Arrange
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class);
    	
    	    			
    	//Act
    	Boolean actual = controller.logout(auth, request, response);
    	//Assert
    	
    	assertThat(actual).isTrue();
    }
    
}
