package awt.jaxrs.security.authenticator;

import awt.jaxrs.security.AuthorizationHeader;

public interface Authenticator {
    void authenticate(final AuthorizationHeader header);
}
