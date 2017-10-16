package awt.jaxrs.security.authenticator;

import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import awt.jaxrs.security.AuthorizationHeader;

public interface Authenticator {
    SecurityContext authenticate(final AuthorizationHeader header, final Map<String, String[]> parameters);
}
