package awt.jaxrs.security.authenticator;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import awt.jaxrs.security.AuthorizationHeader;

/**
 * Does not allow anyone to log in.
 * 
 * @author awt
 */
public class DenyAllAuthenticator implements Authenticator {

    @Override
    public void authenticate(final AuthorizationHeader header) {
	throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
