package awt.jaxrs.security.authenticator;

import java.util.*;

import javax.ws.rs.BadRequestException;

/**
 * Parses an authorization token into username/password. Helpful for when scheme
 * is Basic.
 * 
 * @author awt
 */
public class BasicPasswordAuthentication {
    private final String username;
    private final String password;

    public BasicPasswordAuthentication(final String token) {
	Objects.requireNonNull(token);

	final String decoded = new String(Base64.getDecoder().decode(token));

	final String[] parts = decoded.split(":");
	if (parts.length != 2) {
	    throw new BadRequestException();
	}

	this.username = parts[0];
	this.password = parts[1];
    }

    public String getUsername() {
	return this.username;
    }

    public String getPassword() {
	return this.password;
    }

    @Override
    public String toString() {
	return "BasicPasswordAuthentication [username=" + this.username + ", password=" + this.password + "]";
    }
}
