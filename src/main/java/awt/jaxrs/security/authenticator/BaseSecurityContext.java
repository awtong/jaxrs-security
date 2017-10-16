package awt.jaxrs.security.authenticator;

import java.security.Principal;
import java.util.*;

import javax.ws.rs.core.SecurityContext;

public class BaseSecurityContext implements SecurityContext {
    private final Principal principal;
    private final Collection<String> roles;
    private final String scheme;
    private final boolean secure;

    public BaseSecurityContext(final Principal principal, final Collection<String> roles, final String scheme,
	    final boolean secure) {
	this.principal = principal;
	this.roles = roles == null ? Collections.emptyList() : roles;
	this.scheme = scheme;
	this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
	return this.principal;
    }

    @Override
    public boolean isUserInRole(final String role) {
	return this.roles.contains(role);
    }

    @Override
    public boolean isSecure() {
	return this.secure;
    }

    @Override
    public String getAuthenticationScheme() {
	return this.scheme;
    }

    @Override
    public String toString() {
	return "BaseSecurityContext [principal=" + this.principal + ", roles=" + this.roles + ", scheme=" + this.scheme
		+ ", secure=" + this.secure + "]";
    }
}
