package awt.jaxrs.security;

import java.util.Objects;

import javax.ws.rs.BadRequestException;

/**
 * Object representation of an authorization header. Assumes header is in the
 * format [scheme] [token].
 *
 * @author awt
 */
public final class AuthorizationHeader {
    private static final int VALID_NUMBER_OF_PARTS = 2;
    private final String scheme;
    private final String token;

    public AuthorizationHeader(final String headerValue) {
	Objects.requireNonNull(headerValue);

	final String[] parts = headerValue.split(" ");
	if (parts.length != VALID_NUMBER_OF_PARTS) {
	    throw new BadRequestException();
	}

	this.scheme = parts[0];
	this.token = parts[1];
    }

    public String getScheme() {
	return this.scheme;
    }

    public String getToken() {
	return this.token;
    }

    @Override
    public String toString() {
	return "AuthorizationHeader [scheme=" + this.scheme + ", token=" + this.token + "]";
    }
}
