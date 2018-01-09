package awt.jaxrs.security;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.IOException;
import java.util.*;

import javax.annotation.Priority;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;

import awt.jaxrs.security.authenticator.Authenticator;

/**
 * Executes specified authentication method.
 *
 * @author awt
 * @see Secured
 */
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {
    private final Collection<Secured> authentications;

    public SecurityFilter(final Collection<Secured> authentications) {
	this.authentications = authentications == null ? Collections.emptyList() : authentications;
    }

    @Override
    public void filter(final ContainerRequestContext context) throws IOException {
	final String authorization = context.getHeaderString(AUTHORIZATION);
	if ((authorization == null) || authorization.isEmpty()) {
	    if (this.authentications.isEmpty()) {
		// Should not happen in real life as the filter should not be
		// registered if @Authentication annotation not present.
		throw new NotAuthorizedException(Response.status(UNAUTHORIZED).build());
	    } else {
		boolean first = true;
		String initialChallenge = null;
		final Collection<String> moreChallenges = new ArrayList<>();
		for (final Secured authentication : this.authentications) {
		    if (first) {
			initialChallenge = this.createChallenge(authentication);
			first = false;
		    } else {
			moreChallenges.add(this.createChallenge(authentication));
		    }
		}

		throw new NotAuthorizedException(initialChallenge,
			moreChallenges.toArray(new Object[moreChallenges.size()]));
	    }
	}

	final AuthorizationHeader header = new AuthorizationHeader(authorization);
	final Optional<Secured> opt = this.authentications.stream()
		.filter(authentication -> header.getScheme().equalsIgnoreCase(authentication.scheme())).findFirst();
	final Secured authentication = opt
		.orElseThrow(() -> new NotAuthorizedException(Response.status(UNAUTHORIZED).build()));
	try {
	    final Authenticator authenticator = authentication.authenticator().newInstance();
	    final Map<String, String[]> parameters = new HashMap<>();
	    if (authentication.parameters() != null) {
		for (final Param param : authentication.parameters()) {
		    parameters.put(param.key(), param.values());
		}
	    }

	    final SecurityContext security = authenticator.authenticate(header, parameters);
	    context.setSecurityContext(security);
	} catch (final InstantiationException | IllegalAccessException exception) {
	    throw new IOException(exception);
	}
    }

    private String createChallenge(final Secured authentication) {
	return String.format("%s realm=\"%s\"", authentication.scheme(), authentication.realm());
    }
}
