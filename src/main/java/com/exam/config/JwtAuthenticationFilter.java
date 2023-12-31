package com.exam.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	       String requestHeader = request.getHeader("Authorization");
	        //Bearer 2352345235sdfrsfgsdfsdf
	        logger.info(" Header :  {}", requestHeader);
	        String username = null;
	        String token = null;
	        
	        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
	            //looking good
	            token = requestHeader.substring(7);
	            try {

	                username = this.jwtUtil.getUsernameFromToken(token);

	            } catch (IllegalArgumentException e) {
	                logger.info("Illegal Argument while fetching the username !!");
	                e.printStackTrace();
	            } catch (ExpiredJwtException e) {
	                logger.info("Given jwt token is expired !!");
	                e.printStackTrace();
	            } catch (MalformedJwtException e) {
	                logger.info("Some changed has done in token !! Invalid Token");
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();

	            }


	        } else {
	            logger.info("Invalid Header Value !! ");
	        }


	        //
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


	            //fetch user detail from username
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            Boolean validateToken = this.jwtUtil.validateToken(token, userDetails);
	            if (validateToken) {

	                //set the authentication
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);


	            } else {
	                logger.info("Validation fails !!");
	            }


	        }

	        filterChain.doFilter(request, response);
		
	}
	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		final String requestTokenHeader = request.getHeader("Authorization");
//		
//		System.out.println(requestTokenHeader);
//		String username = null;
//		String jwtToken = null;
//		
//		if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer ")) {
//			//yes
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username=this.jwtUtil.extractUsername(jwtToken);
//			} catch (ExpiredJwtException e) {
//				e.printStackTrace();
//				System.out.println("jwt token expired");
//			}catch(Exception e) {
//				e.printStackTrace();
//				System.out.println("Error");
//			}
//		}
//		else {
//			System.out.println("Token is invalid not strat with bearer");
//		}
//		
//		//Now validate the token
//		if(username!=null && SecurityContextHolder.getContext().getAuthentication()== null) {
//			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//			
//			if(this.jwtUtil.validateToken(jwtToken, userDetails)) {
//				
//				//token is valid
//				
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//			}
//		}
//		else {
//			System.out.println("Token is not valid");
//		}
//		
//		filterChain.doFilter(request, response);
//		
//			
//	}

}
