package fr.rennes.epsi.poec.bob.guitarshop.Domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private String username;
    private String password;
    private String role;
    private boolean checked = true;


    // ================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(UserRole.valueOf(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // ================================
    @Override
    public boolean isAccountNonExpired() {
        return this.checked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.checked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.checked;
    }

    @Override
    public boolean isEnabled() {
        return this.checked;
    }

    // ================================

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ================================

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
