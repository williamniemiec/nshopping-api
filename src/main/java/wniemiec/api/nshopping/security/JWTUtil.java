package wniemiec.api.nshopping.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;


/**
 * Responsible for providing JWT utilities.
 */
@Component
public class JWTUtil {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();
    }

    public boolean isValidToken(String token) {
        boolean validToken = true;

        try {
            validToken = isValidClaim(getClaims(token));
        } 
        catch (IOException e) {
            validToken = false;
        }
        
        return validToken;
    }

    private boolean isValidClaim(Claims claims) {
        return  hasUsername(claims) 
                && isValidExpirationDate(claims);
    }

    private boolean hasUsername(Claims claims) {
        return (claims.getSubject() != null);
    }

    private boolean isValidExpirationDate(Claims claims) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = claims.getExpiration();

        return  (expirationDate != null) 
                && now.before(expirationDate);
    }

    private Claims getClaims(String token) throws IOException {
        try {
            return Jwts
                .parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        }
        catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public String getUsername(String token) {
        String username;

        try {
            Claims claims = getClaims(token);
            
            username = claims.getSubject();
        }
        catch (IOException e) {
            username = null;
        }

        return username;
    }
}
