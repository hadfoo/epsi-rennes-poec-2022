package fr.epsi.rennes.poec.manucre.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private String role;
	private boolean active = true;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String getPassword() {
		return password;
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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(UserRole.valueOf(role));
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return active;
	}
	@Override
	public boolean isAccountNonLocked() {
		return active;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}
	@Override
	public boolean isEnabled() {
		return active;
	}
	
	
	
	
}