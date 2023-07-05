package com.exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class MySecurityConfig {
	
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
//	public UserDetailsService geUserDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
	 
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		
		return provider;
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
        		.cors(cors->cors.disable())
        		.authorizeHttpRequests(
        				auth->auth
        				.requestMatchers("/generate-token","/user/").permitAll().
        				requestMatchers(HttpMethod.OPTIONS).permitAll().
        				anyRequest().authenticated())
        		.exceptionHandling(ex->ex.authenticationEntryPoint(unauthorizedHandler))
        		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		
        		;
        		            
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authenticationProvider(authenticationProvider());
        return http.build();
    }
	
//	@Bean
//	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
//		http.
//		  csrf().disable()
//		  .cors().disable()
//		  .authorizeHttpRequests().requestMatchers("/generate-token","/user/").permitAll()
//		  .requestMatchers(HttpMethod.OPTIONS).permitAll()
//		  .anyRequest().authenticated()
//		  .and()
//		  .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//		  .and()
//		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		  
//		http.authenticationProvider(authenticationProvider());
//	    return http.build();
//	}
	
	
}