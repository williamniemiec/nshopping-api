package wniemiec.api.nshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import wniemiec.api.nshop.security.JWTAuthenticationFilter;
import wniemiec.api.nshop.security.JWTAuthorizationFilter;
import wniemiec.api.nshop.security.JWTUtil;
import java.util.Arrays;
import java.util.List;


/**
 * Responsible for configuring authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final String[] PUBLIC_MATCHERS;
    private static final String[] PUBLIC_MATCHERS_GET;
    private static final String[] PUBLIC_MATCHERS_POST;
    private static final List<String> CORS_ALLOWED_METHODS;

    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;


    //-------------------------------------------------------------------------
    //		Initialization blocks
    //-------------------------------------------------------------------------
    static {
        PUBLIC_MATCHERS = new String[] {
            "/h2-console/**"
        };

        PUBLIC_MATCHERS_GET = new String[] {
            "/products/**",
            "/categories/**",
            "/states/**"
        };

        PUBLIC_MATCHERS_POST = new String[] {
            "/clients",
            "/auth/forgot/**"
        };

        CORS_ALLOWED_METHODS = Arrays.asList(
            "GET", 
            "POST", 
            "PUT", 
            "DELETE", 
            "OPTIONS"
        );
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureHttpCors(http);
        configureHttpRequestsAllowed(http);
        configureHttpSession(http);
        configureHttpFilters(http);

        if (isTestProfileActive()) {
            configureHttpUsingTestProfile(http);
        }
    }

    private void configureHttpCors(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable();
    }

    private void configureHttpRequestsAllowed(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .antMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest()
            .authenticated();
    }

    private void configureHttpSession(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureHttpFilters(HttpSecurity http) throws Exception {
        http.addFilter(new JWTAuthenticationFilter(
            authenticationManager(), 
            jwtUtil
        ));
        http.addFilter(new JWTAuthorizationFilter(
            authenticationManager(), 
            jwtUtil, 
            userDetailsService
        ));
    }

    private void configureHttpUsingTestProfile(HttpSecurity http) throws Exception {
        http
            .headers()
            .frameOptions()
            .disable();
    }

    private boolean isTestProfileActive() {
        List<String> activeProfiles = getActiveProfiles();

        return activeProfiles.contains("test");
    }

    private List<String> getActiveProfiles() {
        return Arrays.asList(env.getActiveProfiles());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = getCorsConfiguration();
        final UrlBasedCorsConfigurationSource src = getCorsUrlConfiguration();
        
        configureCorsAllowedMethods(corsConfiguration);
        configureCorsAllowedUrl(corsConfiguration, src);

        return src;
    }

    private UrlBasedCorsConfigurationSource getCorsUrlConfiguration() {
        return new UrlBasedCorsConfigurationSource();
    }

    private CorsConfiguration getCorsConfiguration() {
        return new CorsConfiguration().applyPermitDefaultValues();
    }

    private void configureCorsAllowedMethods(CorsConfiguration corsConfiguration) {
        corsConfiguration.setAllowedMethods(CORS_ALLOWED_METHODS);
    }

    private void configureCorsAllowedUrl(CorsConfiguration corsConfiguration, 
                                         UrlBasedCorsConfigurationSource src) {
        src.registerCorsConfiguration("/**", corsConfiguration);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        configurePasswordEncoder(auth);
    }

    private void configurePasswordEncoder(AuthenticationManagerBuilder auth) 
    throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
