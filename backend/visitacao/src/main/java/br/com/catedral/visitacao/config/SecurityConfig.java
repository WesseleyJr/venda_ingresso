package br.com.catedral.visitacao.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.catedral.visitacao.security.JwtAuthenticationFilter;
import br.com.catedral.visitacao.security.JwtAuthorizationFilter;
import br.com.catedral.visitacao.security.JwtUtil;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.cors((cors) -> cors.configurationSource(corsConfigurationSource()))
		.authorizeHttpRequests(authorize -> 
	        authorize
	            .requestMatchers(HttpMethod.GET, "/usuario/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
	            .requestMatchers(HttpMethod.GET, "/agenda/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/agenda").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/agenda/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/agenda/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/guia/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/guia").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/guia/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/guia/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/ingresso/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/ingresso").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/ingresso").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/ingresso/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/cliente/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/cliente/**").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/cliente/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/cliente/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/pagamento/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/pagamento").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/pagamento/**").permitAll()
	            .requestMatchers(HttpMethod.PATCH, "/pagamento/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/pagamento/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/qrcode/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/qrcode").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/qrcode/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/qrcode/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/senha/**").permitAll()
	            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	 
	            .anyRequest().authenticated()
	    )
	    .httpBasic(Customizer.withDefaults())
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		JwtAuthenticationFilter jwtAuthenticationFilter = 
				new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
						, jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		JwtAuthorizationFilter jwtAuthorizationFilter = 
				new JwtAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
						jwtUtil, userDetailsService);
		
		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(jwtAuthorizationFilter);
		
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:8081"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
}
