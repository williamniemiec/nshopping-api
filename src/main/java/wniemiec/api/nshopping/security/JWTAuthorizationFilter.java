package wniemiec.api.nshopping.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Responsible for handling JWT authorization.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, 
                                  JWTUtil jwtUtil, 
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
    throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (hasJwtInHeader(authorizationHeader)) {
            doAuthentication(authorizationHeader);
        }

        chain.doFilter(request, response);
    }

    private void doAuthentication(String header) {
        UsernamePasswordAuthenticationToken auth = getAuthorization(header);
        
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    private boolean hasJwtInHeader(String authorization) {
        return  (authorization != null) 
                && authorization.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken getAuthorization(String header) {
        return getAuthorizationFromJwt(header.substring(7));
    }

    private UsernamePasswordAuthenticationToken getAuthorizationFromJwt(String token) {

        if (!jwtUtil.isValidToken(token)) {
            return null;
        }

        String username = jwtUtil.getUsername(token);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
