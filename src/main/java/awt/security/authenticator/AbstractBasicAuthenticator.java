package awt.security.authenticator;

import awt.security.AuthorizationHeader;

/**
 * Extensible class for when scheme = Basic.
 * 
 * @author awt
 *
 */
public abstract class AbstractBasicAuthenticator implements Authenticator {

    @Override
    public void authenticate(final AuthorizationHeader header) {
	final BasicPasswordAuthentication auth = new BasicPasswordAuthentication(header.getToken());
	this.authenticate(auth.getUsername(), auth.getPassword());
    }

    abstract void authenticate(final String username, final String password);
}
