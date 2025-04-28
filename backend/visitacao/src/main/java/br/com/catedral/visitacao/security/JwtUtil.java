package br.com.catedral.visitacao.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${auth-jwt-secret}")
	private String jwtSecret;
	
	@Value("${auth-jwt-expiration-miliseg}")
	private Long jwtExpirationMiliseg;
	
	public String generateToken(String username, String usuarioNome, String role, Long usuarioId) {
		SecretKey secreKeySpec = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder()
				.setSubject(username)
				.claim("nome", usuarioNome)
				.claim("id", usuarioId)
				.claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis() + 
						this.jwtExpirationMiliseg))
				.signWith(secreKeySpec)
				.compact();
	}
	
	public boolean isValidToken(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUserName(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtSecret.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}