package app;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtKey {
	
	private static JwtKey jwtkey = null;
	private Key key;

	private JwtKey() {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
	
	public static JwtKey getInstance() {
		if(jwtkey == null)jwtkey = new JwtKey();
		return jwtkey;
	}

	public Key getKey() {
		return key;
	}

}
