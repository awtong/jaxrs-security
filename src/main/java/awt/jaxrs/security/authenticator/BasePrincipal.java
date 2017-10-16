package awt.jaxrs.security.authenticator;

import java.security.Principal;

public class BasePrincipal implements Principal {
    private final String name;

    public BasePrincipal(final String name) {
	this.name = name;
    }

    @Override
    public String getName() {
	return this.name;
    }

    @Override
    public String toString() {
	return "BasePrincipal [name=" + this.name + "]";
    }
}
