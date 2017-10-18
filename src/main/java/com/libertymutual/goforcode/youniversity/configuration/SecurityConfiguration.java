package com.libertymutual.goforcode.youniversity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	public SecurityConfiguration(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
        protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	    	.antMatchers(HttpMethod.GET, "/swagger-ui.html", "/webjars/**", "/swagger-resources/**","/v2/**", "/", "/css/**", "/js/**").permitAll()
	    	.antMatchers(HttpMethod.POST, "/user/create").permitAll()
	    	.antMatchers(HttpMethod.PUT, "/api/session/mine").permitAll()
		
	    	.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated()
				.and()
					.csrf().disable();
//		.addFilterAfter(new CsrfIntoCookieFilter(), CsrfFilter.class)
//		.csrf()
//		.csrfTokenRepository(tokenRepository());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}	
	
	@Override
	public UserDetailsService userDetailsService() {
	    return userDetailsService;
	}
	
	public CsrfTokenRepository tokenRepository() {
	    HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
	    tokenRepository.setHeaderName("X-XSRF-TOKEN");
	    tokenRepository.setParameterName("_csrf");	    
	    return tokenRepository;
	}
}
