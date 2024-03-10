package fplhn.udpm.identity.infrastructure.security;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {
    @Getter
    private Long id;
    @Getter
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    @Setter
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserPrincipal create(Student student) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"));

        return new UserPrincipal(
                student.getId(),
                student.getEmailFpt(),
                authorities
        );
    }

    public static UserPrincipal create(Staff staff) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_STAFF"));

        return new UserPrincipal(
                staff.getId(),
                staff.getAccountFPT(),
                authorities
        );
    }

    public static UserPrincipal create(Staff staff, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(staff);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
