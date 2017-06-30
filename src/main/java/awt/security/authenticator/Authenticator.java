package awt.security.authenticator;

import awt.security.AuthorizationHeader;

public interface Authenticator {
    void authenticate(final AuthorizationHeader header);
}
