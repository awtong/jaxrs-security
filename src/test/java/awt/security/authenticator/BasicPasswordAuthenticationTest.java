package awt.security.authenticator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Base64;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

import awt.jaxrs.security.authenticator.BasicPasswordAuthentication;

public class BasicPasswordAuthenticationTest {

    @Test
    public void testValidBasicAuthToken() {
	final String encoded = Base64.getEncoder().encodeToString("username:password".getBytes());

	final BasicPasswordAuthentication auth = new BasicPasswordAuthentication(encoded);
	assertThat(auth.getUsername(), is("username"));
	assertThat(auth.getPassword(), is("password"));
    }

    @Test
    public void testTooFewPartsInBasicAuthToken() {
	final String encoded = Base64.getEncoder().encodeToString("username".getBytes());

	try {
	    new BasicPasswordAuthentication(encoded);
	    fail("Should throw BadRequestException");
	} catch (final BadRequestException expected) {

	}
    }

    @Test
    public void testTooManyPartsInBasicAuthToken() {
	final String encoded = Base64.getEncoder().encodeToString("username:password:invalid".getBytes());

	try {
	    new BasicPasswordAuthentication(encoded);
	    fail("Should throw BadRequestException");
	} catch (final BadRequestException expected) {

	}
    }
}
