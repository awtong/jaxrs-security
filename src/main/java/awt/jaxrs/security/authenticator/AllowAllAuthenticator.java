package awt.jaxrs.security.authenticator;

import awt.jaxrs.security.AuthorizationHeader;

/**
 * Allows anyone to log in.
 * 
 * @author awt
 */
public class AllowAllAuthenticator implements Authenticator {

    @Override
    public void authenticate(final AuthorizationHeader header) {
	// do nothing
    }
}
