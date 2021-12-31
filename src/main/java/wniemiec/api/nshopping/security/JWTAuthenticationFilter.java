package wniemiec.api.nshopping.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wniemiec.api.nshopping.dto.CredentialsDTO;
import wniemiec.api.nshopping.security.exception.UnknownException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/**
 * Responsible for handling JWT authentication.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, 
                                   JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());

        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) 
    throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationToken authToken = generateAuthToken(req);

            return authenticationManager.authenticate(authToken);
        }
        catch (IOException e) {
            throw new UnknownException(e.getMessage());
        }
    }


    private UsernamePasswordAuthenticationToken generateAuthToken(HttpServletRequest req)
            throws IOException {
        CredentialsDTO creds = getCredentials(req);

        return new UsernamePasswordAuthenticationToken(
            creds.getEmail(), 
            creds.getPassword(), 
            new ArrayList<>()
        );
    }


    private CredentialsDTO getCredentials(HttpServletRequest req) throws IOException {
        return new ObjectMapper().readValue(
            req.getInputStream(), 
            CredentialsDTO.class
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) 
    throws IOException, ServletException {
        String token = generateJwt(auth);
        
        addAuthorizationHeader(res, token);
    }


    private void addAuthorizationHeader(HttpServletResponse res, String token) {
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");
    }


    private String generateJwt(Authentication auth) {
        String username = extractUsernameFromAuthentication(auth);
        
        return jwtUtil.generateToken(username);
    }


    private String extractUsernameFromAuthentication(Authentication auth) {
        return ((UserSpringSecurity) auth.getPrincipal()).getUsername();
    }


    //-------------------------------------------------------------------------
    //		Classes
    //-------------------------------------------------------------------------
    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, 
                                            HttpServletResponse response, 
                                            AuthenticationException exception)
        throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Unauthorized\", "
                    + "\"message\": \"Incorrect email and / or password\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
