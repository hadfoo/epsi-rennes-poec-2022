package fr.rennes.epsi.poec.bob.guitarshop.Domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_CUSTOMER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }


}
