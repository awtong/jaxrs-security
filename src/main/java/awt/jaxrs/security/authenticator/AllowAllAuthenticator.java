package awt.jaxrs.security.authenticator;

import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import awt.jaxrs.security.AuthorizationHeader;

/**
 * Allows anyone to log in.
 *
 * @author awt
 */
public class AllowAllAuthenticator implements Authenticator {

    @Override
    public SecurityContext authenticate(final AuthorizationHeader header, final Map<String, String[]> parameters) {
	return null;
    }
}
