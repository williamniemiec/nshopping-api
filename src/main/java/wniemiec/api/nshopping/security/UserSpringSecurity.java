package wniemiec.api.nshopping.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wniemiec.api.nshopping.domain.enums.Profile;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Responsible for handling user authorization.
 */
public class UserSpringSecurity implements UserDetails {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public UserSpringSecurity() {
    }

    public UserSpringSecurity(Integer id, String email, String password, 
                              Set<Profile> profiles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = profiles
            .stream()
            .map(profile -> new SimpleGrantedAuthority(profile.getLabel()))
            .collect(Collectors.toList());
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Profile profile) {
        return authorities.contains(new SimpleGrantedAuthority(profile.getLabel()));
    }


    //-------------------------------------------------------------------------
    //		Getters
    //-------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
