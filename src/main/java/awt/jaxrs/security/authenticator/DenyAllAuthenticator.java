package awt.jaxrs.security.authenticator;

import java.util.Map;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.*;

import awt.jaxrs.security.AuthorizationHeader;

/**
 * Does not allow anyone to log in.
 *
 * @author awt
 */
public class DenyAllAuthenticator implements Authenticator {

    @Override
    public SecurityContext authenticate(final AuthorizationHeader header, final Map<String, String[]> parameters) {
	throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
