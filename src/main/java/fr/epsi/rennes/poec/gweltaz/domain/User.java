package fr.epsi.rennes.poec.gweltaz.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private static final long serialVersionUID = 1L;
	private String email;
    private String password;
    private String role;
    private boolean checked = true;

    public String getEmail() {
        return this.email;
    }

    // ================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(UserRole.ROLE_USER);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
