package com.videoClub;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.videoClub.security.TokenHelper;
import com.videoClub.auth.RestAuthenticationEntryPoint;
import com.videoClub.auth.TokenAuthenticationFilter;
import com.videoClub.service.impl.CustomUserDetailsService;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	// Neautorizovani pristup zastcenim resursima
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenHelper tokenUtils;

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		// communication between client and server is stateless
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		// za neautorizovane zahteve posalji 401 gresku
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
		.authorizeRequests()
		.antMatchers("/auth/login").permitAll()
		.antMatchers(HttpMethod.GET,"/api/action/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/actions").permitAll()
		.antMatchers(HttpMethod.GET,"/api/actions/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/action_event/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/action_event/action/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/action_events").permitAll()
		.antMatchers(HttpMethod.GET,"/api/artist/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/artists").permitAll()
		.antMatchers(HttpMethod.GET,"/api/artists/actors/{videoId}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/artists/director/{videoId}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/offer/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/offers").permitAll()
		.antMatchers(HttpMethod.GET,"/api/bronze_immunity_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/silver_immunity_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/gold_immunity_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/bronze_title/acquire_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/bronze_title/save_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/bronze_title/reward_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/silver_title/acquire_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/silver_title/save_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/silver_title/reward_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/gold_title/acquire_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/gold_title/save_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/gold_title/reward_points").permitAll()
		.antMatchers(HttpMethod.GET,"/api/video_content/{id}").permitAll()
		.antMatchers(HttpMethod.GET,"/api/video_contents").permitAll()
		
		// every request needs to be authorized
		.anyRequest().authenticated().and()
		// presretni svaki zahtev filterom
		.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);
		http.csrf().disable();
	}

	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login", "/h2/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/login", "/h2/**", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
	}

}
